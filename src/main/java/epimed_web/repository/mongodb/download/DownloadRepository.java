package epimed_web.repository.mongodb.download;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import epimed_web.entity.mongodb.download.Download;

public interface DownloadRepository extends MongoRepository<Download, String>, DownloadRepositoryCustom {

	public Download findById(String idDownload);
	public List<Download> findAll();
}
