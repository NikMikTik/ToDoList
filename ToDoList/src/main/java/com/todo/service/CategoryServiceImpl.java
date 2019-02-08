package com.todo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.todo.dto.CategoryDto;
import com.todo.dto.EventDto;
import com.todo.dto.ResponseDto;
import com.todo.exception.ToDoException;
import com.todo.model.Category;
import com.todo.model.Event;
import com.todo.repository.CategoryRepository;
import com.todo.repository.EventRepository;

@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {

	ModelMapper modelMapper = new ModelMapper();
	ResponseDto response = new ResponseDto();

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	EventRepository eventRepository;

//	@Override
//	public List<CategoryDto> getAllCategories() {
//		List<Category> categoryList = categoryRepository.findAll();
//		CategoryDto categoryDto = null;
//		List<CategoryDto> categoryDtoList = new ArrayList<>();
//		for (Category category : categoryList) {
//			categoryDto= modelMapper.map(category, CategoryDto.class);
//			List<Event> eventList = eventRepository.findByCategory(category);
////			System.out.println(eventList);
//			for (Event event : eventList) {
//				List<EventDto> eventDtoList = new ArrayList<>();
//
////				System.out.println(event.getImportance());
//				EventDto eventDto=modelMapper.map(event, EventDto.class);
////				System.out.println(eventDto.getImportance());
//				eventDtoList.add(eventDto);
//
//				categoryDto.setEventDto(eventDtoList);
//				
//			}
//
//		}			categoryDtoList.add(categoryDto);
//
//		return categoryDtoList;
//	}

	@Override
	public ResponseDto createCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		if (categoryRepository.findByCategoryName(categoryDto.getCategoryName()) != null) {
			response.setCode(HttpStatus.FORBIDDEN.value());
			response.setMessage("Category Already Exists.. Enter Correct Details");
			return response;
		}
		if (categoryRepository.save(category) != null) {
			response.setCode(HttpStatus.OK.value());
			response.setMessage("New Category: " + category.getCategoryName() + " Created Successfully");
			return response;
		} else {
			response.setCode(HttpStatus.FORBIDDEN.value());
			response.setMessage("Category not created.. Enter Correct Details");
			return response;
		}
	}

	@Override
	public ResponseDto deleteCategory(CategoryDto categoryDto) {
		if (categoryRepository.findByCategoryId(categoryDto.getCategoryId()) != null) {
			Category category = categoryRepository.findByCategoryId(categoryDto.getCategoryId());
			if (category.getCategoryTotalEvents() > 0) {
				response.setCode(HttpStatus.FORBIDDEN.value());
				response.setMessage("Category cannot be deleted.. It contains Events");
				return response;

			} else {
				categoryRepository.delete(category);
				response.setCode(HttpStatus.OK.value());
				response.setMessage("Category deleted Successfully");
				return response;

			}
		} else {
			response.setCode(HttpStatus.FORBIDDEN.value());
			response.setMessage("Category does not exist");
			return response;

		}

	}

	@Override
	public List<EventDto> getEventsForCategory(String categoryName) {
		List<Event> eventList = eventRepository.findAll();
		EventDto eventDto = new EventDto();
		List<EventDto> eventDtoList = new ArrayList<>();
		for (Event event : eventList) {
			if (event.getCategory().getCategoryName().equals(categoryName)) {
				eventDto = modelMapper.map(event, EventDto.class);
				eventDtoList.add(eventDto);

			}
		}
		return eventDtoList;

	}

	@Override
	public List<String> getAllCategoriesNames() {
		List<Category> categoryList = categoryRepository.findAll();
		List<String> categoryNameList = new ArrayList<>();
		for (Category category : categoryList) {
			String categoryName = category.getCategoryName();
			categoryNameList.add(categoryName);
		}
		return categoryNameList;
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categoryList=categoryRepository.findAll();
		List<CategoryDto> catgeoryDtoList = new ArrayList<>();


		for (Category category : categoryList) {
			List<EventDto> eventDtoList = new ArrayList<>();
			CategoryDto categoryDto = null;
			List<Event> eventList=eventRepository.findByCategory(category);
			category.setEvent(eventList);
			
//		System.out.println(eventList);
			for (Event event : eventList) {
				EventDto eventDto =modelMapper.map(event, EventDto.class);
				eventDtoList.add(eventDto);
				
			}
			categoryDto=modelMapper.map(category, CategoryDto.class);
			categoryDto.setEventDto(eventDtoList);
			catgeoryDtoList.add(categoryDto);
		}
		return catgeoryDtoList;
	}

}
