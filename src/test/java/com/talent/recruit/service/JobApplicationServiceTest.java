package com.talent.recruit.service;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.talent.recruit.dto.JobApplicationDTO;
import com.talent.recruit.entity.JobApplicationEntity;
import com.talent.recruit.entity.JobApplicationId;
import com.talent.recruit.entity.JobOfferEntity;
import com.talent.recruit.exception.DataNotFoundException;
import com.talent.recruit.exception.ResourceAlreadyExistException;
import com.talent.recruit.repo.JobApplicationRepository;
import com.talent.recruit.repo.JobOfferRepository;
import com.talent.recruit.service.impl.JobApplicationServiceImpl;
import com.talent.recruit.utils.ModelConverter;

@RunWith(MockitoJUnitRunner.class)
public class JobApplicationServiceTest {

	JobApplicationServiceImpl jobApplicationService;

	@Mock
	JobApplicationRepository jobApplicationRepository;

	@Mock
	JobOfferRepository jobOfferRepository;

	@Mock
	ModelConverter modelConverter;

	JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
	JobApplicationEntity jobApplicationEntity = new JobApplicationEntity();

	@Before
	public void setUp() {
		jobApplicationService = new JobApplicationServiceImpl(modelConverter, jobApplicationRepository,
				jobOfferRepository);
		jobApplicationDTO.setApplicationStatus("APPLIED");
		jobApplicationDTO.setOfferId(1L);
		jobApplicationDTO.setResumeText("Sample resume text");
		jobApplicationDTO.setEmail("johnDoe@email.com");

		jobApplicationEntity.setApplicationStatus("APPLIED");
		JobApplicationId jobApplicationId = new JobApplicationId();
		jobApplicationId.setId(1L);
		jobApplicationId.setEmail("johnDoe@email.com");
		jobApplicationEntity.setId(jobApplicationId);
		jobApplicationEntity.setResumeText("Sample resume text");

		Mockito.when(modelConverter.toJobApplicationEntity(Mockito.any())).thenReturn(jobApplicationEntity);
		Mockito.when(modelConverter.toJobApplicationDTO(Mockito.any())).thenReturn(jobApplicationDTO);

	}

	@Test(expected = DataNotFoundException.class)
	public void whenOfferIdIsInvalid_thenThrowException() {
		Mockito.when(jobOfferRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		jobApplicationService.createApplication(jobApplicationDTO);
	}

	@Test(expected = ResourceAlreadyExistException.class)
	public void whenOfferAndEmailAlreadyExist_thenThrowException() {
		Mockito.when(jobOfferRepository.findById(Mockito.any())).thenReturn(Optional.of(new JobOfferEntity()));
		Mockito.when(jobApplicationRepository.findApplicationByIdAndEmail(Mockito.any(), Mockito.any()))
				.thenReturn(jobApplicationEntity);

		jobApplicationService.createApplication(jobApplicationDTO);
	}

	@Test
	public void whenOfferAndEmailIsValid_thenSaveApplication() {
		JobOfferEntity ent = new JobOfferEntity();
		ent.setJobTitle("Software Engineer Job");
		ent.setStartDate(new Date());
		ent.setId(1L);

		Mockito.when(jobOfferRepository.findById(Mockito.any())).thenReturn(Optional.of(ent));
		Mockito.when(jobApplicationRepository.findApplicationByIdAndEmail(Mockito.any(), Mockito.any()))
				.thenReturn(null);

		JobApplicationDTO result = jobApplicationService.createApplication(jobApplicationDTO);
		assertEquals(result.getEmail(),jobApplicationDTO.getEmail());
	}

	@Test(expected = DataNotFoundException.class)
	public void getApplication_whenOfferIdIsNotValid_thenThrowException() {
		Mockito.when(jobOfferRepository.existsById(Mockito.any())).thenReturn(false);
		jobApplicationService.getApplicationList(1L);
	}

	@Test(expected = DataNotFoundException.class)
	public void getApplication_whenEmailDoesNotExist_thenThrowException() {
		Mockito.when(jobOfferRepository.existsById(Mockito.any())).thenReturn(true);
		Mockito.when(jobApplicationRepository.findApplicationByIdAndEmail(Mockito.any(), Mockito.any()))
				.thenReturn(null);
		jobApplicationService.getApplication(1L, jobApplicationDTO);
	}

	@Test
	public void getApplication_whenDataIsValid_thenGetApplication() {
		Mockito.when(jobOfferRepository.existsById(Mockito.any())).thenReturn(true);
		Mockito.when(jobApplicationRepository.findApplicationByIdAndEmail(Mockito.any(), Mockito.any()))
				.thenReturn(jobApplicationEntity);
		JobApplicationDTO result = jobApplicationService.getApplication(1L, jobApplicationDTO);
		assertEquals(result.getEmail(),jobApplicationDTO.getEmail());

	}
}
