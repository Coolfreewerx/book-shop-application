package ku.cs.shop.controllers.system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.apache.commons.lang3.RandomStringUtils;
import javafx.scene.text.Text;
import java.util.Random;
import ku.cs.shop.models.Book;
import ku.cs.shop.models.Order;
import ku.cs.shop.models.OrderList;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.OrderDataSource;

import java.io.IOException;
import java.net.URL;
import java.text.StringCharacterIterator;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class ConfirmOrderController {
    @FXML private Label bookNameLabel;
    @FXML private Label sumBookPriceLabel;
    @FXML private Text noficationItem;
    @FXML private TextField inputNumOfBookTextField;

    Random random = new Random();
    private int numberRandomTracking = random.nextInt(900000-100000) + 100000;

    private Order order = new Order();
    private BookDetailController bookDetailController;
    private int totalBookOrdered;
    private int stockInShop;
    private double costOfBook;
    private String randomStringAtIndexOne = RandomStringUtils.randomAlphanumeric(2);

    public void setStockInShop(int stockInShop) {
        this.stockInShop = stockInShop;
    }
    public void setCostOfBook(double costOfBook) {
        this.costOfBook = costOfBook;
    }
    public void setTotalBookOrdered(int totalBookOrdered) {
        this.totalBookOrdered = totalBookOrdered;
    }

//   OrderDataSource orderDataSource = new OrderDataSource("csv-data/bookOrder.csv");
//    OrderList orderList = orderDataSource.readData();

    public void setController(BookDetailController book) {
        this.bookDetailController = book;
        bookNameLabel.setText(bookDetailController.getBook().getBookName());
        setCostOfBook(bookDetailController.getBook().getBookPrice());
        setStockInShop(bookDetailController.getBook().getBookStock());
        System.out.println("book stock of " + bookNameLabel.getText() + " is " + stockInShop);
    }

    public int checkInputNumOfOrder() {
        if ( Pattern.matches("[1-9]+[0-9]+" , inputNumOfBookTextField.getText())
                || ( (inputNumOfBookTextField.getText().length() == 1) && Pattern.matches("[0-9]+", inputNumOfBookTextField.getText()) )
                && Integer.parseInt(inputNumOfBookTextField.getText()) >= 0) {
            setTotalBookOrdered(Integer.parseInt(inputNumOfBookTextField.getText()));
        }

        if (Integer.parseInt(inputNumOfBookTextField.getText()) < 0)
            setTotalBookOrdered(Integer.parseInt("0"));

        return 0;
    }

    @FXML
    void handleAddNumOfBookInput(ActionEvent event) {
        checkInputNumOfOrder();
        System.out.println(totalBookOrdered);

        if(totalBookOrdered > stockInShop)
            noficationItem.setText("สินค้าในคลังไม่เพียงพอ กรุณากรอกจำนวนใหม่");
            sumBookPriceLabel.setText("0.00");

        if (totalBookOrdered <= stockInShop) {
            noficationItem.setText("");
            sumBookPriceLabel.setText(String.format("%.02f",costOfBook * totalBookOrdered));
             if ( sumBookPriceLabel.getText().equals("0.00")) {
                 noficationItem.setText("กรุณากรอกจำนวนสินค้าให้ถูกต้อง (มากกว่า 0) ");
             }
        }
    }

    @FXML
    void handleComfirmBuyBook(ActionEvent event) {
        if (Double.parseDouble(sumBookPriceLabel.getText()) > 0) {
            order.setBookImage(bookDetailController.getBook().getBookImg());
            order.setBookName(bookDetailController.getBook().getBookName());
            order.setBookShop(bookDetailController.getBook().getBookShop());
            order.setTotalBookOrdered(Integer.parseInt(inputNumOfBookTextField.getText()));
            order.setTotalPriceOrdered(Double.parseDouble(sumBookPriceLabel.getText()));
            order.setTrackingNumber(randomStringAtIndexOne + "-" + numberRandomTracking);
            order.setCustomerName(bookDetailController.getAccount().getUserName());
            order.setCustomerPhone(bookDetailController.getAccount().getPhone());

            if (order.getCustomerPhone().equals("null"))
                order.setCustomerPhone("ไม่มีข้อมูลการติดต่อ");

            order.setTimeOfOrdered(LocalDateTime.now());

            System.out.println("---------------");
            System.out.println(order.getBookImage());
            System.out.println(order.getBookName());
            System.out.println(order.getBookShop());
            System.out.println(order.getCustomerName());
            System.out.println(order.getCustomerPhone());
            System.out.println(order.getTotalBookOrdered());
            System.out.println(order.getTrackingNumber());
            System.out.println(order.getTotalPriceOrdered());
            System.out.println(order.getTimeOfOrdered());
        }

//        orderList.addOrder(order);
//        orderDataSource.writeData(orderList);
    }

    @FXML
    void handleClosePage(MouseEvent event) {
        try {
            com.github.saacsos.FXRouter.goTo("bookDetail");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
