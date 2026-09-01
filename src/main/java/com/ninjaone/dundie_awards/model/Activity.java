package com.ninjaone.dundie_awards.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "occured_at")
    private LocalDateTime occuredAt;

    @Column(name = "event")
    private String event;

    public Activity() {

    }

    public Activity(LocalDateTime localDateTime, String event) {
        super();
        this.occuredAt = localDateTime;
        this.event = event;
    }

    public static Activity employeeCreated(Employee employee) {
        return new Activity(LocalDateTime.now(), String.format("Created employee %s %s from %s", employee.getFirstName(), employee.getLastName(), employee.getOrganization().getName()));
    }

    public static Activity employeeDeleted(Employee employee) {
        return new Activity(LocalDateTime.now(), String.format("Deleted employee %s %s from %s", employee.getFirstName(), employee.getLastName(), employee.getOrganization().getName()));
    }

    public static Activity employeeUpdated(Employee oldEmployee, Employee updatedEmployee) {
        return new Activity(LocalDateTime.now(), String.format("Updated employee %s %s to %s %s", oldEmployee.getFirstName(), oldEmployee.getLastName(), updatedEmployee.getFirstName(), updatedEmployee.getLastName()));
    }

    public LocalDateTime getOccuredAt() {
        return occuredAt;
    }

    public String getEvent() {
        return event;
    }

}
