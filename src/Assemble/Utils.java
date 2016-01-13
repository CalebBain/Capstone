package Assemble;

import StyleComponents.Style;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/13/2016.
 */
public class Utils {

    public static boolean tryValue(String value) {
        boolean result = false;
        try {
            Integer.parseInt(value);
            result =  true;
        } catch (NumberFormatException nfe) {
        }
        return result;
    }

    public static String check(String keyword, NamedNodeMap nodeMap) {
        String result = "";
        try {
            Node word = nodeMap.getNamedItem(keyword);
            result =  (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException e) {
        }
        return result;
    }

    public static void setStyle(Style style, NamedNodeMap nodeMap) {
        String prop;
        if (!(prop = check("alt-background-color", nodeMap)).isEmpty()) style.addAttribute("alternate-background-color", prop);
        if (!(prop = check("background", nodeMap)).isEmpty()) style.addAttribute("background", prop);
        if (!(prop = check("background-color", nodeMap)).isEmpty()) style.addAttribute("background-color", prop);
        if (!(prop = check("background-image", nodeMap)).isEmpty()) style.addAttribute("background-image", prop);
        if (!(prop = check("background-repeat", nodeMap)).isEmpty()) style.addAttribute("background-repeat", prop);
        if (!(prop = check("background-position", nodeMap)).isEmpty()) style.addAttribute("background-position", prop);
        if (!(prop = check("background-attachment", nodeMap)).isEmpty()) style.addAttribute("background-attachment", prop);
        if (!(prop = check("background-clip", nodeMap)).isEmpty()) style.addAttribute("background-clip", prop);
        if (!(prop = check("background-origin", nodeMap)).isEmpty()) style.addAttribute("background-origin", prop);
        if (!(prop = check("border", nodeMap)).isEmpty()) style.addAttribute("border", prop);
        if (!(prop = check("border-top", nodeMap)).isEmpty()) style.addAttribute("border-color", prop);
        if (!(prop = check("border-right", nodeMap)).isEmpty()) style.addAttribute("border-style", prop);
        if (!(prop = check("border-bottom", nodeMap)).isEmpty()) style.addAttribute("border-width", prop);
        if (!(prop = check("border-left", nodeMap)).isEmpty()) style.addAttribute("border-top", prop);
        if (!(prop = check("border-image", nodeMap)).isEmpty()) style.addAttribute("border-top-color", prop);
        if (!(prop = check("border-color", nodeMap)).isEmpty()) style.addAttribute("border-top-style", prop);
        if (!(prop = check("border-top-color", nodeMap)).isEmpty()) style.addAttribute("border-top-width", prop);
        if (!(prop = check("border-right-color", nodeMap)).isEmpty()) style.addAttribute("border-right", prop);
        if (!(prop = check("border-bottom-color", nodeMap)).isEmpty()) style.addAttribute("border-right-color", prop);
        if (!(prop = check("border-left-color", nodeMap)).isEmpty()) style.addAttribute("border-right-style", prop);
        if (!(prop = check("border-style", nodeMap)).isEmpty()) style.addAttribute("border-right-width", prop);
        if (!(prop = check("border-top-style", nodeMap)).isEmpty()) style.addAttribute("border-bottom", prop);
        if (!(prop = check("border-right-style", nodeMap)).isEmpty()) style.addAttribute("border-bottom-color", prop);
        if (!(prop = check("border-bottom-style", nodeMap)).isEmpty()) style.addAttribute("border-bottom-style", prop);
        if (!(prop = check("border-left-style", nodeMap)).isEmpty()) style.addAttribute("border-bottom-width", prop);
        if (!(prop = check("border-width", nodeMap)).isEmpty()) style.addAttribute("border-left", prop);
        if (!(prop = check("border-top-width", nodeMap)).isEmpty()) style.addAttribute("border-left-color", prop);
        if (!(prop = check("border-right-width", nodeMap)).isEmpty()) style.addAttribute("border-left-style", prop);
        if (!(prop = check("border-bottom-width", nodeMap)).isEmpty()) style.addAttribute("border-left-width", prop);
        if (!(prop = check("border-left-width", nodeMap)).isEmpty()) style.addAttribute("border-image", prop);
        if (!(prop = check("border-radius", nodeMap)).isEmpty()) style.addAttribute("border-radius", prop);
        if (!(prop = check("border-top-left-radius", nodeMap)).isEmpty()) style.addAttribute("border-top-left-radius", prop);
        if (!(prop = check("border-top-right-radius", nodeMap)).isEmpty()) style.addAttribute("border-top-right-radius", prop);
        if (!(prop = check("border-bottom-right-radius", nodeMap)).isEmpty()) style.addAttribute("border-bottom-right-radius", prop);
        if (!(prop = check("border-bottom-left-radius", nodeMap)).isEmpty()) style.addAttribute("border-bottom-left-radius", prop);
        if (!(prop = check("top", nodeMap)).isEmpty()) style.addAttribute("top", prop);
        if (!(prop = check("right", nodeMap)).isEmpty()) style.addAttribute("right", prop);
        if (!(prop = check("bottom", nodeMap)).isEmpty()) style.addAttribute("bottom", prop);
        if (!(prop = check("left", nodeMap)).isEmpty()) style.addAttribute("left", prop);
        if (!(prop = check("height", nodeMap)).isEmpty()) style.addAttribute("height", prop);
        if (!(prop = check("width", nodeMap)).isEmpty()) style.addAttribute("width", prop);
        if (!(prop = check("gridline-color", nodeMap)).isEmpty()) style.addAttribute("gridline-color", prop);
        if (!(prop = check("button-layout", nodeMap)).isEmpty()) style.addAttribute("button-layout", prop);
        if (!(prop = check("button-icon", nodeMap)).isEmpty()) style.addAttribute("dialogbuttonbox-buttons-have-icons", prop);
        if (!(prop = check("color", nodeMap)).isEmpty()) style.addAttribute("color", prop);
        if (!(prop = check("font", nodeMap)).isEmpty()) style.addAttribute("font", prop);
        if (!(prop = check("font-family", nodeMap)).isEmpty()) style.addAttribute("font-family", prop);
        if (!(prop = check("font-size", nodeMap)).isEmpty()) style.addAttribute("font-size", prop);
        if (!(prop = check("font-style", nodeMap)).isEmpty()) style.addAttribute("font-style", prop);
        if (!(prop = check("font-weight", nodeMap)).isEmpty()) style.addAttribute("font-weight", prop);
        if (!(prop = check("icon", nodeMap)).isEmpty()) style.addAttribute("file-icon", prop);
        if (!(prop = check("icon-size", nodeMap)).isEmpty()) style.addAttribute("icon-size", prop);
        if (!(prop = check("image", nodeMap)).isEmpty()) style.addAttribute("image", prop);
        if (!(prop = check("image-position", nodeMap)).isEmpty()) style.addAttribute("image-position", prop);
        if (!(prop = check("margin", nodeMap)).isEmpty()) style.addAttribute("margin", prop);
        if (!(prop = check("margin-top", nodeMap)).isEmpty()) style.addAttribute("margin-top", prop);
        if (!(prop = check("margin-right", nodeMap)).isEmpty()) style.addAttribute("margin-right", prop);
        if (!(prop = check("margin-bottom", nodeMap)).isEmpty()) style.addAttribute("margin-bottom", prop);
        if (!(prop = check("margin-left", nodeMap)).isEmpty()) style.addAttribute("margin-left", prop);
        if (!(prop = check("max-height", nodeMap)).isEmpty()) style.addAttribute("max-height", prop);
        if (!(prop = check("max-width", nodeMap)).isEmpty()) style.addAttribute("max-width", prop);
        if (!(prop = check("textbox-interaction", nodeMap)).isEmpty()) style.addAttribute("messagebox-text-interaction-flags", prop);
        if (!(prop = check("min-height", nodeMap)).isEmpty()) style.addAttribute("min-height", prop);
        if (!(prop = check("min-width", nodeMap)).isEmpty()) style.addAttribute("min-width", prop);
        if (!(prop = check("opacity", nodeMap)).isEmpty()) style.addAttribute("opacity", prop);
        if (!(prop = check("padding", nodeMap)).isEmpty()) style.addAttribute("padding", prop);
        if (!(prop = check("padding-top", nodeMap)).isEmpty()) style.addAttribute("padding-top", prop);
        if (!(prop = check("padding-right", nodeMap)).isEmpty()) style.addAttribute("padding-right", prop);
        if (!(prop = check("padding-bottom", nodeMap)).isEmpty()) style.addAttribute("padding-bottom", prop);
        if (!(prop = check("padding-left", nodeMap)).isEmpty()) style.addAttribute("padding-left", prop);
        if (!(prop = check("alt-empty-row-color", nodeMap)).isEmpty()) style.addAttribute("paint-alternating-row-colors-for-empty-area", prop);
        if (!(prop = check("position", nodeMap)).isEmpty()) style.addAttribute("position", prop);
        if (!(prop = check("select-background-color", nodeMap)).isEmpty()) style.addAttribute("selection-background-color", prop);
        if (!(prop = check("select-color", nodeMap)).isEmpty()) style.addAttribute("selection-color", prop);
        if (!(prop = check("select-decoration", nodeMap)).isEmpty()) style.addAttribute("show-decoration-selected", prop);
        if (!(prop = check("spacing", nodeMap)).isEmpty()) style.addAttribute("spacing", prop);
        if (!(prop = check("subcontrol-origin", nodeMap)).isEmpty()) style.addAttribute("subcontrol-origin", prop);
        if (!(prop = check("subcontrol-position", nodeMap)).isEmpty()) style.addAttribute("subcontrol-position", prop);
        if (!(prop = check("text-align", nodeMap)).isEmpty()) style.addAttribute("text-align", prop);
        if (!(prop = check("text-decoration", nodeMap)).isEmpty()) style.addAttribute("text-decoration", prop);
    }
}
