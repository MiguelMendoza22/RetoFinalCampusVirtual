package com.sofkau.models.rutasdeaprendizaje;

public class ResponseCrearRutaExitosa {
	private int duration;
	private String description;
	private String pathID;
	private String title;
	private String coachID;
	private int statePath;

	public void setDuration(int duration){
		this.duration = duration;
	}

	public int getDuration(){
		return duration;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setPathID(String pathID){
		this.pathID = pathID;
	}

	public String getPathID(){
		return pathID;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setCoachID(String coachID){
		this.coachID = coachID;
	}

	public String getCoachID(){
		return coachID;
	}

	public void setStatePath(int statePath){
		this.statePath = statePath;
	}

	public int getStatePath(){
		return statePath;
	}
}
