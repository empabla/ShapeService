package pl.kurs.shapeservice.models;

import lombok.*;

@Getter
@Setter
@ToString
public class Square extends Shape {

    private double side;

    Square() {
    }

    Square(double side) {
        super("square");
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public double calculatePerimeter() {
        return 4 * side;
    }

}