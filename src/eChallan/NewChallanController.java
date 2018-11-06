package eChallan;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.Random;

public class NewChallanController {

    @FXML private JFXTextField violatedVehicleNumber = new JFXTextField();
    @FXML private JFXDatePicker challanDateField = new JFXDatePicker();
    @FXML private JFXTimePicker challanTimeField = new JFXTimePicker();
    @FXML private JFXCheckBox signalViolationRule = new JFXCheckBox();
    @FXML private JFXCheckBox drivingDocumentsRule = new JFXCheckBox();
    @FXML private JFXCheckBox speedLimitRule = new JFXCheckBox();
    @FXML private JFXCheckBox roadSafetyRule = new JFXCheckBox();

    @FXML private ChallanGlobals challanGlobals = new ChallanGlobals();
    @FXML private Random random = new Random();

    private Integer fineAmount = 0;
    private String challanID;
    private String officerName = null;

    @FXML
    private void initialize() throws Exception {
        welcomeOfficer();
        challanGlobals.successMessage("Welcome " + officerName);
    }

    @FXML
    private void createChallan() throws Exception {

        String vehicleNumberString = violatedVehicleNumber.getText();
        String challanDate = challanDateField.getValue().toString();
        String challanTime = challanTimeField.getValue().toString();
        Boolean isSignalViolation = signalViolationRule.isSelected();
        Boolean isDrivingDocuments = drivingDocumentsRule.isSelected();
        Boolean isSpeedLimit = speedLimitRule.isSelected();
        Boolean isRoadSafety = roadSafetyRule.isSelected();
        Boolean goAhead = true;

        if(Pattern.matches("\\w{2}\\d{2}\\w{2}\\d{4}",vehicleNumberString)) {
            challanGlobals.setVehicleNumber(vehicleNumberString);
        }
        else {
            challanGlobals.inputError("Invalid Vehicle Number");
            goAhead = false;
        }

        if(challanDate.isEmpty()) {
            challanGlobals.inputError("Date field is empty");
            goAhead = false;
        }
        else {
            challanGlobals.setChallanDate(challanDate);
        }

        if(challanTime.isEmpty()) {
            challanGlobals.inputError("Time field is empty");
            goAhead = false;
        }
        else {
            challanGlobals.setChallanTime(challanTime);
        }

        if (!isSignalViolation && !isDrivingDocuments && !isSpeedLimit && !isRoadSafety) {
            challanGlobals.inputError("Tick Rules Broken");
            goAhead = false;
        }
        else {
            if(isSignalViolation) {
                challanGlobals.addRules("Signal Violation");
                fineAmount = fineAmount + 200;
            }
            if(isDrivingDocuments) {
                challanGlobals.addRules("No Driving Documents");
                fineAmount = fineAmount + 300;
            }
            if(isSpeedLimit) {
                challanGlobals.addRules("Speed Limit Violation");
                fineAmount = fineAmount + 500;
            }
            if(isRoadSafety) {
                challanGlobals.addRules("Road Safety Violation");
                fineAmount = fineAmount + 700;
            }
        }
        if(goAhead) {
            insertNewChallanInDB();
            challanGlobals.rulesViolatedList = new ArrayList<>();
            fineAmount = 0;
        }
    }

    private void insertNewChallanInDB() throws Exception {

        try {
            Integer randomNumber = random.nextInt(100);
            challanID = randomNumber.toString();
            String officerID = challanGlobals.readFromFile("rto.txt");
            challanGlobals.challanConn = DBConnector.getConnection();
            challanGlobals.challanStmt = challanGlobals.challanConn.createStatement();
            String SQLQuery = "INSERT INTO `challan` VALUES ('"+challanID+"','"+officerID+"','"+challanGlobals.getVehicleNumber()+"','"+challanGlobals.getChallanDate()+"','"+challanGlobals.getChallanTime()+"','"+challanGlobals.getRules()+"','"+fineAmount.toString()+"','0');";
            challanGlobals.challanStmt.executeUpdate(SQLQuery);
            challanSuccess();
        } catch (Exception exeception) {
            System.err.print(exeception);
        } finally {
            challanGlobals.challanConn.close();
            challanGlobals.challanStmt.close();
        }
    }

    private void challanSuccess() throws Exception {

        try {
            challanGlobals.challanConn = DBConnector.getConnection();
            String SQLQuery = "SELECT aadharid,fname,lname,phonenum FROM `civilian` WHERE vehiclenum='"+challanGlobals.getVehicleNumber()+"' LIMIT 1;";
            challanGlobals.challanResultSet = challanGlobals.challanConn.createStatement().executeQuery(SQLQuery);
            while(challanGlobals.challanResultSet.next()) {
                String aadharid = challanGlobals.challanResultSet.getString(1);
                String name = challanGlobals.challanResultSet.getString(2) + challanGlobals.challanResultSet.getString(3);
                String phoneNumber = challanGlobals.challanResultSet.getString(4);
                String message = "Challan Successfully Created\n" + "ChallanID: " + challanID + "\nAadhar Number: " + aadharid + "\nName: " +name + "\nFine Amount: " + fineAmount + "\nPhone Numeber: " + phoneNumber;
                challanGlobals.successMessage(message);
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            challanGlobals.challanConn.close();
            challanGlobals.challanResultSet.close();
        }
    }

    private void welcomeOfficer() throws Exception {
        try {
            challanGlobals.challanConn = DBConnector.getConnection();
            String SQLQuery = "SELECT officername from `officer` where officerid = '"+challanGlobals.readFromFile("rto.txt")+"' LIMIT 1;";
            challanGlobals.challanResultSet = challanGlobals.challanConn.createStatement().executeQuery(SQLQuery);
            while(challanGlobals.challanResultSet.next()) {
                officerName = challanGlobals.challanResultSet.getString(1);
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            challanGlobals.challanConn.close();
            challanGlobals.challanResultSet.close();
        }
    }
}