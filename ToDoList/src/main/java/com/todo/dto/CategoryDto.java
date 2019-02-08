package com.todo.dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

public class CategoryDto {

	public int categoryId;
	@NotBlank
	public String categoryName;
	
	@OneToMany(mappedBy="eventDto")
	private Collection<EventDto> eventDto=new ArrayList<>();
	private int categoryTotalEvents=0;

	private int completedEvents=0;
	
	private int remainingEvents=0;
	
	public int getCompletedEvents() {
		return completedEvents;
	}
	
	public void setCompletedEvents(int completedEvents) {
		this.completedEvents = completedEvents;
	}
	public int getRemainingEvents() {
		return remainingEvents;
	}
	public void setRemainingEvents(int remainingEvents) {
		this.remainingEvents = remainingEvents;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Collection<EventDto> getEventDto() {
		return eventDto;
	}
	public void setEventDto(Collection<EventDto> eventDto) {
		this.eventDto = eventDto;
	}
	public int getCategoryTotalEvents() {
		return categoryTotalEvents;
	}
	public void setCategoryTotalEvents(int categoryTotalEvents) {
		this.categoryTotalEvents = categoryTotalEvents;
	}
	
	
}
