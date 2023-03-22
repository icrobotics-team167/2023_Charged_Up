package frc.robot;

import frc.robot.controls.controllers.ControllerType;

public class Config {

    public static final class Settings {

        // Control Scheme
        public static final boolean TANK_DRIVE = false;

        public static final boolean EXPONENTIAL_JOYSTICKS = false;
        public static final double JOYSTICKS_EXPONENT = 2;

        // Controllers
        public static final ControllerType PRIMARY_CONTROLLER_TYPE = ControllerType.JOYSTICK;
        public static final ControllerType SECONDARY_CONTROLLER_TYPE = ControllerType.JOYSTICK;
        public static final ControllerType TERTIARY_CONTROLLER_TYPE = ControllerType.JOYSTICK;
        public static final ControllerType QUATERNARY_CONTROLLER_TYPE = ControllerType.JOYSTICK;


        // Dead zones
        public static final boolean PRIMARY_DEADZONE_ENABLED = true;
        public static final boolean SECONDARY_DEADZONE_ENABLED = true;

        // CPU period (seconds)
        public static final double CPU_PERIOD = 0.02;
    }

    public static final class Tolerances {

        // Threshold for a controller trigger to count as pressed (on XB controllers
        // only)
        public static final double XB_TRIGGER_PRESSED_THRESHOLD = 0.5;

        // Primary controller deadzone size
        public static final double PRIMARY_CONTROLLER_DEADZONE_SIZE = 0.125;

        // Secondary controller deadzone size
        public static final double SECONDARY_CONTROLLER_DEADZONE_SIZE = 0.175;

        // Tertiary controller deadzone size
        public static final double TERTIARY_CONTROLLER_DEADZONE_SIZE = 0.09;

        // Quaternary controller deadzone size
        public static final double QUATERNARY_CONTROLLER_DEADZONE_SIZE = 0.09;
    }

    public static final class Ports {

        // Controllers
        public static final int PRIMARY_CONTROLLER = 0;
        public static final int SECONDARY_CONTROLLER = 1; // if applicable
        public static final int TERTIARY_CONTROLLER = 2; // if applicable
        public static final int QUATERNARY_CONTROLLER = 3; // if applicable


        // main control system components
        // public static final int RoboRio = 0
        // public static final int PDP = 1
        // public static final int PH = 2

        // Spark tank motor controller ports
        public static final class SparkTank {
            // Pneumatics Control Hub CAN Address
            public static final int PH = 2;

            // Drivebase CAN bus Addresses
            public static final int LEFT_1 = 6;
            public static final int LEFT_2 = 7;
            public static final int LEFT_3 = 8;
            public static final int RIGHT_1 = 3;
            public static final int RIGHT_2 = 4;
            public static final int RIGHT_3 = 5;

            // Pneumatic Hub Ports
            // Drivebase PH Ports
            public static final int LOW_GEAR = 1; // shifter cylinder out = high gear
        }

        public static final class Arm {
            // Arm CAN bus Addresses
            // TEMPORARY VALUES
            public static final int EXTEND_RETRACT = 12;
            public static final int SWIVEL = 9;
            public static final int PIVOT_1 = 10;
            public static final int PIVOT_2 = 11;
            public static final int CLAW = 13;

            // public static final int CLOSE_CLAW = 2;

            // Digital Limit Switches
            // Temporary Values
            public static final int EXTEND_RETRACT_SWITCH = 0;
            public static final int SWIVEL_SWITCH = 1;
            public static final int PIVOT_SWITCH = 2;
        }

    }

}
