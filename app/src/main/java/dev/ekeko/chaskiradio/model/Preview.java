package dev.ekeko.chaskiradio.model;

import java.io.Serializable;

public class Preview implements Serializable {
    String id, name, photo, urlStreaming;//si foto.lenght es ==1 entonces hay que asignar un recurso vectorial con un switch,
    //Caso contrario y siempre que sea != "", cargamos con Glide
    String description,date,time; //Fecha y rango de horas del programa
    int counter;//para podcast será la canitdad de minutos de duración, para actividad será la cantidad de panticipantes
    int type;//Para podcast: 0 Programado, 1 especial, para Actividades: 0  votación, 1 form, 2 sorteo
    public Preview(){

    }

    public Preview(String id, String name, String photo, String urlStreaming,String description, String date, String time, int counter, int type) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.urlStreaming = urlStreaming;
        this.description = description;
        this.date = date;
        this.time = time;
        this.counter = counter;
        this.type = type;
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

    public String getUrlStreaming() {
        return urlStreaming;
    }

    public void setUrlStreaming(String urlStreaming) {
        this.urlStreaming = urlStreaming;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
