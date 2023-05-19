package com.infogen.loyalty.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@DynamicUpdate
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username", unique = true)
    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "soft_deleted")
    private boolean softDeleted;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<RewardPoints> rewardPoints;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(name, customer.name) && Objects.equals(username, customer.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}