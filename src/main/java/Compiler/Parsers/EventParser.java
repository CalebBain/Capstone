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

    public boolean ActionEvents(String file, String name, String component, String param, String methods, StringBuilder sb, NamedNodeMap nodeMap){
        component = Utils.setName(component);
        if(!param.isEmpty()) param = String.format("\"%s\"", param);
        sb.append(String.format("%s %s = new %s(%s, this)", component, name, component, param));
        try{
            if(!methods.isEmpty()){
                hasProps = true;
                sb.append(String.format("{\n%s", methods));
            }
        }catch (NullPointerException ignored){
        }
        callEvents(file, name, sb, nodeMap);
        return hasProps;
    }

    public boolean Events(String file, String name, String component, String param, String methods, StringBuilder sb, NamedNodeMap nodeMap){
        component = Utils.setName(component);
        if(!param.isEmpty()) param = String.format("\"%s\"", param);
        sb.append(String.format("%s %s = new %s(%s)", component, name, component, param));
        try{
            if(!methods.isEmpty()){
                hasProps = true;
                sb.append(String.format("{\n%s", methods));
            }
        }catch (NullPointerException ignored){
        }
        callEvents(file, name, sb, nodeMap);
        return hasProps;
    }

    private void callEvents(String file, String name, StringBuilder sb, NamedNodeMap nodeMap){
        for(String prop : events.keySet()) PropCheck(file, name, prop, sb, nodeMap);
        PropCheck(file, name, "when-mouse-is-released", sb, nodeMap);
        PropCheck(file, name, "when-mouse-is-pressed", sb, nodeMap);
        PropCheck(file, name, "when-mouse-moves", sb, nodeMap);
        PropCheck(file, name, "when-double-clicked", sb, nodeMap);
        PropCheck(file, name, "when-left", sb, nodeMap);
        PropCheck(file, name, "when-entered", sb, nodeMap);
        PropCheck(file, name, "when-change", sb, nodeMap);
        if(hasProps){
            hasProps = false;
            sb.append("}");
        }
        sb.append(";\n");
    }

    private void PropCheck(String file, String name, String command, StringBuilder sb, NamedNodeMap nodeMap){
        if(Utils.exists(command, nodeMap)){
            String[] props = Utils.check(command, nodeMap).split(" ");
            if(!hasProps) {
                hasProps = true;
                sb.append("{\n");
            }
            String[] array = ParamSwitch(file, name, command, props);
            Write(array, sb);
        }
    }

    private String[] ParamSwitch(String file, String name, String event, String... params){
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
            }
        }
        String[] result = new String[parameters.size() + 3];
        result[0] = Event;
        result[1] = Class;
        result[2] = Method;
        System.arraycopy(parameters.toArray(), 0, result, 2, parameters.size());
        return result;
    }

    private String EventFind(String event, boolean prop, boolean complete){
        String result = "";
        if(events.containsKey(event)){
            String Event = events.get(event);
            if (prop) result = String.format(" Q%s", Utils.capitalize(Event));
            else result = Event;
        }else {
            switch (event){
                case "when-mouse-is-released": result = (prop) ? "QMouseEvent" : "mouseReleaseEvent"; break;
                case "when-mouse-is-pressed": result = (prop) ? "QMouseEvent" : "mousePressEvent"; break;
                case "when-mouse-moves": result = (prop) ? "QMouseEvent" : "mouseMoveEvent"; break;
                case "when-double-clicked": result = (prop) ? "QMouseEvent" : "mouseDoubleClickEvent"; break;
                case "when-left": result = (prop) ? "QEvent" : "leaveEvent"; break;
                case "when-entered": result = (prop) ? "QEvent" : "enterEvent"; break;
                case "when-change": result = (prop) ? "QEvent" : "changeEvent"; break;
            }
        }
        if(complete) result += " event";
        return result;
    }

    private void Write(String[] Params, StringBuilder sb){
        sb.append(String.format("protected void %s(%s) {\n",
                EventFind(Params[0], false, false), EventFind(Params[0], true, true)));
        String prop1 = Params[1], prop2 = Params[2];
        if(prop2.contains("(")) sb.append(String.format("new %s().%s;\n", prop1, prop2));
        else sb.append(String.format("new %s().%s(this, event);\n", prop1, prop2));
        sb.append("}\n");
    }
}
