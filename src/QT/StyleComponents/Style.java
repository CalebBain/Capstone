package QT.StyleComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Style {
    private String name;
    private String fullName;
    private List<String> nameAttributes = new ArrayList<>();
    private String subControl = "";
    private String component;
    private boolean special;
    private Map<String, String> attributes = new HashMap<>();

    public Style(String name, boolean special) {
        parseName(setName(name));
        this.special = special;
    }

    public Style(String name, String component, boolean special) {
        parseName(setName(name));
        this.component = component;
        this.special = special;
    }

    private void parseName(String name) {
        name = name.replaceAll("\\.", "");
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

    private String setName(String name) {
        Map<String, String> components = new HashMap<String, String>() {{
            put("button", "QPushButton");
            put("window", "QMainWindow");
            put("number", "QLCDNumber");
            put("radio", "QRadioButton");
            put("slider", "QSlider");
            put("check-box", "QCheckBox");
            put("tri-state", "QTriState");
            put("menu-bar", "QMenuBar");
        }};
        for (Map.Entry<String, String> entry : components.entrySet())
            if (name.startsWith(entry.getKey()))
                name = name.replaceFirst(entry.getKey(), entry.getValue());
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

    public void setAttributes(String attributes) {
        String[] parts = attributes.split("; ");
        for (String part : parts) {
            part = part.replaceAll(";", "");
            String[] params = part.split(":");
            this.attributes.put(params[0], params[1]);
        }
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public void setSubControl(String subControl) {
        this.subControl = subControl;
    }

    public void addNameAttributes(String prop) {
        if (!nameAttributes.contains(prop)) nameAttributes.add(prop);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(component);
        if (special) sb.append("#" + name);

        if (!subControl.isEmpty()) sb.append("::" + subControl);
        for (String nameAttribute : nameAttributes) sb.append(":" + nameAttribute);
        sb.append("\n{\n");
        for (Map.Entry<String, String> attribute : attributes.entrySet()) {
            sb.append(attribute.getKey() + ":");
            sb.append(attribute.getValue() + ";\n");
        }
        sb.append("}\n\n");
        return sb.toString();
    }
}
