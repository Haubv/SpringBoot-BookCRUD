package demo_springjwt.demo.service;

import demo_springjwt.demo.dto.UserDto;
import demo_springjwt.demo.entity.Book;
import demo_springjwt.demo.response.Response;

public interface UserBookService {

    Response markAsRead(UserDto user, Book book);

}
