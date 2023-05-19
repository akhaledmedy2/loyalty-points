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
@Table(name = "reward_points")
public class RewardPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "month_date")
    private Date month;

    @Column(name = "points")
    private int points;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}