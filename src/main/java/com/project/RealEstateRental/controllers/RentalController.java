package com.project.RealEstateRental.controllers;

import com.project.RealEstateRental.models.*;
import com.project.RealEstateRental.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class RentalController {
    private static final String FOLDER_PATH="C:/Users/lukat/OneDrive/Desktop/myfiles/";

    @Autowired
    PropertiesRepository propertiesRepository;
    @Autowired
    OwnersRepository ownersRepository;
    @Autowired
    PropertyTagsRepository propertyTagsRepository;
    @Autowired
    BoroughsRepository boroughsRepository;
    @Autowired
    EquipmentsRepository equipmentsRepository;
    @Autowired
    PicturesRepository picturesRepository;
    @Autowired
    StructuresRepository structuresRepository;
    @Autowired
    TagsRepository tagsRepository;
    @Autowired
    TypesRepository typesRepository;
//int bathrooms, String heating, Equipments equipment, int status, int deposit, int price, String title, String description
    @PostMapping("/creatingProp")
    public Owners create(
            @RequestBody PropertyBody propertyBody
    ){
        Types type=typesRepository.findById(propertyBody.getTypeId()).orElseThrow();
        Structures struct = structuresRepository.findById(propertyBody.getStructureId()).orElseThrow();
        Boroughs borough = boroughsRepository.findById(propertyBody.getBoroughId()).orElseThrow();
        Equipments equip = equipmentsRepository.findById(propertyBody.getEquipmentId()).orElseThrow();

        Properties property = propertiesRepository.save(new Properties(
                    type,
                    struct,
                    propertyBody.getRooms(),
                    propertyBody.getSquareFootage(),
                    borough,
                    propertyBody.getFloor(),
                    propertyBody.getBathrooms(),
                    propertyBody.getHeating(),
                    equip,
                    propertyBody.getActive(),
                    propertyBody.getVisible(),
                    propertyBody.getCategory(),
                    propertyBody.getDeposit(),
                    propertyBody.getPrice(),
                    propertyBody.getTitle(),
                    propertyBody.getDescription()
                )
        );
        Owners owner = ownersRepository.save(new Owners(
                propertyBody.getName(),
                propertyBody.getEmail(),
                propertyBody.getPhone(),
                propertyBody.getContract(),
                propertyBody.getStreet(),
                propertyBody.getNumber(),
                propertyBody.getMoreInfo(),
                property
        ));

        List<Integer> tags=parseTagIds(propertyBody.getTagIds());
        for(Integer temp:tags){
            Tags tag=tagsRepository.findById(temp).orElseThrow();
            propertyTagsRepository.save(new Property_tags(property,tag));
        }
        return owner;
    }
    @PostMapping("/updateProp/{id}")
    @Transactional
    void update(
            @RequestBody PropertyBody propertyBody,
            @PathVariable int id
    ){
        Properties property=propertiesRepository.findById(id).orElseThrow();
        Types type=typesRepository.findById(propertyBody.getTypeId()).orElseThrow();
        Structures struct = structuresRepository.findById(propertyBody.getStructureId()).orElseThrow();
        Boroughs borough = boroughsRepository.findById(propertyBody.getBoroughId()).orElseThrow();
        Equipments equip = equipmentsRepository.findById(propertyBody.getEquipmentId()).orElseThrow();

        property.setType(type);
        property.setStructure(struct);
        property.setRooms(propertyBody.getRooms());
        property.setSquareFootage(propertyBody.getSquareFootage());
        property.setBorough(borough);
        property.setFloor(propertyBody.getFloor());
        property.setBathrooms(propertyBody.getBathrooms());
        property.setHeating(propertyBody.getHeating());
        property.setEquipment(equip);
        property.setActive(propertyBody.getActive());
        property.setDeposit(propertyBody.getDeposit());
        property.setPrice(propertyBody.getPrice());
        property.setTitle(propertyBody.getTitle());
        property.setDescription(propertyBody.getDescription());

        propertiesRepository.save(property);

        Owners owner = ownersRepository.findById(property.getIdProperty()).orElseThrow();
        owner.setName(propertyBody.getName());
        owner.setEmail(propertyBody.getEmail());
        owner.setPhone(propertyBody.getPhone());
        owner.setContract(propertyBody.getContract());
        owner.setStreet(propertyBody.getStreet());
        owner.setNumber(propertyBody.getNumber());

        ownersRepository.save(owner);

        propertyTagsRepository.deleteByProperty(property);

        List<Integer> tags=parseTagIds(propertyBody.getTagIds());
        for(Integer temp:tags){
            Tags tag=tagsRepository.findById(temp).orElseThrow();
            propertyTagsRepository.save(new Property_tags(property,tag));
        }
    }

    @GetMapping("/ownersandproperties")
    public List<Owners> getAll(){
        return ownersRepository.findAll();
    }
    @GetMapping("/properties")
    public List<Properties> filterProperties(
            @RequestParam(required = false) Integer typeId,
            @RequestParam(required = false) Integer structureId,
            @RequestParam(required = false) Integer rooms,
            @RequestParam(required = false) Integer squareFootage,
            @RequestParam(required = false) Integer bathrooms,
            @RequestParam(required = false) String heating,
            @RequestParam(required = false) Integer equipmentId,
            @RequestParam(required = false) Integer boroughId,
            @RequestParam(required = false) String floor,
            @RequestParam(required = false) Integer active,
            @RequestParam(required = false) Integer visible,
            @RequestParam(required = false) Integer category,
            @RequestParam(required = false) Integer deposit,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String tagIds) {

        List<Integer> tagIdsList = parseTagIds(tagIds);
        Integer numTags = tagIdsList.size();
        Types type = typeId != null ? typesRepository.findById(typeId).orElse(null) : null;
        Structures structure = structureId != null ? structuresRepository.findById(structureId).orElse(null) : null;
        Equipments equipment = equipmentId != null ? equipmentsRepository.findById(equipmentId).orElse(null) : null;
        Boroughs borough = boroughId != null ? boroughsRepository.findById(boroughId).orElse(null) : null;

        return propertiesRepository.findByFilter(
                type, structure, rooms, squareFootage, bathrooms, heating,
                equipment, borough, floor, active, visible, category, deposit, price, title, description, tagIdsList,numTags);
    }

    private List<Integer> parseTagIds(String tagIdsString) {
        List<Integer> tagIds = new ArrayList<>();
        if (tagIdsString != null && !tagIdsString.isEmpty()) {
            String[] tagIdsArray = tagIdsString.split(",");
            for (String tagId : tagIdsArray) {
                tagIds.add(Integer.parseInt(tagId));
            }
        }
        return tagIds;
    }
}


