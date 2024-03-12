package com.zep.AuthorisationandAuthentication.models;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
@Entity
@Data
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser implements UserDetails {
 @Setter
 @Id
 @GeneratedValue(strategy=GenerationType.AUTO)
 @Column(name="user_id")
 private Integer userId;
    @Column(unique=false)
 private String firstName;
    @Column(unique=false)
 private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;


    @Column(unique=true)
 private String email;

 private String password;
 @ManyToMany(fetch=FetchType.EAGER)
 @JoinTable(
    name="user_role_junction",joinColumns = {@JoinColumn(name="user_id")},
 inverseJoinColumns = {@JoinColumn(name="role_id")}
 )
 private Set<Role> authorities;

    public ApplicationUser(Integer userId,String email,String password,Set<Role>authorities){
super();
this.userId=userId;
this.email=email;
this.password=password;
this.authorities=authorities;
}

    public ApplicationUser(int userId, String email, String encodedPassword, Set<Role> authorities, String firstName, String lastName,String phoneNumber) {
        this.userId = userId;
        this.email = email;

        this.password = encodedPassword;
        this.authorities = authorities;
        this.firstName = firstName;
        this.lastName = lastName; this.phoneNumber=phoneNumber;
    }



    @Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    
    return this.authorities;
}

    @Override
    public String getPassword() {
      return this.password;
    }

    @Override
    public String getUsername() {
    return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
       return true;
    }

    @Override
    public boolean isEnabled() {
return true;
    }
    
}
