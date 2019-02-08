package com.todo.service;

import java.util.List;

import com.todo.dto.CategoryDto;
import com.todo.dto.EventDto;
import com.todo.dto.ResponseDto;

public interface ICategoryService {

	List<CategoryDto> getAllCategories();

	ResponseDto createCategory(CategoryDto categoryDto);

	ResponseDto deleteCategory(CategoryDto categoryDto);

	List<EventDto> getEventsForCategory(String categoryName);

	List<String> getAllCategoriesNames();



}
