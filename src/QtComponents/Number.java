package QtComponents;

import Assemble.QT;
import Assemble.Utils;
import StyleComponents.Style;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QLCDNumber;
import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/8/2016.
 */
public class Number extends QLCDNumber implements Component {

    private Style style;
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Number(QWidget parent, Node node) {
        super(parent);
        this.nodeMap = node.getAttributes();
        setIdentity();
    }

    private void setIdentity() {
        this.Name = Utils.check("name", nodeMap);
        this.Class = Utils.check("class", nodeMap);
        if (!Name.isEmpty()) {
            this.style = new Style(Name, Component(), true);
            this.setAccessibleName(Name);
        } else this.style = new Style("number", Component(), false);
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
        setFrameProps();
        onFunction();
    }

    private void setFrameProps() {
        String count;
        if (Utils.tryValue((count = Utils.check("shadow", nodeMap)))) this.setFrameShadow(Shadow.resolve(Integer.parseInt(count)));
        switch (Utils.check("shadow", nodeMap)) {
            case "plain": this.setFrameShadow(Shadow.Plain); break;
            case "raised": this.setFrameShadow(Shadow.Raised); break;
            case "sunken": this.setFrameShadow(Shadow.Sunken); break;
        }

        if (Utils.tryValue((count = Utils.check("shape", nodeMap)))) this.setFrameShape(Shape.resolve(Integer.parseInt(count)));
        switch (Utils.check("shape", nodeMap)) {
            case "no-frame": this.setFrameShape(Shape.NoFrame); break;
            case "box": this.setFrameShape(Shape.Box); break;
            case "panel": this.setFrameShape(Shape.Panel); break;
            case "styled-panel": this.setFrameShape(Shape.StyledPanel); break;
            case "horizontal-line": this.setFrameShape(Shape.HLine); break;
            case "vertical-line": this.setFrameShape(Shape.VLine); break;
            case "window-panel": this.setFrameShape(Shape.WinPanel); break;
        }
    }

    private String[] Func(String prop){
        String call;
        if (!(call = Utils.check(prop, nodeMap)).isEmpty()) return call.split(":");
        return new String[0];
    }

    private void onFunction() {
        String[] callParts = Func("on-overflow");
        if (callParts.length == 1) this.overflow.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.overflow.connect(QT.findComponent(callParts[0]), callParts[1]);
    }

    @Override
    public void setStyle() {
        if (QT.styles.containsKey(Component())) style.addAll(QT.styles.get(Component()));
        if (QT.styles.containsKey(Class)) style.addAll(QT.styles.get(Class));
        if (QT.styles.containsKey(Name)) style.addAll(QT.styles.remove(Name));
        Utils.setStyle(style, nodeMap);
        setProps();
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
    public QLCDNumber Widgit() {
        return this;
    }

    @Override
    public void SetStylesheet() {
        this.setStyleSheet("");
    }
}
