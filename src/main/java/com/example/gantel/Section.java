package com.example.gantel;

import jakarta.persistence.*;

@Entity
@Table(name = "section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String coach;

    private String name;

    private String price;

    private String photo;

    private String days;

    private String sport_inventory;

    public Section(String name, String coach, String days, String photo, String price, String sport_inventory) {
        this.name = name;
        this.coach = coach;
        this.days = days;
        this.photo = photo;
        this.price = price;
        this.sport_inventory = sport_inventory;
    }

    public Section() {
        this.name = "Default";
        this.coach = "Default";
        this.days = "Monday,Friday";
        this.photo = "https://example.com/image.png";
        this.price = "100$";
        this.sport_inventory = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSportInventory() {
        return sport_inventory;
    }

    public void setSportInventory(String sport_inventory) {
        this.sport_inventory = sport_inventory;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }
}
