package com.erik.inputdialog;

public class Kontak {
    private String name;
    private String number;

    public Kontak(String name, String number){
        this.name = name;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
