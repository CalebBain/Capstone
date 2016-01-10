package StyleComponents;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caleb Bain on 1/9/2016.
 */
public class Style {
    private final String name;

    private Map<String, String> attrabutes = new HashMap<>();

    public Style(String name) {
        this.name = name;
    }

    public void addAttrabute(String key, String value) {
        attrabutes.put(key, value);
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getAttrabutes() {
        return attrabutes;
    }
}
