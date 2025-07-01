package com.praktikum.data;

public class Item {
    private String namaItem;
    private String deskripsiItem;
    private String lokasi;
    private String status;

    public Item(String namaItem, String deskripsiItem, String lokasi){
        this.namaItem = namaItem;
        this.deskripsiItem = deskripsiItem;
        this.lokasi = lokasi;
        this.status = "Reported";
    }

    public String getNamaItem() {
        return namaItem;
    }

    public String getDeskripsiItem(){
        return deskripsiItem;
    }

    public String getLokasi(){
        return lokasi;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}