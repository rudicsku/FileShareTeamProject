package com.codecool.fileshare.model;

import java.io.File;

public class Image {
    String uuid; //todo Maybe change to an another data format
    String base64Code; //todo Maybe change to an another data format
    File imageFile; //todo Maybe change to Path
    String category;
    String extension;

    //CONSTRUCTOR FOR DATABASE

    public Image(String uuid, String base64Code, String category, String extension) {
        this.uuid = uuid;
        this.base64Code = base64Code;
        this.category = category;
        this.extension = extension;
    }

    //ALL
    public Image(String uuid, String base64Code, File imageFile, String category, String extension) {
        this.uuid = uuid;
        this.base64Code = base64Code;
        this.imageFile = imageFile;
        this.category = category;
        this.extension = extension;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public String getBase64Code() {
        return base64Code;
    }

    public void setBase64Code(String base64Code) {
        this.base64Code = base64Code;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImagefile(File imagefile) {
        this.imageFile = imagefile;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "Image{" +
                "uuid='" + uuid + '\'' +
                ", base64Code='" + base64Code + '\'' +
                ", imageFile=" + imageFile +
                ", category='" + category + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
