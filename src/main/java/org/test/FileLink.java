package org.test;

import org.springframework.stereotype.Component;

@Component
public class FileLink {
  FileItem[] fileItem;
  public FileItem[] getFileItem() {
	return fileItem;
}
public void setFileItem(FileItem[] fileItem) {
	this.fileItem = fileItem;
}
public FileLink(){}
}
