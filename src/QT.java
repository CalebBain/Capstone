import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.trolltech.qt.gui.*;

import java.util.List;

public class QT extends QApplication{
    private Node Window;

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
                case "button": Button(node, window); break;
            }
        }
        window.show();
        this.exec();
    }

    public QWidget Window() {
        QWidget window = new QWidget();
        NamedNodeMap nodeMap = Window.getAttributes();
        String title = nodeMap.getNamedItem("title").getNodeValue();
        Node h = nodeMap.getNamedItem("height");
        Node w = nodeMap.getNamedItem("width");
        window.setWindowTitle(tr((!title.isEmpty())? title : "JAML Applicaiton"));
        window.resize(((w != null) ? Integer.parseInt(w.getNodeValue()) : 400),
                ((h != null) ? Integer.parseInt(h.getNodeValue()) : 200));
        return window;
    }

    public void Button(Node Button, QWidget window) {
        QPushButton button = new QPushButton(window);
        NamedNodeMap nodeMap = Button.getAttributes();
        String h = nodeMap.getNamedItem("height").getNodeValue();
        String w = nodeMap.getNamedItem("width").getNodeValue();
        button.setText(tr(Button.getTextContent()));
        button.resize(((!w.isEmpty())? Integer.parseInt(w) : 20), ((!h.isEmpty())? Integer.parseInt(h) : 10));
        button.setFont(Font(nodeMap.getNamedItem("font-family").getNodeValue(),
                nodeMap.getNamedItem("font-size").getNodeValue(), nodeMap.getNamedItem("font-style").getNodeValue(),
                nodeMap.getNamedItem("font-weight").getNodeValue()));
    }

    public QFont Font(String family, String size, String style, String weight) {
        QFont font = new QFont();
        QFontDatabase database = new QFontDatabase();
        List<String> families = database.families();
        List<Integer> sizes = database.pointSizes(family);
        if(families.contains(family)) font.setFamily(family);
        try {
            int Size = Integer.parseInt(size);
            if(sizes.contains(Size)) font.setPixelSize(Size);
        }catch (NumberFormatException nfe){}
        QFont.Style Style = QFont.Style.StyleNormal;
        switch(style) {
            case "italic":
                Style = QFont.Style.StyleItalic;
                break;
            case "oblique":
                Style = QFont.Style.StyleOblique;
                break;
        }
        font.setStyle(Style);
        font.setWeight(bold(weight));
        return font;
    }

    public int bold(String keyword){
        int weight = 50;
        try {
            weight = Integer.parseInt(keyword);
        }catch (NumberFormatException nfe){
            switch(keyword){
                case "lighter":     weight = 15; break;
                case "light":       weight = 25; break;
                case "normal":      weight = 50; break;
                case "bold":        weight = 75; break;
                case "bolder":      weight = 85; break;
                default:            weight = 50;
            }
        }
        return weight;
    }
}
