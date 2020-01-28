package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) // 누구한데 요청할거냐?->spring을 이용해서 처리
@WebMvcTest(RestaurantController.class) // restaurantController 클래스를이용할것임을 명시한다
public class RestaurantControllerTest {

    @Autowired  // 스프링이 알아서 객체를 만들어서 사용한다 IoC , 개발자가 객체를 만들지 않았지만 사용가능
    private MockMvc mvc;    // 가짜개체 ; 우리가 테스트 하려는것(RestaurantController)을 대체한다

    //@SpyBean(RestaurantRepositoryImpl.class)
    // 컨트롤러에 원하는 객체를 주입할수있다
    // 실질적인 구현이 없는RestaurantRepository의 객체를
    // 사용하기로했기때문에 문제가 되므로 RestaurantRepositoryImpl.class 를넣어준다

    // @SpyBean : 진짜 객체주입 , @MockBean : 가짜 객체 주입
    @MockBean
    private RestaurantService restaurantService;    // 구체적인 구현체가 아닌 인터페이스를 사용하도록한다

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                                .id(1004L)
                                .name("Bob zip")
                                .address("Seoul")
                                .build();
        restaurants.add(restaurant);
        given(restaurantService.getRestaurants()).willReturn(restaurants);     //임의의 레스토랑 목록을 돌려주면된다
        mvc.perform(get("/restaurants"))
        .andExpect(status().isOk())    // /restaurants로 접속했을때 정상적으로 나오니? "그리고 기대한다"의 뜻
        .andExpect(content().string(
                containsString("\"id\":1004")  //json 형태로 확인
        )).andExpect(content().string(
                containsString("\"name\":\"Bob zip\"")  //json 형태로 확인
        ));
    }

    // 가게 상세 테스트
    @Test
    public void detail() throws Exception {
        Restaurant restaurant = Restaurant.builder()
                                .id(1004L)
                                .name("Bob zip")
                                .address("Seoul")
                                .build();
        restaurant.setMenuItem(Arrays.asList(new MenuItem("Kimchi")));
        // given ; 정보를 가짜로 얻어서 (MockMvc) 처리하면 willreturn되는지 확인했다
        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);     //임의의 레스토랑 목록을 돌려주면된다
        Restaurant restaurant2 = Restaurant.builder()
                                .id(2020L)
                                .name("Cyber Food")
                                .address("Seoul")
                                .build();

        restaurant2.setMenuItem(Arrays.asList(new MenuItem("Kimchi")));
        given(restaurantService.getRestaurant(2020L)).willReturn(restaurant2);     //임의의 레스토랑 목록을 돌려주면된다

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")  //json 형태로 확인
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ))
                .andExpect(content().string(
                        containsString("Kimchi")
                ));
        mvc.perform(get("/restaurants/2020"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":2020")  //json 형태로 확인
                )).andExpect(content().string(
                containsString("\"name\":\"Cyber Food\"")  //json 형태로 확인
        ));
    }


    //가게등록
    @Test
    public void createWithVaildData() throws Exception {
        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1234L)
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .build();
        });

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)  //이내용이 json이라는것을 알려야한다
                .content("{\"name\":\"hyemi\", \"address\":\"Busan\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1234"))
                .andExpect(content().string("{}"));

        //Mockito.verify addresuaurant가 되는지 확인한다
        // any() : 어떤 객체를 넣었더라도 실행이 되는지 확인한다
        verify(restaurantService).addRestaurant(any());
    }
    @Test
    public void createWithInvaildData() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)  //이내용이 json이라는것을 알려야한다
                .content("{\"name\":\"\", \"address\":\"\"}"))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void updateWithVaildData() throws Exception {
        mvc.perform(patch("/restaurants/1234L")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Bobzip\", \"address\":\"Busan\"}"))
                .andExpect(status().isOk());
        // 실제로 일어나야하는일 : 수정하기
        verify(restaurantService).updateRestaurant(1234L,"Soolzip","Seoul");
    }
    @Test
    public void updateWithInvaildData() throws Exception {
        mvc.perform(patch("/restaurants/1234L")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\", \"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}