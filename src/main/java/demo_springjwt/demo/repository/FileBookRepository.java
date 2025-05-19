package demo_springjwt.demo.repository;

import demo_springjwt.demo.entity.FileBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileBookRepository extends JpaRepository<FileBook, Long> {

}
