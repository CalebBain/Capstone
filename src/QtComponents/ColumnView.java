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
 * Created by Caleb Bain on 1/15/2016.
 */
public final class ColumnView extends QColumnView implements Component {
    private Events events = new Events() {
    };
    private Map<String, Style> styles = new HashMap<>();
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public ColumnView(Node node) {
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
        switch (Utils.check("drag-drop-mode", nodeMap)) {
            case "no-drag-drop":
                this.setDragDropMode(DragDropMode.NoDragDrop);
                break;
            case "drop-only":
                this.setDragDropMode(DragDropMode.DropOnly);
                break;
            case "drag-only":
                this.setDragDropMode(DragDropMode.DragOnly);
                break;
            case "drag-drop":
                this.setDragDropMode(DragDropMode.DragDrop);
                break;
            case "internal-move":
                this.setDragDropMode(DragDropMode.InternalMove);
                break;
        }
        switch (Utils.check("edit-trigger", nodeMap)) {
            case "no-edit-triggers":
                this.setEditTriggers(EditTrigger.NoEditTriggers);
                break;
            case "current-change":
                this.setEditTriggers(EditTrigger.CurrentChanged);
                break;
            case "double-click":
                this.setEditTriggers(EditTrigger.DoubleClicked);
                break;
            case "selected-click":
                this.setEditTriggers(EditTrigger.SelectedClicked);
                break;
            case "edit-key-press":
                this.setEditTriggers(EditTrigger.EditKeyPressed);
                break;
            case "any-key-press":
                this.setEditTriggers(EditTrigger.AnyKeyPressed);
                break;
            case "all-edit-triggers":
                this.setEditTriggers(EditTrigger.AllEditTriggers);
                break;
        }
        if (Utils.check("horizontal-scroll-mode", nodeMap).equals("scroll-per-item"))
            this.setHorizontalScrollMode(ScrollMode.ScrollPerItem);
        if (Utils.check("horizontal-scroll-mode", nodeMap).equals("scroll-per-pixel"))
            this.setHorizontalScrollMode(ScrollMode.ScrollPerPixel);
        if (Utils.check("vertical-scroll-mode", nodeMap).equals("scroll-per-item"))
            this.setVerticalScrollMode(ScrollMode.ScrollPerItem);
        if (Utils.check("vertical-scroll-mode", nodeMap).equals("scroll-per-pixel"))
            this.setVerticalScrollMode(ScrollMode.ScrollPerPixel);
        switch (Utils.check("selection-behavior", nodeMap)) {
            case "select-items":
                this.setSelectionBehavior(SelectionBehavior.SelectItems);
                break;
            case "select-rows":
                this.setSelectionBehavior(SelectionBehavior.SelectRows);
                break;
            case "select-columns":
                this.setSelectionBehavior(SelectionBehavior.SelectColumns);
                break;
        }
        switch (Utils.check("selection-mode", nodeMap)) {
            case "single-select":
                this.setSelectionMode(SelectionMode.SingleSelection);
                break;
            case "contiguous-select":
                this.setSelectionMode(SelectionMode.ContiguousSelection);
                break;
            case "extended-select":
                this.setSelectionMode(SelectionMode.ExtendedSelection);
                break;
            case "multi-select":
                this.setSelectionMode(SelectionMode.MultiSelection);
                break;
            case "no-select":
                this.setSelectionMode(SelectionMode.NoSelection);
                break;
        }
        if (Utils.check("resize-grips-visible", nodeMap).equals("true")) this.setResizeGripsVisible(true);
        if (Utils.check("resize-grips-visible", nodeMap).equals("false")) this.setResizeGripsVisible(false);
        onFunction();
    }

    private String[] Func(String prop) {
        String call;
        if (!(call = Utils.check(prop, nodeMap)).isEmpty()) return call.split(":");
        return new String[0];
    }

