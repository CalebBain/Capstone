package Assemble;

import StyleComponents.Style;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caleb Bain on 1/9/2016.
 */
public class StyleParser {

    public StyleParser(Node style) {
        List<Style> styles = GetContent(style.getTextContent());
    }

    private List<Style> GetContent(String s) {
        List<Style> tokens = new ArrayList<>();
        s = s.replaceAll("\\s+"," ");
        s = s.replaceAll("} ","}|");
        s = s.replaceAll(": ",":");
        s = s.substring(1, s.length()-2);
        String[] commands = s.split(" }\\|");
        for(String command : commands){
            String[] parts = command.split(" \\{ ");
            Style style = new Style(parts[0]);
            parts = parts[1].split(" ");
            for(String part : parts){
                part.replaceAll(";", "");
                String[] params = part.split(":");
                style.addAttrabute(params[0], params[1]);
            }
            tokens.add(style);
        }
        return tokens;
    }


    private void ParseStyle(Node style) {
        List<Style> styles = new ArrayList<>();
        //NamedNodeMap nodeMap = style.getAttributes();
        //String link = nodeMap.getNamedItem("link").getNodeValue();
        String s = style.getTextContent();

    }


}
