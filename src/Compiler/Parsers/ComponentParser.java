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
    private EventParser events = new EventParser();
    private StringBuilder sb;
    private String file;

    public ComponentParser(String file, String name, StringBuilder sb, Node node) {
        this.sb = sb;
        this.file = file;
        Window(name, "QMainWindow", node);
        nodeLoop(node);
    }

    public String nodeLoop(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            String component = elementsSwitch(n.getNodeName(), n);
            if (component.equals("layout")) nodeLoop(n);
        }
        return sb.toString();
    }

    public String elementsSwitch(String name, Node node){
        String component = "widget";
        switch (name) {
            case "check-box": CheckBox(name, node); break;
            case "radio": Radio(name, node); break;
            case "button": Button(name, node); break;
            case "number": Number(name, node); break;
            case "slider": Slider(name, node); break;
            case "grid": Grid(name, node);
                component = "layout"; break;
        }
        return component;
    }

    private void addChild(String name, String layout, String component, String child, NamedNodeMap nodeMap){
        switch (layout) {
            case "window": WindowChild(child, component); break;
            case "grid" : GridChild(name, component, child, nodeMap); break;
            case "menubar" : MenubarChild(name, component, child, nodeMap); break;
            case "menu" : MenuChild(name, component, child, nodeMap); break;
        }
    }

    private void WindowChild(String name, String component){
        switch (component){
            case "layout" : sb.append(String.format("\t\tcenterWidget.setLayout(%s);\n", name)); break;
            case "widget" : sb.append(String.format("\t\t%s.setParent(centerWidget);\n", name)); break;
            case "menubar" : sb.append(String.format("\t\twindow.setMenuBar(%s);\n", name)); break;
        }
    }

    private void GridChild(String name, String component, String child, NamedNodeMap nodeMap){
        Utils.capitalize(component);
        sb.append(String.format("\t\t%1s.add%2s(%3s, ", name, component, child));
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

    private void MenubarChild(String name, String component, String child, NamedNodeMap nodeMap){
        switch (component){
            case "widget" :
                Utils.tryBoolean(name, "menu-corner", "\t\t%s.setCornerWidget(%2s);\n", "left", "right", sb, nodeMap);
                break;
            case "menu" : sb.append(String.format("\t\t%s.addMenu(%s);\n", name, child)); break;
            case "action" :
                sb.append(String.format("\t\t%s.addAction(%s);\n", name, child));
                Utils.tryBoolean(name, "default", child, "\t\t%s.setDefaultUp(%s);\n", sb, nodeMap);
                Utils.tryBoolean(name, "active", child, "\t\t%s.setNativeMenuBar(%s);\n", sb, nodeMap);
                break;
            case "separator" : sb.append(String.format("\t\t%s.addSeparator();\n", name)); break;
        }
    }

    private void MenuChild(String name, String component, String child, NamedNodeMap nodeMap) {
        switch (component){
            case "menu" : sb.append(String.format("\t\t%s.addMenu(%s);\n", name, child)); break;
            case "action" :
                sb.append(String.format("\t\t%s.addAction(%s);\n", name, child));
                Utils.tryBoolean(name, "default", child, "\t\t%s.setDefaultUp(%s);\n", sb, nodeMap);
                Utils.tryBoolean(name, "active", child, "\t\t%s.setNativeMenuBar(%s);\n", sb, nodeMap);
                break;
            case "separator" : sb.append(String.format("\t\t%s.addSeparator();\n", name)); break;
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
        sb.append(String.format("\t\tQMainWindow window = new QMainWindow(%1s);\n", app));
        sb.append("\t\tQWidget centerWidget = new QWidget();\n");
        sb.append("\t\twindow.setCentralWidget(centerWidget);\n");
        sb.append(String.format("\t\t%1s.setWindowTitle(tr(\"%2s\"));\n", name, Utils.tryEmpty("title", "JAML Applicaiton", nodeMap)));
        Utils.tryBoolean(name, "dock-animation", "\t\t%1s.setAnimated(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "dock-nesting", "\t\t%1s.setDockNestingEnabled(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "document-mode", "\t\t%1s.setDocumentMode(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "unified-mac-title-toolbar", "\t\t%1s.setUnifiedTitleAndToolBarOnMac(%2s);\n", sb, nodeMap);
        if(!(prop = Utils.check("dock-option", nodeMap)).isEmpty()){
            sb.append(String.format("\t\t%1s.setDockOptions(DockOption.", name));
            switch(prop){
                case "animated-docks": sb.append("AnimatedDocks);\n"); break;
                case "allow-nested-docks": sb.append("AllowNestedDocks);\n"); break;
                case "allow-tabbed-docks": sb.append("AllowTabbedDocks);\n"); break;
                case "force-tabbed-docks": sb.append("ForceTabbedDocks);\n"); break;
                case "vertical-tabs": sb.append("VerticalTabs);\n"); break;
            }
        }
        if(!(prop = Utils.check("tab-shape", nodeMap)).isEmpty()){
            sb.append(String.format("\t\t%1s.setTabShape(QTabWidget.TabShape.", name));
            switch(prop){
                case "rounded": sb.append("Rounded);\n"); break;
                case "triangular": sb.append("Triangular);\n"); break;
            }
        }
        if(!(prop = Utils.check("tool-button-style", nodeMap)).isEmpty()){
            sb.append(String.format("\t\t%1s.setToolButtonStyle(Qt.ToolButtonStyle.", name));
            switch(prop){
                case "icon-only": sb.append("ToolButtonIconOnly);\n"); break;
                case "text-only": sb.append("ToolButtonTextOnly);\n"); break;
                case "text-beside-icon": sb.append("ToolButtonTextBesideIcon);\n"); break;
                case "text-under-icon": sb.append("ToolButtonTextUnderIcon);\n"); break;
                case "follow-style": sb.append("ToolButtonFollowStyle);\n"); break;
            }
        }
        functions.MakeFunc("\t\t" + name + ".iconSizeChanged.connect(", Utils.check("on-icon-size-change", nodeMap), sb, nodeMap);
        functions.MakeFunc("\t\t" + name + ".toolButtonStyleChanged.connect(", Utils.check("on - tool - button - style - change", nodeMap), sb, nodeMap);
        functions.onWidgetFunctions(name, sb, nodeMap);
    }

    private void Slider(String name, Node node){
        String prop;
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        String n = Utils.tryEmpty("name", name, nodeMap);
        events.Events(file, n, name, "", sb, nodeMap);
        if(!(prop = Utils.check("tick-position", nodeMap)).isEmpty()){
            sb.append(String.format("\t\t%1s.setTickPosition(TickPosition.", n));
            switch(prop){
                case "both": sb.append("TicksBothSides"); break;
                case "above": case "left": sb.append("TicksAbove"); break;
                case "below": case "right": sb.append("TicksBelow"); break;
                default: sb.append("NoTicks"); break;
            }
            sb.append(");\n");
        }
        Utils.tryValue(n, "interval", "\t\t%1s.setTickInterval(%2s);\n", sb, nodeMap);
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
        events.Events(file, n, name, "", sb, nodeMap);
        Utils.tryCapitalize(n, "segment-style", "\t\t%1s.setSegmentStyle(QLCDNumber.SegmentStyle.%2s);\n", sb, nodeMap);
        Utils.tryCapitalize(n, "mode", "\t\t%1s.setMode(QLCDNumber.Mode.%2s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "small-decimal-point", "\t\t%1s.setSmallDecimalPoint(%2s);\n", sb, nodeMap);
        Utils.tryValue(n, "digit-count", "\t\t%1s.setDigitCount(%2s);\n", sb, nodeMap);
        Utils.tryValue(n, "value", "\t\t%1s.display(%2s);\n", sb, nodeMap);
        styles.Frame(n, sb, nodeMap);
        functions.MakeFunc("\t\t" + n + ".overflow.connect(", Utils.check("on - overflow", nodeMap), sb, nodeMap);
        functions.onWidgetFunctions(n, sb, nodeMap);
        addChild(layoutName, layout, "widget", n, nodeMap);
    }

    private void Button(String name, Node node){
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        String n = Utils.tryEmpty("name", name, nodeMap);
        events.Events(file, n, name, node.getTextContent(), sb, nodeMap);
        Utils.tryBoolean(n, "default", "\t\t%1s.setDefault(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "flat", "\t\t%1s.setFlat(%2s);\n", sb, nodeMap);
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
        events.Events(file, n, name, "", sb, nodeMap);
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
        events.Events(file, n, name, "", sb, nodeMap);
        Utils.tryBoolean(n, "checkable", "\t\t%1s.setTristate(%2s);\n", sb, nodeMap);
        if(!(prop = Utils.check("check-state", nodeMap)).isEmpty()){
            sb.append(String.format("\t\t%1s.setCheckState(Qt.CheckState.", n));
            switch(prop){
                case "unchecked": sb.append("Unchecked);\n"); break;
                case "partially-checked": sb.append("PartiallyChecked);\n"); break;
                case "checked": sb.append("Checked);\n"); break;
            }
        }
        Utils.tryBoolean(n, "default", "\t\t%1s.setDefaultUp(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "native-menubar", "\t\t%1s.setNativeMenuBar(%2s);\n", sb, nodeMap);
        styles.AbstractButton(n, sb, nodeMap);
        functions.MakeFunc("\t\t" + n + ".stateChanged.connect(", Utils.check("on - state - change", nodeMap), sb, nodeMap);
        functions.onAbstractButtonFunctions(n, sb, nodeMap);
        addChild(layoutName, layout, "widget", n, nodeMap);
    }

    private void ColumnView(String name, Node node){
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        String n = Utils.tryEmpty("name", name, nodeMap);
        events.Events(file, n, name, "", sb, nodeMap);
        Utils.tryBoolean(n, "resize-grips-visible", "\t\t%1s.setResizeGripsVisible(%2s);\n", sb, nodeMap);
        styles.AbstractItemView(n, sb, nodeMap);
        functions.MakeFunc("\t\t" + n + ".updatePreviewWidget.connect(", "on-preview-update", sb, nodeMap);
        functions.onAbstractItemViewFunctions(n, sb, nodeMap);
        addChild(layoutName, layout, "widget", n, nodeMap);
    }

    private void MenuBar(String name, Node node){
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        String n = Utils.tryEmpty("name", name, nodeMap);
        styles.Widget(n, sb, nodeMap);
        functions.MakeFunc("\t\t" + n + ".hovered.connect(", "on-hover", sb, nodeMap);
        functions.MakeFunc("\t\t" + n + ".triggered.connect(", "on-trigger", sb, nodeMap);
        functions.onWidgetFunctions(n, sb, nodeMap);
        addChild(layoutName, layout, "menubar", n, nodeMap);
    }

    private void Menu(String name, Node node){
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        String n = Utils.tryEmpty("name", name, nodeMap);

        styles.Widget(n, sb, nodeMap);
        functions.MakeFunc("\t\t" + n + ".aboutToHide.connect(", "on-hide", sb, nodeMap);
        functions.MakeFunc("\t\t" + n + ".aboutToShow.connect(", "on-show", sb, nodeMap);
        functions.MakeFunc("\t\t" + n + ".hovered.connect(", "on-hover", sb, nodeMap);
        functions.MakeFunc("\t\t" + n + ".triggered.connect(", "on-trigger", sb, nodeMap);
        functions.onWidgetFunctions(n, sb, nodeMap);
        addChild(layoutName, layout, "menubar", n, nodeMap);
    }

    private void Grid(String name, Node node) {
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        String n = Utils.tryEmpty("name", name, nodeMap);
        events.Events(file, n, name, "", sb, nodeMap);
        addChild(layoutName, layout, "layout", n, nodeMap);
    }
}
