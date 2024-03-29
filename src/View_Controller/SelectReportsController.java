package View_Controller;

import DBAccess.DBAppointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/** This class allows user to generate reports.
 * The user is able to select 4 different reports to generate.*/
public class SelectReportsController {

    Stage stage;
    Parent scene;

    /** This method takes user to a screen where user is able to sort
     * appointments by month and type.
     * @param actionEvent the event or mouse click on Sort Customer Appointments button.*/
    public void onActionSortCustomerApptBtn(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerApptCount.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method takes user to a screen where it shows appointment schedule for each contact.
     * @param actionEvent the event or mouse click on Contact Appointment Schedule button.*/
    public void onActionContactApptSchedBtn(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/ContactSchedule.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method takes user to a screen that shows list of scheduled appointments for the day.
     * @param actionEvent the event or mouse click on Appointments Today button.*/
    public void onActionShowApptsTodayBtn(ActionEvent actionEvent) throws IOException {

        if (DBAppointment.getAppointmentsToday().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Appointments");
            alert.setHeaderText(null);
            alert.setContentText("No scheduled appointments for today.");

            alert.showAndWait();

        } else {

            stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/ApptsToday.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }

    /** This method button enables user to go back to previous screen: Appointment Table View.
     * @param actionEvent the event or mouse click on Back button.*/
    public void onActionBackBtn(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/ApptTableView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

}
