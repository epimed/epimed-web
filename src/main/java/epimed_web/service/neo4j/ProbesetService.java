package epimed_web.service.neo4j;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epimed_web.entity.neo4j.Gene;
import epimed_web.form.AjaxForm;
import epimed_web.repository.neo4j.ProbesetRepository;
import epimed_web.service.util.FormatService;

@Service
public class ProbesetService {
	
	
	@Autowired
	private FormatService formatService;
	
	@Autowired
	private ProbesetRepository probesetRepository;
	
	/** =============================================================================== */
	
	public boolean populate(AjaxForm ajaxForm) {
		
		String originalSymbol = ajaxForm.getSymbol();
		String symbol = formatService.removeVersion(ajaxForm.getSymbol());
		String idPlatform = ajaxForm.getIdPlatform();
		
		Set<String> probesets = new HashSet<String>(); 
	
		// ===== Link by genes =====
		for (Gene gene: ajaxForm.getListGenes()) {
			probesets.addAll(probesetRepository.findByGene(gene.getUid(), idPlatform));
		}
		
		// ===== Link by nucleotide =====
		probesets.addAll(probesetRepository.findByNucleotideSymbol(symbol, idPlatform));
		probesets.addAll(probesetRepository.findByNucleotideSymbol(originalSymbol, idPlatform));

		
		ajaxForm.setProbesets(probesets);
		
		if (probesets.isEmpty()) {
			return false;
		}
		
		return true;
		
	}
	
	/** =============================================================================== */

}
