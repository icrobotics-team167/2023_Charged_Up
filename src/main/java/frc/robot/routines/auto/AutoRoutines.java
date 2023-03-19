package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.routines.auto.*;
import frc.robot.routines.Routine;
import frc.robot.subsystems.turret.Turret;
import frc.robot.subsystems.turret.TurretPosition;

import java.time.Duration;

/**
 * An enum for selecting auto routines.
 */
public enum AutoRoutines {
        // ROUTINE TEMPLATE
        //
        // ROUTINE_ID("Routine Name", new Routine(new Action[] {
        // new NullAction(),
        // })),

        BALANCE_CAUTIOUS("Balance Cautious", new Routine(new Action[] {
                        new MoveArm(TurretPosition.HIGH_MID),
                        new OpenClaw(),
                        new Wait(0.25),
                        // new DriveStraight(20, -0.3).withTurret(TurretPosition.INITIAL),
                        new DriveStraight(170, -0.5).withTurret(TurretPosition.INITIAL),
                        // new MoveArm(TurretPosition.INITIAL),
                        // new DriveStraight(5, 0.2),
                        // new DriveStraight(40, -0.3),
                        new DriveStraightUntil(
                                        navx -> Math.abs(navx.getPitch()) >= 8,
                                        0.3,
                                        Duration.ofMillis(3500)),
                        new DriveStraight(35, 0.4),
                        // new DriveStraight(30 + Math.random() * 4 - 2, -0.4),
                        // new Wait(0.5),
                        new AutoBalance()
        // new NaiveAutoBalance()

        })),
        GO_STRAIGHT_BLUE("Score cube then cone (Blue Alliance)", new Routine(new Action[] {
                        new MoveArm(TurretPosition.HIGH_GOAL_CUBE_BLUE),
                        // new MoveArm(TurretPosition.HIGH_GOAL_CENTER),
                        new OpenClaw(),
                        new DriveStraight(195, -0.3)
                                        .withTurret(TurretPosition.INITIAL),
                        new MoveArm(TurretPosition.INITIAL.withSwivel(-180)),
                        new MoveArm(new TurretPosition(-28, -180, 17)),
                        new Wait(5),
                        new CloseClaw(),
                        new DriveStraight(180, 0.3).withTurret(TurretPosition.INITIAL),
                        new MoveArm(TurretPosition.HIGH_GOAL_CONE_BLUE),
                        new OpenClaw(),
        })),
        GO_STRAIGHT_RED("Score cube then cone (Red Alliance)", new Routine(new Action[] {
                        new MoveArm(TurretPosition.HIGH_GOAL_CUBE_RED),
                        // new MoveArm(TurretPosition.HIGH_GOAL_CENTER),
                        new OpenClaw(),
                        new DriveStraight(195, -0.3)
                                        .withTurret(TurretPosition.INITIAL),
                        new MoveArm(TurretPosition.INITIAL.withSwivel(180)),
                        new MoveArm(new TurretPosition(-28, 180, 17)),
                        new CloseClaw(),
                        new DriveStraight(180, 0.3).withTurret(TurretPosition.INITIAL),
                        new MoveArm(TurretPosition.HIGH_GOAL_CONE_RED),
                        new OpenClaw(),
        })),
        TEST_BALANCE("Test:Balance Test", new Routine(new Action[] {
                        new DriveStraightUntil(
                                        navx -> Math.abs(navx.getPitch()) >= 8,
                                        -0.3,
                                        Duration.ofMillis(3500)),
                        new DriveStraight(35, -0.4),
                        new AutoBalance()

        })),
        NOTHING("Nothing", new Routine(new Action[] {
                        new NullAction(),
        }));

        public String name;
        public Routine actions;

        AutoRoutines(String name, Routine actions) {
                this.name = name;
                this.actions = actions;
        }
}