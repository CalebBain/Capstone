package QtComponents;

import Assemble.QT;
import Assemble.Utils;
import EventClass.Events;
import StyleComponents.Style;
import com.trolltech.qt.core.QChildEvent;
import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.core.QTimerEvent;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public final class Window extends QMainWindow implements Component {
    private Events events = new Events() {};
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

    private void setProps() {
        String prop;
        if (Utils.check("dock-animation", nodeMap).equals("true")) this.setAnimated(true);
        else if (Utils.check("dock-animation", nodeMap).equals("false")) this.setAnimated(false);
        if (Utils.check("dock-nesting", nodeMap).equals("true")) this.setDockNestingEnabled(true);
        else if (Utils.check("dock-nesting", nodeMap).equals("false")) this.setDockNestingEnabled(false);
        switch(Utils.check("dock-option", nodeMap)){
            case "animated-docks": this.setDockOptions(DockOption.AnimatedDocks); break;
            case "allow-nested-docks": this.setDockOptions(DockOption.AllowNestedDocks); break;
            case "allow-tabbed-docks": this.setDockOptions(DockOption.AllowTabbedDocks); break;
            case "force-tabbed-docks": this.setDockOptions(DockOption.ForceTabbedDocks); break;
            case "vertical-tabs": this.setDockOptions(DockOption.VerticalTabs); break;
        }
        if (Utils.check("document-mode", nodeMap).equals("true")) this.setDocumentMode(true);
        else if (Utils.check("document-mode", nodeMap).equals("false")) this.setDocumentMode(false);
        if (Utils.check("tab-shape", nodeMap).equals("rounded")) this.setTabShape(QTabWidget.TabShape.Rounded);
        if (Utils.check("tab-shape", nodeMap).equals("triangular")) this.setTabShape(QTabWidget.TabShape.Triangular);
        if (Utils.tryValue(prop = Utils.check("tab-shape", nodeMap))) this.setTabShape(QTabWidget.TabShape.resolve(Integer.parseInt(prop)));
        switch(Utils.check("tool-button-style", nodeMap)){
            case "icon-only": this.setToolButtonStyle(Qt.ToolButtonStyle.ToolButtonIconOnly); break;
            case "text-only": this.setToolButtonStyle(Qt.ToolButtonStyle.ToolButtonTextOnly); break;
            case "text-beside-icon": this.setToolButtonStyle(Qt.ToolButtonStyle.ToolButtonTextBesideIcon); break;
            case "text-under-icon": this.setToolButtonStyle(Qt.ToolButtonStyle.ToolButtonTextUnderIcon); break;
            case "follow-style": this.setToolButtonStyle(Qt.ToolButtonStyle.ToolButtonFollowStyle); break;
        }
        if (Utils.check("unified-mac-title-toolbar", nodeMap).equals("true")) this.setUnifiedTitleAndToolBarOnMac(true);
        else if (Utils.check("unified-mac-title-toolbar", nodeMap).equals("false")) this.setUnifiedTitleAndToolBarOnMac(false);

        onFunction();
    }

    private String[] Func(String prop){
        String call;
        String[] calls = new String[0];
        if (!(call = Utils.check(prop, nodeMap)).isEmpty()) calls = call.split(":");
        return calls;
    }

    private void onFunction() {
        String[] callParts;
        if ((callParts = Func("on-custom-context-menu-request")).length == 1) this.customContextMenuRequested.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.customContextMenuRequested.connect(QT.findComponent(callParts[0]), callParts[1]);
    }

    @Override
    public String setStyle() {
        String component = "QMainWindow";
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