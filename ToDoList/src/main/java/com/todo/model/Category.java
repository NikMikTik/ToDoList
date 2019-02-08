package com.todo.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="Category")
public class Category {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int categoryId;
	
	@NotBlank
	public String categoryName;
	
	@OneToMany(mappedBy="category")
	private Collection<Event> event=new ArrayList<>();
	
	private int categoryTotalEvents=0;
	
	private int completedEvents=0;
	
	private int remainingEvents=0;
	
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
	public Collection<Event> getEvent() {
		return event;
	}
	public void setEvent(Collection<Event> event) {
		this.event = event;
	}
	
	public int getCategoryTotalEvents() {
		return categoryTotalEvents;
	}
	public void setCategoryTotalEvents(int categoryTotalEvents) {
		this.categoryTotalEvents = categoryTotalEvents;
	}
	
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
	
	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + ", event=" + event
				+ ", categoryTotalEvents=" + categoryTotalEvents + ", completedEvents=" + completedEvents
				+ ", remainingEvents=" + remainingEvents + "]";
	}
	public Category(int categoryId, @NotBlank String categoryName, Collection<Event> event, int categoryTotalEvents,
			int completedEvents, int remainingEvents) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.event = event;
		this.categoryTotalEvents = categoryTotalEvents;
		this.completedEvents = completedEvents;
		this.remainingEvents = remainingEvents;
	}
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
