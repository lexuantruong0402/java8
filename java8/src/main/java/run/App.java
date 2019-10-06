package run;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.topica.edu.itlab.jdbc.tutorial.Connect;
import com.topica.edu.itlab.jdbc.tutorial.entity.*;

public class App<T> {
	public static Connect connect = new Connect();
	public static List<School> listSchool;
	public static List<ClassOfSchool> listClass;
	public static List<Subject> listSubject;
	public static List<Student> listStudent;
	public static List<StudentSubjectRegister> listRegister;

	public static void example1() {
		listSchool.stream().forEach(school -> {
			System.out.println(school.getSchoolName() + " ");
			List<ClassOfSchool> join = listClass.stream().filter(clazz -> clazz.getSchoolId() == school.getSchoolId())
					.collect(Collectors.toList());
			Optional<Long> a = join.stream()
					.map(j -> listStudent.stream().filter(stu -> stu.getClassId() == j.getClassId()).count())
					.reduce(Long::sum);
			System.out.println(a.get());
		});
	}

	public static void example2() {
		listSubject.stream().forEach(subject -> {
			System.out.println(subject.getSubjectName());
			List<StudentSubjectRegister> join = listRegister.stream()
					.filter(re -> re.getSubjectId() == subject.getSubjectId()).collect(Collectors.toList());
			OptionalDouble a = join.stream().mapToDouble(StudentSubjectRegister::getScore).average();
			System.out.println(a.getAsDouble());
		});
	}

	public static void example3() {
		Map<Integer, Double> list = listRegister.stream().collect(Collectors.groupingBy(re -> re.getSubjectId(),
				Collectors.averagingDouble(StudentSubjectRegister::getScore)));

		LinkedHashMap<Integer, Double> finalMap = new LinkedHashMap<>();
		list.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> finalMap.put(x.getKey(), x.getValue()));

		System.out.println(finalMap);
		Entry<Integer, Double> entry = finalMap.entrySet().iterator().next();
		Integer key = entry.getKey();

		Subject result = listSubject.stream().filter(sub -> sub.getSubjectId() == key).findAny().orElse(null);
		System.out.println(result.getSubjectName());
	}

	public static void example4() {
		listSubject.stream().forEach(sub -> {
			System.out.println(sub.getSubjectName());
			
			List<StudentSubjectRegister> list = listRegister.stream()
					.filter(res -> res.getSubjectId() == sub.getSubjectId()).collect(Collectors.toList());
			
			System.out.println("Descending");
			List<StudentSubjectRegister> descending = list.stream()
					.sorted(Comparator.comparingDouble(StudentSubjectRegister::getScore).reversed())
					.collect(Collectors.toList());	
			descending.stream().limit(2).forEach(des -> {
				Student result = listStudent.stream().filter(stu-> stu.getStudentId() == des.getStudentId()).findAny().orElse(null);
				System.out.print(result.getStudentId()+" ");
				System.out.print(result.getStudentName()+" ");
				System.out.print(result.getStudentMobile()+" ");
				System.out.println(des.getScore());
			});
			
			System.out.println("Ascending");
			List<StudentSubjectRegister> ascending = list.stream()
					.sorted(Comparator.comparingDouble(StudentSubjectRegister::getScore))
					.collect(Collectors.toList());	
			ascending.stream().limit(2).forEach(des -> {
				Student result = listStudent.stream().filter(stu-> stu.getStudentId() == des.getStudentId()).findAny().orElse(null);
				System.out.print(result.getStudentId()+" ");
				System.out.print(result.getStudentName()+" ");
				System.out.print(result.getStudentMobile()+" ");
				System.out.println(des.getScore());
			});
		});
	}

	public static void example5() {
		
	}
	public static void main(String[] args) {
		connect.ConnectionSQL();
		listSchool = connect.getListSchool();
		listClass = connect.getlistClass();
		listSubject = connect.getListSubject();
		listStudent = connect.getListStudent();
		listRegister = connect.getListRegister();
		example4();
	}
}
