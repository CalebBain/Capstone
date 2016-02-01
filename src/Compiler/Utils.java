package Compiler;

import Compiler.Parsers.Style;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Utils {

    private static Map<String, String> components = new HashMap<String, String>() {{
        put("button", "QPushButton");
        put("window", "QMainWindow");
        put("number", "QLCDNumber");
        put("radio", "QRadioButton");
        put("slider", "QSlider");
        put("check-box", "QCheckBox");
        put("tri-state", "QTriState");
        put("menu-bar", "QMenuBar");
        put("grid", "QGridLayout");
        put("menubar", "QMenuBar");
        put("menu", "QMenu");
        put("action", "QAction");
    }};

    public static boolean tryValue(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static String setName(String name) {
        for (Map.Entry<String, String> entry : components.entrySet())
            if (name.startsWith(entry.getKey()))
                name = name.replaceFirst(entry.getKey(), entry.getValue());
        return name;
    }

    public static boolean exists(String keyword, NamedNodeMap nodeMap){
        try {
            Node word = nodeMap.getNamedItem(keyword);
            word.getNodeValue();
            return true;
        } catch (NullPointerException ignored) {
            return false;
        }
    }

    public static String check(String keyword, NamedNodeMap nodeMap) {
        try {
            Node word = nodeMap.getNamedItem(keyword);
            return (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException ignored) {
            return "";
        }
    }

    public static boolean check(String keyword, String check, NamedNodeMap nodeMap) {
        try {
            Node word = nodeMap.getNamedItem(keyword);
            return ((word != null) ? word.getNodeValue() : "").equals(check);
        } catch (NullPointerException e) {
            return false;
        }
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

    public static boolean tryBoolean(String value){
        return (value.equals("true") || value.equals("false"));
    }

    public static void tryCheck(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (!(p = Utils.check(prop, nodeMap)).isEmpty()) sb.append(String.format(command, name, p));
    }

    public static void tryCheck(String name, String prop, String command, String replacement, StringBuilder sb,
                                NamedNodeMap nodeMap){
        String p;
        if (!(p = Utils.check(prop, nodeMap)).isEmpty()) sb.append(String.format(command, name, p));
        else sb.append(String.format(command, name, replacement));
    }

    public static void tryValue(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (Utils.tryValue(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
    }

    public static int tryValue(String prop, String command, int replacement, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        int result = replacement;
        if (Utils.tryValue(p = Utils.check(prop, nodeMap))){
            result = Integer.parseInt(p);
            sb.append(String.format(command, p));
        } else sb.append(String.format(command, replacement));
        return result;
    }

    public static void tryBoolean(String name, String prop, String child, String command, StringBuilder sb,
                                  NamedNodeMap nodeMap){
        if (Utils.tryBoolean(Utils.check(prop, nodeMap))) sb.append(String.format(command, name, child));
    }

    public static void tryBoolean(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (Utils.tryBoolean(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
    }

    public static void formatAppend(String command, StringBuilder sb, String... props){
        sb.append(String.format(command, props));
    }

    public static String tryEmpty(String prop, String replacement, NamedNodeMap nodeMap){
        String p;
        return (!(p = Utils.check(prop, nodeMap)).isEmpty()) ? p : replacement;
    }

    public static void tryEmptyAppend(String name, String value, String command, StringBuilder sb){
        if(!value.isEmpty()) sb.append(String.format(command, name, value));
    }

    public static String tryEmpty(String prop, String replacement, List<String> namedComponents,
                                  List<String> comps, NamedNodeMap nodeMap){
        String p;
        if((p = Utils.check(prop, nodeMap)).isEmpty()) p = replacement;
        if(!components.containsKey(p)) namedComponents.add(p);
        else if(!comps.contains(p)) comps.add(p);
        else{
            int count = 0;
            for(String comp : comps) if(comp.startsWith(p)) count++;
            p += count;
        }
        return p;
    }

    public static void tryCapitalize(String name, String prop, String command, StringBuilder sb,
                                     NamedNodeMap nodeMap){
        String p;
        if(!(p = Utils.check(prop, nodeMap)).isEmpty()) formatAppend(command, sb, name, capitalize(p));
    }

    public static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
