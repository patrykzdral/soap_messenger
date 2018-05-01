package com.pwr.fxfiles.node;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class NodeController  implements Initializable {
    public CheckBox checkBoxLayer;
    public CheckBox checkBoxAll;
    public VBox vBoxToAll;
    public Button buttonToAll;
    public VBox vBoxExactly;
    public Button buttonExactly;
    public VBox vBoxLayer;
    public ComboBox comboBoxLayer;
    public Button buttonSendToLayer;
    public CheckBox checkBoxExactly;
    public TableView tableViewNodes;
    public TableColumn tableColumnNumber;
    public TableColumn tableColumnName;
    public TableColumn tableColumnType;
    public TableColumn tableColumnPort;
    public TableColumn tableColumnHost;

    public void initialize(URL location, ResourceBundle resources) {

    }




    public void buttonSendToLayer_onAction(ActionEvent actionEvent) {
    }

    public void buttonSendToAll_onAction(ActionEvent actionEvent) {
    }

    public void buttonSendExcactly_onAction(ActionEvent actionEvent) {
    }
}
