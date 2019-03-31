package com.registration.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
//import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name = "student_course_registration")
public class StudentCourseRegEntity implements Serializable{

	private static final long serialVersionUID = 1L;


	public StudentCourseRegEntity(CourseEntity course) {
		this.course = course;
	}
	
	/*
	 * Map the primary key of student record here
	 */
	@Id
    @ManyToOne
    @JoinColumn
    private StudentEntity student;

	/*
	 * Map the primary key of course record here
	 */
	@Id
    @ManyToOne
    @JoinColumn
    private CourseEntity course;

    private int score;


	public StudentCourseRegEntity() {
    }


	public StudentEntity getStudent() {
		return student;
	}


	public void setStudent(StudentEntity student) {
		this.student = student;
	}


	public CourseEntity getCourse() {
		return course;
	}


	public void setCourse(CourseEntity course) {
		this.course = course;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}

	 @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentCourseRegEntity)) return false;
        StudentCourseRegEntity that = (StudentCourseRegEntity) o;
        return Objects.equals(student.getName(), that.student.getName()) &&
                Objects.equals(course.getName(), that.course.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(student.getName(), course.getName());
    }


	@Override
	public String toString() {
		return "StudentCourseRegEntity [student=" + student + ", course=" + course + ", score=" + score + "]";
	} 

}	