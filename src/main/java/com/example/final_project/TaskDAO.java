package com.example.final_project;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private Connection connection;

    public TaskDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @CRUDOperation(OperationType.READ)
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        String query = "SELECT * FROM Tasks";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = mapResultSetToTask(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    @CRUDOperation(OperationType.READ)
    public List<Task> getCompletedTasks() {
        List<Task> tasks = new ArrayList<>();

        String query = "SELECT * FROM public.tasks WHERE status = 'Completed'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = mapResultSetToTask(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    @CRUDOperation(OperationType.READ)
    public List<Task> getInProgressTasks() {
        List<Task> tasks = new ArrayList<>();

        String query = "SELECT * FROM public.tasks WHERE status = 'In Progress'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = mapResultSetToTask(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    @CRUDOperation(OperationType.CREATE)
    public void addTask(Task task) {
        String query = "INSERT INTO Tasks (name, description, status, deadline) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getStatus());
            preparedStatement.setObject(4, task.getDeadline());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @CRUDOperation(OperationType.UPDATE)
    public void editTask(Task task) {
        String query = "UPDATE Tasks SET name=?, description=?, status=?, deadline=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getStatus());
            preparedStatement.setObject(4, task.getDeadline());
            preparedStatement.setInt(5, task.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @CRUDOperation(OperationType.DELETE)
    public void deleteTask(int taskId) {
        String query = "DELETE FROM Tasks WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, taskId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Task mapResultSetToTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getInt("id"));
        task.setName(resultSet.getString("name"));
        task.setDescription(resultSet.getString("description"));
        task.setStatus(resultSet.getString("status"));
        task.setDeadline(resultSet.getObject("deadline", LocalDate.class));
        return task;
    }
}