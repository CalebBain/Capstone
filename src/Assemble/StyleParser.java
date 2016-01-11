package Assemble;

import StyleComponents.Style;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caleb Bain on 1/9/2016.
 */
public class StyleParser {

    public StyleParser(Node style) {
        List<Style> styles = new ArrayList<>();
        NamedNodeMap nodeMap = style.getAttributes();
        Node link = nodeMap.getNamedItem("link");

        if(link != null){
            String sheet = "";
            try(BufferedReader br = new BufferedReader(new FileReader(link.getNodeValue()))){
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) sheet += sCurrentLine;
            }catch(IOException e){}
            styles.addAll(GetContent(sheet));
        }
        styles.addAll(GetContent(style.getTextContent()));
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
            String name = parts[0];
            Style style = null;
            if(name.contains(":")){
                String[] temp = name.split(":");
                style = new Style(temp[0], temp[1]);
            }else style = new Style(name);
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
