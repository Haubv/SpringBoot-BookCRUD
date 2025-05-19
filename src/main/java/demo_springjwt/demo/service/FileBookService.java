package demo_springjwt.demo.service;

import demo_springjwt.demo.entity.FileBook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileBookService {
    FileBook saveFileBook(MultipartFile file);

    public File loadFileBook(long id);

    List<FileBook> findAll();
}
