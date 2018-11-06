package eChallan;

import javafx.scene.control.Alert;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.FileWriter;

public class ChallanGlobals {

    private String userNameRTO = null;
    private String userNameAadharID = null;
    private String vehicleNumber = null;
    private String challanDate = null;
    private String challanTime = null;
    private String aadharID = null;
    private String firstName = null;
    private String lastName = null;
    private String pinCode = null;
    private String streetNumber = null;
    private String licenseNumber = null;
    private String vehicleModel = null;
    private String phoneNumber = null;
    private String vehicleType = null;


    public ArrayList<String> rulesViolatedList = new ArrayList<>();
    private Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR);
    private Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);

    public Connection challanConn = null;
    public Statement challanStmt = null;
    public ResultSet challanResultSet = null;

    ChallanGlobals() {
        //dummy
    }

    public String getUserNameRTO() {
        return this.userNameRTO;
    }

    public String getUserNameAadharID() {
        return this.userNameAadharID;
    }

    public String getVehicleNumber() {
        return this.vehicleNumber;
    }

    public String getChallanDate() {
        return this.challanDate;
    }

    public String getChallanTime() {
        return this.challanTime;
    }

    public String getAadharID() {
        return this.aadharID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPinCode() {
        return this.pinCode;
    }

    public String getStreetNumber() {
        return this.streetNumber;
    }

    public String getLicenseNumber() {
        return this.licenseNumber;
    }

    public String getVehicleModel() {
        return this.vehicleModel;
    }

    public String getPhoneNumber() {
        return  this.phoneNumber;
    }

    public String getVehicleType() {
        return this.vehicleType;
    }

    public void setUserNameRTO(String uname) {
        this.userNameRTO = uname;
    }

    public void setUserNameAadharID(String uid) {
        this.userNameAadharID = uid;
    }

    public void setVehicleNumber(String vnumber) {
        this.vehicleNumber = vnumber;
    }

    public void setChallanDate(String cdate) {
        this.challanDate = cdate;
    }

    public void setChallanTime(String ctime) {
        this.challanTime = ctime;
    }

    public void setAadharID(String aid) {
        this.aadharID = aid;
    }

    public void setFirstName(String fname) {
        this.firstName = fname;
    }

    public void setLastName(String lname) {
        this.lastName = lname;
    }

    public void setPinCode(String pcode) {
        this.pinCode = pcode;
    }

    public void setStreetNumber(String snum) {
        this.streetNumber = snum;
    }

    public void setLicenseNumber(String lnum) {
        this.licenseNumber = lnum;
    }

    public void setVehicleModel(String vmodel) {
        this.vehicleModel = vmodel;
    }

    public void setPhoneNumber(String pnum) {
        this.phoneNumber = pnum;
    }

    public void setVehicleType(String vtype) {
        this.vehicleType = vtype;
    }

    public void inputError(String errorMsg) {
        inputErrorAlert.setTitle("Input Error");
        inputErrorAlert.setHeaderText(errorMsg);
        inputErrorAlert.showAndWait();
    }

    public void successMessage(String successMsg) {
        successAlert.setTitle("eChallan");
        successAlert.setHeaderText(successMsg);
        successAlert.showAndWait();
    }

    public void addRules(String ruleName) {
        rulesViolatedList.add(ruleName);
    }

    public String getRules() {
        return rulesViolatedList.toString();
    }

    public void writeToFile(String fileName, String userID) throws IOException {

        fileName = "D:\\eChallan\\src\\eChallan\\text\\" + fileName;
        FileWriter fw = null;
        try {
            fw= new FileWriter(fileName);
            fw.write(userID);
            fw.close();
        } catch(Exception e) {
            System.out.println(e);
        } finally {
            fw.close();
        }
    }

    public String readFromFile(String fileName) throws Exception {

        fileName = "D:\\eChallan\\src\\eChallan\\text\\" + fileName;
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

}