package frc.robot.routines;

import frc.robot.routines.auto.AutoState;

public abstract class Action {

    public abstract void init();

    public abstract void periodic();

    public abstract boolean isDone();

    public abstract void done();

    protected AutoState state;

    public Action() {
        state = AutoState.INIT;
    }

    public void exec() {
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
