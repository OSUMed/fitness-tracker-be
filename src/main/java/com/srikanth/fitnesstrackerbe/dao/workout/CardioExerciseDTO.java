package com.srikanth.fitnesstrackerbe.dao.workout;
import java.util.List;

public class CardioExerciseDTO extends ExerciseDTO {
    private List<CardioSetDTO> cardioSets;

	public CardioExerciseDTO(String type, String exerciseName, List<CardioSetDTO> cardioSets) {
		super(type, exerciseName, cardioSets);
		this.cardioSets = cardioSets;
	}

	public List<CardioSetDTO> getCardioSets() {
		return cardioSets;
	}

	public void setCardioSets(List<CardioSetDTO> cardioSets) {
		this.cardioSets = cardioSets;
	}

	@Override
	public String toString() {
		return "CardioExerciseDTO [cardioSets=" + cardioSets + "]";
	}

    
}
