package azl.quizx.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A list of quiz downloadable versions. 
 */
@XmlRootElement(name="versionList")
public class DownloadableVersionDTOList {
	private List<DownloadableVersionDTO> data;

	@XmlElement(required = false)
	public List<DownloadableVersionDTO> getData() {
		return data;
	}

	public void setData(List<DownloadableVersionDTO> data) {
		this.data = data;
	}	
}