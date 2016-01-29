package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;

import java.util.HashMap;
import java.util.Map;

public final class InlineStyleParser {
    private Map<String, Style> styles = new HashMap<>();

    public void AbstractItemView(String name, StringBuilder sb, NamedNodeMap nodeMap){
        String prop;
        Utils.tryBoolean(name, "alt-row-color", "\t\t%1s.setAlternatingRowColors(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "draggable", "\t\t%1s.setDragEnabled(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "auto-scroll", "\t\t%1s.setAutoScroll(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "drop-indicator", "\t\t%1s.setDropIndicatorShown(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "tab-key-navigation", "\t\t%1s.TabKeyNavigation(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "auto-scroll-margin", "\t\t%1s.setAutoScrollMargin(%2s);\n", sb, nodeMap);
        if (!(prop = Utils.check("drag-drop-mode", nodeMap)).isEmpty()){
            sb.append(String.format("\t\t%1s.setDragDropMode(QAbstractItemView.DragDropMode.", name));
            switch (prop) {
                case "no-drag-drop": sb.append("NoDragDrop);\n"); break;
                case "drop-only": sb.append("DropOnly);\n"); break;
                case "drag-only": sb.append("DragOnly);\n"); break;
                case "drag-drop": sb.append("DragDrop);\n"); break;
                case "internal-move": sb.append("InternalMove);\n"); break;
            }
        }
        if (!(prop = Utils.check("edit-trigger", nodeMap)).isEmpty()){
            sb.append(String.format("\t\t%1s.setEditTriggers(QAbstractItemView.EditTrigger.", name));
            switch (prop) {
                case "no-edit-triggers": sb.append("NoEditTriggers);\n"); break;
                case "current-change": sb.append("CurrentChanged);\n"); break;
                case "double-click": sb.append("DoubleClicked);\n"); break;
                case "selected-click": sb.append("SelectedClicked);\n"); break;
                case "edit-key-press": sb.append("EditKeyPressed);\n"); break;
                case "any-key-press": sb.append("AnyKeyPressed);\n"); break;
                case "all-edit-triggers": sb.append("AllEditTriggers);\n"); break;
            }
        }
        if (!(prop = Utils.check("horizontal-scroll-mode", nodeMap)).isEmpty()) {
            sb.append(String.format("\t\t%1s.setHorizontalScrollMode(QAbstractItemView.ScrollMode.", name));
            switch (prop) {
                case "per-item": sb.append("ScrollPerItem);\n"); break;
                case "per-pixel": sb.append("ScrollPerPixel);\n"); break;
            }
        }
        if (!(prop = Utils.check("selection-behavior", nodeMap)).isEmpty()) {
            sb.append(String.format("\t\t%1s.setSelectionBehavior(QAbstractItemView.SelectionBehavior.", name));
            switch (prop) {
                case "items": sb.append("SelectItems);\n"); break;
                case "rows": sb.append("SelectRows);\n"); break;
                case "columns": sb.append("SelectColumns);\n"); break;
            }
        }
        if (!(prop = Utils.check("text-elide-mode", nodeMap)).isEmpty()) {
            sb.append(String.format("\t\t%1s.setTextElideMode(Qt.TextElideMode.", name));
            switch (prop) {
                case "left": sb.append("ElideLeft);\n"); break;
                case "right": sb.append("ElideRight);\n"); break;
                case "middle": sb.append("ElideMiddle);\n"); break;
                case "none": sb.append("ElideNone);\n"); break;
            }
        }
        if (!(prop = Utils.check("vertical-scroll-mode", nodeMap)).isEmpty()) {
            sb.append(String.format("\t\t%1s.setVerticalScrollMode(QAbstractItemView.ScrollMode.", name));
            switch (prop) {
                case "per-item": sb.append("ScrollPerItem);\n"); break;
                case "per-pixel": sb.append("ScrollPerPixel);\n"); break;
            }
        }
        AbstractScrollArea(name, sb, nodeMap);
    }

    public void AbstractScrollArea(String name, StringBuilder sb, NamedNodeMap nodeMap){
        String prop;
        if(!(prop = Utils.check("horizontal-scroll-bar-policy", nodeMap)).isEmpty()){
            sb.append(String.format("\t\t%1s.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.", name));
            switch (prop) {
                case "always-on": sb.append("ScrollBarAlwaysOn);\n"); break;
                case "as-needed": sb.append("ScrollBarAsNeeded);\n"); break;
                case "always-off": sb.append("ScrollBarAlwaysOff);\n"); break;
            }
        }
        if(!(prop = Utils.check("vertical-scroll-bar-policy", nodeMap)).isEmpty()){
            sb.append(String.format("\t\t%1s.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.", name));
            switch (prop) {
                case "always-on": sb.append("ScrollBarAlwaysOn);\n"); break;
                case "as-needed": sb.append("ScrollBarAsNeeded);\n"); break;
                case "always-off": sb.append("ScrollBarAlwaysOff);\n"); break;
            }
        }
        Frame(name, sb, nodeMap);
    }

    public void Frame(String name, StringBuilder sb, NamedNodeMap nodeMap){
        String prop;
        if(!(prop = Utils.check("shadow", nodeMap)).isEmpty()){
            sb.append(String.format("\t\t%1s.setFrameShadow(QFrame.Shadow.", name));
            switch (prop) {
                case "sunken": sb.append("Sunken);\n"); break;
                case "plain": sb.append("Plain);\n"); break;
                case "raised": sb.append("Raised);\n"); break;
            }
        }
        if(!(prop = Utils.check("shape", nodeMap)).isEmpty()){
            sb.append(String.format("\t\t%1s.setFrameShape(QFrame.Shape.", name));
            switch (prop) {
                case "no-frame": sb.append("NoFrame);\n"); break;
                case "box": sb.append("Box);\n"); break;
                case "panel": sb.append("Panel);\n"); break;
                case "styled-panel": sb.append("StyledPanel);\n"); break;
                case "horizontal-line": sb.append("HLine);\n"); break;
                case "vertical-line": sb.append("VLine);\n"); break;
                case "window-panel": sb.append("WinPanel);\n"); break;
            }
        }
        if(!(prop = Utils.check("size", nodeMap)).isEmpty()){
            String[] sizes = prop.split(" ");
            sb.append(String.format("\t\t%1s.setFrameRect(new QRect(", name));

            String v1 = "", v2 = "", v3 = "", v4 = "";
            try {
                v1 = sizes[0]; v2 = sizes[1]; v3 = sizes[2]; v4 = sizes[3];
            }catch (NullPointerException ignored){
            }
            switch (sizes.length){
                case 4: sb.append(String.format("0, 0, %1s, %2s));\n", v1, v1)); break;
                case 3: sb.append(String.format("0, 0, %1s, %2s));\n", v1, v2)); break;
                case 2: sb.append(String.format("%1s, %2s, %3s, %4s));\n", v1, v1, v2, v3)); break;
                case 1: sb.append(String.format("%1s, %2s, %3s, %4s));\n", v1, v2, v3, v4)); break;
            }
        }
        Widget(name, sb, nodeMap);
    }

    public void AbstractSlider(String name, StringBuilder sb, NamedNodeMap nodeMap){
        String[] range = Utils.check("range", nodeMap).split(" ");
        if (range.length > 1) sb.append(String.format("\t\t%1s.setRange(%2s, %3s);\n", name, range[0], range[1]));
        Utils.tryBoolean(name, "orientation", "vertical", "horizontal", "\t\t%1s.setOrientation(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "min-value", "\t\t%1s.setMinimum(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "max-value", "\t\t%1s.setMaximum(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "value", "\t\t%1s.setValue(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "page-steps", "\t\t%1s.setPageStep(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "single-steps", "\t\t%1s.setSingleStep(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "slide-position", "\t\t%1s.setSliderPosition(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "invert-numbers", "\t\t%1s.setInvertedControls(%2s);\n", "horizontal", "vertical", sb, nodeMap);
        Utils.tryBoolean(name, "invert-numbers", "\t\t%1s.setInvertedControls(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "invert-controls", "\t\t%1s.setInvertedAppearance(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "tracking", "\t\t%1s.setTracking(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "slide-down-tracking", "\t\t%1s.setSliderPosition(%2s);\n", sb, nodeMap);
        Widget(name, sb, nodeMap);
    }

    public void AbstractButton(String name, StringBuilder sb, NamedNodeMap nodeMap){
        String prop;
        if (Utils.tryBoolean(prop = Utils.check("checkable", nodeMap))) sb.append(String.format("\t\t%1s.setCheckable(%2s);\n", name, prop));
        if (Utils.tryBoolean(prop = Utils.check("checked", nodeMap))) sb.append(String.format("\t\t%1s.setChecked(%2s);\n", name, prop));
        if (Utils.tryBoolean(prop = Utils.check("pressed", nodeMap))) sb.append(String.format("\t\t%1s.setDown(%2s);\n", name, prop));
        if (Utils.tryBoolean(prop = Utils.check("repeatable", nodeMap))) sb.append(String.format("\t\t%1s.setAutoRepeat(%2s);\n", name, prop));
        if (Utils.tryBoolean(prop = Utils.check("exclusive", nodeMap))) sb.append(String.format("\t\t%1s.setAutoExclusive(%2s);\n", name, prop));
        if (!(prop = Utils.check("text", nodeMap)).isEmpty()) sb.append(String.format("\t\t%1s.setText(%2s);\n", name, prop));
        if (!(prop = Utils.check("short-cut", nodeMap)).isEmpty()) sb.append(String.format("\t\t%1s.setShortcut(%2s);\n", name, prop));
        if (Utils.tryValue((prop = Utils.check("repeatable-delay", nodeMap)))) sb.append(String.format("\t\t%1s.setAutoRepeatDelay(%2s);\n", name, prop));
        if (Utils.tryValue((prop = Utils.check("repeatable-interval", nodeMap)))) sb.append(String.format("\t\t%1s.setAutoRepeatInterval(%2s);\n", name, prop));
        Widget(name, sb, nodeMap);
    }

    public void Widget(String name, StringBuilder sb, NamedNodeMap nodeMap){
        String prop;
        if(!(prop = Utils.check("margin", nodeMap)).isEmpty()) {
            String[] margins = prop.replace("px", "").split(" ");
            sb.append(String.format("\t\t%1s.setContentsMargins(", name));
            String v1 = "", v2 = "", v3 = "", v4 = "";
            try {
                v1 = margins[0]; v2 = margins[1]; v3 = margins[2]; v4 = margins[3];
            }catch (NullPointerException ignored){
            }
            switch (margins.length){
                case 4: sb.append(String.format("%1s, %2s, %3s, %4s));\n", v1, v2, v3, v4)); break;
                case 3: sb.append(String.format("%1s, %2s, %3s, %4s));\n", v1, v2, v3, v2)); break;
                case 2: sb.append(String.format("%1s, %2s, %3s, %4s));\n", v1, v2, v1, v2)); break;
                case 1: sb.append(String.format("%1s, %2s, %3s, %4s));\n", v1, v1, v1, v1)); break;
            }
        }
        if(!(prop = Utils.check("cursor", nodeMap)).isEmpty()){
            sb.append(String.format("\t\t%1s.setCursor(new QCursor(Qt.CursorShape.", name));
            switch (prop){
                case "arrow": sb.append("ArrowCursor));\n"); break;
                case "up-arrow": sb.append("UpArrowCursor));\n"); break;
                case "cross": sb.append("CrossCursor));\n"); break;
                case "wait": sb.append("WaitCursor));\n"); break;
                case "vertical-beam": sb.append("IBeamCursor));\n"); break;
                case "size-vertical": sb.append("SizeVerCursor));\n"); break;
                case "size-horizontal": sb.append("SizeHorCursor));\n"); break;
                case "size-major-diagonal": sb.append("SizeFDiagCursor));\n"); break;
                case "size-minor-diagonal": sb.append("SizeBDiagCursor));\n"); break;
                case "size-all": sb.append("SizeAllCursor));\n"); break;
                case "blank": sb.append("BlankCursor));\n"); break;
                case "vertical-split": sb.append("SplitVCursor));\n"); break;
                case "horizontal-split": sb.append("SplitHCursor));\n"); break;
                case "hand-pointer": sb.append("PointingHandCursor));\n"); break;
                case "forbidden": sb.append("ForbiddenCursor));\n"); break;
                case "open-hand": sb.append("OpenHandCursor));\n"); break;
                case "closed-hand": sb.append("OpenHandCursor));\n"); break;
                case "question-mark": sb.append("WhatsThisCursor));\n"); break;
                case "busy": sb.append("BusyCursor));\n"); break;
                case "drag-move": sb.append("DragMoveCursor));\n"); break;
                case "drag-copy": sb.append("DragMoveCursor));\n"); break;
                case "drag-link": sb.append("DragLinkCursor));\n"); break;
                case "bitmap": sb.append("BitmapCursor));\n"); break;
            }
        }
        Utils.tryBoolean(name, "mouse-tracking", "\t\t%1s.setMouseTracking(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "visibility", "\t\t%1s.setHidden(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "update", "\t\t%1s.setUpdatesEnabled(%2s);\n", sb, nodeMap);
        Utils.tryCheck(name, "tool-tip", "\t\t%1s.setToolTip(%2s);\n", sb, nodeMap);
    }

    /*public void setStyle(Style style, NamedNodeMap nodeMap) {
        String[] attributes = {"alt-background-color", "background", "background-color", "background-image", "image",
                "background-repeat", "background-position", "background-attachment", "background-clip", "opacity",
                "background-origin", "border", "border-top", "border-right", "border-bottom", "border-left", "color",
                "border-image", "border-color", "border-top-color", "font-weight", "border-right-color", "padding",
                "border-bottom-color", "border-left-color", "border-style", "border-top-style", "icon-size", "top",
                "border-right-style", "border-bottom-style", "border-left-style", "border-width", "border-top-width",
                "spacing", "border-right-width", "border-bottom-width", "border-left-width", "border-radius", "right",
                "border-top-left-radius", "border-top-right-radius", "border-top-right-radius", "text-decoration",
                "selection-background-color", "selection-color", "border-bottom-right-radius", "text-align",
                "border-bottom-left-radius", "bottom", "left", "height", "width", "gridline-color", "button-layout",
                "subcontrol-position", "font", "font-family", "font-size", "font-style", "image-position", "position",
                "padding-top", "margin-top", "margin-bottom", "margin-left", "max-height", "max-width", "min-height",
                "min-width", "padding-right", "padding-bottom", "margin-right", "padding-left", "subcontrol-origin"};
        for (String attribute : attributes) Utils.addAttribute(style, nodeMap, attribute);
        Utils.addAttribute(style, nodeMap, "alt-empty-row-color", "paint-alternating-row-colors-for-empty-area");
        Utils.addAttribute(style, nodeMap, "textbox-interaction", "messagebox-text-interaction-flags");
        Utils.addAttribute(style, nodeMap, "icon", "file-icon");
        Utils.addAttribute(style, nodeMap, "dialogbuttons-have-icons", "dialogbuttonbox-buttons-have-icons");
        Utils.addAttribute(style, nodeMap, "selection-decoration", "show-decoration-selected");
    }*/
}
