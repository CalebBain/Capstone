package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

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

public final class ComponentParser {
    private InlineStyleParser styles = new InlineStyleParser();
    private FunctionParser functions = new FunctionParser();
    private EventParser events = new EventParser();
    private ChildParser children = new ChildParser();
    private StringBuilder sb;
    private String file;
    private String prop;
    private Map<String, Style> stylesSheet = new HashMap<>();

    public ComponentParser(String file, String name, StringBuilder sb, Node node) {
        this.sb = sb;
        this.file = file;
        Window(name, "QMainWindow", node.getAttributes());
        nodeLoop(node);
    }

    public String StyleSheet(){
        StringBuilder sb = new StringBuilder();
        for(Style style : stylesSheet.values()) sb.append(style.toString());
        return sb.toString();
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
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = Utils.tryEmpty("name", layout, parent.getAttributes());
        NamedNodeMap nodeMap = node.getAttributes();
        switch (name) {
            case "check-box": CheckBox(name, layout, layoutName, nodeMap); break;
            case "radio": Radio(name, layout, layoutName, nodeMap); break;
            case "button": Button(name, node.getTextContent(), layout, layoutName, nodeMap); break;
            case "number": Number(name, layout, layoutName, nodeMap); break;
            case "slider": Slider(name, layout, layoutName, nodeMap); break;
            case "menubar": MenuBar(name, layout, layoutName, nodeMap);
                component = "layout"; break;
            case "menu": Menu(name, layout, layoutName, nodeMap);
                component = "layout"; break;
            case "action": Action(name, layout, layoutName, nodeMap);
                component = "layout"; break;
            case "separator": Separator(name, layout, layoutName, nodeMap); break;
            case "grid": Grid(name, layout, layoutName, nodeMap);
                component = "layout"; break;
        }
        return component;
    }

