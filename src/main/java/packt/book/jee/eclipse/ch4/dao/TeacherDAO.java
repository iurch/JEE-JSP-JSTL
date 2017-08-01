package packt.book.jee.eclipse.ch4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import packt.book.jee.eclipse.ch4.bean.Teacher;
import packt.book.jee.eclipse.ch4.db.connection.DatabaseConnectionFactory;

public class TeacherDAO {
	public void addTeacher(Teacher teacher) throws SQLException {
		Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
		
		try {
			final String sql = "INSERT INTO Teacher (first_name,last_name,designation) VALUES (?,?,?) ";
			PreparedStatement stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, teacher.getFirstName());
			stmt.setString(2, teacher.getLastName());
			stmt.setString(3, teacher.getDesignation());
			stmt.execute();
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				teacher.setId(rs.getInt(1));
			}
			rs.close();
			stmt.close();
		} finally {
			con.close();
		}
	}
	
	public List<Teacher> getTeachers() throws SQLException{
		Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
		
		List<Teacher> teachers = new ArrayList<Teacher>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
			StringBuilder sb = new StringBuilder("SELECT Teacher.id as teacherId, ")
					.append("Teacher.first_name as teacherFirstName, ")
					.append("Teacher.last_name as teacherLastName, ")
					.append("Teacher.designation as teacherDesignation ")
					.append("FROM Teacher ")
					.append("order by Teacher.first_name");
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()) {
				Teacher teacher = new Teacher();
				teacher.setId(rs.getInt("teacherId"));
				teacher.setFirstName(rs.getString("teacherFirstName"));
				teacher.setLastName(rs.getString("teacherLastName"));
				teacher.setDesignation(rs.getString("teacherDesignation"));
				
				teachers.add(teacher);
			}
			
			return teachers;
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException e) {}
			if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
			try { con.close(); } catch (SQLException e) {}
		}
		
		
	}
}
