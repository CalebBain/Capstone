package QtComponents;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QFontDatabase;

import java.util.List;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class Font extends QFont {
    private QFontDatabase database = new QFontDatabase();
    private String family;

    public Font() {
    }

    public Font(String family, String size, String style, String weight, String decoration) {
        setFontFamily(family);
        setFontSize(size);
        setTextDecoration(decoration);
        setFontStyle(style);
        setFontWeight(weight);
    }

    public void setFontFamily(String family) {
        List<String> families = database.families();
        this.setFamily((families.contains(family)) ? family : this.defaultFamily());
        this.family = family;
    }
    public void setFontSize(String size){
        try {
            setFontSize(Integer.parseInt(size));
        }catch (NumberFormatException nfe) {
            this.setPixelSize(10);
        }
    }

    public void setFontSize(int size){
        List<Integer> sizes = database.pointSizes(family);
        this.setPixelSize(sizes.contains(size) ? size : 10);
    }

    public void setTextDecoration(String decoration){
        this.setUnderline(decoration.contains("underline"));
        this.setOverline(decoration.contains("overline"));
        this.setStrikeOut(decoration.contains("strike-out"));
    }

    public void setFontStyle(String style){
        switch (style){
            case "italic":  this.setStyle(Style.StyleItalic);  break;
            case "Oblique": this.setStyle(Style.StyleOblique); break;
            default:        this.setStyle(Style.StyleNormal);
        }
    }

    public int setFontWeight(String keyword){
        int weight;
        try {
            weight = Integer.parseInt(keyword);
        }catch (NumberFormatException nfe){
            switch(keyword){
                case "lighter":     weight = 15; break;
                case "light":       weight = 25; break;
                case "bold":        weight = 75; break;
                case "bolder":      weight = 85; break;
                default:            weight = 50;
            }
        }
        return weight;
    }
}
