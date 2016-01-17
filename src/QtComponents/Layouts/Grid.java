package QtComponents.Layouts;

import Assemble.Utils;
import EventClass.Events;
import QtComponents.Component;
import StyleComponents.Style;
import com.trolltech.qt.core.QChildEvent;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QTimerEvent;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLayout;
import com.trolltech.qt.gui.QLayoutItemInterface;
import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/15/2016.
 */
public final class Grid extends QGridLayout implements Component {
    private Events events = new Events() {
    };
    private Map<String, Style> styles = new HashMap<>();
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Grid() {

    }

    public String setStyle() {
        return "";
    }

    public String Name() {
        return Name;
    }

    public String Class() {
        return Class;
    }

    public String Component() {
        return "grid";
    }

    public QGridLayout Widgit() {
        return this;
    }

    public void SetStylesheet(String sheet) {
    }

    public void addChild(QObject child, Node node) {
        String prop;
        int row = (Utils.tryValue((prop = Utils.check("row", nodeMap)))) ? Integer.parseInt(prop) : 0,
                col = (Utils.tryValue((prop = Utils.check("column", nodeMap)))) ? Integer.parseInt(prop) : 0,
                rspan = (Utils.tryValue((prop = Utils.check("row-span", nodeMap)))) ? Integer.parseInt(prop) : 0,
                cspan = (Utils.tryValue((prop = Utils.check("column-span", nodeMap)))) ? Integer.parseInt(prop) : 0;
        Qt.AlignmentFlag align;
        switch(Utils.check("grid-alignment", nodeMap)){
            case "bottom":
                align = Qt.AlignmentFlag.AlignBottom;
                break;
            case "center":
                align = Qt.AlignmentFlag.AlignCenter;
                break;
            case "horizontal-center":
                align = Qt.AlignmentFlag.AlignHCenter;
                break;
            case "justify":
                align = Qt.AlignmentFlag.AlignJustify;
                break;
            case "left":
                align = Qt.AlignmentFlag.AlignLeft;
                break;
            case "right":
                align = Qt.AlignmentFlag.AlignRight;
                break;
            case "top":
                align = Qt.AlignmentFlag.AlignTop;
                break;
            case "verical-center":
                align = Qt.AlignmentFlag.AlignVCenter;
                break;
            case "absolute":
            default:
                align = Qt.AlignmentFlag.AlignAbsolute;
        }
        if (child instanceof QWidget) this.addWidget((QWidget) child, row, col, rspan, cspan, align);
        if (child instanceof QLayout) this.addLayout((QLayout) child, row, col, rspan, cspan, align);
        else if (child instanceof QLayoutItemInterface)
            this.addItem((QLayoutItemInterface) child, row, col, rspan, cspan, align);
    }

    public void childEvent(QChildEvent event) {
        events.childEvent(this, event);
    }

    public void timerEvent(QTimerEvent event) {
        events.timerEvent(this, event);
    }
}
