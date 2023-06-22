package pl.kurs.shapeservice.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.kurs.shapeservice.exceptions.ShapeNotFoundException;
import pl.kurs.shapeservice.models.*;
import pl.kurs.shapeservice.services.ShapeService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        ShapeFactory shapeFactory = new ShapeFactory();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ShapeService shapeService = new ShapeService(objectMapper);

        Square sq1 = shapeFactory.createSquare(10);
        Square sq2 = shapeFactory.createSquare(10);
        System.out.println("(sq1 == sq2) = " + (sq1 == sq2));

        Rectangle r1 = shapeFactory.createRectangle(8,5);
        Rectangle r2 = shapeFactory.createRectangle(6,5);
        Circle c1 = shapeFactory.createCircle(5);

        List<Shape> shapeList = List.of(sq1, r1, r2, c1);
        shapeList.forEach(System.out::println);

        try {
            System.out.println("shapeWithLargestArea = " + shapeService.findShapeWithLargestArea(shapeList));
            System.out.println("shapeWithLargestPerimeterByType = "
                    + shapeService.findShapeWithLargestPerimeterByType(shapeList, Rectangle.class));
        } catch (ShapeNotFoundException e) {
            e.printStackTrace();
        }

        shapeService.exportShapesToJson(shapeList, "src/main/resources/shapelist.json");

        List<Shape> importedShapeList = shapeService.importFiguresFromJson("src/main/resources/shapelist.json");
        importedShapeList.forEach(System.out::println);

        System.out.println("(shapeList.equals(importedShapeList)) = " + (shapeList.equals(importedShapeList)));

    }
}
