package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* notes
*   this is the second implantation of the code generator
*
*   the first one relied on the component classes and that interpreted the jaml
*
*   this one compiles the code into a java qt code and runs it
*       I chose to move to this because it was
*
*
*
* */

public class ComponentParser {
    private InlineStyleParser styles = new InlineStyleParser();
    private FunctionParser functions = new FunctionParser();
    private StringBuilder sb;

    public ComponentParser(String name, StringBuilder sb, Node node) {
        this.sb = sb;
        Window(name, "QMainWindow", node);
        nodeLoop(node);
    }

    public String nodeLoop(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            boolean component = elementsSwitch(n.getNodeName(), n);
            if (component) nodeLoop(n);
        }
        return sb.toString();
    }

    public boolean elementsSwitch(String name, Node node){
        boolean layout = false;
        switch (name) {
            case "check-box": CheckBox(name, node); break;
            case "radio": Radio(name, node); break;
            case "button": Button(name, node); break;
            case "number": Number(name, node); break;
            case "slider": Slider(name, node); break;
            case "grid": Grid(name, node);
                layout = true; break;
        }
        return layout;
    }

    private void addChild(String name, String layout, String component, String child, NamedNodeMap nodeMap){
        switch (layout) {
            case "window": WindowChild(child, component); break;
            case "grid" : GridChild(name, component, child, nodeMap); break;
        }
    }

    private void WindowChild(String name, String componentType){
        switch (componentType){
            case "layout" : sb.append(String.format("centerWidget.setLayout(%1s);\n", name)); break;
            case "widget" : sb.append(String.format("%1s.setParent(centerWidget);\n", name)); break;
        }
    }

    private void GridChild(String name, String component, String child, NamedNodeMap nodeMap){
        Utils.capitalize(component);
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

    private void ColumnViewChild(String name, String component){
        switch (component){
            case "window" : case "grid" : break;
            default: sb.append(String.format("ColumnView.setPreviewWidget((%1s);\n", name)); break;
        }
    }

    private void Window(String app, String name, Node node){
        String prop;
        NamedNodeMap nodeMap = node.getAttributes();
        sb.append(String.format("QMainWindow window = new QMainWindow(%1s);\n", app));
        sb.append("QWidget centerWidget = new QWidget();\n");
        sb.append("window.setCentralWidget(centerWidget);\n");
        sb.append(String.format("%1s.setWindowTitle(tr(\"%2s\"));\n", name, Utils.tryEmpty("title", "JAML Applicaiton", nodeMap)));
        Utils.tryBoolean(name, "dock-animation", "%1s.setAnimated(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "dock-nesting", "%1s.setDockNestingEnabled(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "document-mode", "%1s.setDocumentMode(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "unified-mac-title-toolbar", "%1s.setUnifiedTitleAndToolBarOnMac(%2s);\n", sb, nodeMap);
        if(!(prop = Utils.check("dock-option", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setDockOptions(DockOption.", name));
            switch(prop){
                case "animated-docks": sb.append("AnimatedDocks);\n"); break;
                case "allow-nested-docks": sb.append("AllowNestedDocks);\n"); break;
                case "allow-tabbed-docks": sb.append("AllowTabbedDocks);\n"); break;
                case "force-tabbed-docks": sb.append("ForceTabbedDocks);\n"); break;
                case "vertical-tabs": sb.append("VerticalTabs);\n"); break;
            }
        }
        if(!(prop = Utils.check("tab-shape", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setTabShape(QTabWidget.TabShape.", name));
            switch(prop){
                case "rounded": sb.append("Rounded);\n"); break;
                case "triangular": sb.append("Triangular);\n"); break;
            }
        }
        if(!(prop = Utils.check("tool-button-style", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setToolButtonStyle(Qt.ToolButtonStyle.", name));
            switch(prop){
                case "icon-only": sb.append("ToolButtonIconOnly);\n"); break;
                case "text-only": sb.append("ToolButtonTextOnly);\n"); break;
                case "text-beside-icon": sb.append("ToolButtonTextBesideIcon);\n"); break;
                case "text-under-icon": sb.append("ToolButtonTextUnderIcon);\n"); break;
                case "follow-style": sb.append("ToolButtonFollowStyle);\n"); break;
            }
        }
        functions.MakeFunc(name + ".iconSizeChanged.connect(", Utils.check("on-icon-size-change", nodeMap), sb, nodeMap);
        functions.MakeFunc(name + ".toolButtonStyleChanged.connect(", Utils.check("on-tool-button-style-change", nodeMap), sb, nodeMap);
        functions.onWidgetFunctions(name, sb, nodeMap);
    }

    private void Slider(String name, Node node){
        String prop;
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        String n = Utils.tryEmpty("name", name, nodeMap);
        sb.append(String.format("QSlider %1s = new QSlider();\n", n));
        if(!(prop = Utils.check("tick-position", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setTickPosition(TickPosition.", n));
            switch(prop){
                case "both": sb.append("TicksBothSides"); break;
                case "above": case "left": sb.append("TicksAbove"); break;
                case "below": case "right": sb.append("TicksBelow"); break;
                default: sb.append("NoTicks"); break;
            }
            sb.append(");\n");
        }
        Utils.tryValue(n, "interval", "%1s.setTickInterval(%2s);\n", sb, nodeMap);
        styles.AbstractSlider(n, sb, nodeMap);
        functions.onAbstractSliderFunctions(n, sb, nodeMap);
        addChild(layoutName, layout, "widget", n, nodeMap);
    }

    private void Number(String name, Node node){
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        String n = Utils.tryEmpty("name", name, nodeMap);
        sb.append(String.format("QLCDNumber %1s = new QLCDNumber();\n", n));
        Utils.tryCapitalize(n, "segment-style", "%1s.setSegmentStyle(QLCDNumber.SegmentStyle.%2s);\n", sb, nodeMap);
        Utils.tryCapitalize(n, "mode", "%1s.setMode(QLCDNumber.Mode.%2s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "small-decimal-point", "%1s.setSmallDecimalPoint(%2s);\n", sb, nodeMap);
        Utils.tryValue(n, "digit-count", "%1s.setDigitCount(%2s);\n", sb, nodeMap);
        Utils.tryValue(n, "value", "%1s.display(%2s);\n", sb, nodeMap);
        styles.Frame(n, sb, nodeMap);
        functions.MakeFunc(n + ".overflow.connect(", Utils.check("on-overflow", nodeMap), sb, nodeMap);
        functions.onWidgetFunctions(n, sb, nodeMap);
        addChild(layoutName, layout, "widget", n, nodeMap);
    }

    private void Button(String name, Node node){
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        String n = Utils.tryEmpty("name", name, nodeMap);
        sb.append(String.format("QPushButton %1s = new QPushButton(\"%2s\");\n", n, node.getTextContent()));
        Utils.tryBoolean(n, "default", "%1s.setDefault(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "flat", "%1s.setFlat(%2s);\n", sb, nodeMap);
        styles.AbstractButton(n, sb, nodeMap);
        functions.onAbstractButtonFunctions(n, sb, nodeMap);
        addChild(layoutName, layout, "widget", n, nodeMap);
    }

    private void Radio(String name, Node node){
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        String n = Utils.tryEmpty("name", name, nodeMap);
        sb.append(String.format("QRadioButton %1s = new QRadioButton();\n", n));
        styles.AbstractButton(n, sb, nodeMap);
        functions.onAbstractButtonFunctions(n, sb, nodeMap);
        addChild(layoutName, layout, "widget", n, nodeMap);
    }

    private void CheckBox(String name, Node node){
        String prop;
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        String n = Utils.tryEmpty("name", name, nodeMap);
        sb.append(String.format("QCheckBox %1s = new QCheckBox();\n", n));
        Utils.tryBoolean(n, "checkable", "%1s.setTristate(%2s);\n", sb, nodeMap);
        if(!(prop = Utils.check("check-state", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setCheckState(Qt.CheckState.", n));
            switch(prop){
                case "unchecked": sb.append("Unchecked);\n"); break;
                case "partially-checked": sb.append("PartiallyChecked);\n"); break;
                case "checked": sb.append("Checked);\n"); break;
            }
        }
        styles.AbstractButton(n, sb, nodeMap);
        functions.MakeFunc(n + ".stateChanged.connect(", Utils.check("on-state-change", nodeMap), sb, nodeMap);
        functions.onAbstractButtonFunctions(n, sb, nodeMap);
        addChild(layoutName, layout, "widget", n, nodeMap);
    }

    private void ColumnView(String name, Node node){
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        String n = Utils.tryEmpty("name", name, nodeMap);
        Utils.tryBoolean(n, "resize-grips-visible", "%1s.setResizeGripsVisible(%2s);\n", sb, nodeMap);
        styles.AbstractItemView(n, sb, nodeMap);
        functions.MakeFunc(n + ".updatePreviewWidget.connect(", "on-preview-update", sb, nodeMap);
        functions.onAbstractItemViewFunctions(n, sb, nodeMap);
        addChild(layoutName, layout, "widget", n, nodeMap);
    }

    private void Grid(String name, Node node) {
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        String n = Utils.tryEmpty("name", name, nodeMap);
        sb.append(String.format("QGridLayout %1s = new QGridLayout();\n", n));
        addChild(layoutName, layout, "layout", n, nodeMap);
    }
}
