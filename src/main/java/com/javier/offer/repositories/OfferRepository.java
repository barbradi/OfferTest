package com.javier.offer.repositories;


import com.javier.offer.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    Offer getOne(Long id);

    Offer save(Offer offer);

    List<Offer> findAll();
}