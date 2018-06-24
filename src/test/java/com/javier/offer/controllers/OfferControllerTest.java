package com.javier.offer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javier.offer.Services.OfferService;
import com.javier.offer.dtos.OfferDto;
import com.javier.offer.entities.Offer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class OfferControllerTest {

    private static final String OFFER_1_NAME = "offer1";
    private static final String OFFER_2_NAME = "offer1";
    private static final long OFFER_1_ID = 1L;
    private static final long OFFER_2_ID = 2L;

    @InjectMocks
    private OfferController offerController;

    @Mock
    private OfferService offerService;

    // Instead of mocking modelMapper behavior lets simplify just this one and use the real implementation
    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(offerController).build();
    }

    @Test
    public void getOffers() throws Exception {

        // Given
        List<Offer> expectedOffers = getExpectedOfferList();
        when(offerService.getOffers()).thenReturn(expectedOffers);

        // When
        mockMvc
                .perform(get("/offer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id").value(OFFER_1_ID))
                .andExpect(jsonPath("$[0].name").value(OFFER_1_NAME))
                .andExpect(jsonPath("$[1].id").value(OFFER_2_ID))
                .andExpect(jsonPath("$[1].name").value(OFFER_2_NAME));

        // Then
        verify(offerService, times(1)).getOffers();
    }


    @Test
    public void getOffer() throws Exception {

        // Given
        Offer expectedOffer = new Offer();
        expectedOffer.setId(OFFER_1_ID);
        expectedOffer.setName(OFFER_1_NAME);
        when(offerService.getOffer(eq(OFFER_1_ID))).thenReturn(expectedOffer);

        // When
        mockMvc
                .perform(get("/offer/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(OFFER_1_ID))
                .andExpect(jsonPath("$.name").value(OFFER_1_NAME));

        // Then
        verify(offerService, times(1)).getOffer(eq(OFFER_1_ID));
    }

    @Test
    public void addOffer() throws Exception {

        // Given
        OfferDto offerDto = new OfferDto();
        offerDto.setName(OFFER_1_NAME);

        Offer expectedOffer = modelMapper.map(offerDto, Offer.class);
        when(offerService.save(any(Offer.class))).thenReturn(expectedOffer);

        // When
        mockMvc
                .perform(post("/offer")
                        .content(asJsonString(offerDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(OFFER_1_NAME));

        // Then
        verify(offerService, times(1)).save(any(Offer.class));
    }

    @Test
    public void cancelOffer() {
        // TODO
    }

    private List<Offer> getExpectedOfferList() {
        Offer offer1 = new Offer();
        offer1.setId(OFFER_1_ID);
        offer1.setName(OFFER_1_NAME);
        Offer offer2 = new Offer();
        offer2.setId(OFFER_2_ID);
        offer2.setName(OFFER_2_NAME );

        List<Offer> expectedOffers = new ArrayList<>();
        expectedOffers.add(offer1);
        expectedOffers.add(offer2);

        return expectedOffers;
    }
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}