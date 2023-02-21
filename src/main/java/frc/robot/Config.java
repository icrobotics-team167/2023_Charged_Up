package frc.robot;

import frc.robot.controls.controllers.ControllerType;

public class Config {

    public static final class Settings {

        // Control Scheme
        public static final boolean TANK_DRIVE = false;

        // Controllers
        public static final ControllerType PRIMARY_CONTROLLER_TYPE = ControllerType.XB;
        public static final ControllerType SECONDARY_CONTROLLER_TYPE = ControllerType.XB;

        // Dead zones
        public static final boolean TANK_DEAD_ZONE_ENABLED = true;
        public static final boolean INTAKE_DEAD_ZONE_ENABLED = true;
        public static final boolean INDEXER_DEAD_ZONE_ENABLED = true;

        // CPU period (seconds)
        public static final double CPU_PERIOD = 0.02;

        // Shooting RPM
        public static final int SHOOTING_RPM = 3200; // 2000 min - 5000 max
        public static final int LOW_GOAL_RPM = 1500;

    }

    public static final class Tolerances {

        // Threshold for a controller trigger to count as pressed (on XB controllers
        // only)
        public static final double TRIGGER_PRESSED_THRESHOLD = 0.2;

        // Primary controller deadzone size
        public static final double PRIMARY_CONTROLLER_DEADZONE_SIZE = 0.1;

        public static final double SECONDARY_CONTROLLER_DEADZONE_SIZE = 0.3;

        // Intake manual speed dead zone size
        public static final double INTAKE_DEAD_ZONE_SIZE = 0.2;

        // Indexer manual speed dead zone size
        public static final double INDEXER_DEAD_ZONE_SIZE = 0.2;

    }

    public static final class Ports {

        // Controllers
        public static final int PRIMARY_CONTROLLER = 0;
        public static final int SECONDARY_CONTROLLER = 1; // if applicable

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

            // Pneumatic Hub Ports
            // Arm PH Ports
            // TEMPORARY VALUES
            public static final int OPEN_CLAW = 0;
            public static final int CLOSE_CLAW = 2;
            
            // Digital Limit Switches
            // Temporary Values
            public static final int EXTEND_RETRACT_SWITCH = 0;
            public static final int SWIVEL_SWITCH = 1;
            public static final int PIVOT_SWITCH = 2;
        }

    }

}
