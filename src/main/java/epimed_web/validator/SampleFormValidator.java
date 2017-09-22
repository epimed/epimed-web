package epimed_web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import epimed_web.form.SampleForm;

@Component
public class SampleFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SampleForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		SampleForm form =  (SampleForm) target;
	
		// === Consistency : normal tissue status and morphology ===
		
		if (form.getIdTissueStatus()!=null && form.getIdTissueStatus().equals(1) 
				&& form.getIdMorphology()!=null && !form.getIdMorphology().isEmpty()) {
			String message = "The morphology is not defined for NORMAL tissues. "
					+ "Please select TUMORAL tissue status or don't select any morphology.";
			errors.rejectValue("idTissueStatus", "Consistensy.formQuerySamples.idTissueStatus", "");
			errors.rejectValue("idMorphology", "Consistensy.formQuerySamples.idMorphology", "");
			errors.reject("Consistensy.formQuerySamples", message);
		}
		
		// === Consistency : normal tissue status and survival ===
		
		if (form.getIdTissueStatus()!=null && form.getIdTissueStatus().equals(1) 
				&& form.getSurvival()!=null && form.getSurvival()) {
			String message =  "The survival is not defined for NORMAL tissues. "
					+ "Please select TUMORAL tissue status or don't select the survival information.";
			errors.rejectValue("idTissueStatus", "Consistensy.formQuerySamples.idTissueStatus", "");
			errors.rejectValue("survival", "Consistensy.formQuerySamples.survival", "");
			errors.reject("Consistensy.formQuerySamples", message);
		}

	}

}
