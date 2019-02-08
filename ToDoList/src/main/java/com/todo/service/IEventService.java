package com.todo.service;

import java.util.List;

import com.todo.dto.CategoryDto;
import com.todo.dto.EventDto;
import com.todo.dto.ResponseDto;

public interface IEventService {

	List<EventDto> getAllEvents();

	ResponseDto createEvent(CategoryDto categoryDto);

	ResponseDto editEvent(EventDto eventDto);

	ResponseDto deleteEvent(EventDto eventDto);

	ResponseDto completedEvent(EventDto eventDto);
	
	public void statusChangeFromNew();

	public void statusChangeFromRemaining();

	public void eventAlert();
	
	public void deleteCompletedEvent();

	CategoryDto getCategoryEventsById(EventDto eventDto);

}
