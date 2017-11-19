package controller;

import dao.BackUpDatabase;
import java.util.*;
import dao.IngredientsDao;
import dao.MenuCategoryDao;
import dao.RecipeDao;
import dao.RecipeIngredientsDao;
import dao.SalesDetailsDao;
import dao.SalesInfoDao;
import dao.TableDao;
import dao.UserDao;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Ingredients;
import model.MenuCategory;
import model.Recipe;
import model.RecipeIngredients;
import model.SalesDetails;
import model.SalesInfo;
import model.Tables;
import model.Users;

public class BillingController implements Initializable {
    SalesInfoDao salesInfoDao = new SalesInfoDao(); 
    static Tables staticTables = new Tables();
    static String waiter, guests;
    static boolean buttonFlag = false;
    static boolean waiterFlag = false;
    int m = 0, n = 0, p = 1, quantity, row = 0, column = 0, i = 0;
    double sum,vat1=0;
    private long sessionId;
    public static int k = 0, j;
    double price, discountPrice, vat, order_total;
    static String orderType;
    TextField toBePaidAmount;
    TextField toBePaidDiscount;
    final VBox dineInBoxTemp = new VBox();
    final VBox takeAwayBoxTemp = new VBox();

    ObservableList<SalesInfo> data = FXCollections.observableArrayList();
    ObservableList<String> tableData = FXCollections.observableArrayList();
    ObservableList<SalesInfo> data1 = FXCollections.observableArrayList();
    //a variable to keep the quantity on click
    SalesInfo salesInfo = new SalesInfo();
    GridPane grid = new GridPane();
    GridPane recipeGrid = new GridPane();
    //GridPane billingRow=new GridPane();
    TextField qty;
    HashMap<Integer, String> id_qty = new HashMap<>();
    HashMap<Integer, Double> subtotalPrice = new HashMap<>();

    ArrayList<String> bankNames = new ArrayList();
    Button updateOrderFinalButton=new Button("Update");
   
    @FXML
    private TextField guest,guestBox;
    @FXML
    private HBox discountBox;
    @FXML
    private Label discLabel;
    @FXML
    private AnchorPane anchor_pane,waiterPane;
    @FXML
    private HBox fBox,dineInDetails;
    @FXML
    ScrollPane scrollPane,takeAwayScrollPane,liveOrderScrollPane;
    
    @FXML
    private TableView liveOrderTable, billingOrderTable;
    @FXML
    private TableColumn tableCol, subTotalCol, vatCol, discountCol, totalCol,sessionCol;
    @FXML
    private TableColumn tableCol1, subTotalCol1, vatCol1, discountCol1, totalCol1,sessionCol1;
    @FXML
    private TabPane MenuTab;
    @FXML
    private Button takeAwayButton, dineInButton, updateOrderButton, settleButton, deleteButton, settleButton1,finalSubmit,guestBillButton;
    @FXML
    private Tab billOrderTab, liveOrderTab, orderEntryTab;
    @FXML
    private VBox billOrderVBox, vb, billing_section, dineInBox, tableVbox, takeAwayBox,updateDetailsBox;
    @FXML
    private GridPane billOrderGrid;
    @FXML
    private StackPane orderEntryPane;
    @FXML
    private Label subTotal, orderTotal, vatLabel, discountLabel;
    @FXML
    private TextField discountField;
    @FXML
    private ComboBox cbox,tableComboBox;
    
    TextField discField = new TextField();

