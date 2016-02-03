package Compiler.Parsers;

import Compiler.Utils;
import com.trolltech.examples.tutorial.Widgets;
import org.w3c.dom.NamedNodeMap;

import java.util.HashMap;
import java.util.Map;

public final class InlineStyleParser {
    private Map<String, Style> styles = new HashMap<>();
    private String prop;

    public void ColumnView(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QColumnView");
        Utils.tryBoolean(n, "resize-grips-visible", "%1s.setResizeGripsVisible(%2s);\n", sb, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QColumnView", style);
        setStyle(style, nodeMap);
        AbstractItemView(n, sb, nodeMap);
    }

    public void AbstractItemView(String name, StringBuilder sb, NamedNodeMap nodeMap){
        Utils.tryBoolean(name, "alt-row-color", "%1s.setAlternatingRowColors(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "draggable", "%1s.setDragEnabled(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "auto-scroll", "%1s.setAutoScroll(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "drop-indicator", "%1s.setDropIndicatorShown(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "tab-key-navigation", "%1s.TabKeyNavigation(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "auto-scroll-margin", "%1s.setAutoScrollMargin(%2s);\n", sb, nodeMap);
        if (!(prop = Utils.check("drag-drop-mode", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setDragDropMode(QAbstractItemView.DragDropMode.", name));
            switch (prop) {
                case "no-drag-drop": sb.append("NoDragDrop);\n"); break;
                case "drop-only": sb.append("DropOnly);\n"); break;
                case "drag-only": sb.append("DragOnly);\n"); break;
                case "drag-drop": sb.append("DragDrop);\n"); break;
                case "internal-move": sb.append("InternalMove);\n"); break;
            }
        }
        if (!(prop = Utils.check("edit-trigger", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setEditTriggers(QAbstractItemView.EditTrigger.", name));
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
            sb.append(String.format("%1s.setHorizontalScrollMode(QAbstractItemView.ScrollMode.", name));
            switch (prop) {
                case "per-item": sb.append("ScrollPerItem);\n"); break;
                case "per-pixel": sb.append("ScrollPerPixel);\n"); break;
            }
        }
        if (!(prop = Utils.check("selection-behavior", nodeMap)).isEmpty()) {
            sb.append(String.format("%1s.setSelectionBehavior(QAbstractItemView.SelectionBehavior.", name));
            switch (prop) {
                case "items": sb.append("SelectItems);\n"); break;
                case "rows": sb.append("SelectRows);\n"); break;
                case "columns": sb.append("SelectColumns);\n"); break;
            }
        }
        if (!(prop = Utils.check("text-elide-mode", nodeMap)).isEmpty()) {
            sb.append(String.format("%1s.setTextElideMode(Qt.TextElideMode.", name));
            switch (prop) {
                case "left": sb.append("ElideLeft);\n"); break;
                case "right": sb.append("ElideRight);\n"); break;
                case "middle": sb.append("ElideMiddle);\n"); break;
                case "none": sb.append("ElideNone);\n"); break;
            }
        }
        if (!(prop = Utils.check("vertical-scroll-mode", nodeMap)).isEmpty()) {
            sb.append(String.format("%1s.setVerticalScrollMode(QAbstractItemView.ScrollMode.", name));
            switch (prop) {
                case "per-item": sb.append("ScrollPerItem);\n"); break;
                case "per-pixel": sb.append("ScrollPerPixel);\n"); break;
            }
        }
        AbstractScrollArea(name, sb, nodeMap);
    }

    public void AbstractScrollArea(String name, StringBuilder sb, NamedNodeMap nodeMap){
        if(!(prop = Utils.check("horizontal-scroll-bar-policy", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.", name));
            switch (prop) {
                case "always-on": sb.append("ScrollBarAlwaysOn);\n"); break;
                case "as-needed": sb.append("ScrollBarAsNeeded);\n"); break;
                case "always-off": sb.append("ScrollBarAlwaysOff);\n"); break;
            }
        }
        if(!(prop = Utils.check("vertical-scroll-bar-policy", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.", name));
            switch (prop) {
                case "always-on": sb.append("ScrollBarAlwaysOn);\n"); break;
                case "as-needed": sb.append("ScrollBarAsNeeded);\n"); break;
                case "always-off": sb.append("ScrollBarAlwaysOff);\n"); break;
            }
        }
        Frame(name, sb, nodeMap);
    }

    public void LCDNumber(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QLCDNumber");
        Utils.tryCapitalize(n, "segment-style", "%1s.setSegmentStyle(QLCDNumber.SegmentStyle.%2s);\n", sb, nodeMap);
        Utils.tryCapitalize(n, "mode", "%1s.setMode(QLCDNumber.Mode.%2s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "small-decimal-point", "%1s.setSmallDecimalPoint(%2s);\n", sb, nodeMap);
        Utils.tryValue(n, "digit-count", "%1s.setDigitCount(%2s);\n", sb, nodeMap);
        Utils.tryValue(n, "value", "%1s.display(%2s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QLCDNumber", style);
        Frame(n, sb, nodeMap);
    }

    public void Frame(String name, StringBuilder sb, NamedNodeMap nodeMap){
        if(!(prop = Utils.check("shadow", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setFrameShadow(QFrame.Shadow.", name));
            switch (prop) {
                case "sunken": sb.append("Sunken);\n"); break;
                case "plain": sb.append("Plain);\n"); break;
                case "raised": sb.append("Raised);\n"); break;
            }
        }
        if(!(prop = Utils.check("shape", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setFrameShape(QFrame.Shape.", name));
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
            sb.append(String.format("%1s.setFrameRect(new QRect(", name));

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

    public void Slider(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QSlider");
        if(!(prop = Utils.check("tick-position", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setTickPosition(QSlider.TickPosition.", n));
            switch(prop){
                case "both": sb.append("TicksBothSides"); break;
                case "above": case "left": sb.append("TicksAbove"); break;
                case "below": case "right": sb.append("TicksBelow"); break;
                default: sb.append("NoTicks"); break;
            }
            sb.append(");\n");
        }
        Utils.tryValue(n, "interval", "%1s.setTickInterval(%2s);\n", sb, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QSlider", style);
        setStyle(style, nodeMap);
        AbstractSlider(n, sb, nodeMap);
    }

    public void AbstractSlider(String name, StringBuilder sb, NamedNodeMap nodeMap){
        String[] range = Utils.check("range", nodeMap).split(" ");
        if (range.length > 1) sb.append(String.format("%1s.setRange(%2s, %3s);\n", name, range[0], range[1]));
        if(!(prop = Utils.check("orientation", nodeMap)).isEmpty()){
            String value = "";
            switch(prop){
                case "vertical": value = "Vertical"; break;
                case "horizontal": value = "Horizontal"; break;
            }
            Utils.tryEmptyAppend(name, value, "%s.setOrientation(Qt.Orientation.%s);\n", sb);
        }
        Utils.tryValue(name, "min-value", "%1s.setMinimum(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "max-value", "%1s.setMaximum(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "value", "%1s.setValue(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "page-steps", "%1s.setPageStep(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "single-steps", "%1s.setSingleStep(%2s);\n", sb, nodeMap);
        Utils.tryValue(name, "slide-position", "%1s.setSliderPosition(%2s);\n", sb, nodeMap);
        if(!(prop = Utils.check("invert-numbers", nodeMap)).isEmpty()){
            String value = "";
            switch(prop){
                case "vertical": value = "Vertical"; break;
                case "horizontal": value = "Horizontal"; break;
            }
            Utils.tryEmptyAppend(name, value, "%s.setInvertedControls(Qt.Orientation.%s);\n", sb);
        }
        Utils.tryBoolean(name, "invert-numbers", "%1s.setInvertedControls(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "invert-controls", "%1s.setInvertedAppearance(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "tracking", "%1s.setTracking(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "slide-down-tracking", "%1s.setSliderPosition(%2s);\n", sb, nodeMap);
        Widget(name, sb, nodeMap);
    }

    public void PushButton(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QPushButton");
        Utils.tryCheck(n, "shortcut", "%s.setShortcut(new QKeySequence(tr(%s));\n", sb, nodeMap);
        Utils.tryBoolean(n, "default", "%1s.setDefault(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "flat", "%1s.setFlat(%2s);\n", sb, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QPushButton", style);
        setStyle(style, nodeMap);
        AbstractButton(n, sb, nodeMap);
    }

    public void RadioButton(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QRadioButton");
        stylesSheet.put((!n.isEmpty()) ? n : "QRadioButton", style);
        setStyle(style, nodeMap);
        AbstractButton(n, sb, nodeMap);
    }

    public void CheckBox(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QCheckBox");
        Utils.tryBoolean(n, "checkable", "%1s.setTristate(%2s);\n", sb, nodeMap);
        if(!(prop = Utils.check("check-state", nodeMap)).isEmpty()){
            sb.append(String.format("%1s.setCheckState(Qt.CheckState.", n));
            switch(prop){
                case "unchecked": sb.append("Unchecked);\n"); break;
                case "partially-checked": sb.append("PartiallyChecked);\n"); break;
                case "checked": sb.append("Checked);\n"); break;
            }
        }
        Utils.tryBoolean(n, "default", "%1s.setDefaultUp(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "native-menubar", "%1s.setNativeMenuBar(%2s);\n", sb, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QCheckBox", style);
        setStyle(style, nodeMap);
        AbstractButton(n, sb, nodeMap);
    }

    public void AbstractButton(String name, StringBuilder sb, NamedNodeMap nodeMap){
        Utils.tryBoolean(name, "checkable", "%s.setCheckable(%s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "checked", "%s.setChecked(%s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "pressed", "%s.setDown(%s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "repeatable", "%s.setAutoRepeat(%s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "exclusive", "%s.setAutoExclusive(%s);\n", sb, nodeMap);
        Utils.tryCheck(name, "text", "%s.setText(%s);\n", sb, nodeMap);
        Utils.tryValue(name, "repeatable-delay", "%s.setAutoRepeatDelay(%s);\n", sb, nodeMap);
        Utils.tryValue(name, "repeatable-interval", "%s.setAutoRepeatInterval(%s);\n", sb, nodeMap);
        Widget(name, sb, nodeMap);
    }

    private Map<String, String> cursors = new HashMap<String, String>(){{
        put("arrow", "ArrowCursor");
        put("up-arrow", "UpArrowCursor");
        put("cross", "CrossCursor");
        put("wait", "WaitCursor");
        put("vertical-beam", "IBeamCursor");
        put("size-vertical", "SizeVerCursor");
        put("size-horizontal", "SizeHorCursor");
        put("size-major-diagonal", "SizeFDragCursor");
        put("size-minor-diagonal", "SizeBDragCursor");
        put("size-all", "SizeAllCursor");
        put("blank", "BlankCursor");
        put("vertical-split", "SplitVCursor");
        put("horizontal-split", "SplitHCursor");
        put("hand-pointer", "PointingHandCursor");
        put("forbidden", "ForbiddenCursor");
        put("open-hand", "OpenHandCursor");
        put("closed-hand", "OpenHandCursor");
        put("question-mark", "WhatsThisCursor");
        put("busy", "BusyCursor");
        put("drag-move", "DragMoveCursor");
        put("drag-copy", "DragMoveCursor");
        put("drag-link", "DragLinkCursor");
        put("bitmap", "BitmapCursor");
    }};

    public void MainWindow(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QMainWindow");
        sb.append("QWidget centerWidget = new QWidget();\n");
        sb.append(String.format("%s.setCentralWidget(centerWidget);\n", n));
        sb.append(String.format("%s.setWindowTitle(tr(\"%s\"));\n", n, Utils.tryEmpty("title", "JAML Applicaiton", nodeMap)));
        Utils.tryBoolean(n, "dock-animation", "%s.setAnimated(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "dock-nesting", "%s.setDockNestingEnabled(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "document-mode", "%s.setDocumentMode(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "unified-mac-title-toolbar", "%1s.setUnifiedTitleAndToolBarOnMac(%2s);\n", sb, nodeMap);
        if(!(prop = Utils.check("dock-option", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setDockOptions(DockOption.", n));
            switch(prop){
                case "animated-docks": sb.append("AnimatedDocks);\n"); break;
                case "allow-nested-docks": sb.append("AllowNestedDocks);\n"); break;
                case "allow-tabbed-docks": sb.append("AllowTabbedDocks);\n"); break;
                case "force-tabbed-docks": sb.append("ForceTabbedDocks);\n"); break;
                case "vertical-tabs": sb.append("VerticalTabs);\n"); break;
            }
        }
        if(!(prop = Utils.check("tab-shape", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setTabShape(QTabWidget.TabShape.", n));
            switch(prop){
                case "rounded": sb.append("Rounded);\n"); break;
                case "triangular": sb.append("Triangular);\n"); break;
            }
        }
        if(!(prop = Utils.check("tool-button-style", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setToolButtonStyle(Qt.ToolButtonStyle.", n));
            switch(prop){
                case "icon-only": sb.append("ToolButtonIconOnly);\n"); break;
                case "text-only": sb.append("ToolButtonTextOnly);\n"); break;
                case "text-beside-icon": sb.append("ToolButtonTextBesideIcon);\n"); break;
                case "text-under-icon": sb.append("ToolButtonTextUnderIcon);\n"); break;
                case "follow-style": sb.append("ToolButtonFollowStyle);\n"); break;
            }
        }
        setStyle(style, nodeMap);
        stylesSheet.put(n, style);
        Widget(n, sb, nodeMap);
    }

    public void MenuBar(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QMenuBar");
        setStyle(style, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QMenuBar", style);
        Widget(n, sb, nodeMap);
    }

    public void Menu(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QMenu");
        Utils.tryCheck(n, "icon", "%s.setIcon(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "tear-off", "%s.setTearOffEnabled(%s);\n", sb, nodeMap);
        Utils.tryCheck(n, "title", "%s.setTitle(\"%s\");\n", "menu", sb, nodeMap);
        setStyle(style, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QMenu", style);
        Widget(n, sb, nodeMap);
    }

    public void Widget(String name, StringBuilder sb, NamedNodeMap nodeMap){
        if(!(prop = Utils.check("margin", nodeMap)).isEmpty()) {
            String[] margins = prop.replaceAll("px", "").split(" ");
            sb.append(String.format("%1s.setContentsMargins(", name));
            String v1 = "", v2 = "", v3 = "", v4 = "";
            switch (margins.length){
                case 4: v1 = margins[0]; v2 = margins[1]; v3 = margins[2]; v4 = margins[3]; break;
                case 3: v1 = margins[0]; v2 = margins[1]; v3 = margins[2]; v4 = margins[1]; break;
                case 2: v1 = margins[0]; v2 = margins[1]; v3 = margins[0]; v4 = margins[1]; break;
                case 1: v1 = margins[0]; v2 = margins[0]; v3 = margins[0]; v4 = margins[0]; break;
            }
            String value = String.format("%1s, %2s, %3s, %4s", v1, v2, v3, v4);
            Utils.tryEmptyAppend(name, value, "%1s.setContentsMargins(%s));\n", sb);
        }
        if(!(prop = Utils.check("cursor", nodeMap)).isEmpty()){
            Utils.tryEmptyAppend(name, cursors.get(prop), "%s.setCursor(new QCursor(Qt.CursorShape.%s));\n", sb);
        }
        Utils.tryBoolean(name, "mouse-tracking", "%1s.setMouseTracking(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "visibility", "%1s.setHidden(%2s);\n", sb, nodeMap);
        Utils.tryBoolean(name, "update", "%1s.setUpdatesEnabled(%2s);\n", sb, nodeMap);
        Utils.tryCheck(name, "tool-tip", "%1s.setToolTip(%2s);\n", sb, nodeMap);
    }

    public void Action(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QAction");
        Utils.tryBoolean(n, "repeatable", "%s.setAutoRepeat(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "checkable", "%s.setCheckable(%s);\n", sb, nodeMap);
        Utils.tryCheck(n, "icon", "%s.setIcon(%s);\n", sb, nodeMap);
        Utils.tryCheck(n, "icon-Text", "%s.setIconText(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "icon-visible", "%s.setIconVisibleMenu(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "is-separator", "%s.setSeparator(%s);\n", sb, nodeMap);
        Utils.tryCheck(n, "shortcut", "%s.setShortcut(new QKeySequence(tr(\"%s\")));\n", sb, nodeMap);
        if(!(prop = Utils.check("menu-role", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setMenuRole(QAction.MenuRole.", n));
            switch(prop){
                case "none": sb.append("NoRole);\n"); break;
                case "text-heuristic": sb.append("TextHeuristicRole);\n"); break;
                case "application-specific": sb.append("ApplicationSpecificRole);\n"); break;
                case "about-qt": sb.append("AboutQtRole);\n"); break;
                case "about": sb.append("AboutRole);\n"); break;
                case "preferences": sb.append("PreferencesRole);\n"); break;
                case "quit": sb.append("QuitRole);\n"); break;
            }
        }
        if(!(prop = Utils.check("priority", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setPriority(QAction.Priority.", n));
            switch(prop){
                case "high": sb.append("HighPriority);\n"); break;
                case "normal": sb.append("NormalPriority);\n"); break;
                case "low": sb.append("LowPriority);\n"); break;
            }
        }
        if(!(prop = Utils.check("soft-key-role", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setSoftKeyRole(QAction.SoftKeyRole.", n));
            switch(prop){
                case "none": sb.append("NoSoftKey);\n"); break;
                case "positive": sb.append("PositiveSoftKey);\n"); break;
                case "negative": sb.append("NegativeSoftKey);\n"); break;
                case "select": sb.append("SelectSoftKey);\n"); break;
            }
        }
        if(!(prop = Utils.check("shortcut-context", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setShortcutContext(Qt.ShortcutContext.", n));
            switch(prop){
                case "widget": sb.append("WidgetShortcut);\n"); break;
                case "Widget-children": sb.append("WidgetWithChildrenShortcut);\n"); break;
                case "Window": sb.append("WindowShortcut);\n"); break;
                case "Application": sb.append("ApplicationShortcut);\n"); break;
            }
        }
        Utils.tryCheck(n, "status-tip", "%s.setStatusTip(%s);\n", sb, nodeMap);
        Utils.tryCheck(n, "tool-tip", "%s.setToolTip(%s);\n", sb, nodeMap);
        Utils.tryCheck(n, "whats-this", "%s.setWhatsThis(%s);\n", sb, nodeMap);
        stylesSheet.put((!n.isEmpty()) ? n : "QAction", style);
        setStyle(style, nodeMap);
    }

    public void setStyle(Style style, NamedNodeMap nodeMap) {
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
    }
}
