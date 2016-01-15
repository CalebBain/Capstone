package EventClass;

import com.trolltech.qt.core.QEvent;

/**
 * Created by Caleb Bain on 1/14/2016.
 */
public abstract class ButtonEvent implements EventService {

    @Override
    public void whenEvent(QEvent event) {
        System.out.println("Button Event running");
    }
}
