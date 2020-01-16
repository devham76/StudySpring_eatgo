package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuItemRepository menuItemRepository;

    @Before // 모든 테스트가 실행되기 전에 이것을 반드시 실행하게 한다
    public void setUp(){
        MockitoAnnotations.initMocks(this); //Mock이 붙어있는 곳에 객체 생성
        mockRestaurantRepository();
        mockMenuItemRepository();
        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository);
    }

    // 가짜로 객체를 만든다
    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");
        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(restaurant);
    }
    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Kimchi"));

        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId()).isEqualTo(1004L);
    }

    @Test
    public void getRestaurant(){
        Restaurant restaurant = restaurantService.getRestaurant(1004L);
        assertThat(restaurant.getId()).isEqualTo(1000L);

        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertThat(menuItem.getName()).isEqualTo("Kimchi");
    }

}