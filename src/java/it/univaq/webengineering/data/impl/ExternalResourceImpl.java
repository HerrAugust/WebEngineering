/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.webengineering.data.impl;

import it.univaq.webengineering.data.model.ExternalResource;
import it.univaq.webengineering.data.model.WebengineeringDataLayer;

/**
 *
 * @author agost
 */
public class ExternalResourceImpl implements ExternalResource {
    private String name, description, weblink;
    private int id;
    private final WebengineeringDataLayer dl;

    public ExternalResourceImpl(WebengineeringDataLayer dl) {
        this.name = "";
        this.description = "";
        this.weblink = "";
        this.id = 0;
        this.dl = dl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }
}
