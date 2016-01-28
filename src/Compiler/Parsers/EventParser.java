package Compiler.Parsers;

import org.w3c.dom.NamedNodeMap;
import Compiler.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventParser {

    private boolean hasProps = false;

    public void Events(String file, String name, String component, String param, StringBuilder sb, NamedNodeMap nodeMap){
        component = ComponentFind(component);
        if(!param.isEmpty()) param = String.format("\"%s\"", param);
        sb.append(String.format("\t\t%s %s = new %s(%s)", component, name, component, param));
        PropCheck(file, name, component, "when-action", sb, nodeMap);
        PropCheck(file, name, component, "when-closed", sb, nodeMap);
        PropCheck(file, name, component, "when-context-menu", sb, nodeMap);
        PropCheck(file, name, component, "when-drag-entered", sb, nodeMap);
        PropCheck(file, name, component, "when-drag-left", sb, nodeMap);
        PropCheck(file, name, component, "when-drag-moved", sb, nodeMap);
        PropCheck(file, name, component, "when-dropped", sb, nodeMap);
        PropCheck(file, name, component, "when-in-focus", sb, nodeMap);
        PropCheck(file, name, component, "when-out-of-focus", sb, nodeMap);
        PropCheck(file, name, component, "when-hidden", sb, nodeMap);
        PropCheck(file, name, component, "when-method-input", sb, nodeMap);
        PropCheck(file, name, component, "when-key-pressed", sb, nodeMap);
        PropCheck(file, name, component, "when-key-released", sb, nodeMap);
        PropCheck(file, name, component, "when-moved", sb, nodeMap);
        PropCheck(file, name, component, "when-painted", sb, nodeMap);
        PropCheck(file, name, component, "when-resized", sb, nodeMap);
        PropCheck(file, name, component, "when-shown", sb, nodeMap);
        PropCheck(file, name, component, "when-tablet", sb, nodeMap);
        PropCheck(file, name, component, "when-timed", sb, nodeMap);
        PropCheck(file, name, component, "when-mouse-wheel-moves", sb, nodeMap);
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
        String eventClass = EvenFind(event);
        event = event.replaceAll("-","_");
        String Method = name + "_" + event.replaceAll("when_", "");
        List<String> parameters = new ArrayList<>();
        parameters.add(component + " " + name);
        for (String param : params){
            String[] parts = param.split(":");
            switch(parts[0]){
                case "class": Class = parts[1]; break;
                case "method": Method = parts[1]; break;
                case "component": case"components": parameters.add(parts[1]); break;
            }
        }
        parameters.add(eventClass);
        String[] result = new String[parameters.size() + 2];
        result[0] = Class;
        result[1] = Method;
        System.arraycopy(parameters.toArray(), 0, result, 2, parameters.size());
        return result;
    }

    private String EvenFind(String event){
        String result;
        switch (event){
            case "when-action":             result="QActionEvent event"; break;
            case "when-closed":             result="QCloseEvent event"; break;
            case "when-context-menu":       result="QContextMenuEvent event"; break;
            case "when-drag-entered":       result="QDragEnterEvent event"; break;
            case "when-drag-left":          result="QDragLeaveEvent event"; break;
            case "when-drag-moved":         result="QDragMoveEvent event"; break;
            case "when-dropped":            result="QDropEvent event"; break;
            case "when-in-focus":           result="QFocusInEvent event"; break;
            case "when-out-of-focus":       result="QFocusOutEvent event"; break;
            case "when-hidden":             result="QHideEvent event"; break;
            case "when-method-input":       result="QInputMethodEvent event"; break;
            case "when-key-pressed":        result="QKeyPressEvent event"; break;
            case "when-key-released":       result="QKeyReleasedEvent event"; break;
            case "when-moved":              result="QMovedEvent event"; break;
            case "when-painted":            result="QPaintEvent event"; break;
            case "when-resized":            result="QResizeEvent event"; break;
            case "when-shown":              result="QShowEvent event"; break;
            case "when-tablet":             result="QTabletEvent event"; break;
            case "when-timed":              result="QTimerEvent event"; break;
            case "when-mouse-wheel-moves":  result="QWheelEvent event"; break;
            case "when-mouse-is-released":
            case "when-mouse-is-pressed":
            case "when-mouse-moves":
            case "when-double-clicked":     result="QMouseEvent event"; break;
            case "when-left":
            case "when-entered":
            case "when-change":
            default:                        result="QEvent event"; break;
        }
        return result;
    }

    private String ComponentFind(String name){
        String result = "";
        switch(name){
            case "number": result = "QLCDNumber"; break;
            case "window": result = "QMainWindow"; break;
            case "button": result = "QPushButton"; break;
            case "radio": result = "QRadioButton"; break;
            case "check-box": result = "QCheckBox"; break;
            case "grid": result = "QGridLayout"; break;
            case "slider": result = "QSlider"; break;
        }
        return result;
    }

    private void Write(String[] Params, StringBuilder sb){
        String[] yourArray = Arrays.copyOfRange(Params, 2, Params.length);
        WriteClass(Params[0], sb);
        WriteMethod(Params[1], sb, yourArray);
        WriteParams(sb, yourArray);
    }

    private void WriteClass(String name, StringBuilder sb){
        sb.append(String.format("\t\t\ttry{\n\t\t\t\tClass<?> c = Class.forName(\"%s\");\n", name));
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
        sb.append(String.format("\t\t\t\tMethod method = c.getDeclaredMethod(\"%s\"", name));
        for(String Class : params) sb.append(String.format(", %s.class", Class));
        sb.append(");\n");
    }

    private void WriteParams(StringBuilder sb, String... params){
        sb.append("\t\t\t\tmethod.invoke(c");
        for(String param : params) sb.append(String.format(", %s", param));
        sb.append(");\n\t\t\t} catch ( ReflectiveOperationException e) {\n\t\t\t\te.printStackTrace();\n\t\t\t}\n");
    }
}
