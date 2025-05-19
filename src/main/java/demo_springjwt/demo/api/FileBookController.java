package demo_springjwt.demo.api;

import demo_springjwt.demo.dto.UserDto;
import demo_springjwt.demo.entity.Book;
import demo_springjwt.demo.entity.FileBook;
import demo_springjwt.demo.repository.BookRepository;
import demo_springjwt.demo.repository.FileBookRepository;
import demo_springjwt.demo.service.FileBookService;
import demo_springjwt.demo.service.UserBookService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("file-book")
public class FileBookController extends BaseController {

    @Autowired
    private FileBookService fileBookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserBookService userBookService;

    @Autowired
    private FileBookRepository fileBookRepository;


    @PostMapping
    public FileBook saveFileBook(@RequestParam("file") MultipartFile file) {
        return fileBookService.saveFileBook(file);
    }

    @GetMapping
    public List<FileBook> findAll() {
        return fileBookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> serveFile(@PathVariable long id) throws IOException {
        File image = this.fileBookService.loadFileBook(id);
        if (image == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] bytes = FileUtils.readFileToByteArray(image);
        HttpHeaders headers = new HttpHeaders();

        if ("png".equals(FilenameUtils.getExtension(image.getName()))) {
            headers.setContentType(MediaType.IMAGE_PNG);
        }

        if ("gif".equals(FilenameUtils.getExtension(image.getName()))) {
            headers.setContentType(MediaType.IMAGE_GIF);
        }

        if ("jpg".equals(FilenameUtils.getExtension(image.getName())) || "jpeg".equals(FilenameUtils.getExtension(image.getName()))) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        }
        if ("pdf".equals(FilenameUtils.getExtension(image.getName())) || "pdf".equals(FilenameUtils.getExtension(image.getName()))) {
            headers.setContentType(MediaType.APPLICATION_PDF);
        }
        Optional<Book> book = this.bookRepository.findByFileBookId(id);
        Optional<UserDto> user = this.getCurrentUser();
        if (book.isPresent() && user.isPresent()) {
            userBookService.markAsRead(user.get(), book.get());
        }
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
