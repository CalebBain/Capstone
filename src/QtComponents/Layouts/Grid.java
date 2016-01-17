package QtComponents.Layouts;

import Assemble.Utils;
import EventClass.Events;
import QtComponents.Component;
import StyleComponents.Style;
import com.trolltech.qt.core.QChildEvent;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QTimerEvent;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/15/2016.
 */
public final class Grid extends QGridLayout implements Component {
    private Events events = new Events() {};
    private Map<String, Style> styles = new HashMap<>();
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Grid(QWidget parent, Node node) {
        super(parent);
    }

    @Override
    public String setStyle() {
        return null;
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
        return "grid";
    }

    @Override
    public QGridLayout Widgit() {
        return this;
    }

    @Override
    public void SetStylesheet(String sheet) {}

    @Override
    public void addChild(QObject child, Node node) {
        String prop;
        int row = (Utils.tryValue((prop = Utils.check("row", nodeMap)))) ? Integer.parseInt(prop) : 1,
        col = (Utils.tryValue((prop = Utils.check("column", nodeMap)))) ? Integer.parseInt(prop) : 1,
        rspan = (Utils.tryValue((prop = Utils.check("row-span", nodeMap)))) ? Integer.parseInt(prop) : 1,
        cspan = (Utils.tryValue((prop = Utils.check("column-span", nodeMap)))) ? Integer.parseInt(prop) : 1;
        Qt.AlignmentFlag align = Qt.AlignmentFlag.valueOf(Utils.check("grid-alignment", nodeMap));
        if(child instanceof QWidget) this.addWidget((QWidget)child, row, col, rspan, cspan, align);
        if(child instanceof QLayout) this.addLayout((QLayout) child, row, col, rspan, cspan, align);
        else if(child instanceof QLayoutItemInterface) this.addItem((QLayoutItemInterface) child, row, col, rspan, cspan, align);
    }

    public void childEvent(QChildEvent event) { events.childEvent(this, event); }
    public void timerEvent(QTimerEvent event) { events.timerEvent(this, event); }
}
