package demo_springjwt.demo.api;

import demo_springjwt.demo.dto.BookDto;
import demo_springjwt.demo.entity.Book;
import demo_springjwt.demo.entity.FileBook;
import demo_springjwt.demo.repository.BookRepository;
import demo_springjwt.demo.repository.FileBookRepository;
import demo_springjwt.demo.response.Response;
import demo_springjwt.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private FileBookRepository fileBookRepository;


    @Value("${upload.path}")
    private String fileUpload;

    @PostMapping
    public Response createBook(@RequestBody BookDto bookDto) {
        return Response.build().ok().data(bookService.createBook(bookDto));
    }

    @PostMapping("/{id}")
    public Response updateBook(@PathVariable long id, @RequestBody BookDto bookDto) {
        return Response.build().ok().data(bookService.updateBook(id, bookDto));
    }

    @PutMapping("/{id}")
    public Response saveBook(@PathVariable long id, @RequestBody BookDto bookDto) {
        Optional<FileBook> fileBook = fileBookRepository.findById(id);
        if (fileBook.isPresent()) {
            return Response.build().ok().data(bookService.saveBook(bookDto));
        }
        return null;
    }

    @GetMapping
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookDto::toDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public Response deleteById(@PathVariable long id) {
        return bookService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Response getBookById(@PathVariable long id) {
        Response result = bookService.findById(id);
        return result;
    }

//	   @GetMapping
//	    public ModelAndView home(){
//	        ModelAndView modelAndView = new ModelAndView("/book/list");
//	        List<BookDto> bookDtos = (List<BookDto>) bookService.findAll();
//	        modelAndView.addObject("books", bookDtos);
//	        modelAndView.addObject("message", "Thanh cong");
//	        return modelAndView;
//	    }
//	    @GetMapping("/create")
//	    public ModelAndView showFormCreate(){
//	        ModelAndView modelAndView = new ModelAndView("/book/create");
//	        modelAndView.addObject("book", new BookDto());
//	        return modelAndView;
//	    }

//	@PostMapping("/create")
//    public Response createFile(@RequestParam("file") MultipartFile file, @RequestBody BookDto dto){
//        String fileName = file.getOriginalFilename();
//        try {
//            FileCopyUtils.copy(file.getBytes(), new File(this.fileUpload + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Book book = new Book();
//        book.setName(dto.getName());
//        book.setFileName(fileName);
//        Optional<TypeOfBook> typeBook = typeOfBookRepository.findById(dto.getTypeBookId());
//        if(typeBook.isPresent()) {
//        	book.setTypeBook(typeBook.get());
//        }
//        bookRepository.save(book);
//        return Response.build().ok().message("OK!");
//    }
//    

//	    @PostMapping("/create")
//	    public Response createFile(@RequestParam("file") MultipartFile file,
//	    		@RequestParam("name") String name,
//	    		@RequestParam("typeBookId") long typeBookId){
//	        String fileName = file.getOriginalFilename();
//	        try {
//	            FileCopyUtils.copy(file.getBytes(), new File(this.fileUpload + fileName));
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	        Book book = new Book();
//	        book.setName(name);
//	        book.setFileName(fileName);
//	        Optional<TypeOfBook> typeBook = typeOfBookRepository.findById(typeBookId);
//	        if(typeBook.isPresent()) {
//	        	book.setTypeBook(typeBook.get());
//	        }
//	        bookRepository.save(book);
//	        return Response.build().ok().message("OK!");
//	    }

    @PostMapping("/test")
    public Response test(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
                         @RequestParam("typeBookId") long typeBookId) {
        String fileName = file.getOriginalFilename();
        try {
            System.out.println(name);
            System.out.println(typeBookId);
            FileCopyUtils.copy(file.getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.build().ok().message("OK");
    }
}
