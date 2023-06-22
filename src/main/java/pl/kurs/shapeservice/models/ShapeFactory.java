package pl.kurs.shapeservice.models;

import java.util.HashMap;
import java.util.Map;

public class ShapeFactory {

    private Map<String, Shape> shapeCache;

    public ShapeFactory() {
        shapeCache = new HashMap<>();
    }

    public Map<String, Shape> getShapeCache() {
        return shapeCache;
    }

    public Square createSquare(double side) {
        String key = "square_" + side;
        if (shapeCache.containsKey(key)) {
            return (Square) shapeCache.get(key);
        } else {
            Square square = new Square(side);
            shapeCache.put(key, square);
            return square;
        }
    }

    public Circle createCircle(double radius) {
        String key = "circle_" + radius;
        if (shapeCache.containsKey(key)) {
            return (Circle) shapeCache.get(key);
        } else {
            Circle circle = new Circle(radius);
            shapeCache.put(key, circle);
            return circle;
        }
    }

    public Rectangle createRectangle(double width, double height) {
        String key = "rectangle_" + width + "_" + height;
        if (shapeCache.containsKey(key)) {
            return (Rectangle) shapeCache.get(key);
        } else {
            Rectangle rectangle = new Rectangle(width, height);
            shapeCache.put(key, rectangle);
            return rectangle;
        }
    }

}