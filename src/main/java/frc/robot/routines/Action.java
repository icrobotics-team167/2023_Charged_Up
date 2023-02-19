package frc.robot.routines;

import frc.robot.routines.auto.AutoState;

public abstract class Action {

    private boolean isEnabled;

    public abstract void init();

    public abstract void onEnable();

    public abstract void periodic();

    public abstract boolean isDone();

    public abstract void done();

    protected AutoState state;

    public Action() {
        state = AutoState.INIT;
    }

    public void exec() {
        if (edu.wpi.first.wpilibj.RobotState.isEnabled()&&!isEnabled) {
            onEnable();
            isEnabled = true;
        } else if (edu.wpi.first.wpilibj.RobotState.isDisabled()) {
            isEnabled = false;
        }
        if (state == null) {
            return;
        }
        switch (state) {
            case INIT:
                init();
                setState(AutoState.PERIODIC);
                break;
            case PERIODIC:
                periodic();
                if (isDone()) {
                    setState(AutoState.DONE);
                }
                break;
            case DONE:
                done();
                setState(AutoState.FINISHED);
                break;
            case FINISHED:
                break;
            case EXIT:
                done();
                break;
            default:
                break;
        }
    }

    public void setState(AutoState state) {
        this.state = state;
    }

    public AutoState getState() {
        return state;
    }

}
