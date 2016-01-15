package EventClass;

import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.gui.QWidget;

/**
 * Created by Caleb Bain on 1/15/2016.
 */
public abstract class Events {

    public void windowWhenMousePressed(QWidget component, QEvent event){}
    public void windowWhenMenuOpens(QWidget component, QEvent event){}
    public void windowWhenShows(QWidget component, QEvent event){}

}
