package cbs.cine_foro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import cbs.cine_foro.entity.Nationality;
import cbs.cine_foro.error.NationalityNotExistsException;
import cbs.cine_foro.repository.NationalityRepo;
import jakarta.annotation.PostConstruct;

@SpringBootTest
public class NationServiceImplTest {

    @Autowired
    private NationServiceImpl service;

    @MockBean
    private NationalityRepo repo;

    private List<Nationality> nations;

    @PostConstruct
    void setNations() {
        nations = List.of(
                new Nationality(1L, "Japanese"),
                new Nationality(2L, "Korean"),
                new Nationality(3L, "Spanish"));
    }

    @Test
    void testSaveNationality() {
        for (Nationality n : nations) {
            Mockito.when(repo.save(n))
                    .thenReturn(n);
            Nationality result = service.saveNationality(n);
            assertEquals(n, result);
        }
    }

    @Test
    void testGetAllNationalities() {
        Mockito.when(repo.findAll())
                .thenReturn(nations);

        List<Nationality> nationalities = service.getAllNationalities();
        assertTrue(nationalities.size() == nations.size());
    }

    @Test
    void testGetNationalityByName() throws NationalityNotExistsException {
        final String name = "Japanese";
        Mockito.when(repo.findByNationName(name))
                .thenReturn(nations.get(0));

        Nationality expected = service.getNationalityByName(name);
        assertNotNull(expected);
        assertEquals(name, expected.getNationName());
    }

    @Test
    void testGetNationalityByNameNotExists() throws NationalityNotExistsException {
        final String name = "PepoteArmor";
        Mockito.when(repo.findByNationName(name))
                .thenReturn(null);
        assertThrows(NationalityNotExistsException.class,
                () -> service.getNationalityByName(name));
    }

    // don't know
    //@Test
    void testRemoveNationality() {
        Nationality toRemove = service.getAllNationalities().get(0);
        assertNotNull(toRemove);
        service.removeNationality(toRemove);
        // no exist, throws exception
        assertEquals(service.getAllNationalities().size(), nations.size() - 1);
        assertThrows(NationalityNotExistsException.class,
                () -> service.getNationalityByName(toRemove.getNationName()));
    }

    //@Test
    void testRemoveNationalityByName() {
        Nationality toRemove = service.getAllNationalities().get(0);
        assertNotNull(toRemove);
        service.removeNationalityByName(toRemove.getNationName());
        // no exist, throws exception
        assertEquals(service.getAllNationalities().size(), nations.size() - 2);
        assertThrows(NationalityNotExistsException.class,
                () -> service.getNationalityByName(toRemove.getNationName()));
    }  

}
