package QtComponents;

import Assemble.QT;
import Assemble.Utils;
import StyleComponents.Style;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QLCDNumber;
import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/8/2016.
 */
public final class Number extends QLCDNumber implements Component {

    private Map<String, Style> styles = new HashMap<>();
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
            this.styles.put(Name, new Style(Name, "QLCDNumber", true));
            this.setAccessibleName(Name);
        } else this.styles.put("QLCDNumber", new Style("QLCDNumber", "QLCDNumber", false));
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
    public String setStyle() {
        String name = (!this.Name.equals("")) ? this.Name : "QLCDNumber";
        for(Map.Entry<String, Style> style : QT.styles.entrySet()){
            if (style.getKey().startsWith("QLCDNumber")){
                if(style.getKey().equals("QLCDNumber")) styles.get(name).addAll(style.getValue());
                else {
                    style.getValue().setComponent("QLDCNumebr");
                    styles.put(style.getKey(), style.getValue());
                }
            }
            if(style.getKey().startsWith(this.Name)&&!this.Name.isEmpty()){
                if(style.getKey().equals(this.Name)) styles.get(name).addAll(style.getValue());
                else {
                    style.getValue().setComponent("QLDCNumebr");
                    styles.put(style.getKey(), style.getValue());
                }
            }
            if(style.getKey().startsWith(this.Class)&&!this.Class.isEmpty()){
                if(style.getKey().equals(this.Class)) styles.get(name).addAll(style.getValue());
                else {
                    style.getValue().setComponent("QLDCNumebr");
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
        return "number";
    }

    @Override
    public QLCDNumber Widgit() {
        return this;
    }

    @Override
    public void SetStylesheet(String sheet) {
        this.setStyleSheet(sheet);
    }
}
