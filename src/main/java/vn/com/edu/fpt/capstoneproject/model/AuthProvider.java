package vn.com.edu.fpt.capstoneproject.model;

import lombok.Getter;

public enum  AuthProvider {
    LOCAL("local"),
    FACEBOOK("facebook"),
    GOOGLE("google"),
    GITHUB("github");
    @Getter
    private final String text;
    AuthProvider(String text){this.text = text;}
}