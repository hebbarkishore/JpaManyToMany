package com.registration.dao;

import java.util.List;

import com.registration.entity.CourseEntity;
import com.registration.entity.StudentCourseRegEntity;
import com.registration.entity.StudentEntity;

public interface RegistrationRepository {

	public Long saveStudents(List<StudentEntity> students);
	public Long saveCourses(List<CourseEntity> courses);

	public List<StudentEntity> getAllStudent();
	public List<CourseEntity> getAllCourses();
	public List<StudentCourseRegEntity> getAllStudentCourseRepo();
	
	public Integer getStudentIdByNam(String nam);	
	public int deleteStudentCourseReg(Integer studentId);	
	public int deleteStudent(Integer studentId);
	
	public List<String> getStudentNamByCourse(String course);
	public List<String> getStudentNamByNoCourse(String course);
}
