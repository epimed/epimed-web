package epimed_web.controller.gene;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import epimed_web.entity.neo4j.Platform;
import epimed_web.repository.neo4j.Neo4jPlatformRepository;
import epimed_web.service.log.ApplicationLogger;

@Controller
@RequestMapping("/genes")
public class ProbesetController extends ApplicationLogger {
	
	@Autowired 
	private Neo4jPlatformRepository platformRepository;

	/** ====================================================================================== */

	@RequestMapping(value = "/probeset", method = RequestMethod.GET)
	public String updateGet(
			Model model,
			@RequestParam(value = "organism", required = false) Integer organism,
			@RequestParam(value = "taxid", required = false) Integer taxid,
			@RequestParam(value = "idSelectedPlatform", required = false) String idSelectedPlatform
			) throws IOException {
		
		if (taxid==null) { 
			taxid = 9606;
			
		}
		if (idSelectedPlatform==null || idSelectedPlatform.isEmpty()) {
			idSelectedPlatform = "GPL570";
		}
		
		List<Platform> listPlatforms = platformRepository.findAllEnabledByTaxid(taxid);
		
		model.addAttribute("listPlatforms", listPlatforms);
		model.addAttribute("taxid", taxid);
		model.addAttribute("idSelectedPlatform", idSelectedPlatform);
		
		return "genes/probeset";
	}
	
	/** ====================================================================================== */
	
}
