package hibernatePackage;

// Generated Mar 13, 2017 10:52:21 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Language generated by hbm2java
 */
@Entity
@Table(name = "language")
public class Language implements java.io.Serializable {

	private int id;
	private String name;

	public Language() {
	}

	public Language(int id) {
		this.id = id;
	}

	public Language(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}