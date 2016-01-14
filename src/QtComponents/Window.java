package QtComponents;

import Assemble.QT;
import Assemble.Utils;
import StyleComponents.Style;
import com.trolltech.qt.gui.QDesktopWidget;
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
        QDesktopWidget desktop = new QDesktopWidget();
        this.Name = Utils.check("name", nodeMap);
        this.Class = Utils.check("class", nodeMap);
        if (!Name.isEmpty()) {
            this.styles.put(Name, new Style(Name, "QMainWindow", true));
            this.styles.get(Name).addAttribute("max-height", desktop.screenGeometry().height() + "");
            this.styles.get(Name).addAttribute("min-height", "1");
            this.styles.get(Name).addAttribute("max-width", desktop.screenGeometry().width() + "");
            this.styles.get(Name).addAttribute("min-width", "1");
            this.setAccessibleName(Name);
        } else{
            this.styles.put("QMainWindow", new Style("QMainWindow", "QMainWindow", false));
            this.styles.get("QMainWindow").addAttribute("max-height", desktop.screenGeometry().height() + "");
            this.styles.get("QMainWindow").addAttribute("min-height", "1");
            this.styles.get("QMainWindow").addAttribute("max-width", desktop.screenGeometry().width() + "");
            this.styles.get("QMainWindow").addAttribute("min-width", "1");
        }
    }

    @Override
    public String setStyle() {
        String name = (!this.Name.equals("")) ? this.Name : "QMainWindow";
        for(Map.Entry<String, Style> style : QT.styles.entrySet()){
            if (style.getKey().startsWith("QMainWindow")){
                if(style.getKey().equals("QMainWindow")) styles.get(name).addAll(style.getValue());
                else styles.put(style.getKey(), style.getValue());
            }
            if(style.getKey().startsWith(this.Name)&&!this.Name.isEmpty()){
                if(style.getKey().equals(this.Name)) styles.get(name).addAll(style.getValue());
                else styles.put(style.getKey(), style.getValue());
            }
            if(style.getKey().startsWith(this.Class)&&!this.Class.isEmpty()){
                if(style.getKey().equals(this.Class)) styles.get(name).addAll(style.getValue());
                else styles.put(style.getKey(), style.getValue());
            }
        }
        Utils.setStyle(styles.get(name), nodeMap);
        String prop = Utils.check("title", nodeMap);
        this.setWindowTitle(tr((!prop.isEmpty()) ? prop : "JAML Applicaiton"));
        if(styles.size() == 1 && styles.get(name).getAttributes().size() == 0) return "";
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Style> style: styles.entrySet()) sb.append(style.getValue().toString());
        return sb.toString();
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
    public void SetStylesheet(String sheet) {
        this.setStyleSheet(sheet);
    }
}