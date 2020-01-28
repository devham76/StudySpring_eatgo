package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/*
*  UI layer는 로직과 상관없이 중간다리 역할만 한다 !
*  실제로 데이터를 활용하는 것들은 domain모델에서 처리한다 (RestaurantRepository)
* */

@CrossOrigin // crossorigin문제해결
// 우리가 RestaurantController의 객체를 만들지 않았지만 이 어플리케이션이 작동했다,
// 왜냐? 스프링이 @RestController이 붙어있는 RestaurantController를 직접 관리했기 때문이다
// 내가 사용할 객체를 내가 직접만들지 않는다(의존성 역전 = IoC)
@RestController
public class RestaurantController {

    // ★ IoC사용 전 ★
    // Contorller에서 Restaurant Repository를 직접 만들어준다
    //private RestaurantRepository repository = new RestaurantRepository();

    // ★ IoC사용 후 ★
    @Autowired // 해당 Controller를 만들어줄때 spring이 알아서 RestaurantRepository의 객체를 만들어준다
    private RestaurantService restaurantService;    // 레스토랑정보 + 메뉴

    // 레스토랑의 목록을 돌려준다
    @GetMapping("/restaurants")
    public List<Restaurant> list() {

        List<Restaurant> restaurants = restaurantService.getRestaurants();

        return restaurants;
    }

    // 가게 상세 목록 리턴
    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id){
        // PathVariable: 주소에있는 값을 파라메터 값으로 활용한다
        // @PathVariable("id") Long id {id}값은 파라메터 Long타입의 id라는 변수로 들어온다


        /* 레스토랑 정보를 리스트로 관리를 하는데, 목록/상세 둘다 리스트를 사용하고 있기 때문에 "중복코드"가 발생한다
        이부분을 Controller와 상관없는 domain레이어인 repository를 만들어서 처리하자 !
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add( new Restaurant(1004L, "Bob zip", "Seoul") );
        restaurants.add( new Restaurant(2020L, "Cyber Food", "Seoul") );
        restaurants.add( new Restaurant(3030L, "Bob zip", "Seoul") );
        */

        Restaurant restaurant = restaurantService.getRestaurant(id);
        // 레스토랑 기본정보 + 메뉴정보

        return restaurant;
    }

    // 가게등록
    // @RequestBody를 통해서 외부에서 호출할때 넘겨주는 파라메터를 받는다
    // @Vaild : requestBody가 정확하게 존재하는지 확인하고 아니면 실행x 400, bad request를 반환
    @PostMapping("/restaurants")
    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource)
            throws URISyntaxException {
        Restaurant restaurant = Restaurant.builder()
                        .name(resource.getName())
                        .address(resource.getAddress())
                        .build();
        restaurantService.addRestaurant(restaurant);
        URI location = new URI("/restaurants/"+restaurant.getId());

        return ResponseEntity.created(location).body("{}");
    }
    // 수정
    @PatchMapping("/restaurants/{id}")
    public String update(@PathVariable("id") Long id,
                            @Valid @RequestBody Restaurant resource){
        restaurantService.updateRestaurant(id, resource.getName(),resource.getAddress());
        return "{}";
    }
}
