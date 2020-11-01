package dev.ekeko.chaskiradio.model;

import java.io.Serializable;

public class Nominee implements Serializable {
    String id, name, photo,description;
    int votes;//cantidad de votos
    private boolean isSelected = false;//votado
    public Nominee(){

    }

    public Nominee(String id, String name, String photo, String description, int votes, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.description = description;
        this.votes = votes;
        this.isSelected= isSelected;
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

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        Nominee itemCompare = (Nominee) obj;
        if(itemCompare.getId().equals(this.getId()))
            return true;

        return false;
    }
}
