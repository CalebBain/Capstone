import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.trolltech.qt.gui.*;

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
        window.resize(((w != null) ? Integer.parseInt(w.getNodeValue()) : 400), ((h != null) ? Integer.parseInt(h.getNodeValue()) : 200));
        return window;
    }

    public void Button(Node Button, QWidget window) {
        QPushButton button = new QPushButton(window);
        NamedNodeMap nodeMap = Button.getAttributes();
        String h = nodeMap.getNamedItem("height").getNodeValue();
        String w = nodeMap.getNamedItem("width").getNodeValue();
        button.setText(tr(Button.getTextContent()));
        button.resize(((!w.isEmpty())? Integer.parseInt(w) : 20), ((!h.isEmpty())? Integer.parseInt(h) : 10));
    }
}