    @FXML
    private void clearButtonAction(ActionEvent event) {
        Node source = (Node) event.getSource();
        moveToFrontPage(source);
    }
    @FXML
    private void logoutAction(ActionEvent event) {
          try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage;
            Node source = (Node) event.getSource();
            stage = (Stage) source.getScene().getWindow();
            stage.getScene().setRoot(root);
             stage.setMaximized(false);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML
    private void newOrderButtonAction(ActionEvent event) {
        Node source = (Node) event.getSource();
        moveToFrontPage(source);
    }

    @FXML
    private void setGuestTextfield(ActionEvent event){
       Object source = event.getSource();
        if (source instanceof Button) { //should always be true in your example
            Button clickedBtn = (Button) source; // that's the button that was clicked
            System.out.println(clickedBtn.getId()); // prints the id of the button
            String id=clickedBtn.getId();
            if(id.equals("oneButton")){
                guest.setText("1");
            }
            else if(id.equals("twoButton")){
                guest.setText("2");
            }
             else if(id.equals("threeButton")){
                guest.setText("3");
            }
             else if(id.equals("fourButton")){
                guest.setText("4");
            }
             else if(id.equals("fiveButton")){
                guest.setText("5");
            }
             else if(id.equals("sixButton")){
                guest.setText("6");
            }
             else if(id.equals("sevenButton")){
                guest.setText("7");
            }
             else if(id.equals("eightButton")){
                guest.setText("8");
            }
             else if(id.equals("nineButton")){
                guest.setText("9");
            }
           
        } 
    }
    
    @FXML
    private void billingTakeAwayButton(ActionEvent event) {
        billingOrder("Take Away");
    }

    @FXML
    private void billingDineInButton(ActionEvent event) {
        billingOrder("Dine In");
    }

    @FXML
    private void billingAllOrderButton(ActionEvent event) {
        billingOrder("");
    }

    @FXML
    private void takeAwayButton(ActionEvent event) {
        Order("Take Away");
    }

    @FXML
    private void liveOrderButton(ActionEvent event) {
        Order("Dine In");
    }

    @FXML
    private void allOrderButton(ActionEvent event) {
        Order("");
    }

    @FXML
    public void handle(ActionEvent event) throws IOException {
        ConnectionServer con = new ConnectionServer();
//updating sales 
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Do you want to update your todays sale?");
                                alert.setHeaderText("UPDATE SALES");
                                alert.setContentText("if you have no order live then update to the server");
                                //alert.showAndWait();

                                //alert method
                                try{
                               Optional<ButtonType> result = alert.showAndWait();

                                        if (result.get()== ButtonType.OK) {
                                             con.connectServer();

                                        }else {
                                            System.out.println("canceled");
                                        }
                                //alert method ends
                                }catch(Exception ex){
                                    System.out.println("No Such Element Exception");
                                }
//updating sales ends
        
        
        
        

        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void takeAwayButtonAction(ActionEvent event) {
        orderType = "Take Away";
        takeAwayButton.setVisible(false);
        dineInButton.setVisible(false);
        guest.setVisible(false);
        setCategories();
    }

    @FXML
    private void dineInButtonAction(ActionEvent event) {
        orderType = "Dine In";
        takeAwayButton.setVisible(false);
        dineInButton.setVisible(false);
        tableVbox.setVisible(true);
        guest.setVisible(true);
        getTableView();
    }

    @FXML
    private void finalSubmitButton(ActionEvent event) {
        if(vb.getChildren().isEmpty()){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Empty Submission");
            alert.setHeaderText("No Items to be submitted");
            alert.setContentText("Please enter items to be submitted");
            alert.showAndWait();
        }else{
     
        //unique sessionId using date and time
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddHHmmss");
        String sessionId = ft.format(dNow);
        //unique sessionId using date and time
        
        //setting the salesInfo
        salesInfo.setSessionId(Long.parseLong(sessionId));
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        salesInfo.setDate(date); 
        salesInfo.setGuestNumber(guest.getText());
        if (orderType.equals("Dine In")) {
            //sttting table flag to reserved
            TableDao tabledao = new TableDao();
            staticTables.setTableStatus("reserved");
            tabledao.update(staticTables);
            //setting table flag to reserved ends
            salesInfo.setTable(staticTables);
            UserDao userdao = new UserDao();
            int waiterId = userdao.getId(waiter);
            //System.out.println("waiter id is "+waiterId);
            salesInfo.setWaiterId((Users) userdao.checkdao(waiterId));
        }
        if (orderType.equals("Take Away")) {
            TableDao tabledao = new TableDao();
            Tables noTables = (Tables) tabledao.getTable("table 0");
            salesInfo.setTable(noTables);
            UserDao userDao = new UserDao();
            salesInfo.setWaiterId((Users) userDao.checkdao(LoginController.userId));
            //System.out.println("waiter id is "+waiterId);
        }
        salesInfo.setGuestBillRequest(false);
        salesInfo.setOrderType(orderType);
        salesInfo.setOrderStatus("Live");
        salesInfo.setDiscount(discountPrice + "");
        
        
        
        
        salesInfo.setVat(RoundTo2Decimals(vat));
        System.out.println("Just Vat is "+vat);
        
        //getting the subtotal price in salesinfo from the frontend subtotal tag
        String[] parts = subTotal.getText().split(":");
        double subTotalTag = Double.parseDouble(parts[1]);
        salesInfo.setSubTotal(subTotalTag);
        //getting the subtotal price in salesinfo from the frontend subtotal tag ends

        //SalesInfoDao salesinfodao = new SalesInfoDao();
        //enamul
       

        String[] orderTotalParts=orderTotal.getText().split(": ");
        String order_total=orderTotalParts[1];
        System.out.println("order total is "+order_total);
        salesInfo.setOrderTotal(Double.parseDouble(order_total));
        if(guest.getText()!=""){
          salesInfoDao.insert(salesInfo);
            for (Map.Entry m : id_qty.entrySet()) {
              int id = (int) m.getKey();
              String value = m.getValue().toString();
              //System.out.println("id_qty id is: "+id);
              //System.out.println("id_qty value is: "+ value);
              RecipeDao recipeDao = new RecipeDao();
              Recipe recipe = (Recipe) recipeDao.checkdao(id);
              SalesDetails salesDetails = new SalesDetails();
              salesDetails.setSalesInfo(salesInfo);
              salesDetails.setQuantity(Long.parseLong(value));
              salesDetails.setRecipe(recipe);
              SalesDetailsDao salesdetailsdao = new SalesDetailsDao();
              salesdetailsdao.insert(salesDetails);
          }
          //going to new order page 
          Node source = (Node) event.getSource();
          moveToFrontPage(source);
          //going to new order page ends   
        }
        else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Empty Guest");
            alert.setHeaderText("Enter Guest Number");
            alert.setContentText("Enter Guest Number");
            alert.showAndWait();
        }

        
        }
    }

    //setSubtotal in the field
    public void setSubtotal(UpdateOrderController updateOrder) {
            System.out.println("Came to the subtotal");
       
            double totalPrice = updateOrder.getSalesDetailsPrice();
            price = (int) Math.round(price);
            subTotal.setText("Sub Total: "+updateOrder.getSalesInfo().getSubTotal());
            
            //System.out.println("THe order status is "+updateOrder.getSalesInfo().getOrderType());

            if((!discField.getText().trim().isEmpty())){
                discountPrice = getDiscount(discField.getText(), updateOrder.getSalesInfo().getSubTotal());
                //vat = getVat(order_total);
                
                //eclipse for enum
                float vatFloat = Float.parseFloat(updateOrder.getVat()+"");
                
                vatLabel.setText("Vat: " + vatFloat);
                
                
                
                
                orderTotal.setText("Order Total: " + Math.round((updateOrder.getSalesInfo().getSubTotal()-discountPrice+updateOrder.getExclusiveVat())));
                
                discountLabel.setText("Discount " + discountPrice);

                updateOrder.setDiscount(discountPrice+"");
            }else{
                                //orderTotal.setText("Order Total: NaN Tk.");
                                discountPrice = 0;
                                //vat = getVat(order_total);
                                float vatFloat = Float.parseFloat(updateOrder.getVat()+"");
                                vatLabel.setText("Vat: " + vatFloat);
                                //discountField.setText("0");
                                //bar
                                discountLabel.setText("Discount: 0");
                                
                                
                                orderTotal.setText("Order Total: " + Math.round((updateOrder.getSalesInfo().getSubTotal() + discountPrice+updateOrder.getExclusiveVat())));
            

            }
            orderTotal.setText("Order Total: " + Math.round((updateOrder.getSalesDetailsPrice())));
            subTotal.setText("Sub Total: " + updateOrder.getSalesInfo().getSubTotal());
            discountLabel.setText("Discount: " + updateOrder.getDiscount());
            float vatFloat = Float.parseFloat(updateOrder.getVat()+"");
            vatLabel.setText("Vat: " + vatFloat);
            //setDiscountField1(updateOrder);



     
        updateOrderFinalButton.setOnAction((ActionEvent e) -> {

                    if(guestBox.getText().isEmpty() && updateOrder.getSalesInfo().getOrderType().equals("Dine In")){
                        //System.out.println("HOISEEEEEEEffffffffffffffffffffffffffffffffff");
                               Alert alert = new Alert(AlertType.INFORMATION);
                               alert.setTitle("No Guest Selected");
                               alert.setHeaderText("Empty Guest Field");
                               alert.setContentText("Please enter a guest number");
                               alert.showAndWait();
                        
                    }
                    else{
                        
                        updateOrder.getSalesInfo().setGuestNumber(guestBox.getText());
                        orderTotal.setText("Order Total: " + Math.round((updateOrder.getSalesDetailsPrice())));
                        subTotal.setText("Sub Total: " + updateOrder.getSalesInfo().getSubTotal());
                        discountLabel.setText("Discount: " + updateOrder.getDiscount());
                        vatLabel.setText("Vat: " + updateOrder.getVat());
                        updateOrder.updateSalesInDatabase();
                        Node source = (Node) e.getSource();
                        moveToFrontPage(source); 
                    }
        
        });

    }
    //setSubtotal in the field ends

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        discountField.setText("0");
        updateDetailsBox.setVisible(false);
        cbox.getItems().addAll(
            "Dine In",
            "Take Away");
        cbox.getSelectionModel().selectFirst();
     
        TableDao tableDao=new TableDao();
        //Tables table=new Tables();
        List<Tables> tableList=tableDao.view();
        for(Tables table:tableList){
            if(table.getTableStatus().equals("unoccupied") && !table.getTableName().equals("table 0"))
              tableData.add(table.getTableName());
        }
        scrollPane.setFitToWidth(true);
        takeAwayScrollPane.setFitToWidth(true);
        liveOrderScrollPane.setFitToWidth(true);
        Order("");
        billingOrder("");
        tableVbox.setVisible(false);
        guest.setVisible(false);
        setBankList();
        updateOrderFinalButton.setStyle("-fx-background-color:#87B87F;-fx-text-fill: white;");
        updateOrderFinalButton.setMinWidth(80);
        VBox.setMargin(updateOrderFinalButton,new Insets(1,20,1,250));
        
        //disc amount set
//        discountAmountField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
//            if (!newValue.matches("\\d*")) {
//                discountAmountField.setText(newValue.replaceAll("[^\\d]", ""));
//            }
//        });
//        discountAmountField.textProperty().addListener((observable, oldValue, newValue) -> {
//           String[] parts=orderTotal.getText().split(": ");
//           orderTotal.setText("Order Total: "+(Double.parseDouble(parts[1])-Double.parseDouble(newValue)));
//        });
        //disc amount set ends
        
        //guest field ke jodi change kore
            guest.focusedProperty().addListener(new ChangeListener<Boolean>()
                {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                    {
                        if (newPropertyValue)
                        {
                            guests = guest.getText();

                        }
                        else
                        {
                            guests = guest.getText();

                        }
                    }
                });
        //guest field ke jodi change kori ends
//Setting the timer to check live order update
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Order("");
                    takeAwayBox.getChildren().clear();
                    dineInBoxTemp.getChildren().clear();
                    //SalesInfoDao salesDao = new SalesInfoDao();
                    List<SalesInfo> salesList = salesInfoDao.view();
          
                    if (!salesList.isEmpty()) {
                        for (SalesInfo s : salesList) {
                            //System.out.println(s.getOrderStatus());
                            if (s.getOrderStatus().equals("Live")) {
                                //System.out.println(s.getSessionId()+"");  
                                //for guest bill request from android
                                if(s.isGuestBillRequest()){
                                    System.out.println("Request ID: "+s.getSessionId());
                                    s.setGuestBillRequest(false);
                                    //SalesInfoDao sd=new SalesInfoDao();
                                    salesInfoDao.update(s);
                                    
                                    Alert alert = new Alert(AlertType.INFORMATION);
                                    alert.setTitle("New Bill Request From Waiter");
                                    alert.setHeaderText("Guest Bill Request");
                                    alert.setContentText("Click ok to print guest bill ");
                                    //alert.showAndWait();
                     
                                    //alert method
                                    try{
                                   Optional<ButtonType> result = alert.showAndWait();
                                       
                                            if (result.get()== ButtonType.OK) {
                                                    s.setGuestBillRequest(false);                                   
                                                    PositionPdf p1 = new PositionPdf();
                                                    p1.makePdf(s.getSessionId(),"");
                                            } else {
                                                System.out.println("canceled");
                                            }
                                    //alert method ends
                                    }catch(Exception ex){
                                        System.out.println("No Such Element Exception");
                                    }
                                }else{
                                   System.out.println("No request from pos");
                                }
                                    

                                Button liveButton = new Button();
                                liveButton.setText(s.getTable().getTableName());
                                liveButton.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
                                liveButton.setStyle("-fx-background-color:#3F51B5; -fx-text-fill:#ffff00;-fx-padding:5px;-fx-border-color: #ffffff;");
                                if (!liveButton.getText().contains("table 0")) {
                                    liveButton.setText(s.getTable().getTableName());
                                    dineInBoxTemp.getChildren().add(liveButton);
                                } else {
                                    liveButton.setText(s.getSessionId() + "");
                                    takeAwayBox.getChildren().add(liveButton);
                                }

                                //This is to handle the (bam pasher je table er button gula ashe ogula) table button
                                liveButton.setOnAction((ActionEvent e) -> {
                                    //System.out.println("The pressed button is "+dineInButton.getText());
                                    SalesDetailsDao salesDetailsdao = new SalesDetailsDao();
                                    List<SalesDetails> salesList1 = salesDetailsdao.getListFromSameSession(s.getSessionId());
                                    String tableName = s.getTable().getTableName();
                                    String sessionId1 = s.getSessionId() + "";
                                    //traversing through each recipe in one single order
                                    HashMap<String, String> recipe_qty = new HashMap<>();
                                    for (SalesDetails s1 : salesList1) {
                                        recipe_qty.put(s1.getRecipe().getName(), s1.getQuantity()+"");
                                    }
                                    //traversing through each recipe in one single order
                                    Alert alert = new Alert(AlertType.INFORMATION);
                                    alert.setTitle("Menu List");
                                    alert.setHeaderText(null);
                                    String mainContent = "Table: " + tableName + "\nSessionId: " + sessionId1 + "\n";
                                    Iterator it = recipe_qty.entrySet().iterator();
                                    while (it.hasNext()) {
                                        Map.Entry pair = (Map.Entry) it.next();
                                        //System.out.println(pair.getKey() + " = " + pair.getValue());
                                        mainContent = mainContent + "\n" + pair.getKey() + "  Qty: " + pair.getValue();
                                        it.remove(); // avoids a ConcurrentModificationException
                                    }
                                    alert.setContentText(mainContent);
                                    alert.showAndWait();
                                });
                                //This is to handle the table button ends 
                            }//if the order is live ends here
                        }
                    }//if sale list empty ends here
                });
            }
        }, 0, 30000);

        dineInBox.getChildren().addAll(dineInBoxTemp);
        //Setting the timer to check live order update ends
    }

    public void setCategories() {
        waiterFlag=false;
        setInitialVisibility();
        setDiscountField();
        MenuCategoryDao categorydao = new MenuCategoryDao();
        MenuCategory category = new MenuCategory();
        List<MenuCategory> categoryList = categorydao.view();
        //menu category loop
        for (MenuCategory cat : categoryList) {
            //System.out.println("cat is "+cat.getMenuCategoryName()+"and the color is "+cat.getMenuCategoryColor());
            final Button categoryButton = new Button(cat.getMenuCategoryName());
            setCategoryButtonProperty(categoryButton, cat);

            //category button Action
            categoryButton.setOnAction((ActionEvent e) -> {
                row = 0;
                column = 0;
                recipeGrid.getChildren().clear();
                RecipeDao recipedao = new RecipeDao();
                Recipe recipe = new Recipe();
                List<Recipe> recipies = recipedao.showRecipeById(Integer.parseInt(categoryButton.getId()));
                for (Recipe r : recipies) {
                    //System.out.println("recipe is "+r.getName()+"and price is "+r.getPrice());
                    //conditions for 6 recipe buttons in a row
                    if (column > 6) {
                        row++;
                        column = 0;
                    }
                    //Recipe Button creation
                    final Button recipeButton = new Button(r.getName());
                    setRecipeButtonProperty(recipeButton, r);
                    
                    //Price Label of Recipe Button
                    final Label priceLabel = new Label();
                    setPriceLabelProperty(priceLabel, r);
                    
                    //Placement of Recipe Button and price label in a vbox
                    VBox recipeBox = new VBox();
                    recipeBox.getChildren().addAll(recipeButton, priceLabel);
                    setRecipeGrid(recipeGrid, recipeBox);
                    
//recipe button action start
recipeButton.setOnAction((ActionEvent e1) -> {
    Double key = subtotalPrice.get(r.getId());
                        if (key == null) {
                            setVisible();
                            //HBox creation for each row in the billing section
                            HBox hb = new HBox();
                            hb.setSpacing(2);
                            hb.setPadding(new Insets(5, 2, 5, 10));
                            //1.Name of the Menu
                            Label nameLabel = new Label(r.getName());
                            nameLabel.setMinWidth(170);
                            nameLabel.setMaxWidth(170);
                            nameLabel.wrapTextProperty().setValue(true);
                            hb.getChildren().add(nameLabel);
                            //2.minus button creattion
                            Button minusButton = new Button();
                            setMinusButtonProperty(minusButton, hb, r);
                            hb.getChildren().add(minusButton);
                            //3.quantity textfield creation
                            qty = new TextField();
                            qty.setId(r.getId() + "");
                            qty.setText("1");
                            qty.setPrefWidth(40);
                            hb.getChildren().add(qty);
                            //here after clicking a menu the quantity will be updated once
                            double price_add = Integer.parseInt(r.getPrice());
                            double totalPrice_add = price_add * 1;
                            subtotalPrice.put(r.getId(), totalPrice_add);
                            id_qty.put(r.getId(), qty.getText());
                            totalPrice();
                            //here after clicking a menu the quantity will be updated once
                            qty.textProperty().addListener((observable, oldValue, newValue) -> {
                                int newVal = 0;
                                if (!newValue.matches("\\d*")) {
                                    qty.setText(newValue.replaceAll("[^\\d]", ""));
                                }               if (newValue.trim().isEmpty()) {
                                    newVal = 0;
                                } else {
                                    newVal = Integer.parseInt(newValue);
                                }               double price1 = Integer.parseInt(r.getPrice());
                                double totalPrice = price1 * newVal;
                                id_qty.put(r.getId(), newVal + "");
                                subtotalPrice.put(r.getId(), totalPrice);
                                totalPrice();
                            });
                            //4.plus button creation
                            Button plusButton = new Button();
                            setPlusButtonProperty(plusButton, hb, r);
                            hb.getChildren().add(plusButton);
                            //5.Delete Button creation
                            final Button deleteButton1 = new Button();
                            setDeleteButtonProperty(deleteButton1, hb, r);
                            hb.getChildren().add(deleteButton1);
                            vb.getChildren().addAll(hb);
                            vb.setSpacing(5);
                            k++;
                            //recipeButton.setDisable(true);
                        } else {
                        }
                    });

//end action of recipe button
billing_section.getChildren().remove(vb);
billing_section.getChildren().addAll(vb);
column++;
                }
            });
            //end action of category button
            grid.setPadding(new Insets(30, 0, 0, 0));
            grid.add(categoryButton, i, p);
            i++;
            if (i > 6) {
                p++;
                i = 0;
            }
        }
        //menu category end
        VBox vBox = new VBox();
        vBox.getChildren().addAll(grid, recipeGrid);
        orderEntryPane.getChildren().addAll(vBox);
    }

    public double totalPrice() {
        Recipe r = new Recipe();
        double sum = 0,subTotalSum=0;
        vat=0;
        vat1=0;
        for (Map.Entry m : subtotalPrice.entrySet()) {
            int id = (int) m.getKey();
            String value = m.getValue().toString();
            //System.out.println("price id is: "+id);
            //System.out.println("price value is: "+ value);
            RecipeDao recipeDao = new RecipeDao();
            r = (Recipe)recipeDao.checkdao(id);
            if(Double.parseDouble(r.getNetPrice())>0){//inclusive
                 //if(r.getId() == 667||r.getId()== 669||r.getId()== 653||r.getId()== 651||r.getId()== 656||r.getId() == 662 || r.getId() == 664|| r.getId() == 658 ||r.getId() == 660 )vat = 0;
                 vat = vat+Math.round((Double.parseDouble(value))/1.15*.15 * 100.0) / 100.0;
                 subTotalSum+=Double.parseDouble(value);
                 
            }
            else{//exclusive
                
                vat1=vat1+Math.round(.15 * (Double.parseDouble(value)) * 100.0) / 100.0;
                System.out.println("vat1 is "+vat1);
                System.out.println("Value is "+value);
                
                //if(r.getId() == 667||r.getId()== 669||r.getId()== 653||r.getId()== 651||r.getId()== 656||r.getId() == 662 || r.getId() == 664|| r.getId() == 658 ||r.getId() == 660 )vat = 0;
                vat = vat+ Math.round(0.04 * (Double.parseDouble(value)) * 100.0) / 100.0;
                System.out.println("Vat is "+vat);
                subTotalSum+=Double.parseDouble(value);
                sum+=vat+5;
                
            }
            sum += Double.parseDouble(value);
        
        }
        subTotal.setText("Sub Total: " + subTotalSum);
        String[] parts = subTotal.getText().split(": ");
        price = Double.parseDouble(parts[1]);
        if (discountField.getText().isEmpty()) {
            discountPrice = 0;
        } else {
            discountPrice = (Double.parseDouble(discountField.getText()) / 100) * price;
            
        }

        discountLabel.setText("Discount: " + discountPrice);
        //vat = Math.round(.15 * (price) * 100.0) / 100.0;
        float vatFloat = Float.parseFloat(vat+"");
        vatFloat= (vatFloat-Float.parseFloat(discountField.getText())*vatFloat/100);
        vatLabel.setText("Vat: " + vatFloat);
        order_total = (int) Math.round(sum - discountPrice);
        orderTotal.setText("Order Total: " + Math.round(order_total));

        System.out.println("The subtotal price is now  " + sum);
        return sum;
    }

    private void setRecipeGrid(GridPane recipeGrid, VBox recipeBox) {
        recipeGrid.add(recipeBox, column, row);
        recipeGrid.setPadding(new Insets(15, 0, 0, 0));
        recipeGrid.setHgap(3); //horizontal gap in pixels => that's what you are asking for
        recipeGrid.setVgap(3); //vertical gap in pixels
    }

    private void setPriceLabelProperty(Label priceLabel, Recipe r) {
        priceLabel.setMaxWidth(Double.MAX_VALUE);
        priceLabel.setAlignment(Pos.CENTER);
        priceLabel.setText("BDT. " + r.getPrice());
        //priceLabel.setPrefSize( Double.MAX_VALUE, Double.MAX_VALUE );
        priceLabel.setStyle("-fx-background-color:#BDBDBD; -fx-font-weight: bold;");
    }
    
       private void setDiscountField1(UpdateOrderController updateOrder){
        double totalPrice= updateOrder.getSalesDetailsPrice();
                            
        discField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                discField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        discField.textProperty().addListener((observable, oldValue, newValue) -> {
            
            String[] parts = subTotal.getText().split(": ");
            price = Double.parseDouble(parts[1]);
            //vat=getVat(price);
            //vatLabel.setText("Vat: "+vat);
            

            
            
            
            price = (int) Math.round(price);
            if (!newValue.trim().isEmpty()) {
                //kaj koriii
                discountPrice = getDiscount(newValue, price);
                //vat = getVat(order_total);
                float vatFloat = Float.parseFloat(updateOrder.getVat()+"");
                vatLabel.setText("Vat: " + vatFloat);
                orderTotal.setText("Order Total: " + Math.round((price - discountPrice+updateOrder.getExclusiveVat())));
                discountLabel.setText("Discount: " + discountPrice);
                
                updateOrder.setDiscount(discountPrice+"");
                
            } else {
                //orderTotal.setText("Order Total: NaN Tk.");
                discountPrice = 0;
                //vat = getVat(order_total);
                float vatFloat = Float.parseFloat(updateOrder.getVat()+"");
                vatLabel.setText("Vat: " + vatFloat);
                //discountField.setText("0");
                discountLabel.setText("Discount: 0");
                orderTotal.setText("Order Total: " + Math.round((price+updateOrder.getExclusiveVat())));
                
                updateOrder.setDiscount(discountPrice+"");
            }
            setSubtotal(updateOrder);
        });    
    }
    private void setDiscountField() {

        //updateOrderInvisible kore dei
        //updateOrderFinalButton.setVisible(false);

        //restrict discountfield to only numbers
        discountField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                discountField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        discountField.textProperty().addListener((observable, oldValue, newValue) -> {
            
            String[] parts = subTotal.getText().split(": ");
            price = Double.parseDouble(parts[1]);
            //vat=getVat(price);
            //vatLabel.setText("Vat: "+vat);
            price = (int) Math.round(price);
            if (!newValue.trim().isEmpty()) {
                //kaj koriii
                discountPrice = getDiscount(newValue, price);
                //vat = getVat(order_total);
                
                float vatFloat = Float.parseFloat(vat+"");
                vatFloat= (vatFloat-Float.parseFloat(discountField.getText())*vatFloat/100);
                vatLabel.setText("Vat: " + vatFloat);
                orderTotal.setText("Order Total: " + Math.round((price - discountPrice+vat1)));
                discountLabel.setText("Discount: " + discountPrice);
            } else {
                //orderTotal.setText("Order Total: NaN Tk.");
                discountPrice = 0;
                //vat = getVat(order_total);
                float vatFloat = Float.parseFloat(vat+"");
                vatLabel.setText("Vat: " + vatFloat);
                //discountField.setText("0");
                discountLabel.setText("Discount: 0");
                orderTotal.setText("Order Total: " + Math.round((price+vat1)));
            }
        });
    }

    private void setVisible() {
        subTotal.setVisible(true);
        orderTotal.setVisible(true);
        vatLabel.setVisible(true);
        discountLabel.setVisible(true);
    }

    private void setInitialVisibility() {
        subTotal.setVisible(false);
        orderTotal.setVisible(false);
        vatLabel.setVisible(false);
        discountLabel.setVisible(false);
    }

    private void setPlusButtonProperty(Button plusButton, HBox hb, Recipe r) {
        Image plusIcon = new Image(getClass().getResourceAsStream("/resources/images/plusIcon.png"));
        plusButton.setGraphic(new ImageView(plusIcon));
        plusButton.setId(r.getId() + "");
        plusButton.setCursor(Cursor.HAND);
        plusButton.setOnAction((ActionEvent e) -> {
            for (Node node : hb.getChildren()) {
                if (node instanceof TextField && node.getId().equals(plusButton.getId())) {
                    ((TextField) node).setText((Integer.parseInt(((TextField) node).getText()) + 1) + "");
                }
            }
        });
    }

    private void setMinusButtonProperty(Button minusButton, HBox hb, Recipe r) {
        Image minusIcon = new Image(getClass().getResourceAsStream("/resources/images/minusIcon.png"));
        minusButton.setGraphic(new ImageView(minusIcon));
        minusButton.setCursor(Cursor.HAND);
        minusButton.setId(r.getId() + "");
        minusButton.setOnAction((ActionEvent e) -> {
            for (Node node : hb.getChildren()) {
                if (node instanceof TextField && node.getId().equals(minusButton.getId())) {
                    if (Integer.parseInt(((TextField) node).getText()) > 0) {
                        ((TextField) node).setText((Integer.parseInt(((TextField) node).getText()) - 1) + "");
                    }
                }
            }
        });
    }

    private void setDeleteButtonProperty1(Button deleteButton, HBox hb, Recipe r, UpdateOrderController updateOrder) {
        deleteButton.setId(r.getId() + "");
        Image deleteIcon = new Image(getClass().getResourceAsStream("/resources/images/delete.png"));
        ImageView imageView = new ImageView(deleteIcon);
        imageView.setFitHeight(13);
        imageView.setFitWidth(10);
        deleteButton.setGraphic(imageView);
        
        
        //10292017
        deleteButton.setOnAction((ActionEvent e) -> {
            updateOrder.deleteFromList(r);
            updateOrder.getSalesDetailsPrice();
            setSubtotal(updateOrder);
            vb.getChildren().remove(hb);

        });

    }

    private void setDeleteButtonProperty(Button deleteButton, HBox hb, Recipe r) {
        deleteButton.setId(r.getId() + "");
        Image deleteIcon = new Image(getClass().getResourceAsStream("/resources/images/delete.png"));
        ImageView imageView = new ImageView(deleteIcon);
        imageView.setFitHeight(13);
        imageView.setFitWidth(10);
        deleteButton.setGraphic(imageView);

        deleteButton.setOnAction((ActionEvent e) -> {
            id_qty.remove(r.getId());
            subtotalPrice.remove(r.getId());
            totalPrice();
            vb.getChildren().remove(hb);
        });
    }

    private double getDiscount(String newValue, double price) {
        discountPrice = Math.round(((Double.parseDouble(newValue) / 100) * price) * 100);
        discountPrice = discountPrice / 100;
        return discountPrice;
    }

    private double getVat(double price) {
        vat = Math.round(.15 * price ) / 100.0;
        return vat;
    }

    private void setTextValue(String st, int rId) {
        //qty.setText(st);
        id_qty.put(rId, qty.getText());
        for (Map.Entry m : id_qty.entrySet()) {
            int id = (int) m.getKey();
            String value = m.getValue().toString();
        }
    }

    private void setRecipeButtonProperty(Button recipeButton, Recipe r) {
        recipeButton.setMaxWidth(90);
        recipeButton.setMinWidth(90);
        recipeButton.setMinHeight(100);
        recipeButton.wrapTextProperty().setValue(true);
        recipeButton.setStyle("-fx-background-color:" + r.getCategory().getMenuCategoryColor());
        recipeButton.getStyleClass().add("recipeButton");
    }

    private void setCategoryButtonProperty(Button categoryButton, MenuCategory cat) {
        categoryButton.setId(Integer.toString(cat.getId()));

        //styling category button
        categoryButton.setMinWidth(100);
        categoryButton.setMaxWidth(100);
        categoryButton.setMinHeight(40);
        //categoryButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        String color = cat.getMenuCategoryColor();
        categoryButton.wrapTextProperty().setValue(true);
        categoryButton.setStyle("-fx-background-color:" + color);
        categoryButton.getStyleClass().add("button1");
    }

    private void ingredientLiveStockUpdate(HashMap<Integer, String> id_qty) {
        for (Map.Entry m : id_qty.entrySet()) {
            int id = (int) m.getKey();
            //System.out.println(id);
            //check for Recipe in database
            Recipe recipe = new Recipe();
            RecipeDao recipedao = new RecipeDao();
            recipe = (Recipe) recipedao.checkdao(id);
            RecipeIngredientsDao riDao = new RecipeIngredientsDao();
            List<RecipeIngredients> recipeList = riDao.showRecipeIngredients(id);

            for (RecipeIngredients r : recipeList) {
                
                //update ingredients quantity for the selected menu
                IngredientsDao ingredientsdao = new IngredientsDao();
                Ingredients ingredient = new Ingredients();
                ingredient = (Ingredients) ingredientsdao.checkdao(r.getIngredients().getId());
                double livestock = ingredient.getLivestock();
                System.out.println("******************************livestock was before "+livestock);
                double quantity = r.getQuantity();
                livestock = livestock - quantity;
                
//                System.out.println("The remaining livestock is "+livestock_string);
                ingredient.setLivestock(livestock);
                ingredientsdao.update(ingredient);
                System.out.println("******************************livestock is now "+livestock);
            }
        }
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public long getSessionId() {
        return sessionId;
    }

    private void Order(String status) {
        liveOrderTable.getItems().clear();
        //Live Order Section
        //SalesInfoDao salesInfoDao = new SalesInfoDao();
        List<SalesInfo> salesInfoList = salesInfoDao.view();
        
        sessionCol.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        tableCol.setCellValueFactory(new PropertyValueFactory<>("tableName"));
        subTotalCol.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        vatCol.setCellValueFactory(new PropertyValueFactory<>("vat"));
        discountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("orderTotal"));
        //SalesInfo salesInf = liveOrderTable.getSelectionModel().getSelectedItem();
        //System.out.println(salesInf.getDiscount()); 
        liveOrderTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SalesInfo>() {
            @Override
            public void changed(ObservableValue<? extends SalesInfo> observable, SalesInfo oldValue, SalesInfo newValue) throws NullPointerException {
                if (newValue != null) {
                    System.out.println(newValue.getSessionId());
                    showOrderBox(newValue);
                    final HBox billingHbox = new HBox();
                    final GridPane cashGrid = new GridPane();
                    //deleteButton on action event starts from here
                    deleteButton.setOnAction((ActionEvent e) -> {
                        
                        
                        
                        
                        SalesInfo sf = new SalesInfo();
                        //SalesInfoDao salesInfoDao1 = new SalesInfoDao();
                        sf = (SalesInfo) salesInfoDao.checkDaoLong(newValue.getSessionId());
                        SalesDetailsDao salesDetailsDao = new SalesDetailsDao();
                        List<SalesDetails> salesList = salesDetailsDao.getListFromSameSession(sf.getSessionId());
                                                //updating inventory 
                                                                                        Alert alert = new Alert(AlertType.INFORMATION);
                                                                                        alert.setTitle("Do you want to update Inventory?");
                                                                                        alert.setHeaderText("UPDATE INVENTORY");
                                                                                        alert.setContentText("If the recipe has been made then update inventory");
                                                                                        //alert.showAndWait();

                                                                                        //alert method
                                                                                        try{
                                                                                       Optional<ButtonType> result = alert.showAndWait();

                                                                                                if (result.get()== ButtonType.OK) {
                                                                                                        id_qty.clear();
                                                                                                        //List<SalesDetails> salesDetails =  salesDetailsDao.getListFromSameSession(sf.getSessionId());
                                                                                                        for(SalesDetails sd : salesList){

                                                                                                            id_qty.put(sd.getRecipe().getId(),(sd.getQuantity()+""));
                                                                                                        }
                                                                                                        ingredientLiveStockUpdate(id_qty);

                                                                                                }else {
                                                                                                    System.out.println("canceled");
                                                                                                }
                                                                                        //alert method ends
                                                                                        }catch(Exception ex){
                                                                                            System.out.println("No Such Element Exception");
                                                                                        }
                                                    //updating inventory ends
                        
                        
                        
                        for (SalesDetails sd : salesList) {
                            //System.out.println(sd.getId()+"is the recipes to be deleted");
                            salesDetailsDao.delete(sd);
                        }
                        salesInfoDao.delete(sf);
                        //updating the table flag to occupied
                        TableDao tabledao = new TableDao();
                        staticTables = sf.getTable();
                        staticTables.setTableStatus("unoccupied");
                        //System.out.println("Static table id, name and status"+ staticTables.getId()+" "+staticTables.getTableName()+" "+staticTables.getTableStatus());
                        tabledao.update(staticTables);
                        //Order("");
                        //refreshing the full page
                        
                        
                        

                        
                        
                        
                        Node source = (Node) e.getSource();
                        moveToFrontPage(source);
                        //refreshing the full page ends
                        //updating the table flag to occupied ends
                    });
//deleteButton on action event ends here

//UpdateButton on action event starts here
                    updateOrderButton.setOnAction((ActionEvent e) -> {
                       
                        updateDetailsBox.setVisible(true);
                        //tableComboBox.setItems(tableData);
                        //creating Dynamic Box
                            HBox hbox = new HBox();
                            Label discLabel1 = new Label("DISC(%)");
                            discField.setMaxWidth(50);
                            discLabel.setPadding(new Insets(5,5,5,5));

                                        //removing newly creted discount
                                             discountBox.getChildren().clear();
                                             vb.getChildren().clear();
                                             tableVbox.getChildren().clear();
                                             grid.getChildren().clear();
                                        //removing newly created discount ends
                            
                            
                            hbox.getChildren().add(discLabel1);
                            hbox.getChildren().add(discField);
                            
                            discountBox.getChildren().add(hbox);
                       
                        //creating Dynamic Box ends

                        
                        //getSalesInfo SalesDetails
                            long newSessionId = newValue.getSessionId();
                            UpdateOrderController updateOrder = new UpdateOrderController(newSessionId);
                            
                            double discountPrice = Double.parseDouble(updateOrder.getSalesInfo().getDiscount());
                            double total = updateOrder.getSalesInfo().getSubTotal();
                            String discountPercentage = Math.round(discountPrice/total*100)+"";
                            
                            //System.out.println("discount is0000000000000000000000000000000000000000 "+discountPrice);
                            //System.out.println("discoun percentage is 0000000000000000000000000000000 "+ discountPercentage);
                            
                            discField.setText(discountPercentage);
                            discountLabel.setText(discountPrice+"");
                            
                        //getSalesInfo SalesDetails
                  


                        //setting discount Field
                                setDiscountField1(updateOrder);
                        //setting discount FIeld ends
                        
                        if(updateOrder.getSalesInfo().getOrderType().equals("Dine In"))
                             cbox.getSelectionModel().selectFirst();
                        
                        else
                             cbox.getSelectionModel().select(1);
                        
                        
                        if(cbox.getSelectionModel().getSelectedItem().equals("Take Away")){
                            dineInDetails.setVisible(false);

                        }
                        else{
                            dineInDetails.setVisible(true);
                            guestBox.setText(updateOrder.getSalesInfo().getGuestNumber());

                            //lalalaa
                            TableDao tableDao=new TableDao();
                            List<Tables> tableList=tableDao.view();
                            tableData.clear();
                            for(Tables table:tableList){
                                if(table.getTableStatus().equals("unoccupied") && !table.getTableName().equals("table 0"))
                                  tableData.add(table.getTableName());
                            }
                            
                            //tableComboBox.getItems().clear();
                            tableComboBox.setItems(tableData);
                            tableComboBox.getSelectionModel().select(updateOrder.getSalesInfo().getTableName());
                            }
                        
                        
                        
                        
                           cbox.valueProperty().addListener(new ChangeListener<String>() {
                            @Override public void changed(ObservableValue ov, String t, String t1) {
                              if(t1.equals("Take Away")){
                                  dineInDetails.setVisible(false);
                                  updateOrder.getSalesInfo().setGuestNumber("");
                                  updateOrder.getSalesInfo().setOrderType("Take Away");
                                  
                                  TableDao tableDao=new TableDao();
                                  Tables tables = new Tables();
                                  Tables table1=updateOrder.getSalesInfo().getTable();
                                  
                                  //ai table unoccupy hobe
                                  //updateOrder.setUnoccupyTable(table1);
                                  
                                  //ai table reserve hobe
                                  //tableDao.update(table1);
                                  tables = (Tables)tableDao.getTable("table 0");
                                  updateOrder.getSalesInfo().setTable(tables);
                                  
                              }else{
                                    dineInDetails.setVisible(true);
                                    TableDao tableDao=new TableDao();
                                    List<Tables> tableList=tableDao.view();
                                    tableData.clear();
                                    for(Tables table:tableList){
                                        if(table.getTableStatus().equals("unoccupied") && !table.getTableName().equals("table 0"))
                                        {
                                            System.out.println(table.getTableName());
                                            tableData.add(table.getTableName()); 
                                        }
                                         
                                    }

                                    tableComboBox.setItems(tableData);
                                    tableComboBox.getSelectionModel().selectFirst();
                                    updateOrder.getSalesInfo().setGuestNumber(guestBox.getText());
                                    updateOrder.getSalesInfo().setOrderType("Dine In");
                                        
                              }
                            }    
                        });

                  tableComboBox.valueProperty().addListener(new ChangeListener<String>() {
                    @Override public void changed(ObservableValue ov, String t, String t1) {

                          if(t==null){
                              t="table 0";
                          }
                          if(t1==null){
                              t1 = "table 0";
                          }
                            String tableName=t1;
                            TableDao tableDao=new TableDao();
                            Tables tables = new Tables();
                            System.out.println("table name is "+tableName);
                            tables = (Tables)tableDao.getTable(tableName);

                            //aitable unoccupy hobe
                            //updateOrder.setUnoccupyTable(updateOrder.getSalesInfo().getTable());

                            //aita reserved hobe
                            updateOrder.getSalesInfo().setTable(tables);

                              System.out.println("old is "+t+" and new is "+t1);

                          }
                     });
                       
                        
                        
                        setSubtotal(updateOrder);
                        //initializing object reference ends
                        //session id er under jeshob sales details ache nie ashbo
                        List<SalesDetails> salesList = updateOrder.getSalesDetails();
                        for (SalesDetails sd : salesList) {
                            // Label recipeLabel = new Label(sd.getRecipe().getName());
                            //vb.getChildren().add(recipeLabel);
                            HBox hb = new HBox();
                            hb.setSpacing(2);
                            hb.setPadding(new Insets(5, 2, 5, 10));
                            //1.Name of the Menu
                            Label nameLabel = new Label(sd.getRecipe().getName());
                            nameLabel.setMinWidth(170);
                            nameLabel.setMaxWidth(170);
                            nameLabel.wrapTextProperty().setValue(true);
                            hb.getChildren().add(nameLabel);
                            //2.minus button creattion
                            Button minusButton = new Button();
                            setMinusButtonProperty(minusButton, hb, sd.getRecipe());
                            hb.getChildren().add(minusButton);
                            //3.quantity textfield creation
                            qty = new TextField();
                            qty.setId(sd.getRecipe().getId() + "");
                            qty.setText(sd.getQuantity()+"");
                            qty.setPrefWidth(40);
                            hb.getChildren().add(qty);
                            //here after clicking a menu the quantity will be updated once
                            double price_add = Integer.parseInt(sd.getRecipe().getPrice());
                            double totalPrice_add = price_add * 1;
                            //here after clicking a menu the quantity will be updated once
                            qty.textProperty().addListener((javafx.beans.value.ObservableValue<? extends java.lang.String> observable1, java.lang.String oldValue1, java.lang.String newValue1) -> {
                                int newVal = 0;
                                if (!newValue1.matches("\\d*")) {
                                    qty.setText(newValue1.replaceAll("[^\\d]", ""));
                                }
                                if (newValue1.trim().isEmpty()) {
                                    newVal = 0;
                                } else {
                                    newVal = Integer.parseInt(newValue1);
                                    sd.setQuantity(Long.parseLong(newValue1));
                                    updateOrder.updateQuantity(sd.getRecipe(), newValue1);
                                    setSubtotal(updateOrder);
                                }
                            });
                            //4.plus button creation
                            Button plusButton = new Button();
                            setPlusButtonProperty(plusButton, hb, sd.getRecipe());
                            hb.getChildren().add(plusButton);
                            //5.Delete Button creation
                            final Button deleteButton1 = new Button();
                            setDeleteButtonProperty1(deleteButton1, hb, sd.getRecipe(), updateOrder);
                            hb.getChildren().add(deleteButton1);
                            vb.getChildren().addAll(hb);
                            vb.setSpacing(5);
                            k++;
                        }
                        vb.getChildren().add(updateOrderFinalButton);
                        finalSubmit.setVisible(false);
                      
                        //session id er under e jeshob details ache nie ashlam
                        //orderEntry tab e chole jabe
                        MenuTab.getSelectionModel().select(orderEntryTab);
                        dineInButton.setVisible(false);
                        takeAwayButton.setVisible(false);
                        setDiscountField();
                        MenuCategoryDao categorydao = new MenuCategoryDao();
                        MenuCategory category = new MenuCategory();
                        List<MenuCategory> categoryList = categorydao.view();
                        //menu category loop
                         i=0;
                        p=0;
                        for (MenuCategory cat : categoryList) {
                            //System.out.println("cat is "+cat.getMenuCategoryName()+"and the color is "+cat.getMenuCategoryColor());
                            final Button categoryButton = new Button(cat.getMenuCategoryName());
                            setCategoryButtonProperty(categoryButton, cat);
                            //category button Action
                            categoryButton.setOnAction((ActionEvent e1) -> {
                                row = 0;
                                column = 0;
                                recipeGrid.getChildren().clear();
                                RecipeDao recipedao = new RecipeDao();
                                Recipe recipe = new Recipe();
                                List<Recipe> recipies = recipedao.showRecipeById(Integer.parseInt(categoryButton.getId()));
                                for (Recipe r : recipies) {
                                    //System.out.println("recipe is "+r.getName()+"and price is "+r.getPrice());
                                    //conditions for 6 recipe buttons in a row
                                    if (column > 6) {
                                        row++;
                                        column = 0;
                                    }
                                    //Recipe Button creation
                                    final Button recipeButton = new Button(r.getName());
                                    setRecipeButtonProperty(recipeButton, r);
                                    //Price Label of Recipe Button
                                    final Label priceLabel = new Label();
                                    setPriceLabelProperty(priceLabel, r);
                                    //Placement of Recipe Button and price label in a vbox
                                    VBox recipeBox = new VBox();
                                    recipeBox.getChildren().addAll(recipeButton, priceLabel);
                                    setRecipeGrid(recipeGrid, recipeBox);
                                    //recipe button action start
                                    recipeButton.setOnAction((ActionEvent e2) -> {
                                        //here after clicking a menu the quantity will be updated once
                                        boolean hasSalesDetails = updateOrder.containList(r);
                                        if (hasSalesDetails) {
                                            Alert alert = new Alert(AlertType.INFORMATION);
                                            alert.setTitle("Already Have This in list");
                                            alert.setHeaderText("Already Have In List");
                                            alert.setContentText("select a new Item");
                                            alert.showAndWait();
                                        } else {
                                            //HBox creation for each row in the billing section
                                            vb.getChildren().remove(updateOrderFinalButton);
                                            HBox hb = new HBox();
                                            hb.setSpacing(2);
                                            hb.setPadding(new Insets(5, 2, 5, 10));
                                            //1.Name of the Menu
                                            Label nameLabel = new Label(r.getName());
                                            nameLabel.setMinWidth(170);
                                            nameLabel.setMaxWidth(170);
                                            nameLabel.wrapTextProperty().setValue(true);
                                            hb.getChildren().add(nameLabel);
                                            //2.minus button creattion
                                            Button minusButton = new Button();
                                            setMinusButtonProperty(minusButton, hb, r);
                                            hb.getChildren().add(minusButton);
                                            //3.quantity textfield creation
                                            qty = new TextField();
                                            qty.setId(r.getId() + "");
                                            qty.setText("1");
                                            qty.setPrefWidth(40);
                                            hb.getChildren().add(qty);
                                            //4.plus button creation
                                            Button plusButton = new Button();
                                            setPlusButtonProperty(plusButton, hb, r);
                                            hb.getChildren().add(plusButton);
                                            //5.Delete Button creation
                                            final Button deleteButton2 = new Button();
                                            setDeleteButtonProperty1(deleteButton2, hb, r, updateOrder);
                                            hb.getChildren().add(deleteButton2);
                                            vb.getChildren().addAll(hb);
                                            vb.setSpacing(5);
                                            //inserting the salesDetails in updateOrder starts
                                            SalesDetails salesDetails = new SalesDetails();
                                            salesDetails.setQuantity(Long.parseLong(qty.getText()));
                                            salesDetails.setRecipe(r);
                                            salesDetails.setSalesInfo(updateOrder.getSalesInfo());
                                            updateOrder.getSalesDetails().add(salesDetails);
                                            setSubtotal(updateOrder);
                                            qty.textProperty().addListener((javafx.beans.value.ObservableValue<? extends java.lang.String> observable2, java.lang.String oldValue2, java.lang.String newValue2) -> {
                                                int newVal = 0;
                                                if (!newValue2.matches("\\d*")) {
                                                    qty.setText(newValue2.replaceAll("[^\\d]", ""));
                                                }
                                                if (newValue2.trim().isEmpty()) {
                                                    newVal = 0;
                                                } else {
                                                    salesDetails.setQuantity(Long.parseLong(newValue2));
                                                    updateOrder.updateQuantity(salesDetails.getRecipe(), newValue2);
                                                    setSubtotal(updateOrder);
                                                }
                                            });
                                            //inserting the salesDetails in updateOrder ends
                                            vb.getChildren().add(updateOrderFinalButton);
                                            finalSubmit.setVisible(false);
                                        }
                                        //here after clicking a menu the quantity will be updated once
                                    });
                                    //end action of recipe button
                                    billing_section.getChildren().remove(vb);
                                    billing_section.getChildren().addAll(vb);
                                    column++;
                                }
                            });
                            //end action of category button
                            grid.setPadding(new Insets(30, 0, 0, 0));
                            grid.add(categoryButton, i, p);
                            i++;
                            if (i > 6) {
                                p++;
                                i = 0;
                            }
                        }
                        VBox vBox = new VBox();
                        vBox.getChildren().addAll(grid, recipeGrid);
                        orderEntryPane.getChildren().addAll(vBox);

                    });
//UpdateButton on action event ends here
//settleButton on action event starts from here
                    settleButton.setOnAction((ActionEvent e) -> {
                        showBillingBox(cashGrid,billingHbox,newValue);
                        showOrderBox(newValue); 
                    });
//settleButton on action event ends here
                }//if(!newValue==null) ends here
            }
        });
        if (!salesInfoList.isEmpty()) {
            for (SalesInfo s : salesInfoList) {
                if (s.getOrderStatus().equals("Live") && s.getOrderType().contains(status)) {
                    List<String> salesInfoColumn = new ArrayList();
                    salesInfoColumn.add(s.getTable().getTableName());
                    salesInfoColumn.add(s.getSubTotal()+"");
                    salesInfoColumn.add(s.getVat()+"");
                    salesInfoColumn.add("0");
                    salesInfoColumn.add(s.getDiscount());
                    String orderTotal = ((s.getSubTotal()) - (Double.parseDouble(s.getDiscount())) + (s.getVat())) + "";
                    salesInfoColumn.add(orderTotal);
                    data.add(s);
                }//if(s.getOrderStatus().equals("Live")&&s.getOrderType().equals("Take Away"))ends      
            }//for(SalesInfo s: salesInfoList) ends
            liveOrderTable.setItems(data);
        }//if ends
        //traversing each row in the live order segment ends
