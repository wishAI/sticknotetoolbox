package main.gui;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import main.model.notes.StickyNote;

import java.util.Objects;


public class ColorManager {

    private ColorManager() {
    }

//    public static Color getColor(String color) {
//        if (color.equals("grey"))
////            return Objects.requireNonNull(makeRGBColor(color)).deriveColor(0, 0, 1.2, 0);
//            return Objects.requireNonNull(makeRGBColor(color)).brighter();
//        else
//            return Objects.requireNonNull(makeRGBColor(color)).brighter();
//    }

    public static void setColorToNode(Node node, Color color) {
        node.setStyle("-fx-background-color: #" + toRGBACode(color) + ";");
    }

    public static void setColorToNode(Node node, String color) {
        if (color.equals("grey")) {
            node.setStyle("-fx-background-color: #" + toRGBCode(Objects.requireNonNull(makeRGBColor(color)).deriveColor(0, 0, 1.2, 0)) + ";");
        } else {
            node.setStyle("-fx-background-color: #" + toRGBCode(Objects.requireNonNull(makeRGBColor(color)).brighter()) + ";");
        }
    }

    public static void setLightColorToNode(Node node, String color) {
        node.setStyle("-fx-background-color: #" + toRGBCode(Objects.requireNonNull(makeRGBColor(color)).brighter().deriveColor(0, 0.6, 1, 0)) + ";");
    }

    public static void setHoverColorToNode(Node node, String color, Color colorHover) {
        setColorToNode(node, color);
        node.setOnMouseEntered(event -> {
            setColorToNode(node, colorHover);
        });
        node.setOnMouseExited(event -> {
            setColorToNode(node, color);
        });
    }

    public static void setHoverColorToNode(Node node, Color color, Color colorHover) {
        setColorToNode(node, color);
        node.setOnMouseEntered(event -> {
            setColorToNode(node, colorHover);
        });
        node.setOnMouseExited(event -> {
            setColorToNode(node, color);
        });
    }

    private static Color makeRGBColor(String color) {
        Color[] rgbColors = {
                new Color(255 / 255.0, 230 / 255.0, 105 / 255.0, 1),
                new Color(255 / 255.0, 99 / 255.0, 105 / 255.0, 1),
                new Color(176 / 255.0, 224 / 255.0, 99 / 255.0, 1),
                new Color(125 / 255.0, 230 / 255.0, 194 / 255.0, 1),
                new Color(125 / 255.0, 209 / 255.0, 240 / 255.0, 1),
                new Color(194 / 255.0, 181 / 255.0, 250 / 255.0, 1),
                new Color(170 / 255.0, 170 / 255.0, 170 / 255.0, 1)
        };

        for (int i = 0; i < StickyNote.COLORS.length; i ++) {
            if (StickyNote.COLORS[i].equals(color)) {
                return rgbColors[i];
            }
        }

        return null;
    }

    private static String toRGBCode(Color c) {
        return String.format(
                "%02X%02X%02X",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255)
        );
    }

    private static String toRGBACode(Color c) {
        return String.format(
                "%02X%02X%02X%02X",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255),
                (int) (c.getOpacity() * 255)
        );
    }

}