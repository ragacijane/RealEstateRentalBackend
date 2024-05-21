package com.project.RealEstateRental.controller;

import com.project.RealEstateRental.model.*;
import com.project.RealEstateRental.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
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
    void create(
            @RequestBody PropertyRequest propertyRequest
    ){
        Types type=typesRepository.findById(propertyRequest.getTypeId()).orElseThrow();
        Structures struct = structuresRepository.findById(propertyRequest.getStructureId()).orElseThrow();
        Boroughs borough = boroughsRepository.findById(propertyRequest.getBoroughId()).orElseThrow();
        Equipments equip = equipmentsRepository.findById(propertyRequest.getEquipmentId()).orElseThrow();

        Properties property = propertiesRepository.save(new Properties(
                    type,
                    struct,
                    propertyRequest.getRooms(),
                    propertyRequest.getSquareFootage(),
                    borough,
                    propertyRequest.getFloor(),
                    propertyRequest.getBathrooms(),
                    propertyRequest.getHeating(),
                    equip,
                    propertyRequest.getActive(),
                    propertyRequest.getVisible(),
                    propertyRequest.getCategory(),
                    propertyRequest.getDeposit(),
                    propertyRequest.getPrice(),
                    propertyRequest.getTitle(),
                    propertyRequest.getDescription()
                )
        );
        Owners owner = ownersRepository.save(new Owners(
                propertyRequest.getName(),
                propertyRequest.getEmail(),
                propertyRequest.getPhone(),
                propertyRequest.getContract(),
                propertyRequest.getStreet(),
                propertyRequest.getNumber(),
                propertyRequest.getMoreInfo(),
                property
        ));

        List<Integer> tags=parseTagIds(propertyRequest.getTagIds());
        for(Integer temp:tags){
            Tags tag=tagsRepository.findById(temp).orElseThrow();
            propertyTagsRepository.save(new Property_tags(property,tag));
        }
    }
    @PostMapping("/updateProp/{id}")
    @Transactional
    void update(
            @RequestBody PropertyRequest propertyRequest,
            @PathVariable int id
    ){
        Properties property=propertiesRepository.findById(id).orElseThrow();
        Types type=typesRepository.findById(propertyRequest.getTypeId()).orElseThrow();
        Structures struct = structuresRepository.findById(propertyRequest.getStructureId()).orElseThrow();
        Boroughs borough = boroughsRepository.findById(propertyRequest.getBoroughId()).orElseThrow();
        Equipments equip = equipmentsRepository.findById(propertyRequest.getEquipmentId()).orElseThrow();

        property.setType(type);
        property.setStructure(struct);
        property.setRooms(propertyRequest.getRooms());
        property.setSquareFootage(propertyRequest.getSquareFootage());
        property.setBorough(borough);
        property.setFloor(propertyRequest.getFloor());
        property.setBathrooms(propertyRequest.getBathrooms());
        property.setHeating(propertyRequest.getHeating());
        property.setEquipment(equip);
        property.setActive(propertyRequest.getActive());
        property.setDeposit(propertyRequest.getDeposit());
        property.setPrice(propertyRequest.getPrice());
        property.setTitle(propertyRequest.getTitle());
        property.setDescription(propertyRequest.getDescription());

        propertiesRepository.save(property);

        Owners owner = ownersRepository.findById(property.getIdProperty()).orElseThrow();
        owner.setName(propertyRequest.getName());
        owner.setEmail(propertyRequest.getEmail());
        owner.setPhone(propertyRequest.getPhone());
        owner.setContract(propertyRequest.getContract());
        owner.setStreet(propertyRequest.getStreet());
        owner.setNumber(propertyRequest.getNumber());

        ownersRepository.save(owner);

        propertyTagsRepository.deleteByProperty(property);

        List<Integer> tags=parseTagIds(propertyRequest.getTagIds());
        for(Integer temp:tags){
            Tags tag=tagsRepository.findById(temp).orElseThrow();
            propertyTagsRepository.save(new Property_tags(property,tag));
        }
    }
    @GetMapping("/getBoroughs")
    public List<Boroughs> getBoroughs(){
        return boroughsRepository.findAll();
    }
    @GetMapping("/getStructures")
    public List<Structures> getStructures(){
        return structuresRepository.findAll();
    }
    @GetMapping("/getTypes")
    public List<Types> getTypes(){
        return typesRepository.findAll();
    }
    @GetMapping("/getEquipments")
    public List<Equipments> getEquipments(){
        return equipmentsRepository.findAll();
    }
    @GetMapping("/getTags")
    public List<Tags> getTags(){
        return tagsRepository.findAll();
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
            @RequestParam(required = false) Float floor,
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

    @GetMapping("/listOfTags/{id}")
    public List<Integer> getListOfTags(
                @PathVariable int id
        ){
        Properties property = propertiesRepository.findById(id).orElseThrow();
        return propertyTagsRepository.findByProperty(property);
    }
    @PostMapping("/upload/{id}")
    @Transactional
    public String uploadImageToFileSystem(
        @PathVariable int id,
        MultipartFile[] images) throws IOException {
        if(images==null || images.length == 0)return "No files uploaded!";

        Properties property=propertiesRepository.findById(id).orElseThrow();

        List<Pictures> existingPictures = picturesRepository.findByProperty(property);
        if( existingPictures!=null ){
            for(Pictures picture: existingPictures){
                Files.delete(Path.of(picture.getPicturePath()));
                picturesRepository.delete(picture);
            }
        }

        int cnt=0;
        for(MultipartFile image: images){
            String picName=property.getIdProperty()+"_picture_"+cnt+".jpeg";
                    cnt++;
            String picturePath=FOLDER_PATH+picName;
            System.out.println(picturePath);
            Pictures picture=picturesRepository.save(
                    new Pictures(picName,picturePath,property));
            image.transferTo(new File(picturePath));
        }
        return "Files uploaded succesfuly";
    }
    @GetMapping("/getImages/{id}")
    public List<String> downloadImages(
            @PathVariable int id
    ){
        Properties property=propertiesRepository.findById(id).orElseThrow();
        List<Pictures> existingPictures = picturesRepository.findByProperty(property);
        List<String> imageNames = new ArrayList<>();
        if( existingPictures!=null ){
            for(Pictures picture: existingPictures) {
                imageNames.add(picture.getPicturePath());
            }
        }
        return imageNames;
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