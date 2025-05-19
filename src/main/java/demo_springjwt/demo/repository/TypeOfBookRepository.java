package demo_springjwt.demo.repository;

import demo_springjwt.demo.entity.TypeOfBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeOfBookRepository extends JpaRepository<TypeOfBook, Long> {

    List<TypeOfBook> findAll();

}
