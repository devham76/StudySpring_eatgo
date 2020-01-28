package kr.co.fastcampus.eatgo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// DB의 테이블과 매칭될 클래스
@Entity // 테이블과 링크될 클래스임을 나타낸다
@Getter
@Builder
@NoArgsConstructor  // 생성자 생성
@AllArgsConstructor  // 모든 필드값들어가는 생성자 자동생성
public class MenuItem
{
    @Id // 테이블의 PK필드
    @GeneratedValue
    private Long id;

    private Long restaurantId;
    private String name;

    public MenuItem(String name) {
        this.name = name;
    }
}
