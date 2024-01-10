package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.io.ResourceLoader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ParserUtils {
    public static final int CODE = 0, VALUE = 1, DESIRED = 2;

    public static String[] extractHighlight(final String inputLine) {
        final String O = Constants.OPEN_HIGHLIGHT, C = Constants.CLOSE_HIGHLIGHT;

        String leftToProcess = inputLine;
        final List<String> blocks = new ArrayList<>();

        while (leftToProcess.contains(O) && leftToProcess.contains(C) &&
                leftToProcess.indexOf(O) < leftToProcess.indexOf(C)) {
            final int openIndex = leftToProcess.indexOf(O),
                    closeIndex = leftToProcess.indexOf(C);

            blocks.add(leftToProcess.substring(0, openIndex));
            blocks.add(leftToProcess.substring(openIndex + O.length(), closeIndex));

            leftToProcess = leftToProcess.substring(closeIndex + C.length());
        }

        if (!leftToProcess.isEmpty())
            blocks.add(leftToProcess);

        return blocks.toArray(String[]::new);
    }

    public static String[] getBlurb(final String blurbCode) {
        final Path blurbFile = Constants.BLURB_FOLDER.resolve(blurbCode + ".txt");

        return FileIO.readResource(
                ResourceLoader.loadResource(blurbFile), "\"" + blurbCode + "\" blurb"
        ).split("\n");
    }

    public static String[] getToolTip(final String toolTipCode) {
        final Path blurbFile = Constants.TOOL_TIP_FOLDER
                .resolve(toolTipCode + ".txt");

        return FileIO.readResource(ResourceLoader.loadResource(blurbFile),
                "\"" + toolTipCode + "\" tooltip").split("\n");
    }

    public static String[] splitIntoCodeAndValue(final String line) {
        final String sep = Constants.SETTING_SEPARATOR,
                o = Constants.OPEN_SETTING_VAL,
                c = Constants.CLOSE_SETTING_VAL;
        final int oi = line.indexOf(o), ol = o.length(),
                ci = line.indexOf(c), si = line.indexOf(sep);

        final boolean hasValue = oi > si && oi < ci, valid = si > 0 && hasValue;

        if (!valid)
            return new String[] {};

        final String code = line.substring(0, si),
                value = line.substring(oi + ol, ci);

        return new String[] { code, value };
    }
}
