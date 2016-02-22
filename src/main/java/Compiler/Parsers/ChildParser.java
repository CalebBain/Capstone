package Compiler.Parsers;

import org.w3c.dom.NamedNodeMap;
import Compiler.Utils;

final class ChildParser {

    protected void addChild(String name, String layout, String component, String child, StringBuilder sb, NamedNodeMap nodeMap){
        switch (layout) {
            case "window": WindowChild(child, component, sb); break;
            case "grid" : GridChild(name, component, child, sb, nodeMap); break;
            case "menubar" : MenubarChild(name, component, child, sb, nodeMap); break;
            case "menu" : MenuChild(name, component, child, sb, nodeMap); break;
            case "label" : LabelChild(name, component, child, sb); break;
            case "section" : WidgetChild(name, component, child, sb); break;
            case "splitter" : SplitChild(name, component, child, sb, nodeMap); break;
            case "list" : ListChild(name, component, child, sb); break;
            case "group" : GroupChild(name, component, child, sb); break;
            case "pen" : PenChild(name, component, child, sb); break;
            case "char-format": CharFormatChild(name, component, child, sb, nodeMap);
            case "text-edit": TextEditChild(name, component, child, sb);
            case "document": DocumentChild(name, component, child, sb);
        }
    }

    private void DocumentChild(String name, String component, String child, StringBuilder sb){
        switch(component){
            case "font": sb.append(String.format("%s.setDefaultFont(%s);\n", name, child)); break;
            case "text-option": sb.append(String.format("%s.setDefaultTextOption(%s);\n", name, child)); break;
            case "document-layout": sb.append(String.format("%s.setDocumentLayout(%s);\n", name, child)); break;
        }
    }

    private void TextEditChild(String name, String component, String child, StringBuilder sb){
        switch(component){
            case "char-format": sb.append(String.format("%s.setCurrentCharFormat(%s);\n", name, child)); break;
            case "document": sb.append(String.format("%s.setDocument(%s);\n", name, child)); break;
        }
    }

    private void CharFormatChild(String name, String component, String child, StringBuilder sb, NamedNodeMap nodeMap){
        switch(component){
            case "font": sb.append(String.format("%s.setFont(%s);\n", name, child)); break;
            case "pen": sb.append(String.format("%s.setTextOutline(%s);\n", name, child)); break;
            case "color": sb.append(String.format("%s.setUnderlineColor(%s);\n", name, child)); break;
        }
        TextFormatChild(name, component, child, sb, nodeMap);
    }

    private void TextFormatChild(String name, String component, String child, StringBuilder sb, NamedNodeMap nodeMap){
        switch (component){
            case "tab" : sb.append(String.format("%s.setProperty(0x1035, %s);\n", name, child)); break;
            case "brush" :
                String prop;
                if (!(prop = Utils.check("type", nodeMap)).isEmpty()){
                    String value = "";
                    switch (prop) {
                        case "foreground": value = String.format("0x821, %s", child); break;
                        case "background": value = String.format("0x820, %s", child); break;
                        case "border": value = String.format("0x4009, %s", child); break;
                    }
                    Utils.tryEmptyAppend(name, value, "%s.setProperty(%s);\n", sb);
                }
                break;
            case "pen":
                if (!(prop = Utils.check("type", nodeMap)).isEmpty()){
                    String value;
                    switch (prop) {
                        case "text-outline": value = String.format("0x2022, %s", child); break;
                        default: value = String.format("0x810, %s", child);
                    }
                    Utils.tryEmptyAppend(name, value, "%s.setProperty(%s);\n", sb);
                }
        }
    }

    private void PenChild(String name, String component, String child, StringBuilder sb){
        if(component.equals("brush")) sb.append(String.format("%s.setBrush(%s);\n", name, child));
    }

    private void GroupChild(String name, String component, String child, StringBuilder sb) {
        if(component.equals("layout")) sb.append(String.format("%s.setLayout(%s);\n", name, child));
    }

    private void ListChild(String name, String component, String child, StringBuilder sb) {
        if(component.equals("item"))  sb.append(String.format("%s.addItem(%s);\n", name, child));
    }

    private void SplitChild(String name, String component, String child, StringBuilder sb, NamedNodeMap nodeMap) {
        String prop;
        if(component.equals("widget")) {
            if(!(prop = Utils.check("insert", nodeMap)).isEmpty()) sb.append(String.format("%s.insertWidget(%s, %s);\n", name, prop, child));
            else sb.append(String.format("%s.addWidget(%s);\n", name, child));
        }
    }

