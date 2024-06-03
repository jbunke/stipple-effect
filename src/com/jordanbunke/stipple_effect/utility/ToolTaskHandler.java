package com.jordanbunke.stipple_effect.utility;

public final class ToolTaskHandler {
    private final Object[] constraints;
    private final Thread thread;

    public ToolTaskHandler(final Runnable task, final Object... constraints) {
        this.thread = new Thread(task);
        thread.start();

        this.constraints = constraints;
    }

    public static ToolTaskHandler dummy() {
        return new ToolTaskHandler(() -> {});
    }

    public boolean done() {
        return !thread.isAlive();
    }

    public boolean shouldKill(final Object... constraints) {
        if (constraints.length != this.constraints.length)
            return true;

        for (int i = 0; i < constraints.length; i++)
            if (!this.constraints[i].equals(constraints[i]))
                return false;

        return true;
    }

    public static ToolTaskHandler update(
            final ToolTaskHandler current, final Runnable task,
            final Object... updatedConstraints
    ) {
        if (current.done() || current.shouldKill(updatedConstraints)) {
            current.thread.interrupt();
            return new ToolTaskHandler(task, updatedConstraints);
        }

        return current;
    }
}
