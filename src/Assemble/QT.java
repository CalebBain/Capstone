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

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public final class QT extends QApplication {
    public static List<Component> components = new ArrayList<>();
    public static Map<String, Style> styles = new HashMap<>();
    private StringBuilder sb = new StringBuilder();
    private QWidget window;

    public QT(Node window, Map<String, Style> styles, String[] args) {
        super(args);
        this.styles = styles;
        CompileElements(window);
    }

    public static QObject findComponent(String component) {
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
        window = new Window(Window);
        components.add((Component) window);
        sb.append(((Component) window).setStyle());
        window.setStyleSheet(nodeLoop(nodeList, (Component) window));
        window.show();
        this.exec();
    }

    private QObject elementsSwitch(String name, Component parent, Node node) {
        QObject component = null;
        switch (name) {
            case "button":
                switch (check(node.getAttributes(), "type")) {
                    case "radio":
                        component = new Radio(node);
                        break;
                    case "check-box":
                        component = new Checkbox(node, false);
                        break;
                    case "tri-state":
                        component = new Checkbox(node, true);
                        break;
                    default:
                        component = new Button(node);
                }
                components.add((Component) component);
                sb.append(((Component) component).setStyle());
                if (parent != null) parent.addChild(component, node);
                break;
            case "number":
                component = new Number(node);
                components.add((Component) component);
                sb.append(((Component) component).setStyle());
                if (parent != null) parent.addChild(component, node);
                break;
            case "slider":
                component = new Slider(node);
                components.add((Component) component);
                sb.append(((Component) component).setStyle());
                if (parent != null) parent.addChild(component, node);
                break;
            case "grid":
                component = new Grid();
                components.add((Component) component);
                sb.append(((Component) component).setStyle());
                if (parent != null) parent.addChild(component, node);
                break;
        }
        return component;
    }

    public String nodeLoop(NodeList nodeList, Component parent) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            QObject component = elementsSwitch(node.getNodeName(), parent, node);
            if (component instanceof QLayout) nodeLoop(node.getChildNodes(), (Component) component);
        }
        return sb.toString();
    }
}