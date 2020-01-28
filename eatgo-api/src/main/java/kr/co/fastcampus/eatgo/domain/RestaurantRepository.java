package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

//RestaurantRepository를 다른 형태로 쉽게 변경할수있다
public interface RestaurantRepository extends CrudRepository<Restaurant,Long> {
    List<Restaurant> findAll();
    Optional<Restaurant> findById(Long id);
    Restaurant save(Restaurant restaurant);
}
