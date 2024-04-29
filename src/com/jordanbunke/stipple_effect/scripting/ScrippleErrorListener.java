package com.jordanbunke.stipple_effect.scripting;

import java.util.ArrayList;
import java.util.List;

public final class ScrippleErrorListener {
    private static final List<String> errors;

    static {
        errors = new ArrayList<>();
    }

    private enum ErrorClass {
        COMPILE, RUNTIME;

        private String prefix() {
            return name() + " ERROR: ";
        }
    }

    public enum Message {
        PATH_NOT_STRING,
        PATH_DOES_NOT_CONTAIN_IMAGE,
        TERNARY_CONDITION_NOT_BOOL,
        TERNARY_BRANCHES_OF_DIFFERENT_TYPES,
        OPERAND_NAN_RT, OPERAND_NAN_SEM,
        OPERAND_NOT_A_COLLECTION_RT, OPERAND_NOT_A_COLLECTION_SEM,
        OPERAND_NOT_BOOL,
        DIV_BY_ZERO,
        ARR_LENGTH_NOT_INT,
        ARR_LENGTH_NEGATIVE,
        COLOR_CHANNEL_NOT_INT,
        COLOR_CHANNEL_OUT_OF_BOUNDS,
        EXPECTED_IMAGE_FOR_CALL,
        EXPECTED_COLOR_FOR_CALL,
        EXPECTED_MAP_FOR_CALL,
        IMG_ARG_NOT_INT,
        TEX_COL_REPL_ARG_NOT_IMG,
        PIX_ARG_OUT_OF_BOUNDS,
        NON_POSITIVE_IMAGE_BOUND,
        MAP_DOES_NOT_CONTAIN_ELEMENT,
        EXPECTED_SEARCHABLE_FOR_CALL,
        ARGS_PARAMS_MISMATCH,
        UNINITIALIZED_VAR,
        UNDEFINED_VAR,
        INDEX_NOT_INT,
        INDEX_OUT_OF_BOUNDS,
        OWNER_NOT_COLLECTION,
        ELEMENT_DOES_NOT_MATCH_COL
        ;

        private String get(final String[] args) {
            return errorClass().prefix() + switch (this) {
                case PATH_NOT_STRING -> {
                    final String actualType = args[0];

                    yield "Filepath argument should be of type \"string\"," +
                            " instead is of type \"" + actualType + "\"";
                }
                case PATH_DOES_NOT_CONTAIN_IMAGE -> {
                    final String filepath = args[0];

                    yield "No image was found at the filepath \"" +
                            filepath + "\"";
                }
                case TERNARY_CONDITION_NOT_BOOL -> {
                    final String actualType = args[0];

                    yield "Ternary condition should be of type \"bool\"," +
                            " instead is of type \"" + actualType + "\"";
                }
                case TERNARY_BRANCHES_OF_DIFFERENT_TYPES -> {
                    final String ifType = args[0], elseType = args[1];

                    yield "Ternary branches are of different types;" +
                            " \"if\" branch is of type \"" + ifType +
                            "\", and the \"else\" branch is of type \"" +
                            elseType + "\"";

                }
                case OPERAND_NAN_RT ->
                        "Operand was not evaluated to be a number";
                case OPERAND_NOT_A_COLLECTION_RT ->
                        "Operand was not evaluated to a collection";
                case OPERAND_NOT_BOOL -> {
                    final String actualType = args[0];

                    yield "Operand should be of type \"bool\"," +
                            " instead is of type \"" + actualType + "\"";
                }
                case OPERAND_NAN_SEM -> {
                    final String actualType = args[0];

                    yield "Operand should be a numerical type (" +
                            "\"int\", \"float\"" +
                            "), instead is of type \"" + actualType + "\"";
                }
                case OPERAND_NOT_A_COLLECTION_SEM -> {
                    final String actualType = args[0];

                    yield "Operand should be a collection type (" +
                            "array - ?\"[]\", list - ?\"<>\", set - ?\"{}\"" +
                            "), instead is of type \"" + actualType + "\"";
                }
                case DIV_BY_ZERO ->
                    "Attempted to divide by 0";
                case ARR_LENGTH_NOT_INT -> {
                    final String actualType = args[0];

                    yield "Array length should be of type \"int\"," +
                            " instead is of type \"" + actualType + "\"";
                }
                case ARR_LENGTH_NEGATIVE -> {
                    final String length = args[0];

                    yield "Attempted to initialize array of negative length: " +
                            length;
                }
                case COLOR_CHANNEL_OUT_OF_BOUNDS -> {
                    final String channel = args[0], value = args[1];

                    yield channel + " color channel was evaluated as " +
                            value + ", which is out of bounds (0 <= x <= 255)";
                }
                case COLOR_CHANNEL_NOT_INT -> {
                    final String channel = args[0], actualType = args[1];

                    yield channel + "color channel should be of type \"int\"," +
                            " instead is of type \"" + actualType + "\"";
                }
                case EXPECTED_IMAGE_FOR_CALL ->
                        expectedTypeButGot("image", args[0]);
                case EXPECTED_COLOR_FOR_CALL ->
                        expectedTypeButGot("color", args[0]);
                case EXPECTED_MAP_FOR_CALL ->
                        expectedTypeButGot("map - {?:?}", args[0]);
                case EXPECTED_SEARCHABLE_FOR_CALL ->
                    expectedTypeButGot(
                            "map - {?:?}\", \"list - <>\", or \"set - {}",
                            args[0]);
                case MAP_DOES_NOT_CONTAIN_ELEMENT -> {
                    final String element = args[0];

                    yield "Attempted to fetch element with key \"" + element +
                            "from a map, but no such entry exists";
                }
                case IMG_ARG_NOT_INT -> {
                    final String dimension = args[0], actualType = args[1];

                    yield dimension + " argument should be of type \"int\"," +
                            " instead is of type \"" + actualType + "\"";
                }
                case TEX_COL_REPL_ARG_NOT_IMG -> {
                    final String arg = args[0], actualType = args[1];

                    yield arg + " argument should be of type \"image\"," +
                            " instead is of type \"" + actualType + "\"";
                }
                case PIX_ARG_OUT_OF_BOUNDS -> {
                    final String dimension = args[0], value = args[1],
                            bound = args[2];

                    yield dimension + " argument was evaluated as " +
                            value + ", which is out of bounds (0 <= " +
                            dimension + " < " + bound + ")";
                }
                case NON_POSITIVE_IMAGE_BOUND -> {
                    final String dimension = args[0], value = args[1];

                    yield dimension + " argument was evaluated as " + value +
                            ", which is invalid (" + dimension + " > 0)";
                }
                case ARGS_PARAMS_MISMATCH -> {
                    final String expected = args[0], received = args[1];

                    yield "The number of arguments passed into the script" +
                            " is different to the number of parameters; " +
                            "expected: " + expected +
                            ", received: " + received;
                }
                case UNDEFINED_VAR -> {
                    final String ident = args[0];

                    yield "Attempted to reference variable \"" + ident +
                            "\" that is not defined in the current scope";
                }
                case UNINITIALIZED_VAR -> {
                    final String ident = args[0];

                    yield "Variable \"" + ident +
                            "\" has not been initialized";
                }
                case INDEX_NOT_INT -> {
                    final String actualType = args[0];

                    yield "Collection index should be of type \"int\"," +
                            " instead is of type \"" + actualType + "\"";
                }
                case INDEX_OUT_OF_BOUNDS -> {
                    final String index = args[0], size = args[1];
                    final boolean include = args.length > 2 &&
                            Boolean.parseBoolean(args[2]);

                    yield "Collection index " + index + " is out of bounds" +
                            " for collection of size " + size +
                            "; (0 <= x <" + (include ? "= " : " ") +
                            size + ")";
                }
                case OWNER_NOT_COLLECTION -> expectedTypeButGot(
                        "list - <>\" or \"array - []", args[0]);
                case ELEMENT_DOES_NOT_MATCH_COL -> {
                    final String expectedType = args[0], actualType = args[1];

                    yield "Element resolved to type \"" + expectedType +
                            "\"; this collection takes elements of type \"" +
                            actualType + "\"";
                }
            };
        }

