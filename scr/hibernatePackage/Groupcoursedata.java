package hibernatePackage;

// Generated Mar 13, 2017 10:52:21 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Groupcoursedata generated by hbm2java
 */
@Entity
@Table(name = "groupcoursedata")
public class Groupcoursedata implements java.io.Serializable {

	private int id;
	private Coursedata coursedata;
	private Group group;
	private Set<Attendance> attendances = new HashSet<Attendance>(0);

	public Groupcoursedata() {
	}

	public Groupcoursedata(int id) {
		this.id = id;
	}

	public Groupcoursedata(int id, Coursedata coursedata, Group group, Set<Attendance> attendances) {
		this.id = id;
		this.coursedata = coursedata;
		this.group = group;
		this.attendances = attendances;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "goursedataid")
	public Coursedata getCoursedata() {
		return this.coursedata;
	}

	public void setCoursedata(Coursedata coursedata) {
		this.coursedata = coursedata;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupid")
	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "groupcoursedata")
	public Set<Attendance> getAttendances() {
		return this.attendances;
	}

	public void setAttendances(Set<Attendance> attendances) {
		this.attendances = attendances;
	}

}
