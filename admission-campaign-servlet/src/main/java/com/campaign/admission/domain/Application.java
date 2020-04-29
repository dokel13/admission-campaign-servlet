package com.campaign.admission.domain;

import java.util.Objects;

public class Application {

    private Integer id;
    private User user;
    private Specialty specialty;
    private Boolean enrollment;
    private Integer markSum;

    private Application(ApplicationBuilder applicationBuilder) {
        id = applicationBuilder.id;
        user = applicationBuilder.user;
        specialty = applicationBuilder.specialty;
        enrollment = applicationBuilder.enrollment;
        markSum = applicationBuilder.markSum;
    }

    public static ApplicationBuilder builder() {
        return new ApplicationBuilder();
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

    public static class ApplicationBuilder {

        private Integer id;
        private User user;
        private Specialty specialty;
        private Boolean enrollment;
        private Integer markSum;

        public ApplicationBuilder() {
        }

        public Application build() {
            return new Application(this);
        }

        public ApplicationBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ApplicationBuilder withUser(User user) {
            this.user = user.clone();
            return this;
        }

        public ApplicationBuilder withSpecialty(Specialty specialty) {
            this.specialty = specialty;
            return this;
        }

        public ApplicationBuilder withEnrollment(Boolean enrollment) {
            this.enrollment = enrollment;
            return this;
        }

        public ApplicationBuilder withMarkSum(Integer markSum) {
            this.markSum = markSum;
            return this;
        }
    }
}
