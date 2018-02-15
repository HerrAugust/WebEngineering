/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.webengineering.data.model;

/**
 *
 * @author agost
 */
public interface ExternalResource {
    public String getName();
    public void setName(String name);
    public String getDescription();
    public void setDescription(String description);
    public String getWeblink();
    public void setWeblink(String weblink);
    public int getId();
    public void setId(int id);
}
