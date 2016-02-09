package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

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
    private Map<String, Style> stylesSheet = new HashMap<>();
    private List<String> namedComponents = new ArrayList<>();
    private List<String> components = new ArrayList<>();
    private Map<String, String> methodCalls;
    private final FunctionParser functions;
    private final String file;
    private StringBuilder sb;

    public ComponentParser(String file, Map<String, String> methodCalls, StringBuilder sb, Node node) {
        this.sb = sb;
        this.methodCalls = methodCalls;
        this.file = file.replaceFirst("\\.jaml", "");
        functions = new FunctionParser();
        NamedNodeMap nodeMap = node.getAttributes();
        String name = "window";
        String methods = methodCalls.get("window");
        sb.append(String.format("QMainWindow %s = new QMainWindow()", name));
        try{
            if (!methods.isEmpty()) sb.append(String.format("{\n%s\n}", methods));
        }catch (NullPointerException ignored){
        }
        sb.append(";\n");
        styles.MainWindow(name, stylesSheet, sb, nodeMap);
        functions.WindowFunctions(name, sb, nodeMap);
        nodeLoop("window", node);
    }

    public String StyleSheet(){
        StringBuilder sb = new StringBuilder();
        Collection<Style> temp = stylesSheet.values();
        Style[] styles = temp.toArray(new Style[temp.size()]);
        for(int i = 0; i < styles.length;){
            sb.append(styles[i].toString());
            if(++i < styles.length) sb.append("\\n\"+\n");
        }

        return sb.toString();
    }

    public String nodeLoop(String layoutName, Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            String component =  elementsSwitch(n.getNodeName(), layoutName, n);
            String[] parts = component.split(":");
            if (parts[0].equals("layout")) nodeLoop(parts[1], n);
        }
        return sb.toString();
    }

    public String elementsSwitch(String name, String layoutName, Node node){
        String component = "";
        Node parent = node.getParentNode();
        String layout = parent.getNodeName();
        NamedNodeMap nodeMap = node.getAttributes();
        String methods = methodCalls.get(name);
        String n;
        switch (name) {
            case "label":
                n  = methodName(name, methods, "", nodeMap);
                //styles.CheckBox(n, stylesSheet, sb, nodeMap);
                //functions.CheckBoxFunctions(n, sb, nodeMap);
                children.addChild(layoutName, layout, "layout:" + n, n, sb, nodeMap);
                break;
            case "check-box":
                n  = methodName(name, methods, "", nodeMap);
                styles.CheckBox(n, stylesSheet, sb, nodeMap);
                functions.CheckBoxFunctions(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                break;
            case "radio":
                n  = methodName(name, methods, "", nodeMap);
                styles.RadioButton(n, stylesSheet, sb, nodeMap);
                functions.onAbstractButtonFunctions(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                break;
            case "number":
                n  = methodName(name, methods, "", nodeMap);
                styles.LCDNumber(n, stylesSheet, sb, nodeMap);
                functions.NumberFunctions(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                break;
            case "slider":
                n  = methodName(name, methods, "", nodeMap);
                styles.Slider(n, stylesSheet, sb, nodeMap);
                functions.onAbstractSliderFunctions(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                break;
            case "grid":
                n  = methodName(name, methods, "", nodeMap);
                Style style = new Style(n, "QGridLayout");
                if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QGridLayout", style);
                styles.setStyle(style, nodeMap);
                children.addChild(layoutName, layout, "layout", n, sb, nodeMap);
                component = "layout:" + n;
                break;
            case "menubar":
                n = "menubar";
                events.Events(file, n, name, "", methods, sb, nodeMap);
                styles.MenuBar(n, stylesSheet, sb, nodeMap);
                functions.MenuBarFunctions(n, sb, nodeMap);
                children.addChild(layoutName, layout, "menubar", n, sb, nodeMap);
                component = "layout:" + n;
                break;
            case "menu":
                n  = methodName(name, methods, "", nodeMap);
                styles.Menu(n, stylesSheet, sb, nodeMap);
                functions.MenuFunctions(n, sb, nodeMap);
                children.addChild(layoutName, layout, "menu", n, sb, nodeMap);
                component = "layout:" + n;
                break;
            case "list":
                n = methodName(name, methods, "", nodeMap);
                styles.List(n, stylesSheet, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                break;
            case "button":
                n = methodName(name, methods, node.getTextContent(), nodeMap);
                styles.PushButton(n, stylesSheet, sb, nodeMap);
                functions.onAbstractButtonFunctions(n, sb, nodeMap);
                children.addChild(layoutName, layout, "widget", n, sb, nodeMap);
                break;
            case "action":
                n = Utils.tryEmpty("name", name, namedComponents, components, nodeMap);
                String text = Utils.tryEmpty("text", "action", nodeMap);
                events.ActionEvents(file, n, name, text, methods, sb, nodeMap);
                styles.Action(n, stylesSheet, sb, nodeMap);
                functions.ActionFunctions(n, sb, nodeMap);
                children.addChild(layoutName, layout, "action", n, sb, nodeMap);
                component = "layout:" + n;
                break;
            case "separator":
                n = Utils.tryEmpty("name", name, namedComponents, components, nodeMap);
                children.addChild(layoutName, layout, "separator", n, sb, nodeMap);
                break;

        }
        return component;
    }

    private String methodName(String name, String methods, String text, NamedNodeMap nodeMap){
        String n = Utils.tryEmpty("name", name, namedComponents, components, nodeMap);
        events.Events(file, n, name, text, methods, sb, nodeMap);
        return n;
    }
}
