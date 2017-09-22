package epimed_web.entity.pojo;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {

	private MultipartFile file;

	public FileUpload() {
		super();
	}

	public FileUpload(MultipartFile file) {
		super();
		this.file = file;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public String toString() {
		return "UploadedFile [file=" + file.getOriginalFilename() +  ", name=" + file.getName() + ", size=" + file.getSize() + ", contentType=" + file.getContentType() + "]";
	}

}
