package com.jordanbunke.stipple_effect.parse;

import com.jordanbunke.stipple_effect.utility.Constants;

public class ParserUtils {
    public static final int HIGHLIGHTED_INDEX = 1;

    public static String[] extractHighlight(final String inputLine) {
        final int openLength = Constants.OPEN_HIGHLIGHT.length(),
                closeLength = Constants.CLOSE_HIGHLIGHT.length(),
                openIndex = inputLine.indexOf(Constants.OPEN_HIGHLIGHT),
                closeIndex = inputLine.indexOf(Constants.CLOSE_HIGHLIGHT);

        final boolean has = openIndex >= 0 && closeIndex > openIndex;

        return has ? new String[] {
                inputLine.substring(0, openIndex),
                inputLine.substring(openIndex + openLength, closeIndex),
                inputLine.substring(closeIndex + closeLength)
        } : new String[] { inputLine };
    }
}
