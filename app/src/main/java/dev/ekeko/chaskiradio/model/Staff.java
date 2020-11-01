package dev.ekeko.chaskiradio.model;

import java.io.Serializable;

public class Staff implements Serializable {
    String id, name, photo,description;
    String position;//Conductor, reportero, invitado, etc
    public Staff(){

    }

    public Staff(String id, String name, String photo, String description, String position) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.description = description;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
