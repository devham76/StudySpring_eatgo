package kr.co.fastcampus.eatgo.domain;

import java.util.List;

//RestaurantRepository를 다른 형태로 쉽게 변경할수있다
public interface RestaurantRepository {
    List<Restaurant> findAll();
    Restaurant findById(Long id);
}
