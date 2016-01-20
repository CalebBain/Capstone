package QT.QtComponents;

import QT.Assemble.QT;
import QT.Assemble.Utils;
import QT.EventClass.Events;
import QT.StyleComponents.Style;
import com.trolltech.qt.core.QChildEvent;
import com.trolltech.qt.core.QEvent;
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
        QDesktopWidget desktop = new QDesktopWidget();
        String name = "QColumnView";
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
        switch (Utils.check("drag-drop-mode", nodeMap)) {
            case "no-drag-drop": this.setDragDropMode(DragDropMode.NoDragDrop); break;
            case "drop-only": this.setDragDropMode(DragDropMode.DropOnly); break;
            case "drag-only": this.setDragDropMode(DragDropMode.DragOnly); break;
            case "drag-drop": this.setDragDropMode(DragDropMode.DragDrop); break;
            case "internal-move": this.setDragDropMode(DragDropMode.InternalMove); break;
        }
        switch (Utils.check("edit-trigger", nodeMap)) {
            case "no-edit-triggers": this.setEditTriggers(EditTrigger.NoEditTriggers); break;
            case "current-change": this.setEditTriggers(EditTrigger.CurrentChanged); break;
            case "double-click": this.setEditTriggers(EditTrigger.DoubleClicked); break;
            case "selected-click": this.setEditTriggers(EditTrigger.SelectedClicked); break;
            case "edit-key-press": this.setEditTriggers(EditTrigger.EditKeyPressed); break;
            case "any-key-press": this.setEditTriggers(EditTrigger.AnyKeyPressed); break;
            case "all-edit-triggers": this.setEditTriggers(EditTrigger.AllEditTriggers); break;
        }
        if (Utils.check("horizontal-scroll-mode", nodeMap).equals("scroll-per-item")) this.setHorizontalScrollMode(ScrollMode.ScrollPerItem);
        if (Utils.check("horizontal-scroll-mode", nodeMap).equals("scroll-per-pixel")) this.setHorizontalScrollMode(ScrollMode.ScrollPerPixel);
        if (Utils.check("vertical-scroll-mode", nodeMap).equals("scroll-per-item")) this.setVerticalScrollMode(ScrollMode.ScrollPerItem);
        if (Utils.check("vertical-scroll-mode", nodeMap).equals("scroll-per-pixel")) this.setVerticalScrollMode(ScrollMode.ScrollPerPixel);
        switch (Utils.check("selection-behavior", nodeMap)) {
            case "select-items": this.setSelectionBehavior(SelectionBehavior.SelectItems); break;
            case "select-rows": this.setSelectionBehavior(SelectionBehavior.SelectRows); break;
            case "select-columns": this.setSelectionBehavior(SelectionBehavior.SelectColumns); break;
        }
        switch (Utils.check("selection-mode", nodeMap)) {
            case "single-select": this.setSelectionMode(SelectionMode.SingleSelection); break;
            case "contiguous-select": this.setSelectionMode(SelectionMode.ContiguousSelection); break;
            case "extended-select": this.setSelectionMode(SelectionMode.ExtendedSelection); break;
            case "multi-select": this.setSelectionMode(SelectionMode.MultiSelection); break;
            case "no-select": this.setSelectionMode(SelectionMode.NoSelection); break;
        }
        if (Utils.check("resize-grips-visible", nodeMap).equals("true")) this.setResizeGripsVisible(true);
        if (Utils.check("resize-grips-visible", nodeMap).equals("false")) this.setResizeGripsVisible(false);
        Utils.setWidgetProps(this, nodeMap);
        onFunction();
    }

    private void onFunction() {
        String[] callParts;
        if ((callParts = Utils.Func("on-preview-update", nodeMap)).length == 1) this.updatePreviewWidget.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.updatePreviewWidget.connect(QT.findComponent(callParts[0]), callParts[1]);
        Utils.onAbstractItemViewFunctions(this, nodeMap);
    }

    public String setStyle() {
        String name = Utils.getStyleSheets("QColumnView", styles, Name, Class);
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

    public void addChild(Component child, Node node) {
        if (child instanceof QWidget) this.setPreviewWidget((QWidget) child.Widgit());
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
