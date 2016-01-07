package QtComponents;

import com.trolltech.qt.gui.QWidget;

/**
 * Created by Caleb Bain on 1/7/2016.
 */
public class Window extends QWidget{
    private int Width = 400;
    private int Height = 200;
    private String Title = "JAML Applicaiton";

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        this.setWidth(width);
        Width = width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        this.setHeight(height);
        Height = height;
    }

    /**
     *
     * @return first element of the array is the width and the second is the height
     */
    public int[] getSize() {
        int[] result = {Width, Height};
        return result;
    }

    public void setSize(int width, int height) {
        this.setSize(width, height);
        Width = width;
        Height = height;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.setTitle(title);
        Title = title;
    }
}
