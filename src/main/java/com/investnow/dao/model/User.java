package com.investnow.dao.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonView;
import com.investnow.util.View;

@Entity
@Table(name = "User")
public class User extends BaseModel implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.User.class)
    private Long userId;

    @JsonView(View.User.class)
    @Column(nullable = false, unique = true)
    private String userName;

    @JsonView(View.User.class)
    @Column(nullable = false)
    private String firstName;

    @JsonView(View.User.class)
    private String lastName;

    private String password;

    @Column(nullable = false, unique = true)
    private String pan;

    @JsonView(View.User.class)
    @Column(nullable = false, unique = true)
    private String passportNumber;

    @JsonView(View.User.class)
    @Column(nullable = false)
    private String address;

    @JsonView(View.User.class)
    @Column(nullable = false, unique = true)
    private Long contactNumber;

    @JsonView(View.User.class)
    private String email;

    @Column(name = "not_locked", columnDefinition = "boolean default true")
    private boolean isNotLocked=true;

    @Transient
    private String token;

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public String getPan()
    {
        return pan;
    }

    public void setPan(String pan)
    {
        this.pan = pan;
    }

    public String getPassportNumber()
    {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber)
    {
        this.passportNumber = passportNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Long getContactNumber()
    {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber)
    {
        this.contactNumber = contactNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }

    @Override
    public String getUsername()
    {
         return userName;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return isNotLocked;
    }

    public void setNotLocked(boolean isNotLocked)
    {
        isNotLocked = this.isNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
