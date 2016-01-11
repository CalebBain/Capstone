package StyleComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Style {
    private final String name;
    private final String nameAttrabute;
    private List<String> PseudoStates = new ArrayList<>();
    private Map<String, String> attrabutes = new HashMap<>();

    public Style(String name) {
        this.name = name;
        this.nameAttrabute = "";
    }

    public Style(String name, String nameAttrabute) {
        this.name = name;
        this.nameAttrabute = nameAttrabute;
    }

    public void addPseudoState(String state){
        PseudoStates.add(state);
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
