package entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String gender;
    private String email;
    private String status;

    public User(){};
}