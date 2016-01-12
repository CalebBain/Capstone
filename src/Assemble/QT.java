package Assemble;

import QtComponents.*;
import QtComponents.Number;
import StyleComponents.Style;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// (least to most important)
//  1. External style sheet
//  2. Internal style sheet (in the style section)
//  3.Inline style (inside an JAML element)

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class QT extends QApplication {
    public static List<Component> components = new ArrayList<>();
    public static Map<String, Style> styles = new HashMap<>();


    public QT(Node window, Map<String, Style> styles, String[] args) {
        super(args);
        this.styles = styles;
        CompileElements(window);
    }

    public static QWidget findComponent(String component) {
        for (Component com : components)
            if (com.Name().equals(component) || com.Class().equals(component) || com.Component().equals(component))
                return com.Widgit();
        return components.get(0).Widgit();
    }

    private String check(NamedNodeMap nodeMap, String keyword) {
        try {
            Node word = nodeMap.getNamedItem(keyword);
            return (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException e) {
            return "";
        }
    }

    public void CompileElements(Node Window) {
        QWidget window = new Window(Window);
        components.add((Component) window);
        NodeList nodeList = Window.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            QWidget component = null;
            switch (node.getNodeName()) {
                case "button":
                    String type = check(node.getAttributes(), "type");
                    if (type.equals("radio")) component = new Radio(findParent(node.getParentNode()), node);
                    else if (type.equals("checkbox")) component = new Checkbox(findParent(node.getParentNode()), node, false);
                    else if (type.equals("tri-state")) component = new Checkbox(findParent(node.getParentNode()), node, true);
                    else component = new Button(findParent(node.getParentNode()), node);
                    break;
                case "number":
                    component = new Number(findParent(node.getParentNode()), node);
                    break;
                case "slider":
                    component = new Slider(findParent(node.getParentNode()), node);
                    break;
            }
            components.add((Component) component);
            ((Component) component).setStyle();
        }
        window.show();
        this.exec();
    }

    public QWidget findParent(Node node) {
        NamedNodeMap nodeMap = node.getAttributes();
        String component = node.getNodeName();
        Node Class = nodeMap.getNamedItem("class");
        Node name = nodeMap.getNamedItem("name");
        for (Component com : components)
            if (com.Name().equals(name) || com.Class().equals(Class) || com.Component().equals(component))
                return com.Widgit();
        return components.get(0).Widgit();
    }
}
