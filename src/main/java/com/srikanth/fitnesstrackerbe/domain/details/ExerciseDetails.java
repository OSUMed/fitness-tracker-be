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
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = StrengthExerciseDetails.class, name = "strength"),
    @JsonSubTypes.Type(value = CardioExerciseDetails.class, name = "cardio"),
    @JsonSubTypes.Type(value = StretchExerciseDetails.class, name = "stretch")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ExerciseDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("type")
    private String type;
    private String name;
    private String infoLink;
    private String notes;
    
    public ExerciseDetails() {
    	
    }
	public ExerciseDetails(String type, String name, String infoLink, String notes) {
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
