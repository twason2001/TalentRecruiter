package com.talent.recruit.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class JobOfferDTO {
	
	private Long offerId;
	@NotNull
	private String jobTitle;
	@NotNull
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private Date startDate;

	private Integer noOfApplications;
	
}
    