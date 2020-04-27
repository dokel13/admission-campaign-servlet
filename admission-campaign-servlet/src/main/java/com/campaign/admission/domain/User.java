package com.campaign.admission.domain;

import java.util.Objects;

public class User implements Cloneable {

    private Integer id;
    private Role role;
    private String email;
    private String password;
    private String name;
    private String surname;

    private User(UserBuilder userBuilder){
        id = userBuilder.id;
        role = userBuilder.role;
        email = userBuilder.email;
        password = userBuilder.password;
        name = userBuilder.name;
        surname = userBuilder.surname;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    protected User clone() {

        return builder()
                .withId(getId())
                .withRole(getRole())
                .withEmail(getEmail())
                .withPassword(getPassword())
                .withName(getName())
                .withSurname(getSurname())
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
        User user = (User) o;

        return Objects.equals(id, user.id) &&
                role == user.role &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, email, password, name, surname);
    }

    @Override
    public String toString() {
        return "User{" +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    public static class UserBuilder {

        private Integer id;
        private Role role;
        private String email;
        private String password;
        private String name;
        private String surname;

        private UserBuilder() {
        }

        public User build() {
            return new User(this);
        }

        public UserBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public UserBuilder withRole(Role role) {
            this.role = role;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder withSurname(String surname) {
            this.surname = surname;
            return this;
        }
    }
}
