package StyleComponents;

import java.util.List;

/**
 * Created by Caleb Bain on 1/9/2016.
 */
public class Style {
    private final String name;
    private List<String> attrabutes;

    public Style(String name) {
        this.name = name;
    }

    public Style(String name, List<String> attrabutes) {
        this.name = name;
        this.attrabutes = attrabutes;
    }


}
