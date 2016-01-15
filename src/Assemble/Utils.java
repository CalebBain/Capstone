package Assemble;

import StyleComponents.Style;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/13/2016.
 */
public final class Utils {

    public static boolean tryValue(String value) {
        boolean result = false;
        try {
            Integer.parseInt(value);
            result = true;
        } catch (NumberFormatException nfe) {}
        return result;
    }

    public static String check(String keyword, NamedNodeMap nodeMap) {
        String result = "";
        try {
            Node word = nodeMap.getNamedItem(keyword);
            result = (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException e) {}
        return result;
    }

    private static void addAttribute(Style style, NamedNodeMap nodeMap, String attribute) {
        String prop = check(attribute, nodeMap);
        try{
            if (!prop.isEmpty()) style.addAttribute(attribute, prop);
        }catch (NullPointerException e){}
    }

    private static void addAttribute(Style style, NamedNodeMap nodeMap, String attribute, String attributeName) {
        String prop = check(attribute, nodeMap);
        if (!prop.isEmpty()) style.addAttribute(attributeName, prop);
    }

    public static void setStyle(Style style, NamedNodeMap nodeMap) {
        String[] attributes = {"alt-background-color", "background", "background-color", "background-image", "background-repeat", "background-position",
            "background-attachment", "background-clip", "background-origin", "border", "border-top", "border-right", "border-bottom", "border-left",
            "border-image", "border-color", "border-top-color", "font-weight", "border-right-color", "border-bottom-color", "border-left-color",
            "border-style", "border-top-style", "icon-size", "border-right-style", "border-bottom-style", "border-left-style", "border-width",
            "border-top-width", "spacing", "border-right-width", "border-bottom-width", "border-left-width", "border-radius", "border-top-left-radius",
            "border-top-right-radius", "border-top-right-radius", "selection-background-color", "selection-color", "margin", "border-bottom-right-radius",
            "border-bottom-left-radius", "top", "right", "bottom", "left", "height", "width", "gridline-color", "button-layout", "color", "font",
            "font-family", "font-size", "font-style", "image-position", "margin-top", "position", "margin-bottom", "margin-left", "max-height", "max-width",
            "min-height", "min-width", "opacity", "padding", "padding-top", "padding-right", "padding-bottom", "margin-right", "padding-left", "image",
            "subcontrol-origin", "subcontrol-position", "text-align", "text-decoration"};
        for (String attribute : attributes) addAttribute(style, nodeMap, attribute);
        addAttribute(style, nodeMap, "alt-empty-row-color", "paint-alternating-row-colors-for-empty-area");
        addAttribute(style, nodeMap, "textbox-interaction", "messagebox-text-interaction-flags");
        addAttribute(style, nodeMap, "icon", "file-icon");
        addAttribute(style, nodeMap, "dialogbuttons-have-icons", "dialogbuttonbox-buttons-have-icons");
        addAttribute(style, nodeMap, "selection-decoration", "show-decoration-selected");
    }
}
