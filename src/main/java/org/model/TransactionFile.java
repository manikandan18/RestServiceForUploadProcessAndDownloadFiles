package org.model;


import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
public class TransactionFile {

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
   public String getInput_name() {
		return input_name;
	}
   
	public void setInput_name(String input_name) {
		this.input_name = input_name;
	}
	public String getOutput_name() {
		return output_name;
	}
	public void setOutput_name(String output_name) {
		this.output_name = output_name;
	}
	@NotNull	
    String input_name;
	@NotNull
    String output_name;
	@NotNull
	String transformType;
	public String getTransformType() {
		return transformType;
	}

	public void setTransformType(String transformType) {
		this.transformType = transformType;
	}
	private long id;
}

