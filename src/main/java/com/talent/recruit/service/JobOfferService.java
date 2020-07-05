package com.talent.recruit.service;

import java.util.List;

import com.talent.recruit.dto.JobOfferDTO;

public interface JobOfferService {

	JobOfferDTO createOffer(JobOfferDTO offer);

	JobOfferDTO getOffer(Long offerId);

	List<JobOfferDTO> getOfferList();

	

}
