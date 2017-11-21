package epimed_web.controller.gene;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import epimed_web.entity.neo4j.Assembly;
import epimed_web.repository.neo4j.AssemblyRepository;
import epimed_web.service.log.ApplicationLogger;

@Controller
@RequestMapping("/genes")
public class PositionController extends ApplicationLogger {

	
	@Autowired
	private AssemblyRepository assemblyRepository;
	
	/** ====================================================================================== */

	@RequestMapping(value = "/position", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateGet(
			Model model,
			@RequestParam(value = "organism", required = false) Integer taxid,
			@RequestParam(value = "idAssembly", required = false) String assembly,
			@RequestParam(value = "positionType", required = false) String positionType,
			@RequestParam(value = "input", required = false) String input
			) throws IOException {


		// === Taxid ===
		if (taxid==null) {
			taxid= 9606;
		}
		
		// === Assemblies ===
		List<Assembly> listAssemblies = assemblyRepository.findByTaxid(taxid);
		
		// === positionType ===
		if (positionType==null) {
			positionType="unique";
		}
		
		model.addAttribute("selectedTaxid", taxid);
		model.addAttribute("selectedAssembly", assembly);
		model.addAttribute("listAssemblies", listAssemblies);
		model.addAttribute("selectedPositionType", positionType);
		model.addAttribute("input", input);
		
		return "genes/position";
	}

	/** ====================================================================================== */

}
