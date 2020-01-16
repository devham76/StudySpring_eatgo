package kr.co.fastcampus.eatgo.domain;


import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private  Long id;
    private  String name;
    private  String address;
    private List<MenuItem> menuItems = new ArrayList<>();

    Restaurant(){

    }
    public Restaurant(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return name+" in "+address;
    }

    public String getAddress() {
        return address;
    }

    public Long getId() {
        return id;
    }

    // 해당 레스토랑의 메뉴를 추가한다
    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }
    public List<MenuItem> getMenuItems(){
        return menuItems;
    }

    public void setMenuItem(List<MenuItem> menuItems) {
        for(MenuItem menuItem:menuItems)
            addMenuItem(menuItem);
    }
}
