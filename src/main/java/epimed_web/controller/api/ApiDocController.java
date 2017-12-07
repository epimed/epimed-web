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
package epimed_web.controller.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import epimed_web.service.log.ApplicationLogger;

@Controller
public class ApiDocController extends ApplicationLogger {


	/** ====================================================================================== */

	@RequestMapping(value = "/apiExpGroup", method = RequestMethod.GET)
	public String loadGseWithR (
			HttpServletRequest request
			) throws IOException {

		return "api/apiExpGroup";

	}

	/** ====================================================================================== */

	@RequestMapping(value = "/apiQuerySamples", method = RequestMethod.GET)
	public String apiQuerySamples (
			HttpServletRequest request
			) throws IOException {

		return "api/apiQuerySamples";
	}



	/** ====================================================================================== */

	@RequestMapping(value = "/apiQuerySeries", method = RequestMethod.GET)
	public String apiQuerySeries (
			HttpServletRequest request
			) throws IOException {

		return "api/apiQuerySeries";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/apiQueryPlatforms", method = RequestMethod.GET)
	public String apiQueryPlatforms (
			HttpServletRequest request
			) throws IOException {

		return "api/apiQueryPlatforms";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/apiQueryGenes", method = RequestMethod.GET)
	public String apiQueryJobs (
			HttpServletRequest request
			) throws IOException {

		return "api/apiQueryGenes";
	}
	
	/** ====================================================================================== */

	@RequestMapping(value = "/apiQueryTissueSpecificGenes", method = RequestMethod.GET)
	public String apiQueryTissueSpecificGenes (
			HttpServletRequest request
			) throws IOException {

		return "api/apiQueryTissueSpecificGenes";
	}

	/** ====================================================================================== */


}
