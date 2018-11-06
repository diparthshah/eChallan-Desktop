package eChallan;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import java.util.regex.Pattern;

public class AddRecordController {

    @FXML private JFXTextField aadharID = new JFXTextField();
    @FXML private JFXTextField fname = new JFXTextField();
    @FXML private JFXTextField lname = new JFXTextField();
    @FXML private JFXTextField pinCode = new JFXTextField();
    @FXML private JFXTextField streetNo = new JFXTextField();
    @FXML private JFXTextField licenseNo = new JFXTextField();
    @FXML private JFXTextField vehicleNo = new JFXTextField();
    @FXML private JFXTextField vehicleModel = new JFXTextField();
    @FXML private JFXTextField phoneNo = new JFXTextField();
    @FXML private JFXComboBox<Label> vehicleTypeCombo = new JFXComboBox<>();

    private ChallanGlobals challanGlobals = new ChallanGlobals();

    private static final String TWO_WHEELER = "Two Wheeler";
    private static final String FOUR_WHEELER = "Four Wheeler";
    private static final String COMMERCIAL_TRANSPORT = "Commercial Transport";


    @FXML
    private void initialize() {
        vehicleTypeCombo.getItems().add(new Label(TWO_WHEELER));
        vehicleTypeCombo.getItems().add(new Label(FOUR_WHEELER));
        vehicleTypeCombo.getItems().add(new Label(COMMERCIAL_TRANSPORT));
        vehicleTypeCombo.setEditable(false);
        vehicleTypeCombo.setConverter(new StringConverter<Label>() {
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
    private void addRecord() throws Exception {

        String aadharIDText = aadharID.getText();
        String firstName = fname.getText();
        String lastName = lname.getText();
        String pinCodeText = pinCode.getText();
        String streetNumber = streetNo.getText();
        String licenseNumber = licenseNo.getText();
        String vehicleNumber = vehicleNo.getText();
        String vehicleModelText = vehicleModel.getText();
        String phoneNumber = phoneNo.getText();
        String vehicleTypeText = vehicleTypeCombo.getValue().toString();
        Boolean isTwoWheeler = vehicleTypeText.contains(TWO_WHEELER);
        Boolean isFourWheeler = vehicleTypeText.contains(FOUR_WHEELER);
        Boolean isCommercial = vehicleTypeText.contains(COMMERCIAL_TRANSPORT);
        Boolean goAhead = true;

        if(Pattern.matches("\\d{12}", aadharIDText)) {
            challanGlobals.setAadharID(aadharIDText);
        }
        else {
            challanGlobals.inputError("Invalid AadharID");
            goAhead = false;
        }

        if(Pattern.matches("\\w+", firstName)) {
            challanGlobals.setFirstName(firstName);
        }
        else {
            challanGlobals.inputError("Enter Correct FirstName");
            goAhead = false;
        }

        if(Pattern.matches("\\w+", lastName)) {
            challanGlobals.setLastName(lastName);
        }
        else {
            challanGlobals.inputError("Enter Correct LastName");
            goAhead = false;
        }

        if(Pattern.matches("\\d{6}", pinCodeText)) {
            challanGlobals.setPinCode(pinCodeText);
        }
        else {
            challanGlobals.inputError("Enter Correct PinCode");
            goAhead = false;
        }

        if(Pattern.matches("\\d{2}", streetNumber)) {
            challanGlobals.setStreetNumber(streetNumber);
        }
        else {
            challanGlobals.inputError("Enter Correct Street Number");
            goAhead = false;
        }

        if(Pattern.matches("\\d{6}", licenseNumber)) {
            challanGlobals.setLicenseNumber(licenseNumber);
        }
        else {
            challanGlobals.inputError("Enter Correct License Number");
            goAhead = false;
        }

        if(Pattern.matches("\\w{2}\\d{2}\\w{2}\\d{4}", vehicleNumber)) {
            challanGlobals.setVehicleNumber(vehicleNumber);
        }
        else {
            challanGlobals.inputError("Enter Correct Vehicle Number");
            goAhead = false;
        }

        if(Pattern.matches("\\w+", vehicleModelText)) {
            challanGlobals.setVehicleModel(vehicleModelText);
        }
        else {
            challanGlobals.inputError("Enter Correct Vehicle Model");
            goAhead = false;
        }

        if(Pattern.matches("\\d{10}", phoneNumber)) {
            challanGlobals.setPhoneNumber(phoneNumber);
        }
        else {
            challanGlobals.inputError("Enter Correct Phone Number");
            goAhead = false;
        }

        if(vehicleTypeText.isEmpty()) {
            challanGlobals.inputError("Select Vehicle Type");
        }
        else {
            if(isTwoWheeler) {
                challanGlobals.setVehicleType("Two Wheeler");
            }

            if(isFourWheeler) {
                challanGlobals.setVehicleType("Four Wheeler");
            }

            if(isCommercial) {
                challanGlobals.setVehicleType("Commercial Transport");
            }
        }

        if(goAhead) {
            addNewRecordInDB();
        }
    }

    private void addNewRecordInDB() throws Exception {

        try {
            challanGlobals.challanConn = DBConnector.getConnection();
            challanGlobals.challanStmt = challanGlobals.challanConn.createStatement();
            String SQLQuery = "INSERT INTO `civilian` VALUES ('"+challanGlobals.getAadharID()+"','"+challanGlobals.getFirstName()+"','"+challanGlobals.getLastName()+"','"+challanGlobals.getPinCode()+"','"+challanGlobals.getStreetNumber()+"','"+challanGlobals.getLicenseNumber()+"','"+challanGlobals.getVehicleNumber()+"','"+challanGlobals.getVehicleType()+"','"+challanGlobals.getVehicleModel()+"','"+challanGlobals.getPhoneNumber()+"');";
            challanGlobals.challanStmt.executeUpdate(SQLQuery);
            challanGlobals.successMessage("Record Added Successfully");
        } catch(Exception exception) {
            System.out.println(exception);
        } finally {
            challanGlobals.challanConn.close();
            challanGlobals.challanStmt.close();
        }
    }

}
