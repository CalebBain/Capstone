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
public final class Radio extends QRadioButton implements Component {
    private Events events = new Events() {
    };
    private Map<String, Style> styles = new HashMap<>();
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Radio(Node node) {
        super(node.getTextContent());
        this.nodeMap = node.getAttributes();
        setIdentity(nodeMap);
    }

    private void setIdentity(NamedNodeMap nodeMap) {
        this.Name = Utils.check("name", nodeMap);
        this.Class = Utils.check("class", nodeMap);
        QDesktopWidget desktop = new QDesktopWidget();
        String name = "QRadioButton";
        if (!Name.isEmpty()) {
            this.styles.put(Name, new Style(Name, name, true));
            this.styles.get(Name).addAttribute("max-height", desktop.screenGeometry().height() + "");
            this.styles.get(Name).addAttribute("min-height", "1");
            this.styles.get(Name).addAttribute("max-width", desktop.screenGeometry().width() + "");
            this.styles.get(Name).addAttribute("min-width", "1");
            this.setAccessibleName(Name);
        } else {
            this.styles.put(name, new Style(name, name, false));
            this.styles.get(name).addAttribute("max-height", desktop.screenGeometry().height() + "");
            this.styles.get(name).addAttribute("min-height", "1");
            this.styles.get(name).addAttribute("max-width", desktop.screenGeometry().width() + "");
            this.styles.get(name).addAttribute("min-width", "1");
        }
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
        if (Utils.tryValue((count = Utils.check("repeatable-delay", nodeMap))))
            this.setAutoRepeatDelay(Integer.parseInt(count));
        if (Utils.tryValue((count = Utils.check("repeatable-interval", nodeMap))))
            this.setAutoRepeatInterval(Integer.parseInt(count));
        onFunction();
    }

    private String[] Func(String prop) {
        String call;
        String[] calls = new String[0];
        if (!(call = Utils.check(prop, nodeMap)).isEmpty()) calls = call.split(":");
        return calls;
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

    public String setStyle() {
        String component = "QRadioButton";
        String name = (!this.Name.equals("")) ? this.Name : component;
        for (Map.Entry<String, Style> style : QT.styles.entrySet()) {
            if (style.getKey().startsWith(component)) {
                if (style.getKey().equals(component)) styles.get(name).addAll(style.getValue());
                else {
                    style.getValue().setComponent(component);
                    styles.put(style.getKey(), style.getValue());
                }
            }
            if (style.getKey().startsWith(this.Name) && !this.Name.isEmpty()) {
                if (style.getKey().equals(this.Name)) styles.get(name).addAll(style.getValue());
                else {
                    style.getValue().setComponent(component);
                    styles.put(style.getKey(), style.getValue());
                }
            }
            if (style.getKey().startsWith(this.Class) && !this.Class.isEmpty()) {
                if (style.getKey().equals(this.Class)) styles.get(name).addAll(style.getValue());
                else {
                    style.getValue().setComponent(component);
                    styles.put(style.getKey(), style.getValue());
                }
            }
        }
        Utils.setStyle(styles.get(name), nodeMap);
        setProps();
        StringBuilder sb = new StringBuilder();
        if (styles.size() == 1 && styles.get(name).getAttributes().size() == 0) return "";
        for (Map.Entry<String, Style> style : styles.entrySet()) sb.append(style.getValue().toString());
        return sb.toString();
    }

    public String Name() {
        return Name;
    }

    public String Class() {
        return Class;
    }

    public String Component() {
        return "radio";
    }

    public QRadioButton Widgit() {
        return this;
    }

    public void SetStylesheet(String sheet) {
        this.setStyleSheet(sheet);
    }

    public void addChild(Component child, Node node) {

    }

    public void actionEvent(QActionEvent event) {
        super.actionEvent(event);
        events.actionEvent(this, event);
    }

    public void changeEvent(QEvent event) {
        super.changeEvent(event);
        events.changeEvent(this, event);
    }

    public void closeEvent(QCloseEvent event) {
        super.closeEvent(event);
        events.closeEvent(this, event);
    }

    public void dragEnterEvent(QDragEnterEvent event) {
        super.dragEnterEvent(event);
        events.dragEnterEvent(this, event);
    }

    public void dragLeaveEvent(QDragLeaveEvent event) {
        super.dragLeaveEvent(event);
        events.dragLeaveEvent(this, event);
    }

    public void dragMoveEvent(QDragMoveEvent event) {
        super.dragMoveEvent(event);
        events.dragMoveEvent(this, event);
    }

    public void dropEvent(QDropEvent event) {
        super.dropEvent(event);
        events.dropEvent(this, event);
    }

    public void enterEvent(QEvent event) {
        super.enterEvent(event);
        events.enterEvent(this, event);
    }

    public void focusInEvent(QFocusEvent event) {
        super.focusInEvent(event);
        events.focusInEvent(this, event);
    }

    public void focusOutEvent(QFocusEvent event) {
        super.focusOutEvent(event);
        events.focusOutEvent(this, event);
    }

    public void hideEvent(QHideEvent event) {
        super.hideEvent(event);
        events.hideEvent(this, event);
    }

    public void inputMethodEvent(QInputMethodEvent event) {
        super.inputMethodEvent(event);
        events.inputMethodEvent(this, event);
    }

    public void keyPressEvent(QKeyEvent event) {
        super.keyPressEvent(event);
        events.keyPressEvent(this, event);
    }

    public void keyReleaseEvent(QKeyEvent event) {
        super.keyReleaseEvent(event);
        events.keyReleaseEvent(this, event);
    }

    public void leaveEvent(QEvent event) {
        super.leaveEvent(event);
        events.leaveEvent(this, event);
    }

    public void mouseDoubleClickEvent(QMouseEvent event) {
        super.mouseDoubleClickEvent(event);
        events.mouseDoubleClickEvent(this, event);
    }

    public void mouseMoveEvent(QMouseEvent event) {
        super.mouseMoveEvent(event);
        events.mouseMoveEvent(this, event);
    }

    public void mouseReleaseEvent(QMouseEvent event) {
        super.mouseReleaseEvent(event);
        events.mouseReleaseEvent(this, event);
    }

    public void moveEvent(QMoveEvent event) {
        super.moveEvent(event);
        events.moveEvent(this, event);
    }

    public void paintEvent(QPaintEvent event) {
        super.paintEvent(event);
        events.paintEvent(this, event);
    }

    public void resizeEvent(QResizeEvent event) {
        super.resizeEvent(event);
        events.resizeEvent(this, event);
    }

    public void tabletEvent(QTabletEvent event) {
        super.tabletEvent(event);
        events.tabletEvent(this, event);
    }

    public void wheelEvent(QWheelEvent event) {
        super.wheelEvent(event);
        events.wheelEvent(this, event);
    }

    public void childEvent(QChildEvent event) {
        super.childEvent(event);
        events.childEvent(this, event);
    }

    public void timerEvent(QTimerEvent event) {
        super.timerEvent(event);
        events.timerEvent(this, event);
    }

    public void showEvent(QShowEvent event) {
        super.showEvent(event);
        events.showEvent(this, event);
    }

    public void contextMenuEvent(QContextMenuEvent event) {
        super.contextMenuEvent(event);
        events.contextMenuEvent(this, event);
    }

    public void mousePressEvent(QMouseEvent event) {
        super.mousePressEvent(event);
        events.mousePressEvent(this, event);
    }
}
