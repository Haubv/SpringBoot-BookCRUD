package demo_springjwt.demo.service;

import demo_springjwt.demo.dto.FavoriteBookDto;
import demo_springjwt.demo.response.Response;

import java.util.List;


public interface FavoriteBookService {

    Response findById(long id);

    Response deleteById(long id);

    List<FavoriteBookDto> findAll();

    Response addToFavorite(long userId, long bookId);

    FavoriteBookDto saveFavoriteBook(FavoriteBookDto favoriteBookDto);

}
