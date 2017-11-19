package controller;

import dao.UserDao;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Enamul Karim
 */
public class LoginController implements Initializable {
   public static int userId;
    @FXML
    private TextField userName; 
    
    @FXML
    private PasswordField userPassword; 
    
    @FXML
    public void onEnter(ActionEvent ae){
        System.out.println("Enter pressed");
        submitButtonAction(ae);
    }
    @FXML
    private void submitButtonAction(ActionEvent event) {
          System.out.println(userName.getText());
          System.out.println(userPassword.getText());
          UserDao userdao=new UserDao();
          boolean value=userdao.validate(userName.getText(), userPassword.getText());
          
           System.out.println(value);
           if(value){
                try {
                   userId=userdao.getId(userName.getText());
                   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/billing.fxml"));
                   Parent root = fxmlLoader.load();
                   Stage stage;
                   Node  source = (Node)  event.getSource(); 
                   stage  = (Stage) source.getScene().getWindow();
                   stage.getScene().setRoot(root);
                   stage.setMaximized(true);
                   stage.resizableProperty().setValue(Boolean.TRUE);
//                    Parent root = FXMLLoader.load(getClass().getResource("/view/tables.fxml"));
//
//                    Scene scene = new Scene(root);
//                    
//                    Stage stage=new Stage();
//                   // stage.setMaximized(true);
//                    stage.setScene(scene);
//                    stage.setTitle("Login");
//                    stage.setResizable(false);
//                    stage.sizeToScene();
//                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
       }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
