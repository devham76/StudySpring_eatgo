package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class RestaurantTests {
    // 객체를 만들 수 있는지 테스트한다
    @Test
    public void creation(){
        //Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");
        // builder패턴으로 변경 @Builder
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        // 어떤것은 어떤것이여야한다
        assertThat(restaurant.getId()).isEqualTo(1004L);
        assertThat(restaurant.getName()).isEqualTo("Bob zip");
        assertThat(restaurant.getAddress()).isEqualTo("Seoul");
    }
    @Test
    public void information() {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        assertThat(restaurant.getInformation()).isEqualTo("Bob zip in Seoul");
    }

}