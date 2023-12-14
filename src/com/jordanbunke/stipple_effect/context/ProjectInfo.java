package com.jordanbunke.stipple_effect.context;

import com.jordanbunke.stipple_effect.utility.Constants;

import java.nio.file.Path;

public class ProjectInfo {
    private Path filepath;
    private boolean editedSinceLastSave;

    public ProjectInfo() {
        this(null);
    }

    public ProjectInfo(final Path filepath) {
        this.filepath = filepath;
        this.editedSinceLastSave = false;
    }

    @Override
    public String toString() {
        return (editedSinceLastSave ? "* " : "") + (filepath == null
                ? Constants.UNTITLED_PROJECT_NAME
                : filepath.getFileName());
    }

    public void markAsEdited() {
        editedSinceLastSave = true;
    }
}
