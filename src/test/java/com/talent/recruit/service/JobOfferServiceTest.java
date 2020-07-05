package com.talent.recruit.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.talent.recruit.dto.JobOfferDTO;
import com.talent.recruit.entity.JobOfferEntity;
import com.talent.recruit.exception.DataNotFoundException;
import com.talent.recruit.exception.ResourceAlreadyExistException;
import com.talent.recruit.repo.JobOfferRepository;
import com.talent.recruit.service.impl.JobOfferServiceImpl;
import com.talent.recruit.utils.ModelConverter;

@RunWith(MockitoJUnitRunner.class)
public class JobOfferServiceTest {

	JobOfferServiceImpl jobOfferService;

	@Mock
	JobOfferRepository jobOfferRepository;

	@Mock
	ModelConverter modelConverter;

	JobOfferDTO jobOfferDto = new JobOfferDTO();
	JobOfferEntity ent = new JobOfferEntity();

	@Before
	public void setUp() {
		jobOfferService = new JobOfferServiceImpl(modelConverter, jobOfferRepository);
		jobOfferDto.setJobTitle("Software Engineer Job");
		jobOfferDto.setStartDate(new Date());

		ent.setJobTitle(jobOfferDto.getJobTitle());
		ent.setStartDate(jobOfferDto.getStartDate());

		Mockito.when(modelConverter.toJobOfferEntity(Mockito.any())).thenReturn(ent);
		Mockito.when(modelConverter.toJobOfferDTO(Mockito.any())).thenReturn(jobOfferDto);

	}

	@Test
	public void whenOfferIsValid_createUser() {

		Mockito.when(jobOfferRepository.findByJobTitle(Mockito.anyString())).thenReturn(null);
		Mockito.when(jobOfferRepository.save(Mockito.any(JobOfferEntity.class))).thenReturn(ent);
		JobOfferDTO result = jobOfferService.createOffer(jobOfferDto);
		assertEquals(result.getJobTitle(),jobOfferDto.getJobTitle());

	}

	@Test(expected = ResourceAlreadyExistException.class)
	public void whenOfferAlreadyExist_throwException() {
		Mockito.when(jobOfferRepository.findByJobTitle(Mockito.anyString())).thenReturn(ent);
		jobOfferService.createOffer(jobOfferDto);
	}

	@Test(expected = DataNotFoundException.class)
	public void whenOfferIdIsInvalid_throwException() {
		Mockito.when(jobOfferRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		jobOfferService.getOffer(1L);
	}

	@Test
	public void whenOfferIdvalid_getOffer() {
		Mockito.when(jobOfferRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ent));
		JobOfferDTO result = jobOfferService.getOffer(1L);
		assertEquals(result.getJobTitle(),ent.getJobTitle());
	}

}
