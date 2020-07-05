package com.talent.recruit.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.talent.recruit.dto.JobOfferDTO;
import com.talent.recruit.entity.JobOfferEntity;
import com.talent.recruit.exception.DataNotFoundException;
import com.talent.recruit.exception.ResourceAlreadyExistException;
import com.talent.recruit.repo.JobOfferRepository;
import com.talent.recruit.service.JobOfferService;
import com.talent.recruit.utils.Constants;
import com.talent.recruit.utils.ModelConverter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobOfferServiceImpl implements JobOfferService {

	private ModelConverter modelConverter;

	
	private JobOfferRepository offerRepo;

	@Override
	public JobOfferDTO createOffer(JobOfferDTO offer) {
		JobOfferEntity offerEntity = toEntity(offer);

		Optional<JobOfferEntity> optionEntity = Optional.ofNullable(offerRepo.findByJobTitle(offer.getJobTitle()));
		if (optionEntity.isPresent()) {
			throw new ResourceAlreadyExistException(String.format(Constants.TITLE_EXIST, offer.getJobTitle()));
		}
		offerRepo.save(offerEntity);
		return toDTO(offerEntity);
	}

	@Override
	public JobOfferDTO getOffer(final Long offerId) {
		return toDTO(findOffer(offerId));

	}

	public JobOfferEntity findOffer(final Long offerId) {
		return offerRepo.findById(offerId)
				.orElseThrow(() -> new DataNotFoundException(String.format(Constants.OFFER_NOT_FOUND, offerId)));
	}

	private List<JobOfferDTO> getAllOffers() {
		return StreamSupport.stream(offerRepo.findAll().spliterator(), false).map(this::toDTO)
				.collect(Collectors.toList());

	}

	@Override
	public List<JobOfferDTO> getOfferList() {
		return getAllOffers();
	}

	private JobOfferEntity toEntity(JobOfferDTO offerDTO) {
		return modelConverter.toJobOfferEntity(offerDTO);
	}

	private JobOfferDTO toDTO(JobOfferEntity offerEntity) {
		return modelConverter.toJobOfferDTO(offerEntity);
	}

}
