package demo_springjwt.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "type_of_book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypeOfBook extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private String typeName;

    //Map với bảng book bằng OneToMany.
    @OneToMany(mappedBy = "typeBook")
    private List<Book> books;

}
