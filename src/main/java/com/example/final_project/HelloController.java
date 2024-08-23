package com.example.final_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button inProgressRecords;

    @FXML
    private Button allRecords;

    @FXML
    private Button completedRecords;

    @FXML
    private TableColumn<Task, LocalDate> deadlinePicker;

    @FXML
    private TableColumn<Task, String> descriptionField;

    @FXML
    private TableColumn<Task, String> nameField;

    @FXML
    private TableColumn<Task, String> statusField;

    @FXML
    private TableView<Task> tableTasks;

    private ObservableList<Task> tasks;

    private TaskDAO taskDAO = new TaskDAO();

    @FXML
    void deleteSelectedTask(ActionEvent event) {
        Task selectedTask = tableTasks.getSelectionModel().getSelectedItem();

        if (selectedTask != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Task");
            alert.setHeaderText("Are you sure you want to delete this task?");
            alert.setContentText("Task: " + selectedTask.getName());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                taskDAO.deleteTask(selectedTask.getId());
                loadTasks();
            }
        }
    }

    @FXML
    void showAddTaskDialog(ActionEvent event) {
        Dialog<Task> dialog = new Dialog<>();
        dialog.setTitle("Add Task");
        dialog.setHeaderText("Enter task details");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        TaskFormPane taskFormPane = new TaskFormPane();

        dialog.getDialogPane().setContent(taskFormPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                Task newTask = taskFormPane.getTask();
                taskDAO.addTask(newTask);
                loadTasks();
            }
            return null;
        });

        dialog.showAndWait();
    }

    @FXML
    void showEditTaskDialog(ActionEvent event) {
        Task selectedTask = tableTasks.getSelectionModel().getSelectedItem();

        if (selectedTask != null) {
            Dialog<Task> dialog = new Dialog<>();
            dialog.setTitle("Edit Task");
            dialog.setHeaderText("Edit task details");

            ButtonType editButton = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(editButton, ButtonType.CANCEL);

            TaskFormPane taskFormPane = new TaskFormPane(selectedTask);

            dialog.getDialogPane().setContent(taskFormPane);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == editButton) {
                    Task editedTask = taskFormPane.getTask();
                    editedTask.setId(selectedTask.getId());
                    taskDAO.editTask(editedTask);
                    loadTasks();
                }
                return null;
            });

            dialog.showAndWait();
        }
    }

    @FXML
    void showCompletedTasks(ActionEvent event) {
        for ( int i = 0; i < tableTasks.getItems().size(); i++) {
            tableTasks.getItems().clear();
        }

        loadCompletedTasks();

    }

    @FXML
    void showAllTasks(ActionEvent event) {
        for ( int i = 0; i < tableTasks.getItems().size(); i++) {
            tableTasks.getItems().clear();
        }

        loadTasks();

    }

    @FXML
    void showInProgressTasks(ActionEvent event) {
        for ( int i = 0; i < tableTasks.getItems().size(); i++) {
            tableTasks.getItems().clear();
        }

        loadInProgressTasks();

    }

    Task loadTasks() {
        tasks = FXCollections.observableArrayList(taskDAO.getAllTasks());
        tableTasks.setItems(tasks);

        allRecords.setStyle("-fx-background-color: transparent; -fx-text-fill: green;\n" +
                "    -fx-border-width: 2px;\n" +
                "    -fx-border-color: white;");
        inProgressRecords.setStyle("-fx-background-color: transparent; -fx-text-fill: white;\n" +
                "    -fx-border-width: 0.7px;\n" +
                "    -fx-border-color: white;");
        completedRecords.setStyle("-fx-background-color: transparent; -fx-text-fill: white;\n" +
                "    -fx-border-width: 0.7px;\n" +
                "    -fx-border-color: white;");


        return null;
    }

    Task loadInProgressTasks () {
        tasks = FXCollections.observableArrayList(taskDAO.getInProgressTasks());
        tableTasks.setItems(tasks);

        inProgressRecords.setStyle("-fx-background-color: transparent; -fx-text-fill: green;\n" +
                "    -fx-border-width: 2px;\n" +
                "    -fx-border-color: white;");
        allRecords.setStyle("-fx-background-color: transparent; -fx-text-fill: white;\n" +
                "    -fx-border-width: 0.7px;\n" +
                "    -fx-border-color: white;");
        completedRecords.setStyle("-fx-background-color: transparent; -fx-text-fill: white;\n" +
                "    -fx-border-width: 0.7px;\n" +
                "    -fx-border-color: white;");

        return null;
    }

    Task loadCompletedTasks () {
        tasks = FXCollections.observableArrayList(taskDAO.getCompletedTasks());
        tableTasks.setItems(tasks);

        completedRecords.setStyle("-fx-background-color: transparent; -fx-text-fill: green;\n" +
                "    -fx-border-width: 2px;\n" +
                "    -fx-border-color: white;");
        allRecords.setStyle("-fx-background-color: transparent; -fx-text-fill: white;\n" +
                "    -fx-border-width: 0.7px;\n" +
                "    -fx-border-color: white;");
        inProgressRecords.setStyle("-fx-background-color: transparent; -fx-text-fill: white;\n" +
                "    -fx-border-width: 0.7px;\n" +
                "    -fx-border-color: white;");


        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameField.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        descriptionField.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        statusField.setCellValueFactory(new PropertyValueFactory<Task, String>("status"));
        deadlinePicker.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("deadline"));

        tableTasks.getItems().setAll(loadTasks());
    }
}