//Live Order Section ends 
    }

    //billing order starts
    private void billingOrder(String status) {
        billingOrderTable.getItems().clear();
        //Live Order Section
        //SalesInfoDao salesInfoDao = new SalesInfoDao();
        List<SalesInfo> salesInfoList = salesInfoDao.view();

        sessionCol1.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        tableCol1.setCellValueFactory(new PropertyValueFactory<>("tableName"));
        subTotalCol1.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        vatCol1.setCellValueFactory(new PropertyValueFactory<>("vat"));   
        discountCol1.setCellValueFactory(new PropertyValueFactory<>("discount"));
        totalCol1.setCellValueFactory(new PropertyValueFactory<>("orderTotal"));
        //SalesInfo salesInf = liveOrderTable.getSelectionModel().getSelectedItem();
        //System.out.println(salesInf.getDiscount()); 
        billingOrderTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SalesInfo>() {
            @Override
            public void changed(ObservableValue<? extends SalesInfo> observable, SalesInfo oldValue, SalesInfo newValue) throws NullPointerException {
                if (!newValue.equals(null)) {
                    System.out.println(newValue.getSessionId());
                    showOrderBox(newValue);
                    final HBox billingHbox = new HBox();
                    final GridPane cashGrid = new GridPane();

//settleButton on action event starts from here
                    settleButton1.setOnAction((ActionEvent e) -> {
                        showBillingBox(cashGrid,billingHbox,newValue);
                        
                    });
//settleButton on action event ends here

                     guestBillButton.setOnAction((ActionEvent e) -> {
                                newValue.setGuestBillRequest(false);
                                PositionPdf p1 = new PositionPdf();
                                p1.makePdf(newValue.getSessionId(),"");
                        //showOrderBox(newValue);
                    });        
                }//if(!newValue==null) ends here
            }
        });
        if (!salesInfoList.isEmpty()) {
            for (SalesInfo s : salesInfoList) {
                if (s.getOrderStatus().equals("Live") && s.getOrderType().contains(status)) {
                    List<String> salesInfoColumn = new ArrayList();
                    salesInfoColumn.add(s.getTable().getTableName());
                    salesInfoColumn.add(s.getSubTotal()+"");
                    salesInfoColumn.add(s.getVat()+"");
                    salesInfoColumn.add("0");
                    salesInfoColumn.add(s.getDiscount());
                    String orderTotal = ((s.getSubTotal()) - (Double.parseDouble(s.getDiscount())) + (s.getVat())) + "";
                    salesInfoColumn.add(orderTotal);
                    data1.add(s);
                }//if(s.getOrderStatus().equals("Live")&&s.getOrderType().equals("Take Away"))ends      
            }//for(SalesInfo s: salesInfoList) ends
            billingOrderTable.setItems(data1);
        }//if ends
        //traversing each row in the live order segment ends
