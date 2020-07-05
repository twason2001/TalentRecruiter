package com.talent.recruit.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.talent.recruit.entity.JobOfferEntity;

public interface JobOfferRepository  extends CrudRepository<JobOfferEntity, Long>{

	@Query("select r FROM JobOfferEntity r WHERE r.jobTitle = :jobTitle")
	JobOfferEntity findByJobTitle(String jobTitle);
}
