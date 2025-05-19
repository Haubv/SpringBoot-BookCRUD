package demo_springjwt.demo.service;

import demo_springjwt.demo.dto.TypeOfBookDto;

import java.util.List;

public interface TypeOfBookService {
    List<TypeOfBookDto> findAllType();
}
