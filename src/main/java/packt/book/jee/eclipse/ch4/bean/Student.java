package packt.book.jee.eclipse.ch4.bean;

import java.sql.SQLException;
import java.util.List;

import packt.book.jee.eclipse.ch4.dao.StudentDAO;

public class Student extends Person {
	
	private StudentDAO studentDao = new StudentDAO();
	
	private long enrolledsince;

	public long getEnrolledsince() {
		return enrolledsince;
	}

	public void setEnrolledsince(long enrolledsince) {
		this.enrolledsince = enrolledsince;
	}
	
	
	public void addStudent() throws SQLException {
		studentDao.addStudent(this);
	}
	
	public List<Student> getStudents() throws SQLException{
		return studentDao.getStudents();
	}
}
