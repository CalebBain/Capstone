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
        this.resize(((!width.isEmpty())? Integer.parseInt(width) : 400), ((!height.isEmpty())? Integer.parseInt(height) : 200));
    }

    public void setSize(int width, int height) {
        this.resize(width, height);
    }

    public void setTitle(String title){
        this.setWindowTitle(tr((!title.isEmpty()) ? title : "JAML Applicaiton"));
    }
}
