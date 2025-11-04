/**
 * Sealed interface for geometric shapes.
 * Only Circle, Rectangle, and Triangle are permitted to implement this interface.
 */
public sealed interface Shape 
    permits Circle, Rectangle, Triangle {
    
    /**
     * Calculates the area of the shape.
     * @return the area of the shape
     */
    double area();
    
    /**
     * Calculates the perimeter of the shape.
     * @return the perimeter of the shape
     */
    double perimeter();
}

