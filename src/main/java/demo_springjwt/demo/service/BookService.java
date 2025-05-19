package demo_springjwt.demo.service;

import demo_springjwt.demo.dto.BookDto;
import demo_springjwt.demo.response.Response;

import java.util.List;

public interface BookService {

    Response createBook(BookDto bookDto);

    Response updateBook(long id, BookDto bookDto);

    Response findById(long id);

    Response deleteById(long id);

    List<BookDto> findAll();

    BookDto saveBook(BookDto bookDto);

}
