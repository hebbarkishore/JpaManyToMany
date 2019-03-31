package com.registration.dao;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.registration.entity.CourseEntity;
import com.registration.entity.StudentCourseRegEntity;
import com.registration.entity.StudentEntity;

@Repository
@Transactional
public class RegistrationRepositoryImpl implements RegistrationRepository{

	@PersistenceContext
	private EntityManager em;

	/**
	 * It creates the records for students and student_course_registrations
	 * @param students
	 * @return
	 */
	public Long saveStudents(List<StudentEntity> students) {
		if( students == null || students.size() <=0 ) return null;
		long insrtCnt = 0;		
		for( StudentEntity student : students ) {
			em.persist(student);	
			insrtCnt++;
		}
		return insrtCnt;
	}

	public List<StudentEntity> getAllStudent() {
		return em.createQuery("SELECT c FROM StudentEntity c", StudentEntity.class).getResultList();
	}
	
	
	/**
	 * It creates the courses records
	 * @param courses
	 * @return
	 */
	public Long saveCourses(List<CourseEntity> courses) {
		if( courses == null || courses.size() <=0 ) return null;
		long insrtCnt = 0;
		for( CourseEntity course : courses ) {
			em.persist(course);	
			insrtCnt++;
		}
		return insrtCnt;
	}

	public List<CourseEntity> getAllCourses() {
		return em.createQuery("SELECT c FROM CourseEntity c", CourseEntity.class).getResultList();
	}

	@Override
	public Integer getStudentIdByNam(String nam) {
		Query q = em.createNativeQuery("SELECT id FROM student b where b.name=?");
		q.setParameter(1, nam);
		List<java.math.BigInteger> studntIds = (List<java.math.BigInteger>)q.getResultList();
		return (studntIds == null || studntIds.size() <=0) ? null : ((java.math.BigInteger)studntIds.get(0)).intValue();
	}

	@Override
	public int deleteStudentCourseReg(Integer studentId) {
		/*
		 * Doesn't allow cross join to delete(DELETE FROM StudentCourseRegEntity b WHERE b.student.name=:input), 
		 * so had to go with the ID to delete the record
		 */
		Query q = em.createNativeQuery("DELETE FROM student_course_registration b WHERE b.student_id=:id");
		q.setParameter("id", studentId);
		return q.executeUpdate();
	}
	
	@Override
	public int deleteStudent(Integer studentId) {
		Query q = em.createNativeQuery("DELETE FROM student b where b.id=:id");
		q.setParameter("id", studentId);
		return q.executeUpdate();
	}

	/**
	 * It directly fetches the student record from studentCourseRegEntity by using the course using course id
	 * and retrieving the name of the student from the student table.
	 */
	@Override
	public List<String> getStudentNamByCourse(String course) {
		Query q = em.createQuery("select a.student.name from StudentCourseRegEntity a where a.course.name=:course", String.class);
		q.setParameter("course", course);
		List<String> values = q.getResultList();
		return values;		
	}

	@Override
	public List<StudentCourseRegEntity> getAllStudentCourseRepo() {
		return em.createQuery("SELECT c FROM StudentCourseRegEntity c", StudentCourseRegEntity.class).getResultList();
	}
	
	
	/**
	 * This will fetch the student record where the input course is not enrolled.
	 * It checks for the student and course record combination in studentCourseRegEntity and compare that in Student table.
	 */
	@Override
	public List<String> getStudentNamByNoCourse(String course) {
		Query q = em.createQuery("select a.name from StudentEntity a where not exists (select 1 from StudentCourseRegEntity b "
				+ "where b.student.name=a.name and b.course.name=:course)", String.class);
		q.setParameter("course", course);
		List<String> values = q.getResultList();
		return values;
	}

	
}