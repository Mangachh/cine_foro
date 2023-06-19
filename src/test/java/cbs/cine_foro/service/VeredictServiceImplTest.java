package cbs.cine_foro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import cbs.cine_foro.entity.Movie;
import cbs.cine_foro.entity.User;
import cbs.cine_foro.entity.Veredict;
import cbs.cine_foro.entity.VeredictUser;
import cbs.cine_foro.error.VeredictNotExistsException;
import cbs.cine_foro.repository.VeredictRepo;
import jakarta.annotation.PostConstruct;

@SpringBootTest
public class VeredictServiceImplTest {

    @Autowired
    private IVeredictService service;

    @MockBean
    private VeredictRepo repo;


    // create 2-3 veredicts
    List<Veredict> veredicts;
    List<User> users;
    Movie movie;

    @PostConstruct
    void init() {
        // Users
        users = List.of(
            new User(1L, "Pepote"),
            new User(2L, "Mariela"),
            new User(3L, "Corazón")
        );

       movie = Movie.builder()
                    .movieId(1L)
                    .userProposed(users.get(0))
                    .originalTitle("Pepote in pepoteland")
                    .build();

        veredicts = List.of(
            Veredict.builder()
                .movieId(movie)
                .veredictId(1L)
                .veredicts(
                    new VeredictUser(users.get(0), 7.0f, "Mejor_A", "Peor_A", "Viuda_A")
                ).build(),
            Veredict.builder()
                .movieId(movie)
                .veredictId(1L)
                .veredicts(
                    new VeredictUser(users.get(1), 2.0f, "Mejor_B", "Peor_b", "Viuda_b")
                ).build(),
            Veredict.builder()
                .movieId(movie)
                .veredictId(1L)
                .veredicts(
                    new VeredictUser(users.get(2), 5.2f, "Mejor_C", "Peor_C", "Viuda_C")
                ).build()            
        );
    }
    

    @Test
    void testGetVeredictById() throws VeredictNotExistsException {
        final Long id = 1L;
        final Optional<Veredict> expected = this.veredicts.stream().filter(p -> p.getVeredictId() == id).findFirst();
        Mockito.when(repo.findById(id))
                .thenReturn(expected);

        Veredict result = service.getVeredictById(id);

        assertEquals(expected.get(), result);
    }

    @Test
    void testGetVeredictByIdNotFound() throws VeredictNotExistsException {
        final Long id = 100L;
        Mockito.when(repo.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(VeredictNotExistsException.class, () -> service.getVeredictById(id));
    }

    @Test
    void testGetVeredictsByMovie() throws VeredictNotExistsException {
        // one movie only, get all
        Mockito.when(repo.findAllByMovie(movie))
                .thenReturn(this.veredicts);

        List<Veredict> vers = this.service.getVeredictsByMovie(movie);
        assertEquals(this.veredicts, vers);
    }

    @Test
    void testGetVeredictsByMovieNotFound() throws VeredictNotExistsException {
        // one movie only, get all
        Mockito.when(repo.findAllByMovie(movie))
                .thenReturn(List.of());

        assertThrows(VeredictNotExistsException.class, () -> service.getVeredictsByMovie(movie));
    }

    

    @Test
    void testGetVeredictsByMovieId() throws VeredictNotExistsException {
        final Long id = 1L;
        // one movie only, get all
        Mockito.when(repo.findAllByMovieId(id))
                .thenReturn(this.veredicts);

        List<Veredict> vers = this.service.getVeredictsByMovieId(id);
        assertEquals(this.veredicts, vers);
    }

    @Test
    void testGetVeredictsByMovieIdNotFound() throws VeredictNotExistsException {
        final Long id = 1L;
        // one movie only, get all
        Mockito.when(repo.findAllByMovieId(id))
                .thenReturn(List.of());

        assertThrows(VeredictNotExistsException.class, () -> service.getVeredictsByMovieId(id));
    }

    
    @Test
    void testGetVeredictsByUser() throws VeredictNotExistsException {
        final User user = users.get(1);
        List<Veredict> vers = this.veredicts.stream()
                .filter(p -> p.getVeredicts().getUserId().getUserId() == user.getUserId()).toList();

        Mockito.when(repo.findAllByUser(user))
                .thenReturn(vers);

        assertEquals(vers, this.service.getVeredictsByUser(user));
    }
    
    @Test
    void testGetVeredictsByUserNotExist() {
        User u = new User();
        Mockito.when(repo.findAllByUser(u))
                .thenReturn(List.of());

        assertThrows(VeredictNotExistsException.class, () -> service.getVeredictsByUser(u));
    }   

    @Test
    void testGetVeredictsByUserId() throws VeredictNotExistsException {
        final Long id = 2L;
        List<Veredict> vers = this.veredicts.stream().filter(p -> p.getVeredicts().getUserId().getUserId() == id)
                .toList();

        Mockito.when(repo.findAllByUserId(id))
                .thenReturn(vers);

        assertEquals(vers, this.service.getVeredictsByUserId(id));
    }
    
    @Test
    void testGetVeredictsByUserIdNotExist() {
        final Long id = 55L;

        Mockito.when(this.repo.findAllByUserId(id))
                .thenReturn(List.of());

        assertThrows(VeredictNotExistsException.class, () -> this.service.getVeredictsByUserId(id));
    }

    @Test
    void testGetVeredictsByUserName() throws VeredictNotExistsException {
        final String userName = "Pepote";

        List<Veredict> vers = this.veredicts.stream()
                .filter(p -> p.getVeredicts().getUserId().getName().equals(userName)).toList();

        Mockito.when(this.repo.findAllByUserName(userName))
                .thenReturn(vers);

        assertEquals(vers, this.service.getVeredictsByUserName(userName));
    }
    
    @Test
    void testGetVeredictsByUserNameNotExists() {
        final String userName = "lalalalal";
        Mockito.when(this.repo.findAllByUserName(userName))
                .thenReturn(List.of());

        assertThrows(VeredictNotExistsException.class, () -> this.service.getVeredictsByUserName(userName));
    }

    @Test
    void testSaveVeredict() {
        Veredict a = new Veredict(1L, new Movie(), new VeredictUser());
        Mockito.when(this.repo.save(a))
                .thenReturn(a);

        assertEquals(a, this.service.saveVeredict(a));

    }
}
