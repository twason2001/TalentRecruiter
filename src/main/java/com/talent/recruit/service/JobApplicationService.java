package com.talent.recruit.service;

import java.util.List;

import com.talent.recruit.dto.JobApplicationDTO;

public interface JobApplicationService {
	JobApplicationDTO createApplication(final JobApplicationDTO application);

	JobApplicationDTO getApplication(final Long offerId,final JobApplicationDTO application);

	List<JobApplicationDTO> getApplicationList(final Long offerId);

}
