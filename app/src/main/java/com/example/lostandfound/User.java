package com.example.lostandfound;

public class User {
   String name_lost,roll_number_lost,phone_number_lost,item_lost,place_lost;


    public User(String name_lost, String roll_number_lost, String phone_number_lost, String item_lost, String place_lost) {
        this.name_lost = name_lost;
        this.roll_number_lost = roll_number_lost;
        this.phone_number_lost = phone_number_lost;
        this.item_lost = item_lost;
        this.place_lost = place_lost;
    }

    public User(String name_lost) {
        this.name_lost = name_lost;
    }

    public String getName_lost() {
        return name_lost;
    }

    public String getRoll_number_lost() {
        return roll_number_lost;
    }

    public String getPhone_number_lost() {
        return phone_number_lost;
    }

    public String getItem_lost() {
        return item_lost;
    }

    public String getPlace_lost() {
        return place_lost;
    }
}
