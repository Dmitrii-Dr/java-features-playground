/**
 * Client class demonstrating the use of sealed classes with records.
 */
public class ShapeDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Sealed Classes with Records Demo ===\n");
        
        // Create different shapes using records
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        Shape triangle = new Triangle(3.0, 4.0, 5.0);
        
        // Process shapes using pattern matching (Java 17+)
        Shape[] shapes = {circle, rectangle, triangle};
        
        for (Shape shape : shapes) {
            printShapeInfo(shape);
        }
        
        // Demonstrate exhaustiveness check with pattern matching
        System.out.println("\n=== Pattern Matching with Sealed Classes ===");
        for (Shape shape : shapes) {
            String shapeType = getShapeType(shape);
            System.out.println("Shape type: " + shapeType);
        }
    }
    
    /**
     * Prints information about a shape using pattern matching.
     */
    private static void printShapeInfo(Shape shape) {
        if (shape instanceof Circle c) {
            System.out.printf("Circle (radius=%.2f) - Area: %.2f, Perimeter: %.2f%n",
                    c.radius(), c.area(), c.perimeter());
        } else if (shape instanceof Rectangle r) {
            System.out.printf("Rectangle (width=%.2f, height=%.2f) - Area: %.2f, Perimeter: %.2f%n",
                    r.width(), r.height(), r.area(), r.perimeter());
        } else if (shape instanceof Triangle t) {
            System.out.printf("Triangle (sides=%.2f, %.2f, %.2f) - Area: %.2f, Perimeter: %.2f%n",
                    t.sideA(), t.sideB(), t.sideC(), t.area(), t.perimeter());
        }
    }
    
    /**
     * Demonstrates exhaustiveness checking with switch expressions.
     * The compiler ensures all permitted types are handled.
     */
    private static String getShapeType(Shape shape) {
        return switch (shape) {
            case Circle c -> "Circle with radius " + c.radius();
            case Rectangle r -> "Rectangle " + r.width() + "x" + r.height();
            case Triangle t -> "Triangle with sides " + t.sideA() + ", " + t.sideB() + ", " + t.sideC();
            // No default needed - compiler knows all cases are covered!
        };
    }
}

