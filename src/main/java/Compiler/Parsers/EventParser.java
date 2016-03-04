package Compiler.Parsers;

import org.w3c.dom.NamedNodeMap;
import Compiler.Utils;
import org.w3c.dom.Node;

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

    public boolean Events(String file, String name, String component, String param, String methods, StringBuilder sb, NamedNodeMap nodeMap){
        component = Utils.setName(component);
        if(!param.isEmpty()) param = String.format("\"%s\"", param);
        sb.append(String.format("final %s %s = new %s(%s)", component, name, component, param));
        try{
            if(!methods.isEmpty()){
                hasProps = true;
                sb.append(String.format("{\n%s", methods));
            }
        }catch (NullPointerException ignored){}
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
        return hasProps;
    }

    private void PropCheck(String file, String name, String command, StringBuilder sb, NamedNodeMap nodeMap){
        if(exists(command, nodeMap)){
            String[] props = Utils.check(command, nodeMap).split(" ");
            if(!hasProps) {
                hasProps = true;
                sb.append("{\n");
            }
            String Class = file, Event = command,
                Method = name.replaceAll("-","_") + "_" + command.replaceAll("when_", "").replaceAll("-", "_");
            for (String param : props){
                String[] parts = param.split(":");
                if(parts[0].equals("class")) Class = parts[1];
                if(parts[0].equals("method")) Method = parts[1];
            }
            sb.append(String.format("protected void %s(%s) { new %s().%s(this, event); }\n",
                    EventFind(Event, false), EventFind(Event, true), Class, Method));
        }
    }

    private String EventFind(String event, boolean prop){
        String result = "";
        if(events.containsKey(event)){
            String Event = events.get(event);
            if (prop) result = String.format(" Q%s", Utils.capitalize(Event));
            else result = Event;
        } else {
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
        if(prop) result += " event";
        return result;
    }

    public boolean exists(String keyword, NamedNodeMap nodeMap){
        try {
            Node word = nodeMap.getNamedItem(keyword);
            word.getNodeValue();
            return true;
        } catch (NullPointerException ignored) {
            return false;
        }
    }
}
