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

        SCORE_CONE("Score Cone", new Routine(new Action[] {
        // TODO: Make an auto routine for scoring a cone
        })),
        SCORE_CUBE("Score cube", new Routine(new Action[] {
        // TODO: Make an auto routine for scoring a cube
        })),
        BALANCE("Balance (Engaged score)", new Routine(new Action[] {
                        new DriveForwardsUntil(
                                        navx -> navx.getPitch() >= 5,
                                        0.5,
                                        Duration.ofMillis(3500)),
                        new DriveStraight(25, 0.4),
                        new NaiveAutoBalance(),
        })),
        GO_STRAIGHT("Go straight (Out of community score)", new Routine(new Action[] {
                        new DriveStraight(38, -0.5),
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