package com.talent.recruit.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talent.recruit.dto.JobApplicationDTO;
import com.talent.recruit.service.JobApplicationService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = JobApplicationController.class)
public class JobApplicationControllerTest {
	
	private final String API_CREATE_ENDPOINT="/api/application/create";
	private final String GET_APPLICATIONS_ENDPOINT="/api/application/get-applications/1";

	
	JobApplicationDTO jobApplicationDTO=new JobApplicationDTO();
	
	ObjectMapper objMapper=new ObjectMapper();
		
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JobApplicationService jobApplicationService;
	

	@Before
	public void setUp() {
		jobApplicationDTO.setApplicationStatus("APPLIED");
		jobApplicationDTO.setOfferId(1L);
		jobApplicationDTO.setResumeText("Sample resume text");
		jobApplicationDTO.setEmail("johnDoe@email.com");
	}
	
	@Test
	public void whenApplicationIsValid_thenStatusIs201() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_CREATE_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(jobApplicationDTO)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	
	}
	
	
	@Test
	public void whenStatusIsInvalid_thenStatusIs400() throws Exception {
				
		JSONObject resultJson = new JSONObject();
		jobApplicationDTO.setApplicationStatus("WRONG STATUS");
		
		resultJson.put("applicationStatus", "Invalid Application Status!");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_CREATE_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(jobApplicationDTO)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		JSONAssert.assertEquals(response.getContentAsString(), resultJson, false);

	}
	
	@Test
	public void whenEmailIsNotValid_thenStatusIs400() throws Exception {
		
		JSONObject resultJson = new JSONObject();
		jobApplicationDTO.setEmail("WRONG_EMAIL");
		
		resultJson.put("email", "must be a well-formed email address");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_CREATE_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(jobApplicationDTO)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		JSONAssert.assertEquals(response.getContentAsString(), resultJson, false);
		
	}
	
	@Test
	public void whenResumeTextIsNull_thenStatusIs400() throws Exception {
		
		JSONObject resultJson = new JSONObject();
		jobApplicationDTO.setResumeText(null);
		
		resultJson.put("resumeText", "must not be blank");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_CREATE_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(jobApplicationDTO)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		JSONAssert.assertEquals(response.getContentAsString(), resultJson, false);
		
	}
	
	
	@Test
	public void whenGetOffer_thenStatusIs200() throws Exception {
		Mockito.when(jobApplicationService.getApplication(Mockito.anyLong(),Mockito.any(JobApplicationDTO.class))).thenReturn(jobApplicationDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_APPLICATIONS_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(jobApplicationDTO));

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	public void whenGetAllOffer_thenStatusIs200() throws Exception {
		List<JobApplicationDTO> listOfApplications=new ArrayList<>();
		listOfApplications.add(jobApplicationDTO);
		
		Mockito.when(jobApplicationService.getApplicationList(Mockito.anyLong())).thenReturn(listOfApplications);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_APPLICATIONS_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		

	}
	
	
	
}
