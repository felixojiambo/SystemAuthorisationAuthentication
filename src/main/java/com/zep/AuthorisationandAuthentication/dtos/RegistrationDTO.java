package com.zep.AuthorisationandAuthentication.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private  String phoneNumber;
    private String token;
//    public RegistrationDTO(String email, String password){
//        super();
//        this.email = email;
//        this.password = password;
//    }



    public String toString(){
        return "Registration info: username: " + this.email + " password: " + this.password;
    }
}