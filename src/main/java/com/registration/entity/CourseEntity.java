package com.registration.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class CourseEntity {
	public CourseEntity() {
		super();
	}
	
	public CourseEntity(String name) {
        this.name = name;
    }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	
	/*
	 * Each course can have multiple record in student_course_registration table
	 */
	@OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
	private Set<StudentCourseRegEntity> registrations  = new HashSet<>();;

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

	public Set<StudentCourseRegEntity> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(Set<StudentCourseRegEntity> registrations) {
		this.registrations = registrations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((registrations == null) ? 0 : registrations.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseEntity other = (CourseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (registrations == null) {
			if (other.registrations != null)
				return false;
		} else if (!registrations.equals(other.registrations))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CourseEntity [id=" + id + ", name=" + name + "]";
	}
	
}
