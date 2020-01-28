package kr.co.fastcampus.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Builder
@NoArgsConstructor // 생성자 자동으로 생성
@AllArgsConstructor //  모든 필드 값을 파라미터로 받는 생성자를 만들어줍니다
public class Restaurant {
    @Id
    @GeneratedValue
    @Setter
    private  Long id;

    @NotEmpty
    private  String name;
    @NotEmpty // 비어있으면 안된다, 비어있으면 @Vaild에서 비어있는지 확인하고 badreqeust를 날린다
    private  String address;

    // 임시로 통과시킨다는 뜻, 이부분은 DB에저장되지 않는다
    @Transient
    private List<MenuItem> menuItems;

    public String getInformation() {
        return name+" in "+address;
    }

    // 해당 레스토랑의 메뉴를 추가한다
    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void setMenuItem(List<MenuItem> menuItems) {
        this.menuItems = new ArrayList<>(menuItems);
    }

    public void setInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
