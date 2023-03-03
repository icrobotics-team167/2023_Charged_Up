package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.routines.auto.*;
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
                        new DriveStraight(180, -0.3)
                                        .withTurret(TurretPosition.INTAKE.withExtension(3.5).withPivot(-20)),
                        // new MoveArm(TurretPo6sition.INTAKE.withExtension(3.5).withPivot(-20)),
                        new CloseClaw(),
                        new DriveForwardsUntil(
                                        navx -> navx.getPitch() >= 8,
                                        0.2,
                                        Duration.ofMillis(3500)),
                        new DriveStraight(50, 0.4),
                        new Wait(1),
                        new NaiveAutoBalance()
        })),
        GO_STRAIGHT("Score and go straight (Out of community score)", new Routine(new Action[] {
                        new MoveArm(TurretPosition.HIGH_GOAL),
                        new OpenClaw(),
                        new DriveStraight(180, -0.5)
                                        .withTurret(TurretPosition.INTAKE.withExtension(3.5).withSwivel(180)),
                        new MoveArm(TurretPosition.INTAKE.withExtension(3.5).withPivot(-20).withSwivel(180)),
                        new CloseClaw(),
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