package it.univaq.webengineering.data.model;

import it.univaq.webengineering.framework.data.DataLayerException;
import java.io.InputStream;

/**
 *
 * @author Giuseppe Della Penna
 */
public interface Image {

    void setId(int o);
    void setOriginal_name(String o);
    void setName_on_disk(String o);
    void setPath(String o);
    void setCourse_id(int o);
    
    int getId();
    String getOriginal_name();
    String getName_on_disk();
    String getPath();
    int getCourse_id();
}
