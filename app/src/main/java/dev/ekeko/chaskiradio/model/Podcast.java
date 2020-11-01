package dev.ekeko.chaskiradio.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Podcast extends Preview implements Serializable {
    ArrayList<Staff> staff=new ArrayList<>();
    ArrayList<String> photos=new ArrayList<>();
    ArrayList<Preview> activities=new ArrayList<>();
    //Faltan 2 ArrayList: 1 de comentario y 1 de Actividades Ä
    public Podcast(){

    }

    public Podcast(ArrayList<Staff> staff, ArrayList<String> photos,ArrayList<Preview> activities) {
        this.staff = staff;
        this.photos = photos;
        this.activities=activities;
    }

    public Podcast(String id, String name, String photo, String urlStreaming, String description, String date, String time, int counter, int type, ArrayList<Staff> staff, ArrayList<String> photos,ArrayList<Preview> activities) {
        super(id, name, photo, urlStreaming, description, date, time, counter, type);
        this.staff = staff;
        this.photos = photos;
        this.activities = activities;
    }

    public ArrayList<Staff> getStaff() {
        return staff;
    }

    public void setStaff(ArrayList<Staff> staff) {
        this.staff = staff;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public ArrayList<Preview> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Preview> activities) {
        this.activities = activities;
    }
}
