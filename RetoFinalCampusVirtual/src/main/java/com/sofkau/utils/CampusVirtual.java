package com.sofkau.utils;

public enum CampusVirtual {
    CAMPUS_VIRTUAL_BASE_URL("https://localhost:7245/"),
    CONTENT_COURSE_RESOURCE("api/Content"),
    CREATE_PATH_RESOURCE("api/LearningPath/CreateLearningPath"),
    CREATE_COURSES("api/Course");

    private final String  value;

    CampusVirtual(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
