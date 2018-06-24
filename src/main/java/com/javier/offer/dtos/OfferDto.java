package com.javier.offer.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.javier.offer.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OfferDto {

    private Long id;

    private String name;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date creationDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expirityDate;

    private String Description;

    private double discount;

    private double discountPercentage;

    private Status status;
}
