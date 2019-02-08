package com.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.model.Category;
import com.todo.model.Event;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Category findByCategoryId(int categoryId);
	Category findByCategoryName(String categoryName);
	List<Category> findByEventCategory(String categoryName);
}
