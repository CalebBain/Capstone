package Compiler;

import Compiler.Parser.Style;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class Utils {

    public static boolean tryValue(String value) {
        boolean result = false;
        try {
            Integer.parseInt(value);
            result = true;
        } catch (NumberFormatException ignored) {
        }
        return result;
    }

    public static String check(String keyword, NamedNodeMap nodeMap) {
        String result = "";
        try {
            Node word = nodeMap.getNamedItem(keyword);
            result = (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException ignored) {
        }
        return result;
    }

    public static boolean check(String keyword, String check, NamedNodeMap nodeMap) {
        String result;
        try {
            Node word = nodeMap.getNamedItem(keyword);
            result = (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException e) {
            result =  "";
        }
        return result.equals(check);
    }

    public static void addAttribute(Style style, NamedNodeMap nodeMap, String attribute) {
        String prop = check(attribute, nodeMap);
        try {
            if (!prop.isEmpty()) style.addAttribute(attribute, prop);
        } catch (NullPointerException ignored) {
        }
    }

    public static void addAttribute(Style style, NamedNodeMap nodeMap, String attribute, String attributeName) {
        String prop = check(attribute, nodeMap);
        if (!prop.isEmpty()) style.addAttribute(attributeName, prop);
    }

    public static boolean tryBoolean(String value, String v1, String v2){
        boolean result = false;
        if(value.equals(v1) || value.equals(v2)) result = true;
        return result;
    }

    public static boolean tryBoolean(String value){
        boolean result = false;
        if(value.equals("true") || value.equals("false")) result = true;
        return result;
    }

    public static void tryCheck(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (Utils.tryValue(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
    }

    public static void tryValue(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (Utils.tryValue(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
    }

    public static void tryBoolean(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (Utils.tryBoolean(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
    }

    public static void tryBoolean(String name, String prop, String v1, String v2, String command, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (Utils.tryBoolean(p = Utils.check(prop, nodeMap), v1, v2)) sb.append(String.format(command, name, p));
    }
}