        private static String expectedTypeButGot(
                final String expectedType, final String actualType
        ) {
            return "Attempting to call a function for type \"" +
                    expectedType + "\" on an expression of type \"" +
                    actualType + "\"";
        }

        private ErrorClass errorClass() {
            return switch (this) {
                case PATH_NOT_STRING,
                        TERNARY_CONDITION_NOT_BOOL,
                        TERNARY_BRANCHES_OF_DIFFERENT_TYPES,
                        OPERAND_NAN_SEM,
                        OPERAND_NOT_A_COLLECTION_SEM,
                        OPERAND_NOT_BOOL,
                        ARR_LENGTH_NOT_INT,
                        COLOR_CHANNEL_NOT_INT,
                        EXPECTED_IMAGE_FOR_CALL,
                        EXPECTED_COLOR_FOR_CALL,
                        EXPECTED_MAP_FOR_CALL,
                        EXPECTED_SEARCHABLE_FOR_CALL,
                        IMG_ARG_NOT_INT,
                        TEX_COL_REPL_ARG_NOT_IMG,
                        UNDEFINED_VAR,
                        INDEX_NOT_INT,
                        OWNER_NOT_COLLECTION,
                        ELEMENT_DOES_NOT_MATCH_COL ->
                        ErrorClass.COMPILE;
                case PATH_DOES_NOT_CONTAIN_IMAGE,
                        OPERAND_NAN_RT,
                        OPERAND_NOT_A_COLLECTION_RT,
                        DIV_BY_ZERO,
                        ARR_LENGTH_NEGATIVE,
                        COLOR_CHANNEL_OUT_OF_BOUNDS,
                        PIX_ARG_OUT_OF_BOUNDS,
                        NON_POSITIVE_IMAGE_BOUND,
                        MAP_DOES_NOT_CONTAIN_ELEMENT,
                        ARGS_PARAMS_MISMATCH,
                        UNINITIALIZED_VAR,
                        INDEX_OUT_OF_BOUNDS ->
                        ErrorClass.RUNTIME;
            };
        }
    }

    public static void fireError(
            final Message message, final TextPosition position,
            final String... args
    ) {
        errors.add(formatError(message, position, args));
    }

    private static String formatError(
            final Message message, final TextPosition position,
            final String[] args
    ) {
        return message.get(args) + " [at " + position + "]";
    }
}
