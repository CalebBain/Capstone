package Assemble;

import StyleComponents.Style;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
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
                    String CurrentLine;
                    while ((CurrentLine = br.readLine()) != null) sheet += CurrentLine;
                } catch (IOException e) { }
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
                    Style style = new Style(name, name.startsWith("."));
                    style.setAttributes(parts[1]);
                    tokens.put(style.getFullName(), style);
                }
            }
        }
        return tokens;
    }
}
