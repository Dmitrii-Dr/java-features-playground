package patternmatching;

/**
 * Demonstration of pattern matching with switch expressions (Java 17+).
 * Shows how to use pattern matching to handle different object types in a switch statement.
 */
public class PatternMatchingSwitchDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Pattern Matching with Switch Demo ===\n");
        
        // Test with various object types
        processObjectWithSwitch("Hello World");
        processObjectWithSwitch("Hi");
        processObjectWithSwitch(42);
        processObjectWithSwitch(100);
        processObjectWithSwitch(null);
        processObjectWithSwitch(3.14);
        processObjectWithSwitch(true);
        processObjectWithSwitch(new int[]{1, 2, 3});
        
        // Deconstruction examples
        System.out.println("\n=== Deconstruction Patterns Demo ===\n");
        demonstrateDeconstruction();
        
        // Guard patterns examples
        System.out.println("\n=== Guard Patterns (when clauses) Demo ===\n");
        processStringWithGuard("Hello World");
        processStringWithGuard("Hi");
        processStringWithGuard("Programming");
        processStringWithGuard("Java");
        processStringWithGuard(null);
        processStringWithGuard(123);
        
        // More guard examples
        System.out.println("\n--- More Guard Pattern Examples ---");
        processWithGuards(42);
        processWithGuards(150);
        processWithGuards(-5);
        processWithGuards(0);
    }
    
    /**
     * Demonstrates pattern matching with switch expressions.
     * This feature allows type checking and variable binding in switch cases.
     */
    public static void processObjectWithSwitch(Object obj) {
        switch (obj) {
            // If obj is a String, create pattern variable 's'
            case String s -> {
                if (s.length() > 5) {
                    System.out.println("Long string: " + s.toUpperCase());
                } else {
                    System.out.println("Short string: " + s);
                }
            }
            
            // If obj is an Integer, create pattern variable 'i'
            case Integer i -> System.out.println("Integer value: " + i);
            
            // A clean way to handle nulls
            case null -> System.out.println("Object is null");
            
            // The default case for all other types
            default -> System.out.println("Unknown type: " + obj.getClass().getSimpleName());
        }
    }
    
    /**
     * Alternative example showing switch expression that returns a value.
     */
    public static String describeObject(Object obj) {
        return switch (obj) {
            case String s when s.length() > 10 -> "Long string with " + s.length() + " characters";
            case String s -> "String: " + s;
            case Integer i when i > 100 -> "Large integer: " + i;
            case Integer i -> "Integer: " + i;
            case null -> "Null object";
            default -> "Other: " + obj.getClass().getSimpleName();
        };
    }
    
    /**
     * Example showing multiple type patterns in a single case.
     */
    public static void processNumber(Object obj) {
        switch (obj) {
            case Byte b -> System.out.println("Byte: " + b);
            case Short s -> System.out.println("Short: " + s);
            case Integer i -> System.out.println("Integer: " + i);
            case Long l -> System.out.println("Long: " + l);
            case Float f -> System.out.println("Float: " + f);
            case Double d -> System.out.println("Double: " + d);
            case null -> System.out.println("Null number");
            default -> System.out.println("Not a number type");
        }
    }
    
    /**
     * Demonstrates deconstruction patterns (Java 19+).
     * Deconstruction allows extracting record components directly in switch cases.
     */
    public static void demonstrateDeconstruction() {
        // Example 1: Simple deconstruction of Point record
        Object point = new Point(10, 20);
        processShape(point);
        
        // Example 2: Nested deconstruction - Circle contains Point
        Object circle = new Circle(new Point(5, 5), 15.0);
        processShape(circle);
        
        // Example 3: Nested deconstruction - Rectangle contains two Points
        Object rectangle = new Rectangle(new Point(0, 0), new Point(10, 10));
        processShape(rectangle);
        
        // Example 4: Deconstruction with Person record
        Object person = new Person("Alice", 30);
        processShape(person);
        
        // Example 5: Deconstruction in switch expression
        System.out.println("\n--- Deconstruction with Switch Expression ---");
        System.out.println("Point area: " + calculateArea(new Point(3, 4)));
        System.out.println("Circle area: " + calculateArea(new Circle(new Point(0, 0), 5.0)));
        System.out.println("Rectangle area: " + calculateArea(new Rectangle(new Point(0, 0), new Point(5, 10))));
    }
    
    /**
     * Demonstrates deconstruction patterns in switch statements.
     * Extracts record components directly in the case labels.
     */
    public static void processShape(Object shape) {
        switch (shape) {
            // Deconstruct Point record: extract x and y components
            case Point(int x, int y) -> {
                System.out.println("Point: x=" + x + ", y=" + y);
            }
            
            // Nested deconstruction: extract center (Point) and radius from Circle
            case Circle(Point(int cx, int cy), double radius) -> {
                System.out.println("Circle: center=(" + cx + "," + cy + "), radius=" + radius);
            }
            
            // Nested deconstruction: extract two Points from Rectangle
            case Rectangle(Point(int x1, int y1), Point(int x2, int y2)) -> {
                int width = Math.abs(x2 - x1);
                int height = Math.abs(y2 - y1);
                System.out.println("Rectangle: topLeft=(" + x1 + "," + y1 + 
                                 "), bottomRight=(" + x2 + "," + y2 + 
                                 "), size=" + width + "x" + height);
            }
            
            // Deconstruct Person record
            case Person(String name, int age) -> {
                System.out.println("Person: " + name + ", age " + age);
            }
            
            case null -> System.out.println("Shape is null");
            default -> System.out.println("Unknown shape: " + shape.getClass().getSimpleName());
        }
    }
    
    /**
     * Demonstrates deconstruction patterns in switch expressions.
     * Returns a value based on deconstructed record components.
     */
    public static double calculateArea(Object shape) {
        return switch (shape) {
            // Points don't have area, but we can use deconstruction to access components
            // Using _ for intentionally unused variables
            case Point(int _, int _) -> 0.0;
            
            // Deconstruct Circle and calculate area using radius
            // center is unused, so we use _ to indicate that
            case Circle(Point _, double radius) -> Math.PI * radius * radius;
            
            // Deconstruct Rectangle and calculate area from points
            case Rectangle(Point(int x1, int y1), Point(int x2, int y2)) -> {
                int width = Math.abs(x2 - x1);
                int height = Math.abs(y2 - y1);
                yield width * height;
            }
            
            case null -> 0.0;
            default -> 0.0;
        };
    }
    
    /**
     * Example showing deconstruction with guards (when clauses).
     * Filters based on deconstructed values.
     */
    public static String describePerson(Object obj) {
        return switch (obj) {
            case Person(String name, int age) when age < 18 -> 
                name + " is a minor (age " + age + ")";
            case Person(String name, int age) when age >= 65 -> 
                name + " is a senior (age " + age + ")";
            case Person(String name, int age) -> 
                name + " is an adult (age " + age + ")";
            case null -> "No person provided";
            default -> "Not a person";
        };
    }
    
    /**
     * Demonstrates guard patterns with arbitrary boolean conditions using 'when' clauses.
     * Guards allow you to add additional conditions to pattern matches.
     */
    public static void processStringWithGuard(Object obj) {
        switch (obj) {
            // This case only matches if obj is a String AND its length is > 5
            case String s when s.length() > 5 -> System.out.println("Long string: " + s);
            
            // This case matches any other string
            case String s -> System.out.println("Short string: " + s);
            
            case null -> System.out.println("Object is null");
            
            default -> System.out.println("Not a string: " + obj.getClass().getSimpleName());
        }
    }
    
    /**
     * More examples of guard patterns with different conditions.
     */
    public static void processWithGuards(Object obj) {
        switch (obj) {
            // Multiple guards with different conditions
            case Integer i when i > 100 -> 
                System.out.println("Large positive integer: " + i);
            case Integer i when i < 0 -> 
                System.out.println("Negative integer: " + i);
            case Integer i when i == 0 -> 
                System.out.println("Zero");
            case Integer i when i % 2 == 0 -> 
                System.out.println("Even positive integer: " + i);
            case Integer i -> 
                System.out.println("Odd positive integer (1-100): " + i);
            
            // String guards with multiple conditions
            case String s when s.length() > 10 && s.contains("Java") -> 
                System.out.println("Long Java-related string: " + s);
            case String s when s.startsWith("A") -> 
                System.out.println("String starting with 'A': " + s);
            
            case null -> System.out.println("Null value");
            default -> System.out.println("Other type: " + obj.getClass().getSimpleName());
        }
    }
    
    /**
     * Example showing guard patterns with deconstruction.
     * Combines record deconstruction with boolean conditions.
     */
    public static void processShapeWithGuards(Object shape) {
        switch (shape) {
            // Guard on deconstructed Point values
            case Point(int x, int y) when x > 0 && y > 0 -> 
                System.out.println("Point in first quadrant: (" + x + ", " + y + ")");
            case Point(int x, int y) when x == 0 && y == 0 -> 
                System.out.println("Origin point");
            case Point(int x, int y) -> 
                System.out.println("Point in other quadrant: (" + x + ", " + y + ")");
            
            // Guard on deconstructed Circle radius
            case Circle(Point _, double radius) when radius > 10 -> 
                System.out.println("Large circle with radius: " + radius);
            case Circle(Point _, double radius) when radius <= 1 -> 
                System.out.println("Small circle with radius: " + radius);
            case Circle(Point _, double radius) -> 
                System.out.println("Medium circle with radius: " + radius);
            
            // Guard on deconstructed Person age
            case Person(String name, int age) when age >= 18 && age < 65 -> 
                System.out.println("Working-age person: " + name + " (age " + age + ")");
            case Person(String name, int age) -> 
                System.out.println("Person: " + name + " (age " + age + ")");
            
            case null -> System.out.println("Shape is null");
            default -> System.out.println("Unknown shape");
        }
    }
}

