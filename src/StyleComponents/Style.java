package StyleComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Style {
    private String name;
    private List<String> nameAttributes;
    private String subControl;
    private String component;
    private boolean special;
    private Map<String, String> attributes = new HashMap<>();

    public Style(String name, boolean special) {
        parseName(name);
        this.nameAttributes = new ArrayList<>();
        this.special = special;
    }

    public Style(String name, String component, boolean special) {
        parseName(name);
        this.nameAttributes = new ArrayList<>();
        this.component = component;
        this.special = special;
    }

    private void parseName(String name){
        name = name.replaceAll(".", "");
        String[] temp;
        if (name.contains("::")) {
            temp = name.split("::");
            this.name = temp[0];
            if (temp[1].contains(":")) {
                temp = name.split(":");
                setSubControl(temp[0]);
                for (int i = 1; i < temp.length; i++) this.addNameAttributes(temp[i]);
            }
        }else if (name.contains(":")) {
            temp = name.split(":");
            this.name = temp[0];
            for (int i = 1; i < temp.length; i++) this.addNameAttributes(temp[i]);
        }else this.name = name;
    }

    public void addAll(Style style) {
        this.attributes.putAll(style.getAttributes());
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getAttributes() {
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

    public void addNameAttributes(String prop){
        if(!nameAttributes.contains(prop)) nameAttributes.add(prop);
    }

    public boolean isSpecial() {
        return special;
    }
}
