package com.todo.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.model.Category;
import com.todo.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
	List<Event> findAllByOrderByCreatedDateTimeDesc();

	Event findByEventId(int eventId);
	List<Event> findByCategory(Category category);
	List<Event> findByStatus(String status);
}
