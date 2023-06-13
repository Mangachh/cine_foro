package cbs.cine_foro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cbs.cine_foro.entity.Nationality;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class NationalityRepoTest {

    @Autowired
    private NationalityRepo repo;

    @ParameterizedTest
    @MethodSource("saveNationalitiesArgs")
    @Order(1)
    void testSaveNationality(final Nationality nationality) {
        Nationality result = repo.save(nationality);
        assertEquals(nationality, result);
    }

    static Stream<Nationality> saveNationalitiesArgs() {
        return Stream.of(
                new Nationality("Japanese"),
                new Nationality("Korean"),
                new Nationality("Spanish"),
                new Nationality("Chinese"));
    }

    @Test
    @Order(2)
    void testFindAllNationalities() {
        List<Nationality> nations = repo.findAll();
        assertNotNull(nations);
        assertEquals(nations.size(), saveNationalitiesArgs().count());
    }

    @Test
    @Order(3)
    void testFindByNationName() {
        Nationality expected = saveNationalitiesArgs().findFirst().get();
        Nationality result = repo.findByNationName(expected.getNationName());
        assertEquals(expected.getNationName(), result.getNationName());
    }

    @Test
    @Order(4)
    void testDeleteByNationName() {
        Nationality toRemove = saveNationalitiesArgs().findFirst().get();
        repo.deleteByNationName(toRemove.getNationName());
        Nationality result = repo.findByNationName(toRemove.getNationName());
        assertNull(result);
    }

}
