package QtComponents;

import com.trolltech.qt.gui.QLCDNumber;
import com.trolltech.qt.gui.QWidget;

/**
 * Created by Caleb Bain on 1/8/2016.
 */
public class Number extends QLCDNumber{

    public Number(QWidget parent) {
        super(parent);
        this.setSmallDecimalPoint(true);
    }

    public void setHeight(String height) {
        this.setHeight(height);
    }

    public void setWidth(String width) {
        this.setWidth(width);
    }

    public void setSize(String width, String height) {
        try {
            this.resize(((!width.isEmpty())? Integer.parseInt(width) : 20), ((!height.isEmpty())? Integer.parseInt(height) : 10));
        } catch (NumberFormatException nfe){}
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
