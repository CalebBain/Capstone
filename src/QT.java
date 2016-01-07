import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.trolltech.qt.gui.*;
import QtComponents.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class QT extends QApplication{
    private Node Window;
    private Map<String, QWidget> components = new HashMap();
    private Map<String, QWidget> names = new HashMap();
    private Map<String, QWidget> ids = new HashMap();

    public QT(NodeList nodeList, String[] args) {
        super(args);
        Window = nodeList.item(0);
    }

    public void CompileElements(String[] args) {
        QWidget window = Window();
        NodeList nodeList = Window.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            switch(node.getNodeName()){
                case "button":
                    Button(node, findParent(node.getParentNode()));
                    break;
            }
        }
        window.show();
        this.exec();
    }

    private QWidget findParent(Node node){
        NamedNodeMap nodeMap = node.getAttributes();
        String Class = node.getNodeName();
        String name = nodeMap.getNamedItem("name").getNodeValue();
        String id = nodeMap.getNamedItem("id").getNodeValue();
        for(Map.Entry<String, QWidget> Id : ids.entrySet())               if(Id.getKey().equals(Class))        return Id.getValue();
        for(Map.Entry<String, QWidget> Name : names.entrySet())           if(Name.getKey().equals(Class))      return Name.getValue();
        for(Map.Entry<String, QWidget> component : components.entrySet()) if(component.getKey().equals(Class)) return component.getValue();
        return components.get("window");
    }

    public QWidget Window() {
        NamedNodeMap nodeMap = Window.getAttributes();
        Window window = new Window();
        components.put("window", window);
        names.put(nodeMap.getNamedItem("name").getNodeValue(), window);
        ids.put(nodeMap.getNamedItem("id").getNodeValue(), window);
        window.setSize(nodeMap.getNamedItem("width").getNodeValue(), nodeMap.getNamedItem("height").getNodeValue());
        window.setTitle(nodeMap.getNamedItem("title").getNodeValue());
        return window;
    }

    public void Button(Node Button, QWidget parent) {
        NamedNodeMap nodeMap = Button.getAttributes();
        Button button = new Button(parent);
        components.put("button", button);
        names.put(nodeMap.getNamedItem("name").getNodeValue(), button);
        ids.put(nodeMap.getNamedItem("id").getNodeValue(), button);
        button.setSize(nodeMap.getNamedItem("width").getNodeValue(), nodeMap.getNamedItem("height").getNodeValue());
        button.setTextFont(Font(nodeMap));
        button.setButtonText(Button.getTextContent());
        button.setMargins(nodeMap.getNamedItem("margin").getNodeValue());
    }

    public Font Font(NamedNodeMap nodeMap) {
        Font font = new Font();
        font.setFontFamily(nodeMap.getNamedItem("font-family").getNodeValue());
        font.setFontWeight(nodeMap.getNamedItem("font-weight").getNodeValue());
        font.setFontStyle(nodeMap.getNamedItem("font-style").getNodeValue());
        font.setFontSize(nodeMap.getNamedItem("font-size").getNodeValue());
        font.setTextDecoration(nodeMap.getNamedItem("text-decoration").getNodeValue());
        return font;
    }
}
