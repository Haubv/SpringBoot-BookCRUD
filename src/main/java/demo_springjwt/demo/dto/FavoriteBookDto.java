package demo_springjwt.demo.dto;

import demo_springjwt.demo.entity.FavoriteBook;
import demo_springjwt.demo.entity.FileBook;
import demo_springjwt.demo.entity.TypeOfBook;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteBookDto {
    private Long id;
    //	private Long userId;
//	private Long bookId;
    private String name;
    private String author;
    private String publishedDate;
    private Long typeBookId;
    private String typeBook;
    private Long fileBookId;
    private String pathFile;
    private String fileName;
//	private MultipartFile fileName; 

    public static FavoriteBookDto toDTO(FavoriteBook favoriteBook) {
        FavoriteBookDto result = new FavoriteBookDto();
        result.setId(favoriteBook.getId());
//		result.setUserId(favoriteBook.getUserId());
//		result.setBookId(favoriteBook.getBookId());
        result.setName(favoriteBook.getName());
        result.setTypeBook(favoriteBook.getTypeBook() != null ? favoriteBook.getTypeBook().getTypeName() : "");
        result.setTypeBookId(favoriteBook.getTypeBook() != null ? favoriteBook.getTypeBook().getId() : null);
        result.setPublishedDate(favoriteBook.getPublishedDate());
        result.setAuthor(favoriteBook.getAuthor());
        result.setFileBookId(favoriteBook.getFileBook() != null ? favoriteBook.getFileBook().getId() : null);
        result.setPathFile(favoriteBook.getFileBook() != null ? favoriteBook.getFileBook().getPath() : "");
        return result;
    }

    public FavoriteBookDto() {

    }

    public static FavoriteBook toEntity(FavoriteBookDto favoriteBookDto, TypeOfBook typeBook, FileBook fileBook) {
        FavoriteBook result = new FavoriteBook();
        result.setId(favoriteBookDto.getId());
        result.setName(favoriteBookDto.getName());
        result.setAuthor(favoriteBookDto.getAuthor());
        result.setPublishedDate(favoriteBookDto.getPublishedDate());
        result.setTypeBook(typeBook);
        result.setFileBook(fileBook);
        return result;
    }

}
