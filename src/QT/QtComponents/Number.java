package QT.QtComponents;

import QT.QT;
import Compiler.Utils;
import QT.EventClass.Events;
import Compiler.Parser.Style;
import com.trolltech.qt.core.QChildEvent;
import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.core.QTimerEvent;
import com.trolltech.qt.gui.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/8/2016.
 */
public final class Number extends QLCDNumber implements Component {
    private Events events = new Events() {
    };
    private Map<String, Style> styles = new HashMap<>();
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Number(Node node) {
        this.nodeMap = node.getAttributes();
        setIdentity();
    }

    private void setIdentity() {
        this.Name = Utils.check("name", nodeMap);
        this.Class = Utils.check("class", nodeMap);
        String name = "QLCDNumber";
        if (!Name.isEmpty()) {
            this.styles.put(Name, new Style(Name, name, true));
            Utils.setDimensions(styles, Name);
            this.setAccessibleName(Name);
        } else {
            this.styles.put(name, new Style(name, name, false));
            Utils.setDimensions(styles, name);
        }
    }

    private void setProps() {
        switch (Utils.check("segment-style", nodeMap)) {
            case "outline": this.setSegmentStyle(SegmentStyle.Outline); break;
            case "filled": this.setSegmentStyle(SegmentStyle.Filled); break;
            case "flat": this.setSegmentStyle(SegmentStyle.Flat); break;
        }
        switch (Utils.check("mode", nodeMap)) {
            case "hex": this.setMode(Mode.Hex); break;
            case "dec": this.setMode(Mode.Dec); break;
            case "oct": this.setMode(Mode.Oct); break;
            case "bin": this.setMode(Mode.Bin); break;
        }
        if (Utils.check("small-decimal-point", nodeMap).equals("true")) this.setSmallDecimalPoint(true);
        else if (Utils.check("small-decimal-point", nodeMap).equals("false")) this.setSmallDecimalPoint(false);

        String count;
        if (Utils.tryValue((count = Utils.check("digit-count", nodeMap)))) this.setDigitCount(Integer.parseInt(count));
        if (Utils.tryValue((count = Utils.check("value", nodeMap)))) this.display(Integer.parseInt(count));
        Utils.setFrameProps(this, nodeMap);
        onFunction();
    }

    private void onFunction() {
        String[] callParts;
        if ((callParts = Utils.Func("on-overflow", nodeMap)).length == 1) this.overflow.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.overflow.connect(QT.findComponent(callParts[0]), callParts[1]);
        Utils.onWidgetFunctions(this, nodeMap);
    }

    public String setStyle() {
        Utils.getStyleSheets("QLDCNumber", styles, Name, Class, nodeMap);
        setProps();
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
        return "number";
    }

    public QLCDNumber Widgit() {
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
