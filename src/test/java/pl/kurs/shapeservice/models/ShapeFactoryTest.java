package pl.kurs.shapeservice.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class ShapeFactoryTest {

    private ShapeFactory shapeFactory;

    @Before
    public void init() {
        shapeFactory = new ShapeFactory();
    }

    @Test
    public void shouldCacheCreatedSquareAndReturnItsReferenceWhenCreatingIdenticalOne() {
        //given
        double side = 5.0;

        Square square = shapeFactory.createSquare(side);
        Map<String, Shape> shapeCache = shapeFactory.getShapeCache();

        //when
        Square cachedSquare = shapeFactory.createSquare(side);

        //then
        assertTrue(shapeCache.containsValue(square));
        assertSame(square, cachedSquare);
    }

    @Test
    public void shouldCacheCreatedCircleAndReturnItsReferenceWhenCreatingIdenticalOne() {
        //given
        double radius = 3.5;

        Circle circle = shapeFactory.createCircle(radius);
        Map<String, Shape> shapeCache = shapeFactory.getShapeCache();

        //when
        Circle cachedCircle = shapeFactory.createCircle(radius);

        //then
        assertTrue(shapeCache.containsValue(circle));
        assertSame(circle, cachedCircle);
    }

    @Test
    public void shouldCacheCreatedRectangleAndReturnTheSameReferenceWhenCreatingIdenticalOne() {
        //given
        double width = 4.0;
        double height = 6.0;

        Rectangle rectangle = shapeFactory.createRectangle(width, height);
        Map<String, Shape> shapeCache = shapeFactory.getShapeCache();

        //when
        Rectangle cachedRectangle = shapeFactory.createRectangle(width, height);

        //then
        assertTrue(shapeCache.containsValue(rectangle));
        assertSame(rectangle, cachedRectangle);
    }

}