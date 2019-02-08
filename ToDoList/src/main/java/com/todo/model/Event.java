package com.todo.model;

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

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Event_Table")
public class Event {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int eventId;
	
	@NotBlank
	private String topic;
	
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;
	private LocalDateTime eventDateTime;
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime createdDateTime;
	
	private LocalDateTime completedDateTime;

	
	@NotBlank
	private String summary;
	
	@NotBlank
	private String importance;
	
	@NotBlank
	private String status="New";
	
	
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
	
	
	public LocalDateTime getCompletedDateTime() {
		return completedDateTime;
	}
	public void setCompletedDateTime(LocalDateTime completedDateTime) {
		this.completedDateTime = completedDateTime;
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
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	

	
	public Event(int eventId, @NotBlank String topic, Category category, LocalDateTime eventDateTime,
			LocalDateTime createdDateTime, LocalDateTime completedDateTime, @NotBlank String summary,
			@NotBlank String importance, @NotBlank String status) {
		super();
		this.eventId = eventId;
		this.topic = topic;
		this.category = category;
		this.eventDateTime = eventDateTime;
		this.createdDateTime = createdDateTime;
		this.completedDateTime = completedDateTime;
		this.summary = summary;
		this.importance = importance;
		this.status = status;
	}
	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", topic=" + topic + ", eventDateTime="
				+ eventDateTime + ", createdDateTime=" + createdDateTime + ", completedDateTime=" + completedDateTime
				+ ", summary=" + summary + ", importance=" + importance + ", status=" + status + "]";
	}
	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public LocalDateTime getEventDateTime() {
		return eventDateTime;
	}
	public void setEventDateTime(LocalDateTime eventDateTime) {
		this.eventDateTime = eventDateTime;
	}
	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
