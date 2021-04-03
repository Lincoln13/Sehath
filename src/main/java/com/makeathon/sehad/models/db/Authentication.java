package com.makeathon.sehad.models.db;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Authentication", schema = "public")
public class Authentication {

    @Id
    @Column(name = "USER_ID")
    @SequenceGenerator(name = "SEQ", sequenceName = "hibernate_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
    private Long userId;

    private String email;

    private String password;

    private Boolean disabled;

    @Column(name = "disabled_date")
    private Date disabledDate;

    @OneToMany(mappedBy = "authentication", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Authority> authorityList;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Date getDisabledDate() {
        return disabledDate;
    }

    public void setDisabledDate(Date disabledDate) {
        this.disabledDate = disabledDate;
    }

    public List<Authority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", disabled=" + disabled +
                ", disabledDate=" + disabledDate +
                ", authorityList=" + authorityList +
                '}';
    }
}
