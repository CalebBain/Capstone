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
 * Created by Caleb Bain on 1/8/2016.
 */
public final class Slider extends QSlider implements Component {
    private Events events = new Events() {};
    private Map<String, Style> styles = new HashMap<>();
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Slider(QWidget parent, Node node) {
        super(parent);
        this.nodeMap = node.getAttributes();
        setIdentity(nodeMap);
    }

    private void setIdentity(NamedNodeMap nodeMap) {
        this.Name = Utils.check("name", nodeMap);
        this.Class = Utils.check("class", nodeMap);
        if (!Name.isEmpty()) {
            this.styles.put(Name, new Style(Name, "QSlider", true));
            this.setAccessibleName(Name);
        } else this.styles.put("QSlider", new Style("QSlider", "QSlider", false));
    }

    private void setProps() {
        switch (Utils.check("position", nodeMap)) {
            case "both": this.setTickPosition(TickPosition.TicksBothSides); break;
            case "left": case "above": this.setTickPosition(TickPosition.TicksAbove); break;
            case "right": case "below": this.setTickPosition(TickPosition.TicksBelow); break;
            case "no-ticks": this.setTickPosition(TickPosition.NoTicks);
        }
        String steps;
        if(Utils.check("orientation", nodeMap).equals("horizontal")) this.setOrientation(Qt.Orientation.Horizontal);
        if(Utils.check("orientation", nodeMap).equals("vertical")) this.setOrientation(Qt.Orientation.Vertical);
        if (Utils.tryValue(steps = Utils.check("interval", nodeMap))) this.setMinimum(Integer.parseInt(steps));
        if (Utils.tryValue(steps = Utils.check("min-value", nodeMap))) this.setMinimum(Integer.parseInt(steps));
        if (Utils.tryValue(steps = Utils.check("max-value", nodeMap))) this.setMaximum(Integer.parseInt(steps));
        if (Utils.tryValue(steps = Utils.check("value", nodeMap))) this.setValue(Integer.parseInt(steps));
        if (Utils.tryValue(steps = Utils.check("page-steps", nodeMap))) this.setPageStep(Integer.parseInt(steps));
        if (Utils.tryValue(steps = Utils.check("arrow-steps", nodeMap))) this.setSingleStep(Integer.parseInt(steps));
        if (Utils.check("invert-numbers", nodeMap).equals("true")) this.setInvertedAppearance(true);
        else if (Utils.check("invert-numbers", nodeMap).equals("false")) this.setInvertedAppearance(false);
        if (Utils.check("invert-controls", nodeMap).equals("true")) this.setInvertedControls(true);
        else if (Utils.check("invert-controls", nodeMap).equals("false")) this.setInvertedControls(false);
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
        if ((callParts = Func("on-value-change")).length == 1) this.valueChanged.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.valueChanged.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-release")).length == 1) this.sliderReleased.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.sliderReleased.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-press")).length == 1) this.sliderPressed.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.sliderPressed.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-action-trigger")).length == 1) this.actionTriggered.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.actionTriggered.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-range-change")).length == 1) this.rangeChanged.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.rangeChanged.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-custom-context-menu-request")).length == 1) this.customContextMenuRequested.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.customContextMenuRequested.connect(QT.findComponent(callParts[0]), callParts[1]);
    }

    @Override
    public String setStyle() {
        String component = "QSlider";
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
        StringBuilder sb = new StringBuilder();
        if(styles.size() == 1 && styles.get(name).getAttributes().size() == 0) return "";
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
        return "slider";
    }

    @Override
    public QSlider Widgit() {
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
