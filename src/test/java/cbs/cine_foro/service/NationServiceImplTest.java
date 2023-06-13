package cbs.cine_foro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;


import cbs.cine_foro.entity.Nationality;
import cbs.cine_foro.error.NationalityNotExistsException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class NationServiceImplTest {

    @Autowired
    private NationServiceImpl service;



    @ParameterizedTest
    @MethodSource("saveNationalitiesArgs")
    @Order(1)
    void testSaveNationality(Nationality n) {
        Nationality result = service.saveNationality(n);
        assertEquals(n, result);
    }

    static Stream<Nationality> saveNationalitiesArgs(){
        return Stream.of(
            new Nationality("Japanese"),
            new Nationality("Korean"),
            new Nationality("English"),
            new Nationality("Spanish")
        );
    }

    @Test
    @Order(2)
    void testGetAllNationalities() {
        List<Nationality> nationalities = service.getAllNationalities();
        assertTrue(nationalities.size() == saveNationalitiesArgs().count());        
    }

    @Test
    @Order(3)
    void testGetNationalityByName() throws NationalityNotExistsException {
        final String name = "Japanese";
        Nationality expected = service.getNationalityByName(name);
        assertNotNull(expected);
        assertEquals(name, expected.getNationName());
    }

    @Test
    @Order(4)
    void testGetNationalityByNameNotExists() throws NationalityNotExistsException {
        final String name = "PepoteArmor";
        assertThrows(NationalityNotExistsException.class, 
                () -> service.getNationalityByName(name));
    }

    @Test
    @Order(5)
    void testRemoveNationality() {
        Nationality toRemove = service.getAllNationalities().get(0);
        assertNotNull(toRemove);
        service.removeNationality(toRemove);
        // no exist, throws exception
        assertEquals(service.getAllNationalities().size(), saveNationalitiesArgs().count() - 1);
        assertThrows(NationalityNotExistsException.class, 
                        () -> service.getNationalityByName(toRemove.getNationName()));
    }

    @Test
    @Order(6)
    void testRemoveNationalityByName() {
        Nationality toRemove = service.getAllNationalities().get(0);
        assertNotNull(toRemove);
        service.removeNationalityByName(toRemove.getNationName());
        // no exist, throws exception
        assertEquals(service.getAllNationalities().size(), saveNationalitiesArgs().count() - 2);
        assertThrows(NationalityNotExistsException.class,
                () -> service.getNationalityByName(toRemove.getNationName()));
    }
    
    @Test
    @Order(7)
    void testRemoveNationalityByNameNotExist() {
        assertThrows(NationalityNotExistsException.class,
                () -> service.getNationalityByName("PepoteNation"));
    }

    
}
