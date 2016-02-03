package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;

public final class FunctionParser {

    public void MakeFunc(String name, String prop, StringBuilder sb, NamedNodeMap nodeMap){
        String[] callParts = new String[0];
        String p;
        if (!(p = Utils.check(prop, nodeMap)).isEmpty()){
            if (!p.isEmpty()) callParts = p.split(":");
            if(callParts.length == 1) sb.append(String.format("%s.connect(this, \"%s\");\n", name, callParts[0]));
            else if(callParts.length == 2) sb.append(String.format("%s.connect(%s, \"%s\");\n", name, callParts[0], callParts[1]));
        }
    }

    public void onAbstractSliderFunctions(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(name + ".valueChanged", "on-value-change", sb, nodeMap);
        MakeFunc(name + ".sliderReleased", "on-release", sb, nodeMap);
        MakeFunc(name + ".sliderMoved", "on-move", sb, nodeMap);
        MakeFunc(name + ".sliderPressed", "on-press", sb, nodeMap);
        MakeFunc(name + ".actionTriggered", "on-action-trigger", sb, nodeMap);
        MakeFunc(name + ".actionTriggered", "on-range-change", sb, nodeMap);
        onWidgetFunctions(name, sb, nodeMap);
    }

    public void CheckBoxFunctions(String n, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(n + ".stateChanged", Utils.check("on-state-change", nodeMap), sb, nodeMap);
        onAbstractItemViewFunctions(n, sb, nodeMap);
    }

    public void onAbstractButtonFunctions(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(name + ".clicked", "on-click", sb, nodeMap);
        MakeFunc(name + ".released", "on-release", sb, nodeMap);
        MakeFunc(name + ".pressed", "on-press", sb, nodeMap);
        MakeFunc(name + ".toggled", "on-toggle", sb, nodeMap);
        onWidgetFunctions(name, sb, nodeMap);
    }

    public void ColumnViewFunctions(String n, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(n + ".updatePreviewWidget", "on-preview-update", sb, nodeMap);
        onAbstractItemViewFunctions(n, sb, nodeMap);
    }

    public void onAbstractItemViewFunctions(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(name + ".clicked", "on-click", sb, nodeMap);
        MakeFunc(name + ".pressed", "on-press", sb, nodeMap);
        MakeFunc(name + ".doubleClicked", "on-double-click", sb, nodeMap);
        MakeFunc(name + ".activated", "on-activate", sb, nodeMap);
        MakeFunc(name + ".entered", "on-enter", sb, nodeMap);
        MakeFunc(name + ".viewportEntered", "on-viewport-enter", sb, nodeMap);
        onWidgetFunctions(name, sb, nodeMap);
    }

    public void MenuBarFunctions(String n, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(n + ".hovered", "on-hover", sb, nodeMap);
        MakeFunc(n + ".triggered", "on-trigger", sb, nodeMap);
        onWidgetFunctions(n, sb, nodeMap);
    }

    public void MenuFunctions(String n, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(n + ".aboutToHide", "on-hide", sb, nodeMap);
        MakeFunc(n + ".aboutToShow", "on-show", sb, nodeMap);
        MakeFunc(n + ".hovered", "on-hover", sb, nodeMap);
        MakeFunc(n + ".triggered", "on-trigger", sb, nodeMap);
        onWidgetFunctions(n, sb, nodeMap);
    }

    public void onWidgetFunctions(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(name + ".customContextMenuRequested", "on-custom-context-menu-request", sb, nodeMap);
    }


}
