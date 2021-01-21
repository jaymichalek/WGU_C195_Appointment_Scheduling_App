package View_Controller;

import DBAccess.DBCustomer;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static DBAccess.DBCountry.getAllCountry;
import static DBAccess.DBFirstLevelDiv.getAllFirstLevelDiv;

/** This class is the controller for UpdateCustomerScreen.fxml screen.
 * Update customer screen enables users to update customer information
 * based on selection from customers table view list screen.*/
public class UpdateCustomerController implements Initializable {

    public TextField updateIDText;
    public TextField updateNameText;
    public TextField updateAddressText;
    public ComboBox updateFirstLdCombo;
    public ComboBox updateCountryCombo;
    public TextField updatePhoneText;
    public TextField updatePostalText;

    Stage stage;
    Parent scene;
    private int customerId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateFirstLdCombo.setItems(getAllFirstLevelDiv());
        updateCountryCombo.setItems(getAllCountry());

    }

    /** This method obtains selected customer from the table view list.
     * @param customer The customer selected.*/
    public void getCustomer(Customer customer){

        customerId = customer.getCustomerId();
        int divId = customer.getDivisionId();
        int countryId = customer.getCountryId();

        updateNameText.setText(customer.getCustomerName());
        updateAddressText.setText(customer.getCustomerAddress());
        updatePhoneText.setText(customer.getCustomerPhoneNo());
        updatePostalText.setText(customer.getPostalCode());

        //FIXME: Set combo boxes to previously selected items:

        updateFirstLdCombo.getSelectionModel().select(divId);
        updateCountryCombo.getSelectionModel().select(countryId);

    }

    /** This method enables user to cancel customer update.
     * Cancel button enables user to cancel and switch back to previous
     * screen: Customers Table View list.
     * @param actionEvent the event or mouse click on Cancel button.*/
    public void onActionUpdateCustomerCancelBtn(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerTableView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method allows user to delete customer selected.
     * Delete button enables user to delete current user information shown on screen.
     * This will also switch back to previous screen: Customers Table View List.
     * @param actionEvent the event or mouse click on Delete button.*/
    public void onActionUpdateCustomerDeleteBtn(ActionEvent actionEvent) throws IOException {

        String customerName = updateNameText.getText();
        String customerAddress = updateAddressText.getText();
        String postalCode = updatePostalText.getText();
        String customerPhoneNo = updatePhoneText.getText();

        //Grabs first level division selected from combo box:
        FirstLevelDivision division = (FirstLevelDivision) updateFirstLdCombo.getSelectionModel().getSelectedItem();
        //Grabs ONLY the division ID:
        int divId = division.getFirstLevelDivId();

        //Grabs country selected from combo box:
        Country country = (Country) updateCountryCombo.getSelectionModel().getSelectedItem();

        //Combo box selection validation:
        if (division == null || country == null) {
            return;
        }

        //Delete customer selected from table view:
        DBCustomer.deleteCustomer(customerId);

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerTableView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method allows user to update selected customer information.
     * Update button enables user to submit edited/updated customer information to the database.
     * This will also switch user back to previous screen: Customer Table View list but with a
     * list updated with the new customer information updated by user.
     * @param actionEvent the event or mouse click on Update button.*/
    public void onActionUpdateCustomerBtn(ActionEvent actionEvent) throws IOException {

        String customerName = updateNameText.getText();
        String customerAddress = updateAddressText.getText();
        String postalCode = updatePostalText.getText();
        String customerPhoneNo = updatePhoneText.getText();

        //Grabs first level division selected from combo box:
        FirstLevelDivision division = (FirstLevelDivision) updateFirstLdCombo.getSelectionModel().getSelectedItem();
        //Grabs ONLY the division ID:
        int divId = division.getFirstLevelDivId();

        //Grabs country selected from combo box:
        Country country = (Country) updateCountryCombo.getSelectionModel().getSelectedItem();

        //Combo box selection validation:
        if (division == null || country == null) {
            return;
        }

        //Update database by calling updateCustomer query method in DBCustomer:
        DBCustomer.updateCustomer(customerId, customerName, customerAddress, postalCode, customerPhoneNo, divId);

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerTableView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

}
