package pl.kurs.shapeservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.kurs.shapeservice.exceptions.ShapeNotFoundException;
import pl.kurs.shapeservice.models.Shape;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ShapeService {

    private ObjectMapper objectMapper;

    public ShapeService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Shape findShapeWithLargestArea(List<Shape> shapes) throws ShapeNotFoundException {
        return Optional.ofNullable(shapes)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparingDouble(Shape::calculateArea))
                .orElseThrow(() -> new ShapeNotFoundException("No shape found"));
    }

    public Shape findShapeWithLargestPerimeterByType(List<Shape> shapes, Class<? extends Shape> shapeType)
            throws ShapeNotFoundException {
        return Optional.ofNullable(shapes)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .filter(x -> x.getClass() == shapeType)
                .max(Comparator.comparingDouble(Shape::calculatePerimeter))
                .orElseThrow(() -> new ShapeNotFoundException("No shape found"));
    }

    public void exportShapesToJson(List<Shape> shapes, String filePath) {
        try (
                FileWriter fileWriter = new FileWriter(filePath)
        ) {
            objectMapper.writeValue(fileWriter, shapes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Shape> importFiguresFromJson(String filePath) {
        try (
                FileReader fileReader = new FileReader(filePath)
        ) {
            return objectMapper.readValue(fileReader, new TypeReference<List<Shape>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
