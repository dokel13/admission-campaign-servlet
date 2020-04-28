package com.campaign.admission.domain;

import java.util.Objects;

public class Exam {

    private Integer id;
    private User user;
    private String subject;
    private Integer mark;

    public Exam(ExamBuilder examBuilder) {
        id = examBuilder.id;
        user = examBuilder.user;
        subject = examBuilder.subject;
        mark = examBuilder.mark;
    }

    public static ExamBuilder builder() {
        return new ExamBuilder();
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getSubject() {
        return subject;
    }

    public Integer getMark() {
        return mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exam exam = (Exam) o;

        return Objects.equals(id, exam.id) &&
                Objects.equals(user, exam.user) &&
                Objects.equals(subject, exam.subject) &&
                Objects.equals(mark, exam.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, subject, mark);
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", user=" + user +
                ", subject='" + subject + '\'' +
                ", mark=" + mark +
                '}';
    }
    
    public static class ExamBuilder {
        
        private Integer id;
        private User user;
        private String subject;
        private Integer mark;

        public ExamBuilder() {
        }
        
        public Exam build() {
            return new Exam(this);
        }

        public ExamBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ExamBuilder withUser(User user) {
            this.user = user.clone();
            return this;
        }

        public ExamBuilder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public ExamBuilder withMark(Integer mark) {
            this.mark = mark;
            return this;
        }
    }
}
