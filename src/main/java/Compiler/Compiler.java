package Compiler;

import Compiler.Parsers.ComponentParser;
import Compiler.Parsers.SlotParser;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class Compiler {
    public static void main(String[] args) {
        Compiler a = new Compiler();
        a.input(args[0]);
    }

    private void input(String file) {
        try {
            File fXmlFile = new File(file);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            if (doc.hasChildNodes()){
                NodeList nodeList = doc.getChildNodes();
                Node node = nodeList.item(0);
                if (node instanceof Element && node.getNodeName().equals("jaml")) {
                    Element docElement = (Element) node;
                    Node methods = docElement.getElementsByTagName("methods").item(0);
                    Map<String, String> methodCalls = new HashMap<>();
                    if (methods != null) methodCalls = new SlotParser().Parse(methods);
                    Node window = docElement.getElementsByTagName("window").item(0);
                    if (window != null){
                        String Code = new ComponentParser(fXmlFile.getName(), methodCalls, window).toString();
                        System.out.println(Code);
                        new GroovyShell(new Binding()).evaluate(Code);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}