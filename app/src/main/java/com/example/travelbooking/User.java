package com.example.travelbooking;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private byte[] image;

    public User(int id, String firstName, String lastName, String phone, String email, byte[] image) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getImage() {
        return image;
    }
}
