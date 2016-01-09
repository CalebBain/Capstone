package QtComponents;

import com.trolltech.qt.gui.QMainWindow;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class Window extends QMainWindow implements Component {

    private String Id;
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Window(Node node) {
        this.nodeMap = node.getAttributes();
        setIdentity(nodeMap);
    }

    public String check(NamedNodeMap nodeMap, String keyword) {
        Node word = nodeMap.getNamedItem(keyword);
        return (word != null) ? word.getNodeValue() : "";
    }

    public void setHeight(String height) {
        try {
            this.resize(width(), Integer.parseInt(height));
        } catch (NumberFormatException nfe) {
        }
    }

    public void setWidth(String width) {
        try {
            this.resize(Integer.parseInt(width), height());
        } catch (NumberFormatException nfe) {
        }
    }

    public void setSize(String width, String height) {
        if (width.isEmpty()) setHeight(height);
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

    public void setTitle(String title) {
        this.setWindowTitle(tr((!title.isEmpty()) ? title : "JAML Applicaiton"));
    }

    public void setIdentity(NamedNodeMap nodeMap) {
        this.Id = check(nodeMap, "id");
        this.Name = check(nodeMap, "name");
        this.Class = check(nodeMap, "class");
    }

    @Override
    public String Id() {
        return Id;
    }

    @Override
    public String Name() {
        return Name;
    }

    @Override
    public String Class() {
        return Class;
    }

    @Override
    public String Component() {
        return this.getClass().getName();
    }

    @Override
    public void setStyle() {
        setSize(check(nodeMap, "width"), check(nodeMap, "height"));
        setTitle(check(nodeMap, "title"));
    }

    @Override
    public void SetStylesheet() {
        this.setStyleSheet("");
    }
}
