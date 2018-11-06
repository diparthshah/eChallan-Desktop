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

public class CheckHistoryController {

    @FXML private JFXTreeTableView<ChallanDetails> checkHistoryTable;
    private ChallanGlobals challanGlobals = new ChallanGlobals();

    @FXML
    private void initialize() throws Exception{

        String officerID = challanGlobals.readFromFile("rto.txt");

        JFXTreeTableColumn<ChallanDetails, String> challanID = new JFXTreeTableColumn("ChallanID");
        challanID.setPrefWidth(180);

        challanID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ChallanDetails, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ChallanDetails, String> param) {
                return param.getValue().getValue().challanID;
            }
        });

        JFXTreeTableColumn<ChallanDetails, String> vehicleNumber = new JFXTreeTableColumn("Vehicle Number");
        vehicleNumber.setPrefWidth(180);

        vehicleNumber.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ChallanDetails, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ChallanDetails, String> param) {
                return param.getValue().getValue().vehicleNumber;
            }
        });

        JFXTreeTableColumn<ChallanDetails, String> challanDate = new JFXTreeTableColumn("Date");
        challanDate.setPrefWidth(180);

        challanDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ChallanDetails, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ChallanDetails, String> param) {
                return param.getValue().getValue().challanDate;
            }
        });

        JFXTreeTableColumn<ChallanDetails, String> challanTime = new JFXTreeTableColumn("Time");
        challanTime.setPrefWidth(180);

        challanTime.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ChallanDetails, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ChallanDetails, String> param) {
                return param.getValue().getValue().challanTime;
            }
        });

        JFXTreeTableColumn<ChallanDetails, String> fineAmount = new JFXTreeTableColumn("Fine Amount");
        fineAmount.setPrefWidth(180);

        fineAmount.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ChallanDetails, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ChallanDetails, String> param) {
                return param.getValue().getValue().fineAmount;
            }
        });

        ObservableList<ChallanDetails> challanDetails = FXCollections.observableArrayList();

        try {
            challanGlobals.challanConn = DBConnector.getConnection();
            String SQLQuery = "SELECT * FROM `challan` WHERE officerid='"+officerID+"';";
            challanGlobals.challanResultSet = challanGlobals.challanConn.createStatement().executeQuery(SQLQuery);
            while (challanGlobals.challanResultSet.next()) {
                challanDetails.add(new ChallanDetails(challanGlobals.challanResultSet.getString(1), challanGlobals.challanResultSet.getString(3), challanGlobals.challanResultSet.getString(4), challanGlobals.challanResultSet.getString(5), challanGlobals.challanResultSet.getString(7)));
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            challanGlobals.challanConn.close();
            challanGlobals.challanResultSet.close();
            //challanGlobals.challanStmt.close();
        }

        final TreeItem<ChallanDetails> root = new RecursiveTreeItem<ChallanDetails>(challanDetails, RecursiveTreeObject::getChildren);
        checkHistoryTable.getColumns().setAll(challanID, vehicleNumber, challanDate, challanTime, fineAmount);
        checkHistoryTable.setRoot(root);
        checkHistoryTable.setShowRoot(false);


    }
}

class ChallanDetails extends RecursiveTreeObject<ChallanDetails> {
    StringProperty challanID;
    StringProperty vehicleNumber;
    StringProperty challanDate;
    StringProperty challanTime;
    StringProperty fineAmount;

    public ChallanDetails(String challanID, String vehicleNumber, String challanDate, String challanTime, String fineAmount)
    {
        this.challanID = new SimpleStringProperty(challanID);
        this.vehicleNumber  = new SimpleStringProperty(vehicleNumber);
        this.challanDate = new SimpleStringProperty(challanDate);
        this.challanTime = new SimpleStringProperty(challanTime);
        this.fineAmount = new SimpleStringProperty(fineAmount);
    }
}
