package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.stipple_effect.project.SEContext;

public record LayerRep(SEContext project, int index) {
    @Override
    public String toString() {
        return index + 1 + " of " + project.getState().getLayers().size() +
                " in " + project.projectInfo.getName();
    }
}
