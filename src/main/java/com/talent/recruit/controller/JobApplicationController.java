package com.talent.recruit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.talent.recruit.dto.JobApplicationDTO;
import com.talent.recruit.service.JobApplicationService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/application")
@AllArgsConstructor
public class JobApplicationController {
	
	private JobApplicationService jobApplicationService;
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public JobApplicationDTO createApplication(@RequestBody @Valid JobApplicationDTO application){
		return jobApplicationService.createApplication(application);
		
	}
	
	@GetMapping("/get-application/{offerId}")
	@ResponseStatus(HttpStatus.OK)
	public JobApplicationDTO getApplication(@PathVariable Long offerId,@RequestBody JobApplicationDTO application){
		return jobApplicationService.getApplication(offerId,application);
	}
	
	@GetMapping("/get-applications/{offerId}")
	@ResponseStatus(HttpStatus.OK)
	public List<JobApplicationDTO> getApplicationList(@PathVariable Long offerId){		
		return jobApplicationService.getApplicationList(offerId);
	}

}
