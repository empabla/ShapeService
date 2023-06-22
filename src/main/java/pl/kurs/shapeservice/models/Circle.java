package pl.kurs.shapeservice.models;

import lombok.*;

@Getter
@Setter
@ToString
public class Circle extends Shape {

    private double radius;

    Circle() {
    }

    Circle(double radius) {
        super("circle");
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

}
