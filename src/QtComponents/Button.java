package QtComponents;

import Assemble.QT;
import Assemble.Utils;
import StyleComponents.Style;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class Button extends QPushButton implements Component {

    private Map<String, Style> styles = new HashMap<>();
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Button(QWidget parent, Node node) {
        super(node.getTextContent(), parent);
        this.nodeMap = node.getAttributes();
        setIdentity(nodeMap);
    }

    private void setIdentity(NamedNodeMap nodeMap) {
        this.Name = Utils.check("name", nodeMap);
        this.Class = Utils.check("class", nodeMap);
        if (!Name.isEmpty()) {
            this.styles.put(Name, new Style(Name, "QPushButton", true));
            this.setAccessibleName(Name);
        } else this.styles.put("QPushButton", new Style("QPushButton", "QPushButton", false));
    }



    private void setProps() {
        if (Utils.check("exclusive", nodeMap).equals("true")) this.setAutoExclusive(true);
        else if (Utils.check("exclusive", nodeMap).equals("false")) this.setAutoExclusive(false);
        if (Utils.check("repeatable", nodeMap).equals("true")) this.setAutoRepeat(true);
        else if (Utils.check("repeatable", nodeMap).equals("false")) this.setAutoRepeat(false);
        if (Utils.check("checkable", nodeMap).equals("true")) this.setCheckable(true);
        else if (Utils.check("checkable", nodeMap).equals("false")) this.setCheckable(false);
        if (Utils.check("checked", nodeMap).equals("true")) this.setChecked(true);
        else if (Utils.check("checked", nodeMap).equals("false")) this.setChecked(false);
        if (Utils.check("default", nodeMap).equals("true")) this.setDefault(true);
        else if (Utils.check("default", nodeMap).equals("false")) this.setDefault(false);
        if (Utils.check("flat", nodeMap).equals("true")) this.setFlat(true);
        else if (Utils.check("flat", nodeMap).equals("false")) this.setFlat(false);
        String count;
        if (Utils.tryValue((count = Utils.check("repeatable-delay", nodeMap)))) this.setAutoRepeatDelay(Integer.parseInt(count));
        if (Utils.tryValue((count = Utils.check("repeatable-interval", nodeMap)))) this.setAutoRepeatInterval(Integer.parseInt(count));
        onFunction();
    }

    private String[] Func(String prop){
        String call;
        if (!(call = Utils.check(prop, nodeMap)).isEmpty()) return call.split(":");
        return new String[0];
    }

    private void onFunction() {
        String[] callParts = Func("on-click");
        if (callParts.length == 1) this.clicked.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.clicked.connect(QT.findComponent(callParts[0]), callParts[1]);
        callParts = Func("on-release");
        if (callParts.length == 1) this.released.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.released.connect(QT.findComponent(callParts[0]), callParts[1]);
        callParts = Func("on-press");
        if (callParts.length == 1) this.pressed.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.pressed.connect(QT.findComponent(callParts[0]), callParts[1]);
    }

    @Override
    public String setStyle() {
        String name = (!this.Name.equals("")) ? this.Name : "QPushButton";
        for(Map.Entry<String, Style> style : QT.styles.entrySet()){
            if (style.getKey().startsWith("QPushButton")){
                if(style.getKey().equals("QPushButton")) styles.get(name).addAll(style.getValue());
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
        setProps();
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
        return "button";
    }

    @Override
    public QPushButton Widgit() {
        return this;
    }

    @Override
    public void SetStylesheet(String sheet) {
        this.setStyleSheet(sheet);
    }
}
