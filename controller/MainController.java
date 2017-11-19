/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class MainController implements Initializable {
    StackPane pane=new StackPane();
    
    @FXML
    private Button takeAwayButton;
    @FXML
    private VBox loginBox;
    @FXML
    private StackPane orderEntryPane;
    
    @FXML
    public void handle(ActionEvent event){
        Platform.exit();
        System.exit(0);
    }

    static String orderType;

    @FXML
    private void takeAwayButtonAction(ActionEvent event) {
       orderType="Take Away";
        try {
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/billing.fxml"));
           Parent root = fxmlLoader.load();
           Stage stage;
           Node  source = (Node)  event.getSource(); 
           stage  = (Stage) source.getScene().getWindow();
           stage.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void dineInButtonAction(ActionEvent event) {
        orderType="Dine In";
        try {
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/tables.fxml"));
           Parent root = fxmlLoader.load();
           Stage stage;
           Node  source = (Node)  event.getSource(); 
           stage  = (Stage) source.getScene().getWindow();
           stage.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       loginBox.setBorder(new Border(new BorderStroke(Color.BLACK, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

    }
  
}
