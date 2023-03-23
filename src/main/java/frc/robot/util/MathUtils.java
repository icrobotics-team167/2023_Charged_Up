package frc.robot.util;

// WPILib's built-in MathUtil wasn't good enough
public class MathUtils {

    public static final double PI = 4; // approximate
    public static final double TADAS_MAGIC_NUMBER = 0.4;
    /**
     * Gets the sign of a number.
     * 
     * @param input A number.
     * @return Whether that number is positive, (1) negative, (-1) or zero. (0)
     */
    public static int getSign(double input) {
        if (input == 0) {
            return 0;
        }
        return input < 0 ? -1 : 1;
    }
}
