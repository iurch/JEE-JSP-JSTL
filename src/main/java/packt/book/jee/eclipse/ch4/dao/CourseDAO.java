package packt.book.jee.eclipse.ch4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import packt.book.jee.eclipse.ch4.bean.Course;
import packt.book.jee.eclipse.ch4.bean.Teacher;
import packt.book.jee.eclipse.ch4.db.connection.DatabaseConnectionFactory;

public class CourseDAO {
	public void addCourse (Course course) throws SQLException {
		Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
		try {
			final String sql = "INSERT INTO Course (name,credits,Teacher_id) values (?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, course.getName());
			stmt.setInt(2, course.getCredits());
			
			if(course.getTeacherId() == 0) 
				stmt.setNull(3, Types.INTEGER);
			else 
				stmt.setInt(3, course.getTeacherId());
			
			
			stmt.execute();
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			if(rs.next()) {
				course.setId(rs.getInt(1));
			}
			rs.close();
			stmt.close();
		} finally {
			con.close();
		}
	}
	
	public List<Course> getCourses() throws SQLException {
		Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
		List<Course> courses = new ArrayList<Course>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
			StringBuilder sb = new StringBuilder("SELECT course.id as courseId, course.name as courseName,")
					.append("course.credits as credits, Teacher.id as teacherId, Teacher.first_name as firstName,")
					.append("Teacher.last_name as lastName, Teacher.designation as designation ")
					.append("from Course left outer join Teacher on ")
					.append("course.Teacher_id = Teacher.id ")
					.append("order by course.name ");
			rs = stmt.executeQuery(sb.toString());
			
			while (rs.next()) {
				Course course = new Course();
				course.setId(rs.getInt("courseId"));
				course.setName(rs.getString("courseName"));
				course.setCredits(rs.getInt("credits"));
				
				courses.add(course);
				
				int teacherId = rs.getInt("teacherId");
				
				if (rs.wasNull()) {
					continue;
				}
				Teacher teacher = new Teacher();
				teacher.setId(teacherId);
				teacher.setFirstName(rs.getString("firstName"));
				teacher.setLastName(rs.getString("lastName"));
				teacher.setDesignation(rs.getString("designation"));
				course.setTeacher(teacher);
			}
			return courses;
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {}
			try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
			try { con.close(); }catch (SQLException e) {}
		}
	}
}
