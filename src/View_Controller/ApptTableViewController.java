package View_Controller;

import DBAccess.DBAppointment;
import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static DBAccess.DBAppointment.getAllAppointments;

/** This class enables user to select buttons for going back to previous screen, adding/updating/deleting appointments.
 * Appointment Table View screen displays list of all scheduled appointments in table view.*/
public class ApptTableViewController implements Initializable {

    public TableView<Appointment> apptTableView;
    public TableColumn apptIdCol;
    public TableColumn apptTitleCol;
    public TableColumn apptDescriptionCol;
    public TableColumn apptLocationCol;
    public TableColumn apptContactCol;
    public TableColumn apptTypeCol;
    public TableColumn apptStartDateTimeCol;
    public TableColumn apptEndDateTimeCol;
    public TableColumn apptCustomerIDCol;
    public ToggleGroup filterApptToggle;
    public RadioButton filterMonthRadioBtn;
    public RadioButton filterWeekRadioBtn;
    public RadioButton filterAllRadioBtn;

    Stage stage;
    Parent scene;
    private Appointment selectedAppt;

    /** This method initialized appointment table view screen with list of appointments.
     * @param url the location
     * @param resourceBundle the resources.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        apptTableView.setItems(getAllAppointments());
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }

    /** This method allows user to filter appointments by month.
     * Radio button selected will filter list to appointments scheduled only for current month.
     * @param actionEvent the event or mouse click on By Month radio button.*/
    public void onActionFilterByMonthBtn(ActionEvent actionEvent) {

        if (filterMonthRadioBtn.isSelected()) {

            apptTableView.setItems(DBAppointment.getAppointmentsByCurrentMonth());

        }

    }

    /** This method allows user to filter appointments by week.
     * Radio button selected will filter list to appointments occurring for current week.
     * @param actionEvent the event or mouse click on By Week radio button.*/
    public void onActionFilterByWeekBtn(ActionEvent actionEvent) {

        if(filterWeekRadioBtn.isSelected()) {

            apptTableView.setItems(DBAppointment.getAppointmentsByCurrentWeek());

        }

    }

    /** This method allows user to list back all appointments.
     * Radio button selected will list all scheduled appointments.
     * @param actionEvent the event or mouse click on All radio button.*/
    public void onActionFilterAllBtn(ActionEvent actionEvent) {

        if (filterAllRadioBtn.isSelected()) {

            apptTableView.setItems(getAllAppointments());

        }

    }

    /** This method button enables user to go back to previous screen: Welcome screen.
     * @param actionEvent the event or mouse click on Back button.*/
    public void onActionApptBackBtn(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/Welcome.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method button enables user to add a new customer.
     * Switches to next screen: Add Appointment screen.
     * @param actionEvent the event or mouse click on Add button.*/
    public void onActionApptAddBtn(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/AddAppointmentScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method enables user to update appointment information for appointment selected from the
     * table view list. This jumps to next screen: Update Appointment screen.
     * @param actionEvent the event or mouse click on Update button.*/
    public void onActionApptUpdateBtn(ActionEvent actionEvent) throws IOException {

        selectedAppt = apptTableView.getSelectionModel().getSelectedItem();

        if (selectedAppt == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("No appointment selected.");
            alert.setContentText("Please select an appointment to update.");

            alert.showAndWait();

            return;

        } else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View_Controller/UpdateAppointmentScreen.fxml"));
            loader.load();

            UpdateAppointmentController MODController = loader.getController();
            MODController.getAppt(selectedAppt);

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }

    /** This method enables user to delete current appointment selected.
     * @param actionEvent the event or mouse click on Delete button.*/
    public void onActionApptDeleteBtn(ActionEvent actionEvent) {

        selectedAppt = apptTableView.getSelectionModel().getSelectedItem();

        if (selectedAppt == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("No appointment selected.");
            alert.setContentText("Please select an appointment to delete.");

            alert.showAndWait();

            return;

        } else {

            //Show appt ID and type on alert box:
            int apptId = selectedAppt.getAppointmentId();
            String apptType = selectedAppt.getType();

            //Deleting appointment alert confirmation box:
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting A Customer");
            alert.setHeaderText("You are about to delete an appointment.");
            alert.setContentText("Are you sure you want to proceed?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                DBAppointment.deleteAppt(apptId);

                //Update appointment table view info after deleting appointment:
                apptTableView.setItems(DBAppointment.getAllAppointments());

                //Show delete confirmation on GUI with id and type of appointment:
                Alert alertDeleteConfirmation = new Alert(Alert.AlertType.INFORMATION);
                alertDeleteConfirmation.setTitle("Appointment Deleted");
                alertDeleteConfirmation.setHeaderText(null);
                alertDeleteConfirmation.setContentText("Appointment has been deleted. \n" + "Appointment ID:" +
                        " " + apptId + ".\n" + "Appointment Type: " + apptType + ".");

                alertDeleteConfirmation.showAndWait();

            } else {

                return;

            }

        }

    }

    /** This method enables user to go to the reports screen.
     * @param actionEvent the event or mouse click on Reports button.*/
    public void onActionApptReportBtn(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/SelectReports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

}
