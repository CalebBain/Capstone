package EventClass;

import com.trolltech.qt.core.QEvent;

/**
 * Created by Caleb Bain on 1/14/2016.
 */
public final class EventDelegate {
    private EventLookUp lookup = new EventLookUp();
    private EventService eventService;
    private String eventType;

    public EventDelegate(String eventType){
        this.eventType = eventType;
    }

    public void doTask(QEvent event){
        eventService = lookup.getBusinessService(eventType);
        eventService.whenEvent(event);
    }
}
