package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

final class ChildParser {
    private StringBuilder sb;

    private Map<String, Map<String, String>> NodeList = new HashMap<String, Map<String, String>>(){{
        put("Alignment", new HashMap<String, String>(){{
            put("bottom","Qt.AlignmentFlag.AlignBottom");
            put("center","Qt.AlignmentFlag.AlignCenter");
            put("horizontal-center","Qt.AlignmentFlag.AlignHCenter");
            put("justify","Qt.AlignmentFlag.AlignJustify");
            put("left","Qt.AlignmentFlag.AlignLeft");
            put("right","Qt.AlignmentFlag.AlignRight");
            put("top","Qt.AlignmentFlag.AlignTop");
            put("vertical-center","Qt.AlignmentFlag.AlignVCenter");
            put("absolute","Qt.AlignmentFlag.AlignAbsolute");
        }});
        put("Pen", new HashMap<String, String>(){{
            put("text-outline","%s.setProperty(0x2022, %s);");
            put("default","%s.setProperty(0x810, %s);");
        }});
        put("MenuCorner", new HashMap<String, String>(){{
            put("right","Qt.Corner.TopRightCorner");
            put("left","Qt.Corner.TopLeftCorner");
        }});
    }};

    protected void addChild(String name, String layout, String component, String child, StringBuilder sb, NamedNodeMap nodeMap){
        this.sb = sb;
        switch (layout) {
            case "window": WindowChild(child, component); break;
            case "grid" : GridChild(name, component, child, nodeMap); break;
            case "menubar" : MenubarChild(name, component, child, nodeMap); break;
            case "menu" : MenuChild(name, component, child, nodeMap); break;
            case "label" : LabelChild(name, component, child); break;
            case "section" : WidgetChild(name, component, child); break;
            case "splitter" : SplitChild(name, component, child); break;
            case "list" : ListChild(name, component, child); break;
            case "group" : GroupChild(name, component, child); break;
            case "pen" : PenChild(name, component, child); break;
            case "char-format": CharFormatChild(name, component, child, nodeMap);
            case "text-edit": TextEditChild(name, component, child);
            case "document": DocumentChild(name, component, child);
        }
    }

    private void DocumentChild(String name, String component, String child){
        tryChildParent(name, component, child, "font", "%s.setDefaultFont(%s);\n");
        tryChildParent(name, component, child, "text-option", "%s.setDefaultFont(%s);\n");
        tryChildParent(name, component, child, "document-layout", "%s.setDocumentLayout(%s);\n");
    }
    private void TextEditChild(String name, String component, String child){
        tryChildParent(name, component, child, "char-format", "%s.setCurrentCharFormat(%s);\n");
        tryChildParent(name, component, child, "document", "%s.setDocument(%s);\n");
    }
    private void CharFormatChild(String name, String component, String child, NamedNodeMap nodeMap){
        tryChildParent(name, component, child, "font", "%s.setFont(%s);\n");
        tryChildParent(name, component, child, "pen", "%s.setTextOutline(%s);\n");
        tryChildParent(name, component, child, "color", "%s.setUnderlineColor(%s);\n");
        TextFormatChild(name, component, child, nodeMap);
    }
    private void TextFormatChild(String name, String component, String child, NamedNodeMap nodeMap){
        tryChildParent(name, component, child, "tab", "%s.setProperty(0x1035, %s);\n");
        tryChildType(name, component, child, "brush&foreground", "%s.setProperty(0x821, %s);\n", nodeMap);
        tryChildType(name, component, child, "brush&background", "%s.setProperty(0x820, %s);\n", nodeMap);
        tryChildType(name, component, child, "brush&border", "%s.setProperty(0x4009, %s);\n", nodeMap);
        tryChildMapDefault(name, component, child, "%s.setProperty(0x810, %s);\n", NodeList.get("Pen"), nodeMap);
    }
    private void PenChild(String name, String component, String child){
        tryChildParent(name, component, child, "brush", "%s.setBrush(%s);\n");
    }
    private void GroupChild(String name, String component, String child) {
        tryChildParent(name, component, child, "layout", "%s.setLayout(%s);\n");
    }
    private void ListChild(String name, String component, String child) {
        tryChildParent(name, component, child, "item", "%s.addItem(%s);\n");
    }
    private void SplitChild(String name, String component, String child) {
        tryChildParent(name, component, child, "widget", "%s.addWidget(%s);\n");
    }
    private void WidgetChild(String name, String component, String child){
        tryChildParent(name, component, child, "layout", "%s.setLayout(%s);\n");
        tryChildParent(name, component, child, "widget", "%s.setParent(%s);\n");
    }
    private void LabelChild(String name, String component, String child){
        tryChildParent(name, component, child, "widget", "%s.setBuddy(%s);\n");
        tryChildParent(name, component, child, "movie", "%s.setMovie(%s);\n");
        tryChildParent(name, component, child, "picture", "%s.setPicture(%s);\n");
        tryChildParent(name, component, child, "drawing", "%s.setPixmap(%s);\n");
    }
    private void WindowChild(String name, String component){
        tryChild(name, component, "layout", "centerWidget.setLayout(%s);\n");
        tryChild(name, component, "widget", "%s.setParent(centerWidget);\n");
        tryChild(name, component, "menubar", "window.setMenuBar(%s);\n");
    }
    private void GridChild(String name, String component, String child, NamedNodeMap nodeMap){
        tryFindAppend(name, component, "widget", "%s.addWidget(%s, %s, %s, %s, %s, %s);\n", nodeMap, child,
                "row/int/1", "column/int/1", "row-span/int/row", "column-span/int/column",
                "position/map/Alignment/absolute");
        tryFindAppend(name, component, "layout", "%s.addLayout(%s, %s, %s, %s, %s, %s);\n", nodeMap, child,
                "row/int/1", "column/int/1", "row-span/int/row", "column-span/int/column",
                "position/map/Alignment/absolute");
        tryFindAppend(name, component, "item", "%s.addItem(%s, %s, %s, %s, %s, %s);\n", nodeMap, child,
                "row/int/1", "column/int/1", "row-span/int/row", "column-span/int/column",
                "position/map/Alignment/absolute");
    }

