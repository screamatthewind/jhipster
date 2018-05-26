package com.therealdanvega.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate dueDate;
    private Boolean completed;

    public Task() {

    }

	public Task(Long id, String name, LocalDate dueDate, Boolean completed) {
		super();
		this.id = id;
		this.name = name;
		this.dueDate = dueDate;
		this.completed = completed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

}
