package Assemble;

import StyleComponents.Style;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Caleb Bain on 1/9/2016.
 */
public class StyleParser {

    public StyleParser(Node style) {
        Map<String, Character> tokens = GetContent(style.getTextContent());
    }

    private Map<String, Character> GetContent(String s){
        char currentState = 'a';
        char[][] states = {{'a','b','~','~','e','~'}, {'a','b','c','~','e','~'},
                           {'c','c','~','d','~','~'}, {'a','b','~','~','~','f'},
                           {'a','b','~','~','~','f'}, {'a','b','~','~','~','~'}};
        Map<String, Character> tokens = new HashMap<>();
        char[] list = s.toCharArray();
        int count = 0;
        while(count++ < s.length()){
            String token = "";
            for(;count < s.length(); count++){
                char c = list[count];
                int j = 0, i = currentState - 97;
                String t = c + "";
                if(c == ' ') j = 0;
                else if(t.matches("[A-z0-9\\-]")) j = 1;
                else if(c == ':') j = 2;
                else if(c == ';') j = 3;
                else if(c == '{') j = 4;
                else if(c == '}') j = 5;
                char state = states[i][j];
                if(state == 'a' || state == 'e') {
                    if(!token.isEmpty()) tokens.put(token, state);
                    currentState = state;
                    break;
                }
                if(c != ';' && c!=' ') token += c;
                currentState = state;
            }

        }
        return tokens;
    }




















    private void ParseStyle(Node style){
        List<Style> styles = new ArrayList<>();
        //NamedNodeMap nodeMap = style.getAttributes();
        //String link = nodeMap.getNamedItem("link").getNodeValue();
        String s = style.getTextContent();

    }


}
