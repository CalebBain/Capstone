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

    private String Id;
    private String Name;
    private String Class;

    public Button(QWidget parent, Node node) {
        super(parent);
        NamedNodeMap nodeMap = node.getAttributes();
        setIdentity(nodeMap);
        setSize(check(nodeMap, "width"), check(nodeMap, "height"));
        setTextFont(new Font(nodeMap));
        setButtonText(node.getTextContent());
        setMargins(check(nodeMap, "margin"));
        onClick(check(nodeMap, "onclick"));
    }

    public void setIdentity(NamedNodeMap nodeMap){
        this.Id = check(nodeMap, "id");
        this.Name = check(nodeMap, "name");
        this.Class = check(nodeMap, "class");
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
            case  4:
                this.setGeometry(m[0], m[1], this.width(), this.height()); break;
            case  3:
                this.setGeometry(m[0], m[1], this.height(), this.height()); break;
            case  2:
                this.setGeometry(m[0], m[1], this.height(), this.height()); break;
            case  1:
                this.setGeometry(m[0], m[0], this.height(), this.height()); break;
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

    public String Id(){
        return Id;
    }

    public String Name(){
        return Name;
    }

    public String Class(){
        return Class;
    }

    public String Component(){
        return this.getClass().getName();
    }
}
