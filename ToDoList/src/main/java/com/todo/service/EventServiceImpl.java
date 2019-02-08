package com.todo.service;

import static org.mockito.Mockito.after;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.todo.dto.CategoryDto;
import com.todo.dto.EventDto;
import com.todo.dto.ResponseDto;
import com.todo.model.Category;
import com.todo.model.Event;
import com.todo.repository.CategoryRepository;
import com.todo.repository.EventRepository;

import javassist.compiler.ast.Symbol;
import org.springframework.data.domain.Sort;

@Service
@Transactional
public class EventServiceImpl implements IEventService {

	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

	ModelMapper modelMapper = new ModelMapper();
	ResponseDto response = new ResponseDto();

	@Override
	public ResponseDto createEvent(CategoryDto categoryDto) {
		if (categoryRepository.findByCategoryName(categoryDto.getCategoryName()) == null) {
			response.setCode(HttpStatus.FORBIDDEN.value());
			response.setMessage("Category Does not Exist");
			return response;
		}
		Category category = categoryRepository.findByCategoryName(categoryDto.getCategoryName());
		category.setCategoryTotalEvents(category.getCategoryTotalEvents() + 1);
		category.setRemainingEvents(category.getRemainingEvents() + 1);
		Collection<EventDto> eventDtoList = categoryDto.getEventDto();
		Collection<Event> eventList = new ArrayList<>();

		Event event = new Event();
		for (EventDto eventDto : eventDtoList) {
			event = modelMapper.map(eventDto, Event.class);
			event.setCreatedDateTime(LocalDateTime.now());
			eventList.add(event);
			event.setCategory(category);
		}
		category.setEvent(eventList);

		if (eventRepository.save(event) != null) {
			categoryRepository.save(category);
			response.setCode(HttpStatus.OK.value());
			response.setMessage(event.getImportance() + " To Do Event: " + event.getTopic() + " Created Successfully");
			return response;
		} else {
			response.setCode(HttpStatus.FORBIDDEN.value());
			response.setMessage("Event not created.. Enter Correct Details");
			return response;
		}

	}

	@Override
	public List<EventDto> getAllEvents() {
		// Direction direction;
		EventDto eventDto;
		List<Event> eventList = eventRepository.findAllByOrderByCreatedDateTimeDesc();
		List<EventDto> eventDtoList = new ArrayList<>();
		for (Event event : eventList) {
			eventDto = modelMapper.map(event, EventDto.class);
			eventDtoList.add(eventDto);
		}
		return eventDtoList;
	}

	@Override
	public ResponseDto editEvent(EventDto eventDto) {
		Event event = new Event();
		if (eventRepository.findByEventId(eventDto.getEventId()) != null) {
			event = eventRepository.findByEventId(eventDto.getEventId());
			event.setTopic(eventDto.getTopic());
			event.setEventDateTime(eventDto.getEventDateTime());
			event.setImportance(eventDto.getImportance());
			event.setSummary(eventDto.getSummary());
			response.setCode(HttpStatus.OK.value());
			response.setMessage("To Do Event: " + event.getTopic() + " edited Successfully");
			return response;
		} else {
			response.setCode(HttpStatus.FORBIDDEN.value());
			response.setMessage("Event not created.. Enter Correct Details");
			return response;
		}

	}

	@Override
	public ResponseDto deleteEvent(EventDto eventDto) {
		Event event;
		if (eventRepository.findByEventId(eventDto.getEventId()) != null) {
			event = eventRepository.findByEventId(eventDto.getEventId());
			Category category = event.getCategory();
			category.setCategoryTotalEvents(category.getCategoryTotalEvents() - 1);
			if (event.getStatus().equals("Completed"))
				category.setCompletedEvents(category.getCompletedEvents() - 1);
			else
				category.setRemainingEvents(category.getRemainingEvents() - 1);
			categoryRepository.save(category);
			eventRepository.delete(event);
			response.setCode(HttpStatus.OK.value());
			response.setMessage(event.getTopic() + " Event deleted Successfully");
			logger.info(event.getTopic() + " Event deleted Successfully");
			return response;
		} else {
			response.setCode(HttpStatus.FORBIDDEN.value());
			response.setMessage("Event does not Exist.. Cannot delete this event..");
			return response;
		}
	}

