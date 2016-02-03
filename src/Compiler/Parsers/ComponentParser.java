package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
    private final InlineStyleParser styles = new InlineStyleParser();
    private final EventParser events = new EventParser();
    private final ChildParser children = new ChildParser();
    private final FunctionParser functions;
    private final String file;
    private StringBuilder sb;
    private String prop;
    private Map<String, Style> stylesSheet = new HashMap<>();
    private List<String> namedComponents = new ArrayList<>();
    private List<String> components = new ArrayList<>();
    private Map<String, String> methodCalls;

    public ComponentParser(String file, Map<String, String> methodCalls, StringBuilder sb, Node node) {
        this.sb = sb;
        this.methodCalls = methodCalls;
        this.file = file.replaceFirst("\\.jaml", "");
        functions = new FunctionParser();
        Window("window", methodCalls.get("window"), node.getAttributes());
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
            String component =  elementsSwitch(n.getNodeName(), n);
            if (component.equals("layout")) nodeLoop(n);
        }
        return sb.toString();
    }

    public String elementsSwitch(String name, Node node){
        String component = "";
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        String layoutName = (!name.isEmpty()) ? Utils.tryEmpty("name", layout, parent.getAttributes()) : name;
        NamedNodeMap nodeMap = node.getAttributes();
        String methods = methodCalls.get(name);
        switch (name) {
            case "check-box": CheckBox(name, methods, layout, layoutName, nodeMap); break;
            case "radio": Radio(name, methods, layout, layoutName, nodeMap); break;
            case "button": Button(name, methods, node.getTextContent(), layout, layoutName, nodeMap); break;
            case "number": Number(name, methods, layout, layoutName, nodeMap); break;
            case "slider": Slider(name, methods, layout, layoutName, nodeMap); break;
            case "separator": Separator(name, layout, layoutName, nodeMap); break;
            case "column-view": ColumnView(name, methods, layout, layoutName, nodeMap); break;
            case "menubar": MenuBar(name, methods, layout, layoutName, nodeMap);
                component = "layout"; break;
            case "menu": Menu(name, methods, layout, layoutName, nodeMap);
                component = "layout"; break;
            case "action": Action(name, methods, node.getTextContent(), layout, layoutName, nodeMap);
                component = "layout"; break;
            case "grid": Grid(name, methods, layout, layoutName, nodeMap);
                component = "layout"; break;
        }
        return component;
    }

    private void Window(String name, String methods, NamedNodeMap nodeMap){
        sb.append(String.format("QMainWindow %s = new QMainWindow()", name));
        try{
            if(!methods.isEmpty()){
                sb.append(String.format("{\n%s\n};\n", methods));
            }
        }catch (NullPointerException ignored){
        }
        styles.MainWindow(name, stylesSheet, sb, nodeMap);
        functions.MakeFunc(name + ".iconSizeChanged.connect(", Utils.check("on-icon-size-change", nodeMap), sb, nodeMap);
        functions.MakeFunc(name + ".toolButtonStyleChanged.connect(", Utils.check("on-tool-button-style-change", nodeMap), sb, nodeMap);
        functions.onWidgetFunctions(name, sb, nodeMap);
    }

    private String methodName(String name, String methods, String text, NamedNodeMap nodeMap){
        String n = Utils.tryEmpty("name", name, namedComponents, components, nodeMap);
        events.Events(file, n, name, text, methods, sb, nodeMap);
        return name;
    }

    private void Slider(String name, String methods, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = methodName(name, methods, "", nodeMap);
        styles.Slider(n, stylesSheet, sb, nodeMap);
        functions.onAbstractSliderFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
    }

    private void Number(String name, String methods, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = methodName(name, methods, "", nodeMap);
        styles.LCDNumber(n, stylesSheet, sb, nodeMap);
        functions.MakeFunc("" + n + ".overflow.connect(", Utils.check("on - overflow", nodeMap), sb, nodeMap);
        functions.onWidgetFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
    }

    private void Button(String name, String methods, String text, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = methodName(name, methods, text, nodeMap);
        styles.PushButton(n, stylesSheet, sb, nodeMap);
        functions.onAbstractButtonFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
    }

    private void Radio(String name, String methods, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = methodName(name, methods, "", nodeMap);
        styles.RadioButton(n, stylesSheet, sb, nodeMap);
        functions.onAbstractButtonFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
    }

    private void CheckBox(String name, String methods, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = methodName(name, methods, "", nodeMap);
        styles.CheckBox(n, stylesSheet, sb, nodeMap);
        functions.CheckBoxFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
    }

    private void ColumnView(String name, String methods, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = methodName(name, methods, "", nodeMap);
        functions.ColumnViewFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
    }

    private String MenuBar(String name, String methods, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = methodName(name, methods, "", nodeMap);
        styles.MenuBar(n, stylesSheet, sb, nodeMap);
        functions.MenuBarFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "menubar", n, sb, nodeMap);
        return n;
    }

    private String Menu(String name, String methods, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = methodName(name, methods, "", nodeMap);
        styles.Menu(n, stylesSheet, sb, nodeMap);
        functions.MenuFunctions(n, sb, nodeMap);
        children.addChild(layoutName, layout, "menu", n, sb, nodeMap);
        return n;
    }

    private String Action(String name, String methods, String text, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = methodName(name, methods, text, nodeMap);
        styles.Action(n, stylesSheet, sb, nodeMap);
        functions.MakeFunc(n + ".triggered", "method", sb, nodeMap);
        children.addChild(layoutName, layout, "action", n, sb, nodeMap);
        return n;
    }

    private void Separator(String name, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = Utils.tryEmpty("name", name, namedComponents, components, nodeMap);
        children.addChild(layoutName, layout, "separator", n, sb, nodeMap);
    }

    private void Grid(String name, String methods, String layout, String layoutName, NamedNodeMap nodeMap) {
        String n = methodName(name, methods, "", nodeMap);
        Style style = new Style(n, "QGridLayout");
        stylesSheet.put((!n.isEmpty()) ? n : "QGridLayout", style);
        styles.setStyle(style, nodeMap);
        children.addChild(layoutName, layout, "layout", n, sb, nodeMap);
    }
}
