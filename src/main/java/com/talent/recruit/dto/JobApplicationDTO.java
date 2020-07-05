package com.talent.recruit.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.talent.recruit.model.JobApplicationStatus;
import com.talent.recruit.utils.ValueValidator;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)

public class JobApplicationDTO {

	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String resumeText;
	
	@ValueValidator(EnumValidatorClass = JobApplicationStatus.class)
	@NotNull
	private String applicationStatus;
	
	@NotNull
	private Long offerId;
	
	private JobOfferDTO offer;

}
