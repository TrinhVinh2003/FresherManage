package com.example.FresherManage.repository;


import com.example.FresherManage.domain.Entity.Center;
import com.example.FresherManage.domain.Entity.Fresher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class FresherRepositoryTest {

    @Autowired
    private FresherRepository fresherRepository;

    @Autowired
    private CenterRepository centerRepository;
    @Test
    public void FresherRepository_SaveAll_ReturnSaveFresher(){

        Center center = Center.builder()
                .name("Vmo")
                .location("Cau giay")
                .build();
        centerRepository.save(center);
        //Arrange
        Fresher fresher = Fresher.builder()
                .name("vinh")
                .email("vinh534473@gmail.com")
                .programmingLanguage("Java")
                .center(center)
                .build();



        //Act
        Fresher saveFresher = fresherRepository.save(fresher);

        //Assert
        Assertions.assertNotNull(saveFresher);
        Assertions.assertTrue(saveFresher.getId() >0 );
    }


    @Test
    public void FresherRepository_GetAll_ReturnMoreThanOneFresher(){
        Center center = Center.builder()
                .name("Vmo")
                .location("Cau giay")
                .build();
        centerRepository.save(center);
        Fresher fresher1 = Fresher.builder()
                .name("vinh")
                .email("vinh534473@gmail.com")
                .programmingLanguage("Java")
                .center(center)
                .build();

        Fresher fresher2 = Fresher.builder()
                .name("Alice")
                .email("alice@example.com")
                .programmingLanguage("C++")
                .center(center)
                .build();


        fresherRepository.save(fresher2);
        fresherRepository.save(fresher1);

        List<Fresher> fresherList = fresherRepository.findAll();

        Assertions.assertNotNull(fresherList);
        Assertions.assertEquals(2,fresherList.size());


    }

    @Test
    public void FresherRepository_FindById_ReturnMoreThanOneFresher(){
        Center center = Center.builder()
                .name("Vmo")
                .location("Cau giay")
                .build();
        centerRepository.save(center);

        Fresher fresher = Fresher.builder()
                .name("Alice")
                .email("alice@example.com")
                .programmingLanguage("C++")
                .center(center)
                .build();


        fresherRepository.save(fresher);

        Fresher fresherById = fresherRepository.findById(fresher.getId()).get();

        Assertions.assertNotNull(fresherById);


    }

    @Test
    public void FresherRepository_UpdateFresher_ReturnFresherNotNull(){
        Center center = Center.builder()
                .name("Vmo")
                .location("Cau giay")
                .build();
        centerRepository.save(center);

        Fresher fresher = Fresher.builder()
                .name("Alice")
                .email("alice@example.com")
                .programmingLanguage("C++")
                .center(center)
                .build();
        fresherRepository.save(fresher);

        Fresher fresherById = fresherRepository.findById(fresher.getId()).get();
        fresherById.setName("Vinh");
        fresherById.setEmail("vinh534473@gmail.com");
        fresherById.setProgrammingLanguage("Java");

        Fresher fresherUpdate = fresherRepository.save(fresherById);

        Assertions.assertNotNull(fresherUpdate.getName());
        Assertions.assertNotNull(fresherUpdate.getEmail());
    }
}
