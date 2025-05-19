package demo_springjwt.demo.repository;

import demo_springjwt.demo.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
