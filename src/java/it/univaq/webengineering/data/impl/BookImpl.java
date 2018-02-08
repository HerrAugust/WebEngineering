package it.univaq.webengineering.data.impl;

import it.univaq.webengineering.data.model.Book;
import it.univaq.webengineering.data.model.Image;
import it.univaq.webengineering.data.model.Teacher;
import it.univaq.webengineering.framework.data.DataLayerException;
import it.univaq.webengineering.data.model.WebengineeringDataLayer;
import java.util.List;

public class BookImpl implements Book {

    private int id, year;
    private String title, author, volume, publisher, weblink;
    protected WebengineeringDataLayer dl;

    public BookImpl(WebengineeringDataLayer dl) {
        this.dl = dl;
        this.title = "";
        this.author = "";
        this.volume = "";
        this.publisher = "";
        this.weblink = "";
        this.year = 0;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getAuthor() {
        return this.author;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getVolume() {
        return this.volume;
    }

    @Override
    public String getPublisher() {
        return this.publisher;
    }

    @Override
    public String getWeblink() {
        return this.weblink;
    }

    @Override
    public int getYear() {
        return this.year;
    }

    @Override
    public void setId(int i) {
        this.id = i;
    }

    @Override
    public void setAuthor(String i) {
        this.author = i;
    }

    @Override
    public void setTitle(String i) {
        this.title = i;
    }

    @Override
    public void setVolume(String i) {
        this.volume = i;
    }

    @Override
    public void setPublisher(String i) {
        this.publisher = i;
    }

    @Override
    public void setWeblink(String i) {
        this.weblink = i;
    }

    @Override
    public void setYear(int i) {
        this.year = i;
    }

}
