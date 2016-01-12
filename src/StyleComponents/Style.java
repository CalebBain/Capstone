package StyleComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Style {
    private final String name;
    private List<String> nameAttributes;
    private String subControl;
    private String component;
    private boolean special;
    private Map<String, String> attributes = new HashMap<>();

    public Style(String name, boolean special) {
        this.name = name;
        this.nameAttributes = new ArrayList<>();
        this.special = special;
    }

    public Style(String name, String component, boolean special) {
        this.name = name;
        this.nameAttributes = new ArrayList<>();
        this.component = component;
        this.special = special;
    }

    public void addAll(Style style) {
        this.attributes.putAll(style.getAttrabutes());
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getAttrabutes() {
        return attributes;
    }

    public String getSubControl() {
        return subControl;
    }

    public void setSubControl(String subControl) {
        this.subControl = subControl;
    }

    public String getComponent() {
        return component;
    }

    public List<String> getNameAttributes() {
        return nameAttributes;
    }

    public void setNameAttributes(List<String> nameAttributes) {
        this.nameAttributes = nameAttributes;
    }

    public void addNameAttributes(String prop){
        if(!nameAttributes.contains(prop)) nameAttributes.add(prop);
    }

    public boolean isSpecial() {
        return special;
    }
}
