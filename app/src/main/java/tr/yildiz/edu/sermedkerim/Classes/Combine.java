package tr.yildiz.edu.sermedkerim.Classes;

import android.graphics.Bitmap;

public class Combine {
    Bitmap head;
    Bitmap face;
    Bitmap ustbeden;
    Bitmap altbeden;
    Bitmap ayak;
    String number;

    public Combine(Bitmap head, Bitmap face, Bitmap ustbeden, Bitmap altbeden, Bitmap ayak, String number) {
        this.head = head;
        this.face = face;
        this.ustbeden = ustbeden;
        this.altbeden = altbeden;
        this.ayak = ayak;
        this.number = number;
    }

    public Bitmap getHead() {
        return head;
    }

    public Bitmap getFace() {
        return face;
    }

    public Bitmap getUstbeden() {
        return ustbeden;
    }

    public Bitmap getAltbeden() {
        return altbeden;
    }

    public Bitmap getAyak() {
        return ayak;
    }

    public String getNumber() {
        return number;
    }

    public void setHead(Bitmap head) {
        this.head = head;
    }

    public void setFace(Bitmap face) {
        this.face = face;
    }

    public void setUstbeden(Bitmap ustbeden) {
        this.ustbeden = ustbeden;
    }

    public void setAltbeden(Bitmap altbeden) {
        this.altbeden = altbeden;
    }

    public void setAyak(Bitmap ayak) {
        this.ayak = ayak;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
