package com.javier.offer.Services;

import com.javier.offer.Status;
import com.javier.offer.entities.Offer;
import com.javier.offer.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public Offer save(Offer offer) {
        Date nowDate = new Date();
        if (isaOfferWhichExpires(offer, nowDate)) {
            offer.setStatus(Status.EXPIRED);
        }
        else {
            offer.setStatus(Status.VALID);
        }
        return offerRepository.save(offer);
    }

    public Offer getOffer(Long id) {
        Date nowDate = new Date();
        Offer offer = offerRepository.getOne(id);
        checkIfExpired(offer, nowDate);
        return offer;
    }

    public List<Offer> getOffers() {
        Date nowDate = new Date();
        List<Offer> offers = offerRepository.findAll();
        // a custom query and a CRON job could be set if performance is a concern
        offers.forEach(s -> checkIfExpired(s, nowDate));
        return offers;
    }

    public Offer cancelOffer(Long id) {
        Offer offer = offerRepository.getOne(id);
        offer.setStatus(Status.CANCELED);
        return offerRepository.save(offer);
    }

    private void checkIfExpired(Offer offer, Date nowDate ) {
        if (isaOfferWhichExpires(offer, nowDate)) {
            offer.setStatus(Status.EXPIRED);
            offerRepository.save(offer);
        }
    }

    private boolean isaOfferWhichExpires(Offer offer, Date nowDate ) {
        return (offer.getStatus() == Status.VALID || offer.getStatus() == null)
                && offer.getExpirityDate().getTime() < nowDate.getTime();
    }
}
