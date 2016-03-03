package Compiler;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

public final class Utils {

    public static Map<String, String> components = new HashMap<String, String>() {{
        put("button", "QPushButton");
        put("window", "QMainWindow");
        put("number", "QLCDNumber");
        put("radio", "QRadioButton");
        put("slider", "QSlider");
        put("check_box", "QCheckBox");
        put("grid", "QGridLayout");
        put("menubar", "QMenuBar");
        put("label", "QLabel");
        put("menu", "QMenu");
        put("action", "QAction");
        put("list", "QListWidget");
        put("section", "QWidget");
        put("splitter", "QSplitter");
        put("item", "QListWidgetItem");
        put("text_area", "QTextEdit");
        put("group", "QGroupBox");
        put("text_format", "QTextCharFormat");
        put("brush", "QBrush");
        put("pen", "QPen");
        put("tab", "QTextOption_Tab");
        put("font", "QFont");
        put("gradient", "QGradient");
        put("char_format", "QTextCharFormat");
        put("text_option", "QTextOption");
        put("document", "QTextDocument");
        put("document_layout", "QPlaintTextDocumentLayout");
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
        String comp = components.get(name.replaceAll("\\d", "").replaceAll("-", "_"));
        return (comp == null) ? name : comp;
    }
    public static String check(String keyword, NamedNodeMap nodeMap) {
        try {
            Node word = nodeMap.getNamedItem(keyword);
            return (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException ignored) {
            return "";
        }
    }
    public static boolean tryBoolean(String value){
        return (value.equals("true") || value.equals("false"));
    }
    public static String tryEmpty(String prop, String replacement, NamedNodeMap nodeMap){
        String p;
        return (!(p = Utils.check(prop, nodeMap)).isEmpty()) ? p : replacement;
    }
    public static void tryEmptyAppend(String name, String value, String command, StringBuilder sb){
        if(!value.isEmpty()) sb.append(String.format(command, name, value));
    }
    public static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
