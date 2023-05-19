package com.infogen.loyalty.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@DynamicUpdate
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "status")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "loyalty_points")
    private int loyaltyPoints;

    @Column(name = "transaction_id",unique = true)
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}