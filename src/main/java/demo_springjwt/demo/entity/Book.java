package demo_springjwt.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "name", unique = true)
    private String name;
    private String publishedDate;
    private String author;
    private String fileName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_book_id")
    private TypeOfBook typeBook;

    @OneToOne
    @JoinColumn(name = "file_book_id", unique = true)
    private FileBook fileBook;


    //	@ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    //	private List<User> users;
}