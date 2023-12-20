package com.jordanbunke.stipple_effect.parse;

import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ParserUtils {
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

}
