package org.test;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
@Component
public class LinkFileAndURI {
   public Link getDetail() {
		return detail;
	}
	public void setDetail(Link detail) {
		this.detail = detail;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
   Link detail = null;
   String fileName = null;
}
