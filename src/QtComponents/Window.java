package QtComponents;

import Assemble.QT;
import Assemble.Utils;
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

    private void setIdentity(NamedNodeMap nodeMap) {
        this.Name = Utils.check("name", nodeMap);
        this.Class = Utils.check("class", nodeMap);
        if (!Name.isEmpty()) {
            this.style = new Style(Name, "QMainWindow", true);
            this.setAccessibleName(Name);
        } else this.style = new Style("QMainWindow", "QMainWindow", false);
    }

    @Override
    public void setStyle() {
        if (QT.styles.containsKey(Component())) style.addAll(QT.styles.get("QMainWindow"));
        if (QT.styles.containsKey(Class)) style.addAll(QT.styles.get(Class));
        if (QT.styles.containsKey(Name)) style.addAll(QT.styles.remove(Name));
        Utils.setStyle(style, nodeMap);
        String prop = Utils.check("title", nodeMap);
        this.setWindowTitle(tr((!prop.isEmpty()) ? prop : "JAML Applicaiton"));
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
        this.setStyleSheet("");
    }
}