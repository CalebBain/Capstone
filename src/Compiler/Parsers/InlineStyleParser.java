package Compiler.Parsers;

import Compiler.Utils;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QGridLayout;
import org.w3c.dom.NamedNodeMap;

import java.util.HashMap;
import java.util.Map;

public final class InlineStyleParser {
    private String prop;
    private String value = "";

    public void List(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QListWidget");
        Utils.tryBoolean(n, "sorting", "%s.setSortingEnabled(%s);\n", sb, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QListView", style);
        setStyle(style, nodeMap);
        ListView(n, sb, nodeMap);
    }

    public void ListView(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(prop = Utils.check("movement", nodeMap)).isEmpty()){
            switch (prop) {
                case "static": value = "Static"; break;
                case "free": value = "Free"; break;
                case "snap": value = "Snap"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setMovement(QListView.Movement.%s);\n",sb);
        }
        if (!(prop = Utils.check("layout-mode", nodeMap)).isEmpty()){
            switch (prop) {
                case "single-pass": value = "SinglePass"; break;
                case "batched": value = "Batched"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setLayoutMode(QListView.LayoutMode.%s);\n",sb);
        }
        if (!(prop = Utils.check("orientation", nodeMap)).isEmpty()){
            switch (prop) {
                case "horizontal": value = "LeftToRight"; break;
                case "vertical": value = "TopToBottom"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setFlow(QListView.Flow.%s);\n",sb);
        }
        if (!(prop = Utils.check("view-mode", nodeMap)).isEmpty()){
            switch (prop) {
                case "list": value = "ListMode"; break;
                case "icon": value = "IconMode"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setViewMode(QListView.ViewMode.%s);\n",sb);
        }
        if (!(prop = Utils.check("resize-mode", nodeMap)).isEmpty()){
            switch (prop) {
                case "adjust": value = "Adjust"; break;
                case "fixed": value = "Fixed"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setResizeMode(QListView.ResizeMode.%s);\n",sb);
        }
        if (!(prop = Utils.check("grid-size", nodeMap)).isEmpty()){
            String[] parts = prop.split(" ");
            String value = parts[0] + ", " + parts[1];
            sb.append(String.format("%s.setGridSize(new QSize(%s);\n", n, value));
        }
        Utils.tryBoolean(n, "selection-rectangle-visible", "%s.setSelectionRectVisible(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "uniform-sizes", "%s.setUniformItemSizes(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "word-wrap", "%s.setWordWrap(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "wrapping", "%s.setWrapping(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "batch-size", "%s.setBatchSize(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "model-column", "%s.setModelColumn(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "spacing", "%s.setSpacing(%s);\n", sb, nodeMap);
        AbstractItemView(n, sb, nodeMap);
    }

    public void AbstractItemView(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(prop = Utils.check("drag-drop-mode", nodeMap)).isEmpty()){
            switch (prop) {
                case "no-drag-drop": value = "NoDragDrop"; break;
                case "drop-only": value = "DropOnly"; break;
                case "drag-only": value = "DragOnly"; break;
                case "drag-drop": value = "DragDrop"; break;
                case "internal-move": value = "InternalMove"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setDragDropMode(QAbstractItemView.DragDropMode.%s);\n",sb);
        }
        if (!(prop = Utils.check("edit-trigger", nodeMap)).isEmpty()){
            switch (prop) {
                case "no-edit-triggers": value = "NoEditTriggers"; break;
                case "current-change": value = "CurrentChanged"; break;
                case "double-click": value = "DoubleClicked"; break;
                case "selected-click": value = "SelectedClicked"; break;
                case "edit-key-press": value = "EditKeyPressed"; break;
                case "any-key-press": value = "AnyKeyPressed"; break;
                case "all-edit-triggers": value = "AllEditTriggers"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setEditTriggers(QAbstractItemView.EditTrigger.%s);\n",sb);
        }
        if (!(prop = Utils.check("horizontal-scroll-mode", nodeMap)).isEmpty()) {
            switch (prop) {
                case "per-item": value = "ScrollPerItem"; break;
                case "per-pixel": value = "ScrollPerPixel"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setHorizontalScrollMode(QAbstractItemView.ScrollMode.%s);\n",sb);
        }
        if (!(prop = Utils.check("selection-behavior", nodeMap)).isEmpty()) {
            switch (prop) {
                case "items": value = "SelectItems"; break;
                case "rows": value = "SelectRows"; break;
                case "columns": value = "SelectColumns"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setSelectionBehavior(QAbstractItemView.SelectionBehavior.%s);\n",sb);
        }
        if (!(prop = Utils.check("text-elide-mode", nodeMap)).isEmpty()) {
            switch (prop) {
                case "left": value = "ElideLeft"; break;
                case "right": value = "ElideRight"; break;
                case "middle": value = "ElideMiddle"; break;
                case "none": value = "ElideNone"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setTextElideMode(Qt.TextElideMode.%s);\n",sb);
        }
        if (!(prop = Utils.check("vertical-scroll-mode", nodeMap)).isEmpty()) {
            switch (prop) {
                case "per-item": value = "ScrollPerItem"; break;
                case "per-pixel": value = "ScrollPerPixel"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setVerticalScrollMode(QAbstractItemView.ScrollMode.%s);\n",sb);
        }
        Utils.tryBoolean(n, "alt-row-color", "%s.setAlternatingRowColors(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "draggable", "%s.setDragEnabled(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "auto-scroll", "%s.setAutoScroll(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "drop-indicator", "%s.setDropIndicatorShown(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "tab-key-navigation", "%s.TabKeyNavigation(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "auto-scroll-margin", "%s.setAutoScrollMargin(%s);\n", sb, nodeMap);
        AbstractScrollArea(n, sb, nodeMap);
    }

    public void AbstractScrollArea(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if(!(prop = Utils.check("horizontal-scroll-bar-policy", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.", n));
            switch (prop) {
                case "always-on": value = "ScrollBarAlwaysOn"; break;
                case "as-needed": value = "ScrollBarAsNeeded"; break;
                case "always-off": value = "ScrollBarAlwaysOff"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.%s);\n",sb);
        }
        if(!(prop = Utils.check("vertical-scroll-bar-policy", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.", n));
            switch (prop) {
                case "always-on": value = "ScrollBarAlwaysOn"; break;
                case "as-needed": value = "ScrollBarAsNeeded"; break;
                case "always-off": value = "ScrollBarAlwaysOff"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.%s);\n",sb);
        }
        Frame(n, sb, nodeMap);
    }

    public void LCDNumber(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QLCDNumber");
        Utils.tryCapitalize(n, "segment-style", "%s.setSegmentStyle(QLCDNumber.SegmentStyle.%s);\n", sb, nodeMap);
        Utils.tryCapitalize(n, "mode", "%s.setMode(QLCDNumber.Mode.%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "small-decimal-point", "%s.setSmallDecimalPoint(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "digit-count", "%s.setDigitCount(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "value", "%s.display(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QLCDNumber", style);
        Frame(n, sb, nodeMap);
    }

    public void Label(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QLabel");
        if (!(prop = Utils.check("align", nodeMap)).isEmpty()){
            switch (prop) {
                case "left": value = "AlignLeft"; break;
                case "right": value = "AlignRight"; break;
                case "horizontal-center": value = "AlignHCenter"; break;
                case "justify": value = "AlignJustify"; break;
                case "top": value = "AlignTop"; break;
                case "bottom": value = "AlignBottom"; break;
                case "vertical-center": value = "AlignVCenter"; break;
                case "center": value = "AlignCenter"; break;
                case "absolute": value = "AlignAbsolute"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setAlignment(Qt.AlignmentFlag.%s);\n",sb);
        }
        if (!(prop = Utils.check("text-format", nodeMap)).isEmpty()){
            switch (prop) {
                case "auto": value = "AutoText"; break;
                case "plain": value = "PlainText"; break;
                case "rich": value = "RichText"; break;
                case "log": value = "LogText"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setTextFormat(Qt.TextFormat.%s);\n",sb);
        }
        if (!(prop = Utils.check("text-interaction", nodeMap)).isEmpty()){
            String[] flags = prop.split(" ");
            for(int i = 0; i < flags.length;){
                switch (flags[i]) {
                    case "none": value += "Qt.TextInteractionFlag.NoTextInteraction"; break;
                    case "mouse-select": value += "Qt.TextInteractionFlag.TextSelectableByMouse"; break;
                    case "key-select": value += "Qt.TextInteractionFlag.TextSelectableByKeyboard"; break;
                    case "mouse-access": value += "Qt.TextInteractionFlag.LinksAccessibleByMouse"; break;
                    case "key-access": value += "Qt.TextInteractionFlag.LinksAccessibleByKeyboard"; break;
                    case "editable": value += "Qt.TextInteractionFlag.TextEditable"; break;
                    case "editor": value += "Qt.TextInteractionFlag.TextEditorInteraction"; break;
                    case "browser": value += "Qt.TextInteractionFlag.TextBrowserInteraction"; break;
                }
                if(++i < flags.length) value += ", ";
            }
            Utils.tryEmptyAppend(n, value, "%s.setAlignment(Qt.AlignmentFlag.%s);\n",sb);
        }
        Utils.tryCheck(n, "text", "%s.setText(tr(\"%s\"));\n", sb, nodeMap);
        Utils.tryBoolean(n, "open-external-links", "%s.setOpenExternalLinks(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "word-warp", "%s.setWordWrap(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "scaled-contents", "%s.setScaledContents(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "indent", "%s.setIndent(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "", "%s.setMargin(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QLCDNumber", style);
        Frame(n, sb, nodeMap);
    }

    public void Frame(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if(!(prop = Utils.check("shadow", nodeMap)).isEmpty()){
            switch (prop) {
                case "sunken": value = "Sunken"; break;
                case "plain": value = "Plain"; break;
                case "raised": value = "Raised"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setFrameShadow(QFrame.Shadow.%s);\n",sb);
        }
        if(!(prop = Utils.check("shape", nodeMap)).isEmpty()){
            switch (prop) {
                case "no-frame": value = "NoFrame"; break;
                case "box": value = "Box"; break;
                case "panel": value = "Panel"; break;
                case "styled-panel": value = "StyledPanel"; break;
                case "horizontal-line": value = "HLine"; break;
                case "vertical-line": value = "VLine"; break;
                case "window-panel": value = "WinPanel"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setFrameShape(QFrame.Shape.%s);\n",sb);
        }
        if(!(prop = Utils.check("size", nodeMap)).isEmpty()){
            String[] sizes = prop.split(" ");
            sb.append(String.format("%s.setFrameRect(new QRect(", n));

            String v1 = "", v2 = "", v3 = "", v4 = "";
            try {
                v1 = sizes[0]; v2 = sizes[1]; v3 = sizes[2]; v4 = sizes[3];
            }catch (NullPointerException ignored){
            }
            switch (sizes.length){
                case 4: sb.append(String.format("0, 0, %s, %s));\n", v1, v1)); break;
                case 3: sb.append(String.format("0, 0, %s, %s));\n", v1, v2)); break;
                case 2: sb.append(String.format("%s, %s, %3s, %4s));\n", v1, v1, v2, v3)); break;
                case 1: sb.append(String.format("%s, %s, %3s, %4s));\n", v1, v2, v3, v4)); break;
            }
        }
        Widget(n, sb, nodeMap);
    }

    public void Slider(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QSlider");
        if(!(prop = Utils.check("tick-position", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setTickPosition(QSlider.TickPosition.", n));
            switch(prop){
                case "both": value = "TicksBothSides"; break;
                case "above": case "left": value = "TicksAbove"; break;
                case "below": case "right": value = "TicksBelow"; break;
                case "no-ticks": value = "NoTicks"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setTickPosition(QSlider.TickPosition.%s);\n", sb);
        }
        Utils.tryValue(n, "interval", "%s.setTickInterval(%s);\n", sb, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QSlider", style);
        setStyle(style, nodeMap);
        AbstractSlider(n, sb, nodeMap);
    }

    public void AbstractSlider(String n, StringBuilder sb, NamedNodeMap nodeMap){
        String[] range = Utils.check("range", nodeMap).split(" ");
        if (range.length > 1) sb.append(String.format("%s.setRange(%s, %s);\n", n, range[0], range[1]));
        if(!(prop = Utils.check("orientation", nodeMap)).isEmpty()){
            switch(prop){
                case "vertical": value = "Vertical"; break;
                case "horizontal": value = "Horizontal"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setOrientation(Qt.Orientation.%s);\n", sb);
        }
        if(!(prop = Utils.check("invert-numbers", nodeMap)).isEmpty()){
            switch(prop){
                case "vertical": value = "Vertical"; break;
                case "horizontal": value = "Horizontal"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setInvertedControls(Qt.Orientation.%s);\n", sb);
        }
        Utils.tryBoolean(n, "invert-numbers", "%s.setInvertedControls(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "invert-controls", "%s.setInvertedAppearance(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "tracking", "%s.setTracking(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "slide-down-tracking", "%s.setSliderPosition(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "min-value", "%s.setMinimum(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "max-value", "%s.setMaximum(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "value", "%s.setValue(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "page-steps", "%s.setPageStep(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "single-steps", "%s.setSingleStep(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "slide-position", "%s.setSliderPosition(%s);\n", sb, nodeMap);
        Widget(n, sb, nodeMap);
    }

    public void PushButton(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QPushButton");
        Utils.tryCheck(n, "shortcut", "%s.setShortcut(new QKeySequence(tr(%s));\n", sb, nodeMap);
        Utils.tryBoolean(n, "default", "%s.setDefault(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "flat", "%s.setFlat(%s);\n", sb, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QPushButton", style);
        setStyle(style, nodeMap);
        AbstractButton(n, sb, nodeMap);
    }

    public void RadioButton(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QRadioButton");
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QRadioButton", style);
        setStyle(style, nodeMap);
        AbstractButton(n, sb, nodeMap);
    }

    public void CheckBox(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QCheckBox");
        if(!(prop = Utils.check("check-state", nodeMap)).isEmpty()){
            switch(prop){
                case "unchecked": value = "Unchecked"; break;
                case "partially-checked": value = "PartiallyChecked"; break;
                case "checked": value = "Checked"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setCheckState(Qt.CheckState.%s);\n", sb);
        }
        Utils.tryBoolean(n, "checkable", "%s.setTristate(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "default", "%s.setDefaultUp(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "native-menubar", "%s.setNativeMenuBar(%s);\n", sb, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QCheckBox", style);
        setStyle(style, nodeMap);
        AbstractButton(n, sb, nodeMap);
    }

    public void AbstractButton(String n, StringBuilder sb, NamedNodeMap nodeMap){
        Utils.tryCheck(n, "text", "%s.setText(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "checkable", "%s.setCheckable(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "checked", "%s.setChecked(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "pressed", "%s.setDown(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "repeatable", "%s.setAutoRepeat(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "exclusive", "%s.setAutoExclusive(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "repeatable-delay", "%s.setAutoRepeatDelay(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "repeatable-interval", "%s.setAutoRepeatInterval(%s);\n", sb, nodeMap);
        Widget(n, sb, nodeMap);
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
        if(!(prop = Utils.check("dock-option", nodeMap)).isEmpty()){
            switch(prop){
                case "animated-docks": value = "AnimatedDocks"; break;
                case "allow-nested-docks": value = "AllowNestedDocks"; break;
                case "allow-tabbed-docks": value = "AllowTabbedDocks"; break;
                case "force-tabbed-docks": value = "ForceTabbedDocks"; break;
                case "vertical-tabs": value = "VerticalTabs"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setDockOptions(DockOption.%s);\n", sb);
        }
        if(!(prop = Utils.check("tab-shape", nodeMap)).isEmpty()){
            switch(prop){
                case "rounded": value = "Rounded"; break;
                case "triangular": value = "Triangular"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setTabShape(QTabWidget.TabShape.%s);\n", sb);
        }
        if(!(prop = Utils.check("tool-button-style", nodeMap)).isEmpty()){
            switch(prop){
                case "icon-only": value = "ToolButtonIconOnly"; break;
                case "text-only": value = "ToolButtonTextOnly"; break;
                case "text-beside-icon": value = "ToolButtonTextBesideIcon"; break;
                case "text-under-icon": value = "ToolButtonTextUnderIcon"; break;
                case "follow-style": value = "ToolButtonFollowStyle"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setToolButtonStyle(Qt.ToolButtonStyle.%s);\n", sb);
        }
        Utils.tryBoolean(n, "dock-animation", "%s.setAnimated(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "dock-nesting", "%s.setDockNestingEnabled(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "document-mode", "%s.setDocumentMode(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "unified-mac-title-toolbar", "%s.setUnifiedTitleAndToolBarOnMac(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put(n, style);
        Widget(n, sb, nodeMap);
    }

    public void MenuBar(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QMenuBar");
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QMenuBar", style);
        Widget(n, sb, nodeMap);
    }

    public void Menu(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QMenu");
        Utils.tryCheck(n, "icon", "%s.setIcon(%s);\n", sb, nodeMap);
        Utils.tryCheck(n, "title", "%s.setTitle(\"%s\");\n", "menu", sb, nodeMap);
        Utils.tryBoolean(n, "tear-off", "%s.setTearOffEnabled(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QMenu", style);
        Widget(n, sb, nodeMap);
    }

    public void Grid(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QGridLayout");
        new QGridLayout().setOriginCorner(Qt.Corner.BottomRightCorner);
        if(!(prop = Utils.check("tool-button-style", nodeMap)).isEmpty()){
            switch(prop){
                case "bottom-right": value = "BottomRightCorner"; break;
                case "bottom-left": value = "BottomLeftCorner"; break;
                case "top-right": value = "TopRightCorner"; break;
                case "top-left": value = "TopLeftCorner"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setToolButtonStyle(Qt.ToolButtonStyle.%s);\n", sb);
        }
        Utils.tryValue(n, "spacing", "%s.setSpacing(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "row-spacing", "%s.setVerticalSpacing(%s);\n", sb, nodeMap);
        Utils.tryValue(n, "column-spacing", "%s.setHorizontalSpacing(%s);\n", sb, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QGridLayout", style);
        setStyle(style, nodeMap);
    }

    public void Widget(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if(!(prop = Utils.check("margin", nodeMap)).isEmpty()) {
            String[] margins = prop.replaceAll("px", "").split(" ");
            sb.append(String.format("%s.setContentsMargins(", n));
            String v1 = "", v2 = "", v3 = "", v4 = "";
            switch (margins.length){
                case 4: v1 = margins[0]; v2 = margins[1]; v3 = margins[2]; v4 = margins[3]; break;
                case 3: v1 = margins[0]; v2 = margins[1]; v3 = margins[2]; v4 = margins[1]; break;
                case 2: v1 = margins[0]; v2 = margins[1]; v3 = margins[0]; v4 = margins[1]; break;
                case 1: v1 = margins[0]; v2 = margins[0]; v3 = margins[0]; v4 = margins[0]; break;
            }
            String value = String.format("%s, %s, %s, %s", v1, v2, v3, v4);
            Utils.tryEmptyAppend(n, value, "%s.setContentsMargins(%s));\n", sb);
        }
        if(!(prop = Utils.check("cursor", nodeMap)).isEmpty()){
            Utils.tryEmptyAppend(n, cursors.get(prop), "%s.setCursor(new QCursor(Qt.CursorShape.%s));\n", sb);
        }
        Utils.tryCheck(n, "tool-tip", "%s.setToolTip(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "mouse-tracking", "%s.setMouseTracking(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "visibility", "%s.setHidden(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "update", "%s.setUpdatesEnabled(%s);\n", sb, nodeMap);
    }

    public void Action(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QAction");
        if(!(prop = Utils.check("menu-role", nodeMap)).isEmpty()){
            switch(prop){
                case "none": value = "NoRole"; break;
                case "text-heuristic": value = "TextHeuristicRole"; break;
                case "application-specific": value = "ApplicationSpecificRole"; break;
                case "about-qt": value = "AboutQtRole"; break;
                case "about": value = "AboutRole"; break;
                case "preferences": value = "PreferencesRole"; break;
                case "quit": value = "QuitRole"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setMenuRole(QAction.MenuRole.%s);\n", sb);
        }
        if(!(prop = Utils.check("priority", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setPriority(QAction.Priority.", n));
            switch(prop){
                case "high": value = "HighPriority"; break;
                case "normal": value = "NormalPriority"; break;
                case "low": value = "LowPriority"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setPriority(QAction.Priority.%s);\n", sb);
        }
        if(!(prop = Utils.check("soft-key-role", nodeMap)).isEmpty()){
            switch(prop){
                case "none": value = "NoSoftKey"; break;
                case "positive": value = "PositiveSoftKey"; break;
                case "negative": value = "NegativeSoftKey"; break;
                case "select": value = "SelectSoftKey"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setSoftKeyRole(QAction.SoftKeyRole.%s);\n", sb);
        }
        if(!(prop = Utils.check("shortcut-context", nodeMap)).isEmpty()){
            switch(prop){
                case "widget": value = "WidgetShortcut"; break;
                case "Widget-children": value = "WidgetWithChildrenShortcut"; break;
                case "Window": value = "WindowShortcut"; break;
                case "Application": value = "ApplicationShortcut"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setShortcutContext(Qt.ShortcutContext.%s);\n", sb);
        }
        Utils.tryCheck(n, "shortcut", "%s.setShortcut(new QKeySequence(tr(\"%s\")));\n", sb, nodeMap);
        Utils.tryCheck(n, "status-tip", "%s.setStatusTip(%s);\n", sb, nodeMap);
        Utils.tryCheck(n, "tool-tip", "%s.setToolTip(%s);\n", sb, nodeMap);
        Utils.tryCheck(n, "whats-this", "%s.setWhatsThis(%s);\n", sb, nodeMap);
        Utils.tryCheck(n, "icon", "%s.setIcon(%s);\n", sb, nodeMap);
        Utils.tryCheck(n, "icon-Text", "%s.setIconText(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "repeatable", "%s.setAutoRepeat(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "checkable", "%s.setCheckable(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "icon-visible", "%s.setIconVisibleMenu(%s);\n", sb, nodeMap);
        Utils.tryBoolean(n, "is-separator", "%s.setSeparator(%s);\n", sb, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QAction", style);
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
