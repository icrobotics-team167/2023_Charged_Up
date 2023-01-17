package frc.robot;

import frc.robot.controls.controllers.ControllerType;

public class Config {

    public static final class Settings {

        // Use Talon tank drive/test bot (false) or Spark tank drive/real bot (true)
        public static final boolean SPARK_TANK_ENABLED = true;

        // Controllers
        public static final ControllerType PRIMARY_CONTROLLER_TYPE = ControllerType.XB;
        public static final ControllerType SECONDARY_CONTROLLER_TYPE = ControllerType.PS;

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

        // Threshold for a controller trigger to count as pressed (on XB controllers only)
        public static final double TRIGGER_PRESSED_THRESHOLD = 0.2;

        // Tank drive dead zone size
        public static final double TANK_DEAD_ZONE_SIZE = 0.1;

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
        // public static final int PCM = 2

        // Spark tank motor controller ports
        public static final class SparkTank {
            public static final int PCM = 2;
            
            //Drive base CAN bus Addresses
            public static final int LEFT_1 = 3;
            public static final int LEFT_2 = 4;
            public static final int LEFT_3 = 5;
            public static final int RIGHT_1 = 6;
            public static final int RIGHT_2 = 7;
            public static final int RIGHT_3 = 8;

            //PCM Ports
            public static final int LOW_GEAR = 5; //shifter cylinder out = high gear
            // public static final int SOLENOID_REVERSE = 2;
        }

        // Intake ports
        public static final class Intake {
            // CAN Bus Addresses
            public static final int INTAKE_MOTOR = 9;//12;
            public static final int OMNI_MOTOR = 10;
            
            //PCM ports
            public static final int IN_OUT = 6;
        }

        // Indexer ports
        public static final class Indexer {
            //CAN Bus Addresses
            public static final int PRE_SHOOTER = 12;//15;
            public static final int LIFT_MOTOR = 11;

            // public static final int LIMIT_SWITCH = 0;
        }

         // Shooter motor controller ports
        public static final class Shooter {
            //CAN Bus Addresses
            
            public static final int SHOOTER = 14;
            public static final int SHOOTER2 = 13;//9;
        }

        // Climb ports
        public static final class Climb {
            //CAN Bus Addresses
            public static final int WINCH2 = 16;
            public static final int WINCH = 15;//13;

            //PCM
            public static final int CLIMBER_ARMS = 7;
        }

    }

}