    private void MenubarChild(String name, String component, String child, NamedNodeMap nodeMap){
        tryChildMap(name, "widget", child, "%s.setCornerWidget(%s, %s);\n", NodeList.get("MenuCorner"), nodeMap);
        tryChildParent(name, component, child, "menu", "%s.addMenu(%s);\n");
        tryChildParent(name, component, child, "action", "%s.addAction(%s);\n");
        tryBoolean(name, "default", child, "%s.setDefaultUp(%s);\n", nodeMap);
        tryBoolean(name, "active", child, "%s.setNativeMenuBar(%s);\n", nodeMap);
        tryCheck(name, "separator", "%s.addSeparator();\n", nodeMap);
    }

    private void MenuChild(String name, String component, String child, NamedNodeMap nodeMap) {
        tryChildParent(name, component, child, "menu", "%s.addMenu(%s);\n");
        tryChildParent(name, component, child, "action", "%s.addAction(%s);\n");
        tryBoolean(name, "default", child, "%s.setDefaultUp(%s);\n", nodeMap);
        tryBoolean(name, "active", child, "%s.setNativeMenuBar(%s);\n", nodeMap);
        tryBoolean(name, "collapsible", child, "%s.setSeparatorsCollapsible(%s);\n", nodeMap);
        tryCheck(name, "separator", "%s.addSeparator();\n", nodeMap);
    }

    private void tryFindAppend(String name, String component, String compare, String command, NamedNodeMap nodeMap, String... params){
        String prop;
        if(component.equals(compare)){
            Map<Integer, Map.Entry<String, Object>> values = new HashMap<Integer, Map.Entry<String, Object>>(){{
                put(0, new AbstractMap.SimpleEntry<>("name", name));
            }};
            int count = 1;
            for(String s : params){
                String[] s1 = s.split("/");
                if(s1.length == 1) values.put(count++, new AbstractMap.SimpleEntry<>(s1[0], s1[0]));
                else if(s1[1].equals("int")){
                    if(!(prop = check(s1[0], nodeMap)).isEmpty())
                        values.put(count++, new AbstractMap.SimpleEntry<>(s1[0], Integer.parseInt(prop)));
                    else if(tryInteger(s1[2]))
                        values.put(count++, new AbstractMap.SimpleEntry<>(s1[0], Integer.parseInt(s1[2])));
                    else for(Map.Entry entry : values.values()) if(entry.getKey().equals(s1[2])){
                        values.put(count++, new AbstractMap.SimpleEntry<>(s1[0], entry.getValue()));
                        break;
                    }
                }else if(s1[1].equals("map")){
                    String value = "";
                    Map<String, String> map = NodeList.get(s1[2]);
                    if(!(prop = check(s1[0], nodeMap)).isEmpty()) value = map.get(prop);
                    else if(s1.length == 4) value = map.get(s1[3]);
                    values.put(count++, new AbstractMap.SimpleEntry<>(s1[0], value));
                }
            }
            Object[] list = new Object[values.size()];
            for(Map.Entry entry : values.entrySet())
                list[(int)entry.getKey()] = ((Map.Entry) entry.getValue()).getValue();
            String comp = String.format(command, list);
            sb.append(comp);
        }
    }

    private String check(String keyword, NamedNodeMap nodeMap) {
        try {
            Node word = nodeMap.getNamedItem(keyword);
            return (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException ignored) { return ""; }
    }

    private void tryChildParent(String name, String component, String child, String prop, String command){
        if(prop.equals(component)) sb.append(String.format(command, name, child));
    }

    private void tryChild(String name, String component, String prop, String command){
        if(prop.equals(component)) sb.append(String.format(command, name));
    }

    private boolean tryChildType(String name, String component, String child, String prop, String command, NamedNodeMap nodeMap){
        String[] nameNtype = component.split("&");
        boolean result = false;
        if(prop.equals(nameNtype[0])) if(Utils.check("type", nodeMap).equals(nameNtype[1])){
            sb.append(String.format(command, name, child));
            result = true;
        }
        return result;
    }

    private void tryChildMapDefault(String name, String component, String child, String Default, Map<String, String> map, NamedNodeMap nodeMap){
        boolean result = false;
        for(String key : map.keySet()) {
            result = true;
            tryChildType(name, component, child, key, map.get(key), nodeMap);
        }
        if(!result) sb.append(String.format(Default, name, child));
    }

    private void tryCheck(String name, String p, String command, NamedNodeMap nodeMap){
        String prop;
        if (!(prop = check(p, nodeMap)).isEmpty()) sb.append(String.format(command, name, prop));
    }

    private void tryChildMap(String name, String p, String child, String command, Map<String, String> map, NamedNodeMap nodeMap){
        String prop;
        if(!(prop = Utils.check(p, nodeMap)).isEmpty()) sb.append(String.format(command, name, child, map.get(prop)));
    }

    private boolean tryInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    private void tryBoolean(String name, String prop, String child, String command, NamedNodeMap nodeMap){
        if (Utils.tryBoolean(Utils.check(prop, nodeMap))) sb.append(String.format(command, name, child));
    }
}
