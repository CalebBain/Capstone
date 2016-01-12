package QtComponents;

import StyleComponents.Style;
import com.trolltech.qt.gui.QMainWindow;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class Window extends QMainWindow implements Component {

    private Style style;
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Window(Node node) {
        this.nodeMap = node.getAttributes();
        setIdentity(nodeMap);
    }

    private void setIdentity(NamedNodeMap nodeMap) {
        this.Name = check("name");
        this.Class = check("class");
        if (!Name.isEmpty()) {
            this.style = new Style(Name, this, true);
            this.setAccessibleName(Name);
        } else
            this.style = new Style("number", this, false);
    }

    private String check(String keyword) {
        try {
            Node word = nodeMap.getNamedItem(keyword);
            return (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public void setStyle() {
        String prop;
        this.setWindowTitle(tr((!(prop = check("title")).isEmpty()) ? prop : "JAML Applicaiton"));
        if (!(prop = check("alt-background-color")).isEmpty()) style.addAttribute("alternate-background-color", prop);
        if (!(prop = check("background")).isEmpty()) style.addAttribute("background", prop);
        if (!(prop = check("background-color")).isEmpty()) style.addAttribute("background-color", prop);
        if (!(prop = check("background-image")).isEmpty()) style.addAttribute("background-image", prop);
        if (!(prop = check("background-repeat")).isEmpty()) style.addAttribute("background-repeat", prop);
        if (!(prop = check("background-position")).isEmpty()) style.addAttribute("background-position", prop);
        if (!(prop = check("background-attachment")).isEmpty()) style.addAttribute("background-attachment", prop);
        if (!(prop = check("background-clip")).isEmpty()) style.addAttribute("background-clip", prop);
        if (!(prop = check("background-origin")).isEmpty()) style.addAttribute("background-origin", prop);
        if (!(prop = check("border")).isEmpty()) style.addAttribute("border", prop);
        if (!(prop = check("border-top")).isEmpty()) style.addAttribute("border-color", prop);
        if (!(prop = check("border-right")).isEmpty()) style.addAttribute("border-style", prop);
        if (!(prop = check("border-bottom")).isEmpty()) style.addAttribute("border-width", prop);
        if (!(prop = check("border-left")).isEmpty()) style.addAttribute("border-top", prop);
        if (!(prop = check("border-image")).isEmpty()) style.addAttribute("border-top-color", prop);
        if (!(prop = check("border-color")).isEmpty()) style.addAttribute("border-top-style", prop);
        if (!(prop = check("border-top-color")).isEmpty()) style.addAttribute("border-top-width", prop);
        if (!(prop = check("border-right-color")).isEmpty()) style.addAttribute("border-right", prop);
        if (!(prop = check("border-bottom-color")).isEmpty()) style.addAttribute("border-right-color", prop);
        if (!(prop = check("border-left-color")).isEmpty()) style.addAttribute("border-right-style", prop);
        if (!(prop = check("border-style")).isEmpty()) style.addAttribute("border-right-width", prop);
        if (!(prop = check("border-top-style")).isEmpty()) style.addAttribute("border-bottom", prop);
        if (!(prop = check("border-right-style")).isEmpty()) style.addAttribute("border-bottom-color", prop);
        if (!(prop = check("border-bottom-style")).isEmpty()) style.addAttribute("border-bottom-style", prop);
        if (!(prop = check("border-left-style")).isEmpty()) style.addAttribute("border-bottom-width", prop);
        if (!(prop = check("border-width")).isEmpty()) style.addAttribute("border-left", prop);
        if (!(prop = check("border-top-width")).isEmpty()) style.addAttribute("border-left-color", prop);
        if (!(prop = check("border-right-width")).isEmpty()) style.addAttribute("border-left-style", prop);
        if (!(prop = check("border-bottom-width")).isEmpty()) style.addAttribute("border-left-width", prop);
        if (!(prop = check("border-left-width")).isEmpty()) style.addAttribute("border-image", prop);
        if (!(prop = check("border-radius")).isEmpty()) style.addAttribute("border-radius", prop);
        if (!(prop = check("border-top-left-radius")).isEmpty()) style.addAttribute("border-top-left-radius", prop);
        if (!(prop = check("border-top-right-radius")).isEmpty()) style.addAttribute("border-top-right-radius", prop);
        if (!(prop = check("border-bottom-right-radius")).isEmpty()) style.addAttribute("border-bottom-right-radius", prop);
        if (!(prop = check("border-bottom-left-radius")).isEmpty()) style.addAttribute("border-bottom-left-radius", prop);
        if (!(prop = check("top")).isEmpty()) style.addAttribute("top", prop);
        if (!(prop = check("right")).isEmpty()) style.addAttribute("right", prop);
        if (!(prop = check("bottom")).isEmpty()) style.addAttribute("bottom", prop);
        if (!(prop = check("left")).isEmpty()) style.addAttribute("left", prop);
        if (!(prop = check("height")).isEmpty()) style.addAttribute("height", prop);
        if (!(prop = check("width")).isEmpty()) style.addAttribute("width", prop);
        if (!(prop = check("gridline-color")).isEmpty()) style.addAttribute("gridline-color", prop);
        if (!(prop = check("button-layout")).isEmpty()) style.addAttribute("button-layout", prop);
        if (!(prop = check("button-icon")).isEmpty()) style.addAttribute("dialogbuttonbox-buttons-have-icons", prop);
        if (!(prop = check("color")).isEmpty()) style.addAttribute("color", prop);
        if (!(prop = check("font")).isEmpty()) style.addAttribute("font", prop);
        if (!(prop = check("font-family")).isEmpty()) style.addAttribute("font-family", prop);
        if (!(prop = check("font-size")).isEmpty()) style.addAttribute("font-size", prop);
        if (!(prop = check("font-style")).isEmpty()) style.addAttribute("font-style", prop);
        if (!(prop = check("font-weight")).isEmpty()) style.addAttribute("font-weight", prop);
        //if (!(prop = check("icon-size")).isEmpty()) style.addAttribute("icon-size", prop);
        if (!(prop = check("image")).isEmpty()) style.addAttribute("image", prop);
        if (!(prop = check("image-position")).isEmpty()) style.addAttribute("image-position", prop);
        if (!(prop = check("margin")).isEmpty()) style.addAttribute("margin", prop);
        if (!(prop = check("margin-top")).isEmpty()) style.addAttribute("margin-top", prop);
        if (!(prop = check("margin-right")).isEmpty()) style.addAttribute("margin-right", prop);
        if (!(prop = check("margin-bottom")).isEmpty()) style.addAttribute("margin-bottom", prop);
        if (!(prop = check("margin-left")).isEmpty()) style.addAttribute("margin-left", prop);
        if (!(prop = check("max-height")).isEmpty()) style.addAttribute("max-height", prop);
        if (!(prop = check("max-width")).isEmpty()) style.addAttribute("max-width", prop);
        if (!(prop = check("textbox-interaction")).isEmpty()) style.addAttribute("messagebox-text-interaction-flags", prop);
        if (!(prop = check("min-height")).isEmpty()) style.addAttribute("min-height", prop);
        if (!(prop = check("min-width")).isEmpty()) style.addAttribute("min-width", prop);
        if (!(prop = check("opacity")).isEmpty()) style.addAttribute("opacity", prop);
        if (!(prop = check("padding")).isEmpty()) style.addAttribute("padding", prop);
        if (!(prop = check("padding-top")).isEmpty()) style.addAttribute("padding-top", prop);
        if (!(prop = check("padding-right")).isEmpty()) style.addAttribute("padding-right", prop);
        if (!(prop = check("padding-bottom")).isEmpty()) style.addAttribute("padding-bottom", prop);
        if (!(prop = check("padding-left")).isEmpty()) style.addAttribute("padding-left", prop);
        if (!(prop = check("alt-empty-row-color")).isEmpty()) style.addAttribute("paint-alternating-row-colors-for-empty-area", prop);
        if (!(prop = check("position")).isEmpty()) style.addAttribute("position", prop);
        if (!(prop = check("select-background-color")).isEmpty()) style.addAttribute("selection-background-color", prop);
        if (!(prop = check("select-color")).isEmpty()) style.addAttribute("selection-color", prop);
        if (!(prop = check("select-decoration")).isEmpty()) style.addAttribute("show-decoration-selected", prop);
        if (!(prop = check("spacing")).isEmpty()) style.addAttribute("spacing", prop);
        if (!(prop = check("subcontrol-origin")).isEmpty()) style.addAttribute("subcontrol-origin", prop);
        if (!(prop = check("subcontrol-position")).isEmpty()) style.addAttribute("subcontrol-position", prop);
        if (!(prop = check("text-align")).isEmpty()) style.addAttribute("text-align", prop);
        if (!(prop = check("text-decoration")).isEmpty()) style.addAttribute("text-decoration", prop);
    }

    @Override
    public String Name() {
        return Name;
    }

    @Override
    public String Class() {
        return Class;
    }

    @Override
    public String Component() {
        return this.getClass().getName();
    }

    @Override
    public QMainWindow Widgit() {
        return this;
    }

    @Override
    public void SetStylesheet() {
        this.setStyleSheet("");
    }
}