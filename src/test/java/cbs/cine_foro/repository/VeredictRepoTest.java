package cbs.cine_foro.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Veredict;
import cbs.cine_foro.entity.VeredictUser;

@SpringBootTest
public class VeredictRepoTest {

    @Autowired
    private VeredictRepo repo;

    @Test
    void saveVeredictWithVeredictUser() {
        VeredictUser userOne = VeredictUser.builder()
                .userId(User.builder().userId(1L).build())
                .score(7f)
                .bestMoment("Cuando se caen")
                .worstMoment("El final es horrible")
                .widow("La sombra detrás a puerta")
                .build();

        VeredictUser userTwo = VeredictUser.builder()
                .userId(User.builder().userId(2L).build())
                .score(6f)
                .bestMoment("El malo muere")
                .worstMoment("La música")
                .widow("Comer burritos durante el tiroteo")
                .build();

        Veredict veredict = Veredict.builder()
                .movieId(Movie.builder().movieId(1L).build())
                .veredicts(userTwo)
                .build();

        repo.save(veredict);
        veredict = Veredict.builder()
                .movieId(Movie.builder().movieId(1L).build())
                .veredicts(userOne)
                .build();

        repo.save(veredict);

    }

    @Test
    void saveVeredictWithVeredictUserIdWrong() {
        VeredictUser userOne = VeredictUser.builder()
                .userId(User.builder().userId(69L).build())
                .score(7f)
                .bestMoment("Cuando se caen")
                .worstMoment("El final es horrible")
                .widow("La sombra detrás a puerta")
                .build();

        Veredict veredict = Veredict.builder()
                .movieId(Movie.builder().movieId(1L).build())
                .veredicts(userOne)
                .build();

        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> repo.save(veredict));
        //repo.save(veredict); // exception
    }
    
    @Test
    void saveVeredictWithVeredictRepeatedIDS() {
        VeredictUser userOne = VeredictUser.builder()
                .userId(User.builder().userId(2L).build())
                .score(7f)
                .bestMoment("Cuando se caen")
                .worstMoment("El final es horrible")
                .widow("La sombra detrás a puerta")
                .build();

        Veredict veredict = Veredict.builder()
                .movieId(Movie.builder().movieId(1L).build())
                .veredicts(userOne)
                .build();
        
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> repo.save(veredict));
    }
}
