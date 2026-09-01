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
        return new Activity(LocalDateTime.now(), "Created employee %s %s from %s".formatted(employee.getFirstName(), employee.getLastName(), employee.getOrganization().getName()));
    }

    public static Activity employeeDeleted(Employee employee) {
        return new Activity(LocalDateTime.now(), "Deleted employee %s %s from %s".formatted(employee.getFirstName(), employee.getLastName(), employee.getOrganization().getName()));
    }

    public static Activity employeeUpdated(Employee oldEmployee, Employee updatedEmployee) {
        return new Activity(LocalDateTime.now(), "Updated employee %s %s to %s %s".formatted(oldEmployee.getFirstName(), oldEmployee.getLastName(), updatedEmployee.getFirstName(), updatedEmployee.getLastName()));
    }

    public static Activity organizationCreated(Organization organization) {
        return new Activity(LocalDateTime.now(), "Created organization %s".formatted(organization.getName()));
    }

    public static Activity organizationUpdated(Organization oldOrganization, Organization updatedOrganization) {
        return new Activity(LocalDateTime.now(), "Updated organization %s to %s".formatted(oldOrganization.getName(), updatedOrganization.getName()));
    }

    public static Activity organizationDeleted(Organization organization) {
        return new Activity(LocalDateTime.now(), "Deleted organization %s".formatted(organization.getName()));
    }

    public LocalDateTime getOccuredAt() {
        return occuredAt;
    }

    public String getEvent() {
        return event;
    }

}
