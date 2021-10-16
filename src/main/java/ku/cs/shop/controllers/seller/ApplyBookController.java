package ku.cs.shop.controllers.seller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import ku.cs.shop.controllers.system.ChoiceApplySubtypeBookController;
import ku.cs.shop.controllers.system.StockController;
import ku.cs.shop.controllers.user.ChoiceProvideTypeBookController;
import ku.cs.shop.models.*;
import ku.cs.shop.services.BookDetailDataSource;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.ProvideTypeBookDataSource;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ApplyBookController {
    Seller seller = new Seller();
    Book book = new Book();
    private BookDetailDataSource data = new BookDetailDataSource("csv-data/bookDetail.csv");
    private BookList books = data.readData();

    @FXML private Button addImgButton;
    @FXML private TextField bookNameTextField;
    @FXML private TextField bookAuthorTextField;
    @FXML private TextField bookISBNTextField;
    @FXML private Button addBookButton;
    @FXML private TextField bookPageTextField;
    @FXML private Button goToSellerStockButton;
    @FXML private TextArea bookDetailTextArea;
    @FXML private TextField bookPublisherTextField;
    @FXML private TextField bookStockTextField;
    @FXML private TextField leastStockTextField;
    @FXML private TextField bookPriceTextField;
    @FXML private Label NotificationBookISBN;
    @FXML private Label NotificationBookPage;
    @FXML private Label NotificationBookStock;
    @FXML private Label NotificationLeastStock;
    @FXML private Label NotificationBookPrice;
    @FXML private Label NotificationCantAdd;
    @FXML private ImageView imageView;
    @FXML private MenuButton menuButton;
    @FXML private Button status;
    @FXML private Label usernameInHead;
    @FXML private ImageView img;
    @FXML private ImageView logoJavaPai;
    @FXML private ImageView userImageView;
    @FXML private FlowPane flowPaneSubTypeBook;

    @FXML private Label subTypeBookLabel;
    @FXML private TextField subTypeBookTextField;

    private File selectedImage;
    private String imageName;
    private String currentType;
    private ArrayList<ProvideTypeBook> typeBookArrayList = new ArrayList<>();

    private ArrayList<Account> accountsList = new ArrayList<>();
    private AccountList accountList ;
    private Account account ;

    private ProvideTypeBookDataSource provideTypeBookDataSource = new ProvideTypeBookDataSource("csv-data/provideTypeBookData.csv");
    private ProvideTypeBookList typeBookList = provideTypeBookDataSource.readData();
    private ProvideTypeBook provideTypeBook;

    public void initialize(){
        accountList = (AccountList) com.github.saacsos.FXRouter.getData() ;
        account = accountList.getCurrentAccount() ;
        System.out.println("account " + account);
        System.out.println(userImageView);
        userImageView.setImage(new Image(account.getImagePath()));

        pagesHeader();
        addBookTypeToMenuItem();
    }

    @FXML public void handleKeyBookISBN(){
        book.setBookISBN(bookISBNTextField.getText());
        if(!seller.isBookISBNCorrect(book.getBookISBN())){ book.setBookISBN(""); }
        NotificationBookISBN.setText(seller.checkBookISBNCorrect(book.getBookISBN()));
    }

    @FXML public void handleKeyBookPage(){
        book.setBookPage(bookPageTextField.getText());
        if(! seller.isIntNumber(book.getBookPage())){book.setBookPage("");}
        NotificationBookPage.setText(seller.checkIntNumber(book.getBookPage()));
    }

    @FXML public void handleKeyBookStock(){
        if(! seller.isIntNumber(bookStockTextField.getText())){book.setBookStock(-1);}
        else {book.setBookStock(Integer.parseInt(bookStockTextField.getText()));}
        NotificationBookStock.setText(seller.checkIntNumber(bookStockTextField.getText()));
    }

    @FXML public void handleKeyLeastStock(){
        if(!seller.isIntNumber(leastStockTextField.getText())){book.setLeastStock(-1);}
        else {book.setLeastStock(Integer.parseInt(leastStockTextField.getText()));}
        NotificationLeastStock.setText(seller.checkIntNumber(leastStockTextField.getText()));
    }

    @FXML public void handleKeyBookPrice(){
        if(!seller.isDoubleNumber(bookPriceTextField.getText())){book.setBookPrice(-1);}
        else {book.setBookPrice(Double.parseDouble(bookPriceTextField.getText()));}
        NotificationBookPrice.setText(seller.checkDoubleNumber(bookPriceTextField.getText()));
    }


    public void addBookTypeToMenuItem() {
        for (String type : typeBookList.getSuperTypeBook()) {
            System.out.println("for addBookTypeToMenuItem : " + type);
            MenuItem subBookTypeMenuItem = new MenuItem(type);
            menuButton.getItems().add(subBookTypeMenuItem);
            subBookTypeMenuItem.setOnAction(this :: handleSubBookTypeMenuItem);
        }
    }

    public void handleSubBookTypeMenuItem(ActionEvent actionEvent) {
        MenuItem menuItem = (MenuItem) actionEvent.getSource();
        changeBookType(menuItem.getText());
        System.out.println("Click to " + currentType);
    }

    public void changeBookType(String type){
        typeBookArrayList.clear();
        flowPaneSubTypeBook.getChildren().clear();
        currentType = type;
        book.setBookType(type);
        menuButton.setText(type);


        ArrayList<ProvideTypeBook> provideTypeBookArrayList = typeBookList.findSubTypeBook(book.getBookType());
        int numTypeBookList = typeBookList.numOfSubTypeBook(book.getBookType());
        System.out.println("NumArraylist : " + provideTypeBookArrayList.size());

        try {
            for (int i = 0; i < numTypeBookList ; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/choiceApplySubTypeBook.fxml"));

                flowPaneSubTypeBook.getChildren().add(fxmlLoader.load());
                ChoiceApplySubtypeBookController choiceApplySubtypeBookController = fxmlLoader.getController();
                choiceApplySubtypeBookController.setData(provideTypeBookArrayList.get(i),typeBookArrayList,accountList,i,typeBookList);
                choiceApplySubtypeBookController.changeData();
//                ProvideTypeBook provideTypeBook = choiceApplySubtypeBookController.sendDataBack();
//                typeBookArrayList.add(provideTypeBook);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddImageButton (ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg"));
        selectedImage = fileChooser.showOpenDialog(null);
        if (selectedImage != null) {
            Image image = new Image(selectedImage.toURI().toString());
            imageView.setImage(image);
        }
        book.setBookImg("Haveimage");
    }

    public void setImageName() {
        if (!selectedImage.equals("")) {
            imageName =  bookNameTextField.getText() + "-"
                    + LocalDate.now().getYear() + "-"
                    + LocalDate.now().getMonth() + "-"
                    + LocalDate.now().getDayOfMonth() + "-"
                    + LocalDateTime.now().getHour() + LocalDateTime.now().getMinute() + LocalDateTime.now().getSecond() + ".png" ;
            seller.copyImageToPackage(selectedImage , imageName) ;
        } else {
            imageName = "default.png" ;
        }
    }

    @FXML
    public void handleAddBookButton(ActionEvent actionEvent){
//        System.out.println("typebookarraylist after sndBackData Loop " + 0 + " " + typeBookArrayList.get(0).getSubTypeBook());
        book.setBookName(bookNameTextField.getText());
        book.setBookAuthor(bookAuthorTextField.getText());
        book.setBookDetail(bookDetailTextArea.getText());
        book.setBookPublisher(bookPublisherTextField.getText());
        book.setBookShop(account.getShopName());
        book.setTimeOfAddingBook(LocalDateTime.now());

        book.setBookISBN(bookISBNTextField.getText());
        book.setBookPage(bookPageTextField.getText());
        book.setBookStock(Integer.parseInt(bookStockTextField.getText()));
        book.setLeastStock(Integer.parseInt(leastStockTextField.getText()));
        book.setBookPrice(Double.parseDouble(bookPriceTextField.getText()));
        book.setTypeBookArrayList(typeBookArrayList);
        seller.setTypeBook(book);


        if (seller.getDataCheck(book) && (seller.isBookISBNCorrect(book.getBookISBN())) && (seller.isIntNumber(book.getBookPage()))
                &&(seller.isIntNumber(bookStockTextField.getText()))&&(seller.isIntNumber(leastStockTextField.getText())) &&(seller.isDoubleNumber(bookPriceTextField.getText()))) {
            setImageName();
            book.setBookImg(imageName);
            DataSource<BookList> dataSource;
            dataSource = new BookDetailDataSource("csv-data/bookDetail.csv");
            BookList bookList = dataSource.readData();
            bookList.addBook(book);

            dataSource.writeData(bookList);

            try {
                com.github.saacsos.FXRouter.goTo("sellerStock",accountList);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("ไปที่หน้า sellerStock ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        } else{
            NotificationCantAdd.setText("ไม่สามารถเพิ่มสินค้าได้ กรุณาตรวจสอบข้อมูลใหม่อีกครั้งค่ะ");
            System.out.println("seller.getDataCheck(book) " + seller.getDataCheck(book) );
            System.out.println("seller.isBookISBNCorrect(book.getBookISBN()) " + seller.isBookISBNCorrect(book.getBookISBN()));
            System.out.println("seller.isNumber(book.getBookPage() " + seller.isIntNumber(book.getBookPage()));
            System.out.println("seller.isNumber(bookStockTextField.getText()) " + seller.isIntNumber(bookStockTextField.getText()));
            System.out.println("seller.isNumber(leastStockTextField.getText()) " + seller.isIntNumber(leastStockTextField.getText()));
            System.out.println("seller.isNumber(bookPriceTextField.getText()) " + seller.isDoubleNumber(bookPriceTextField.getText()));
        }
    }

    @FXML public void handleSellerStockButton(){
        try {
            com.github.saacsos.FXRouter.goTo("sellerStock",accountList);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า sellerStock ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    @FXML
    public void handleToAccountDetailButton(ActionEvent actionEvent) { //ปุ่มสำหรับกดไปหน้า home
        try {
            com.github.saacsos.FXRouter.goTo("accountDetail" ,accountList);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า accountDetail ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleToSellerButton(ActionEvent actionEvent) { //ปุ่มสำหรับกดไปหน้า home
        if (account.getShopName().equals("ยังไม่ได้สมัครเป็นผู้ขาย")) {
            try {
                com.github.saacsos.FXRouter.goTo("sellerHaventApply",accountList);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("ไปที่หน้า sellerHaventApply ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
        else{
            try {
                com.github.saacsos.FXRouter.goTo("sellerStock",accountList);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("ไปที่หน้า sellerHaventApply ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    @FXML
    public void handleAllTypeBookButton(ActionEvent actionEvent) { //ปุ่มสำหรับกดไปหน้าหนังสือทั้งหมด
        try {
            com.github.saacsos.FXRouter.goTo("pageBookType", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pagesHeader() { // กำหนดและแสดงข้อมูลตรงส่วน head page
        usernameInHead.setText(account.getUserName());
        img.setImage(new Image(account.getImagePath()));
        if(account instanceof AdminAccount){
            status.setText("Admin");
        }else if(account.getShopName().equals("ยังไม่ได้สมัครเป็นผู้ขาย")){
            status.setText("User");
        }else {
            status.setText("Seller");
        }
    }


    @FXML
    public void mouseClickedInLogo(MouseEvent event){ // คลิกที่ logo แล้วจะไปหน้า home
        try{
            logoJavaPai.getOnMouseClicked();
            com.github.saacsos.FXRouter.goTo("home" ,accountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ออกจากระบบกลับไปล็อกอิน
    @FXML
    public void handleToLogoutButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleToOrderPageButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("bookOrderOfUser" ,accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
