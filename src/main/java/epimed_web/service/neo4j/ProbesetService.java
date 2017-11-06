package epimed_web.service.neo4j;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epimed_web.entity.neo4j.Gene;
import epimed_web.entity.neo4j.Probeset;
import epimed_web.form.AjaxForm;
import epimed_web.repository.neo4j.ProbesetRepository;
import epimed_web.service.log.ApplicationLogger;

@Service
public class ProbesetService extends ApplicationLogger {

	@Autowired
	private ProbesetRepository probesetRepository;
	
	/** =============================================================================== */
	
	public boolean populate(AjaxForm ajaxForm) {
		
		String symbol = ajaxForm.getSymbol();
		String idPlatform = ajaxForm.getIdPlatform();
		
		List<Probeset> probesets = new ArrayList<Probeset>(); 
	
		// ===== Link by genes =====
		for (Gene gene: ajaxForm.getListGenes()) {
			List<Probeset> listProbesets = probesetRepository.findByGene(gene.getUid(), idPlatform);
			gene.setProbesets(listProbesets);
		}
		
		
		// ===== Link by nucleotide =====
		List<Probeset> probesetsByNucleotides = probesetRepository.findByNucleotideSymbol(symbol, idPlatform);
		probesets.addAll(probesetsByNucleotides);
		
		ajaxForm.setProbesets(probesets);
		
		if (probesets.isEmpty()) {
			return false;
		}
		
		return true;
		
	}
	
	/** =============================================================================== */

}
