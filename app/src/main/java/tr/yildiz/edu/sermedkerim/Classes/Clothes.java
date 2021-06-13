package tr.yildiz.edu.sermedkerim.Classes;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;

public class Clothes {
    String clothesType;
    String color;
    String figure;
    String date;
    Float price;
    Bitmap photo;

    public static ArrayList<Clothes> selectedClothes = new ArrayList<>();
    public static ArrayList<Bitmap> cabinRoomClothes = new ArrayList<>();
    public static HashMap<String,Clothes> combine = new HashMap<>();
    public static ArrayList<Bitmap[]> combines = new ArrayList<>();

    public Clothes(String clothesType, String color, String figure, String date, Float price, Bitmap photo) {
        this.clothesType = clothesType;
        this.color = color;
        this.figure = figure;
        this.date = date;
        this.price = price;
        this.photo = photo;
    }

    public String getClothesType() {
        return clothesType;
    }

    public String getColor() {
        return color;
    }

    public String getFigure() {
        return figure;
    }

    public String getDate() {
        return date;
    }

    public Float getPrice() {
        return price;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setClothesType(String clothesType) {
        this.clothesType = clothesType;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
