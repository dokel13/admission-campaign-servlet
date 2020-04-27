package com.campaign.admission.domain;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class Specialty implements Cloneable {

    private Integer id;
    private String name;
    private Integer maxStudentAmount;
    private Boolean open;
    private List<Requirement> requirements;

    public Specialty(SpecialtyBuilder specialtyBuilder) {
        id = specialtyBuilder.id;
        name = specialtyBuilder.name;
        maxStudentAmount = specialtyBuilder.maxStudentAmount;
        open = specialtyBuilder.open;
        requirements = specialtyBuilder.requirements;
    }

    public static SpecialtyBuilder builder() {
        return new SpecialtyBuilder();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMaxStudentAmount() {
        return maxStudentAmount;
    }

    public Boolean getOpen() {
        return open;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    @Override
    protected Specialty clone() {

        return builder()
                .withId(getId())
                .withName(getName())
                .withMaxStudentAmount(getMaxStudentAmount())
                .withOpen(getOpen())
                .withRequirements(requirements)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Specialty specialty = (Specialty) o;

        return Objects.equals(id, specialty.id) &&
                Objects.equals(name, specialty.name) &&
                Objects.equals(maxStudentAmount, specialty.maxStudentAmount) &&
                Objects.equals(open, specialty.open) &&
                Objects.equals(requirements, specialty.requirements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maxStudentAmount, open, requirements);
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxStudentAmount=" + maxStudentAmount +
                ", open=" + open +
                ", requirements=" + requirements +
                '}';
    }

    public static class SpecialtyBuilder {

        private Integer id;
        private String name;
        private Integer maxStudentAmount;
        private Boolean open;
        private List<Requirement> requirements;

        public SpecialtyBuilder() {
        }

        public Specialty build() {
            return new Specialty(this);
        }

        public SpecialtyBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public SpecialtyBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SpecialtyBuilder withMaxStudentAmount(Integer maxStudentAmount) {
            this.maxStudentAmount = maxStudentAmount;
            return this;
        }

        public SpecialtyBuilder withOpen(Boolean open) {
            this.open = open;
            return this;
        }

        public SpecialtyBuilder withRequirements(List<Requirement> requirements) {
            if (requirements != null) {
                this.requirements = requirements.stream().map(Requirement::clone).collect(toList());
            }
            return this;
        }
    }
}
