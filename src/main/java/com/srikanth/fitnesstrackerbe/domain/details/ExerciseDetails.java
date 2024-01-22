package com.srikanth.fitnesstrackerbe.domain.details;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ExerciseDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String name;
    private String infoLink;
    private String notes;
    
    public ExerciseDetails() {
    	
    }
	public ExerciseDetails(Long id, String type, String name, String infoLink, String notes) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.infoLink = infoLink;
		this.notes = notes;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfoLink() {
		return infoLink;
	}
	public void setInfoLink(String infoLink) {
		this.infoLink = infoLink;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	@Override
	public String toString() {
		return "ExerciseDetails [id=" + id + ", type=" + type + ", name=" + name + ", infoLink=" + infoLink + ", notes="
				+ notes + "]";
	}

}
