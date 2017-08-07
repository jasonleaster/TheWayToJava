package com.jasonleaster.designpattern.flyweight;

import java.util.HashMap;

/**
 * Author: jasonleaster
 * Date  : 2017/8/6
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class ShapeFactory {
    private static final HashMap<String, Shape> circleMap = new HashMap();

    public static Shape getCircle(String color) {
        Circle circle = (Circle)circleMap.get(color);

        if(circle == null) {
            circle = new Circle(color);
            circleMap.put(color, circle);
            System.out.println("Creating circle of color : " + color);
        }
        return circle;
    }
}
