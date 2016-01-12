package Assemble;

import StyleComponents.Style;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assembler {
    public static void  main(String[] args)
    {
        Assembler a = new Assembler();
        a.input();
    }

    public void input() {
        try {
            File fXmlFile = new File("jaml_files/main.jaml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
            if (doc.hasChildNodes()) parse(doc.getChildNodes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse(NodeList nodeList){
        printNote(nodeList);
        Node node = nodeList.item(0);
        if(node instanceof Element && node.getNodeName().equals("jaml")){
            Element docElement = (Element)node;
            Node style = docElement.getElementsByTagName("style").item(0);
            Map<String, Style> styles = new HashMap<>();
            if(style != null) styles = new StyleParser().Parse(style);
            Node window = docElement.getElementsByTagName("window").item(0);
            if(window != null) ParseFlavor(window, styles);
        }
    }

    private void ParseFlavor(Node window, Map<String, Style> styles){
        NamedNodeMap nodeMap = window.getAttributes();
        String s = nodeMap.getNamedItem("flavor").getNodeValue();
        switch (s.toLowerCase()){
            case "qt": new QT(window, styles, new String[0]); break;
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