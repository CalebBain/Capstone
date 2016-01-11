package QtComponents;

import StyleComponents.Style;
import com.trolltech.qt.gui.QMainWindow;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class Window extends QMainWindow implements Component {

    private Style style;
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Window(Node node) {
        this.nodeMap = node.getAttributes();
        setIdentity(nodeMap);
    }

    public void setIdentity(NamedNodeMap nodeMap) {
        this.Name = check(nodeMap, "name");
        this.Class = check(nodeMap, "class");
        if(!Name.isEmpty()){
            this.style = new Style(Name);
            this.setAccessibleName(Name);
        }else
            this.style = new Style("number");
    }

    public String check(NamedNodeMap nodeMap, String keyword) {
        try {
            Node word = nodeMap.getNamedItem(keyword);
            return (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException e) {
            return "";
        }
    }

    public void setSize(String width, String height) {
        if (!height.isEmpty())     style.addAttrabute("height", height);
        else if (!width.isEmpty()) style.addAttrabute("width", width);
        else if (!height.isEmpty()&&!width.isEmpty()) {
            style.addAttrabute("height", height);
            style.addAttrabute("width", width);
        }
    }

    public void setAltBackground(String color){
        style.addAttrabute("alternate-background-color", color);
    }

    public void setTitle(String title) {
        this.setWindowTitle(tr((!title.isEmpty()) ? title : "JAML Applicaiton"));
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
    public QMainWindow Widgit() {
        return this;
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
