package QtComponents;

import Assemble.QT;
import Assemble.Utils;
import StyleComponents.Style;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/12/2016.
 */
public class Checkbox extends QCheckBox implements Component {

    private Map<String, Style> styles = new HashMap<>();
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Checkbox(QWidget parent, Node node, boolean TriState) {
        super(parent);
        this.setTristate(TriState);
        this.nodeMap = node.getAttributes();
        setIdentity(nodeMap);
    }

    private void setIdentity(NamedNodeMap nodeMap) {
        this.Name = Utils.check("name", nodeMap);
        this.Class = Utils.check("class", nodeMap);
        if (!Name.isEmpty()) {
            this.styles.put(Name, new Style(Name, "QCheckBox", true));
            this.setAccessibleName(Name);
        } else this.styles.put("QCheckBox", new Style("QCheckBox", "QCheckBox", true));
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
    public void setStyle() {
        String name = (!this.Name.equals("")) ? this.Name : "QCheckBox";
        for(Map.Entry<String, Style> style : QT.styles.entrySet()){
            if (style.getKey().startsWith("QCheckBox")){
                if(style.getKey().equals("QCheckBox")) styles.get(name).addAll(style.getValue());
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
        setProps();
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
        return "Checkbox";
    }

    @Override
    public QCheckBox Widgit() {
        return this;
    }


    @Override
    public void SetStylesheet() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Style> style: styles.entrySet()) sb.append(style.toString());
        this.setStyleSheet(sb.toString());
    }
}
