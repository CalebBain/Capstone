package QT.QtComponents;

import Compiler.Utils;
import QT.EventClass.Events;
import Compiler.Parser.Style;
import com.trolltech.qt.gui.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/12/2016.
 */
public final class Checkbox extends QCheckBox{
    private Events events = new Events() {
    };
    private Map<String, Style> styles = new HashMap<>();
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;
    private boolean TriState;

    public Checkbox(Node node, boolean TriState) {
        this.nodeMap = node.getAttributes();
        this.TriState = TriState;
        setIdentity(nodeMap, (TriState)? "QTriState" : "QCheckBox");
    }

    private void setIdentity(NamedNodeMap nodeMap, String TriState) {
        this.Name = Utils.check("name", nodeMap);
        this.Class = Utils.check("class", nodeMap);
        String name = "QCheckBox";
        if (!Name.isEmpty()) {
            this.styles.put(Name, new Style(Name, name, true));
            Utils.setDimensions(styles, Name);
            this.setAccessibleName(Name);
        } else {
            this.styles.put(name, new Style(name, name, false));
            Utils.setDimensions(styles, name);
        }
    }

    public String setStyle() {
        Utils.getStyleSheets("QCheckBox", styles, Name, Class, nodeMap);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Style> style : styles.entrySet()) sb.append(style.getValue().toString());
        return sb.toString();
    }
}
