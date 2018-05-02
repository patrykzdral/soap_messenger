package com.pwr.fxfiles.node;

import com.pwr.fxfiles.node.custom.CustomMessageBox;
import com.pwr.model.listNode.ListNode;
import com.pwr.model.message.Message;
import com.pwr.model.message.MessageBody;
import com.pwr.model.nodes.AbstractNode;
import com.pwr.model.nodes.RouterNode;
import com.pwr.model.nodes.SimpleNode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;

public class NodeController  implements Initializable {
    public TextArea textAreaReceivedMessages;
    public TextArea textAreaMessage;
    public Label labelPort;
    public Label labelName;
    public Label labelForward;
    private SimpleDateFormat simpleDateFormat;
    private CustomMessageBox customMessageBox;
    public RadioButton radioButtonLayer;
    public RadioButton radioButtonAll;
    public RadioButton radioButtonExactly;
    public VBox vBoxToAll;
    public Button buttonToAll;
    public VBox vBoxExactly;
    public Button buttonExactly;
    public VBox vBoxLayer;
    public ComboBox comboBoxLayer;
    public Button buttonSendToLayer;
    public TableView <ListNode> tableViewNodes;
    public TableColumn <ListNode,String> tableColumnNumber;
    public TableColumn <ListNode,String> tableColumnName;
    public TableColumn <ListNode,String> tableColumnType;
    public TableColumn <ListNode,Integer> tableColumnPort;
    public TableColumn <ListNode,String> tableColumnHost;
    private ToggleGroup group;
    private AbstractNode node;

    public void initialize(URL location, ResourceBundle resources) {
        simpleDateFormat = new SimpleDateFormat("[HH:mm:ss]");
        customMessageBox = new CustomMessageBox();
        tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("layerNumberAndNodeName"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("nextPort"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("nodeType"));
        tableColumnHost.setCellValueFactory(new PropertyValueFactory<>("nextHost"));
        tableColumnPort.setCellValueFactory(new PropertyValueFactory<>("port"));
        tableViewNodes.setItems(FXCollections.observableArrayList(new ArrayList<>(Main.nodesObservableList)));
        setToggleGroup();
    }

    public void setOnlineNodes(Set<ListNode> onlineNodes){
        Main.nodesObservableList = FXCollections.observableSet(onlineNodes);
        refreshTableView();
    }

    public Set<ListNode> getOnlineNodes(){
        return Main.nodesObservableList;
    }


    public void buttonSendToLayer_onAction(ActionEvent actionEvent) {
        try {
            node.sendMessage(comboBoxLayer.getSelectionModel().getSelectedItem().toString(), "", "local_broadcast",new MessageBody(textAreaMessage.getText()));
            textAreaMessage.clear();
        } catch (SOAPException | JAXBException e) {
            e.printStackTrace();
        }
    }

    public void buttonSendToAll_onAction(ActionEvent actionEvent) {
        try {
            node.sendMessage("", "", "global_broadcast",new MessageBody(textAreaMessage.getText()));
            textAreaMessage.clear();
        } catch (SOAPException | JAXBException e) {
            e.printStackTrace();
        }
    }

    public void buttonSendExcactly_onAction(ActionEvent actionEvent) {
        if(tableViewNodes.getSelectionModel().getSelectedItem()!=null){
            try {
                ListNode listNode=tableViewNodes.getSelectionModel().getSelectedItem();
                node.sendMessage(listNode.getLayerNumberAndNodeName().substring(0,1), listNode.getLayerNumberAndNodeName().substring(2,3), "unicast",new MessageBody(textAreaMessage.getText()));
                textAreaMessage.clear();
            } catch (SOAPException | JAXBException e) {
                e.printStackTrace();
            }
        }else{
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Operacja wymiany informacji nie powiodła się.",
                    "Powód: nie zaznaczono konkretnego węzła").showAndWait();
        }

    }

