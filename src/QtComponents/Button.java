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

    public Button(QWidget parent, Node node) {
        super(parent);
        NamedNodeMap nodeMap = node.getAttributes();
        setSize(check(nodeMap, "width"), check(nodeMap, "height"));
        setTextFont(new Font(nodeMap));
        setButtonText(node.getTextContent());
        setMargins(check(nodeMap, "margin"));
        onClick(check(nodeMap, "onclick"));
    }

    public String check(NamedNodeMap nodeMap, String keyword){
        Node word = nodeMap.getNamedItem(keyword);
        return (word != null) ? word.getNodeValue() : "";
    }

    public void setHeight(String height) {
        try {
            this.resize(width(), Integer.parseInt(height));
        }catch (NumberFormatException nfe) {
        }
    }

    public void setWidth(String width) {
        try {
            this.resize(Integer.parseInt(width), height());
        }catch (NumberFormatException nfe) {
        }
    }

    public void setSize(String width, String height) {
        if(width.isEmpty()) setHeight(height);
        else if (height.isEmpty()) setWidth(width);
        else {
            try {
                this.resize(Integer.parseInt(width), Integer.parseInt(height));
            } catch (NumberFormatException nfe) {
            }
        }
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
        String[] Margins = new String[0];
        if(!margins.isEmpty()) Margins = margins.split(" ");
        int l = Margins.length;
        int[] m = new int[l];
        for(int i = 0; i < l; i++) m[i] = Integer.parseInt(Margins[i]);
        switch(l){
            case  4: this.setGeometry(m[0], m[1], m[2], m[3]); break;
            case  3: this.setGeometry(m[0], m[1], m[2], m[1]); break;
            case  2: this.setGeometry(m[0], m[1], m[0], m[1]); break;
            case  1: this.setGeometry(m[0], m[0], m[0], m[0]); break;
            default:
        }
    }

    public void onClick(String call) {
        if(!call.equals("")){
            String[] callParts = call.split(":");
            if(callParts.length == 1) this.clicked.connect(QApplication.instance(), callParts[0]);
            else this.clicked.connect(QT.findComponent(callParts[0]), callParts[1]);
        }

    }


}
