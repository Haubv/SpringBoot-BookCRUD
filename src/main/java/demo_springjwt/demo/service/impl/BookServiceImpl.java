package demo_springjwt.demo.service.impl;

import demo_springjwt.demo.dto.BookDto;
import demo_springjwt.demo.entity.Book;
import demo_springjwt.demo.entity.FileBook;
import demo_springjwt.demo.entity.TypeOfBook;
import demo_springjwt.demo.repository.BookRepository;
import demo_springjwt.demo.repository.FileBookRepository;
import demo_springjwt.demo.repository.TypeOfBookRepository;
import demo_springjwt.demo.response.Response;
import demo_springjwt.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TypeOfBookRepository typeOfBookRepository;

    @Autowired
    private FileBookRepository fileBookRepository;

    @Override
    public Response createBook(BookDto bookDto) {
        TypeOfBook typeBook = new TypeOfBook();
        if (bookDto.getTypeBookId() != null) {
            Optional<TypeOfBook> type = typeOfBookRepository.findById(bookDto.getTypeBookId());
            if (type.isPresent()) {
                typeBook.setId(type.get().getId());
            }
        }
        FileBook fileBook = fileBookRepository.findById(bookDto.getFileBookId()).orElse(null);
        Book book = BookDto.toEntity(bookDto, typeBook, fileBook);
//		if(book.getName() == null || book.getTypeBook() == null) {
//			Response.build().message("Không được để trống tên sách/thể loại sách");
//		}
        bookRepository.save(book);
        return Response.build().ok().data(bookRepository.save(book));
    }

    @Override
    public Response updateBook(long id, BookDto bookDto) {
        Optional<TypeOfBook> typeBook = typeOfBookRepository.findById(bookDto.getTypeBookId());
        Optional<FileBook> fileBook = fileBookRepository.findById(bookDto.getFileBookId());
        Book book = bookRepository.findById(id).orElse(null);

        if (bookRepository.existsByFileBookId(bookDto.getFileBookId())) {
            return null;

        } else {
            if (book == null) {
                return Response.build().message("Không tìm thấy sách có id như trên");
            }

            if (bookDto.getTypeBookId() != null) {
                book.setTypeBook(typeBook.get());
            }

            if (fileBook.isPresent()) {
                book.setFileBook(fileBook.get());
            }

            if (bookDto.getName() != null) {
                book.setName(bookDto.getName());
            }

            if (bookDto.getAuthor() != null) {
                book.setAuthor(bookDto.getAuthor());
            }

            if (bookDto.getPublishedDate() != null) {
                book.setPublishedDate(bookDto.getPublishedDate());
            }

            bookRepository.save(book);
            return Response.build().ok().data(BookDto.toDTO(book));
        }

    }

    @Override
    public Response findById(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return Response.build().message("Không tìm thấy sách có id như trên");
        }
        return Response.build().ok().data(BookDto.toDTO(book.get()));
    }

    @Override
    public Response deleteById(long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return Response.build().message("Không tìm thấy book");
        } else {
            book.setDeleted(true);
            bookRepository.save(book);
        }
        return Response.build().message("Deleted");
    }

    @Override
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookDto::toDTO).collect(Collectors.toList());
    }

    @Override
    public BookDto saveBook(BookDto bookDto) {
        Book book = new Book();
        TypeOfBook typeBook = typeOfBookRepository.findById(bookDto.getTypeBookId()).orElse(null);
        FileBook fileBook = fileBookRepository.findById(bookDto.getFileBookId()).orElse(null);
        book = bookRepository.save(BookDto.toEntity(bookDto, typeBook, fileBook));
        return BookDto.toDTO(book);
    }


}
