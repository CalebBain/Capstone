package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;

public final class FunctionParser {

    private void MakeFunc(String name, String prop, StringBuilder sb, NamedNodeMap nodeMap){
        String[] callParts = new String[0];
        String p;
        if (!(p = Utils.check(prop, nodeMap)).isEmpty()){
            if (!p.isEmpty()) callParts = p.split(":");
            if(callParts.length == 1) sb.append(String.format("%s.connect(this, \"%s\");\n", name, callParts[0]));
            else if(callParts.length == 2) sb.append(String.format("%s.connect(%s, \"%s\");\n", name, callParts[0], callParts[1]));
        }
    }

    public void list(String n, StringBuilder sb, NamedNodeMap nodeMap) {
        MakeFunc(n + ".currentItemChanged", "on-current-item-change", sb, nodeMap);
        MakeFunc(n + ".currentRowChanged", "on-current-row-change", sb, nodeMap);
        MakeFunc(n + ".currentTextChanged", "on-current-text-change", sb, nodeMap);
        MakeFunc(n + ".itemActivated", "on-active-item", sb, nodeMap);
        MakeFunc(n + ".itemChanged", "on-item-change", sb, nodeMap);
        MakeFunc(n + ".itemClicked", "on-item-click", sb, nodeMap);
        MakeFunc(n + ".itemDoubleClicked", "on-item-double-click", sb, nodeMap);
        MakeFunc(n + ".itemEntered", "on-item-enter", sb, nodeMap);
        MakeFunc(n + ".itemPressed", "on-item-press", sb, nodeMap);
        MakeFunc(n + ".itemSelectionChanged", "on-item-selection-change", sb, nodeMap);
        ListView(n, sb, nodeMap);
    }

    private void ListView(String n, StringBuilder sb, NamedNodeMap nodeMap) {
        MakeFunc(n + ".indexesMoved", Utils.check("on-index-move", nodeMap), sb, nodeMap);
        AbstractItemView(n, sb, nodeMap);
    }

    protected void CheckBox(String n, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(n + ".stateChanged", Utils.check("on-state-change", nodeMap), sb, nodeMap);
        AbstractItemView(n, sb, nodeMap);
    }

    protected void AbstractItemView(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(name + ".clicked", "on-click", sb, nodeMap);
        MakeFunc(name + ".pressed", "on-press", sb, nodeMap);
        MakeFunc(name + ".doubleClicked", "on-double-click", sb, nodeMap);
        MakeFunc(name + ".activated", "on-activate", sb, nodeMap);
        MakeFunc(name + ".entered", "on-enter", sb, nodeMap);
        MakeFunc(name + ".viewportEntered", "on-viewport-enter", sb, nodeMap);
        Widget(name, sb, nodeMap);
    }

    protected void AbstractSlider(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(name + ".valueChanged", "on-value-change", sb, nodeMap);
        MakeFunc(name + ".sliderReleased", "on-release", sb, nodeMap);
        MakeFunc(name + ".sliderMoved", "on-move", sb, nodeMap);
        MakeFunc(name + ".sliderPressed", "on-press", sb, nodeMap);
        MakeFunc(name + ".actionTriggered", "on-action-trigger", sb, nodeMap);
        MakeFunc(name + ".actionTriggered", "on-range-change", sb, nodeMap);
        Widget(name, sb, nodeMap);
    }

    protected void AbstractButton(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(name + ".clicked", "on-click", sb, nodeMap);
        MakeFunc(name + ".released", "on-release", sb, nodeMap);
        MakeFunc(name + ".pressed", "on-press", sb, nodeMap);
        MakeFunc(name + ".toggled", "on-toggle", sb, nodeMap);
        Widget(name, sb, nodeMap);
    }

    protected void MenuBar(String n, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(n + ".hovered", "on-hover", sb, nodeMap);
        MakeFunc(n + ".triggered", "on-trigger", sb, nodeMap);
        Widget(n, sb, nodeMap);
    }

    protected void Menu(String n, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(n + ".aboutToHide", "on-hide", sb, nodeMap);
        MakeFunc(n + ".aboutToShow", "on-show", sb, nodeMap);
        MakeFunc(n + ".hovered", "on-hover", sb, nodeMap);
        MakeFunc(n + ".triggered", "on-trigger", sb, nodeMap);
        Widget(n, sb, nodeMap);
    }

    protected void Window(String n, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(n + ".iconSizeChanged", Utils.check("on-icon-size-change", nodeMap), sb, nodeMap);
        MakeFunc(n + ".toolButtonStyleChanged", Utils.check("on-tool-button-style-change", nodeMap), sb, nodeMap);
        Widget(n, sb, nodeMap);
    }

    protected void Number(String n, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(n + ".overflow", Utils.check("on-overflow", nodeMap), sb, nodeMap);
        Widget(n, sb, nodeMap);
    }

    protected void Label(String n, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(n + ".linkActivated", Utils.check("on-active", nodeMap), sb, nodeMap);
        MakeFunc(n + ".linkHovered", Utils.check("on-hover", nodeMap), sb, nodeMap);
        Widget(n, sb, nodeMap);
    }

    protected void Splitter(String n, StringBuilder sb, NamedNodeMap nodeMap) {
        MakeFunc(n + ".splitterMoved", Utils.check("on-move", nodeMap), sb, nodeMap);
        Widget(n, sb, nodeMap);
    }


    public void LineEdit(String n, StringBuilder sb, NamedNodeMap nodeMap) {
        MakeFunc(n + ".cursorPositionChanged", Utils.check("on-cursor-move", nodeMap), sb, nodeMap);
        MakeFunc(n + ".editingFinished", Utils.check("on-edit-finish", nodeMap), sb, nodeMap);
        MakeFunc(n + ".returnPressed", Utils.check("on-return-press", nodeMap), sb, nodeMap);
        MakeFunc(n + ".selectionChanged", Utils.check("on-selection-change", nodeMap), sb, nodeMap);
        MakeFunc(n + ".textChanged", Utils.check("on-text-change", nodeMap), sb, nodeMap);
        MakeFunc(n + ".textEdited", Utils.check("on-text-edit", nodeMap), sb, nodeMap);
        Widget(n, sb, nodeMap);
    }

    protected void Group(String n, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(n + ".clicked", Utils.check("on-click", nodeMap), sb, nodeMap);
        MakeFunc(n + ".toggled", Utils.check("on-toggle", nodeMap), sb, nodeMap);
        Widget(n, sb, nodeMap);
    }

    protected void Widget(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(name + ".customContextMenuRequested", "on-custom-context-menu-request", sb, nodeMap);
    }

    protected void Action(String n, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(n + ".triggered", "method", sb, nodeMap);
    }

    protected void AbstactTextDocumentLayout(String n, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(n + ".documentSizeChanged", "on-size-change", sb, nodeMap);
        MakeFunc(n + ".pageCountChanged", "on-page-count-change", sb, nodeMap);
        MakeFunc(n + ".update", "on-update", sb, nodeMap);
        MakeFunc(n + ".updateBlock", "on-update-block", sb, nodeMap);
    }
}
