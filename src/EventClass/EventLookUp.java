package EventClass;

/**
 * Created by Caleb Bain on 1/14/2016.
 */
public final class EventLookUp {

    public EventService getBusinessService(String serviceType){

        switch(serviceType){
            case "window": return new WindowEvent(){};
            case "button": return new ButtonEvent(){};
        }
        return null;
    }
}
