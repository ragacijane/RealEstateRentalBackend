package com.project.RealEstateRental.services;

import com.project.RealEstateRental.models.*;
import com.project.RealEstateRental.repositories.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class DataInitializationService {

    @Autowired
    private BulkSaveService bulkSaveService;

    @Autowired
    private BoroughsRepository boroughsRepository;
    private static final List<String> BOROUGHS= Arrays.asList(
            "Barajevo", "Čukarica", "Grocka", "Lazarevac", "Mladenovac", "Novi Beograd",
            "Obrenovac", "Palilula", "Rakovica", "Savski venac", "Sopot", "Stari grad",
            "Surčin", "Voždovac", "Vračar", "Zemun", "Zvezdara", "Drugo...");
    @Autowired
    private EquipmentsRepository equipmentsRepository;
    private static final List<String> EQUIPMENTS=Arrays.asList(
            "Nenamešten","Polunamešten","Namešten"
    );
    @Autowired
    private StructuresRepository structuresRepository;
    private static final List<String> STRUCTURES=Arrays.asList(
            "Garsonjera","Jednosoban","Jednoiposoban","Dvosoban",
            "Dvoiposoban","Trosoban","Troiposoban","Četvorosoban",
            "Četvoroiposoban","Petosoban i veći","Open Space"
    );
    @Autowired
    private TagsRepository tagsRepository;
    private static final List<String> TAGS=Arrays.asList(
            "Garaža","Garažno mesto","Parking","Lift","Terasa",
            "Obezbeđenje","Recepcija","Podrum","Trezor","Optička mreža",
            "Internet","Kablovska","Video nadzor","Alarm","Interfon",
            "Dozvoljeni kućni ljubimci","Novogradnja","Odmah useljiv",
            "Dvorište","Pristup za invalide","Poslovna zgrada","Lokal na ulici",
            "Kuhinjski elementi","Pravno lice", "Fizičko lice","Stan u kući",
            "Open Space","Klima"
    );
    @Autowired
    private TypesRepository typesRepository;
    private static final List<String> TYPES=Arrays.asList(
            "Stan","Poslovni prostor","Poslovna zgrada",
            "Lokal","Kuća","Garaža",
            "Magacin","Ugostiteljski objekat","Zemljište"
    );
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${admin.username}")
    private String username;
    @Value("${admin.password}")
    private String password;

    @Autowired
    private PropertiesRepository propertiesRepository;
    @PostConstruct
    @Transactional
    public void init() {
        long maxId=propertiesRepository.findMaxIdProperty();
        if(maxId > 0) {
            Properties.setNextId(maxId + 1);
        }else{
            Properties.setNextId(100);
        }
        bulkSaveService.saveAll(boroughsRepository, BOROUGHS, Boroughs::new, Boroughs::getBoroughName);
        bulkSaveService.saveAll(equipmentsRepository, EQUIPMENTS, Equipments::new, Equipments::getEquipmentType);
        bulkSaveService.saveAll(structuresRepository, STRUCTURES, Structures::new, Structures::getStructureType);
        bulkSaveService.saveAll(tagsRepository, TAGS, Tags::new, Tags::getTagName);
        bulkSaveService.saveAll(typesRepository, TYPES, Types::new, Types::getTypeName);
        if (!userRepository.existsByUsername(username)) { // Check if the user exists
            User user = new User(username, passwordEncoder.encode(password));
            userRepository.save(user);
        }
    }
}
