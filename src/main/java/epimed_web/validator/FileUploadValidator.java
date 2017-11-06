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
package epimed_web.validator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import epimed_web.entity.pojo.FileUpload;
import epimed_web.repository.mongodb.experiments.SampleRepository;
import epimed_web.service.log.ApplicationLogger;
import epimed_web.service.util.FileService;
import epimed_web.service.util.FormatService;

@Component
public class FileUploadValidator extends ApplicationLogger implements Validator {

	private File file;
	private List<String> header = new ArrayList<String>();
	private List<Object> data = new ArrayList<Object>();
	private List<String> normalizedHeader = new ArrayList<String>();
	private Map<String, String> mapHeaders = new HashMap<String, String>();
	private String separator = ";";

	@Autowired
	FileService fileService;

	@Autowired
	FormatService formatService;

	@Autowired
	private SampleRepository sampleRepository;


	public List<String> getHeader() {
		return header;
	}

	public File getFile() {
		return file;
	}

	public List<Object> getData() {
		return data;
	}

	public List<String> getNormalizedHeader() {
		return normalizedHeader;
	}

	public Map<String, String> getMapHeaders() {
		return mapHeaders;
	}


	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	/** =========================================================================================  */

	public boolean supports(Class<?> clazz) {
		return FileUpload.class.isAssignableFrom(clazz);
	}

	/** =========================================================================================  */


	public void validate(Object target, Errors errors) {

		FileUpload fileUpload = (FileUpload) target;


		try {

			// === Check if the file exist === 
			checkFileExist(fileUpload, errors); 

			// === Check the file format ===
			checkFileFormat (fileUpload, errors);

			// === Check header ===
			checkFileHeader(fileUpload, errors, fileService);

			// === Check data ===
			checkFileData(fileUpload, errors, fileService, formatService);

		} 
		catch (FileUploadException fue) {
			// Errors found, nothing to do
		}
		catch (Exception e) {
			errors.rejectValue("file", "error.loadFile", "Error: " + e.toString());
			e.printStackTrace();
		} 

	}



	/** =========================================================================================  */

	public void checkFileData(FileUpload fileUpload, Errors errors, FileService fileService, FormatService formatService) throws FileUploadException, Exception {

		this.normalizedHeader.clear();
		this.mapHeaders.clear();

		for (String key : header) {
			String normalizedKey = formatService.normalize(key);
			this.normalizedHeader.add(normalizedKey);
			if (!normalizedKey.equals("id_sample")) {
				this.mapHeaders.put(normalizedKey, key);
			}
		}

		Integer columnIndex = header.indexOf("id_sample");
		this.data = fileService.getCsvData(file, this.separator);
		String [] listIdSamples = fileService.extractColumnCsv(columnIndex, data);
		List<String> mongoHeader = formatService.extractHeader (sampleRepository.getSamples(listIdSamples, "exp_group"), "exp_group");


		logger.debug("File header {}", header);
		logger.debug("Normalized header {}", normalizedHeader);
		logger.debug("List id samples {}", Arrays.toString(listIdSamples));
		logger.debug("Mongo header {}", mongoHeader);

		normalizedHeader.removeAll(mongoHeader);

		if (normalizedHeader==null || normalizedHeader.isEmpty()) {
			errors.rejectValue("file", "error.annotationsNotDetected", "We didn't find any new annotation in the uploaded file. No annotation has been added to the database! Please check the content of your file and try again.");
			throw new FileUploadException();
		}

		if (mongoHeader==null || mongoHeader.isEmpty()) {
			errors.rejectValue("file", "error.samplesNotDetected", "The database doesn't contain samples listed in the uploaded file. No annotation has been added to the database!");
			throw new FileUploadException();
		}

	}

	/** =========================================================================================  */

	public void checkFileHeader(FileUpload fileUpload, Errors errors, FileService fileService) throws FileUploadException, Exception {

		this.file = fileService.convertToFile(fileUpload.getFile());

		// === Define separator ===

		String sep = fileService.guessSeparator(file);
		if (sep!=null) {
			this.separator = sep;
		}


		this.header = fileService.getCsvHeader(file, this.separator);

		if (header==null) {
			errors.rejectValue("file", "error.empty", "The file seems to be empty. Please check the content and field separators.");
			throw new FileUploadException();
		}

		if (!header.contains("id_sample")) {
			errors.rejectValue("file", "error.idSampleNotDetected", "The file doesn't contain a mandatory column \"id_sample\".");
			throw new FileUploadException();
		}

		if (header!=null && header.size()<2) {
			errors.rejectValue("file", "error.idSampleNotDetected", "The file must contain at least 2 columns. "
					+ "Number of detected columns: " 
					+ header.size() + " " + header 
					+ ". Please check the content and field separators.");
			throw new FileUploadException();
		}


	}

	/** ========================================================================================= 
	 * @throws FileUploadException */

	public void checkFileExist (FileUpload fileUpload, Errors errors) throws FileUploadException {

		if (fileUpload.getFile()==null || fileUpload.getFile().getSize()==0) {
			errors.rejectValue("file", "error.fileNotSelected", "Please select a file.");
			throw new FileUploadException();
		}

	}

	/** ========================================================================================= 
	 * @throws FileUploadException */

	public void checkFileFormat (FileUpload fileUpload, Errors errors) throws FileUploadException {

		if (!fileUpload.getFile().getContentType().equals("application/vnd.ms-excel")) {
			errors.rejectValue("file", "error.badFormat", "CSV format is expected.");
			throw new FileUploadException();
		}

	}

	/** ========================================================================================= */

}