//Live Order Section ends 
    }
    //billing order ends
    private void getTableView() {
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10, 10, 10, 30));
        gridpane.setHgap(40);
        gridpane.setVgap(10);
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        //System.out.println(visualBounds.getWidth() + " x " + visualBounds.getHeight());
        double width = visualBounds.getWidth();
        UserDao userdao = new UserDao();
        
//waiter Button 
            guest.setVisible(true);
            UserDao userDao = new UserDao();
            List<String> waiterNames = new ArrayList();
            List<Users> userList = userDao.view();
            for(Users u: userList){
                if(u.getUserType().getTypeName().equals("Waiter")){
                  waiterNames.add(u.getUserName());
                }
            }
            ToggleGroup group = new ToggleGroup();
            group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
                    public void changed(ObservableValue<? extends Toggle> ov,
                        Toggle toggle, Toggle new_toggle) {
                        String value = group.getSelectedToggle().toString();
                        waiter = getBankName(value);
                        waiterFlag=true;
                        
                    }
            });
            
            int wRow=0,wCol=0;
            GridPane waiterGrid=new GridPane();
            waiterGrid.setHgap(20);
            waiterGrid.setVgap(10);
            for (String name : waiterNames) {
                ToggleButton waiterButton = new ToggleButton(name);
                waiterButton.setMinWidth(80);
                waiterButton.setMinHeight(30);
                waiterButton.setToggleGroup(group);
                waiterGrid.add(waiterButton, wCol++, wRow);
                waiterGrid.setHalignment(waiterButton, HPos.CENTER);
                //fBox.getChildren().add(waiterButton);
                if(wCol>3){
                   wCol=0; 
                   wRow++;
                }
            }
            waiterPane.getChildren().add(waiterGrid);
             
                        
