package pl.kurs.shapeservice.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import pl.kurs.shapeservice.exceptions.ShapeNotFoundException;
import pl.kurs.shapeservice.models.Circle;
import pl.kurs.shapeservice.models.Rectangle;
import pl.kurs.shapeservice.models.Shape;
import pl.kurs.shapeservice.models.ShapeFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShapeServiceTest {

    ShapeFactory shapeFactory;
    ObjectMapper objectMapper;
    ShapeService shapeService;
    List<Shape> shapeListForTest;

    @Before
    public void init() {
        shapeFactory = new ShapeFactory();

        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        shapeService = new ShapeService(objectMapper);

        shapeListForTest = new ArrayList<>();
        shapeListForTest.add(shapeFactory.createSquare(2));
        shapeListForTest.add(shapeFactory.createSquare(4));
        shapeListForTest.add(shapeFactory.createRectangle(4, 1));
        shapeListForTest.add(shapeFactory.createRectangle(4, 2));
        shapeListForTest.add(shapeFactory.createRectangle(2, 1));
        shapeListForTest.add(null);
    }

    @Test
    public void shouldReturnSquareWithSide4() throws ShapeNotFoundException {
        //when
        Shape shapeWithLargestArea = shapeService.findShapeWithLargestArea(shapeListForTest);

        //then
        assertTrue(shapeListForTest.get(1) == shapeWithLargestArea);
    }

    @Test
    public void shouldReturnRectangleWithWidth4AndHeight2() throws ShapeNotFoundException {
        //when
        Shape shapeWithLargestPerimeterByType =
                shapeService.findShapeWithLargestPerimeterByType(shapeListForTest, Rectangle.class);

        //then
        assertTrue(shapeListForTest.get(3) == shapeWithLargestPerimeterByType);
    }

    @Test
    public void shouldThrowShapeNotFoundExceptionWhenListIsNullForLargestAreaMethod() {
        //given
        List<Shape> shapeListForTest = null;

        //when
        Exception e = assertThrows(ShapeNotFoundException.class,
                () -> shapeService.findShapeWithLargestArea(shapeListForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(ShapeNotFoundException.class);
        sa.assertThat(e).hasMessage("No shape found");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldThrowShapeNotFoundExceptionWhenListIsEmptyForLargestAreaMethod() {
        //given
        List<Shape> shapeListForTest = new ArrayList<>();

        //when
        Exception e = assertThrows(ShapeNotFoundException.class,
                () -> shapeService.findShapeWithLargestArea(shapeListForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(ShapeNotFoundException.class);
        sa.assertThat(e).hasMessage("No shape found");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldThrowShapeNotFoundExceptionWhenListIsNullForLargestPerimeterMethod() {
        //given
        List<Shape> shapeListForTest = null;
        Class<? extends Shape> shapeTypeForTest = Rectangle.class;

        //when
        Exception e = assertThrows(ShapeNotFoundException.class,
                () -> shapeService.findShapeWithLargestPerimeterByType(shapeListForTest, shapeTypeForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(ShapeNotFoundException.class);
        sa.assertThat(e).hasMessage("No shape found");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldThrowShapeNotFoundExceptionWhenListIsEmptyForLargestPerimeterMethod() {
        //given
        List<Shape> shapeListForTest = new ArrayList<>();
        Class<? extends Shape> shapeTypeForTest = Rectangle.class;

        //when
        Exception e = assertThrows(ShapeNotFoundException.class,
                () -> shapeService.findShapeWithLargestPerimeterByType(shapeListForTest, shapeTypeForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(ShapeNotFoundException.class);
        sa.assertThat(e).hasMessage("No shape found");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldThrowShapeNotFoundExceptionWhenNoCircleTypeOnListForLargestPerimeterMethod() {
        //given
        Class<? extends Shape> shapeTypeForTest = Circle.class;

        //when
        Exception e = assertThrows(ShapeNotFoundException.class,
                () -> shapeService.findShapeWithLargestPerimeterByType(shapeListForTest, shapeTypeForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(ShapeNotFoundException.class);
        sa.assertThat(e).hasMessage("No shape found");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldWriteShapesToFileWhenExportToJson() {
        //given
        String testFilePath = "test.json";

        //when
        shapeService.exportShapesToJson(shapeListForTest, testFilePath);

        //then
        File testFile = new File(testFilePath);
        assertTrue(testFile.exists());

        //cleanup
        testFile.delete();
    }

    @Test
    public void shouldReadShapesFromFileWhenImportFromJson() {
        //given
        String testFilePath = "test.json";
        shapeService.exportShapesToJson(shapeListForTest, testFilePath);

        //when
        List<Shape> actualShapes = shapeService.importFiguresFromJson(testFilePath);

        //then
        assertTrue(shapeListForTest.equals(actualShapes));

        //cleanup
        File file = new File(testFilePath);
        file.delete();
    }

}