package demo_springjwt.demo.service.impl;

import demo_springjwt.demo.dto.UserDto;
import demo_springjwt.demo.entity.Book;
import demo_springjwt.demo.entity.UserBook;
import demo_springjwt.demo.repository.UserBookRepository;
import demo_springjwt.demo.response.Response;
import demo_springjwt.demo.service.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserBookServiceImpl implements UserBookService {

    @Autowired
    private UserBookRepository userBookRepository;

    @Override
    public Response markAsRead(UserDto user, Book book) {
        if (this.userBookRepository.existsByUserIdAndBookId(user.getId(), book.getId())) {
            return null;
        }
        UserBook ub = new UserBook();
        ub.setCreatedAt(new Date());
        ub.setUserId(user.getId());
        ub.setBookId(book.getId());
        ub.setRead(true);
        return Response.build().ok().data(userBookRepository.save(ub));
    }

}