//            String value = group.getSelectedToggle().toString();
//            value = getBankName(value);
        //waiter Button ends
        
  

        TableDao tabledao = new TableDao();
        List<Tables> tableList = tabledao.view();
        List<String> tableListColumn = new ArrayList();
        //table loop 
        for (Tables tables : tableList) {
            if (!(tables.getTableName().contains("table 0"))) {
                tableListColumn.add(tables.getTableName());
                //System.out.println(tables.getTableName());
                Button tableButton = new Button(tables.getTableName());
                setTableButtonProperty(tableButton, tables);
                //table reserved hole button disabled hobe
                if (tables.getTableStatus().contains("reserved")) {
                    tableButton.setDisable(true);
                }
                gridpane.add(tableButton, m, n);
                m++;
                if (width <= 1280) {
                    if (m > 3) {
                        n++;
                        m = 0;
                    }
                } else if (width > 1280 && width < 1600) {
                    if (m > 4) {
                        n++;
                        m = 0;
                    }
                } else {
                    if (m > 7) {
                        n++;
                        m = 0;
                    }
                }
                GridPane.setHalignment(tableButton, HPos.CENTER);
                tableButton.setOnAction((ActionEvent e) -> {
                    staticTables = tables;
                    //  System.out.println(guests);
                    if (waiterFlag) {
                        if(!(guest.getText().isEmpty())){
                             tableVbox.setVisible(false);
                              setCategories();
                        }
                        else{
                            //System.out.println("please select waiter");
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Empty Guest Field");
                            alert.setHeaderText("No Guest Number");
                            alert.setContentText("Please enter the guest number");
                            alert.showAndWait();
                        }
                       
                    } else{
                        //System.out.println("please select waiter");
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("NO WAITER SELECTED");
                        alert.setHeaderText("SELECT A WAITER");
                        alert.setContentText("select a waiter and add the guest number after that you can select your table");
                        alert.showAndWait();
                    }
                    
                });
            }//not contains table 0 ends
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

    public void moveToFrontPage(Node source) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/billing.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage;
            stage = (Stage) source.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getBankName(String value) {
        Pattern pattern = Pattern.compile("'(?:[^']+|'')*'", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            value = matcher.group().replace("'", "");
        }
        return value;
    }

    private void setBankList() {
        bankNames.add("BRAC BANK");
        bankNames.add("CITY BANK");
        bankNames.add("MTV");
        bankNames.add("EBL");
        bankNames.add("BKASH");
    }
    
    private void showOrderBox(SalesInfo newValue){
        UpdateOrderController updateOrder = new UpdateOrderController(newValue.getSessionId());
        vb.getChildren().clear();

        List<SalesDetails> salesList = updateOrder.getSalesDetails();
                    for (SalesDetails sd : salesList) {
                        // Label recipeLabel = new Label(sd.getRecipe().getName());
                        //vb.getChildren().add(recipeLabel);
                        HBox hb = new HBox();
                        hb.setSpacing(2);
                        hb.setPadding(new Insets(5, 2, 5, 10));
                        //1.Name of the Menu
                        Label nameLabel = new Label(sd.getRecipe().getName());
                        nameLabel.setMinWidth(170);
                        nameLabel.setMaxWidth(170);
                        nameLabel.wrapTextProperty().setValue(true);
                        hb.getChildren().add(nameLabel);

                        //3.quantity textfield creation
                        qty = new TextField();
                        qty.setId(sd.getRecipe().getId() + "");
                        qty.setText(sd.getQuantity()+"");
                        qty.setPrefWidth(40);
                        qty.setEditable(false);
                        hb.getChildren().add(qty);


                        vb.getChildren().addAll(hb);
                        vb.setSpacing(5);
                        k++;
                    }
                    discountField.setText("" + Math.round(Double.parseDouble(updateOrder.getSalesInfo().getDiscount()) /updateOrder.getSalesInfo().getSubTotal() * 100));
                    discountField.setEditable(false);
                    setSubtotal(updateOrder);
    }
    
    
    
    private void showBillingBox(GridPane cashGrid,HBox billingHbox,SalesInfo newValue) { 
                            VBox cardVbox = new VBox();
                            MenuTab.getSelectionModel().select(billOrderTab);
                            billOrderVBox.getChildren().clear();
                            //billingHbox.getChildren().clear();
                            cashGrid.getChildren().clear();
                            final Button cashButton = new Button("CASH");
                            cashButton.setMinWidth(140);
                            cashButton.setMinHeight(40);
                            cashButton.setStyle("-fx-text-fill:white");
                            final Button cardButton = new Button("CARD");
                            cardButton.setMinWidth(140);
                            cardButton.setMinHeight(40);
                            cardButton.setStyle("-fx-text-fill:white");
                            final Button splitButton = new Button("SPLIT");
                            splitButton.setMinWidth(140);
                            splitButton.setMinHeight(40);
                            splitButton.setStyle("-fx-text-fill:white");
                            final Button dueButton = new Button("DUE");
                            dueButton.setMinWidth(140);
                            dueButton.setMinHeight(40);
                            dueButton.setStyle("-fx-text-fill:white");

                            billingHbox.setPadding(new Insets(30, 10, 30, 10));
                            billingHbox.setSpacing(30);
                            billingHbox.getChildren().clear();
                            billingHbox.getChildren().addAll(cashButton, cardButton, splitButton, dueButton);
                            billOrderVBox.getChildren().add(billingHbox);

                            cashButton.setOnAction(new EventHandler<ActionEvent>() {
                                final TextField paidAmountField = new TextField();
                                @Override
                                public void handle(ActionEvent e) {
                                    //SalesInfoDao salesinfodao = new SalesInfoDao(); 
                                    SalesInfo si = (SalesInfo)salesInfoDao.checkDaoLong(newValue.getSessionId());
                                    billOrderVBox.getChildren().clear();
                                    cashGrid.getChildren().clear();
                                    billingHbox.setPadding(new Insets(30, 10, 30, 10));
                                    billingHbox.setSpacing(30);
                                    billingHbox.getChildren().clear();
                                    billingHbox.getChildren().addAll(cashButton, cardButton, splitButton, dueButton);
                                    billOrderVBox.getChildren().add(billingHbox);
                                    
                                    final Label cashDiscountLabel = new Label("Cash Discount");
                                    toBePaidDiscount = new TextField("");
                                    toBePaidDiscount.setOnAction((ActionEvent event) -> {
                                        Double amountsToBePaid = Double.parseDouble(toBePaidAmount.getText());
                                        Double discount = Double.parseDouble(toBePaidDiscount.getText());
                                        toBePaidAmount.setText( ""+(amountsToBePaid-discount) );
                                        
                                    });
                                    toBePaidDiscount.textProperty().addListener((observable, oldValue, newValue) -> {
                                        paidAmountField.setText("0");
                                        toBePaidAmount.setText("0");
                                        if (toBePaidDiscount.getText().isEmpty()) {
                                            newValue =0+"";
                                            toBePaidDiscount.setText("0");
                                        } else {
                                            if (!newValue.matches("\\d*")) {
                                                toBePaidDiscount.setText(newValue.replaceAll("[^\\d]", ""));
                                            } else {
                                                        Double amountsToBePaid = si.getOrderTotal();
                                                        Double discount = Double.parseDouble(toBePaidDiscount.getText());
                                                        toBePaidAmount.setText( ""+(amountsToBePaid-discount) );
                                           }
                                        }

                                    });
                                    
                                    
                                    
                                    final Label toBePaidLabel = new Label("Amounts to be Paid:");
                                    toBePaidLabel.setTextFill(Color.web("#dd0000"));
                                    toBePaidAmount = new TextField("");
                                    //toBePaidAmount.setTextFill(Color.web("#0076a3"));
                                    toBePaidAmount.setStyle("-fx-font: 24 arial;");
                                    
                                    
//                                    final Label discountAmountLabel = new Label("Disc. amount:");                                  
//                                    final TextField discountAmountField = new TextField();
//                                     discountAmountField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
//                                            if (!newValue.matches("\\d*")) {
//                                                discountField.setText(newValue.replaceAll("[^\\d]", ""));
//                                            }
//                                        });
//                                     
//                                            
//                                      discountAmountField.textProperty().addListener((observable, oldValue, newVal) -> {
//                                            toBePaidAmount.setText((Double.parseDouble(toBePaidAmount.getText())-Double.parseDouble(newVal))+"");
//                                           
//                                            
//                                      });

                                    final Label paidAmountLabel = new Label("Paid Amount");
                                    

                                   

                                    final Label changeAmountLabel = new Label("Change Amount");
                                    final TextField changeAmountField = new TextField();
                                    changeAmountField.setEditable(false);
                                    
                                    
                                    
                                    final Button cashSubmit = new Button("Print");
                                    cashSubmit.setMinWidth(130);
                                    cashSubmit.setStyle("-fx-text-fill:white");

                                    cashSubmit.setOnAction((ActionEvent e1) -> {
                                        if (Double.parseDouble(changeAmountField.getText()) >= 0) {
                                            SalesInfo sf = new SalesInfo();
                                            //SalesInfoDao salesInfoDao = new SalesInfoDao();
                                            
                                            sf = (SalesInfo) salesInfoDao.checkDaoLong(newValue.getSessionId());
                                            sf.setPaymentType("Cash");
                                            
                                            if(!toBePaidDiscount.getText().isEmpty()){
                                                sf.setDiscount( ""+( Double.parseDouble(sf.getDiscount())+Double.parseDouble(toBePaidDiscount.getText()) ) );
                                            
                                            }else{
                                                sf.setDiscount("0");
                                            }
                                            System.out.println("The discount that has been updated is "+sf.getDiscount());
                                            sf.setCashAmount(toBePaidAmount.getText());
                                            sf.setOrderTotal(Double.parseDouble(toBePaidAmount.getText()) );
                                            sf.setDueAmount("0");
                                            sf.setCardAmount("0");
                                            sf.setServiceCharge("0");
                                            sf.setPaidAmount(paidAmountField.getText());
                                            sf.setReturnedAmount(changeAmountField.getText());
                                            sf.setOrderStatus("Paid");
                                            sf.setGuestBillRequest(false);
                                            Tables table = new Tables();
                                            TableDao tabledao = new TableDao();
                                            table = (Tables) tabledao.checkdao(sf.getTable().getId());
                                            table.setTableStatus("unoccupied");
                                            tabledao.update(table);
                                            salesInfoDao.update(sf);
                                            id_qty.clear();
                                            SalesDetailsDao salesDetailsDao = new SalesDetailsDao();
                                            List<SalesDetails> salesList = salesDetailsDao.getListFromSameSession(sf.getSessionId());
                                            for (SalesDetails sd : salesList) {
                                                int recipeId = sd.getRecipe().getId();
                                                String recipeQuantity = sd.getQuantity()+"";
                                                id_qty.put(recipeId, recipeQuantity);
                                                System.out.println("sd recipe id is " + sd.getRecipe().getId());
                                                System.out.println("sd quantity is " + sd.getQuantity());
                                            }
                                            //calling the ingredientsUdate method
                                            ingredientLiveStockUpdate(id_qty);
                                            //making report
                                            PositionPdf p1 = new PositionPdf();
                                            p1.makePdf(newValue.getSessionId(), "cash");
                                            //making report ends
                                            //updating inventory ends
                                            Node source = (Node) e1.getSource();
                                            moveToFrontPage(source);
                                        } else {
                                            System.out.println("Paid amount is less than order total");
                                            Alert alert = new Alert(AlertType.INFORMATION);
                                            alert.setTitle("Invalid Payment");
                                            alert.setHeaderText("Please check the paid amount");
                                            alert.setContentText("Paid Amount is less than Order Total");
                                            alert.showAndWait();
                                        }
                                    });
                                    changeAmountField.setText("0");
                                    //SalesInfoDao salesInfoDao = new SalesInfoDao();
                                    SalesInfo sf = new SalesInfo();
                                    sf = (SalesInfo) salesInfoDao.checkDaoLong(newValue.getSessionId());
                                    //System.out.println(sf.getSubTotal());
                                    
                                    //double orderTotal = Double.parseDouble(sf.getSubTotal()) + Double.parseDouble(sf.getVat()) - Double.parseDouble(sf.getDiscount());
                                    double orderTotal = sf.getOrderTotal();
                                    paidAmountField.textProperty().addListener((observable, oldValue, newValue) -> {
                                        if (paidAmountField.getText().isEmpty()) {
                                            newValue =0+"";
                                            paidAmountField.setText("0");
                                        } else {
                                            if (!newValue.matches("\\d*")) {
                                                paidAmountField.setText(newValue.replaceAll("[^\\d]", ""));
                                            } else {
                                                changeAmountField.setText(Math.round((Double.parseDouble(newValue) - Double.parseDouble(toBePaidAmount.getText()))*100.0)/100.0 + "");
                                            }
                                        }

                                    });


                                    System.out.println("order total: " + sf.getOrderTotal());
                                    toBePaidAmount.setText(orderTotal + "");
                                    
                                    cashGrid.add(toBePaidLabel, 0, 0);
                                    cashGrid.add(toBePaidAmount, 1, 0);
                                    
                                    
                                    cashGrid.add(cashDiscountLabel, 0, 1);
                                    cashGrid.add(toBePaidDiscount, 1, 1);
                                  
                                    cashGrid.add(paidAmountLabel, 0, 2);
                                    cashGrid.add(paidAmountField, 1, 2);

                                    cashGrid.add(changeAmountLabel, 0, 3);
                                    cashGrid.add(changeAmountField, 1, 3);
 
                                    cashGrid.add(cashSubmit, 1, 4);
                                    cashGrid.setVgap(20);
                                    cashGrid.setHgap(10);
                                    
                                    
                                    //billOrderVBox.getChildren().remove(cashGrid);
                                    billOrderVBox.getChildren().addAll(cashGrid);

                                }
                            });

                            //cardButton set on action starts
                            cardButton.setOnAction((ActionEvent e) -> {
                                
                                billOrderVBox.getChildren().clear();
                                billingHbox.setPadding(new Insets(30, 10, 30, 10));
                                billingHbox.setSpacing(30);
                                billingHbox.getChildren().clear();
                                
                                billingHbox.getChildren().addAll(cashButton, cardButton, splitButton, dueButton);
                                billOrderVBox.getChildren().add(billingHbox);
                                
                                //enamul kaj kore
                                //GridPane cardGrid=new GridPane();
                                //cardGrid.setVgap(20);
                                cardVbox.setPadding(new Insets(30, 10, 500, 10));
                                cardVbox.setSpacing(30);
                                int r1 = 0;
                                ToggleGroup group = new ToggleGroup();
                                group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
                                        public void changed(ObservableValue<? extends Toggle> ov,
                                            Toggle toggle, Toggle new_toggle) {
                                            String value = group.getSelectedToggle().toString();
                                            buttonFlag = true;
                                        }
                                });
                                
                                for (String name : bankNames) {
                                    ToggleButton buttonBank = new ToggleButton(name);
                                    buttonBank.setMinWidth(250);
                                    buttonBank.setMinHeight(40);
                                    buttonBank.setToggleGroup(group);
                                    cardVbox.getChildren().add(buttonBank);
                                    
                                }
                                Button printButton = new Button("Print");
                                printButton.setStyle("-fx-text-fill:white;-fx-font-size:16px;");
                                printButton.setMinWidth(250);
                                printButton.setMinHeight(40);
                                cardVbox.getChildren().add(printButton);
                                printButton.setOnAction((ActionEvent e1) -> {
                                    if(buttonFlag){
                                                                            String value = group.getSelectedToggle().toString();
                                                                            value = getBankName(value);
                                                                            //System.out.println(value);
                                                                            SalesInfo sf = new SalesInfo();
                                                                            //SalesInfoDao salesInfoDao = new SalesInfoDao();
                                                                            sf = (SalesInfo) salesInfoDao.checkDaoLong(newValue.getSessionId());
                                                                            sf.setPaymentType("Card");
                                                                            sf.setCashAmount("0");
                                                                            sf.setDueAmount("0");
                                                                            sf.setCardAmount(sf.getOrderTotal()+"");
                                                                            sf.setServiceCharge("0");
                                                                            sf.setPaidAmount(sf.getOrderTotal()+"");
                                                                            sf.setReturnedAmount("0");
                                                                            sf.setOrderStatus("Paid");
                                                                            sf.setGuestBillRequest(false);
                                                                            sf.setBankName(value);
                                                                            Tables table = new Tables();
                                                                            TableDao tabledao = new TableDao();
                                                                            table = (Tables) tabledao.checkdao(sf.getTable().getId());
                                                                            table.setTableStatus("unoccupied");
                                                                            tabledao.update(table);
                                                                            salesInfoDao.update(sf);
                                                                            id_qty.clear();
                                                                            SalesDetailsDao salesDetailsDao = new SalesDetailsDao();
                                                                            List<SalesDetails> salesList = salesDetailsDao.getListFromSameSession(sf.getSessionId());
                                                                            for (SalesDetails sd : salesList) {
                                                                                int recipeId = sd.getRecipe().getId();
                                                                                String recipeQuantity = sd.getQuantity()+"";
                                                                                id_qty.put(recipeId, recipeQuantity);
                                                                                System.out.println("sd recipe id is " + sd.getRecipe().getId());
                                                                                System.out.println("sd quantity is " + sd.getQuantity());
                                                                            }
                                                                            //calling the ingredientsUdate method 
                                                                            ingredientLiveStockUpdate(id_qty);
                                                                            //making report
                                                                            PositionPdf pdf=new PositionPdf();
                                                                            pdf.makePdf(newValue.getSessionId(),"card");
                                                                            //making report
                                                                            //making report ends
                                                                            //updating inventory ends
                                                                            try {
                                                                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/billing.fxml"));
                                                                                Parent root = fxmlLoader.load();
                                                                                Stage stage;
                                                                                Node source = (Node) e1.getSource();
                                                                                stage = (Stage) source.getScene().getWindow();
                                                                                stage.getScene().setRoot(root);
                                                                            }catch (IOException ex) {
                                                                                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                                                                            }
                                    }else{
                                            Alert alert = new Alert(AlertType.INFORMATION);
                                            alert.setTitle("NO BANK SELECTED");
                                            alert.setHeaderText("EMPTY SELECTION");
                                            alert.setContentText("Please Select a bank name to proceed");
                                            alert.showAndWait();  
                                    }

                                });
                                // bank buttons action ends
                                
                                billOrderVBox.getChildren().remove(cashGrid);
                                billOrderVBox.getChildren().add(cardVbox);
                                cardVbox.setAlignment(Pos.CENTER);
                            });
                            //cardButton set on action ends
                            
                            
        //split button on action starts                    

                            splitButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    billOrderVBox.getChildren().clear();
                                    billingHbox.setPadding(new Insets(30, 10, 30, 10));
                                    billingHbox.setSpacing(30);
                                    billingHbox.getChildren().clear();
                                    billingHbox.getChildren().addAll(cashButton, cardButton, splitButton, dueButton);
                                    billOrderVBox.getChildren().add(billingHbox);

                                    HBox splitHBox = new HBox(10);
                                    TextField cashAmount = new TextField();
                                    cashAmount.setPromptText("Cash Amount");
                                   

                                    splitHBox.getChildren().add(cashAmount);

                                    VBox bankListPanel = new VBox();
                                    //bankListPanel.setPadding(new Insets(30, 10, 500, 10));
                                    bankListPanel.setSpacing(30);
                                    int r1 = 0;
                                    ToggleGroup group = new ToggleGroup();

                                    for (String name : bankNames) {
                                        ToggleButton buttonBank = new ToggleButton(name);
                                        buttonBank.setMinWidth(250);
                                        buttonBank.setMinHeight(40);
                                        buttonBank.setToggleGroup(group);
                                        bankListPanel.getChildren().add(buttonBank);

                                    }
                                    Button splitPrintButton = new Button("Print");
                                    splitPrintButton.setStyle("-fx-text-fill:white;-fx-font-size:16px;");
                                    splitPrintButton.setMinWidth(250);
                                    splitPrintButton.setMinHeight(40);
                                    bankListPanel.getChildren().add(splitPrintButton);

                                    splitHBox.getChildren().add(bankListPanel);
                                    //splitHBox.setAlignment(Pos.CENTER);
                                    TextField cardAmount = new TextField();
                                    cardAmount.setPromptText("Card Amount");
                                    cardAmount.setEditable(false);
                                    splitHBox.getChildren().add(cardAmount);
                               
                                    
                                    //cash amount ke change korle card amount change hobe
                                            SalesInfo sf = new SalesInfo();
                                            //SalesInfoDao salesInfoDao = new SalesInfoDao();
                                            sf = (SalesInfo) salesInfoDao.checkDaoLong(newValue.getSessionId());
                                            //System.out.println(sf.getSubTotal());
                                            double orderTotal = sf.getOrderTotal();
                                                    cashAmount.textProperty().addListener(new ChangeListener<String>() {
                                                            @Override
                                                            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                                                if (!newValue.matches("\\d*")) {
                                                                    cashAmount.setText(newValue.replaceAll("[^\\d]", ""));
                                                                }
                                                            }
                                                        });
                                                        cashAmount.textProperty().addListener((observable, oldValue, newValue) -> {
                                                            if (!newValue.trim().isEmpty()) {
                                                                double cardPayAmount =  orderTotal - Double.parseDouble(newValue) ;
                                                                splitPrintButton.setVisible(true);
                                                                if(cardPayAmount<0){
                                                                    cardAmount.setText("0");
                                                                    splitPrintButton.setVisible(false);
                                                                }
                                                                else{
                                                                    cardAmount.setText(cardPayAmount+"");
                                                                }
                                                            } else {
                                                                //cash amount jodi empty hoy
                                                            }

                                                        });
                                   
                                 //cash amount ke change korle card amount change hobe ends
                                    //AMOUNT TO BE PAID ADD LABEL
                                    final Label toBePaidLabel = new Label("Amounts to be Paid:  "+orderTotal);
                                    toBePaidLabel.setTextFill(Color.web("#0076a3"));
                                    toBePaidLabel.setStyle("-fx-font-size:18px;");
                                    billOrderVBox.getChildren().add(toBePaidLabel);
                                    //AMOUNT TO BE PAID ADD LABEL ENDS
                                    billOrderVBox.getChildren().add(splitHBox);
                                    
                                    
                                    splitPrintButton.setOnAction((ActionEvent e1) -> {
                                        SalesInfo salesInfo1 = new SalesInfo();
                                        //SalesInfoDao salesInfoDao1 = new SalesInfoDao();
                                        salesInfo1 = (SalesInfo) salesInfoDao.checkDaoLong(newValue.getSessionId());
                                        String value = group.getSelectedToggle().toString();
                                        value = getBankName(value);
                                        salesInfo1.setBankName(value);
                                        salesInfo1.setCardAmount(cardAmount.getText());
                                        salesInfo1.setCashAmount(cashAmount.getText());
                                        salesInfo1.setPaymentType("Split");
                                        salesInfo1.setServiceCharge("0");
                                        salesInfo1.setDueAmount("0");
                                        salesInfo1.setPaidAmount(orderTotal+"");
                                        salesInfo1.setReturnedAmount("0");
                                        salesInfo1.setOrderStatus("Paid");
                                        salesInfo1.setGuestBillRequest(false);
                                        
                                        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date()); 
                                        salesInfo1.setDate(date);
                                        if (value.isEmpty()||value==null) {
                                            Alert alert = new Alert(AlertType.INFORMATION);
                                            alert.setTitle("INVALID BANK NAME");
                                            alert.setHeaderText("Select A Valid Bank");
                                            alert.setContentText("Please Select a bank name to proceed");
                                            alert.showAndWait();
                                        } else {
                                            Tables table = new Tables();
                                            TableDao tabledao = new TableDao();
                                            table = (Tables) tabledao.checkdao(salesInfo1.getTable().getId());
                                            table.setTableStatus("unoccupied");
                                            tabledao.update(table);
                                            salesInfoDao.update(salesInfo1);
                                                id_qty.clear();
                                                SalesDetailsDao salesDetailsDao = new SalesDetailsDao();
                                                List<SalesDetails> salesList = salesDetailsDao.getListFromSameSession(newValue.getSessionId());
                                                for (SalesDetails sd : salesList) {
                                                    int recipeId = sd.getRecipe().getId();
                                                    String recipeQuantity = sd.getQuantity()+"";
                                                    id_qty.put(recipeId, recipeQuantity);
                                                    System.out.println("sd recipe id is " + sd.getRecipe().getId());
                                                    System.out.println("sd quantity is " + sd.getQuantity());
                                                }
                                                //calling the ingredientsUdate method 
                                                ingredientLiveStockUpdate(id_qty);
                                                //making report
                                                PositionPdf p=new PositionPdf();
                                                p.makePdf(newValue.getSessionId(),"split");
                                                //making report ends
                                            
                                            try {
                                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/billing.fxml"));
                                                Parent root = fxmlLoader.load();
                                                Stage stage;
                                                Node source = (Node) e1.getSource();
                                                stage = (Stage) source.getScene().getWindow();
                                                stage.getScene().setRoot(root);
                                            }catch (IOException ex) {
                                                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    });
                                }
                            });
    //split button on action ends
                            
                            
    //due button set on action starts 
        dueButton.setOnAction((ActionEvent event) -> {
            UpdateOrderController updateOrder = new UpdateOrderController(newValue.getSessionId());
            SalesInfo salesInfo1 = new SalesInfo();
            //SalesInfoDao salesInfoDao1 = new SalesInfoDao();
            salesInfo1 = (SalesInfo) salesInfoDao.checkDaoLong(newValue.getSessionId());
            salesInfo1.setBankName("");
            salesInfo1.setCardAmount("0");
            salesInfo1.setCashAmount("0");
            salesInfo1.setPaymentType("Split");
            salesInfo1.setServiceCharge("0");
            salesInfo1.setDueAmount(updateOrder.getSalesInfo().getOrderTotal()+"");
            salesInfo1.setPaidAmount("0");
            salesInfo1.setReturnedAmount("0");
            salesInfo1.setOrderStatus("Paid");
            salesInfo1.setGuestBillRequest(false);
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
//            try {
//                String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
//                salesInfo1.setTime(time);
//                Date dt= new SimpleDateFormat("dd-MM-yyyy").parse(date);
//                salesInfo1.setDate(dt);
//            } catch (ParseException ex) {
//                Logger.getLogger(BillingController.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            salesInfo1.setDate(date);
            Tables table = new Tables();
            TableDao tabledao = new TableDao();
            table = (Tables) tabledao.checkdao(salesInfo1.getTable().getId());
            table.setTableStatus("unoccupied");
            tabledao.update(table);
            
            salesInfoDao.update(salesInfo1);
            id_qty.clear();
            SalesDetailsDao salesDetailsDao = new SalesDetailsDao();
            List<SalesDetails> salesList = salesDetailsDao.getListFromSameSession(newValue.getSessionId());
            for (SalesDetails sd : salesList) {
                int recipeId = sd.getRecipe().getId();
                String recipeQuantity = sd.getQuantity()+"";
                id_qty.put(recipeId, recipeQuantity);
                System.out.println("sd recipe id is " + sd.getRecipe().getId());
                System.out.println("sd quantity is " + sd.getQuantity());
            }
            //calling the ingredientsUdate method
            ingredientLiveStockUpdate(id_qty);
                                Node source = (Node) event.getSource();
                                moveToFrontPage(source);
                                //making report
                                PositionPdf p1 = new PositionPdf();
                                p1.makePdf(newValue.getSessionId(),"due");
                                //making report ends
                            });
    //due button set on action ends
     
    }

//guest field er on focus er code

//guest field er on focus er code ends
    
 //converter
    

double RoundTo2Decimals(double val) {
      DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
}


    
    
}//last billing controller bracket
