package Compiler.Assemble;

import Compiler.Parsers.SlotParser;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class Assembler {
    public static void main(String[] args) {
        Assembler a = new Assembler();
        a.input(args[0]);
    }

    private void input(String file) {
        try {
            File fXmlFile = new File(file);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            //System.out.println(doc.getDocumentURI());
            //System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
            if (doc.hasChildNodes()){
                NodeList nodeList = doc.getChildNodes();
                //printNote(nodeList);
                //System.out.println("\n\n");
                Node node = nodeList.item(0);
                if (node instanceof Element && node.getNodeName().equals("jaml")) {
                    Element docElement = (Element) node;
                    //Node style = docElement.getElementsByTagName("style").item(0);
                    //Map<String, Style> styles;
                    //if (style != null) styles = new StyleParser().Parse(style);
                    Node methods = docElement.getElementsByTagName("methods").item(0);
                    Map<String, String> methodCalls = new HashMap<>();
                    if (methods != null) methodCalls = new SlotParser().Parse(methods);
                    Node window = docElement.getElementsByTagName("window").item(0);
                    if (window != null) new Compiler().assemble(fXmlFile.getName(), methodCalls, window);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printNote(NodeList nodeList) {
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println("\nNode Name = " + tempNode.getNodeName() + " [OPEN]");
                System.out.println("Node Value = " + tempNode.getTextContent());
                if (tempNode.hasAttributes()) {
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node node = nodeMap.item(i);
                        System.out.print("attr name : " + node.getNodeName());
                        System.out.print("\t:\t");
                        System.out.println("attr value : " + node.getNodeValue());
                    }
                }
                if (tempNode.hasChildNodes()) printNote(tempNode.getChildNodes());
                System.out.println("Node Name = " + tempNode.getNodeName() + " [CLOSE]");
            }
        }
    }
}