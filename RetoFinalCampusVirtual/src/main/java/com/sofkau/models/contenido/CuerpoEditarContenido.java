package com.sofkau.models.contenido;

public class CuerpoEditarContenido{
	private int duration;
	private int stateContent;
	private String description;
	private String title;
	private int type;
	private String courseID;

	public void setDuration(int duration){
		this.duration = duration;
	}

	public int getDuration(){
		return duration;
	}

	public void setStateContent(int stateContent){
		this.stateContent = stateContent;
	}

	public int getStateContent(){
		return stateContent;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public void setCourseID(String courseID){
		this.courseID = courseID;
	}

	public String getCourseID(){
		return courseID;
	}
}
