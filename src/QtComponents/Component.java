package QtComponents;

import com.trolltech.qt.gui.QWidget;

/**
 * Created by Caleb Bain on 1/9/2016.
 */
public interface Component {
    public String Id();

    public String Name();

    public String Class();

    public String Component();

    public QWidget Widgit();

    public void setStyle();

    public void SetStylesheet();

}