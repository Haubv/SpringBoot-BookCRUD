package demo_springjwt.demo.repository;

import demo_springjwt.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByName(String name);

    Optional<Book> findById(Long id);

    boolean existsByName(String name);

    Optional<Book> findByFileBookId(Long id);

    boolean existsByFileBookId(Long id);
}
