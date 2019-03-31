package com.registration.entity;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "student")
public class StudentEntity {
	
	
	public StudentEntity() {
		super();
	}

	public StudentEntity(String name, int score, StudentCourseRegEntity... studentCourses) {
        this.name = name;
        for(StudentCourseRegEntity studentCourse : studentCourses) {
        	studentCourse.setStudent(this);
        	studentCourse.setScore(score);
        }
        this.registrations = Stream.of(studentCourses).collect(Collectors.toSet());
    }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String name;
	
	/*
	 * Each student can have multiple record in student_course_registration table
	 * And in order to use the score record of each course went with this approach.
	 * otherwise @ManyToMany and @JoinTable with the StudentCourseRegEntity holds good. 
	 */
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<StudentCourseRegEntity> registrations;

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
		StudentEntity other = (StudentEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return "StudentEntity [id=" + id + ", name=" + name +"]";
	}	
}