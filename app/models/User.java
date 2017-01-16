package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.annotation.CacheStrategy;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import play.db.ebean.*;
import play.data.validation.*;


@Entity
@Table(name = "user")
@CacheStrategy
public class User extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @Id
    public Integer user_id;


    public String name;

    public Integer age;

    @OneToMany(mappedBy="uid")
    @JsonIgnoreProperties("uid")
    private List<Address> addresses;
    /**
     * Generic query helper for entity Company with id Int
     */
    public static Finder<Integer, User> find = new Finder<>(User.class);

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
