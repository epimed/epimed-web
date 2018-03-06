package epimed_web.controller.download;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import epimed_web.entity.mongodb.download.Download;
import epimed_web.repository.mongodb.download.DownloadRepository;
import epimed_web.service.log.ApplicationLogger;

@Controller
public class DownloadController  extends ApplicationLogger {

	@Autowired
	private DownloadRepository downloadRepository;
	
	private Map<String, List<Download>> mapDownload = new TreeMap<String, List<Download>>();
	
	/** ====================================================================================== */

	@RequestMapping(value = "download", method = {RequestMethod.GET, RequestMethod.POST})
	public String displayAllAnnotations(Model model) throws IOException {
		
		List<Download> listDownloads = downloadRepository.findAll();
		mapDownload.clear();
		this.addToMap(mapDownload, listDownloads);
		model.addAttribute("mapDownload", mapDownload);
		return "download/list";
	}
	
	/** ====================================================================================== */
	
	private void addToMap(Map<String, List<Download>> mapDownload, List<Download> listDownloads) {
		for (Download download : listDownloads) {
			String key = download.getCategory();
			List<Download> list = mapDownload.get(key);
			if (list==null) {
				list = new ArrayList<Download>();
			}
			if (!list.contains(download)) {
				list.add(download);
			}
			mapDownload.put(key, list);
		}
	}
	
	/** ====================================================================================== */
	
}
