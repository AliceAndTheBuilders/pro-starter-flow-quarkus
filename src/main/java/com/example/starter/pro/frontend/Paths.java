package com.example.starter.pro.frontend;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Paths {
    public static final String LOGIN = "/ui/login";
    public static final String LOGOUT = "/api/logout";
    public static final String AUTH = "/j_security_check";
    public static final String PROTECTED = "/ui/protected";
    public static final String PUBLIC = "/ui/public";
}
