package ku.cs.shop.models;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class Promotion {
    private String shopName;
    private String codePromotion;
    private int ratePrice;
    private int priceReductionInPercentage;
    private int priceReductionInBaht;
    private String promotionDetail;
    private LocalDateTime timeOfAddingPromotion;
    private static boolean codePromotionCheck = false;

    public Promotion(){}

    public Promotion(String shopName, String codePromotion, int ratePrice, int priceReductionInPercentage, int priceReductionInBaht, String promotionDetail, LocalDateTime timeOfAddingPromotion){
        this.shopName = shopName;
        this.codePromotion = codePromotion;
        this.ratePrice = ratePrice;
        this.priceReductionInPercentage = priceReductionInPercentage;
        this.priceReductionInBaht = priceReductionInBaht;
        this.promotionDetail = promotionDetail;
        this.timeOfAddingPromotion = timeOfAddingPromotion;
    }

    public String getShopName(){ return shopName; }
    public String getCodePromotion(){ return  codePromotion; }
    public int getRatePrice() { return ratePrice; }
    public int getPriceReductionInBaht() { return priceReductionInBaht; }
    public int getPriceReductionInPercentage() { return priceReductionInPercentage; }
    public String getPromotionDetail() { return promotionDetail; }
    public LocalDateTime getTimeOfAddingPromotion() { return timeOfAddingPromotion;}

    public static boolean getCodePromotionCheck(){ return codePromotionCheck; }

    public void setShopName(String shopName) { this.shopName = shopName; }
    public void setCodePromotion(String codePromotion) { this.codePromotion = codePromotion; }
    public void setRatePrice(int ratePrice) { this.ratePrice = ratePrice; }
    public void setPriceReductionInBaht(int priceReductionInBaht) { this.priceReductionInBaht = priceReductionInBaht; }
    public void setPriceReductionInPercentage(int priceReductionInPercentage) { this.priceReductionInPercentage = priceReductionInPercentage; }
    public void setPromotionDetail(String promotionDetail) { this.promotionDetail = promotionDetail; }
    public void setTimeOfAddingPromotion(LocalDateTime timeOfAddingPromotion){ this.timeOfAddingPromotion = timeOfAddingPromotion; }

    // เช็กความถูกต้องของรูปแบบโค้ดส่วนลด
    public static String checkCodePromotionCondition(String codePromotion) {
        if (!Pattern.matches("[a-zA-Z0-9]+", codePromotion)) {
            codePromotionCheck = false ;
            return "โค้ดโปรโมชั่นไม่ตรงตามรูปแบบที่กำหนด" ;
        }
        codePromotionCheck = true ;
        return null ;
    }
    public String toCsv() {
        return "\"" + shopName + "\"," +
                "\"" + codePromotion + "\"," + ratePrice + "," +
                priceReductionInPercentage + "," +
                priceReductionInBaht + "," +
                "\"" + promotionDetail + "\"," + timeOfAddingPromotion;
    }

    @Override
    public String toString() {
        return  "shopName : " + shopName + " โค้ดโปรโมชั่น : " + codePromotion + " ส่วนลด " + priceReductionInPercentage + " เปอร์เซ็นต์ " +
                priceReductionInBaht + ", " + "ส่วนลด " + promotionDetail + " บาท" + " ซื้อตั้งแต่ : " + ratePrice + "เวลา : " + timeOfAddingPromotion;
    }
}