package demo_springjwt.demo.service.impl;

import demo_springjwt.demo.dto.TypeOfBookDto;
import demo_springjwt.demo.entity.TypeOfBook;
import demo_springjwt.demo.repository.TypeOfBookRepository;
import demo_springjwt.demo.service.TypeOfBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeOfBookServiceImpl implements TypeOfBookService {

    @Autowired
    TypeOfBookRepository typeOfBookRepository;

    @Override
    public List<TypeOfBookDto> findAllType() {
        List<TypeOfBook> typeOfBook = typeOfBookRepository.findAll();
        return typeOfBook.stream().map(TypeOfBookDto::toDto).collect(Collectors.toList());
    }

}
