package StyleComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Style {
    private String name;
    private String fullName;
    private List<String> nameAttributes;
    private String subControl;
    private String component;
    private boolean special;
    private Map<String, String> attributes = new HashMap<>();

    public Style(String name, boolean special) {
        parseName(setName(name));
        this.nameAttributes = new ArrayList<>();
        this.special = special;
    }

    public Style(String name, String component, boolean special) {
        parseName(setName(name));
        this.nameAttributes = new ArrayList<>();
        this.component = component;
        this.special = special;
    }

    private void parseName(String name) {
        name = name.replaceAll(".", "");
        fullName = name;
        String[] temp;
        if (name.contains("::")) {
            temp = name.split("::");
            this.name = temp[0];
            if (temp[1].contains(":")) {
                temp = name.split(":");
                setSubControl(temp[0]);
                for (int i = 1; i < temp.length; i++)
                    if (!nameAttributes.contains(temp[i]))
                        this.addNameAttributes(temp[i]);
            }
        } else if (name.contains(":")) {
            temp = name.split(":");
            this.name = temp[0];
            for (int i = 1; i < temp.length; i++)
                if (!nameAttributes.contains(temp[i]))
                    this.addNameAttributes(temp[i]);
        } else this.name = name;
    }

    private String setName(String name){
        Map<String, String> components = new HashMap<String, String>(){{
            put("button", "QPushButton");
            put("window","QPushButton");
            put("number", "QLCDNumber");
            put("radio", "QRadioButton");
            put("slider", "QSlider");
            put("check-box", "QCheckBox");
        }};
        for(Map.Entry<String, String> entry : components.entrySet()) if(name.startsWith(entry.getKey())) name = name.replaceFirst(entry.getKey(),entry.getValue());
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public void addAll(Style style) {
        this.attributes.putAll(style.getAttributes());
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public void setAttributes(String attributes){
        String[] parts = attributes.split(" ");
        for (String part : parts) {
            part = part.replaceAll(";", "");
            String[] params = part.split(":");
            this.attributes.put(params[0], params[1]);
        }
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

    public void addNameAttributes(String prop) {
        if (!nameAttributes.contains(prop)) nameAttributes.add(prop);
    }

    public List<String> getNameAttributes() {
        return nameAttributes;
    }

    public boolean isSpecial() {
        return special;
    }
}
