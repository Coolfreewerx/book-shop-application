package ku.cs.shop.controllers.user;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import ku.cs.shop.controllers.system.StockController;
import ku.cs.shop.models.*;
import ku.cs.shop.services.BookDetailDataSource;
import ku.cs.shop.services.ProvideTypeBookDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class ProvideTypeBookController {

    private AccountList accountList ;
    private Account account ;
    private UserAccount selectedAccount ;

    private ProvideTypeBookDataSource provideTypeBookDataSource = new ProvideTypeBookDataSource("csv-data/provideTypeBook.csv");
    private ProvideTypeBookList typeBookList = provideTypeBookDataSource.readData();
    private ProvideTypeBook provideTypeBook = new ProvideTypeBook("","");

    @FXML private FlowPane flowPaneForSubTypeBook;


    private BookDetailDataSource data = new BookDetailDataSource("csv-data/bookDetail.csv");
    private BookList books = data.readData();

    public void initialize(){
        accountList = (AccountList) com.github.saacsos.FXRouter.getData() ;
        account = accountList.getCurrentAccount() ;
    }


    @FXML private ImageView logoJavaPai;
    @FXML private Button provideUserButton;
    @FXML private Button provideShopButton;
    @FXML private Label topicLabel;
    @FXML private Label mainTopicLabel1;
    @FXML private Label mainTopicLabel;
    @FXML private Label topicLabel1;
    @FXML private TextField newBooktypeTextField;
    @FXML private Button checkBookButton;
    @FXML private Label notificationCheckTypeBookLabel;
    @FXML private Label topicLabel11;
    @FXML private Button addSubTypeBookButton;

    String newBookType;

    @FXML
    public void mouseClickedInLogo(MouseEvent event){
        try{
            logoJavaPai.getOnMouseClicked();
            com.github.saacsos.FXRouter.goTo("home" ,accountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleKeyNewBookTypeTextField(){
        newBookType = newBooktypeTextField.getText();
        if(typeBookList.checkNewTypeBook(newBookType)){
            notificationCheckTypeBookLabel.setText("มีประเภทหนังสือนี้อยู่แล้ว กรุณากรอกประเภทหนังสือใหม่");
        }
        else{
            notificationCheckTypeBookLabel.setText("");
        }
    }

    @FXML
    public void CheckTypeBookButton(ActionEvent actionEvent){
        if (!typeBookList.checkNewTypeBook(newBookType)){
            provideTypeBook.setSuperTypeBook(newBookType);
        }
    }

    @FXML
    public void handleToProvideAdminButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("forAdmin" ,accountList);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า provideTypeBookByAdmin ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddSubTypeBookButton(ActionEvent actionEvent) throws IOException {
        if (! provideTypeBook.getSuperTypeBook().equals("")) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ku/cs/choiceProvideTypeBookByAdmin.fxml"));
            flowPaneForSubTypeBook.getChildren().add(fxmlLoader.load());

            ChoiceProvideTypeBookController choiceProvideTypeBookController = fxmlLoader.getController();

        }
        else{
            notificationCheckTypeBookLabel.setText("กรุณากรอกประเภทของหนังสือ");
        }
    }

}
