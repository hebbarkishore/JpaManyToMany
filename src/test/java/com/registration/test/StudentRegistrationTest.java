package com.registration.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.registration.dao.RegistrationRepository;
import com.registration.entity.CourseEntity;
import com.registration.entity.StudentCourseRegEntity;
import com.registration.entity.StudentEntity;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;

import static org.hamcrest.CoreMatchers.*;

@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentRegistrationTest {
	
	private static boolean isInitDone = false;

	@Autowired
	RegistrationRepository registrationDao;

	/**
	 * This is to setup the record first time when we run the Test file.
	 * It creates the record in course, student and student_course_registration tables.
	 */
	@Before
	public void setUp() {
		if( isInitDone ) {
			return;
		}
		/*
		 * First make sure to have the courses record created in the course table
		 */
		CourseEntity courseA = new CourseEntity("JAVA");
		CourseEntity courseB = new CourseEntity("SPRING");
		CourseEntity courseC = new CourseEntity("C++");

		/*
		 * Save the courses
		 */
		registrationDao.saveCourses(Arrays.asList(courseA, courseB, courseC));
		
		/*
		 * Save all the student records along with the studentCourseRegistration records as well. 
		 * Here we save the score of each student for that particular course.
		 */
		registrationDao.saveStudents(Arrays.asList(
				new StudentEntity("StudentA", 8, new StudentCourseRegEntity(courseA)),
				new StudentEntity("StudentB", 10, new StudentCourseRegEntity(courseB)),
				new StudentEntity("StudentC", 9, new StudentCourseRegEntity(courseC)),
				new StudentEntity("StudentD", 10, new StudentCourseRegEntity(courseC))
				));		
		isInitDone = true;
	}

	
	/**
	 * This is to test the whether Table has the expected record for the StudentA.
	 */
	@Test
	public void addStudentCourseTest() {		
		assertThat(registrationDao.getAllStudent().get(0).getName(), is("StudentA"));
	}
	
	/**
	 * This is to delete the student record.
	 * Here we won't be able to delete the record directly from the student table since it has the dependency in studentcoursereg table.
	 * So we have to remove all the related records from studentcourse table then we delete it from the student table
	 */
	@Test
	public void deleteStudentTest() {
		Integer id = registrationDao.getStudentIdByNam("StudentB");
		registrationDao.deleteStudentCourseReg(id);		
		assertThat(registrationDao.deleteStudent(id), is(1));
	}
	
	/**
	 * This to find the Students who enrolled for the input course.
	 */
	@Test
	public void getStudentNamByCourseTest() {
		List<String> students = registrationDao.getStudentNamByCourse("C++");
		assertThat(students, is(Arrays.asList("StudentC", "StudentD")));
	}
	
	/**
	 * This is to find the students who has not enrolled to the input course.
	 */
	@Test
	public void getStudentNamByNOCourseTest() {
		List<String> students = registrationDao.getStudentNamByNoCourse("SPRING");
		assertThat(students, is(Arrays.asList("StudentA", "StudentC", "StudentD")));
	}
	
}
