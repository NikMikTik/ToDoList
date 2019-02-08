package com.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todo.dto.CategoryDto;
import com.todo.dto.EventDto;
import com.todo.dto.ResponseDto;
import com.todo.service.ICategoryService;
import com.todo.service.IEventService;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/event")
public class EventController {

	@Autowired
	private IEventService ievent;
	
	@Autowired
	private ICategoryService icategory;

	ResponseDto response = new ResponseDto();

//	@RequestMapping(value = "/categories", method = RequestMethod.GET)
//	public List<CategoryDto> getCategories() {
//		return icategory.getAllCategories();
//	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<EventDto> getEvents() {
		return ievent.getAllEvents();
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseDto createEvent(@RequestBody CategoryDto categoryDto) {
		return ievent.createEvent(categoryDto);

	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseDto editEvent(@RequestBody EventDto eventDto) {
		return ievent.editEvent(eventDto);

	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseDto deleteEvent(@RequestBody EventDto eventDto) {
		return ievent.deleteEvent(eventDto);

	}
	@RequestMapping(value = "/completed", method = RequestMethod.POST)
	public ResponseDto completedEvent(@RequestBody EventDto eventDto) {
		return ievent.completedEvent(eventDto);

	}
	
	@RequestMapping(value = "/edit/byId", method = RequestMethod.POST)
	public CategoryDto getCategoryEventsById(@RequestBody EventDto eventDto) {
		System.out.println(eventDto.getEventId());
		return ievent.getCategoryEventsById(eventDto);
	}

}
