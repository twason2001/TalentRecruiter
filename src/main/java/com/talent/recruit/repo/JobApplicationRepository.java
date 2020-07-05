package com.talent.recruit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.talent.recruit.entity.JobApplicationEntity;

public interface JobApplicationRepository extends CrudRepository<JobApplicationEntity, Long>{
	
	@Query("select r FROM JobApplicationEntity r WHERE r.relatedOffer.id = :offerId")
	List<JobApplicationEntity> findAllApplications(Long offerId);

	@Query("select r FROM JobApplicationEntity r WHERE r.relatedOffer.id = :offerId and email=:candidateEmail")
	JobApplicationEntity findApplicationByIdAndEmail(Long offerId, String candidateEmail);
	
	
}
