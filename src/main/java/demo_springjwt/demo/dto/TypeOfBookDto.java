package demo_springjwt.demo.dto;

import demo_springjwt.demo.entity.TypeOfBook;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeOfBookDto {

    private Long id;
    private String name;

    public static TypeOfBookDto toDto(TypeOfBook typeOfBook) {
        TypeOfBookDto typeOfBookDto = new TypeOfBookDto();
        typeOfBookDto.setId(typeOfBook.getId());
        typeOfBookDto.setName(typeOfBook.getTypeName());
        return typeOfBookDto;
    }
}
