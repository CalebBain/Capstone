package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class InlineStyleParser {
    private String prop;
    private String value;
    private String p;

    private Map<String, String> cursors = new HashMap<String, String>(){{
        put("arrow", "Qt.CursorShape.ArrowCursor");
        put("up-arrow", "Qt.CursorShape.UpArrowCursor");
        put("cross", "Qt.CursorShape.CrossCursor");
        put("wait", "Qt.CursorShape.WaitCursor");
        put("vertical-beam", "Qt.CursorShape.IBeamCursor");
        put("size-vertical", "Qt.CursorShape.SizeVerCursor");
        put("size-horizontal", "Qt.CursorShape.SizeHorCursor");
        put("size-major-diagonal", "Qt.CursorShape.SizeFDragCursor");
        put("size-minor-diagonal", "Qt.CursorShape.SizeBDragCursor");
        put("size-all", "Qt.CursorShape.SizeAllCursor");
        put("blank", "Qt.CursorShape.BlankCursor");
        put("vertical-split", "Qt.CursorShape.SplitVCursor");
        put("horizontal-split", "Qt.CursorShape.SplitHCursor");
        put("hand-pointer", "Qt.CursorShape.PointingHandCursor");
        put("forbidden", "Qt.CursorShape.ForbiddenCursor");
        put("open-hand", "Qt.CursorShape.OpenHandCursor");
        put("closed-hand", "Qt.CursorShape.OpenHandCursor");
        put("question-mark", "Qt.CursorShape.WhatsThisCursor");
        put("busy", "Qt.CursorShape.BusyCursor");
        put("drag-move", "Qt.CursorShape.DragMoveCursor");
        put("drag-copy", "Qt.CursorShape.DragMoveCursor");
        put("drag-link", "Qt.CursorShape.DragLinkCursor");
        put("bitmap", "Qt.CursorShape.BitmapCursor");
    }};
    private Map<String, String> StyleStrategy = new HashMap<String, String>(){{
        put("prefer-default", "PreferDefault");
        put("prefer-bitmap", "PreferBitmap");
        put("prefer-device", "PreferDevice");
        put("prefer-outline", "PreferOutline");
        put("force-outline", "ForceOutline");
        put("no-antialias", "NoAntialias");
        put("prefer-antialias", "PreferAntialias");
        put("opengl-compatible", "OpenGLCompatible");
        put("no-font-merging", "NoFontMerging");
        put("prefer-match", "PreferMatch");
        put("prefer-quality", "PreferQuality");
        put("force-integer-metrics", "ForceIntegerMetrics");
    }};
    private Map<String, String> TextFormatType = new HashMap<String, String>(){{
        put("none", "0");
        put("type", "1");
        put("table", "2");
        put("cell", "3");
        put("user", "0x1000");
    }};
    private Map<String, String> TextFormatDirection = new HashMap<String, String>(){{
        put("left-to-right", "Qt.LayoutDirection.LeftToRight");
        put("right-to-left", "Qt.LayoutDirection.RightToLeft");
        put("auto", "Qt.LayoutDirection.LayoutDirectionAuto");
    }};
    private Map<String, String> Alignment = new HashMap<String, String>(){{
        put("absolute", "Qt.AlignmentFlag.AlignAbsolute");
        put("bottom", "Qt.AlignmentFlag.AlignBottom");
        put("center", "Qt.AlignmentFlag.AlignCenter");
        put("horizontal-center", "Qt.AlignmentFlag.AlignHCenter");
        put("justify", "Qt.AlignmentFlag.AlignJustify");
        put("left", "Qt.AlignmentFlag.AlignLeft");
        put("right", "Qt.AlignmentFlag.AlignRight");
        put("top", "Qt.AlignmentFlag.AlignTop");
        put("vertical-center", "Qt.AlignmentFlag.AlignVCenter");
    }};
    private Map<String, String> ListMovement = new HashMap<String, String>(){{
        put("static", "QListView.Movement.Static");
        put("free", "QListView.Movement.Free");
        put("snap", "QListView.Movement.Snap");
    }};
    private Map<String, String> TabType = new HashMap<String, String>(){{
        put("center", "QTextOption.TabType.CenterTab");
        put("left", "QTextOption.TabType.LeftTab");
        put("right", "QTextOption.TabType.RightTab");
        put("delimiter", "QTextOption.TabType.DelimiterTab");
    }};
    private Map<String, String> Styles = new HashMap<String, String>(){{
        put("alt-background-color", "alt-background-color");      put("top", "top");      put("border", "border");
        put("background", "background");         put("border-right", "border-right");         put("left", "left");
        put("background-color", "background-color");                  put("background-image", "background-image");
        put("background-repeat", "background-repeat");     put("padding", "padding");     put("bottom", "bottom");
        put("background-attachment", "background-attachment");  put("right", "right");  put("opacity", "opacity");
        put("border-right-color", "border-right-color");                    put("border-bottom", "border-bottom");
        put("background-position", "background-position"); put("border-top", "border-top"); put("width", "width");
        put("selection-background-color", "selection-background-color");        put("border-left", "border-left");
        put("border-top-color", "border-top-color");   put("border-color", "border-color");   put("font", "font");
        put("border-image", "border-image");     put("font-weight", "font-weight");     put("spacing", "spacing");
        put("alt-empty-row-color", "paint-alternating-row-colors-for-empty-area");        put("height", "height");
        put("border-bottom-right-radius", "border-bottom-right-radius");        put("margin-left", "margin-left");
        put("border-top-style", "border-top-style");                put("border-left-style", "border-left-style");
        put("border-bottom-left-radius", "border-bottom-left-radius");      put("border-radius", "border-radius");
        put("background-origin", "background-origin");  put("border-top-right-radius", "border-top-right-radius");
        put("border-bottom-color", "border-bottom-color");      put("border-bottom-width", "border-bottom-width");
        put("dialogbuttons-have-icons", "dialogbuttonbox-buttons-have-icons");        put("position", "position");
        put("textbox-interaction", "messagebox-text-interaction-flags");                    put("color", "color");
        put("border-top-width", "border-top-width"); put("padding-top", "padding-top");     put("right", "right");
        put("border-bottom-style", "border-bottom-style");                    put("margin-right", "margin-right");
        put("border-top-left-radius", "border-top-left-radius");  put("image", "image"); put("icon", "file-icon");
        put("selection-decoration", "show-decoration-selected");        put("text-decoration", "text-decoration");
        put("subcontrol-position", "subcontrol-position");                    put("padding-left", "padding-left");
        put("border-right-style", "border-right-style");                 put("grid-line-color", "gridline-color");
        put("margin-bottom", "margin-bottom"); put("font-family", "font-family");   put("font-size", "font-size");
        put("max-width", "max-width");       put("icon-size", "icon-size");       put("font-style", "font-style");
        put("min-width", "min-width");   put("padding-right", "padding-right");   put("text-align", "text-align");
        put("border-right-width", "border-right-width");            put("border-left-color", "border-left-color");
        put("image-position", "image-position"); put("max-height", "max-height"); put("min-height", "min-height");
        put("background-clip", "background-clip");                  put("subcontrol-origin", "subcontrol-origin");
        put("border-style", "border-style"); put("border-width", "border-width"); put("margin-top", "margin-top");
        put("button-layout", "button-layout");                          put("selection-color", "selection-color");
        put("padding-bottom", "padding-bottom");                    put("border-left-width", "border-left-width");
    }};
    private Map<String, String> ListLayoutMode = new HashMap<String, String>(){{
        put("single-pass", "QListView.LayoutMode.SinglePass");
        put("batched", "QListView.LayoutMode.Batched");
    }};
    private Map<String, String> ListFlow = new HashMap<String, String>(){{
        put("horizontal", "QListView.Flow.LeftToRight");
        put("vertical", "QListView.Flow.TopToBottom");
    }};
    private Map<String, String> ListViewMode = new HashMap<String, String>(){{
        put("list", "QListView.ViewMode.ListMode");
        put("icon", "QListView.ViewMode.IconMode");
    }};
    private Map<String, String> ListResizeMode = new HashMap<String, String>(){{
        put("adjust", "QListView.ResizeMode.Adjust");
        put("fixed", "QListView.ResizeMode.Fixed");
    }};
    private Map<String, String> AbstractItemViewDragDropMode = new HashMap<String, String>(){{
        put("no-drag-drop", "QAbstractItemView.DragDropMode.NoDragDrop");
        put("drop-only", "QAbstractItemView.DragDropMode.DropOnly");
        put("drag-only", "QAbstractItemView.DragDropMode.DragOnly");
        put("drag-drop", "QAbstractItemView.DragDropMode.DragDrop");
        put("internal-move", "QAbstractItemView.DragDropMode.InternalMove");
    }};
    private Map<String, String> AbstractItemViewEditTrigger = new HashMap<String, String>(){{
        put("no-edit-triggers", "QAbstractItemView.EditTrigger.NoEditTriggers");
        put("current-change", "QAbstractItemView.EditTrigger.CurrentChanged");
        put("double-click", "QAbstractItemView.EditTrigger.DoubleClicked");
        put("selected-click", "QAbstractItemView.EditTrigger.SelectedClicked");
        put("edit-key-press", "QAbstractItemView.EditTrigger.EditKeyPressed");
        put("any-key-press", "QAbstractItemView.EditTrigger.AnyKeyPressed");
        put("all-edit-triggers", "QAbstractItemView.EditTrigger.AllEditTriggers");
    }};
    private Map<String, String> AbstractItemViewScrollMode = new HashMap<String, String>(){{
        put("per-item", "QAbstractItemView.ScrollMode.ScrollPerItem");
        put("per-pixel", "QAbstractItemView.ScrollMode.ScrollPerPixel");
    }};
    private Map<String, String> AbstractItemViewSelectionBehavior = new HashMap<String, String>(){{
        put("items", "QAbstractItemView.SelectionBehavior.SelectItems");
        put("rows", "QAbstractItemView.SelectionBehavior.SelectRows");
        put("columns", "QAbstractItemView.SelectionBehavior.SelectColumns");
    }};
    private Map<String, String> AbstractItemViewTextElideMode = new HashMap<String, String>(){{
        put("left", "Qt.TextElideMode.ElideLeft");
        put("right", "Qt.TextElideMode.ElideRight");
        put("middle", "Qt.TextElideMode.ElideMiddle");
        put("none", "Qt.TextElideMode.ElideNone");
    }};
    private Map<String, String> PenCapStyle = new HashMap<String, String>(){{
        put("square", "Qt.PenCapStyle.SquareCap");
        put("flat", "Qt.PenCapStyle.FlatCap");
        put("round", "Qt.PenCapStyle.RoundCap");
    }};
    private Map<String, String> PenJoinStyle = new HashMap<String, String>(){{
        put("bevel", "Qt.PenJoinStyle.BevelJoin");
        put("miter", "Qt.PenJoinStyle.MiterJoin");
        put("round", "Qt.PenJoinStyle.RoundJoin");
    }};
    private Map<String, String> PenStyle = new HashMap<String, String>(){{
        put("solid", "Qt.PenStyle.SolidLine");
        put("dash", "Qt.PenStyle.DashLine");
        put("dot", "Qt.PenStyle.DotLine");
        put("dash-dot", "Qt.PenStyle.DashDotLine");
        put("dash-double-dot", "Qt.PenStyle.DashDotDotLine");
    }};
    private Map<String, String> GradientTypes = new HashMap<String, String>(){{
        put("conical", "QConicalGradient");
        put("linear", "QLinearGradient");
        put("radial", "QRadialGradient");
    }};
    private Map<String, String> GradientSpreads = new HashMap<String, String>(){{
        put("pad", "QGradient.Spread.PadSpread");
        put("repeat", "QGradient.Spread.RepeatSpread");
        put("reflect", "QGradient.Spread.ReflectSpread");
    }};
    private Map<String, String> GradientCoordinateModes = new HashMap<String, String>(){{
        put("logical", "QGradient.CoordinateMode.LogicalMode");
        put("stretch", "QGradient.CoordinateMode.StretchToDeviceMode");
        put("bounding", "QGradient.CoordinateMode.ObjectBoundingMode");
    }};
    private Map<String, String> Colors = new HashMap<String, String>(){{
        put("white", "#ffffff");
        put("light-gray", "#d3d3d3");
        put("gray", "#808080");
        put("dark-gray", "#a9a9a9");
        put("black", "#000000");
        put("pink", "#ff8b8b");
        put("red", "#ff0000");
        put("dark-red", "#8b0000");
        put("light-orange", "#ffd700");
        put("orange", "#ffa500");
        put("dark-orange", "#ff8b00");
        put("yellow", "#ffff00");
        put("dark-yellow", "#8b8b00");
        put("light-green", "#8bff8b");
        put("green", "#00ff00");
        put("dark-green", "#008b00");
        put("light-cyan", "#8bffff");
        put("cyan", "#00ffff");
        put("dark-cyan", "#008b8b");
        put("light-blue", "#8b8bff");
        put("blue", "#0000ff");
        put("dark-blue", "#00008b");
        put("light-magenta", "#ff8bff");
        put("magenta", "#ff00ff");
        put("purple", "#8b008b");
    }};
    private Map<String, String> Patterns = new HashMap<String, String>(){{
        put("solid", "Qt.BrushStyle.SolidPattern");
        put("extremely-dense", "Qt.BrushStyle.Dense1Pattern");
        put("very-dense", "Qt.BrushStyle.Dense2Pattern");
        put("somewhat-dense", "Qt.BrushStyle.Dense3Pattern");
        put("half-dense", "Qt.BrushStyle.Dense4Pattern");
        put("somewhat-sparse", "Qt.BrushStyle.Dense5Pattern");
        put("very-sparse", "Qt.BrushStyle.Dense6Pattern");
        put("extremely-sparse", "Qt.BrushStyle.Dense7Pattern");
        put("empty", "Qt.BrushStyle.NoBrush");
        put("horizontal-lines", "Qt.BrushStyle.HorPattern");
        put("vertical-lines", "Qt.BrushStyle.VerPattern");
        put("cross-lines", "Qt.BrushStyle.CrossPattern");
        put("backward-diagonal-lines", "Qt.BrushStyle.BDiagPattern");
        put("forward-diagonal-lines", "Qt.BrushStyle.FDiagPattern");
        put("diagonal-cross-lines", "Qt.BrushStyle.DiagCrossPattern");
    }};
    private Map<String, String> ScrollBarPolicy = new HashMap<String, String>(){{
        put("always-on", "Qt.ScrollBarPolicy.ScrollBarAlwaysOn");
        put("as-needed", "Qt.ScrollBarPolicy.ScrollBarAsNeeded");
        put("always-off", "Qt.ScrollBarPolicy.ScrollBarAlwaysOff");
    }};
    private Map<String, String> TextFormat = new HashMap<String, String>(){{
        put("auto", "Qt.TextFormat.AutoText");
        put("plain", "Qt.TextFormat.PlainText");
        put("rich", "Qt.TextFormat.RichText");
        put("log", "Qt.TextFormat.LogText");
    }};
    private Map<String, String> TextInteractionFlag = new HashMap<String, String>(){{
        put("none", "Qt.TextInteractionFlag.NoTextInteraction");
        put("mouse-select", "Qt.TextInteractionFlag.TextSelectableByMouse");
        put("key-select", "Qt.TextInteractionFlag.TextSelectableByKeyboard");
        put("mouse-access", "Qt.TextInteractionFlag.LinksAccessibleByMouse");
        put("key-access", "Qt.TextInteractionFlag.LinksAccessibleByKeyboard");
        put("editable", "Qt.TextInteractionFlag.TextEditable");
        put("editor", "Qt.TextInteractionFlag.TextEditorInteraction");
        put("browser", "Qt.TextInteractionFlag.TextBrowserInteraction");
    }};
    private Map<String, String> Orientation = new HashMap<String, String>(){{
        put("vertical", "Qt.Orientation.Vertical");
        put("horizontal", "Qt.Orientation.Horizontal");
    }};
    private Map<String, String> FrameShadow = new HashMap<String, String>(){{
        put("sunken", "QFrame.Shadow.Sunken");
        put("plain", "QFrame.Shadow.Plain");
        put("always-off", "QFrame.Shadow.Raised");
    }};
    private Map<String, String> FrameShape = new HashMap<String, String>(){{
        put("no-frame", "QFrame.Shape.NoFrame");
        put("box", "QFrame.Shape.Box");
        put("panel", "QFrame.Shape.Panel");
        put("styled-panel", "QFrame.Shape.StyledPanel");
        put("horizontal-line", "QFrame.Shape.HLine");
        put("vertical-line", "QFrame.Shape.VLine");
        put("window-panel", "QFrame.Shape.WinPanel");
    }};
    private Map<String, String> SliderTickPosition = new HashMap<String, String>(){{
        put("both", "QSlider.TickPosition.TicksBothSides");
        put("above", "QSlider.TickPosition.TicksAbove");
        put("left", "QSlider.TickPosition.TicksAbove");
        put("below", "QSlider.TickPosition.TicksBelow");
        put("right", "QSlider.TickPosition.TicksBelow");
        put("no-ticks", "QSlider.TickPosition.NoTicks");
    }};
    private Map<String, String> CheckState = new HashMap<String, String>(){{
        put("unchecked", "Qt.CheckState.Unchecked");
        put("partially-checked", "Qt.CheckState.PartiallyChecked");
        put("checked", "Qt.CheckState.Checked");
    }};
    private Map<String, String> DockOption = new HashMap<String, String>(){{
        put("animated-docks", "DockOption.AnimatedDocks");
        put("allow-nested-docks", "DockOption.AllowNestedDocks");
        put("allow-tabbed-docks", "DockOption.AllowTabbedDocks");
        put("force-tabbed-docks", "DockOption.ForceTabbedDocks");
        put("vertical-tabs", "DockOption.VerticalTabs");
    }};
    private Map<String, String> TabShape = new HashMap<String, String>(){{
        put("rounded", "QTabWidget.TabShape.Rounded");
        put("triangular", "QTabWidget.TabShape.Triangular");
    }};
    private Map<String, String> ToolButtonStyle = new HashMap<String, String>(){{
        put("icon-only", "Qt.ToolButtonStyle.ToolButtonIconOnly");
        put("text-only", "Qt.ToolButtonStyle.ToolButtonTextOnly");
        put("text-beside-icon", "Qt.ToolButtonStyle.ToolButtonTextBesideIcon");
        put("text-under-icon", "Qt.ToolButtonStyle.ToolButtonTextUnderIcon");
        put("follow-style", "Qt.ToolButtonStyle.ToolButtonFollowStyle");
    }};
    private Map<String, String> EchoMode = new HashMap<String, String>(){{
        put("normal", "QLineEdit.EchoMode.Normal");
        put("none", "QLineEdit.EchoMode.NoEcho");
        put("password", "QLineEdit.EchoMode.Password");
        put("echo-on-edit", "QLineEdit.EchoMode.PasswordEchoOnEdit");
    }};
    private Map<String, String> CursorMoveStyle = new HashMap<String, String>(){{
        put("logical", "Qt.CursorMoveStyle.LogicalMoveStyle");
        put("visual", "Qt.CursorMoveStyle.VisualMoveStyle");
    }};
    private Map<String, String> TextAlignment = new HashMap<String, String>(){{
        put("left", "1");
        put("right", "2");
        put("horizontal-center", "4");
        put("justify", "8");
        put("absolute", "10");
        put("top", "20");
        put("bottom", "40");
        put("vertical-center", "80");
        put("center", "84");
    }};

    protected void List(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QListWidget");
        setName(n, sb);
        tryBoolean(n, "sorting", "%s.setSortingEnabled(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QListView", style);
        ListView(n, sb, nodeMap);
    }
    protected void ListView(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(prop = Utils.check("grid-size", nodeMap)).isEmpty()){
            String[] parts = prop.split(" ");
            sb.append(String.format("%s.setGridSize(new QSize(%s, %s);\n", n, parts[0], parts[1]));
        }
        TryList(n, "movement", "%s.setMovement(%s);\n", ListMovement, sb, nodeMap);
        TryList(n, "layout-mode", "%s.setLayoutMode(%s);\n", ListLayoutMode, sb, nodeMap);
        TryList(n, "orientation", "%s.setFlow(%s);\n", ListFlow, sb, nodeMap);
        TryList(n, "view-mode", "%s.setViewMode(%s);\n", ListViewMode, sb, nodeMap);
        TryList(n, "resize-mode", "%s.setResizeMode(%s);\n", ListResizeMode, sb, nodeMap);
        tryBoolean(n, "selection-rectangle-visible", "%s.setSelectionRectVisible(%s);\n", sb, nodeMap);
        tryBoolean(n, "uniform-sizes", "%s.setUniformItemSizes(%s);\n", sb, nodeMap);
        tryBoolean(n, "word-wrap", "%s.setWordWrap(%s);\n", sb, nodeMap);
        tryBoolean(n, "wrapping", "%s.setWrapping(%s);\n", sb, nodeMap);
        tryValue(n, "batch-size", "%s.setBatchSize(%s);\n", sb, nodeMap);
        tryValue(n, "model-column", "%s.setModelColumn(%s);\n", sb, nodeMap);
        tryValue(n, "spacing", "%s.setSpacing(%s);\n", sb, nodeMap);
        AbstractItemView(n, sb, nodeMap);
    }
    protected void AbstractItemView(String n, StringBuilder sb, NamedNodeMap nodeMap){
        TryList(n, "drag-drop-mode", "%s.setDragDropMode(%s);\n", AbstractItemViewDragDropMode, sb, nodeMap);
        TryList(n, "edit-trigger", "%s.setEditTriggers(%s);\n", AbstractItemViewEditTrigger, sb, nodeMap);
        TryList(n, "horizontal-scroll-mode", "%s.setHorizontalScrollMode(%s);\n", AbstractItemViewScrollMode, sb, nodeMap);
        TryList(n, "selection-behavior", "%s.setSelectionBehavior(%s);\n", AbstractItemViewSelectionBehavior, sb, nodeMap);
        TryList(n, "text-elide-mode", "%s.setTextElideMode(%s);\n", AbstractItemViewTextElideMode, sb, nodeMap);
        TryList(n, "vertical-scroll-mode", "%s.setVerticalScrollMode(%s);\n", AbstractItemViewScrollMode, sb, nodeMap);
        tryBoolean(n, "alt-row-color", "%s.setAlternatingRowColors(%s);\n", sb, nodeMap);
        tryBoolean(n, "draggable", "%s.setDragEnabled(%s);\n", sb, nodeMap);
        tryBoolean(n, "auto-scroll", "%s.setAutoScroll(%s);\n", sb, nodeMap);
        tryBoolean(n, "drop-indicator", "%s.setDropIndicatorShown(%s);\n", sb, nodeMap);
        tryBoolean(n, "tab-key-navigation", "%s.TabKeyNavigation(%s);\n", sb, nodeMap);
        tryValue(n, "auto-scroll-margin", "%s.setAutoScrollMargin(%s);\n", sb, nodeMap);
        AbstractScrollArea(n, sb, nodeMap);
    }
    // need todo
    protected void TextEdit(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QListWidget");
        setName(n, sb);

        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QListView", style);
        AbstractScrollArea(n, sb, nodeMap);
    }

    private void TextCharFormat(String n, StringBuilder sb, NamedNodeMap nodeMap){
        setName(n, sb);
        tryBoolean(n, "anchor", "%s.setAnchor(%s);\n", sb, nodeMap);
    }

    private void TextFormat(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(prop = Utils.check("direction", nodeMap)).isEmpty())
            Utils.tryEmptyAppend(n, TextFormatDirection.get(prop), "%s.setLayoutDirection(%s);\n",sb);
        if (!(prop = Utils.check("type", nodeMap)).isEmpty())
            Utils.tryEmptyAppend(n, TextFormatType.get(prop), "%s.setObjectType(%s);\n",sb);
        if (!(prop = Utils.check("properties", nodeMap)).isEmpty()) {
            String[] props = prop.split(" ");
            for(String p : props){
                String[] parts = p.split(":");
                String key = "";
                switch (parts[0]) {
                    case "object-index": key = "0x0"; value = parts[1]; break;
                    case "css-float": key = "0x800"; value = parts[1]; break;
                    case "layout-direction": key = "0x801"; value = String.format("\"%s\"", parts[1]); break;
                    case "background-image": key = "0x823"; value = String.format("\"%s\"", parts[1]); break;
                    case "block-alignment": key = "0x1010"; value = String.format("%s", Alignment.get(parts[1])); break;
                    case "block-top-margin": key = "0x1030"; value = parts[1]; break;
                    case "block-bottom-margin": key = "0x1031"; value = parts[1]; break;
                    case "block-left-margin": key = "0x1032"; value = parts[1]; break;
                    case "block-right-margin": key = "0x1033"; value = parts[1]; break;
                    case "text-indent": key = "0x1034"; value = parts[1]; break;

                    case "block-indent": key = "0x1040"; value = parts[1]; break;

                    case "line-height": key = "0x1048"; value = parts[1]; break;

                    case "horizontal-ruler-width": key = "0x1060"; value = parts[1]; break;

                    case "font-family": key = "0x2000"; value = String.format("\"%s\"", parts[1]); break;
                    case "font-point-size": key = "0x2001"; value = parts[1]; break;
                    case "font-size-adjust": key = "0x2002"; value = parts[1]; break;
                    case "font-weight": key = "0x2003"; value = parts[1]; break;
                    case "italic": key = "0x2004"; value = parts[1]; break;

                    case "overline": key = "0x2006"; value = parts[1]; break;
                    case "strike-out": key = "0x2007"; value = parts[1]; break;
                    case "font-pitch": key = "0x2008"; value = parts[1]; break;
                    case "font-pixel-size": key = "0x2009"; value = parts[1]; break;

                    case "letter-spacing": key = "0x1FE1"; value = parts[1]; break;
                    case "word-spacing": key = "0x1FE2"; value = parts[1]; break;
                    case "style-hint": key = "0x1FE3"; value = parts[1]; break;
                    case "style-strategy": key = "0x1FE4"; value = StyleStrategy.get(parts[1]); break;
                    case "kerning": value = "0x1FE5"; break;
                    case "hinting-preference": value = "0x1FE6"; break;

                    case "underline-color": value = "0x2010"; break;

                    case "text-vertical-align": value = "0x2021"; break;
                    case "outline": value = "0x2022"; break;
                    case "underline-style": value = "0x2023"; break;
                    case "tool-tip": value = "0x2024"; break;

                    case "is-anchor": value = "0x2030"; break;
                    case "anchor-href": value = "0x2031"; break;
                    case "anchor-name": value = "0x2032"; break;

                    case "object-type": value = "0x2F00"; break;

                    case "list-style": value = "0x3000"; break;
                    case "list-indent": value = "0x3001"; break;
                    case "list-number-prefix": value = "0x3002"; break;
                    case "list-number-suffix": value = "0x3003"; break;

                    case "frame-border": value = "0x4000"; break;
                    case "frame-margin": value = "0x4001"; break;
                    case "frame-padding": value = "0x4002"; break;
                    case "frame-width": value = "0x4003"; break;
                    case "frame-height": value = "0x4004"; break;
                    case "frame-top-margin": value = "0x4005"; break;
                    case "frame-bottom-margin": value = "0x4006"; break;
                    case "frame-left-margin": value = "0x4007"; break;
                    case "frame-right-margin": value = "0x4008"; break;
                    case "frame-border-brush": value = "0x4009"; break;
                    case "light-cyan": value = "0x"; break;
                    case "cyan": value = "0x"; break;
                    /*case "dark-cyan": value = "0x"; break;
                    case "light-blue": value = "0x"; break;
                    case "blue": value = "0x"; break;
                    case "dark-blue": value = "0x"; break;
                    case "light-purple": value = "0x"; break;
                    case "purple": value = "0x"; break;
                    case "dark-purple": value = "0x"; break;
                    case "light-magenta": value = "0x"; break;
                    case "magenta": value = "0x"; break;
                    case "dark-magenta": value = "0x"; break;
                    case "dark-cyan": value = "0x"; break;
                    case "light-blue": value = "0x"; break;
                    case "blue": value = "0x"; break;
                    case "dark-blue": value = "0x"; break;
                    case "light-purple": value = "0x"; break;
                    case "purple": value = "0x"; break;
                    case "dark-purple": value = "0x"; break;
                    case "light-magenta": value = "0x"; break;
                    case "magenta": value = "0x"; break;
                    case "dark-magenta": value = "0x"; break;*/
                    default: value = String.format("%s", prop);
                }
                Utils.tryEmptyAppend(n, value, "%s.setProperty(" + key +", %s);\n", sb);
            }
        }
        tryValue(n, "index", "%s.setObjectIndex(%s);\n", sb, nodeMap);
    }
    ///end of todo
    private String Tab(StringBuilder sb, NamedNodeMap nodeMap){
        String name = Utils.trySetName("tab");
        sb.append(String.format("QTextOption_Tab %s = new QTextOption_Tab();\n", name));
        TryList(name, "type", "%s.setType(%s);\n", TabType, sb, nodeMap);
        tryCheck(name, "delimiter", "%s.setText('%s');\n", sb, nodeMap);
        tryValue(name, "position", "%s.setPosition(%s);\n", sb, nodeMap);
        return "";
    }
    private String Pen(StringBuilder sb, NamedNodeMap nodeMap){
        String name = Utils.trySetName("pen");
        sb.append(String.format("QPen %s = new QPen();\n", name));
        TryList(name, "cap-style", "%s.setCapStyle(%s);\n", PenCapStyle, sb, nodeMap);
        TryList(name, "joint", "%s.setJoinStyle(%s);\n", PenJoinStyle, sb, nodeMap);
        TryList(name, "style", "%s.setStyle(%s);\n", PenStyle, sb, nodeMap);
        tryBoolean(name, "pen-cosmetic", "%s.setCosmetic(%s);\n", sb, nodeMap);
        tryValue(name, "pen-dash-offset", "%s.setDashOffset(%s);\n", sb, nodeMap);
        tryValue(name, "pen-miter-limit", "%s.setMiterLimit(%s);\n", sb, nodeMap);
        tryValue(name, "pen-width", "%s.setWidth(%s);\n", sb, nodeMap);
        return name;
    }
    private String Brush(StringBuilder sb, NamedNodeMap nodeMap) {
        boolean brush = false;
        String name = "";
        if(!(prop = Utils.check("gradient", nodeMap)).isEmpty()) {
            String n = Utils.trySetName("gradient");
            String[] parts = prop.split(" ");
            sb.append(String.format("QGradient %s = new %s(%s);\n", name, GradientTypes.get(parts[0]), parts[1]));
            sb.append(String.format("%s.setSpread(%s);\n", n, GradientSpreads.get(parts[2])));
            sb.append(String.format("%s.setCoordinateMode(%s);\n", n, GradientCoordinateModes.get(parts[3])));
            name = Utils.trySetName("brush");
            sb.append(String.format("QBrush %s = new QBrush(%s);\n", name, n));
        } else {
            if (!(prop = Utils.check("color", nodeMap)).isEmpty()) {
                name = Utils.trySetName("brush");
                sb.append(String.format("QBrush %s = new QBrush(", name));
                brush = true;
                if(Colors.containsKey(prop)) sb.append(Colors.get(prop));
                else sb.append(prop);
            }
            if (!(prop = Utils.check("pattern", nodeMap)).isEmpty()) {
                if (!brush) {
                    name = Utils.trySetName("brush");
                    sb.append(String.format("QBrush %s = new QBrush(", name));
                } else sb.append(", ");
                brush = true;
                sb.append(Patterns.get(prop));
            }
            if(brush) sb.append(")");
        }
        return name;
    }
    private void AbstractScrollArea(String n, StringBuilder sb, NamedNodeMap nodeMap){
        TryList(n, "horizontal-scroll-bar-policy", "%s.setHorizontalScrollBarPolicy(%s);\n", ScrollBarPolicy, sb, nodeMap);
        TryList(n, "vertical-scroll-bar-policy", "%s.setVerticalScrollBarPolicy(%s);\n", ScrollBarPolicy, sb, nodeMap);
        Frame(n, sb, nodeMap);
    }
    protected void LCDNumber(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QLCDNumber");
        setName(n, sb);
        tryCapitalize(n, "segment-style", "%s.setSegmentStyle(QLCDNumber.SegmentStyle.%s);\n", sb, nodeMap);
        tryCapitalize(n, "mode", "%s.setMode(QLCDNumber.Mode.%s);\n", sb, nodeMap);
        tryBoolean(n, "small-decimal-point", "%s.setSmallDecimalPoint(%s);\n", sb, nodeMap);
        tryValue(n, "digit-count", "%s.setDigitCount(%s);\n", sb, nodeMap);
        tryValue(n, "value", "%s.display(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QLCDNumber", style);
        Frame(n, sb, nodeMap);
    }
    protected void Label(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QLabel");
        setName(n, sb);
        TryList(n, "align", "%s.setAlignment(%s);\n", Alignment, sb, nodeMap);
        TryList(n, "text-format", "%s.setTextFormat(%s);\n", TextFormat, sb, nodeMap);
        TryList(n, "text-interaction", "%s.setTextInteractionFlags(%s);\n", TextInteractionFlag, sb, nodeMap);
        tryCheck(n, "text", "%s.setText(tr(\"%s\"));\n", sb, nodeMap);
        tryBoolean(n, "open-external-links", "%s.setOpenExternalLinks(%s);\n", sb, nodeMap);
        tryBoolean(n, "word-warp", "%s.setWordWrap(%s);\n", sb, nodeMap);
        tryBoolean(n, "scaled-contents", "%s.setScaledContents(%s);\n", sb, nodeMap);
        tryValue(n, "indent", "%s.setIndent(%s);\n", sb, nodeMap);
        tryValue(n, "margin", "%s.setMargin(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QLCDNumber", style);
        Frame(n, sb, nodeMap);
    }
    protected void Splitter(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap) {
        Style style = new Style(n, "QSplitter");
        setName(n, sb);
        TryList(n, "orientation", "%s.setOrientation(%s);\n", Orientation, sb, nodeMap);
        tryBoolean(n, "resizeable", "%s.setOpaqueResize(%s);\n", sb, nodeMap);
        tryValue(n, "handle-width", "%s.setHandleWidth(%s);\n", sb, nodeMap);
        tryValue(n, "rubber-band", "%s.setRubberBand(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QLCDNumber", style);
        Frame(n, sb, nodeMap);
    }
    protected void Frame(String n, StringBuilder sb, NamedNodeMap nodeMap){
        TryList(n, "shadow", "%s.setFrameShadow(%s);\n", FrameShadow, sb, nodeMap);
        TryList(n, "shape", "%s.setFrameShape(%s);\n", FrameShape, sb, nodeMap);
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
    protected void Slider(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QSlider");
        setName(n, sb);
        TryList(n, "tick-position", "%s.setTickPosition(%s);\n", SliderTickPosition, sb, nodeMap);
        tryValue(n, "interval", "%s.setTickInterval(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QSlider", style);
        AbstractSlider(n, sb, nodeMap);
    }
    private void AbstractSlider(String n, StringBuilder sb, NamedNodeMap nodeMap){
        String[] range = Utils.check("range", nodeMap).split(" ");
        if (range.length > 1) sb.append(String.format("%s.setRange(%s, %s);\n", n, range[0], range[1]));
        TryList(n, "orientation", "%s.setOrientation(%s);\n", Orientation, sb, nodeMap);
        TryList(n, "invert-numbers", "%s.setInvertedControls(%s);\n", Orientation, sb, nodeMap);
        tryBoolean(n, "invert-numbers", "%s.setInvertedControls(%s);\n", sb, nodeMap);
        tryBoolean(n, "invert-controls", "%s.setInvertedAppearance(%s);\n", sb, nodeMap);
        tryBoolean(n, "tracking", "%s.setTracking(%s);\n", sb, nodeMap);
        tryBoolean(n, "slide-down-tracking", "%s.setSliderPosition(%s);\n", sb, nodeMap);
        tryValue(n, "min-value", "%s.setMinimum(%s);\n", sb, nodeMap);
        tryValue(n, "max-value", "%s.setMaximum(%s);\n", sb, nodeMap);
        tryValue(n, "value", "%s.setValue(%s);\n", sb, nodeMap);
        tryValue(n, "page-steps", "%s.setPageStep(%s);\n", sb, nodeMap);
        tryValue(n, "single-steps", "%s.setSingleStep(%s);\n", sb, nodeMap);
        tryValue(n, "slide-position", "%s.setSliderPosition(%s);\n", sb, nodeMap);
        Widget(n, sb, nodeMap);
    }
    protected void PushButton(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QPushButton");
        setName(n, sb);
        tryCheck(n, "shortcut", "%s.setShortcut(new QKeySequence(tr(\"%s\"));\n", sb, nodeMap);
        tryBoolean(n, "default", "%s.setDefault(%s);\n", sb, nodeMap);
        tryBoolean(n, "flat", "%s.setFlat(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QPushButton", style);
        AbstractButton(n, sb, nodeMap);
    }
    protected void RadioButton(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QRadioButton");
        setName(n, sb);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QRadioButton", style);
        AbstractButton(n, sb, nodeMap);
    }
    protected void CheckBox(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QCheckBox");
        setName(n, sb);
        TryList(n, "check-state", "%s.setCheckState(%s);\n", CheckState, sb, nodeMap);
        tryBoolean(n, "checkable", "%s.setTristate(%s);\n", sb, nodeMap);
        tryBoolean(n, "default", "%s.setDefaultUp(%s);\n", sb, nodeMap);
        tryBoolean(n, "native-menubar", "%s.setNativeMenuBar(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QCheckBox", style);
        AbstractButton(n, sb, nodeMap);
    }
    private void AbstractButton(String n, StringBuilder sb, NamedNodeMap nodeMap){
        tryCheck(n, "text", "%s.setText(\"%s\");\n", sb, nodeMap);
        tryBoolean(n, "checkable", "%s.setCheckable(%s);\n", sb, nodeMap);
        tryBoolean(n, "checked", "%s.setChecked(%s);\n", sb, nodeMap);
        tryBoolean(n, "pressed", "%s.setDown(%s);\n", sb, nodeMap);
        tryBoolean(n, "repeatable", "%s.setAutoRepeat(%s);\n", sb, nodeMap);
        tryBoolean(n, "exclusive", "%s.setAutoExclusive(%s);\n", sb, nodeMap);
        tryValue(n, "repeatable-delay", "%s.setAutoRepeatDelay(%s);\n", sb, nodeMap);
        tryValue(n, "repeatable-interval", "%s.setAutoRepeatInterval(%s);\n", sb, nodeMap);
        Widget(n, sb, nodeMap);
    }
    protected void MainWindow(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QMainWindow");
        sb.append("QWidget centerWidget = new QWidget();\n");
        sb.append(String.format("%s.setCentralWidget(centerWidget);\n", n));
        sb.append(String.format("%s.setWindowTitle(tr(\"%s\"));\n", n, Utils.tryEmpty("title", "JAML Applicaiton", nodeMap)));
        TryList(n, "dock-option", "%s.setDockOptions(%s);\n", DockOption, sb, nodeMap);
        TryList(n, "tab-shape", "%s.setTabShape(%s);\n", TabShape, sb, nodeMap);
        TryList(n, "tool-button-style", "%s.setToolButtonStyle(%s);\n", ToolButtonStyle, sb, nodeMap);
        tryBoolean(n, "dock-animation", "%s.setAnimated(%s);\n", sb, nodeMap);
        tryBoolean(n, "dock-nesting", "%s.setDockNestingEnabled(%s);\n", sb, nodeMap);
        tryBoolean(n, "document-mode", "%s.setDocumentMode(%s);\n", sb, nodeMap);
        tryBoolean(n, "unified-mac-title-toolbar", "%s.setUnifiedTitleAndToolBarOnMac(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put(n, style);
        Widget(n, sb, nodeMap);
    }
    protected void MenuBar(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QMenuBar");
        setName(n, sb);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QMenuBar", style);
        Widget(n, sb, nodeMap);
    }
    protected void Menu(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QMenu");
        setName(n, sb);
        tryCheck(n, "icon", "%s.setIcon(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "title", "%s.setTitle(\"%s\");\n", "menu", sb, nodeMap);
        tryBoolean(n, "tear-off", "%s.setTearOffEnabled(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QMenu", style);
        Widget(n, sb, nodeMap);
    }
    protected void Section(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QWidget");
        setName(n, sb);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QGridLayout", style);
        Widget(n, sb, nodeMap);
    }
    protected void LineEdit(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap) {
        Style style = new Style(n, "QLineEdit");
        setName(n, sb);
        TryList(n, "align", "%s.setAlignment(%s);\n", Alignment, sb, nodeMap);
        TryList(n, "echo-mode", "%s.setEchoMode(%s);\n", EchoMode, sb, nodeMap);
        TryList(n, "cursor-move-style", "%s.setCursorMoveStyle(%s);\n", CursorMoveStyle, sb, nodeMap);
        tryCheck(n, "placeholder", "%s.setPlaceholderText(\"%s\");\n", sb, nodeMap);
        tryBoolean(n, "draggable", "%s.setDragEnabled(%s);\n", sb, nodeMap);
        tryBoolean(n, "frame", "%s.setFrame(%s);\n", sb, nodeMap);
        tryBoolean(n, "modified", "%s.setModified(%s);\n", sb, nodeMap);
        tryBoolean(n, "read-only", "%s.setReadOnly(%s);\n", sb, nodeMap);
        tryValue(n, "max-length", "%s.setMaxLength(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if (!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QAction", style);
        Widget(n, sb, nodeMap);
    }
    protected void Group(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap) {
        Style style = new Style(n, "QGroupBox");
        setName(n, sb);
        TryList(n, "align", "%s.setTextAlignment(%s);\n", TextAlignment, sb, nodeMap);
        tryCheck(n, "title", "%s.setTitle(\"%s\");\n", sb, nodeMap);
        tryBoolean(n, "flat", "%s.setFlat(%s);\n", sb, nodeMap);
        tryBoolean(n, "checkable", "%s.setCheckable(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if (!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QAction", style);
        Widget(n, sb, nodeMap);
    }
    protected void Widget(String n, StringBuilder sb, NamedNodeMap nodeMap){
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
            Utils.tryEmptyAppend(n, String.format("%s, %s, %s, %s", v1, v2, v3, v4), "%s.setContentsMargins(%s));\n", sb);
        }
        TryList(n, "cursor", "%s.setCursor(new QCursor(%s));\n", cursors, sb, nodeMap);
        tryCheck(n, "tool-tip", "%s.setToolTip(\"%s\");\n", sb, nodeMap);
        tryBoolean(n, "mouse-tracking", "%s.setMouseTracking(%s);\n", sb, nodeMap);
        tryBoolean(n, "visibility", "%s.setHidden(%s);\n", sb, nodeMap);
        tryBoolean(n, "update", "%s.setUpdatesEnabled(%s);\n", sb, nodeMap);
    }



    protected void Grid(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QGridLayout");
        setName(n, sb);
        if(!(prop = Utils.check("tool-button-style", nodeMap)).isEmpty()){
            switch(prop){
                case "bottom-right": value = "BottomRightCorner"; break;
                case "bottom-left": value = "BottomLeftCorner"; break;
                case "top-right": value = "TopRightCorner"; break;
                case "top-left": value = "TopLeftCorner"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setToolButtonStyle(Qt.ToolButtonStyle.%s);\n", sb);
        }
        tryValue(n, "spacing", "%s.setSpacing(%s);\n", sb, nodeMap);
        tryValue(n, "row-spacing", "%s.setVerticalSpacing(%s);\n", sb, nodeMap);
        tryValue(n, "column-spacing", "%s.setHorizontalSpacing(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QGridLayout", style);
    }

    protected void Item(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap) {
        Style style = new Style(n, "QListWidgetItem");
        setName(n, sb);
        boolean hasFlags = false;
        if(!(prop = Utils.check("selectable", nodeMap)).isEmpty()) {
            sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsSelectable", n));
            hasFlags = true;
        }
        if(!(prop = Utils.check("editable", nodeMap)).isEmpty()) {
            if(!hasFlags) sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsEditable", n));
            else sb.append(", Qt.ItemFlag.ItemIsEditable");
            hasFlags = true;
        }
        if(!(prop = Utils.check("draggable", nodeMap)).isEmpty()) {
            if(!hasFlags) sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsDragEnabled", n));
            else sb.append(", Qt.ItemFlag.ItemIsEditable");
            hasFlags = true;
        }
        if(!(prop = Utils.check("droppable", nodeMap)).isEmpty()) {
            if(!hasFlags) sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsDropEnabled", n));
            else sb.append(", Qt.ItemFlag.ItemIsDropEnabled");
            hasFlags = true;
        }
        if(!(prop = Utils.check("checkable", nodeMap)).isEmpty()) {
            if(!hasFlags) sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsCheckable", n));
            else sb.append(", Qt.ItemFlag.ItemIsCheckable");
            hasFlags = true;
        }
        if(!(prop = Utils.check("checked", nodeMap)).isEmpty()) {
            if(!hasFlags) sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsEnabled", n));
            else sb.append(", Qt.ItemFlag.ItemIsEnabled");
            hasFlags = true;
        }
        if(!(prop = Utils.check("tri-state", nodeMap)).isEmpty()) {
            if(!hasFlags) sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsTristate", n));
            else sb.append(", Qt.ItemFlag.ItemIsTristate");
            hasFlags = true;
        }
        if(hasFlags) sb.append("\");\n");
        if (!(prop = Utils.check("align", nodeMap)).isEmpty()){
            switch (prop) {
                case "left": value = "1"; break;
                case "right": value = "2"; break;
                case "horizontal-center": value = "4"; break;
                case "justify": value = "8"; break;
                case "absolute": value = "10"; break;
                case "top": value = "20"; break;
                case "bottom": value = "40"; break;
                case "vertical-center": value = "80"; break;
                case "center": value = "84"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setTextAlignment(%s);\n",sb);
        }
        tryCheck(n, "status-tip", "%s.setStatusTip(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "text", "%s.setText(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "icon", "%s.setIcon(new QIcon(\"%s\"));\n", sb, nodeMap);
        tryCheck(n, "tool-tip", "%s.setToolTip(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "what-is-this", "%s.setWhatsThis(\"%s\");\n", sb, nodeMap);
        tryBoolean(n, "hidden", "%s.setHidden(%s);\n", sb, nodeMap);
        tryBoolean(n, "selected", "%s.setSelected(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QGridLayout", style);
    }

    protected void Action(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
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
                case "widget-children": value = "WidgetWithChildrenShortcut"; break;
                case "window": value = "WindowShortcut"; break;
                case "application": value = "ApplicationShortcut"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setShortcutContext(Qt.ShortcutContext.%s);\n", sb);
        }
        tryBoolean(n, "repeatable", "%s.setAutoRepeat(%s);\n", sb, nodeMap);
        tryBoolean(n, "checkable", "%s.setCheckable(%s);\n", sb, nodeMap);
        tryBoolean(n, "icon-visible", "%s.setIconVisibleMenu(%s);\n", sb, nodeMap);
        tryBoolean(n, "is-separator", "%s.setSeparator(%s);\n", sb, nodeMap);
        tryCheck(n, "shortcut", "%s.setShortcut(new QKeySequence(tr(\"%s\")));\n", sb, nodeMap);
        tryCheck(n, "status-tip", "%s.setStatusTip(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "tool-tip", "%s.setToolTip(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "what-is-this", "%s.setWhatsThis(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "icon", "%s.setIcon(new QIcon(\"%s\"));\n", sb, nodeMap);
        tryCheck(n, "icon-Text", "%s.setIconText(\"%s\");\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QAction", style);
    }

    private void setStyle(Style style, NamedNodeMap nodeMap) {
        for (Map.Entry<String, String> entry : Styles.entrySet())
            if (!(value = Utils.check(entry.getKey(), nodeMap)).isEmpty()) style.addAttribute(entry.getValue(), value);
    }
    private void TryList(String n, String prop, String command, Map<String, String> map, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(p = Utils.check(prop, nodeMap)).isEmpty())  Utils.tryEmptyAppend(n, map.get(p), command, sb);
    }
    private void tryBoolean(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        if (Utils.tryBoolean(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
    }
    private void tryValue(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        if (Utils.tryInteger(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
        else if(Utils.tryDouble(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
    }
    private void setName(String n, StringBuilder sb){
        if(!Utils.components.containsKey(n.replaceAll("\\d", ""))) sb.append(String.format("%s.setObjectName(\"%s\");\n", n, n));
    }
    private void tryCheck(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(p = Utils.check(prop, nodeMap)).isEmpty()) sb.append(String.format(command, name, p));
    }
    private void tryCheck(String name, String prop, String command, String replacement, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(p = Utils.check(prop, nodeMap)).isEmpty()) sb.append(String.format(command, name, p));
        else sb.append(String.format(command, name, replacement));
    }
    public void tryCapitalize(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if(!(p = Utils.check(prop, nodeMap)).isEmpty()) sb.append(String.format(command, name, Utils.capitalize(p)));
    }
    public String trySetName(String prop){
        List<String> comps = ComponentParser.getComponents();
        if(!comps.contains(prop)) comps.add(prop);
        else{
            int count = 0;
            for(String comp : comps) if(comp.startsWith(prop)) count++;
            prop += count;
            comps.add(prop);
        }
        return prop;
    }
}