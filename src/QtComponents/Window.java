package QtComponents;

import com.trolltech.qt.gui.QWidget;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class Window extends QWidget {

    public void setHeight(String height) {
        this.setHeight(height);
    }

    public void setWidth(String width) {
        this.setWidth(width);
    }

    public void setSize(String width, String height) {
        try{
            this.resize(Integer.parseInt(width), Integer.parseInt(height));
        }catch (NumberFormatException nfe){}
    }

    public void setSize(int width, int height) {
        this.resize(width, height);
    }

    public void setTitle(String title){
        this.setWindowTitle(tr((!title.isEmpty()) ? title : "JAML Applicaiton"));
    }
}
