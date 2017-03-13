package hibernatePackage;

// Generated Mar 13, 2017 10:52:21 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Group generated by hbm2java
 */
@Entity
@Table(name = "group")
public class Group implements java.io.Serializable {

	private int id;
	private String groupcode;
	private String name;
	private Set<Student> students = new HashSet<Student>(0);
	private Set<Groupcoursedata> groupcoursedatas = new HashSet<Groupcoursedata>(0);

	public Group() {
	}

	public Group(int id) {
		this.id = id;
	}

	public Group(int id, String groupcode, String name, Set<Student> students, Set<Groupcoursedata> groupcoursedatas) {
		this.id = id;
		this.groupcode = groupcode;
		this.name = name;
		this.students = students;
		this.groupcoursedatas = groupcoursedatas;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "groupcode", length = 100)
	public String getGroupcode() {
		return this.groupcode;
	}

	public void setGroupcode(String groupcode) {
		this.groupcode = groupcode;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	public Set<Student> getStudents() {
		return this.students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	public Set<Groupcoursedata> getGroupcoursedatas() {
		return this.groupcoursedatas;
	}

	public void setGroupcoursedatas(Set<Groupcoursedata> groupcoursedatas) {
		this.groupcoursedatas = groupcoursedatas;
	}

}
