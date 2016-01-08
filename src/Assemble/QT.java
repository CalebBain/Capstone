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
        QWidget window = new Window(Window);
        components.put("window", window);
        NodeList nodeList = Window.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            QWidget component = null;
            switch(node.getNodeName()){
                case "button": component = new Button(findParent(node.getParentNode()), node); break;
                case "number": component = new Number(findParent(node.getParentNode()), node); break;
                case "slider": component = new Slider(findParent(node.getParentNode()), node); break;
            }
            components.put(node.getNodeName(), component);
            names.put(check(node.getAttributes(), "name"), component);
            ids.put(check(node.getAttributes(), "id"), component);
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
        try{
            Node word = nodeMap.getNamedItem(keyword);
            return (word != null) ? word.getNodeValue() : "";
        }catch (NullPointerException e){
            return "";
        }
    }

    public static QWidget findComponent(String component){
        if(ids.containsKey(component)) return ids.get(component);
        if(names.containsKey(component)) return names.get(component);
        if(components.containsKey(component)) return components.get(component);
        return components.get("window");
    }
}
