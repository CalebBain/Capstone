package QtComponents;

import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class Window extends QWidget {

    public Window(Node node) {
        NamedNodeMap nodeMap = node.getAttributes();
        setSize(check(nodeMap, "width"), check(nodeMap, "height"));
        setTitle(check(nodeMap, "title"));
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

    public void setTitle(String title){
        this.setWindowTitle(tr((!title.isEmpty()) ? title : "JAML Applicaiton"));
    }
}
