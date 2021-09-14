package ku.cs.shop.controllers.seller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import ku.cs.shop.controllers.system.StockController;
import ku.cs.shop.controllers.system.HeadController;
import ku.cs.shop.models.Book;

import ku.cs.shop.services.BookDetailDataSource;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SellerController implements Initializable {
    @FXML private HBox hBoxSellerStock;
    @FXML private Label userNameLabel;
    @FXML private ScrollPane scoll;
    @FXML private GridPane grid;
    @FXML private Button orderListButtonn;
    @FXML private Button addStockButton;
    @FXML void handleAllTypeBookButton(ActionEvent event) { }
    @FXML void handleCartoonBookButton(ActionEvent event) { }
    @FXML void handleMagazineButton(ActionEvent event) { }

    private BookDetailDataSource data = new BookDetailDataSource("src/main/java/ku/cs/shop/bookDetail.csv");
    private ArrayList<Book> books = data.readData();

    private HeadController headController = new HeadController();

    public void initialize (URL location, ResourceBundle resource){
//        try {
//            FXMLLoader fxmlLoaderHead = new FXMLLoader();
//            fxmlLoaderHead.setLocation(getClass().getResource(headController.pagesHeader()));
//            gridHead.add(fxmlLoaderHead.load(),0,0);
//        }catch(IOException e){
//            e.printStackTrace();
//        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < 3; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/stock.fxml"));

                grid.add(fxmlLoader.load(), column, row++); // child,col,row
                StockController stockController = fxmlLoader.getController();
                stockController.setData(books.get(i));
                stockController.changeData();

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddStockButton(){
        try {
            com.github.saacsos.FXRouter.goTo("seller");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า sellerStock ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleOrderListButton(){
        try {
            com.github.saacsos.FXRouter.goTo("seller");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า sellerStock ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
