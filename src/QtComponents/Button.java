package QtComponents;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class Button extends QPushButton {
    private final String name;
    private final String id;

    public Button(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Button(QWidget parent, String name, String id) {
        super(parent);
        this.name = name;
        this.id = id;
    }

    public void setHeight(String height) {
        this.setHeight(height);
    }

    public void setWidth(String width) {
        this.setWidth(width);
    }

    public void setSize(String width, String height) {
        this.resize(((!width.isEmpty())? Integer.parseInt(width) : 20), ((!height.isEmpty())? Integer.parseInt(height) : 10));
    }

    public void setSize(int width, int height) {
        this.resize(width, height);
    }

    public void setTextFont(QFont font) {
        this.setFont(font);
    }

    public void setButtonText(String text) {
        this.setText(tr(text));
    }

    public void setMargins(String margins) {
        String[] Margins = margins.split(" ");
        int l = Margins.length;
        int[] m = new int[l];
        for(int i = 0; i < l; i++) m[i] = Integer.parseInt(Margins[i]);
        switch(l){
            case  4: this.setGeometry(m[0], m[1], m[2], m[3]); break;
            case  3: this.setGeometry(m[0], m[1], m[1], m[2]); break;
            case  2: this.setGeometry(m[0], m[1], m[0], m[1]); break;
            default: this.setGeometry(m[0], m[0], m[0], m[0]);
        }
    }

    public void onClick(QWidget window) {
        this.clicked.connect(window, "");
    }
}
