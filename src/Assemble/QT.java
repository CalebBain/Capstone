package Assemble;

import QtComponents.Number;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.trolltech.qt.gui.*;
import QtComponents.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//1. External style sheet       2. Internal style sheet (in the style section)       3.Inline style (inside an JAML element)

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class QT extends QApplication{
    public static List<Component> components = new ArrayList<>();

    public QT(Node window, String[] args) {
        super(args);
        CompileElements(window);
    }

    public void CompileElements(Node Window) {
        QWidget window = new Window(Window);
        components.add((Component) window);
        NodeList nodeList = Window.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            QWidget component = null;
            //findParent(node.getParentNode());
            switch(node.getNodeName()){
                case "button": component = new Button(window, node); break;
                case "number": component = new Number(window, node); break;
                case "slider": component = new Slider(window, node); break;
            }
            components.add((Component) component);
        }
        window.show();
        this.exec();
    }

    public QWidget findParent(Node node){
        NamedNodeMap nodeMap = node.getAttributes();
        String component = node.getNodeName();
        Node name = nodeMap.getNamedItem("name");
        Node id = nodeMap.getNamedItem("id");
        for (Component com : components){
            String s = com.Id();
            //if() return ids.get(id);
        }



        /*if(names.containsKey(name)) return names.get(name);
        if(components.containsKey(component)) return components.get(component);
        return components.get("window");*/
        return null;
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
        /*if(ids.containsKey(component)) return ids.get(component);
        if(names.containsKey(component)) return names.get(component);
        if(components.containsKey(component)) return components.get(component);
        return components.get("window");*/
        return null;
    }
}
