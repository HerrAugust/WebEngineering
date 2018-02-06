package it.univaq.webengineering.data.model;

import it.univaq.webengineering.framework.data.DataLayerException;
import java.io.InputStream;

/**
 *
 * @author Giuseppe Della Penna
 */
public interface Image {

    int getKey();

    String getCaption();

    void setCaption(String caption);

    InputStream getImageData() throws DataLayerException;

    void setImageData(InputStream is) throws DataLayerException;

    String getImageType();

    void setImageType(String type);

    long getImageSize();

    public String getFilename();

    public void setFilename(String imageFilename);

    void setDirty(boolean dirty);

    boolean isDirty();
}
