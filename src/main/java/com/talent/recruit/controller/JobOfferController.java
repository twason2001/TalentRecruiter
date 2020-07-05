package com.talent.recruit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.talent.recruit.dto.JobOfferDTO;
import com.talent.recruit.service.JobOfferService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("api/offer")
@AllArgsConstructor
public class JobOfferController {
	
	private JobOfferService offerService;
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public JobOfferDTO createOffer(@RequestBody @Valid JobOfferDTO offer){
		return offerService.createOffer(offer);
		
	}
	
	@GetMapping("/get-offer/{offerId}")
	@ResponseStatus(HttpStatus.OK)
	public JobOfferDTO getOffer(@PathVariable Long offerId){
		return offerService.getOffer(offerId);
	}
	
	@GetMapping("/get-offers")
	@ResponseStatus(HttpStatus.OK)
	public List<JobOfferDTO> getOffersList(){		
		return offerService.getOfferList();
	}
	
	
	
	

}
