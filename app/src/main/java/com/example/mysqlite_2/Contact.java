package com.example.mysqlite_2;

import java.io.Serializable;

public class Contact implements Serializable {
    private long id;
    private String name;
    private String email;
    private String number;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Contact(){

    }
    public Contact(long id, String name,String number,String email ){
        this.name=name;
        this.number=number;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + number + ", Email: " + email;
    }
}