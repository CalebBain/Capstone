package Compiler.Parser;

import Compiler.Utils;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLayout;
import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ComponentParser {
    private InlineStyleParser styles = new InlineStyleParser();
    private FunctionParser functions = new FunctionParser();
    private StringBuilder sb;

    public ComponentParser(StringBuilder sb, Node node) {
        this.sb = sb;
        Window("QMainWindow", node);
        nodeLoop(node);
    }

    public String nodeLoop(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            QObject component = elementsSwitch(n.getNodeName(), n);
            if (component instanceof QLayout) nodeLoop(n);
        }
        return sb.toString();
    }

    public QObject elementsSwitch(String name, Node node){
        QObject component = new QWidget();
        switch (name) {
            case "check-box": CheckBox(name, node); break;
            case "radio": Radio(name, node); break;
            case "button": Button(name, node); break;
            case "number": Number(name, node); break;
            case "slider": Slider(name, node); break;
            case "grid": Grid(name, node);
                component = new QGridLayout(); break;
        }
        return component;
    }

    private void addChild(String name, String layout, String component, String child, NamedNodeMap nodeMap){
        switch (layout) {
            case "window": WindowChild(name, component); break;
            case "grid" : GridChild(name, component, child, nodeMap); break;
        }
    }

    private void WindowChild(String name, String componentType){
        switch (componentType){
            case "layout" : sb.append(String.format("centerWidget.setLayout(%1s);", name)); break;
            case "widget" : sb.append(String.format("%1s.setParent(centerWidget);", name)); break;
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
            default: sb.append(String.format("ColumnView.setPreviewWidget((%1s);", name)); break;
        }
    }

    private void Window(String name, Node node){
        String prop;
        NamedNodeMap nodeMap = node.getAttributes();
        sb.append("QMainWindow window = new QMainWindow();");
        sb.append("QWidget centerWidget = new QWidget();");
        sb.append("window.setCentralWidget(centerWidget);");
        sb.append(String.format("%1s.setWindowTitle(tr(%2s));\n", name, Utils.tryEmpty("title", "JAML Applicaiton", nodeMap)));
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
        String layout = node.getParentNode().getNodeName();
        String layoutName = Utils.check("name", node.getParentNode().getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
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
        addChild(layoutName, layout, "widget", name, nodeMap);
    }

    private void Number(String name, Node node){
        String layout = node.getParentNode().getNodeName();
        String layoutName = Utils.check("name", node.getParentNode().getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        sb.append(String.format("QLCDNumber %1s = new QLCDNumber();", name));
        Utils.tryCapitalize(name, "segment-style", "%1s.setSegmentStyle(SegmentStyle.%2s);\n", sb, nodeMap);
        Utils.tryCapitalize(name, "mode", "%1s.setMode(Mode.%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "small-decimal-point", "%1s.setSmallDecimalPoint(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "digit-count", "%1s.setDigitCount(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "value", "%1s.display(%2s);\n", sb, nodeMap);
        styles.Frame(name, sb, nodeMap);
        functions.MakeFunc(name + ".overflow.connect(", Utils.check("on-overflow", nodeMap), sb, nodeMap);
        functions.onWidgetFunctions(name, sb, nodeMap);
        addChild(layoutName, layout, "widget", name, nodeMap);
    }

    private void Button(String name, Node node){
        String layout = node.getParentNode().getNodeName();
        String layoutName = Utils.check("name", node.getParentNode().getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        sb.append(String.format("QPushButton %1s = new QPushButton();", name));
        Utils.tryBoolean(name, "default", "%1s.setDefault(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "flat", "%1s.setFlat(%2s);\n", sb, nodeMap);
        styles.AbstractButton(name, sb, nodeMap);
        functions.onAbstractButtonFunctions(name, sb, nodeMap);
        addChild(layoutName, layout, "widget", name, nodeMap);
    }

    private void Radio(String name, Node node){
        String layout = node.getParentNode().getNodeName();
        String layoutName = Utils.check("name", node.getParentNode().getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        sb.append(String.format("QRadioButton %1s = new QRadioButton();", name));
        styles.AbstractButton(name, sb, nodeMap);
        functions.onAbstractButtonFunctions(name, sb, nodeMap);
        addChild(layoutName, layout, "widget", name, nodeMap);
    }

    private void CheckBox(String name, Node node){
        String prop;
        String layout = node.getParentNode().getNodeName();
        String layoutName = Utils.check("name", node.getParentNode().getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
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
        addChild(layoutName, layout, "widget", name, nodeMap);
    }

    private void ColumnView(String name, Node node){
        String layout = node.getParentNode().getNodeName();
        String layoutName = Utils.check("name", node.getParentNode().getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        Utils.tryBoolean(name, "resize-grips-visible", "%1s.setResizeGripsVisible(%2s);\n", sb, nodeMap);
        styles.AbstractItemView(name, sb, nodeMap);
        functions.MakeFunc(name + ".updatePreviewWidget.connect(", "on-preview-update", sb, nodeMap);
        functions.onAbstractItemViewFunctions(name, sb, nodeMap);
        addChild(layoutName, layout, "widget", name, nodeMap);
    }

    private void Grid(String name, Node node) {
        sb.append(String.format("QGridLayout %1s = new QGridLayout();", name));
    }
}
