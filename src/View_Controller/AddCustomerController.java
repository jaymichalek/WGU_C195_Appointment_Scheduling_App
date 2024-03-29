package View_Controller;

import DBAccess.DBCustomer;
import Model.Country;
import Model.FirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static DBAccess.DBCountry.getAllCountry;
import static DBAccess.DBFirstLevelDiv.*;

/** This class Customer screen enables user to add new customer to the database. Screen is made up of a form that
 * has text fields for Customer ID, Name, Address, Postal Code, and Phone Number. It also has two combo boxes.
 * One for First Level Division and one for Country which allows user to choose from the list.*/
public class AddCustomerController implements Initializable {
    
    public TextField addCustomerIDText;
    public TextField addCustomerNameText;
    public TextField addCustomerAddressText;
    public ComboBox<FirstLevelDivision> addCustomerFirstLdCombo;
    public ComboBox<Country> addCustomerCountryCombo;
    public TextField addCustomerPostalText;
    public TextField addCustomerPhoneText;

    Stage stage;
    Parent scene;

    /** This method initializes add customer screen with list of first level division and country
     * from database to combo boxes.
     * Combo boxes show user choices of available first level division and countries.
     * @param url the location
     * @param resourceBundle the resources.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addCustomerFirstLdCombo.setItems(getAllFirstLevelDiv());
        addCustomerCountryCombo.setItems(getAllCountry());

    }

    /** This method allows user to cancel adding customer.
     * When button is clicked, this switches screen back to Customer table view list.
     * @param actionEvent the event or mouse click on Cancel button.*/
    public void onActionAddCustomerCancelBtn(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerTableView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method allows user to save customer information.
     * When button is clicked, user is able to add customer information to database.
     * Screen will switch back to Customer table view list with new customer added to
     * the list of customers.
     * @param actionEvent the event or mouse click on Add button.*/
    public void onActionAddCustomerAddBtn(ActionEvent actionEvent) throws IOException {

        //Check if any fields are missing/empty:
        if (blankField() == true) {

            return;

        } else {

            String customerName = addCustomerNameText.getText();
            String customerAddress = addCustomerAddressText.getText();
            String postalCode = addCustomerPostalText.getText();
            String customerPhoneNo = addCustomerPhoneText.getText();

            //Grabs first level division selected from combo box:
            FirstLevelDivision division = addCustomerFirstLdCombo.getSelectionModel().getSelectedItem();
            //Grabs ONLY the division ID:
            int divId = division.getFirstLevelDivId();

            //Create new customer:
            DBCustomer.createCustomer(customerName, customerAddress, postalCode, customerPhoneNo, divId);

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerTableView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }

    /** This method filters the first level division selection depending on selected country.
     * @param actionEvent the event or mouse click on Country combo box.*/
    public void onActionCountryCombo(ActionEvent actionEvent) {

        //Grabs country selected from combo box:
        Country country = addCustomerCountryCombo.getSelectionModel().getSelectedItem();

        //Grabs selected country ID:
        int countryID;
        countryID = country.getCountryId();

        //Sets the first level divisions with matching country ID for selection in FirstLdCombo Box.
        // This filters the first level division selection depending on country selected:
        addCustomerFirstLdCombo.setItems(getFirstLevelDivisionsByCountry(countryID));

    }

    /** This method checks all fields if they are empty/null.
     * @return Returns blankField that contains a boolean value.
     * True if a field is empty and false if all fields are filled.*/
    private boolean blankField() {

        boolean blankField = false;

        if (
                        addCustomerNameText.getText().isEmpty()      ||
                        addCustomerAddressText.getText().isEmpty()   ||
                        addCustomerPhoneText.getText().isEmpty()     ||
                        addCustomerPostalText.getText().isEmpty()    ||
                        addCustomerFirstLdCombo.getSelectionModel().isEmpty() ||
                        addCustomerCountryCombo.getSelectionModel().isEmpty()

        ) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setHeaderText("Missing or blank fields.");
            alert.setContentText("Please fill out all fields.");

            alert.showAndWait();

            blankField = true;

        } else {

            blankField = false;

        }

        return blankField;

    }

}
