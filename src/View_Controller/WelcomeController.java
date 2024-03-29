package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/** This class is the controller for Welcome.fxml screen.
 * Welcome screen enables user to choose between Customers and Appointments button.
 * This takes user to either a list of customers or a list of appointments. Welcome
 * screen also has an exit button that enables user to terminate app.*/
public class WelcomeController {

    Stage stage;
    Parent scene;

    /** This method takes user to customer table view list.
     * Clicking on Customers button will have program switch screens
     * to Customers table view list.
     * @param actionEvent the event or mouse click on Customers button.*/
    public void onActionShowCustomerList(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerTableView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method takes user to appointment table view list.
     * Clicking on Appointments button will have program switch screens
     * to Appointments table view list.
     * @param actionEvent the event or mouse click on Appointments button.*/
    public void onActionShowAppointmentList(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/ApptTableView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method terminates the app.
     * Exit button closes app.*/
    public void onActionExit(ActionEvent actionEvent) {

        System.exit(0);

    }

}
