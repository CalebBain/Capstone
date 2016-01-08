package Assemble;

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
                case "button":
                    Button(node, findParent(node.getParentNode()));
                    break;
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

    public QWidget Window() {
        NamedNodeMap nodeMap = Window.getAttributes();
        Window window = new Window();
        components.put("window", window);
        Node name = nodeMap.getNamedItem("name");
        Node id = nodeMap.getNamedItem("id");
        names.put((name != null) ? name.getNodeValue() : "", window);
        ids.put((id != null) ? id.getNodeValue() : "", window);
        window.setSize(nodeMap.getNamedItem("width").getNodeValue(), nodeMap.getNamedItem("height").getNodeValue());
        window.setTitle(nodeMap.getNamedItem("title").getNodeValue());
        return window;
    }

    public void Button(Node Button, QWidget parent) {
        NamedNodeMap nodeMap = Button.getAttributes();
        Button button = new Button(parent);
        components.put("button", button);
        Node name = nodeMap.getNamedItem("name");
        Node id = nodeMap.getNamedItem("id");
        names.put((name != null) ? name.getNodeValue() : "", button);
        ids.put((id != null) ? id.getNodeValue() : "", button);
        button.setSize(nodeMap.getNamedItem("width").getNodeValue(), nodeMap.getNamedItem("height").getNodeValue());
        button.setTextFont(Font(nodeMap));
        button.setButtonText(Button.getTextContent());
        button.setMargins(nodeMap.getNamedItem("margin").getNodeValue());
        button.onClick(nodeMap.getNamedItem("onclick").getNodeValue());
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
