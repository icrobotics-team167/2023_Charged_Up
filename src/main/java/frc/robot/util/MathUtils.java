package frc.robot.util;

// WPILib's built-in mathutils wasn't good enough
public class MathUtils {

    public static int getSign(double input) {
        if (input == 0) {
            return 0;
        }
        return input < 0 ? -1 : 1;
    }
}
