package com.todo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todo.model.Category;

public class EventDto {

	private int eventId;
	@NotBlank
	private String topic;
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="eventId")
	private Category categoryDto;
	private LocalDateTime eventDateTime;
	
	public EventDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	private LocalDateTime completedDateTime;

	public LocalDateTime getEventDateTime() {
		return eventDateTime;
	}
	public void setEventDateTime(LocalDateTime eventDateTime) {
		this.eventDateTime = eventDateTime;
	}
	private LocalDateTime createdDateTime;
	@NotBlank
	private String summary;
	@NotBlank
	private String importance;
	@NotBlank
	private String status="New";
	
	
	public LocalDateTime getCompletedDateTime() {
		return completedDateTime;
	}
	public void setCompletedDateTime(LocalDateTime completedDateTime) {
		this.completedDateTime = completedDateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public Category getCategoryDto() {
		return categoryDto;
	}
	public void setCategoryDto(Category categoryDto) {
		this.categoryDto = categoryDto;
	}
	
	
	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getImportance() {
		return importance;
	}
	public void setImportance(String importance) {
		this.importance = importance;
	}
//	public EventDto() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	
	
	
	
}