    private void WidgetChild(String name, String component, String child, StringBuilder sb){
        switch (component){
            case "layout" : sb.append(String.format("%s.setLayout(%s);\n", name, child)); break;
            case "widget" : sb.append(String.format("%s.setParent(%s);\n", name, child)); break;
        }
    }

    private void LabelChild(String name, String component, String child, StringBuilder sb){
        switch (component){
            case "widget" : sb.append(String.format("%s.setBuddy(%s);\n", name, child)); break;
            case "movie" : sb.append(String.format("%s.setMovie(%s);\n", name, child)); break;
            case "picture" : sb.append(String.format("%s.setPicture(%s);\n", name, child)); break;
            case "drawing" : sb.append(String.format("%s.setPixmap(%s);\n", name, child)); break;
        }
    }

    private void WindowChild(String name, String component, StringBuilder sb){
        switch (component){
            case "layout" : sb.append(String.format("centerWidget.setLayout(%s);\n", name)); break;
            case "widget" : sb.append(String.format("%s.setParent(centerWidget);\n", name)); break;
            case "menubar" : sb.append(String.format("window.setMenuBar(%s);\n", name)); break;
        }
    }

    private void GridChild(String name, String component, String child, StringBuilder sb, NamedNodeMap nodeMap){
        Utils.capitalize(component);
        sb.append(String.format("%s.add%s(%s, ", name, Utils.capitalize(component), child));
        Number row = tryValue("row", "%2s, ", 1, sb, nodeMap);
        Number col = tryValue("column", "%2s, ", 1, sb, nodeMap);
        tryValue("row-span", "%2s, ", row, sb, nodeMap);
        tryValue("column-span", "%2s, ", col, sb, nodeMap);
        sb.append("Qt.AlignmentFlag.");
        switch(Utils.check("position", nodeMap)){
            case "bottom": sb.append("AlignBottom);\n"); break;
            case "center": sb.append("AlignCenter);\n"); break;
            case "horizontal-center": sb.append("AlignHCenter);\n"); break;
            case "justify": sb.append("AlignJustify);\n"); break;
            case "left": sb.append("AlignLeft);\n"); break;
            case "right": sb.append("AlignRight);\n"); break;
            case "top": sb.append("AlignTop);\n"); break;
            case "vertical-center": sb.append("AlignVCenter);\n"); break;
            default: sb.append("AlignAbsolute);\n"); break;
        }
    }

    private void MenubarChild(String name, String component, String child, StringBuilder sb, NamedNodeMap nodeMap){
        switch (component){
            case "widget" :
                sb.append(String.format("%s.setCornerWidget(%s", name, child));
                switch(Utils.check("corner", nodeMap)){
                    case "right": sb.append(", Qt.Corner.TopRightCorner"); break;
                    case "left": sb.append(", Qt.Corner.TopLeftCorner"); break;
                }
                sb.append(");\n");
                break;
            case "menu" :
                sb.append(String.format("%s.addMenu(%s);\n", name, child)); break;
            case "action" :
                sb.append(String.format("%s.addAction(%s);\n", name, child));
                tryBoolean(name, "default", child, "%s.setDefaultUp(%s);\n", sb, nodeMap);
                tryBoolean(name, "active", child, "%s.setNativeMenuBar(%s);\n", sb, nodeMap);
                break;
            case "separator" : sb.append(String.format("%s.addSeparator();\n", name)); break;
        }
    }

    private void MenuChild(String name, String component, String child, StringBuilder sb, NamedNodeMap nodeMap) {
        switch (component){
            case "menu" : sb.append(String.format("%s.addMenu(%s);\n", name, child)); break;
            case "action" :
                sb.append(String.format("%s.addAction(%s);\n", name, child));
                tryBoolean(name, "default", child, "%s.setDefaultUp(%s);\n", sb, nodeMap);
                tryBoolean(name, "active", child, "%s.setNativeMenuBar(%s);\n", sb, nodeMap);
                break;
            case "separator" :
                sb.append(String.format("%s.addSeparator();\n", name));
                tryBoolean(name, "collapsible", child, "%s.setSeparatorsCollapsible(%s);\n", sb, nodeMap);
                break;
        }
    }

    private Number tryValue(String prop, String command, Number replacement, StringBuilder sb, NamedNodeMap nodeMap){
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

    private void tryBoolean(String name, String prop, String child, String command, StringBuilder sb, NamedNodeMap nodeMap){
        if (Utils.tryBoolean(Utils.check(prop, nodeMap))) sb.append(String.format(command, name, child));
    }
}
