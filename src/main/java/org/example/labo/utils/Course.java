package org.example.labo.utils;

class Course {
    private String courseTitle;
    private String courseContent;

    public Course(String courseTitle, String courseContent) {
        this.courseTitle = courseTitle;
        this.courseContent = courseContent;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseContent() {
        return courseContent;
    }
}
