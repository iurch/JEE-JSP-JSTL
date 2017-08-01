package packt.book.jee.eclipse.ch4.bean;

import java.sql.SQLException;
import java.util.List;

import packt.book.jee.eclipse.ch4.dao.TeacherDAO;

public class Teacher extends Person {
	
	private TeacherDAO teacherDao = new TeacherDAO();
	private String designation;

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	
	public boolean isValidTeacher() {		
		return getFirstName() != null && getLastName() != null && designation != null;
	}
	
	
	public void addTeacher() throws SQLException {
		teacherDao.addTeacher(this);
	}
	
	public List<Teacher> getTeachers() throws SQLException{
		return teacherDao.getTeachers();
	}
}
