package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.delta_time.image.GameImage;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ScrippleEquality {
    public static boolean equal(final Object a, final Object b) {
        if (a instanceof GameImage ai && b instanceof GameImage bi)
            return imagesEqual(ai, bi);
        else if (a instanceof Color ac && b instanceof Color bc)
            return colorsEqual(ac, bc);
        else if (a instanceof Object[] arrA && b instanceof Object[] arrB)
            return arraysEqual(arrA, arrB);
        else if (a instanceof Set<?> sa && b instanceof Set<?> sb)
            return setsEqual(sa, sb);
        else if (a instanceof List<?> la && b instanceof List<?> lb)
            return listsEqual(la, lb);
        else if (a instanceof Map<?,?> ma && b instanceof Map<?,?> mb)
            return mapsEqual(ma, mb);

        return a.equals(b) && b.equals(a);
    }

    private static boolean mapsEqual(
            final Map<?,?> a, final Map<?,?> b
    ) {
        if (!setsEqual(a.keySet(), b.keySet()))
            return false;

        final Set<?> keys = a.keySet();

        for (Object key : keys)
            if (!equal(a.get(key), b.get(key)))
                return false;

        return true;
    }

    private static boolean listsEqual(
            final List<?> a, final List<?> b
    ) {
        if (a.size() != b.size())
            return false;

        final int s = a.size();

        for (int i = 0; i < s; i++)
            if (!equal(a.get(i), b.get(i)))
                return false;

        return true;
    }

    @SuppressWarnings("all")
    private static boolean setsEqual(
            final Set<?> a, final Set<?> b
    ) {
        return a.containsAll(b) && b.containsAll(a);
    }

    private static <T> boolean arraysEqual(
            final T[] a, final T[] b
    ) {
        if (a.length != b.length)
            return false;

        final int l = a.length;

        for (int i = 0; i < l; i++)
            if (!equal(a[i], b[i]))
                return false;

        return true;
    }

    private static boolean colorsEqual(final Color a, final Color b) {
        return a.equals(b) && a.getAlpha() == b.getAlpha();
    }

    private static boolean imagesEqual(
            final GameImage a, final GameImage b
    ) {
        if (a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight())
            return false;

        final int w = a.getWidth(), h = a.getHeight();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (!colorsEqual(a.getColorAt(x, y), b.getColorAt(x, y)))
                    return false;
            }
        }

        return true;
    }
}
