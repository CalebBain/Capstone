package Compiler.Parsers;

import org.w3c.dom.NamedNodeMap;
import Compiler.Utils;

import java.util.*;

public final class EventParser {

    private boolean hasProps = false;

    private Map<String, String> events = new HashMap<String, String>(){{
        put("when-action", "actionEvent");
        put("when-closed", "closeEvent");
        put("when-context-menu", "contextMenuEvent");
        put("when-drag-entered", "dragEnterEvent");
        put("when-drag-left", "dragLeaveEvent");
        put("when-drag-moves", "dragMoveEvent");
        put("when-dropped", "dropEvent");
        put("when-in-focus", "focusInEvent");
        put("when-out-of-focus", "focusOutEvent");
        put("when-hidden", "hideEvent");
        put("when-method-input", "inputMethodEvent");
        put("when-key-pressed", "keyPressEvent");
        put("when-key-release", "keyReleaseEvent");
        put("when-moved", "moveEvent");
        put("when-painted", "paintEvent");
        put("when-resized", "resizeEvent");
        put("when-shown", "showEvent");
        put("when-touch-screen", "tabletEvent");
        put("when-timed", "timerEvent");
        put("when-mouse-wheel-moves", "wheelEvent");
    }};

    public void Events(String file, String name, String component, StringBuilder sb, NamedNodeMap nodeMap){
        component = Utils.setName(component);
        sb.append(String.format("\t\t%s %s = new %s(this)", component, name, component));
        callEvents(file, name, component, sb, nodeMap);
    }

    public void Events(String file, String name, String component, String param, StringBuilder sb, NamedNodeMap nodeMap){
        component = Utils.setName(component);
        if(!param.isEmpty()) param = String.format("\"%s\"", param);
        sb.append(String.format("\t\t%s %s = new %s(%s)", component, name, component, param));
        callEvents(file, name, component, sb, nodeMap);
    }

    private void callEvents(String file, String name, String component, StringBuilder sb, NamedNodeMap nodeMap){
        for(String prop : events.keySet()) PropCheck(file, name, component, prop, sb, nodeMap);
        PropCheck(file, name, component, "when-mouse-is-released", sb, nodeMap);
        PropCheck(file, name, component, "when-mouse-is-pressed", sb, nodeMap);
        PropCheck(file, name, component, "when-mouse-moves", sb, nodeMap);
        PropCheck(file, name, component, "when-double-clicked", sb, nodeMap);
        PropCheck(file, name, component, "when-left", sb, nodeMap);
        PropCheck(file, name, component, "when-entered", sb, nodeMap);
        PropCheck(file, name, component, "when-change", sb, nodeMap);
        if(hasProps){
            hasProps = false;
            sb.append("\t\t}");
        }
        sb.append(";\n");
    }

    private void PropCheck(String file, String name, String component, String command, StringBuilder sb, NamedNodeMap nodeMap){
        if(Utils.exists(command, nodeMap)){
            String[] props = Utils.check(command, nodeMap).split(" ");
            if(!hasProps) {
                hasProps = true;
                sb.append("{\n");
            }
            String[] array = ParamSwitch(file, name, component, command, props);
            Write(array, sb);
        }
    }

    private String[] ParamSwitch(String file, String name, String component, String event, String... params){
        String Class = file.replaceAll(".jaml", "");
        name = name.replaceAll("-","_");
        String Event = event;
        event = event.replaceAll("-","_");
        String Method = name + "_" + event.replaceAll("when_", "");
        List<String> parameters = new ArrayList<>();
        for (String param : params){
            String[] parts = param.split(":");
            switch(parts[0]){
                case "class": Class = parts[1]; break;
                case "method": Method = parts[1]; break;
                case "component": case"components": parameters.add(parts[1]); break;
            }
        }
        String[] result = new String[parameters.size() + 3];
        result[0] = Event;
        result[1] = Class;
        result[2] = Method;
        System.arraycopy(parameters.toArray(), 0, result, 2, parameters.size());
        return result;
    }

    private String EventFind(String event, boolean prop){
        String result = "";
        if(events.containsKey(event)){
            String Event = events.get(event);
            if (prop) result = String.format("Q%s event", Utils.capitalize(Event));
            else result = Event;
        }else {
            switch (event){
                case "when-mouse-is-released": result = (prop) ? "QMouseEvent event" : "mouseReleaseEvent"; break;
                case "when-mouse-is-pressed": result = (prop) ? "QMouseEvent event" : "mousePressEvent"; break;
                case "when-mouse-moves": result = (prop) ? "QMouseEvent event" : "mouseMoveEvent"; break;
                case "when-double-clicked": result = (prop) ? "QMouseEvent event" : "mouseDoubleClickEvent"; break;
                case "when-left": result = (prop) ? "QEvent event" : "leaveEvent"; break;
                case "when-entered": result = (prop) ? "QEvent event" : "enterEvent"; break;
                case "when-change": result = (prop) ? "QEvent event" : "changeEvent"; break;
            }
        }
        return result;
    }

    private void Write(String[] Params, StringBuilder sb){
        String[] yourArray = Arrays.copyOfRange(Params, 2, Params.length);
        sb.append(String.format("\t\t\t@Override\n" +
                    "\t\t\tprotected void %s(%s) {\n", EventFind(Params[0], false), EventFind(Params[0], true)));
        WriteClass(Params[1], sb);
        WriteMethod(Params[2], sb, yourArray);
        WriteParams(sb, yourArray);
        sb.append("\t\t\t}\n");
    }

    private void WriteClass(String name, StringBuilder sb){
        sb.append(String.format("\t\t\t\ttry{\n\t\t\t\t\tClass<?> c = Class.forName(\"%s\");\n", name));
    }

    private String[] getClasses(String... params){
        String[] result = new String[params.length];
        for (int i = 0; i < params.length; i++){
            String[] param = params[i].split(" ");
            result[i] = param[0];
        }
        return result;
    }

    private void WriteMethod(String name, StringBuilder sb, String... params){
        params = getClasses(params);
        sb.append(String.format("\t\t\t\t\tMethod method = c.getDeclaredMethod(\"%s\"", name));
        for(String Class : params) sb.append(String.format(", %s.class", Class));
        sb.append(");\n");
    }

    private void WriteParams(StringBuilder sb, String... params){
        sb.append("\t\t\t\t\tmethod.invoke(c, this");
        for(String param : params) sb.append(String.format(", %s", param));
        sb.append(", event);\n" +
                "\t\t\t\t} catch ( ReflectiveOperationException e) {\n" +
                "\t\t\t\t\te.printStackTrace();\n" +
                "\t\t\t\t}\n");
    }
}
