package EventClass;

import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.gui.QWidget;

/**
 * Created by Caleb Bain on 1/14/2016.
 */
public final class EventDelegate {
    private EventLookUp lookup = new EventLookUp();
    private final String EventType;
    private final QWidget component;

    public EventDelegate(String EventType, QWidget component){
        this.EventType = EventType;
        this.component = component;

    }

    public void doTask(QEvent event){
        lookup.getBusinessService(EventType, component, event);
    }
}

final class EventLookUp {

    public void getBusinessService(String EventType, QWidget component, QEvent event){
        Events events = new Events(){};
        switch(EventType){
            case "window-mouse-pressed": events.windowWhenMousePressed(component, event); break;
            case "window-context-menu":  events.windowWhenMenuOpens(component, event);    break;
            case "window-shows":         events.windowWhenShows(component, event);        break;
        }
    }
}