package com.javier.offer.controllers;

import com.javier.offer.Services.OfferService;
import com.javier.offer.dtos.OfferDto;
import com.javier.offer.entities.Offer;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<OfferDto> getOffers() {
        List<Offer> offers = offerService.getOffers();
        return convertToOfferDtoList(offers);
    }

    @GetMapping("/{id}")
    public OfferDto getOffer(@PathVariable Long id) {
        Offer offer = offerService.getOffer(id);
        return convertToOfferDto(offer);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OfferDto addOffer(@RequestBody OfferDto offerDto) {
        offerDto.setCreationDate(new Date());
        Offer offerToSave = convertToOfferEntity (offerDto);
        Offer offerSaved = offerService.save(offerToSave);
        return convertToOfferDto(offerSaved);
    }

    @PutMapping("/{id}/actions/cancel")
    public OfferDto cancelOffer(@PathVariable Long id) {
        Offer offer = offerService.cancelOffer(id);
        return convertToOfferDto(offer);
    }

    private Offer convertToOfferEntity (OfferDto offerDto){
        return modelMapper.map(offerDto, Offer.class);
    }

    private OfferDto convertToOfferDto (Offer offer){
        return modelMapper.map(offer, OfferDto.class);
    }

    private List<OfferDto> convertToOfferDtoList (List<Offer> offers){
        Type listType = new TypeToken<List<OfferDto>>() {}.getType();
        return modelMapper.map(offers, listType);
    }

}
