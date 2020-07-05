package com.talent.recruit.utils;

import java.util.List;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.talent.recruit.dto.JobApplicationDTO;
import com.talent.recruit.dto.JobOfferDTO;
import com.talent.recruit.entity.JobApplicationEntity;
import com.talent.recruit.entity.JobOfferEntity;



@Component
public class ModelConverter {

	private final ModelMapper modelMapper=new ModelMapper();
	
	
	public JobOfferDTO toJobOfferDTO(final JobOfferEntity offerEntity) {
		TypeMap<JobOfferEntity,JobOfferDTO> typeMap=modelMapper.getTypeMap(JobOfferEntity.class, JobOfferDTO.class);
		if(typeMap==null) {
			modelMapper.addMappings(new PropertyMap<JobOfferEntity, JobOfferDTO>() {
					@Override
					protected void configure() {
						using(new AbstractConverter<List<JobApplicationEntity>, Integer>() {
							  public Integer convert(List<JobApplicationEntity> context) {
								    return context.size();
								  }
						    }).map(source.getApplications(),
								destination.getNoOfApplications());
					}
				});
		}
		return modelMapper.map(offerEntity, JobOfferDTO.class);
	}

	public JobOfferEntity toJobOfferEntity(final JobOfferDTO offerDTO) {
		return modelMapper.map(offerDTO, JobOfferEntity.class);
	}
	
	public JobApplicationDTO toJobApplicationDTO(final JobApplicationEntity jobApplicationEntity) {
		TypeMap<JobApplicationEntity,JobApplicationDTO> typeMap=modelMapper.getTypeMap(JobApplicationEntity.class, JobApplicationDTO.class);
		if(typeMap==null) {
			modelMapper.addMappings(new PropertyMap<JobApplicationEntity, JobApplicationDTO>() {
					@Override
					protected void configure() {
						map().getOffer().setJobTitle(source.getRelatedOffer().getJobTitle());
						map().getOffer().setStartDate(source.getRelatedOffer().getStartDate());
					}
				});
		}
		return modelMapper.map(jobApplicationEntity, JobApplicationDTO.class);
	}

	public JobApplicationEntity toJobApplicationEntity(final JobApplicationDTO jobApplicationDTO) {
		TypeMap<JobApplicationDTO, JobApplicationEntity> typeMap = modelMapper.getTypeMap(JobApplicationDTO.class,
				JobApplicationEntity.class);
		if (typeMap == null) {
	
			modelMapper.addMappings(new PropertyMap<JobApplicationDTO, JobApplicationEntity>() {
				@Override
				protected void configure() {
					map().getId().setEmail(source.getEmail());
					map().getId().setId(null);
				}
			});
		}
		return modelMapper.map(jobApplicationDTO, JobApplicationEntity.class);
	}
}
