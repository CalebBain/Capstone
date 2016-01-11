package QtComponents;

import Assemble.QT;
import StyleComponents.Style;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QSlider;
import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/8/2016.
 */
public class Slider extends QSlider implements Component {

    private Style style;
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Slider(QWidget parent, Node node) {
        super(parent);
        this.nodeMap = node.getAttributes();
        setIdentity(nodeMap);

    }

    public void setIdentity(NamedNodeMap nodeMap) {
        this.Name = check(nodeMap, "name");
        this.Class = check(nodeMap, "class");
        if(!Name.isEmpty()){
            this.style = new Style(Name);
            this.setAccessibleName(Name);
        }else
            this.style = new Style("number");
    }

    public String check(NamedNodeMap nodeMap, String keyword) {
        try {
            Node word = nodeMap.getNamedItem(keyword);
            return (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException e) {
            return "";
        }
    }

    public void setSize(String width, String height) {
        if (!height.isEmpty())     style.addAttrabute("height", height);
        else if (!width.isEmpty()) style.addAttrabute("width", width);
        else if (!height.isEmpty()&&!width.isEmpty()) {
            style.addAttrabute("height", height);
            style.addAttrabute("width", width);
        }
    }

    public void setOrientation(String orientation) {
        style.addPseudoState(orientation);
    }

    public void setTickPosition(String position) {
        switch (position) {
            case "both":
                this.setTickPosition(TickPosition.TicksBothSides);
                break;
            case "left":
            case "above":
                this.setTickPosition(TickPosition.TicksAbove);
                break;
            case "right":
            case "below":
                this.setTickPosition(TickPosition.TicksBelow);
                break;
            default:
                this.setTickPosition(TickPosition.NoTicks);
        }
    }

    public void setMargins(String margins) {
        style.addAttrabute("margin", margins);
    }

    public void setInterval(String interval) {
        try {
            this.setTickInterval(Integer.parseInt(interval));
        } catch (NumberFormatException nfe) {
        }
    }

    public void onValueChange(String call) {
        if (!call.equals("")) {
            String[] callParts = call.split(":");
            if (callParts.length == 1) this.valueChanged.connect(QApplication.instance(), callParts[0]);
            else this.valueChanged.connect(QT.findComponent(callParts[0]), callParts[1]);
        }
    }

    public void onPressed(String call) {
        if (!call.equals("")) {
            String[] callParts = call.split(":");
            if (callParts.length == 1) this.sliderPressed.connect(QApplication.instance(), callParts[0]);
            else this.sliderPressed.connect(QT.findComponent(callParts[0]), callParts[1]);
        }
    }

    public void onReleased(String call) {
        if (!call.equals("")) {
            String[] callParts = call.split(":");
            if (callParts.length == 1) this.sliderReleased.connect(QApplication.instance(), callParts[0]);
            else this.sliderReleased.connect(QT.findComponent(callParts[0]), callParts[1]);
        }
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
        return this.getClass().getName();
    }

    @Override
    public QSlider Widgit() {
        return this;
    }

    @Override
    public void setStyle() {
        setSize(check(nodeMap, "width"), check(nodeMap, "height"));
        setOrientation(check(nodeMap, "orientation"));
        setTickPosition(check(nodeMap, "tick-position"));
        setInterval(check(nodeMap, "interval"));
        onValueChange(check(nodeMap, "onvalue-change"));
        onPressed(check(nodeMap, "onpressed"));
        onReleased(check(nodeMap, "onreleased"));
    }

    @Override
    public void SetStylesheet() {
        this.setStyleSheet("");
    }
}