    private void prepareContactModeComponents() {
        if(radioButtonAll.isSelected())  {
            vBoxToAll.setVisible(true);
            vBoxToAll.setDisable(false);
            vBoxToAll.setMinWidth(Control.USE_COMPUTED_SIZE);
            vBoxToAll.setMinHeight(Control.USE_COMPUTED_SIZE);
            vBoxToAll.setPrefWidth(Control.USE_COMPUTED_SIZE);
            vBoxToAll.setPrefHeight(Control.USE_COMPUTED_SIZE);
            vBoxToAll.setMaxWidth(Control.USE_COMPUTED_SIZE);
            vBoxToAll.setMaxHeight(Control.USE_COMPUTED_SIZE);

            vBoxLayer.setVisible(false);
            vBoxLayer.setDisable(true);
            vBoxLayer.setMinWidth(0);
            vBoxLayer.setMinHeight(0);
            vBoxLayer.setPrefWidth(0);
            vBoxLayer.setPrefHeight(0);
            vBoxLayer.setMaxWidth(0);
            vBoxLayer.setMaxHeight(0);

            vBoxExactly.setVisible(false);
            vBoxExactly.setDisable(true);
            vBoxExactly.setMinWidth(0);
            vBoxExactly.setMinHeight(0);
            vBoxExactly.setPrefWidth(0);
            vBoxExactly.setPrefHeight(0);
            vBoxExactly.setMaxWidth(0);
            vBoxExactly.setMaxHeight(0);
        }
        if(radioButtonLayer.isSelected()) {
            vBoxToAll.setVisible(false);
            vBoxToAll.setDisable(true);
            vBoxToAll.setMinWidth(0);
            vBoxToAll.setMinHeight(0);
            vBoxToAll.setPrefWidth(0);
            vBoxToAll.setPrefHeight(0);
            vBoxToAll.setMaxWidth(0);
            vBoxToAll.setMaxHeight(0);

            vBoxLayer.setVisible(true);
            vBoxLayer.setDisable(false);
            vBoxLayer.setMinWidth(Control.USE_COMPUTED_SIZE);
            vBoxLayer.setMinHeight(Control.USE_COMPUTED_SIZE);
            vBoxLayer.setPrefWidth(Control.USE_COMPUTED_SIZE);
            vBoxLayer.setPrefHeight(Control.USE_COMPUTED_SIZE);
            vBoxLayer.setMaxWidth(Control.USE_COMPUTED_SIZE);
            vBoxLayer.setMaxHeight(Control.USE_COMPUTED_SIZE);

            vBoxExactly.setVisible(false);
            vBoxExactly.setDisable(true);
            vBoxExactly.setMinWidth(0);
            vBoxExactly.setMinHeight(0);
            vBoxExactly.setPrefWidth(0);
            vBoxExactly.setPrefHeight(0);
            vBoxExactly.setMaxWidth(0);
            vBoxExactly.setMaxHeight(0);
        }

        if(radioButtonExactly.isSelected()) {
            vBoxToAll.setVisible(false);
            vBoxToAll.setDisable(true);
            vBoxToAll.setMinWidth(0);
            vBoxToAll.setMinHeight(0);
            vBoxToAll.setPrefWidth(0);
            vBoxToAll.setPrefHeight(0);
            vBoxToAll.setMaxWidth(0);
            vBoxToAll.setMaxHeight(0);

            vBoxLayer.setVisible(false);
            vBoxLayer.setDisable(true);
            vBoxLayer.setMinWidth(0);
            vBoxLayer.setMinHeight(0);
            vBoxLayer.setPrefWidth(0);
            vBoxLayer.setPrefHeight(0);
            vBoxLayer.setMaxWidth(0);
            vBoxLayer.setMaxHeight(0);

            vBoxExactly.setVisible(true);
            vBoxExactly.setDisable(false);
            vBoxExactly.setMinWidth(Control.USE_COMPUTED_SIZE);
            vBoxExactly.setMinHeight(Control.USE_COMPUTED_SIZE);
            vBoxExactly.setPrefWidth(Control.USE_COMPUTED_SIZE);
            vBoxExactly.setPrefHeight(Control.USE_COMPUTED_SIZE);
            vBoxExactly.setMaxWidth(Control.USE_COMPUTED_SIZE);
            vBoxExactly.setMaxHeight(Control.USE_COMPUTED_SIZE);
        }
    }
    private void setToggleGroup() {
        group = new ToggleGroup();
        radioButtonAll.setSelected(true);
        radioButtonAll.setToggleGroup(group);
        radioButtonExactly.setToggleGroup(group);
        radioButtonLayer.setToggleGroup(group);
        prepareContactModeComponents();
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    prepareContactModeComponents();
                }
            }
        });
    }

    public void showReceivedMessage(String sender, String message) {
        String receivedMessage = simpleDateFormat.format(new Date()) + " " + sender + ": " + message;
        textAreaReceivedMessages.appendText(receivedMessage + "\n");
    }

    public void showWarning(String exceptionMessage) {
        customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                "Operacja wymiany informacji nie powiodła się.",
                "Powód: " + exceptionMessage + ".").showAndWait();
    }

    public void setNode(SimpleNode layerNode) {
        node = layerNode;

        labelName.setText("Normal:  "+node.getNodeFullName());
        labelPort.setText(String.valueOf(node.getPort()));
        //labelLayerNumberAndNodeName.setText(node.getNodeFullName());
        labelForward.setText(node.getNextLayerNodeHost() + ":" + node.getNextLayerNodePort());

        node.startListening();
        //SimpleNode tempNode = SimpleNode(node.getNodeController(),node.getLayerNumber(), node.getNodeName(), node.getPort(), node.getPort(), String nextLayerNodeHost)
        Main.nodesObservableList.add(new ListNode(layerNode.getNodeFullName(), node.getPort(), "L", node.getNextLayerNodeHost(), node.getNextLayerNodePort()));
        //node.s

       try {
           // node.sendMessage(layerNode.getNodeFullName().substring(0,1), layerNode.getNodeFullName().substring(2,3), "unicast", new MessageBody(getOnlineNodes()));
            node.sendMessage("", "", "global_broadcast", new MessageBody(getOnlineNodes())); } catch (SOAPException | JAXBException e) {

        }


    }

    public void setNode(RouterNode routerNode) {
        this.node = routerNode;

        labelName.setText("Router:  " + node.getNodeFullName());
        labelPort.setText(String.valueOf(node.getPort()));
        //labelLayerNumberAndNodeName.setText(node.getNodeFullName());
        labelForward.setText(node.getNextLayerNodeHost() + ":" + node.getNextLayerNodePort());

        node.startListening();

        Main.nodesObservableList.add(new ListNode(routerNode.getNodeFullName(), node.getPort(), "R - " + routerNode.getNextRouterNodeHost() + ":" + routerNode.getNextRouterNodePort(), node.getNextLayerNodeHost(), node.getNextLayerNodePort()));

           try {
             node.sendMessage("", "", "global_broadcast", new MessageBody(getOnlineNodes()));
          } catch (SOAPException | JAXBException e) {

          }


    }

    private void refreshTableView(){
        tableViewNodes.setItems(FXCollections.observableArrayList(new ArrayList<>(Main.nodesObservableList)));
    }

}

