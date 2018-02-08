package it.univaq.webengineering.data.model;

import it.univaq.webengineering.framework.data.DataLayerException;
import java.io.InputStream;

/**
 *
 * @author Giuseppe Della Penna
 */
public interface Book {
    public int getId();
    public String getAuthor();
    public String getTitle();
    public String getVolume();
    public String getPublisher();
    public String getWeblink();
    public int getYear();
    
    public void setId(int i);
    public void setAuthor(String i);
    public void setTitle(String i);
    public void setVolume(String i);
    public void setPublisher(String i);
    public void setWeblink(String i);
    public void setYear(int i);
}
