package com.javier.offer.Services;

import com.javier.offer.Status;
import com.javier.offer.entities.Offer;
import com.javier.offer.repositories.OfferRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceTest {

    private static final String OFFER_1_NAME = "offer1";
    private static final String OFFER_2_NAME = "offer1";
    private static final long OFFER_1_ID = 1L;
    private static final long OFFER_2_ID = 2L;
    private static final String EXPIRED_DATE = "2016-08-16";  // ISO_LOCAL_DATE
    private static final String NOT_EXPIRED_DATE = "2050-08-16";  // ISO_LOCAL_DATE,

    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferRepository offerRepository;

    @Test
    public void save_notExpiredOffer() {

        // Given
        LocalDate localDate = LocalDate.parse(NOT_EXPIRED_DATE);
        Offer expectedOffer = new Offer();
        expectedOffer.setId(OFFER_1_ID);
        expectedOffer.setName(OFFER_1_NAME);
        expectedOffer.setExpirityDate(java.sql.Date.valueOf(localDate));

        when(offerRepository.save(eq(expectedOffer))).thenReturn(expectedOffer);

        // When
        Offer result = offerService.save(expectedOffer);

        // Then
        assertEquals(expectedOffer.getId(), result.getId());
        assertEquals(Status.VALID, result.getStatus());
    }

    @Test
    public void save_ExpiredOffer() {

        // Given
        LocalDate localDate = LocalDate.parse(EXPIRED_DATE);
        Offer expectedOffer = new Offer();
        expectedOffer.setId(OFFER_1_ID);
        expectedOffer.setName(OFFER_1_NAME);
        expectedOffer.setExpirityDate(java.sql.Date.valueOf(localDate));

        when(offerRepository.save(eq(expectedOffer))).thenReturn(expectedOffer);

        // When
        Offer result = offerService.save(expectedOffer);

        // Then
        assertEquals(expectedOffer.getId(), result.getId());
        assertEquals(Status.EXPIRED, result.getStatus());
    }

    @Test
    public void getOffer_expiredOffer() {

        // Given
        LocalDate localDate = LocalDate.parse(EXPIRED_DATE);
        Offer expectedOffer = new Offer();
        expectedOffer.setId(OFFER_1_ID);
        expectedOffer.setName(OFFER_1_NAME);
        expectedOffer.setStatus(Status.VALID);
        expectedOffer.setExpirityDate(java.sql.Date.valueOf(localDate));

        when(offerRepository.getOne(eq(OFFER_1_ID))).thenReturn(expectedOffer);

        // When
        Offer result = offerService.getOffer(OFFER_1_ID);

        // Then
        assertEquals(expectedOffer.getId(), result.getId());
        assertEquals(Status.EXPIRED, result.getStatus());
    }

    @Test
    public void getOffer_notExpiredOffer() {

        // Given
        LocalDate localDate = LocalDate.parse(NOT_EXPIRED_DATE);
        Offer expectedOffer = new Offer();
        expectedOffer.setId(OFFER_1_ID);
        expectedOffer.setName(OFFER_1_NAME);
        expectedOffer.setStatus(Status.VALID);
        expectedOffer.setExpirityDate(java.sql.Date.valueOf(localDate));

        when(offerRepository.getOne(eq(OFFER_1_ID))).thenReturn(expectedOffer);

        // When
        Offer result = offerService.getOffer(OFFER_1_ID);

        // Then
        assertEquals(expectedOffer.getId(), result.getId());
        assertEquals(Status.VALID, result.getStatus());
    }

    @Test
    public void getOffers() {
    }

    @Test
    public void cancelOffer() {
    }
}