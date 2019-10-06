package com.topica.edu.itlab.jdbc.tutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.topica.edu.itlab.jdbc.tutorial.entity.*;

public class Connect {

	public List<School> listSchool = new ArrayList<School>();
	public List<ClassOfSchool> listClass = new ArrayList<ClassOfSchool>();
	public List<Subject> listSubject = new ArrayList<Subject>();
	public List<Student> listStudent = new ArrayList<Student>();
	public List<StudentSubjectRegister> listRegister = new ArrayList<StudentSubjectRegister>();

	public void loadSchool(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from school");
		// read from database
		while (rs.next()) {
			School school = new School();
			school.setSchoolId(rs.getInt(1));
			school.setSchoolName(rs.getString(2));
			school.setSchoolDesc(rs.getString(3));
			listSchool.add(school);
		}
	}
	
	public void LoadClassOfSchool(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from ClassOfSchool");
		// read from database
		while (rs.next()) {
			ClassOfSchool classOfSchool = new ClassOfSchool();
			classOfSchool.setClassId(rs.getInt(1));
			classOfSchool.setClassCode(rs.getString(2));
			classOfSchool.setClassDesc(rs.getString(3));
			classOfSchool.setSchoolId(rs.getInt(4));
			listClass.add(classOfSchool);
		}
	}
	
	public void loadStudent(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from Student");
		// read from database
		while (rs.next()) {
			Student student = new Student();
			student.setStudentId(rs.getInt(1));
			student.setStudentName(rs.getString(2));
			student.setStudentMobile(rs.getString(3));
			student.setClassId(rs.getInt(4));
			listStudent.add(student);
		}
	}
	
	public void loadSubject(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from Subject");
		// read from database
		while (rs.next()) {
			Subject subject = new Subject();
			subject.setSubjectId(rs.getInt(2));
			subject.setSubjectName(rs.getString(1));
			subject.setSubjectDesc(rs.getString(3));
			String domain = rs.getString(4);
			
			SubjectDomain[] allSubjectDomain = SubjectDomain.values();
			for (SubjectDomain sDomain : allSubjectDomain )
				if (domain.equals(sDomain.toString())) 
					subject.setDomain(sDomain);
			listSubject.add(subject);
		}
		
		/*
		 * for (int i=0; i<listSubject.size(); i++) {
		 * System.out.println(listSubject.get(i).getSubjectId());
		 * System.out.println(listSubject.get(i).getSubjectName());
		 * System.out.println(listSubject.get(i).getSubjectDesc());
		 * System.out.println(listSubject.get(i).getDomain().toString()); }
		 */
	}
	
	public void loadRegister(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from StudentsubjectRegister");
		// read from database
		while (rs.next()) {
			StudentSubjectRegister register = new StudentSubjectRegister();
			register.setStudentId(rs.getInt(1));
			register.setSubjectId(rs.getInt(2));
			register.setScore(rs.getDouble(3));
			listRegister.add(register);
		}
	}
	
	public List<School> getListSchool(){
		return listSchool;
	}
	
	public List<ClassOfSchool> getlistClass(){
		return listClass;
	}
	
	public List<Student> getListStudent(){
		return listStudent;
	}
	
	public List<Subject> getListSubject(){
		return listSubject;
	}
	
	public List<StudentSubjectRegister> getListRegister(){
		return listRegister;
	}
	
	// connect to mysql
	public void ConnectionSQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
			Statement stmt = con.createStatement();
			loadSchool(stmt);
			LoadClassOfSchool(stmt);
			loadSubject(stmt);
			loadStudent(stmt);
			loadRegister(stmt);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
