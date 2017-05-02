package org.test;

import org.springframework.stereotype.Component;

@Component
public class FileItem {
	public FileItem(){}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public byte[] getArr() {
		return arr;
	}
	public void setArr(byte[] arr) {
		this.arr = arr;
	}
	Long id;
	String name;
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	byte[] arr;    
}
