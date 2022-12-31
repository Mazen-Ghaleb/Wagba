package com.example.wagba.view.AdapterData;

public class UserData {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String birthdate;
    private String gender;

    public UserData(String id, String firstName, String lastName, String email, String birthdate, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getGender() {
        return gender;
    }

    public String getId() {
        return id;
    }
}
