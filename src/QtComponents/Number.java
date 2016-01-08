package QtComponents;

import com.trolltech.qt.gui.QLCDNumber;
import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/8/2016.
 */
public class Number extends QLCDNumber{

    public Number(QWidget parent, Node node) {
        super(parent);
        NamedNodeMap nodeMap = node.getAttributes();
        this.setSmallDecimalPoint(true);
        setSize(check(nodeMap, "width"), check(nodeMap, "height"));
        setDigitCount(check(nodeMap, "digit-count"));
        setStyle(check(nodeMap, "style"));
        setMargins(check(nodeMap, "margin"));
    }

    public String check(NamedNodeMap nodeMap, String keyword){
        Node word = nodeMap.getNamedItem(keyword);
        return (word != null) ? word.getNodeValue() : "";
    }

    public void setHeight(String height) {
        try {
            this.resize(width(), Integer.parseInt(height));
        }catch (NumberFormatException nfe) {
        }
    }

    public void setWidth(String width) {
        try {
            this.resize(Integer.parseInt(width), height());
        }catch (NumberFormatException nfe) {
        }
    }

    public void setSize(String width, String height) {
        if(width.isEmpty()) setHeight(height);
        else if (height.isEmpty()) setWidth(width);
        else {
            try {
                this.resize(Integer.parseInt(width), Integer.parseInt(height));
            } catch (NumberFormatException nfe) {
            }
        }
    }

    public void setSize(int width, int height) {
        this.resize(width, height);
    }

    public void setMargins(String margins) {
        String[] Margins = new String[0];
        if(!margins.isEmpty()) Margins = margins.split(" ");
        int l = Margins.length;
        int[] m = new int[l];
        for(int i = 0; i < l; i++) m[i] = Integer.parseInt(Margins[i]);
        switch(l){
            case  4: this.setGeometry(m[0], m[1], m[2], m[3]); break;
            case  3: this.setGeometry(m[0], m[1], m[2], m[1]); break;
            case  2: this.setGeometry(m[0], m[1], m[0], m[1]); break;
            case  1: this.setGeometry(m[0], m[0], m[0], m[0]); break;
            default:
        }
    }

    public void setDigitCount(String count) {
        this.setDigitCount(Integer.parseInt(count));
    }

    public void setStyle(String style){
        switch(style){
            case "outline": this.setSegmentStyle(SegmentStyle.Outline); break;
            case "filled":  this.setSegmentStyle(SegmentStyle.Filled);  break;
            case "flat":    this.setSegmentStyle(SegmentStyle.Flat);    break;
        }
    }
}
