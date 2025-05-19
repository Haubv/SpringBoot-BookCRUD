package demo_springjwt.demo.service.impl;

import demo_springjwt.demo.dto.FavoriteBookDto;
import demo_springjwt.demo.entity.Book;
import demo_springjwt.demo.entity.FavoriteBook;
import demo_springjwt.demo.entity.FileBook;
import demo_springjwt.demo.entity.TypeOfBook;
import demo_springjwt.demo.repository.BookRepository;
import demo_springjwt.demo.repository.FavoriteBookRepository;
import demo_springjwt.demo.repository.FileBookRepository;
import demo_springjwt.demo.repository.TypeOfBookRepository;
import demo_springjwt.demo.response.Response;
import demo_springjwt.demo.service.FavoriteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FavoriteBookServiceImpl implements FavoriteBookService {

    @Autowired
    private FavoriteBookRepository favoriteBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TypeOfBookRepository typeOfBookRepository;

    @Autowired
    private FileBookRepository fileBookRepository;

    @Override
    public Response findById(long id) {
        Optional<FavoriteBook> favoriteBook = favoriteBookRepository.findById(id);
        if (!favoriteBook.isPresent()) {
            return Response.build().message("Không tìm thấy sách có id như trên");
        }
        return Response.build().ok().data(FavoriteBookDto.toDTO(favoriteBook.get()));
    }

    @Override
    public Response deleteById(long id) {
        FavoriteBook favoriteBook = favoriteBookRepository.findById(id).orElse(null);
        if (favoriteBook == null) {
            return Response.build().message("Không tìm thấy book");
        } else {
            favoriteBook.setDeleted(true);
            favoriteBookRepository.save(favoriteBook);
        }
        return Response.build().message("Deleted");
    }

    @Override
    public List<FavoriteBookDto> findAll() {
        List<FavoriteBook> favoriteBooks = favoriteBookRepository.findAll();
        return favoriteBooks.stream().map(FavoriteBookDto::toDTO).collect(Collectors.toList());
    }

    @Override
    public Response addToFavorite(long userId, long bookId) {
        if (favoriteBookRepository.existsByIdAndUserId(bookId, userId)) {
            return null;
        }
        FavoriteBook favoriteBook = new FavoriteBook();
        Optional<Book> book = bookRepository.findById(bookId);
        favoriteBook.setUserId(userId);
        favoriteBook.setBookId(book.get().getId());
        favoriteBook.setName(book.get().getName());
        favoriteBook.setAuthor(book.get().getAuthor());
        favoriteBook.setPublishedDate(book.get().getPublishedDate());
        favoriteBook.setTypeBook(book.get().getTypeBook());
        favoriteBook.setFileBook(book.get().getFileBook());
        favoriteBook.setFileName(book.get().getFileName());
        favoriteBook.setAdded(true);
        Response.build().data(favoriteBookRepository.save(favoriteBook));
        return Response.build().message("Added To Favorite.");
    }

    @Override
    public FavoriteBookDto saveFavoriteBook(FavoriteBookDto favoriteBookDto) {
        FavoriteBook favoriteBook = new FavoriteBook();
        TypeOfBook typeBook = typeOfBookRepository.findById(favoriteBookDto.getTypeBookId()).orElse(null);
        FileBook fileBook = fileBookRepository.findById(favoriteBookDto.getFileBookId()).orElse(null);
        favoriteBook = favoriteBookRepository.save(FavoriteBookDto.toEntity(favoriteBookDto, typeBook, fileBook));
        return FavoriteBookDto.toDTO(favoriteBook);
    }

}
