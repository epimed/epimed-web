package epimed_web.service.neo4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epimed_web.entity.neo4j.Gene;
import epimed_web.entity.neo4j.Position;
import epimed_web.form.AjaxForm;
import epimed_web.repository.neo4j.PositionRepository;
import epimed_web.service.log.ApplicationLogger;

@Service
public class PositionService extends ApplicationLogger {

	@Autowired
	private PositionRepository positionRepository;

	/** =============================================================================== */

	public boolean populate(AjaxForm ajaxForm) {

		try {
			String parameter = ajaxForm.getParameter();
			String [] parts = parameter.split("_");
			String idAssembly = parts[0];
			String positionType = parts[1];

			for (Gene gene: ajaxForm.getListGenes()) {
				
				List<Position> listPositions = positionRepository.findByIdGeneAndIdAssembly(gene.getUid(), idAssembly);
				
				if (positionType!=null && positionType.equals("unique") && listPositions!=null && !listPositions.isEmpty()) {

					Position unique = positionRepository.findUniqueCanonical(gene.getUid(), idAssembly);
					
					if (unique==null) {
						unique = this.selectUniquePosition(listPositions, gene);
					}
					listPositions.clear();
					listPositions.add(unique);
				}

				gene.setPositions(listPositions);

			}

			return true;
		}

		catch (Exception e) {
			return false;
		}

	}

	/** =============================================================================== */

	public Position selectUniquePosition(List<Position> listPositions, Gene gene) {

		Position unique = listPositions.get(0);
		int i=0;

		while (i<listPositions.size()) {

			Position p = listPositions.get(i);
			logger.debug("{}", p);


			if (gene.getAliases()!=null) {
				String uidRoot = p.getIdPosition().split("\\.")[0]; 
				for (String alias: gene.getAliases()) {
					alias = alias.split("\\.")[0]; 
					if (alias.equals(uidRoot)) {
						return p;
					}
				}
			}

			String strand = p.getStrand();
			if (p.getChrom().equals(unique.getChrom()) 
					&& strand.equals(unique.getStrand()) 
					&& p.getExonCount()>=unique.getExonCount()) {

				boolean forward = strand.equals("+") && p.getTxStart() < unique.getTxStart();
				boolean reverse = strand.equals("-") && p.getTxEnd() > unique.getTxEnd();

				if (forward || reverse || gene.getAliases().contains(p.getUid())) {
					unique = p;
				}
			}

			i++;
		}

		return unique;

	}

	/** =============================================================================== */

}
