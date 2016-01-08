package QtComponents;

import Assemble.QT;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class Button extends QPushButton {

    public Button(QWidget parent) {
        super(parent);
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
            case  3: this.setGeometry(m[0], m[1], m[2], m[1]); break;
            case  2: this.setGeometry(m[0], m[1], m[0], m[1]); break;
            default: this.setGeometry(m[0], m[0], m[0], m[0]);
        }
    }

    public void onClick(String call) {
        String[] callParts = call.split(":");
        if(callParts.length == 1) this.clicked.connect(QApplication.instance(), callParts[0]);
        else this.clicked.connect(findComponent(callParts[0]), callParts[1]);
    }

    public QWidget findComponent(String component){
        if(QT.ids.containsKey(component)) return QT.ids.get(component);
        if(QT.names.containsKey(component)) return QT.names.get(component);
        if(QT.components.containsKey(component)) return QT.components.get(component);
        return QT.components.get("window");
    }
}
