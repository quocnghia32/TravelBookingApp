package com.example.travelbooking;

public class User {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String username;
    private String password;
    private byte[] image;

    public User(String firstName, String lastName, String phone, String email, String username, String password,byte[] image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.image = image;
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
    public String getUsername() { return username;}

    public String getPassword() {
        return password;
    }
    public byte[] getImage() { return image;}


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUsername(String username) { this.username = username;}
    public void setPassword(String password) {
        this.password = password;
    }
    public void setImage(byte[] image) { this.image = image;}
}
