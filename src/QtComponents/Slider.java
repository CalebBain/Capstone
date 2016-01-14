package QtComponents;

import Assemble.QT;
import Assemble.Utils;
import StyleComponents.Style;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QSlider;
import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/8/2016.
 */
public class Slider extends QSlider implements Component {

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
        onFunction();
        setTick();
        setValue();
        setInvert();
        setSteps();
    }

    private void setSteps() {
        String steps;
        if (Utils.tryValue((steps = Utils.check("page-steps", nodeMap)))) this.setPageStep(Integer.parseInt(steps));
        if (Utils.tryValue((steps = Utils.check("arrow-steps", nodeMap)))) this.setSingleStep(Integer.parseInt(steps));
    }

    private void setInvert() {
        if (Utils.check("invert-numbers", nodeMap).equals("true")) this.setInvertedAppearance(true);
        else if (Utils.check("invert-numbers", nodeMap).equals("false")) this.setInvertedAppearance(false);
        if (Utils.check("invert-controls", nodeMap).equals("true")) this.setInvertedControls(true);
        else if (Utils.check("invert-controls", nodeMap).equals("false")) this.setInvertedControls(false);
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
    }

    private void setTick() {
        switch (Utils.check("position", nodeMap)) {
            case "both": this.setTickPosition(TickPosition.TicksBothSides); break;
            case "left": case "above": this.setTickPosition(TickPosition.TicksAbove); break;
            case "right": case "below": this.setTickPosition(TickPosition.TicksBelow); break;
            case "no-ticks": this.setTickPosition(TickPosition.NoTicks);
        }
        String interval = Utils.check("interval", nodeMap);
        if (Utils.tryValue(interval)) this.setMinimum(Integer.parseInt(interval));
    }

    private void setValue() {
        String minValue, maxValue, value;
        if (Utils.tryValue((minValue = Utils.check("min-value", nodeMap)))) this.setMinimum(Integer.parseInt(minValue));
        if (Utils.tryValue((maxValue = Utils.check("max-value", nodeMap)))) this.setMaximum(Integer.parseInt(maxValue));
        if (Utils.tryValue((value = Utils.check("value", nodeMap)))) this.setValue(Integer.parseInt(value));
    }

    @Override
    public void setStyle() {
        String name = (!this.Name.equals("")) ? this.Name : "QSlider";
        for(Map.Entry<String, Style> style : QT.styles.entrySet()){
            if (style.getKey().startsWith("QSlider")){
                if(style.getKey().equals("QSlider")) styles.get(name).addAll(style.getValue());
                else styles.put(style.getKey(), style.getValue());
            }
            if(style.getKey().startsWith(this.Name)){
                if(style.getKey().equals(this.Name)) styles.get(name).addAll(style.getValue());
                else styles.put(style.getKey(), style.getValue());
            }
            if(style.getKey().startsWith(this.Class)){
                if(style.getKey().equals(this.Class)) styles.get(name).addAll(style.getValue());
                else styles.put(style.getKey(), style.getValue());
            }
        }
        Utils.setStyle(styles.get(name), nodeMap);
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
        return "slider";
    }

    @Override
    public QSlider Widgit() {
        return this;
    }

    @Override
    public void SetStylesheet() {
        this.setStyleSheet("");
    }
}