    private void Window(String app, String name, NamedNodeMap nodeMap){
        String n = Utils.tryEmpty("name", name, nodeMap);
        Style style = new Style(n, "QSlider");
        stylesSheet.put((!n.isEmpty()) ? n : "QSlider", style);
        styles.setStyle(style, nodeMap);
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

    private void Slider(String name, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = Utils.tryEmpty("name", name, nodeMap);
        Style style = new Style(n, "QSlider");
        events.Events(file, n, name, "", sb, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QSlider", style);
        styles.setStyle(style, nodeMap);
        styles.AbstractSlider(n, sb, nodeMap);
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
        functions.onAbstractSliderFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
    }

    private void Number(String name, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = Utils.tryEmpty("name", name, nodeMap);
        Style style = new Style(n, "QLCDNumber");
        events.Events(file, n, name, "", sb, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QLCDNumber", style);
        styles.setStyle(style, nodeMap);
        styles.Frame(n, sb, nodeMap);
        Utils.tryCapitalize(n, "segment-style", "\t\t%1s.setSegmentStyle(QLCDNumber.SegmentStyle.%2s);\n", sb, nodeMap);
        Utils.tryCapitalize(n, "mode", "\t\t%1s.setMode(QLCDNumber.Mode.%2s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "small-decimal-point", "\t\t%1s.setSmallDecimalPoint(%2s);\n", sb, nodeMap);
        Utils.tryValue(n, "digit-count", "\t\t%1s.setDigitCount(%2s);\n", sb, nodeMap);
        Utils.tryValue(n, "value", "\t\t%1s.display(%2s);\n", sb, nodeMap);
        functions.MakeFunc("\t\t" + n + ".overflow.connect(", Utils.check("on - overflow", nodeMap), sb, nodeMap);
        functions.onWidgetFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
    }

    private void Button(String name, String text, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = Utils.tryEmpty("name", name, nodeMap);
        Style style = new Style(n, "QPushButton");
        events.Events(file, n, name, text, sb, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QPushButton", style);
        styles.setStyle(style, nodeMap);
        styles.AbstractButton(n, sb, nodeMap);
        Utils.tryBoolean(n, "default", "\t\t%1s.setDefault(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "flat", "\t\t%1s.setFlat(%2s);\n", sb, nodeMap);
        functions.onAbstractButtonFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
    }

    private void Radio(String name, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = Utils.tryEmpty("name", name, nodeMap);
        Style style = new Style(n, "QRadioButton");
        events.Events(file, n, name, "", sb, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QRadioButton", style);
        styles.setStyle(style, nodeMap);
        styles.AbstractButton(n, sb, nodeMap);
        functions.onAbstractButtonFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
    }

    private void CheckBox(String name, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = Utils.tryEmpty("name", name, nodeMap);
        Style style = new Style(n, "QCheckBox");
        events.Events(file, n, name, "", sb, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QCheckBox", style);
        styles.setStyle(style, nodeMap);
        styles.AbstractButton(n, sb, nodeMap);
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
        functions.MakeFunc(n + ".stateChanged", Utils.check("on-state-change", nodeMap), sb, nodeMap);
        functions.onAbstractButtonFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
    }

    private void ColumnView(String name, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = Utils.tryEmpty("name", name, nodeMap);
        Style style = new Style(n, "QColumnView");
        events.Events(file, n, name, "", sb, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QColumnView", style);
        styles.setStyle(style, nodeMap);
        styles.AbstractItemView(n, sb, nodeMap);
        Utils.tryBoolean(n, "resize-grips-visible", "\t\t%1s.setResizeGripsVisible(%2s);\n", sb, nodeMap);
        functions.MakeFunc(n + ".updatePreviewWidget", "on-preview-update", sb, nodeMap);
        functions.onAbstractItemViewFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
    }

    private void MenuBar(String name, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = Utils.tryEmpty("name", name, nodeMap);
        Style style = new Style(n, "QMenuBar");
        events.Events(file, n, name, "", sb, nodeMap);
        stylesSheet.put((!n.isEmpty())? n: "QMenuBar", style);
        styles.setStyle(style, nodeMap);
        styles.Widget(n, sb, nodeMap);
        functions.MakeFunc(n + ".hovered", "on-hover", sb, nodeMap);
        functions.MakeFunc(n + ".triggered", "on-trigger", sb, nodeMap);
        functions.onWidgetFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "menubar", n, sb, nodeMap);
    }

    private void Menu(String name, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = Utils.tryEmpty("name", name, nodeMap);
        Style style = new Style(n, "QMenu");
        events.Events(file, n, name, "", sb, nodeMap);
        stylesSheet.put((!n.isEmpty())? n: "QMenu", style);
        styles.setStyle(style, nodeMap);
        Utils.tryCheck(name, "icon", "\t\t%s.setIcon(%s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "tear-off", "\t\t%s.setTearOffEnabled(%s);\n", sb, nodeMap);
        Utils.tryCheck(name, "title", "\t\t%s.setTitle(\"%s\");\n", "menu", sb, nodeMap);
        styles.Widget(n, sb, nodeMap);
        functions.MakeFunc(n + ".aboutToHide", "on-hide", sb, nodeMap);
        functions.MakeFunc(n + ".aboutToShow", "on-show", sb, nodeMap);
        functions.MakeFunc(n + ".hovered", "on-hover", sb, nodeMap);
        functions.MakeFunc(n + ".triggered", "on-trigger", sb, nodeMap);
        functions.onWidgetFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "menu", n, sb, nodeMap);
    }

    private void Action(String name, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = Utils.tryEmpty("name", name, nodeMap);
        Style style = new Style(n, "QAction");
        events.Events(file, n, name, "", sb, nodeMap);
        stylesSheet.put((!n.isEmpty())? n: "QAction", style);
        styles.setStyle(style, nodeMap);
        Utils.tryBoolean(name, "repeatable", "%s.setAutoRepeat(%s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "checkable", "%s.setCheckable(%s);\n", sb, nodeMap);
        Utils.tryCheck(name, "icon", "%s.setIcon(%s);\n", sb, nodeMap);
        Utils.tryCheck(name, "icon-Text", "%s.setIconText(%s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "icon-visible", "%s.setIconVisibleMenu(%s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "is-separator", "%s.setSeparator(%s);\n", sb, nodeMap);
        if(!(prop = Utils.check("shortcut-context", nodeMap)).isEmpty()){
            sb.append(String.format("\t\t%1s.setShortcutContext(Qt.ShortcutContext.", n));
            switch(prop){
                case "widget": sb.append("WidgetShortcut);\n"); break;
                case "Widget-children": sb.append("WidgetWithChildrenShortcut);\n"); break;
                case "Window": sb.append("WindowShortcut);\n"); break;
                case "Application": sb.append("ApplicationShortcut);\n"); break;
            }
        }
        Utils.tryCheck(name, "status-tip", "%s.setStatusTip(%s);\n", sb, nodeMap);
        Utils.tryCheck(name, "text", "%s.setText(%s);\n", sb, nodeMap);
        Utils.tryCheck(name, "tool-tip", "%s.setToolTip(%s);\n", sb, nodeMap);
        Utils.tryCheck(name, "whats-this", "%s.setWhatsThis(%s);\n", sb, nodeMap);
        children.addChild(layoutName, layout, "action", n, sb, nodeMap);
    }

    private void Separator(String name, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = Utils.tryEmpty("name", name, nodeMap);
        children.addChild(layoutName, layout, "separator", n, sb, nodeMap);
    }

    private void Grid(String name, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = Utils.tryEmpty("name", name, nodeMap);
        Style style = new Style(n, "QGridLayout");
        events.Events(file, n, name, "", sb, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QGridLayout", style);
        styles.setStyle(style, nodeMap);
        children.addChild(layoutName, layout, "layout", n, sb, nodeMap);
    }
}
