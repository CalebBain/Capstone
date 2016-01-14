package QtComponents;

import Assemble.QT;
import Assemble.Utils;
import StyleComponents.Style;
import com.trolltech.qt.gui.QMainWindow;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class Window extends QMainWindow implements Component {

    private Map<String, Style> styles = new HashMap<>();
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Window(Node node) {
        this.nodeMap = node.getAttributes();
        setIdentity(nodeMap);
    }

    private void setIdentity(NamedNodeMap nodeMap) {
        this.Name = Utils.check("name", nodeMap);
        this.Class = Utils.check("class", nodeMap);
        if (!Name.isEmpty()) {
            this.styles.put(Name, new Style(Name, "QMainWindow", true));
            this.setAccessibleName(Name);
        } else this.styles.put("QMainWindow", new Style("QMainWindow", "QMainWindow", true));
    }

    @Override
    public void setStyle() {
        String name = (!this.Name.equals("")) ? this.Name : "QMainWindow";
        for(Map.Entry<String, Style> style : QT.styles.entrySet()){
            if (style.getKey().startsWith("QMainWindow")){
                if(style.getKey().equals("QMainWindow")) styles.get(name).addAll(style.getValue());
                else styles.put(style.getKey(), style.getValue());
            }
            if(style.getKey().startsWith(this.Name)){
                if(style.getKey().equals(this.Name)) styles.get(name).addAll(style.getValue());
                else styles.put(style.getKey(), style.getValue());
            }
            if(style.getKey().startsWith(this.Class)){
                if(style.getKey().equals(this.Class)) styles.get(name).addAll(style.getValue());
                else styles.put(style.getKey(), style.getValue());
            }
        }
        Utils.setStyle(styles.get(name), nodeMap);
        String prop = Utils.check("title", nodeMap);
        this.setWindowTitle(tr((!prop.isEmpty()) ? prop : "JAML Applicaiton"));
        SetStylesheet();
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
        return "window";
    }

    @Override
    public QMainWindow Widgit() {
        return this;
    }

    @Override
    public void SetStylesheet() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Style> style: styles.entrySet()) sb.append(style.toString());
        this.setStyleSheet(sb.toString());
    }
}