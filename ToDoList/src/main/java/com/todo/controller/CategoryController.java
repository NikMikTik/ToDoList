package com.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private IEventService ievent;
	
	@Autowired
	private ICategoryService icategory;

	ResponseDto response = new ResponseDto();

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<CategoryDto> getCategories() {
		return icategory.getAllCategories();
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseDto createCategory(@RequestBody CategoryDto categoryDto) {
		return icategory.createCategory(categoryDto);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseDto deleteCategory(@RequestBody CategoryDto categoryDto) {
		return icategory.deleteCategory(categoryDto);
	}

	@RequestMapping(value = "/{categoryName}", method = RequestMethod.GET)
	public List<EventDto> getEventsForCategory(@PathVariable("categoryName")String categoryName) {
		return icategory.getEventsForCategory(categoryName);
	}
	
	@RequestMapping(value = "/names", method = RequestMethod.GET)
	public List<String> getCategoriesName() {
		return icategory.getAllCategoriesNames();
	}

}
