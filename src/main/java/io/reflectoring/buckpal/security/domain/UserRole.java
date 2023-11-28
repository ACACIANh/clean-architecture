package io.reflectoring.buckpal.security.domain;

public enum UserRole {

    GUEST,
    MEMBER,
    ADMIN;

    public String getRole() {
        return "ROLE_" + this.name();
    }
}