    private void onFunction() {
        String[] callParts;
        if ((callParts = Func("on-click")).length == 1) this.clicked.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.clicked.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-press")).length == 1) this.pressed.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.pressed.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-custom-context-menu-request")).length == 1)
            this.customContextMenuRequested.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2)
            this.customContextMenuRequested.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-double-click")).length == 1)
            this.doubleClicked.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.doubleClicked.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-activate")).length == 1)
            this.activated.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.activated.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-enter")).length == 1) this.entered.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.entered.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-viewport-enter")).length == 1)
            this.viewportEntered.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.viewportEntered.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-preview-update")).length == 1)
            this.updatePreviewWidget.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.updatePreviewWidget.connect(QT.findComponent(callParts[0]), callParts[1]);
    }

    public String setStyle() {
        String component = "QColumnView";
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
        if (styles.size() == 1 && styles.get(name).getAttributes().size() == 0) return "";
        StringBuilder sb = new StringBuilder();
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
        return "button";
    }

    public QColumnView Widgit() {
        return this;
    }

    public void SetStylesheet(String sheet) {
        this.setStyleSheet(sheet);
    }

    public void addChild(QObject child, Node node) {
        if (child instanceof QWidget) this.setPreviewWidget((QWidget) child);
    }

    public void actionEvent(QActionEvent event) {
        events.actionEvent(this, event);
    }

    public void changeEvent(QEvent event) {
        events.changeEvent(this, event);
    }

    public void closeEvent(QCloseEvent event) {
        events.closeEvent(this, event);
    }

    public void dragEnterEvent(QDragEnterEvent event) {
        events.dragEnterEvent(this, event);
    }

    public void dragLeaveEvent(QDragLeaveEvent event) {
        events.dragLeaveEvent(this, event);
    }

    public void dragMoveEvent(QDragMoveEvent event) {
        events.dragMoveEvent(this, event);
    }

    public void dropEvent(QDropEvent event) {
        events.dropEvent(this, event);
    }

    public void enterEvent(QEvent event) {
        events.enterEvent(this, event);
    }

    public void focusInEvent(QFocusEvent event) {
        events.focusInEvent(this, event);
    }

    public void focusOutEvent(QFocusEvent event) {
        events.focusOutEvent(this, event);
    }

    public void hideEvent(QHideEvent event) {
        events.hideEvent(this, event);
    }

    public void inputMethodEvent(QInputMethodEvent event) {
        events.inputMethodEvent(this, event);
    }

    public void keyPressEvent(QKeyEvent event) {
        events.keyPressEvent(this, event);
    }

    public void keyReleaseEvent(QKeyEvent event) {
        events.keyReleaseEvent(this, event);
    }

    public void leaveEvent(QEvent event) {
        events.leaveEvent(this, event);
    }

    public void mouseDoubleClickEvent(QMouseEvent event) {
        events.mouseDoubleClickEvent(this, event);
    }

    public void mouseMoveEvent(QMouseEvent event) {
        events.mouseMoveEvent(this, event);
    }

    public void mouseReleaseEvent(QMouseEvent event) {
        events.mouseReleaseEvent(this, event);
    }

    public void moveEvent(QMoveEvent event) {
        events.moveEvent(this, event);
    }

    public void paintEvent(QPaintEvent event) {
        events.paintEvent(this, event);
    }

    public void resizeEvent(QResizeEvent event) {
        events.resizeEvent(this, event);
    }

    public void tabletEvent(QTabletEvent event) {
        events.tabletEvent(this, event);
    }

    public void wheelEvent(QWheelEvent event) {
        events.wheelEvent(this, event);
    }

    public void childEvent(QChildEvent event) {
        events.childEvent(this, event);
    }

    public void timerEvent(QTimerEvent event) {
        events.timerEvent(this, event);
    }

    public void showEvent(QShowEvent event) {
        events.showEvent(this, event);
    }

    public void contextMenuEvent(QContextMenuEvent event) {
        events.contextMenuEvent(this, event);
    }

    public void mousePressEvent(QMouseEvent event) {
        events.mousePressEvent(this, event);
    }
}
