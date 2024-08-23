package com.example.final_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class TaskFormPane extends GridPane {
    private TextField nameField;
    private TextField descriptionField;
    private DatePicker deadlinePicker;

    public TaskFormPane() {
        initialize();
    }

    public TaskFormPane(Task task) {
        initialize();
        populateFields(task);
    }

    private ComboBox<String> statusComboBox;

    private void initialize() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(20, 150, 10, 10));

        nameField = new TextField();
        descriptionField = new TextField();
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Completed", "In Progress");
        deadlinePicker = new DatePicker();

        add(new Label("Name:"), 0, 0);
        add(nameField, 1, 0);
        add(new Label("Description:"), 0, 1);
        add(descriptionField, 1, 1);
        add(new Label("Status:"), 0, 2);
        add(statusComboBox, 1, 2);
        add(new Label("Deadline:"), 0, 3);
        add(deadlinePicker, 1, 3);
    }

    public Task getTask() {
        return new Task(
                nameField.getText(),
                descriptionField.getText(),
                statusComboBox.getValue(),
                deadlinePicker.getValue()
        );
    }

    public void populateFields(Task task) {
        nameField.setText(task.getName());
        descriptionField.setText(task.getDescription());
        statusComboBox.setValue(task.getStatus());
        deadlinePicker.setValue(task.getDeadline());
    }
}
