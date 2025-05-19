package demo_springjwt.demo.dto;


import demo_springjwt.demo.entity.Book;
import demo_springjwt.demo.entity.FileBook;
import demo_springjwt.demo.entity.TypeOfBook;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

    private Long id;
    private String name;
    private String author;
    private String publishedDate;
    private Long typeBookId;
    private String typeBook;
    private Long fileBookId;
    private String pathFile;
    private String fileName;
//	private MultipartFile fileName; 

    public static BookDto toDTO(Book book) {
        BookDto result = new BookDto();
        result.setId(book.getId());
        result.setName(book.getName());
        result.setTypeBook(book.getTypeBook() != null ? book.getTypeBook().getTypeName() : "");
        result.setTypeBookId(book.getTypeBook() != null ? book.getTypeBook().getId() : null);
        result.setPublishedDate(book.getPublishedDate());
        result.setAuthor(book.getAuthor());
        result.setFileBookId(book.getFileBook() != null ? book.getFileBook().getId() : null);
        result.setPathFile(book.getFileBook() != null ? book.getFileBook().getPath() : "");
        return result;
    }

    public BookDto() {

    }

    public static Book toEntity(BookDto bookDto, TypeOfBook typeBook, FileBook fileBook) {
        Book result = new Book();
        result.setId(bookDto.getId());
        result.setName(bookDto.getName());
        result.setAuthor(bookDto.getAuthor());
        result.setPublishedDate(bookDto.getPublishedDate());
        result.setTypeBook(typeBook);
        result.setFileBook(fileBook);
        return result;
    }


//	@ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
//	private List<User> users;

}