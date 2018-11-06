package eChallan;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class CivilianHistoryController {

    @FXML private JFXTreeTableView<CivilianDetails> checkCivilianHistoryTable;
    private ChallanGlobals challanGlobals = new ChallanGlobals();
    private String civilianID = null;
    private String vehicleNumber = null;

    @FXML
    private void initialize() throws Exception {

        civilianID = challanGlobals.readFromFile("aadhar.txt");
        getVehicleNumber();

        JFXTreeTableColumn<CivilianDetails, String> challanID = new JFXTreeTableColumn("ChallanID");
        challanID.setPrefWidth(160);

        challanID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CivilianDetails, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CivilianDetails, String> param) {
                return param.getValue().getValue().challanID;
            }
        });

        JFXTreeTableColumn<CivilianDetails, String> challanDate = new JFXTreeTableColumn("Date");
        challanDate.setPrefWidth(160);

        challanDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CivilianDetails, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CivilianDetails, String> param) {
                return param.getValue().getValue().challanDate;
            }
        });

        JFXTreeTableColumn<CivilianDetails, String> challanTime = new JFXTreeTableColumn("Time");
        challanTime.setPrefWidth(160);

        challanTime.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CivilianDetails, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CivilianDetails, String> param) {
                return param.getValue().getValue().challanTime;
            }
        });

        JFXTreeTableColumn<CivilianDetails, String> officerID = new JFXTreeTableColumn("Officer ID");
        officerID.setPrefWidth(160);

        officerID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CivilianDetails, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CivilianDetails, String> param) {
                return param.getValue().getValue().officerID;
            }
        });

        JFXTreeTableColumn<CivilianDetails, String> fineAmount = new JFXTreeTableColumn("Fine Amount");
        fineAmount.setPrefWidth(140);

        fineAmount.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CivilianDetails, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CivilianDetails, String> param) {
                return param.getValue().getValue().fineAmount;
            }
        });

        JFXTreeTableColumn<CivilianDetails, String> payStatus = new JFXTreeTableColumn("Pay Status");
        payStatus.setPrefWidth(140);

        payStatus.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CivilianDetails, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CivilianDetails, String> param) {
                return param.getValue().getValue().payStatus;
            }
        });

        ObservableList<CivilianDetails> CivilianDetails = FXCollections.observableArrayList();

        try {
            challanGlobals.challanConn = DBConnector.getConnection();
            String SQLQuery = "SELECT challanid,officerid,issuedate,issuetime,fineamount,paystatus FROM `challan` WHERE vehiclenumber='"+vehicleNumber+"';";
            challanGlobals.challanResultSet = challanGlobals.challanConn.createStatement().executeQuery(SQLQuery);
            while (challanGlobals.challanResultSet.next()) {
                CivilianDetails.add(new CivilianDetails(challanGlobals.challanResultSet.getString(1), challanGlobals.challanResultSet.getString(2), challanGlobals.challanResultSet.getString(3), challanGlobals.challanResultSet.getString(4), challanGlobals.challanResultSet.getString(5), challanGlobals.challanResultSet.getString(6)));
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            challanGlobals.challanConn.close();
            challanGlobals.challanResultSet.close();
            //challanGlobals.challanStmt.close();
        }

        final TreeItem<CivilianDetails> root = new RecursiveTreeItem<CivilianDetails>(CivilianDetails, RecursiveTreeObject::getChildren);
        checkCivilianHistoryTable.getColumns().setAll(challanID, officerID, challanDate, challanTime, fineAmount, payStatus);
        checkCivilianHistoryTable.setRoot(root);
        checkCivilianHistoryTable.setShowRoot(false);


    }

    private void getVehicleNumber() throws Exception {
        try {
            challanGlobals.challanConn = DBConnector.getConnection();
            String SQLQuery = "SELECT vehiclenum FROM `civilian` WHERE aadharid='"+civilianID+"' LIMIT 1;";
            challanGlobals.challanResultSet = challanGlobals.challanConn.createStatement().executeQuery(SQLQuery);
            while(challanGlobals.challanResultSet.next()) {
                vehicleNumber = challanGlobals.challanResultSet.getString(1);
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            challanGlobals.challanConn.close();
            challanGlobals.challanResultSet.close();
        }
    }
}

class CivilianDetails extends RecursiveTreeObject<CivilianDetails> {
    StringProperty challanID;
    StringProperty challanDate;
    StringProperty challanTime;
    StringProperty officerID;
    StringProperty fineAmount;
    StringProperty payStatus;

    public CivilianDetails(String challanID, String officerID, String challanDate, String challanTime, String fineAmount, String payStatus)
    {
        this.challanID = new SimpleStringProperty(challanID);
        this.challanDate = new SimpleStringProperty(challanDate);
        this.challanTime = new SimpleStringProperty(challanTime);
        this.officerID = new SimpleStringProperty(officerID);
        this.fineAmount = new SimpleStringProperty(fineAmount);
        this.payStatus = new SimpleStringProperty(payStatus);
    }
}
