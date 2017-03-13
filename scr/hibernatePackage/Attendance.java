package hibernatePackage;

// Generated Mar 13, 2017 10:52:21 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Attendance generated by hbm2java
 */
@Entity
@Table(name = "attendance")
public class Attendance implements java.io.Serializable {

	private int id;
	private Type type;
	private Student student;
	private Groupcoursedata groupcoursedata;
	private String date;
	private String status;
	private String pairnumber;

	public Attendance() {
	}

	public Attendance(int id) {
		this.id = id;
	}

	public Attendance(int id, Type type, Student student, Groupcoursedata groupcoursedata, String date, String status, String pairnumber) {
		this.id = id;
		this.type = type;
		this.student = student;
		this.groupcoursedata = groupcoursedata;
		this.date = date;
		this.status = status;
		this.pairnumber = pairnumber;
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
	@JoinColumn(name = "typeid")
	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studentid")
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupcoursedataid")
	public Groupcoursedata getGroupcoursedata() {
		return this.groupcoursedata;
	}

	public void setGroupcoursedata(Groupcoursedata groupcoursedata) {
		this.groupcoursedata = groupcoursedata;
	}

	@Column(name = "date", length = 100)
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(name = "status", length = 100)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "pairnumber", length = 100)
	public String getPairnumber() {
		return this.pairnumber;
	}

	public void setPairnumber(String pairnumber) {
		this.pairnumber = pairnumber;
	}

}