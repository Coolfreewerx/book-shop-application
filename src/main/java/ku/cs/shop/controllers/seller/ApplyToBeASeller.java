package ku.cs.shop.controllers.seller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.shop.models.Account;
import ku.cs.shop.models.AccountList;
import ku.cs.shop.models.UserAccount;
import ku.cs.shop.services.AccountDataSource;
import ku.cs.shop.services.DataSource;

import java.io.IOException;
import java.time.LocalDateTime;

public class ApplyToBeASeller{ //สมัครเป็นผู้ขายสินค้า
    private String shopName;
    private String password;
    private String passwordRecheck;
    @FXML private Label usernameLabel;
    @FXML private Button submitButton;
    @FXML private TextField nameShopTextField;
    @FXML private PasswordField passwordTextField1;
    @FXML private PasswordField passwordTextField2;
    @FXML private Label notificationShopName;
    @FXML private Label notificationPassword1;
    @FXML private Label notificationPassword2;

    private Account account = new UserAccount ("Freshmin", "Na", "justmeka", "13082000",
            "15", "11", "2001", "default.png", "0823341025", "Women",
            "null", "ยังไม่สมัครเป็นผู้ขาย", "working", LocalDateTime.now());
//    (String firstName, String lastName, String userName, String password,
//    String birthDay, String birthMonth, String birthYear,
//    String imageName, String phone, String sex, String address, String shopName,
//    String status, LocalDateTime loginTime )

    private AccountList accountList ;
//    private Account account ;
//
//    public void initialize(){
//        accountList = (AccountList) com.github.saacsos.FXRouter.getData() ;
//        account = accountList.getCurrentAccount() ;
//        showHead();
//        showData();
//    }

    @FXML
    public void handleKeyCheckShopName(){
//        if(accountList.checkShopNameHaveUsed(nameShopTextField.getText())) {
//            notificationShopName.setText("**ใช้แล้วอีควาย**") ;
//        } else {
//            notificationShopName.setText("**ใช้ได้จ้า**") ;
//        }
    }
    @FXML
    public void handleKeyPassword() {
        notificationPassword1.setText(Account.comparePassword(passwordTextField1.getText(),account.getPassword()));
    }
    @FXML
    public void handleKeyCheckPassword() {
        notificationPassword2.setText(Account.comparePassword(passwordTextField1.getText(),passwordTextField2.getText()));
    }

    @FXML
    public void handleAddSellerStockButton(){
        shopName = nameShopTextField.getText();
        password = passwordTextField1.getText();
        passwordRecheck = passwordTextField1.getText();
        account.setShopName(shopName);

//        DataSource<BookList> dataSource;
//        dataSource = new BookDetailDataSource("src/main/java/ku/cs/shop/bookDetail.csv");
//        BookList bookList = dataSource.readData();
//        bookList.addBook(book);
//        dataSource.writeData(bookList);

        DataSource<AccountList> dataSource;
        dataSource = new AccountDataSource("csv-data/accountData.csv");
        AccountList accountList = dataSource.readData();
        accountList.addAccount(account);
        dataSource.writeData(accountList);

        try {
            com.github.saacsos.FXRouter.goTo("seller");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า seller ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }

    @FXML
    public void handleToHomeButton(ActionEvent actionEvent) { //ปุ่มสำหรับกดไปหน้า home
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้าเพจหลักไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
