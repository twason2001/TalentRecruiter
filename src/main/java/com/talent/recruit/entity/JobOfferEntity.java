package com.talent.recruit.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.Data;


@Data
@Entity
@Table(name = "JOB_OFFERS")
public class JobOfferEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true,nullable=false)
	private String jobTitle;
	
	@Column(nullable=false)
	private Date startDate;
	
	
	@OneToMany(targetEntity=JobApplicationEntity.class, mappedBy="relatedOffer",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<JobApplicationEntity> applications=new ArrayList<>();

}
