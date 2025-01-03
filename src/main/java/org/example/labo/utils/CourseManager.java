package org.example.labo.utils;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private List<Course> courses;

    public CourseManager() {
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public VBox createCourseUI() {
        VBox courseContainer = new VBox(10);
        courseContainer.setStyle("-fx-background-color: #f0f2f5; -fx-padding: 20;");

        for (Course course : courses) {
            Label courseLabel = new Label(course.getCourseTitle());
            courseLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            courseContainer.getChildren().add(courseLabel);

            Label contentLabel = new Label(course.getCourseContent());
            contentLabel.setStyle("-fx-font-size: 14;");
            courseContainer.getChildren().add(contentLabel);
        }

        return courseContainer;
    }
}

