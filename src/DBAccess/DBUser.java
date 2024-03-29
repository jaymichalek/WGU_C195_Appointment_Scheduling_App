package DBAccess;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/** This is the user class to access users database.*/
public class DBUser {

    /** @return Returns a list of all users.*/
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> usersList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT User_ID, User_Name, Password FROM users";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int userId = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");

                User user = new User(userId, userName, password);
                usersList.add(user);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;

    }

    /** This method matches user input for userId and password from log in screen to list of users from users DB.
     * @param userId the user id input from text field in Log In Screen.
     * @param password the password input from text field in Log In Screen.
     * @return Returns an integer that is 0 if no match or 1 if there is a user match from users DB.*/
    public static int getUserMatch(String userId, String password) {

        int match = 0;

        try{

            String sql = "SELECT User_Name, Password FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            ResourceBundle rb = ResourceBundle.getBundle("Trans", Locale.getDefault());

                if (!resultSet.next()) {

                    if (Locale.getDefault().getLanguage().equals("fr")) {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(rb.getString("errorLogInTitle"));
                        alert.setHeaderText(rb.getString("errorLogInHeader"));
                        alert.setContentText(rb.getString("errorLogInContent"));

                        alert.showAndWait();

                    } else {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Logging In");
                        alert.setHeaderText("Username or password does not match our records.");
                        alert.setContentText("Please re-enter username and password.");

                        alert.showAndWait();

                    }

                    match = 0;

                } else {

                    if (Locale.getDefault().getLanguage().equals("fr")) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(rb.getString("infoLogInTitle"));
                        alert.setHeaderText(null);
                        alert.setContentText(rb.getString("infoLogInContent"));

                        alert.showAndWait();

                    } else {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Log-In Successful!");

                        alert.showAndWait();

                    }

                    match = 1;

                }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return match;

    }

    /** This method allows user to search user database for a matching user.
     * @param userName the username
     * @param password the password
     * @return Returns matched Object User with username and password input.*/
    public static ObservableList<User> getUserObjectMatch(String userName, String password) {
        ObservableList<User> idMatchList = FXCollections.observableArrayList();

        try{

            String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int userId  = resultSet.getInt("User_ID");
                String user_name = resultSet.getString("User_Name");
                String passWord = resultSet.getString("Password");

                User user = new User(userId, user_name, passWord);
                idMatchList.add(user);

            }

        }
         catch (SQLException e) {
            e.printStackTrace();
         }

        return idMatchList;
    }

}
