package eChallan;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.io.IOException;
import java.util.regex.Pattern;

public class UserLoginController {

    @FXML private JFXComboBox<Label> userCombo = new JFXComboBox<>();
    @FXML private JFXTextField userID = null;
    @FXML private JFXPasswordField userPassword = null;

    private Alert loginAlert = new Alert(Alert.AlertType.ERROR);
    private ChallanGlobals challanGlobals = new ChallanGlobals();

    @FXML
    protected void initialize() {
        userCombo.getItems().add(new Label("R.T.O"));
        userCombo.getItems().add(new Label("Civilian"));
        userCombo.setEditable(false);
        userCombo.setConverter(new StringConverter<Label>() {
            @Override
            public String toString(Label object) {
                return object==null? "" : object.getText();
            }

            @Override
            public Label fromString(String string) {
                return new Label(string);
            }
        });
    }

    @FXML
    private void userLogin() throws IOException {
        String identification = userID.getText();
        String password = userPassword.getText();
        String user = userCombo.getValue().toString();
        Boolean isRTO = user.contains("R.T.O");

        if(isRTO) {
            if(Pattern.matches("\\d{6}",identification) && Pattern.matches("((?=.*\\w)(?=.*[@#$%]).{6,20})",password)){
                challanGlobals.setUserNameRTO(identification);
                challanGlobals.writeToFile("rto.txt", challanGlobals.getUserNameRTO());
                loadDashboard("layouts/rto-dashboard-window.fxml");
            }
            else {
                loginError();
            }
        }
        else {
            if(Pattern.matches("\\d{12}",identification) && Pattern.matches("((?=.*\\w)(?=.*[@#$%]).{6,20})",password)){
                challanGlobals.setUserNameAadharID(identification);
                challanGlobals.writeToFile("aadhar.txt", challanGlobals.getUserNameAadharID());
                loadDashboard("layouts/civilian-dashboard-window.fxml");
            }
            else {
                loginError();
            }
        }
    }

    private void loginError() {
        loginAlert.setTitle("Login Error");
        loginAlert.setHeaderText("Incorrect Username or Password");
        loginAlert.showAndWait();
    }

    private void loadDashboard(String userType) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(userType));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("eChallan - Dashboard");
        stage.setScene(new Scene(root));
        stage.show();
    }
}