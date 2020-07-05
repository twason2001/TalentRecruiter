package com.talent.recruit.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.talent.recruit.dto.JobApplicationDTO;
import com.talent.recruit.entity.JobApplicationEntity;
import com.talent.recruit.entity.JobOfferEntity;
import com.talent.recruit.exception.DataNotFoundException;
import com.talent.recruit.exception.ResourceAlreadyExistException;
import com.talent.recruit.repo.JobApplicationRepository;
import com.talent.recruit.repo.JobOfferRepository;
import com.talent.recruit.service.JobApplicationService;
import com.talent.recruit.utils.Constants;
import com.talent.recruit.utils.ModelConverter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JobApplicationServiceImpl implements JobApplicationService {

	private ModelConverter modelConverter;

	private JobApplicationRepository applicationRepo;

	private JobOfferRepository offerRepo;

	@Override
	public JobApplicationDTO createApplication(final JobApplicationDTO application) {

		final Long offerId = application.getOfferId();
		JobOfferEntity offerEntity = offerRepo.findById(offerId)
				.orElseThrow(() -> new DataNotFoundException(String.format(Constants.OFFER_NOT_FOUND, offerId)));

		getApplication(offerId,application.getEmail()).ifPresent(s->{
			throw new ResourceAlreadyExistException(
					String.format(Constants.APPLICATION_EXIST, s.getApplicationStatus()));
		});
		
		JobApplicationEntity appEntity = toEntity(application);
		appEntity.setRelatedOffer(offerEntity);

		applicationRepo.save(appEntity);

		return toDTO(appEntity);
	}


	@Override
	public JobApplicationDTO getApplication(final Long offerId,final JobApplicationDTO application) {
		checkIfOfferExist(offerId);
		final String candidateEmail = application.getEmail();

		Optional<JobApplicationEntity> jobApplicationEntity =getApplication(offerId,candidateEmail);
		JobApplicationEntity jobApplicationEntityValue =jobApplicationEntity.orElseThrow(() -> new DataNotFoundException(String.format(Constants.EMAIL_NOT_FOUND, candidateEmail)));

		return toDTO(jobApplicationEntityValue);
	}

	@Override
	public List<JobApplicationDTO> getApplicationList(final Long offerId) {
		checkIfOfferExist(offerId);

		return StreamSupport.stream(applicationRepo.findAllApplications(offerId).spliterator(), false).map(this::toDTO)
				.collect(Collectors.toList());
	}

	private void checkIfOfferExist(final Long offerId) {
		if (!offerRepo.existsById(offerId)) {
			throw new DataNotFoundException(String.format(Constants.OFFER_NOT_FOUND, offerId));
		}
	}
	
	private Optional<JobApplicationEntity> getApplication(final Long offerId,final String candidateEmail) {
		return Optional.ofNullable(applicationRepo.findApplicationByIdAndEmail(offerId, candidateEmail));
	}

	private JobApplicationDTO toDTO(final JobApplicationEntity jobApplicationEntity) {
		return modelConverter.toJobApplicationDTO(jobApplicationEntity);
	}

	private JobApplicationEntity toEntity(final JobApplicationDTO jobApplicationDTO) {
		return modelConverter.toJobApplicationEntity(jobApplicationDTO);
	}

}
