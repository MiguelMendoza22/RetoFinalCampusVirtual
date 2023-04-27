package com.sofkau.utils;

public enum CampusVirtual {
    CAMPUS_VIRTUAL_BASE_URL("https://localhost:7245/"),
    CONTENT_COURSE_RESOURCE("api/Content/"),
    CREATE_PATH_RESOURCE("api/LearningPath/CreateLearningPath"),
    CREATE_COURSES("api/Course"),
    GET_PATH_RESOURCE("api/LearningPath"),
    GET_PATH_ID_RESOURCE("api/LearningPath/GetById?id="),
    EDIT_PATH_ID_RESOURCE("api/LearningPath?id="),
    DELETE_PATH_ID("api/LearningPath/"),
    GET_COURSES_BY_ID("api/Course/GetCourseById?id=");



    private final String  value;

    CampusVirtual(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
