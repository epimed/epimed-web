package epimed_web.service.form;

import java.util.List;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epimed_web.form.SampleForm;
import epimed_web.repository.mongodb.SampleRepository;
import epimed_web.service.mongodb.SampleService;

@Service
public class SampleFormService {
	
	@Autowired
	private SampleRepository sampleRepository;
	
	@Autowired
	private SampleService sampleService;
	
	
	/** ============================================================================ */
	
	public void executeQuery(SampleForm form) {	
		
		Bson filter = this.convertToFilter(form);
		
		Long nbSamples = sampleRepository.count(filter);
		form.setNbSamples(nbSamples);
		
		List<String> listSeries = sampleRepository.listSeries(filter);
		form.setListSeries(listSeries);
		
		List<String> listPlatforms = sampleRepository.listPlatforms(filter);
		form.setListPlatforms(listPlatforms);
		
	}
	
	/** ============================================================================ */
	
	public Bson convertToFilter(SampleForm form) {
		
		Bson filter = sampleService.createFilter(form.getPlatformType(), 
				form.getIdPlatform(), form.getIdTopology(), form.getIdTissueStatus(), 
				form.getIdTissueStage(), form.getIdMorphology(), form.getSurvival());
		
		return filter;
		
	}
	
	/** ============================================================================ */
}
