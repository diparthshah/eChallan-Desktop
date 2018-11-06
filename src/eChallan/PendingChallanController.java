package eChallan;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PendingChallanController {

    @FXML private Label challanIdLabel;
    @FXML private Label challanDateLabel;
    @FXML private Label challanTimeLabel;
    @FXML private Label rulesBrokenLabel;
    @FXML private Label fineAmountLabel;
    @FXML private Label officerInfoLabel;

    private ChallanGlobals challanGlobals = new ChallanGlobals();

    private String aadharNumber = null;

    private String challanIdString = null;
    private String challanDateString = null;
    private String challanTimeString = null;
    private String rulesBrokenString = null;
    private String fineAmountString = null;
    private String officerInfoString = null;

    private String myVehcileNumber = null;

    @FXML
    private void initialize() throws Exception{
        aadharNumber = challanGlobals.readFromFile("aadhar.txt");
        welcomeUser();
        getVehicleNumber();
        getChallanInfo();
        getOfficerInfo();
        setChallanInfo();
    }

    private void welcomeUser() throws Exception{
        try {
            challanGlobals.challanConn = DBConnector.getConnection();
            String SQLQuery = "SELECT fname,lname FROM `civilian` WHERE aadharid='"+aadharNumber+"' LIMIT 1;";
            challanGlobals.challanResultSet = challanGlobals.challanConn.createStatement().executeQuery(SQLQuery);
            while(challanGlobals.challanResultSet.next()) {
                String name = challanGlobals.challanResultSet.getString(1) + " " + challanGlobals.challanResultSet.getString(2);
                challanGlobals.successMessage("Welcome " + name);
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            challanGlobals.challanConn.close();
            challanGlobals.challanResultSet.close();
        }
    }

    private void getVehicleNumber() throws Exception {

        try {
            challanGlobals.challanConn = DBConnector.getConnection();
            String SQLQuery = "SELECT vehiclenum FROM `civilian` WHERE aadharid='"+aadharNumber+"' LIMIT 1;";
            challanGlobals.challanResultSet = challanGlobals.challanConn.createStatement().executeQuery(SQLQuery);
            while(challanGlobals.challanResultSet.next()) {
                myVehcileNumber = challanGlobals.challanResultSet.getString(1);
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            challanGlobals.challanConn.close();
            challanGlobals.challanResultSet.close();
        }

    }

    private void getChallanInfo() throws Exception {
        try {
            challanGlobals.challanConn = DBConnector.getConnection();
            String SQLQuery = "SELECT challanid,issuedate,issuetime,rulesbroken,fineamount,officerid FROM `challan` WHERE vehiclenumber='"+myVehcileNumber+"' AND paystatus='0' LIMIT 1;";
            challanGlobals.challanResultSet = challanGlobals.challanConn.createStatement().executeQuery(SQLQuery);
            while(challanGlobals.challanResultSet.next()) {
                challanIdString = challanGlobals.challanResultSet.getString(1);
                challanDateString = challanGlobals.challanResultSet.getString(2);
                challanTimeString = challanGlobals.challanResultSet.getString(3);
                rulesBrokenString = challanGlobals.challanResultSet.getString(4);
                fineAmountString = challanGlobals.challanResultSet.getString(5);
                officerInfoString = challanGlobals.challanResultSet.getString(6);
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            challanGlobals.challanConn.close();
            challanGlobals.challanResultSet.close();
        }
    }

    private void getOfficerInfo() throws Exception {
        try {
            challanGlobals.challanConn = DBConnector.getConnection();
            String SQLQuery = "SELECT * FROM `officer` WHERE officerid='"+officerInfoString+"' LIMIT 1;";
            challanGlobals.challanResultSet = challanGlobals.challanConn.createStatement().executeQuery(SQLQuery);
            while(challanGlobals.challanResultSet.next()) {
                String officerid = challanGlobals.challanResultSet.getString(1);
                String officername = challanGlobals.challanResultSet.getString(2);
                String officerdesg = challanGlobals.challanResultSet.getString(3);
                String policestn = challanGlobals.challanResultSet.getString(4);
                officerInfoString = " " + officerid + " " + officername + " " + officerdesg + " " + policestn;
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            challanGlobals.challanConn.close();
            challanGlobals.challanResultSet.close();
        }
    }

    private void setChallanInfo() {
        challanIdLabel.setText(challanIdString);
        challanDateLabel.setText(challanDateString);
        challanTimeLabel.setText(challanTimeString);
        rulesBrokenLabel.setText(rulesBrokenString);
        fineAmountLabel.setText(fineAmountString);
        officerInfoLabel.setText(officerInfoString);
    }

    @FXML
    private void markAsPaid() throws Exception {
        try {
            challanGlobals.challanConn = DBConnector.getConnection();
            challanGlobals.challanStmt = challanGlobals.challanConn.createStatement();
            String SQLQuery = "UPDATE `challan` SET paystatus = '1' WHERE challanid = '"+challanIdString+"' ;";
            challanGlobals.challanStmt.executeUpdate(SQLQuery);
            challanGlobals.successMessage("Challan Payment Successful");
        } catch (Exception exeception) {
            System.err.print(exeception);
        } finally {
            challanGlobals.challanConn.close();
            challanGlobals.challanStmt.close();
        }

    }
}
