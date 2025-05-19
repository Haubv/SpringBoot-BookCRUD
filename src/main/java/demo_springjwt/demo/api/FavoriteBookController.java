package demo_springjwt.demo.api;

import demo_springjwt.demo.dto.FavoriteBookDto;
import demo_springjwt.demo.dto.UserDto;
import demo_springjwt.demo.entity.Book;
import demo_springjwt.demo.entity.FavoriteBook;
import demo_springjwt.demo.entity.User;
import demo_springjwt.demo.repository.BookRepository;
import demo_springjwt.demo.repository.FavoriteBookRepository;
import demo_springjwt.demo.repository.UserRepository;
import demo_springjwt.demo.response.Response;
import demo_springjwt.demo.service.FavoriteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/fav")
public class FavoriteBookController extends BaseController {

    @Autowired
    private FavoriteBookRepository favoriteBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private FavoriteBookService favoriteBookService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<FavoriteBookDto> findAll() {
        Optional<User> user = userRepository.findById(this.getCurrentUser().get().getId());
        user.get().getFavoriteBooks();
        List<FavoriteBook> favoriteBooks = favoriteBookRepository.findAll();
        return favoriteBooks.stream().map(FavoriteBookDto::toDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public Response deleteFromFavorite(@PathVariable long id) {
        return favoriteBookService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Response getBookById(@PathVariable long id) {
        return favoriteBookService.findById(id);
    }

    @PostMapping("/{id}")
    public Response addToFavorite(@PathVariable long id) {
        try {
            Optional<Book> book = this.bookRepository.findById(id);
            Optional<UserDto> user = this.getCurrentUser();
            return favoriteBookService.addToFavorite(user.get().getId(), book.get().getId());
        } catch (Exception e) {
            return Response.build().error().data(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public Response saveToFavorite(@PathVariable long id, @RequestBody FavoriteBookDto favoriteBookDto) {
        Optional<FavoriteBook> fileBook = favoriteBookRepository.findById(id);
        if (fileBook.isPresent()) {
            return Response.build().ok().data(favoriteBookService.saveFavoriteBook(favoriteBookDto));
        }
        return null;
    }

}
