package QtComponents;

import com.trolltech.qt.core.QObject;
import org.w3c.dom.Node;

/**
 * Created by Caleb Bain on 1/9/2016.
 */
public interface Component {

    public String setStyle();

    public String Name();

    public String Class();

    public String Component();

    public QObject Widgit();

    public void SetStylesheet(String sheet);

    public void addChild(QObject child, Node node);

}
