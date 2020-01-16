package kr.co.fastcampus.eatgo.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
domain layer
레스토랑 객체들을 저장하고 활용하는 클래스
*/


@Component // RestaurantRepository를 스프링이 직접 관리하도록 해당 애노테이션을 붙인다
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantRepositoryImpl() {
        restaurants.add( new Restaurant(1004L, "Bob zip", ""));
        restaurants.add( new Restaurant(2020L, "Cyber Food", "Seoul") );
    }
    @Override
    public List<Restaurant> findAll() {
        return restaurants;
    }

    @Override
    public Restaurant findById(Long id) {
        Restaurant restaurant = restaurants.stream()
                .filter( r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
        return restaurant;
    }
}
