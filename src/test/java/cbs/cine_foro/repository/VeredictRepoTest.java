package cbs.cine_foro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Veredict;
import cbs.cine_foro.entity.VeredictUser;
import jakarta.annotation.PostConstruct;

@SpringBootTest
//@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class VeredictRepoTest {

    @Autowired
    private VeredictRepo repo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MovieRepo movieRepo;

    private List<Veredict> veredicts;

    private List<User> users;

    private Movie movie;

    @PostConstruct
    void init() {
        this.initUsers();
        this.initMovie();
        this.initVeredicts();
    }

     

    void initUsers() {
        users = List.of(
                new User("Pepote"),
                new User("Mariela"));

        try {
            userRepo.saveAll(users);
            users = userRepo.findAll();
        } catch (Exception e) {
            users = userRepo.findAll();
        }
    }

    void initMovie() {
        movie = movieRepo.findById(1L).orElse(null);

        if (movie != null)
            return;
            
        movie = Movie.builder()
                .originalTitle("Fingers crossed")
                .spanishTitle("La suerte del pringao")
                .build();
        movie.setUserProposed(users.get(0));
        
        try{
            movieRepo.save(movie);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("*********************************");
            movie = movieRepo.findById(1L).get();
        }
    }

    void initVeredicts() {
        if (veredicts != null && veredicts.size() > 0) {
            return;
        }

        veredicts = new ArrayList<>();
        
        VeredictUser userOne = VeredictUser.builder()
                //.userId(users.get(0))
                .score(7f)
                .bestMoment("Cuando se caen")
                .worstMoment("El final es horrible")
                .widow("La sombra detrás a puerta")
                .build();
            userOne.setUser(users.get(0));

        VeredictUser userTwo = VeredictUser.builder()
                //.userId(users.get(1))
                .score(6f)
                .bestMoment("El malo muere")
                .worstMoment("La música")
                .widow("Comer burritos durante el tiroteo")
                .build();
            userTwo.setUser(users.get(1));
        Veredict veredict = Veredict.builder()
                .movie(movie)
                .userVeredict(userTwo)
                .build();

        veredicts.add(veredict);

        veredict = Veredict.builder()
                .movie(movie)
                .userVeredict(userOne)
                .build();

        veredicts.add(veredict);

    }

    @Test
    @Order(1)
    void saveVeredict() {
        for (Veredict v : veredicts) {
            Veredict result = repo.save(v);
            assertEquals(v.getVeredictId(), result.getVeredictId());
        }
    }
    

    @Test
    @Order(2)
    void saveVeredictWithVeredictUserIdWrong() {
        VeredictUser userOne = VeredictUser.builder()
                .user(User.builder().userId(69L).build())
                .score(7f)
                .bestMoment("Cuando se caen")
                .worstMoment("El final es horrible")
                .widow("La sombra detrás a puerta")
                .build();

        Veredict veredict = Veredict.builder()
                .movie(Movie.builder().movieId(1L).build())
                .userVeredict(userOne)
                .build();

        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> repo.save(veredict));
        // repo.save(veredict); // exception
    }

    @Test
    @Order(3)
    void saveVeredictWithVeredictRepeatedIDS() {
        VeredictUser userOne = VeredictUser.builder()
                //.userId(users.get(0))
                .score(7f)
                .bestMoment("Cuando se caen")
                .worstMoment("El final es horrible")
                .widow("La sombra detrás a puerta")
                .build();
        userOne.setUser(users.get(0));

        Veredict veredict = Veredict.builder()
                .movie(movie)
                .userVeredict(userOne)
                .build();
        List<Movie> movies = movieRepo.findAll();

        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> repo.save(veredict));
    }
    
    @Test
    @Order(4)
    void findAllVeredicts() {
        List<Veredict> ll = repo.findAll();
        assertEquals(2, ll.size());
        System.out.println("**********************");
        System.out.println(ll);
        System.out.println("**********************");
    }

    @Test
    @Order(5)
    void findAllByUserId() {
        List<Veredict> veredicts = repo.findAllByUserId(2L);
        assertEquals(veredicts.size(), 1);
    }

    @Test
    @Order(6)
    void findAllByMovieId() {
        List<Veredict> veredicts = repo.findAllByMovieId(1L);
        assertEquals(2, veredicts.size());
    }

    @Test
    @Order(7)
    void findAllByUserName() {
        List<Veredict> vers = repo.findAllByUserName("Pepote");
        System.out.println("**********************");
        System.out.println(vers);
        System.out.println("**********************");
        assertEquals(1, vers.size());
    }

    @Test
    @Order(8)
    // used at the end, easy to clean up
    void deleteAll(){
        repo.deleteAll();
        movieRepo.deleteAll();
        userRepo.deleteAll();
    }

    // delete veredict
    

    // update veredict

    // and don't know what else
}
