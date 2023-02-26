package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.routines.Routine;
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
                        new MoveArm(TurretPosition.HIGH_GOAL),
                        new OpenClaw(),
                        new MoveArm(TurretPosition.INTAKE),
                        new DriveStraight(100, -1),
                        new DriveForwardsUntil(
                                        navx -> navx.getPitch() >= 5,
                                        0.5,
                                        Duration.ofMillis(3500)),
                        new DriveStraight(20, 0.4),
                        new NaiveAutoBalance(),
        })),
        BALANCE_GREEDY("Balance Greedy", new Routine(new Action[] {
                        new DriveForwardsUntil(
                                        navx -> navx.getPitch() >= 5,
                                        0.5,
                                        Duration.ofMillis(3500)),
                        new DriveStraight(20, 0.4),
                        new NaiveAutoBalance(),
        })),
        GO_STRAIGHT("Go straight (Out of community score)", new Routine(new Action[] {
                        new DriveStraight(150, 0.5),
                        new ResetArm()
        })),
        TEST_DRIVE_STRAIGHT_WITH_ARM("Drive straight with the arm", new Routine(new Action[] {
                        new DriveStraight(10, 0.2).withTurret(TurretPosition.INITIAL.withSwivel(
                                        120)),
                        new ResetArm()
        })),
        TEST_CLAW("Move the claw", new Routine(new Action[] {
                        new OpenClaw(),
                        new Wait(5),
                        new CloseClaw()
        })),
        DRIVE_BACKWARDS("drive backwards", new Routine(new Action[] {
                        new DriveStraight(-50, -0.5)
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