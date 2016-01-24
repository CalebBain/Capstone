package Compiler.Parser;

import Compiler.Utils;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLayout;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ComponentParser {
    private InlineStyleParser styles = new InlineStyleParser();
    private FunctionParser functions = new FunctionParser();

    public String nodeLoop(QObject parent, StringBuilder sb, NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            QObject component = elementsSwitch(node.getNodeName(), sb, node.getAttributes());
            if (component instanceof QLayout) nodeLoop(component, sb, node.getChildNodes());
        }
        return sb.toString();
    }


    public QObject elementsSwitch(String name, StringBuilder sb, NamedNodeMap nodeMap){
        QObject component = null;
        switch (name) {
            case "check-box": CheckBox(name, sb, nodeMap); break;
            case "radio": Radio(name, sb, nodeMap); break;
            case "button": Button(name, sb, nodeMap); break;
            case "number": Number(name, sb, nodeMap); break;
            case "slider": Slider(name, sb, nodeMap); break;
            case "grid": Grid(name, sb, nodeMap);
                component = new QGridLayout(); break;
        }
        return component;
    }

    private void Grid(String name, StringBuilder sb, NamedNodeMap nodeMap) {
        sb.append(String.format("QGridLayout %1s = new QGridLayout();", name));
    }

    private void GridChild(String name, String component, String child, StringBuilder sb, NamedNodeMap nodeMap){
        sb.append(String.format("%1s.add%2s(%3s, ", name, component, child));
        int row = Utils.tryValue("row", "%2s, ", 1, sb, nodeMap);
        int col = Utils.tryValue("column", "%2s, ", 1, sb, nodeMap);
        Utils.tryValue("row-span", "%2s, ", row, sb, nodeMap);
        Utils.tryValue("column-span", "%2s, ", col, sb, nodeMap);
        sb.append("Qt.AlignmentFlag.");
        switch(Utils.check("position", nodeMap)){
            case "bottom": sb.append("AlignBottom);\n"); break;
            case "center": sb.append("AlignCenter);\n"); break;
            case "horizontal-center": sb.append("AlignHCenter);\n"); break;
            case "justify": sb.append("AlignJustify);\n"); break;
            case "left": sb.append("AlignLeft);\n"); break;
            case "right": sb.append("AlignRight);\n"); break;
            case "top": sb.append("AlignTop);\n"); break;
            case "vertical-center": sb.append("AlignVCenter);\n"); break;
            default: sb.append("AlignAbsolute);\n"); break;
        }
    }

    private void addChild(String name, String component, String child, StringBuilder sb, NamedNodeMap nodeMap){
        switch (name) {
            case "grid": GridChild(name, component, child, sb, nodeMap); break;
        }
    }

    private void Slider(String name, StringBuilder sb, NamedNodeMap nodeMap){
        String prop;
        sb.append(String.format("QSlider %1s = new QSlider();", name));
        if(!(prop = Utils.check("position", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setTickPosition(TickPosition.", name));
            switch(prop){
                case "both": sb.append("TicksBothSides);\n"); break;
                case "left": sb.append("TicksAbove);\n"); break;
                case "right": sb.append("TicksBelow);\n"); break;
                case "no-ticks": sb.append("NoTicks);\n"); break;
            }
        }
        Utils.tryValue(name, "interval", "%1s.setTickInterval(%2s);\n", sb, nodeMap);
        styles.AbstractSlider(name, sb, nodeMap);
        functions.onAbstractSliderFunctions(name, sb, nodeMap);
    }

    private void Number(String name, StringBuilder sb, NamedNodeMap nodeMap){
        String prop;
        sb.append(String.format("QLCDNumber %1s = new QLCDNumber();", name));
        if(!(prop = Utils.check("segment-style", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setSegmentStyle(SegmentStyle.", name));
            switch(prop){
                case "outline": sb.append("Outline);\n"); break;
                case "filled": sb.append("Filled);\n"); break;
                case "flat": sb.append("Flat);\n"); break;
            }
        }
        if(!(prop = Utils.check("mode", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setMode(Mode.", name));
            switch(prop){
                case "hex": sb.append("Hex);\n"); break;
                case "dec": sb.append("Dec);\n"); break;
                case "oct": sb.append("Oct);\n"); break;
                case "bin": sb.append("Bin);\n"); break;
            }
        }
        Utils.tryBoolean(name, "small-decimal-point", "%1s.setSmallDecimalPoint(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "digit-count", "%1s.setDigitCount(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "value", "%1s.display(%2s);\n", sb, nodeMap);
        styles.Frame(name, sb, nodeMap);
        functions.MakeFunc(name + ".overflow.connect(", Utils.check("on-overflow", nodeMap), sb, nodeMap);
        functions.onWidgetFunctions(name, sb, nodeMap);
    }

    private void Button(String name, StringBuilder sb, NamedNodeMap nodeMap){
        sb.append(String.format("QPushButton %1s = new QPushButton();", name));
        Utils.tryBoolean(name, "default", "%1s.setDefault(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "flat", "%1s.setFlat(%2s);\n", sb, nodeMap);
        styles.AbstractButton(name, sb, nodeMap);
        functions.onAbstractButtonFunctions(name, sb, nodeMap);
    }

    private void Radio(String name, StringBuilder sb, NamedNodeMap nodeMap){
        sb.append(String.format("QRadioButton %1s = new QRadioButton();", name));
        styles.AbstractButton(name, sb, nodeMap);
        functions.onAbstractButtonFunctions(name, sb, nodeMap);
    }

    private void CheckBox(String name, StringBuilder sb, NamedNodeMap nodeMap){
        String prop;
        sb.append(String.format("QCheckBox %1s = new QCheckBox();", name));
        Utils.tryBoolean(name, "checkable", "%1s.setTristate(%2s);\n", sb, nodeMap);
        if(!(prop = Utils.check("check-state", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setCheckState(Qt.CheckState.", name));
            switch(prop){
                case "unchecked": sb.append("Unchecked);\n"); break;
                case "partially-checked": sb.append("PartiallyChecked);\n"); break;
                case "checked": sb.append("Checked);\n"); break;
            }
        }
        styles.AbstractButton(name, sb, nodeMap);
        functions.MakeFunc(name + ".stateChanged.connect(", Utils.check("on-state-change", nodeMap), sb, nodeMap);
        functions.onAbstractButtonFunctions(name, sb, nodeMap);
    }


}
