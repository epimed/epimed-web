/**
 * EpiMed - Information system for bioinformatics developments in the field of epigenetics
 * 
 * This software is a computer program which performs the data management 
 * for EpiMed platform of the Institute for Advances Biosciences (IAB)
 *
 * Copyright University of Grenoble Alps (UGA)
 * 
 * Please check LICENSE file
 *
 * Author: Ekaterina Bourova-Flin 
 *
 */
package epimed_web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController implements ErrorController {

	// @Autowired
	// private WebLogService webLogService;
	
	 private static final String PATH = "/error";
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex, HttpServletRequest request) {

		ModelAndView model = new ModelAndView("error");
		String errorMessage = ex.toString();
		model.addObject("errorMessage", errorMessage);
		
		// webLogService.log(errorMessage);
		
		ex.printStackTrace();
		
		return model;

	}
	
	@RequestMapping(value = PATH)
    public String error() {
        return "error";
    }


	@Override
	public String getErrorPath() {
		return PATH;
	}

}
