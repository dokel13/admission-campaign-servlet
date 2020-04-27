package com.campaign.admission.domain;

import java.util.Objects;

public class Requirement implements Cloneable{

    private Integer id;
    private String subject;
    private Integer mark;

    public Requirement(Integer id, String subject, Integer mark) {
        this.id = id;
        this.subject = subject;
        this.mark = mark;
    }

    public Requirement(String subject, Integer mark) {
        this.subject = subject;
        this.mark = mark;
    }

    public Requirement() {
    }

    public Integer getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public Integer getMark() {
        return mark;
    }

    @Override
    public Requirement clone() {
        return new Requirement(id, subject, mark);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Requirement that = (Requirement) o;

        return Objects.equals(id, that.id) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(mark, that.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, mark);
    }

    @Override
    public String toString() {
        return "Requirement{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", mark=" + mark +
                '}';
    }
}
