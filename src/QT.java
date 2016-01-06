import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.trolltech.qt.gui.*;

public class QT extends QApplication{
    private NodeList nodeList;

    public QT(NodeList nodeList, String[] args) {
        super(args);
        this.nodeList = nodeList;
    }

    public void CompileElements() {

        Node window = nodeList.item(0);
        Window(window);
        this.exec();
    }

    public void Window(Node Window) {
        QWidget window = new QWidget();
        NamedNodeMap nodeMap = Window.getAttributes();
        String title = nodeMap.getNamedItem("title").getNodeValue();
        Node h = nodeMap.getNamedItem("height");
        Node w = nodeMap.getNamedItem("width");
        window.setWindowTitle(tr((!title.isEmpty())? title : "JAML Applicaiton"));
        window.resize(((w != null) ? Integer.parseInt(w.getNodeValue()) : 400), ((h != null) ? Integer.parseInt(h.getNodeValue()) : 200));
        window.show();
    }

    public void Button(Node Button) {
        QPushButton button = new QPushButton();
        NamedNodeMap nodeMap = Button.getAttributes();
        String h = nodeMap.getNamedItem("height").getNodeValue();
        String w = nodeMap.getNamedItem("width").getNodeValue();
        button.setText(tr(Button.getTextContent()));
        button.resize(((!h.isEmpty())? Integer.parseInt(h) : 10), ((!w.isEmpty())? Integer.parseInt(w) : 20));
    }
}
