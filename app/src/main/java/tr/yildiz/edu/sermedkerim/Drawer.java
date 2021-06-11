package tr.yildiz.edu.sermedkerim;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Drawer {

    String number;
    ArrayList<Bitmap> clothes;
    ArrayList<Clothes> clothesArrayList;

    public static ArrayList<Drawer> drawerArrayList = new ArrayList<>();

    public Drawer(String number, ArrayList<Bitmap> clothes, ArrayList<Clothes> clothesArrayList) {
        this.number = number;
        this.clothes = clothes;
        this.clothesArrayList = clothesArrayList;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ArrayList<Bitmap> getClothes() {
        return clothes;
    }

    public void setClothes(ArrayList<Bitmap> clothes) {
        this.clothes = clothes;
    }

    public ArrayList<Clothes> getClothesArrayList() {
        return clothesArrayList;
    }

    public void setClothesArrayList(ArrayList<Clothes> clothesArrayList) {
        this.clothesArrayList = clothesArrayList;
    }
}
