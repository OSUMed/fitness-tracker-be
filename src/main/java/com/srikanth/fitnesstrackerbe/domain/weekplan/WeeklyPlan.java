package com.srikanth.fitnesstrackerbe.domain.weekplan;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class WeeklyPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weeklyPlan")
    private List<DayPlan> dayPlans;

    public WeeklyPlan() {
    }

    public WeeklyPlan(Long id, List<DayPlan> dayPlans) {
        this.id = id;
        this.dayPlans = dayPlans;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DayPlan> getDayPlans() {
        return dayPlans;
    }

    public void setDayPlans(List<DayPlan> dayPlans) {
        this.dayPlans = dayPlans;
    }

    // toString
    @Override
    public String toString() {
        return "WeeklyPlan{" +
                "id=" + id +
                ", dayPlans=" + dayPlans +
                '}';
    }
}
