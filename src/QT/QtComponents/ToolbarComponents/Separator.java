package QT.QtComponents.ToolbarComponents;

import QT.QtComponents.Component;
import com.trolltech.qt.core.QObject;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/19/2016.
 */
public class Separator implements Component{
    public String setStyle() {
        return "";
    }

    public String Name() {
        return "";
    }

    public String Class() {
        return "";
    }

    public String Component() {
        return "separator";
    }

    public QObject Widgit() {
        return null;
    }

    public void SetStylesheet(String sheet) {
    }

    public void addChild(Component child, Node node) {
    }
}
