package tr.yildiz.edu.sermedkerim;

import java.util.ArrayList;

public class Etkinlik {

    String name;
    String type;
    String date;
    String location;
    ArrayList<Clothes> clothes;

    public static ArrayList<Etkinlik> etkinlikler = new ArrayList<>();

    public Etkinlik(String name, String type, String date, String location, ArrayList<Clothes> clothes) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.location = location;
        this.clothes = clothes;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<Clothes> getClothes() {
        return clothes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setClothes(ArrayList<Clothes> clothes) {
        this.clothes = clothes;
    }
}
