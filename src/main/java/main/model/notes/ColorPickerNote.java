package main.model.notes;

import javafx.scene.paint.Color;
import main.gui.notepane.content.ColorPickerPane;

import java.util.HashMap;
import java.util.Map;


@View(value = ColorPickerPane.class)
public class ColorPickerNote extends RecordableStickNote {

    private Color curColor;


    public ColorPickerNote() {
        super();
        curColor = new Color(0.5, 0.5, 0.5, 1);
    }

    public void setHue(double h) {
        curColor = Color.hsb(h, curColor.getSaturation(), curColor.getBrightness());
    }

    public void setSaturation(double s) {
        curColor = Color.hsb(curColor.getHue(), s, curColor.getBrightness());
    }

    public void setBrightness(double b) {
        curColor = Color.hsb(curColor.getHue(), curColor.getSaturation(), b);
    }

    public void setCurColor(Color color) {
        curColor = color;
    }

    public Color getCurColor() {
        return curColor;
    }

    public void setHexValue(String hex) {
        curColor = Color.web(hex);
    }

    public String getHexValue() {
        return toRGBCode();
    }

    public static boolean isHexCode(String str) {
        if (!(str.length() == 7 || str.length() == 9)) {
            return false;
        }

        if (!(str.charAt(0) == '#')) {
            return false;
        }

        for (int i = 1; i < str.length(); i ++) {
            char c = str.charAt(i);
            if (!((c <= '9' && c >= '1') || c == '0' || (c <= 'F' && c >= 'A') || (c <= 'f' && c >= 'a'))) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void record(Map<String, Object> item) {
        item.put("color", curColor);
        item.put("hex", getHexValue());
    }

    private String toRGBCode() {
        return String.format(
                "#%02X%02X%02X",
                (int) (curColor.getRed() * 255),
                (int) (curColor.getGreen() * 255),
                (int) (curColor.getBlue() * 255)
        );
    }

}
