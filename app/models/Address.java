package models;

import com.avaje.ebean.Finder;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CacheStrategy;
import com.avaje.ebean.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by Himanshu on 12-01-2017.
 */
@Entity
@Table(name = "address")
@CacheStrategy
public class Address {
    private static final long serialVersionUID = 2L;

    @Id
    public Integer addr_id;


    public String addr;

    //bi-directional many-to-one association to SigsCcn
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="uid")
    @JsonIgnoreProperties("addresses")
    public User uid;
    /**
     * Generic query helper for entity Company with id Int
     */
    public static Finder<Integer, Address> find = new Finder<>(Address.class);

    public Integer getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(Integer addr_id) {
        this.addr_id = addr_id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public User getUid() {
        return uid;
    }

    public void setUid(User uid) {
        this.uid = uid;
    }
}
