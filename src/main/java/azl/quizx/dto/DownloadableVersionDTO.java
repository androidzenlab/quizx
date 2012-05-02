package azl.quizx.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is used by WSController for downloadable quiz version inquiry from
 * Mobile client.
 */
@XmlRootElement(name="downloadable")
public class DownloadableVersionDTO {
	//version available for client to download
	private int version;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}	
}