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
 * Course generated by hbm2java
 */
@Entity
@Table(name = "course")
public class Course implements java.io.Serializable {

	private int id;
	private String code;
	private String name;
	private String lectureship;
	private Set<Coursedata> coursedatas = new HashSet<Coursedata>(0);

	public Course() {
	}

	public Course(int id) {
		this.id = id;
	}

	public Course(int id, String code, String name, String lectureship, Set<Coursedata> coursedatas) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.lectureship = lectureship;
		this.coursedatas = coursedatas;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "code", length = 100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "lectureship", length = 100)
	public String getLectureship() {
		return this.lectureship;
	}

	public void setLectureship(String lectureship) {
		this.lectureship = lectureship;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	public Set<Coursedata> getCoursedatas() {
		return this.coursedatas;
	}

	public void setCoursedatas(Set<Coursedata> coursedatas) {
		this.coursedatas = coursedatas;
	}

}
