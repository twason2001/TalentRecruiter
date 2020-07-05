package com.talent.recruit.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "JOB_APPLICATIONS")
public class JobApplicationEntity {


	@EmbeddedId
	JobApplicationId id;

	private String resumeText;
	private String applicationStatus;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "JOB_OFFERS_ID", nullable = false)
	@MapsId("id")
	private JobOfferEntity relatedOffer;

}
