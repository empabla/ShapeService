package pl.kurs.shapeservice.models;

import lombok.*;

@Getter
@Setter
@ToString
public class Rectangle extends Shape {

    private double width;
    private double height;

    Rectangle() {
    }

    Rectangle(double width, double height) {
        super("rectangle");
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }

}
