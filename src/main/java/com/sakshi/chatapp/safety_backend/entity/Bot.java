package com.sakshi.chatapp.safety_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Bot {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String personaDescription;
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
	public String getPersonaDescription() {
		return personaDescription;
	}
	public void setPersonaDescription(String personaDescription) {
		this.personaDescription = personaDescription;
	}
    
}