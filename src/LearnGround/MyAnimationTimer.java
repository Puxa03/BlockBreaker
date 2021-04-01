package LearnGround;

import javafx.animation.AnimationTimer;

public abstract class MyAnimationTimer extends AnimationTimer {
    private  boolean running;

    @Override
    public void start() {
        super.start();
        running = true;
    }

    @Override
    public void stop() {
        super.stop();
        running = false;
    }

    public boolean isRunning() {
        return running;
    }


    public abstract void handle(long now);

}
