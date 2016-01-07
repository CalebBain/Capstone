import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.trolltech.qt.gui.*;
import QtComponents.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class QT extends QApplication{
    private Node Window;
    private List<QWidget> components = new ArrayList<>();

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
                    Button(node, node.getParentNode().getNodeName());
                    break;
            }
        }
        window.show();
        this.exec();
    }

    public QWidget Window() {
        NamedNodeMap nodeMap = Window.getAttributes();
        Window window = new Window(nodeMap.getNamedItem("name").getNodeValue(), nodeMap.getNamedItem("id").getNodeValue());
        window.setSize(nodeMap.getNamedItem("width").getNodeValue(), nodeMap.getNamedItem("height").getNodeValue());
        window.setTitle(nodeMap.getNamedItem("title").getNodeValue());
        return window;
    }

    public void Button(Node Button, QWidget parent) {
        NamedNodeMap nodeMap = Button.getAttributes();
        Button button = new Button(parent, nodeMap.getNamedItem("name").getNodeValue(), nodeMap.getNamedItem("id").getNodeValue());
        button.setSize(nodeMap.getNamedItem("width").getNodeValue(), nodeMap.getNamedItem("height").getNodeValue());
        button.setTextFont(Font(nodeMap));
        button.setButtonText(Button.getTextContent());
        button.setMargins(nodeMap.getNamedItem("margin").getNodeValue());
    }

    public Font Font(NamedNodeMap nodeMap){
        Font font = new Font();
        font.setFontFamily(nodeMap.getNamedItem("font-family").getNodeValue());
        font.setFontWeight(nodeMap.getNamedItem("font-weight").getNodeValue());
        font.setFontStyle(nodeMap.getNamedItem("font-style").getNodeValue());
        font.setFontSize(nodeMap.getNamedItem("font-size").getNodeValue());
        font.setTextDecoration(nodeMap.getNamedItem("text-decoration").getNodeValue());
        return font;
    }
}
