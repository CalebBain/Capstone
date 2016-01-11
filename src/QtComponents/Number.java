package QtComponents;

import StyleComponents.Style;
import com.trolltech.qt.gui.QLCDNumber;
import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/8/2016.
 */
public class Number extends QLCDNumber implements Component {

    private Style style;
    private String Name;
    private String Class;
    private NamedNodeMap nodeMap;

    public Number(QWidget parent, Node node) {
        super(parent);
        this.nodeMap = node.getAttributes();
        setIdentity(nodeMap);
    }

    public void setIdentity(NamedNodeMap nodeMap) {
        this.Name = check(nodeMap, "name");
        this.Class = check(nodeMap, "class");
        if(!Name.isEmpty()){
            this.style = new Style(Name);
            this.setAccessibleName(Name);
        }else
            this.style = new Style("number");
    }

    public String check(NamedNodeMap nodeMap, String keyword) {
        try {
            Node word = nodeMap.getNamedItem(keyword);
            return (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException e) {
            return "";
        }
    }

    public void setHeight(String height) {
        try {
            this.resize(width(), Integer.parseInt(height));
        } catch (NumberFormatException nfe) {
        }
    }

    public void setWidth(String width) {
        try {
            this.resize(Integer.parseInt(width), height());
        } catch (NumberFormatException nfe) {
        }
    }

    public void setSize(String width, String height) {
        if (width.isEmpty()) setHeight(height);
        else if (height.isEmpty()) setWidth(width);
        else {
            try {
                this.resize(Integer.parseInt(width), Integer.parseInt(height));
            } catch (NumberFormatException nfe) {
            }
        }
    }

    public void setMargins(String margins) {
        String[] Margins = new String[0];
        if (!margins.isEmpty()) Margins = margins.split(" ");
        int l = Margins.length;
        int[] m = new int[l];
        for (int i = 0; i < l; i++) m[i] = Integer.parseInt(Margins[i]);
        switch (l) {
            case 4:
                this.setGeometry(m[0], m[1], m[2], m[3]);
                break;
            case 3:
                this.setGeometry(m[0], m[1], m[2], m[1]);
                break;
            case 2:
                this.setGeometry(m[0], m[1], m[0], m[1]);
                break;
            case 1:
                this.setGeometry(m[0], m[0], m[0], m[0]);
                break;
        }
    }

    public void setDigitCount(String count) {
        this.setDigitCount(Integer.parseInt(count));
    }

    public void setStyle(String style) {
        switch (style) {
            case "outline":
                this.setSegmentStyle(SegmentStyle.Outline);
                break;
            case "filled":
                this.setSegmentStyle(SegmentStyle.Filled);
                break;
            case "flat":
                this.setSegmentStyle(SegmentStyle.Flat);
                break;
        }
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
    public QLCDNumber Widgit() {
        return this;
    }

    @Override
    public void setStyle() {
        this.setSmallDecimalPoint(true);
        setSize(check(nodeMap, "width"), check(nodeMap, "height"));
        setDigitCount(check(nodeMap, "digit-count"));
        setStyle(check(nodeMap, "style"));
        //setMargins(check(nodeMap, "margin"));
    }

    @Override
    public void SetStylesheet() {
        this.setStyleSheet("");
    }
}
