package com.android.colorlist;

public class Color {

    private String colorCode;
    private String colorName;

    public Color(String colorCode,String colorName){
        this.colorCode = colorCode;
        this.colorName = colorName;
    }

    public String getColorCode(){
        return colorCode;
    }

    public String getColorName(){
        return colorName;
    }
}