/*
    @GetMapping("/getimgs/{id}")
    public ResponseEntity<List<ByteArrayResource>> getImages(@PathVariable int id) throws IOException {
        Properties property = propertiesRepository.findById(id).orElseThrow();

        List<Pictures> existingPictures = picturesRepository.findByProperty(property);
        List<ByteArrayResource> images = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        if (existingPictures != null && !existingPictures.isEmpty()) {
            for (Pictures picture : existingPictures) {
                byte[] byteArray = Files.readAllBytes(new File(picture.getPicturePath()).toPath());
                images.add(new ByteArrayResource(byteArray));
            }
        }

        // Check if any images were found
        if (images.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no images found
        }

        return ResponseEntity.ok()
//                .headers(headers)
                .body(images);
    }

    @GetMapping("/getonlyone/{id}")
    public ResponseEntity<ByteArrayResource> getonlyone(
            @PathVariable int id) throws IOException {
        Properties property = propertiesRepository.findById(id).orElseThrow();

        List<Pictures> existingPictures = picturesRepository.findByProperty(property);
        List<ByteArrayResource> images = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        if (existingPictures != null && !existingPictures.isEmpty()) {
            for (Pictures picture : existingPictures) {
                byte[] byteArray = Files.readAllBytes(new File(picture.getPicturePath()).toPath());
                images.add(new ByteArrayResource(byteArray));
            }
        }

        // Check if any images were found
        if (images.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no images found
        }

        return ResponseEntity.ok()
//                .headers(headers)
                .body(images.get(0));
    }
    @GetMapping("/getone/{id}")
    public ResponseEntity<?> downloadI(
            @PathVariable int id) throws IOException {
        Properties property = propertiesRepository.findById(id).orElseThrow();
        List<Pictures> existingPictures = picturesRepository.findByProperty(property);
        List<ResponseEntity<?>> images = new ArrayList<>();
        int cnt = 0;
        if (existingPictures != null) {
            for (Pictures picture : existingPictures) {
                byte[] byteArray = Files.readAllBytes(new File(picture.getPicturePath()).toPath());
                images.add(ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.valueOf("image/jpeg"))
                        .body(byteArray));
                cnt++;
                //if(cnt==1)break;

            }
        }
        return images.get(2);
    }
    @GetMapping("/getImgs/{id}")
    public List<ResponseEntity<?>> downloadImageToFileSystem(
            @PathVariable int id) throws IOException {
        Properties property=propertiesRepository.findById(id).orElseThrow();
        List<Pictures> existingPictures = picturesRepository.findByProperty(property);
        List<ResponseEntity<?>> images = new ArrayList<>();
        int cnt=0;
        if( existingPictures!=null ){
            for(Pictures picture: existingPictures) {
                byte[] byteArray=Files.readAllBytes(new File(picture.getPicturePath()).toPath());
                images.add(ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.valueOf("image/jpeg"))
                        .body(byteArray));
                cnt++;
                //if(cnt==1)break;

            }
        }
        return images;
    }*/