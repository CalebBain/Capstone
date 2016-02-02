package Compiler.Parsers;

import org.w3c.dom.NamedNodeMap;
import Compiler.Utils;

/**
 * Created by Caleb Bain on 1/29/2016.
 */
public final class ChildParser {

    public void addChild(String name, String layout, String component, String child, StringBuilder sb, NamedNodeMap nodeMap){
        switch (layout) {
            case "window": WindowChild(child, sb, component); break;
            case "grid" : GridChild(name, component, child, sb, nodeMap); break;
            case "menubar" : MenubarChild(name, component, child, sb, nodeMap); break;
            case "menu" : MenuChild(name, component, child,sb , nodeMap); break;
        }
    }

    public void WindowChild(String name, StringBuilder sb, String component){
        switch (component){
            case "layout" : sb.append(String.format("centerWidget.setLayout(%s);\n", name)); break;
            case "widget" : sb.append(String.format("%s.setParent(centerWidget);\n", name)); break;
            case "menubar" : sb.append(String.format("window.setMenuBar(%s);\n", name)); break;
        }
    }

    public void GridChild(String name, String component, String child, StringBuilder sb, NamedNodeMap nodeMap){
        Utils.capitalize(component);
        sb.append(String.format("%1s.add%2s(%3s, ", name, component, child));
        int row = Utils.tryValue("row", "%2s, ", 1, sb, nodeMap);
        int col = Utils.tryValue("column", "%2s, ", 1, sb, nodeMap);
        Utils.tryValue("row-span", "%2s, ", row, sb, nodeMap);
        Utils.tryValue("column-span", "%2s, ", col, sb, nodeMap);
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

    public void MenubarChild(String name, String component, String child, StringBuilder sb, NamedNodeMap nodeMap){
        switch (component){
            case "widget" :
                sb.append(String.format("%s.setCornerWidget(%s", name, child));
                switch(Utils.check("corner", nodeMap)){
                    case "right": sb.append(", Qt.Corner.TopRightCorner"); break;
                    case "left": sb.append(", Qt.Corner.TopLeftCorner"); break;
                }
                sb.append(");\n");
                break;
            case "menu" : sb.append(String.format("%s.addMenu(%s);\n", name, child)); break;
            case "action" :
                sb.append(String.format("%s.addAction(%s);\n", name, child));
                Utils.tryBoolean(name, "default", child, "%s.setDefaultUp(%s);\n", sb, nodeMap);
                Utils.tryBoolean(name, "active", child, "%s.setNativeMenuBar(%s);\n", sb, nodeMap);
                break;
            case "separator" : sb.append(String.format("%s.addSeparator();\n", name)); break;
        }
    }

    public void MenuChild(String name, String component, String child, StringBuilder sb, NamedNodeMap nodeMap) {
        switch (component){
            case "menu" : sb.append(String.format("%s.addMenu(%s);\n", name, child)); break;
            case "action" :
                sb.append(String.format("%s.addAction(%s);\n", name, child));
                Utils.tryBoolean(name, "default", child, "%s.setDefaultUp(%s);\n", sb, nodeMap);
                Utils.tryBoolean(name, "active", child, "%s.setNativeMenuBar(%s);\n", sb, nodeMap);
                break;
            case "separator" :
                sb.append(String.format("%s.addSeparator();\n", name));
                Utils.tryBoolean(name, "collapsible", child, "%s.setSeparatorsCollapsible(%s);\n", sb, nodeMap);
                break;
        }
    }

    public void ColumnViewChild(String name, String component, StringBuilder sb){
        switch (component){
            case "window" : case "grid" : break;
            default: sb.append(String.format("ColumnView.setPreviewWidget((%1s);\n", name)); break;
        }
    }
}
