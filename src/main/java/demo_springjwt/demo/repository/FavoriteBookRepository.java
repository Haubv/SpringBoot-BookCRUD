package demo_springjwt.demo.repository;

import demo_springjwt.demo.entity.FavoriteBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteBookRepository extends JpaRepository<FavoriteBook, Long> {

    FavoriteBook findByName(String name);

    Optional<FavoriteBook> findById(Long id);

    boolean existsByName(String name);

    Optional<FavoriteBook> findByFileBookId(Long id);

    boolean existsByIdAndUserId(Long id, Long userId);
}
