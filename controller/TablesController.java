package controller;


import dao.TableDao;
import dao.UserDao;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Tables;
import model.Users;

/**
 * FXML Controller class
 *
 * @author Tauhedul Amin
 */


public class TablesController implements Initializable {

    static Tables staticTables = new Tables();
    static String waiter;
    static String guests;
    int m=0,n=0;
    
    @FXML
    private TextField guest;
    
    @FXML
    private AnchorPane anchor_pane;
    @FXML
    private ComboBox<String> waiterBox;

    @FXML
    public void handle(ActionEvent event){
        Platform.exit();
        System.exit(0);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //FlowPane flow = new FlowPane();
        //flow.setVgap(10);
       // flow.setHgap(10);
      
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10,10,10,30));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
         
       // Label label = new Label();
        System.out.println(
                visualBounds.getWidth() + " x " + visualBounds.getHeight());
        double width=visualBounds.getWidth();
        UserDao userdao=new UserDao();
         List<Users> users = userdao.view();
       ObservableList<String> options = FXCollections.observableArrayList();
         for(Users u: users){
          if(u.getUserType().getTypeName().equals("Waiter")){
               System.out.print(u.getUserType().getTypeName());
               options.add(u.getUserName());
          }
        }
        // ObservableList obList = FXCollections.observableList(list);
        //  waiterBox.getItems().clear();
        waiterBox.getItems().addAll(options);
        waiterBox.promptTextProperty().setValue("Select Waiter");

        TableDao tabledao = new TableDao();
        List<Tables> tableList = tabledao.view();
        
        //table loop 
        for(Tables tables: tableList){
            if(tables.getTableStatus().contains("unoccupied")){
            //System.out.println(tables.getTableName());
            Button tableButton = new Button(tables.getTableName());
         
            setTableButtonProperty(tableButton,tables);
            
            gridpane.add(tableButton, m, n);
            m++;
            if(width<=1280){
                if(m>5){
                    n++;
                    m=0;
                }
            }
            else if(width>1280 && width <1600){
                if(m>6){
                    n++;
                    m=0;
                }
            }
            else{
                if(m>8){
                    n++;
                    m=0;
                } 
            }
             
            
            
            GridPane.setHalignment(tableButton, HPos.CENTER);
            
            tableButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent e){ 
                    staticTables = tables;
                   waiter = waiterBox.getSelectionModel().getSelectedItem();//.toString();
                   
                   
                    guests = guest.getText();
                  //  System.out.println(guests);
                  boolean isMyComboBoxEmpty = (waiterBox.getValue() == null);
                   if(waiter!=null){
                    //go to the pos
                            try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/billing.fxml"));
                                    Parent root = fxmlLoader.load();
                                    Stage stage;
                                    Node  source = (Node)  e.getSource(); 
                                    stage  = (Stage) source.getScene().getWindow();
                                    stage.getScene().setRoot(root);
                                 } catch (IOException ex) {
                                     Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                                 }

                   }
                   else if(waiter== null){
                       
                       System.out.println("please select waiter");
                       Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("NO WAITER SELECTED");
                        alert.setHeaderText("SELECT A WAITER");
                        alert.setContentText("select a waiter and add the guest number after that you can select your table");
                        alert.showAndWait();
                   }

                }
            
            });
            
            }//if ends
        }//for loop ends
       anchor_pane.getChildren().add(gridpane); 
    }    

    private void setTableButtonProperty(Button tableButton, Tables tables) {
        
        tableButton.setId(Integer.toString(tables.getId()));
        //styling table button
        tableButton.setMinWidth(115);
        tableButton.setMinHeight(50);
        
        tableButton.wrapTextProperty().setValue(true);
        tableButton.setStyle("-fx-background-color:#87B87F");
        tableButton.getStyleClass().add("button1");

    }

}
