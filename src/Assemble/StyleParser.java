package Assemble;

import StyleComponents.Style;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/9/2016.
 */
public class StyleParser {

    public StyleParser() {

    }

    public Map<String, Style> Parse(Node style) {
        Map<String, Style> styles = new HashMap<>();
        NamedNodeMap nodeMap = style.getAttributes();
        Node Link = nodeMap.getNamedItem("link");
        if (Link != null) {
            String[] links = Link.getNodeValue().split(" ");
            for (String link : links) {
                String sheet = "";
                try (BufferedReader br = new BufferedReader(new FileReader(link))) {
                    String sCurrentLine;
                    while ((sCurrentLine = br.readLine()) != null) sheet += sCurrentLine;
                } catch (IOException e) {
                }
                styles.putAll(GetContent(sheet));
            }
        }
        styles.putAll(GetContent(style.getTextContent()));
        return styles;
    }

    private Map<String, Style> GetContent(String s) {
        Map<String, Style> tokens = new HashMap<>();
        if (!s.isEmpty()) {
            s = s.replaceAll("\\s+", " ").replaceAll(" } ", "}").replaceAll(": ", ":");
            s = s.substring(1, s.length() - 2);
            String[] commands = s.split("}");
            for (String command : commands) {
                String[] parts = command.split(" \\{ ");
                String[] names = parts[0].split(", ");
                for (String name : names) {
                    Style style = null;
                    boolean spec = (name.startsWith("."));
                    name = name.replaceAll(".", "");
                    if (name.contains("::")) {
                        String[] temp = name.split("::");
                        if (temp[1].contains(":")) {
                            String[] q = name.split(":");
                            List<String> t = new ArrayList<>();
                            for (int i = 1; i < q.length; i++) t.add(q[i]);
                            style = new Style(temp[0], spec);
                            style.setNameAttributes(t);
                            style.setSubControl(temp[0]);
                        }
                    } else if (name.contains(":")) {
                        String[] temp = name.split(":");
                        List<String> t = new ArrayList<>();
                        for (int i = 1; i < temp.length; i++) t.add(temp[i]);
                        style = new Style(temp[0], spec);
                        style.setNameAttributes(t);
                    } else style = new Style(name, spec);
                    parts = parts[1].split(" ");
                    for (String part : parts) {
                        part.replaceAll(";", "");
                        String[] params = part.split(":");
                        style.addAttribute(params[0], params[1]);
                    }
                    tokens.put(name, style);
                }
            }
        }
        return tokens;
    }
}
