package org.model;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
public class HiringProcessInput {
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
   public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public String getUnion() {
		return union;
	}
	public void setUnion(String union) {
		this.union = union;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
   Double salary;
   String union;
   String location;
   String hiringCriteria;
   public String getHiringCriteria() {
	return hiringCriteria;
}

public void setHiringCriteria(String hiringCriteria) {
	this.hiringCriteria = hiringCriteria;
}
private long id;
}