	@Override
	public ResponseDto completedEvent(EventDto eventDto) {
		Event event;
		Category category;
		if (eventRepository.findByEventId(eventDto.getEventId()) != null) {
			event = eventRepository.findByEventId(eventDto.getEventId());
			event.setStatus("Completed");
			event.setCompletedDateTime(LocalDateTime.now());
			category = event.getCategory();
			category.setCompletedEvents(category.getCompletedEvents() + 1);
			category.setRemainingEvents(category.getRemainingEvents() - 1);
			eventRepository.save(event);
			categoryRepository.save(category);
			response.setCode(HttpStatus.OK.value());
			response.setMessage(event.getTopic() + " Event Completed Successfully");
			logger.info(event.getTopic() + " Event Completed Successfully");
			return response;
		} else {
			response.setCode(HttpStatus.FORBIDDEN.value());
			response.setMessage("This event cannot be marked as completed..");
			return response;
		}
	}

	@Override
	public void statusChangeFromNew() {
		List<Event> eventList = eventRepository.findByStatus("New");
		for (Event event : eventList) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime twoMinBehind = now.minusMinutes(2);
			LocalDateTime createdDateTime = event.getCreatedDateTime();
			if ((twoMinBehind).compareTo(createdDateTime) > 0) {
				event.setStatus("Remaining");
				logger.info("Status of " + event.getTopic() + " changed to Remaining");
			}
		}
	}

	@Override
	public void statusChangeFromRemaining() {
		List<Event> eventList = eventRepository.findAll();
		for (Event event : eventList) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime eventDateTime = event.getEventDateTime();
			if ((!event.getStatus().equals("Completed")) && (!event.getStatus().equals("Overdue"))
					&& ((eventDateTime).compareTo(now) < 0)) {
				event.setStatus("Overdue");
				logger.info("Status of " + event.getTopic() + " changed to Overdue");

			}
		}
	}

	@Override
	public void eventAlert() {
		List<Event> eventList = eventRepository.findAll();
		for (Event event : eventList) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime eventDateTime = event.getEventDateTime();
			// LocalDateTime nowAdd = eventDateTime.plusSeconds(30);
			// LocalDateTime nowMinus = eventDateTime.minusSeconds(30);
			if ((!event.getStatus().equals("Completed"))
					&& (now.toLocalDate().compareTo(eventDateTime.toLocalDate()) == 0)
					&& (now.getHour() == eventDateTime.getHour()) && (now.getMinute() == eventDateTime.getMinute())) {
				logger.info("ALERT ALERT ALERT..!!");
			}

		}

	}

	@Override
	public void deleteCompletedEvent() {
		List<Event> eventList = eventRepository.findByStatus("Completed");
		for (Event event : eventList) {
			LocalDateTime afterEvent = event.getCompletedDateTime().plusMinutes(1);
			System.out.println(afterEvent);
			LocalDateTime now = LocalDateTime.now();
			if ((now.toLocalDate().compareTo(afterEvent.toLocalDate()) == 0) && (now.getHour() == afterEvent.getHour())
					&& (now.getMinute() == afterEvent.getMinute())) {
				EventDto eventDto = modelMapper.map(event, EventDto.class);
				// Category category=event.getCategory();
				// category.setCompletedEvents(category.getCompletedEvents()-1);
				deleteEvent(eventDto);
			}
		}

	}

	@Override
	public CategoryDto getCategoryEventsById(EventDto eventDto) {
		List<EventDto> eventDtoList=new ArrayList<>();
	Event event=eventRepository.findByEventId(eventDto.getEventId());
	eventDto=modelMapper.map(event, EventDto.class);
	eventDtoList.add(eventDto);
	Category category=event.getCategory();
	CategoryDto categoryDto=modelMapper.map(category, CategoryDto.class);
	categoryDto.setEventDto(eventDtoList);
	System.out.println(categoryDto.getCategoryName());
		return categoryDto;
	}

}
