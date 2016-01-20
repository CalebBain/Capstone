package QT.QtComponents.ToolbarComponents;

import QT.QtComponents.Component;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QWidget;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/19/2016.
 */
public class Action extends QAction implements Component {

    public Action(QWidget parent) {
        super(parent);
    }

    public String setStyle() {
        return null;
    }

    public String Name() {
        return null;
    }

    public String Class() {
        return null;
    }

    public String Component() {
        return null;
    }

    public QObject Widgit() {
        return null;
    }

    public void SetStylesheet(String sheet) {

    }

    public void addChild(Component child, Node node) {

    }
}
