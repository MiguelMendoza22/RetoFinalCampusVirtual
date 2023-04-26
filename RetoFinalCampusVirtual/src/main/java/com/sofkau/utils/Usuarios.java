package com.sofkau.utils;

public enum Usuarios {
    USUARIOS_BASE_URL("https://localhost:7056/"),
    USUARIOS_REGISTRO_RESOURCE("api/User");



    private final String  value;

    Usuarios(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
