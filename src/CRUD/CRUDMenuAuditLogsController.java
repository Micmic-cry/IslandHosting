package CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import java.util.ArrayList;

import Tables.Audit_Logs;
public class CRUDMenuAuditLogsController {

    @FXML private TableView<Audit_Logs> auditLogsTable;
    @FXML private TableColumn<Audit_Logs, Integer> log_id;
    @FXML private TableColumn<Audit_Logs, Integer> user_id;
    @FXML private TableColumn<Audit_Logs, String> action_type;
    @FXML private TableColumn<Audit_Logs, String> description;
    @FXML private TableColumn<Audit_Logs, String> ip_address;
    @FXML private TableColumn<Audit_Logs, String> timestamp;

    private ObservableList<Audit_Logs> auditLogsList = FXCollections.observableArrayList();
    private ArrayList<Audit_Logs> auditLogs_ar = new ArrayList<>();
    private Scene scene;
    private Stage stage;

    public void initialize() {
        initializeTableColumns();
        populateTable();    
    }
    
    public void initializeTableColumns() {
        log_id.setCellValueFactory(new PropertyValueFactory<>("log_id"));
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        action_type.setCellValueFactory(new PropertyValueFactory<>("action_type"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        ip_address.setCellValueFactory(new PropertyValueFactory<>("ip_address"));
        timestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        auditLogsTable.setItems(auditLogsList);
    }

    public void populateTable() {
        auditLogs_ar = new Audit_Logs().SELECT_ALL_AUDIT_LOGS();
        for (Audit_Logs audit_log : auditLogs_ar) {
            auditLogsList.add(audit_log);
        }
    }

    public void navigateToUsers(ActionEvent event) {
        navigateToScene(event, "CRUDUsersMenu.fxml");
    }
    
    public void navigateToServers(ActionEvent event) {
        navigateToScene(event, "CRUDServersMenu.fxml");
    }

    public void navigateToOrders(ActionEvent event) {
        navigateToScene(event, "CRUDOrdersMenu.fxml");
    }
    
    private void navigateToScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Double width = stage.getWidth();
            Double height = stage.getHeight();
            scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);
            stage.centerOnScreen();
            if (fxmlFile.equals("../LoginMenu.fxml")) {
                stage.setWidth(650);
                stage.setHeight(300);
            }
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: Implement CRUD for Audit Logs
    public void createAudit_logs() {
        showAudit_logsDialog(null);
    }

    public void updateAudit_logs() {
        Audit_Logs selectedAudit_logs = auditLogsTable.getSelectionModel().getSelectedItem();
        if (selectedAudit_logs == null) {
            showAlert(Alert.AlertType.WARNING, "No Audit Logs Selected", "Please select an Audit Logs to update.");
            return;
        }
    
    }

    public void deleteAudit_logs() {
        Audit_Logs selectedAudit_logs = auditLogsTable.getSelectionModel().getSelectedItem();
        if (selectedAudit_logs == null) {
            showAlert(Alert.AlertType.WARNING, "No Audit Logs Selected", "Please select an Audit Logs to delete.");
            return;
        }
    }

    public void showAudit_logsDialog(Audit_Logs audit_logs) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Audit_logsDialog.fxml"));
            Parent root = loader.load();

            Audit_logsDialogController controller = loader.getController();
            controller.setAudit_logsList(auditLogsList);
            if (audit_logs != null) {
                controller.setAudit_logs(audit_logs); // Existing user for editing
            }

            Stage dialogStage = new Stage();
            dialogStage.setTitle(audit_logs == null ? "Create Audit Logs" : "Update Audit Logs");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.centerOnScreen();
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void logout(ActionEvent event) {
        try {
            navigateToScene(event, "../LoginMenu.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}