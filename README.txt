This has been developed using Spring JPA on hibernate.

I have created the CourseEntity, StudentEntity and StudentCourseRegEntity.

Because we have to maintain the score as well 
so went with the @OneToMany in Student/Course and @ManyToOne with the StudentCourseRegEntity.
Otherwise we could have used only @ManyToMany in Student and Course and and using the 
@JoinTable we could have defined the relationship between the student and studentcourse.

StudentEntity (Table = student) has the following,
ID
NAME

CourseEntity (Table = course) has the following,
ID
NAME

StudentCourseRegEntity (Table = student_course_registration) has the following,
STUDENT_ID ( is a foreign key references to student.id)
COURSE_ID (  is a foreign key references to course.id )
SCORE


