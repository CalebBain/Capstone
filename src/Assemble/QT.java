package Assemble;

import QtComponents.*;
import QtComponents.Layouts.Grid;
import QtComponents.Number;
import StyleComponents.Style;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QLayout;
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
public final class QT extends QApplication {
    public static List<Component> components = new ArrayList<>();
    public static Map<String, Style> styles = new HashMap<>();
    private StringBuilder sb = new StringBuilder();

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
        NodeList nodeList = Window.getChildNodes();
        QWidget window = new Window(Window);
        components.add((Component) window);
        sb.append(((Component) window).setStyle());
        window.setStyleSheet(nodeLoop(nodeList, window));
        window.show();
        this.exec();
    }

    private QObject elementsSwitch(String name, QWidget parent, Node node){
        QObject component = null;
        switch (name) {
            case "button":
                String type = check(node.getAttributes(), "type");
                if (type.equals("radio")) component = new Radio(parent, node);
                else if (type.equals("check-box")) component = new Checkbox(parent, node, false);
                else if (type.equals("tri-state")) component = new Checkbox(parent, node, true);
                else component = new Button(parent, node);
                break;
            case "number": component = new Number(parent, node); break;
            case "slider": component = new Slider(parent, node); break;
            case "grid": component = new Grid(parent);

                break;
        }
        if(component != null){
            components.add((Component) component);
            sb.append(((Component) component).setStyle());
        }
        return component;
    }

    public String nodeLoop(NodeList nodeList, QWidget parent){
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            QObject component = elementsSwitch(node.getNodeName(), parent, node);
            if(component instanceof QLayout){

            }
        }
        return sb.toString();
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
