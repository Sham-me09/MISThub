package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ManageUsersController {

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> idCol;
    @FXML private TableColumn<User, String> nameCol;
    @FXML private TableColumn<User, String> emailCol;
    @FXML private TableColumn<User, String> deptCol;

    private ObservableList<User> users =
            FXCollections.observableArrayList();

    // INITIALIZE 
    @FXML
    
public void initialize() {

    idCol.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                    data.getValue().getId()));

    nameCol.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                    data.getValue().getUsername()));

    emailCol.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                    data.getValue().getEmail()));

    deptCol.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                    data.getValue().getDepartment()));

    loadUsers();
}

    // LOAD USERS 
    @FXML
public void loadUsers() {

    users = UserDAO.getAllUsers();

    userTable.setItems(users);
}

    //  DELETE 
    @FXML
public void deleteUser() {

    User selected = userTable.getSelectionModel().getSelectedItem();

    if (selected == null) {
        showAlert("Select a user first!");
        return;
    }

    // Confirmation dialog
    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    confirm.setTitle("Delete User");
    confirm.setHeaderText("Confirm Deletion");
    confirm.setContentText(
            "Are you sure you want to delete user: "
            + selected.getUsername() + " ?"
    );

    ButtonType result =
            confirm.showAndWait().orElse(ButtonType.CANCEL);

    if (result == ButtonType.OK) {

        UserDAO.deleteUser(selected.getId());

        loadUsers();
    }
}

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}