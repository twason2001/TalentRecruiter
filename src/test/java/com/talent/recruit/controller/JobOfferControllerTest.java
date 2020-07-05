package com.talent.recruit.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
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
import com.talent.recruit.dto.JobOfferDTO;
import com.talent.recruit.service.JobOfferService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = JobOfferController.class)
public class JobOfferControllerTest {
	
	private final String API_CREATE_ENDPOINT="/api/offer/create";
	private final String GET_OFFER_ENDPOINT="/api/offer/get-offer/1";
	private final String GET_OFFERS_ENDPOINT="/api/offer/get-offers";
	

	private JobOfferDTO jobOfferDTO = new JobOfferDTO();

	private ObjectMapper objMapper = new ObjectMapper();
	

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JobOfferService jobOfferService;

	
	@Before
	public void setUp() {
		jobOfferDTO.setOfferId(1L);
		jobOfferDTO.setJobTitle("Software Engineer Job");
		jobOfferDTO.setStartDate(new Date());
	}

	@Test
	public void whenOfferIsValid_thenStatusIs201() throws Exception {

		Mockito.when(jobOfferService.createOffer(Mockito.any(JobOfferDTO.class))).thenReturn(jobOfferDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_CREATE_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(jobOfferDTO))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	}

	@Test
	public void whenTitleIsMissing_thenStatusIs400() throws Exception {
		JSONObject resultJson = new JSONObject();
		resultJson.put("jobTitle", "must not be null");

		String json = "{\"startDate\": \"2020-01-13\"}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_CREATE_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON).content(json).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		JSONAssert.assertEquals(response.getContentAsString(), resultJson, false);

	}

	@Test
	public void whenStartDateIsMissing_thenStatusIs400() throws Exception {

		JSONObject resultJson = new JSONObject();
		resultJson.put("startDate", "must not be null");

		String json = "{\"jobTitle\": \"Software Engineer Job\"}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_CREATE_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON).content(json).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		JSONAssert.assertEquals(response.getContentAsString(), resultJson, false);

	}

	@Test
	public void whenGetOffer_thenStatusIs200() throws Exception {
		Mockito.when(jobOfferService.getOffer(Mockito.anyLong())).thenReturn(jobOfferDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_OFFER_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void whenGetAllOffer_thenStatusIs200() throws Exception {
		List<JobOfferDTO> listOfOffers = new ArrayList<>();
		listOfOffers.add(jobOfferDTO);

		Mockito.when(jobOfferService.getOfferList()).thenReturn(listOfOffers);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_OFFERS_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

}
