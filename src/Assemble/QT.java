package Assemble;

import QtComponents.Number;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.trolltech.qt.gui.*;
import QtComponents.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class QT extends QApplication{
    private Node Window;
    public static Map<String, QWidget> components = new HashMap();
    public static Map<String, QWidget> names = new HashMap();
    public static Map<String, QWidget> ids = new HashMap();

    public QT(NodeList nodeList, String[] args) {
        super(args);
        Window = nodeList.item(0);
    }

    public void CompileElements() {
        QWidget window = Window();
        NodeList nodeList = Window.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            switch(node.getNodeName()){
                case "button": Button(node, findParent(node.getParentNode())); break;
                case "number": Number(node, findParent(node.getParentNode())); break;
            }
        }
        window.show();
        this.exec();
    }

    public QWidget findParent(Node node){
        NamedNodeMap nodeMap = node.getAttributes();
        String component = node.getNodeName();
        Node name = nodeMap.getNamedItem("name");
        Node id = nodeMap.getNamedItem("id");
        if(ids.containsKey(id)) return ids.get(id);
        if(names.containsKey(name)) return names.get(name);
        if(components.containsKey(component)) return components.get(component);
        return components.get("window");
    }

    public String check(NamedNodeMap nodeMap, String keyword){
        Node word = nodeMap.getNamedItem(keyword);
        return (word != null) ? word.getNodeValue() : "";
    }

    public QWidget Window() {
        NamedNodeMap nodeMap = Window.getAttributes();
        Window window = new Window();
        components.put("window", window);
        names.put(check(nodeMap, "name"), window);
        ids.put(check(nodeMap, "id"), window);
        window.setSize(check(nodeMap, "width"), check(nodeMap, "height"));
        window.setTitle(check(nodeMap, "title"));
        return window;
    }

    public void Button(Node Button, QWidget parent) {
        NamedNodeMap nodeMap = Button.getAttributes();
        Button button = new Button(parent);
        components.put("button", button);
        names.put(check(nodeMap, "name"), button);
        ids.put(check(nodeMap, "id"), button);
        button.setSize(check(nodeMap, "width"), check(nodeMap, "height"));
        button.setTextFont(Font(nodeMap));
        button.setButtonText(Button.getTextContent());
        button.setMargins(check(nodeMap, "margin"));
        button.onClick(check(nodeMap, "onclick"));
    }

    public Font Font(NamedNodeMap nodeMap) {
        Font font = new Font();
        font.setFontFamily(check(nodeMap, "font-family"));
        font.setFontWeight(check(nodeMap, "font-weight"));
        font.setFontStyle(check(nodeMap, "font-style"));
        font.setFontSize(check(nodeMap, "font-size"));
        font.setTextDecoration(check(nodeMap, "text-decoration"));
        return font;
    }

    public void Number(Node Number, QWidget parent) {
        NamedNodeMap nodeMap = Number.getAttributes();
        Number number = new Number(parent);
        names.put(check(nodeMap, "name"), number);
        ids.put(check(nodeMap, "id"), number);
        number.setSize(check(nodeMap, "width"), check(nodeMap, "height"));
        number.setDigitCount(check(nodeMap, "digit-count"));
        number.setStyle(check(nodeMap, "style"));
        number.setMargins(check(nodeMap, "margin"));
    }
}
