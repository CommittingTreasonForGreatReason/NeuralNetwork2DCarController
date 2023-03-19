package graphics;

import javafx.animation.AnimationTimer;

public abstract class GameLoopTimer extends AnimationTimer {
    final boolean isPaused = false;

    private float lastFrameTimeNanos = 0;

    @Override
    public final void handle(final long now) {

        // run as long as timer isn't paused
        if (isPaused) {
            return;
        }

        final var secondsSinceLastFrame = (float) ((now - lastFrameTimeNanos) / 1e9);
        lastFrameTimeNanos = now;
        tick(secondsSinceLastFrame);
    }

    // method that gets called every time the timer is not paused  and handle() is triggered
    // used for all the draw/update method calls
    public abstract void tick(final float secondsSinceLastFrame);
}
