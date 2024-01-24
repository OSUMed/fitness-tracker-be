package com.srikanth.fitnesstrackerbe.domain.weekplan;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class WeeklyPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weeklyPlan", orphanRemoval = true)
    @JsonManagedReference // Manages serialization
    private List<DayPlan> dayPlans = new ArrayList<>();

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

    @Override
    public String toString() {
        return "WeeklyPlan{" + "id=" + id + ", dayPlanIds=" + dayPlans.stream().map(DayPlan::getId).collect(Collectors.toList()) + '}';
    }

}
