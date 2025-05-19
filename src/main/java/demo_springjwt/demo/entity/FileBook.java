package demo_springjwt.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class FileBook extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String path;
    private String description;

}
