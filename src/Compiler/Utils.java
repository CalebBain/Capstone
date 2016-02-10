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
        put("label", "QLabel");
        put("menu", "QMenu");
        put("action", "QAction");
        put("list", "QListWidget");
    }};

    public static boolean tryDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static boolean tryInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static String setName(String name) {
        String comp = components.get(name.replaceAll("\\d", ""));
        return (comp == null) ? name : comp;
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

    public static void tryCheck(String name, String prop, String command, String replacement, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (!(p = Utils.check(prop, nodeMap)).isEmpty()) sb.append(String.format(command, name, p));
        else sb.append(String.format(command, name, replacement));
    }

    public static void tryValue(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (Utils.tryInteger(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
        else if(Utils.tryDouble(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
    }

    public static void tryValue(String name, String prop, String command, String command1, String command2, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (Utils.tryInteger(p = Utils.check(prop, nodeMap))) sb.append(String.format(command1, name, p));
        else if(Utils.tryDouble(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
        else sb.append(String.format(command2, name, p));

    }

    public static Number tryValue(String prop, String command, Number replacement, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (Utils.tryInteger(p = Utils.check(prop, nodeMap))){
            sb.append(String.format(command, p));
            return Integer.parseInt(p);
        } else if (Utils.tryDouble(p = Utils.check(prop, nodeMap))){
            sb.append(String.format(command, p));
            return Double.parseDouble(p);
        }else sb.append(String.format(command, replacement));
        return replacement;
    }

    public static void tryBoolean(String name, String prop, String child, String command, StringBuilder sb, NamedNodeMap nodeMap){
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

    public static String tryEmpty(String prop, String replacement, List<String> namedComponents, List<String> comps, NamedNodeMap nodeMap){
        String p;
        if((p = Utils.check(prop, nodeMap)).isEmpty()) p = replacement;
        if(!components.containsKey(p)) namedComponents.add(p);
        else if(!comps.contains(p)) comps.add(p);
        else{
            int count = 0;
            for(String comp : comps) if(comp.startsWith(p)) count++;
            p += count;
            comps.add(p);
        }
        return p;
    }

    public static void tryCapitalize(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if(!(p = Utils.check(prop, nodeMap)).isEmpty()) formatAppend(command, sb, name, capitalize(p));
    }

    public static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
