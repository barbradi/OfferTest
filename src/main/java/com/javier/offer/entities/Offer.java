package com.javier.offer.entities;

import com.javier.offer.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Getter
@Setter
@Entity
public class Offer {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private Date creationDate;

    @Column
    private Date expirityDate;

    @Column
    private String Description;

    @Column
    private double discount;

    @Column
    private double discountPercentage;

    @Column
    private Status Status;
}
