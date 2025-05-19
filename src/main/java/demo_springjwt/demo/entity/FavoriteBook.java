package demo_springjwt.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteBook extends BaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "name", unique = true)
    private String name;
    private String publishedDate;
    private String author;
    private String fileName;
    private boolean isAdded;
    private long userId;
    private long bookId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_book_id")
    private TypeOfBook typeBook;

    @OneToOne
    @JoinColumn(name = "file_book_id", unique = true)
    private FileBook fileBook;

    @ManyToMany(mappedBy = "favoriteBooks")
    private List<User> users;


}
