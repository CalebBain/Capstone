package QtComponents;

import Assemble.QT;
import Assemble.Utils;
import EventClass.Events;
import StyleComponents.Style;
import com.trolltech.qt.core.QChildEvent;
import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QTimerEvent;
import com.trolltech.qt.gui.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/12/2016.
 */
public final class Checkbox extends QCheckBox implements Component {
    private Events events = new Events() {};
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
        } else this.styles.put("QCheckBox", new Style("QCheckBox", "QCheckBox", false));
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
    public String setStyle() {
        String component = "QCheckBox";
        String name = (!this.Name.equals("")) ? this.Name : component;
        for(Map.Entry<String, Style> style : QT.styles.entrySet()){
            if (style.getKey().startsWith(component)){
                if(style.getKey().equals(component)) styles.get(name).addAll(style.getValue());
                else {
                    style.getValue().setComponent(component);
                    styles.put(style.getKey(), style.getValue());
                }
            }
            if(style.getKey().startsWith(this.Name)&&!this.Name.isEmpty()){
                if(style.getKey().equals(this.Name)) styles.get(name).addAll(style.getValue());
                else {
                    style.getValue().setComponent(component);
                    styles.put(style.getKey(), style.getValue());
                }
            }
            if(style.getKey().startsWith(this.Class)&&!this.Class.isEmpty()){
                if(style.getKey().equals(this.Class)) styles.get(name).addAll(style.getValue());
                else {
                    style.getValue().setComponent(component);
                    styles.put(style.getKey(), style.getValue());
                }
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
        return "Checkbox";
    }

    @Override
    public QCheckBox Widgit() {
        return this;
    }

    @Override
    public void SetStylesheet(String sheet) {
        this.setStyleSheet(sheet);
    }

    @Override
    public void addChild(QObject child, Node node) {

    }

    public void actionEvent(QActionEvent event) { events.actionEvent(this, event); }
    public void changeEvent(QEvent event) { events.changeEvent(this, event); }
    public void closeEvent(QCloseEvent event) { events.closeEvent(this, event); }
    public void dragEnterEvent(QDragEnterEvent event) { events.dragEnterEvent(this, event); }
    public void dragLeaveEvent(QDragLeaveEvent event) { events.dragLeaveEvent(this, event); }
    public void dragMoveEvent(QDragMoveEvent event) { events.dragMoveEvent(this, event); }
    public void dropEvent(QDropEvent event) { events.dropEvent(this, event); }
    public void enterEvent(QEvent event) { events.enterEvent(this, event); }
    public void focusInEvent(QFocusEvent event) { events.focusInEvent(this, event); }
    public void focusOutEvent(QFocusEvent event) { events.focusOutEvent(this, event); }
    public void hideEvent(QHideEvent event) { events.hideEvent(this, event); }
    public void inputMethodEvent(QInputMethodEvent event) { events.inputMethodEvent(this, event); }
    public void keyPressEvent(QKeyEvent event) { events.keyPressEvent(this, event); }
    public void keyReleaseEvent(QKeyEvent event) { events.keyReleaseEvent(this, event); }
    public void leaveEvent(QEvent event) { events.leaveEvent(this, event); }
    public void mouseDoubleClickEvent(QMouseEvent event) { events.mouseDoubleClickEvent(this, event); }
    public void mouseMoveEvent(QMouseEvent event) { events.mouseMoveEvent(this, event); }
    public void mouseReleaseEvent(QMouseEvent event) { events.mouseReleaseEvent(this, event); }
    public void moveEvent(QMoveEvent event) { events.moveEvent(this, event); }
    public void paintEvent(QPaintEvent event) { events.paintEvent(this, event); }
    public void resizeEvent(QResizeEvent event) { events.resizeEvent(this, event); }
    public void tabletEvent(QTabletEvent event) { events.tabletEvent(this, event); }
    public void wheelEvent(QWheelEvent event) { events.wheelEvent(this, event); }
    public void childEvent(QChildEvent event) { events.childEvent(this, event); }
    public void timerEvent(QTimerEvent event) { events.timerEvent(this, event); }
    public void showEvent(QShowEvent event){ events.showEvent(this, event); }
    public void contextMenuEvent(QContextMenuEvent event){ events.contextMenuEvent(this, event); }
    public void mousePressEvent(QMouseEvent event) { events.mousePressEvent(this, event); }
}
