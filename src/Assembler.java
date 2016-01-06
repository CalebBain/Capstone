import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class Assembler {
    public static void  main(String[] args)
    {
        Assembler a = new Assembler();
        a.input(args);
    }

    public void input(String[] args) {
        try {
            File fXmlFile = new File("jaml_files/main.jaml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
            if (doc.hasChildNodes()) parseFlavor(doc.getChildNodes(), args);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseFlavor(NodeList nodeList, String[] args){
        printNote(nodeList);
        Node node = nodeList.item(0);
        NamedNodeMap nodeMap = node.getAttributes();
        String s = nodeMap.getNamedItem("flavor").getNodeValue();
        switch (s.toLowerCase()){
            case "qt":
                QT app = new QT(nodeList, args);
                app.CompileElements();
                break;
            case "fx": break;
            case"swing": break;
            default: throw new SyntaxException("didn't recognized the language you were trying to use");
        }
    }

    private void printNote(NodeList nodeList) {
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                // get node name and value
                System.out.println("\nNode Name = " + tempNode.getNodeName() + " [OPEN]");
                System.out.println("Node Value = " + tempNode.getTextContent());
                if (tempNode.hasAttributes()) {
                    // get attributes names and values
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node node = nodeMap.item(i);
                        System.out.print("attr name : " + node.getNodeName());
                        System.out.print("\t:\t");
                        System.out.println("attr value : " + node.getNodeValue());
                    }
                }
                if (tempNode.hasChildNodes()) printNote(tempNode.getChildNodes()); // loop again if has child nodes
                System.out.println("Node Name = " + tempNode.getNodeName() + " [CLOSE]");
            }
        }
    }
}