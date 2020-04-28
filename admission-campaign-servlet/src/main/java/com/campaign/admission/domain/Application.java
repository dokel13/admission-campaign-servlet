package com.campaign.admission.domain;

import java.util.Objects;

public class Application {

    private Integer id;
    private User user;
    private Specialty specialty;
    private Boolean enrollment;
    private Integer markSum;

    public Application(Integer id, User user, Specialty specialty, Boolean enrollment, Integer markSum) {
        this.id = id;
        this.user = user.clone();
        this.specialty = specialty.clone();
        this.enrollment = enrollment;
        this.markSum = markSum;
    }

    public Application(User user, Specialty specialty, Integer markSum) {
        this.user = user.clone();
        this.specialty = specialty.clone();
        this.markSum = markSum;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public Boolean getEnrollment() {
        return enrollment;
    }

    public Integer getMarkSum() {
        return markSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Application that = (Application) o;

        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(specialty, that.specialty) &&
                Objects.equals(enrollment, that.enrollment) &&
                Objects.equals(markSum, that.markSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, specialty, enrollment, markSum);
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", user=" + user +
                ", specialty=" + specialty +
                ", enrollment=" + enrollment +
                ", markSum=" + markSum +
                '}';
    }
}