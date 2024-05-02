package com.jordanbunke.stipple_effect.scripting.util;

import java.util.ArrayList;
import java.util.List;

public final class ScriptErrorLog {
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
        TERN_COND_NOT_BOOL,
        TERNARY_BRANCHES_OF_DIFFERENT_TYPES,
        OPERAND_NAN_RT, OPERAND_NAN_SEM,
        OPERAND_NOT_A_COLLECTION_RT, OPERAND_NOT_A_COLLECTION_SEM,
        OPERAND_NOT_BOOL,
        DIV_BY_ZERO,
        ARR_LENGTH_NOT_INT,
        ARR_LENGTH_NEGATIVE,
        COLOR_CHANNEL_NOT_INT,
        COLOR_CHANNEL_OUT_OF_BOUNDS,
        EXPECTED_FOR_CALL,
        IMG_ARG_NOT_INT,
        ARG_NOT_IMG,
        PIX_ARG_OUT_OF_BOUNDS,
        NON_POSITIVE_IMAGE_BOUND,
        MAP_DOES_NOT_CONTAIN_ELEMENT,
        ARGS_PARAMS_MISMATCH,
        UNINITIALIZED_VAR,
        UNDEFINED_VAR,
        INDEX_NOT_INT,
        INDEX_OUT_OF_BOUNDS,
        ELEMENT_DOES_NOT_MATCH_COL,
        MAP_KEY_TYPE_MISMATCH,
        MAP_VALUE_TYPE_MISMATCH,
        INCONSISTENT_COL_TYPES,
        REASSIGN_FINAL,
        VAR_NOT_INT,
        VAR_NOT_BOOL,
        VAR_NOT_NUM,
        VAR_NOT_STRING,
        VAR_TYPE_MISMATCH,
        COND_NOT_BOOL,
        RETURN_TYPE_MISMATCH,
        VAR_ALREADY_DEFINED,
        ADD_TO_ARRAY,
        REMOVE_FROM_SET_OR_ARRAY,
        ASSIGN_EXPR_NOT_NUM,
        ASSIGN_EXPR_NOT_STRING,
        NOT_ITERABLE
        ;

        private String get(final String[] args) {
            return errorClass().prefix() + switch (this) {
                case NOT_ITERABLE ->
                    typeMismatch("non-iterable type used in iterator loop",
                            args[0], args[1]);
                case PATH_NOT_STRING ->
                        typeMismatch("filepath", "string", args[0]);
                case PATH_DOES_NOT_CONTAIN_IMAGE -> {
                    final String filepath = args[0];

                    yield "No image was found at the filepath \"" +
                            filepath + "\"";
                }
                case TERN_COND_NOT_BOOL, COND_NOT_BOOL -> {
                    final String actualType = args[0],
                            prefix = this == TERN_COND_NOT_BOOL
                                    ? "Ternary c" : "C";

                    yield typeMismatch(prefix + "ondition",
                            "bool", actualType);
                }
                case TERNARY_BRANCHES_OF_DIFFERENT_TYPES -> {
                    final String ifType = args[0], elseType = args[1];

                    yield "Ternary branches are of different types;" +
                            " branch A is of type \"" + ifType +
                            "\", and branch B is of type \"" +
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
                            "\"int\" or \"float\"" +
                            "), instead is of type \"" + actualType + "\"";
                }
                case OPERAND_NOT_A_COLLECTION_SEM -> {
                    final String actualType = args[0];

                    yield "Operand should be a collection type (" +
                            "array - ?\"[]\", list - ?\"<>\" or set - ?\"{}\"" +
                            "), instead is of type \"" + actualType + "\"";
                }
                case DIV_BY_ZERO ->
                    "Attempted to divide by 0";
                case ARR_LENGTH_NOT_INT ->
                        typeMismatch("initialized array length",
                                "int", args[0]);
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

                    yield typeMismatch("color channel \"" + channel + "\"",
                            "int", actualType);
                }
                case EXPECTED_FOR_CALL ->
                        callOwnerTypeMismatch(args[0], args[1], args[2]);
                case MAP_DOES_NOT_CONTAIN_ELEMENT -> {
                    final String element = args[0];

                    yield "Attempted to fetch element with key \"" + element +
                            "from a map, but no such entry exists";
                }
                case IMG_ARG_NOT_INT -> {
                    final String dimension = args[0], actualType = args[1];

                    yield typeMismatch(dimension + " argument",
                            "int", actualType);
                }
                case ARG_NOT_IMG -> {
                    final String arg = args[0], actualType = args[1];

                    yield typeMismatch(arg + " argument",
                            "image", actualType);
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
                case UNDEFINED_VAR ->
                        "Attempted to reference variable \"" + args[0] +
                                "\" that is not defined in the current scope";
                case UNINITIALIZED_VAR ->
                        "Variable \"" + args[0] +
                                "\" has not been initialized";
                case INDEX_NOT_INT -> typeMismatch("collection index",
                        "int", args[0]);
                case INDEX_OUT_OF_BOUNDS -> {
                    final String index = args[0], size = args[1];
                    final boolean include = args.length > 2 &&
                            Boolean.parseBoolean(args[2]);

                    yield "Collection index " + index + " is out of bounds" +
                            " for collection of size " + size +
                            "; (0 <= x <" + (include ? "= " : " ") +
                            size + ")";
                }
                case ELEMENT_DOES_NOT_MATCH_COL ->
                        typeMismatch("elements in this collection",
                                args[0], args[1]);
                case REASSIGN_FINAL ->
                        "Attempting to reassign variable \"" + args[0] +
                                "\", which was declared as immutable";
                case ASSIGN_EXPR_NOT_NUM ->
                        typeMismatch("assignment expression",
                                "int\" or \"float", args[0]);
                case ASSIGN_EXPR_NOT_STRING ->
                        typeMismatch("assignment expression",
                                "string", args[0]);
                case VAR_NOT_STRING ->
                        assignableTypeMismatch("string", args[0]);
                case VAR_NOT_INT ->
                        assignableTypeMismatch("int", args[0]);
                case VAR_NOT_NUM ->
                        assignableTypeMismatch("int\" or \"float", args[0]);
                case VAR_NOT_BOOL ->
                        assignableTypeMismatch("bool", args[0]);
                case VAR_TYPE_MISMATCH ->
                        assignableTypeMismatch(args[0], args[1]);
                case VAR_ALREADY_DEFINED ->
                        "Attempting to declare a variable \"" + args[0] +
                                "\", which already has a definition in this scope";
                case RETURN_TYPE_MISMATCH ->
                        typeMismatch("return expression does not match " +
                                "method signature", args[0], args[1]);
                case INCONSISTENT_COL_TYPES -> typeMismatch(
                        "at index " + args[0] + " of explicit collection " +
                                "initialization", args[1], args[2]);
                case MAP_KEY_TYPE_MISMATCH ->
                        typeMismatch("map key", args[0], args[1]);
                case MAP_VALUE_TYPE_MISMATCH ->
                        typeMismatch("map value", args[0], args[1]);
                case ADD_TO_ARRAY ->
                        "Arrays are of fixed length; elements " +
                                "cannot be added";
                case REMOVE_FROM_SET_OR_ARRAY ->
                        "Cannot remove an element by index " +
                                "from a set or an array, only from a list";
            };
        }

        private static String assignableTypeMismatch(
                final String expectedType, final String actualType
        ) {
            return typeMismatch(
                    "variable being assigned",
                    expectedType, actualType);
        }

        private static String callOwnerTypeMismatch(
                final String call,
                final String expectedType, final String actualType
        ) {
            return typeMismatch(
                    "owner expression of native function call \"" +
                            call + "\"", expectedType, actualType);
        }

        private static String typeMismatch(
                final String prefix,
                final String expectedType, final String actualType
        ) {
            return "Type mismatch... " + prefix + ": expected \"" +
                    expectedType + "\" but got \"" + actualType + "\"";
        }

        private ErrorClass errorClass() {
            return switch (this) {
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
                default -> ErrorClass.COMPILE;
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

    public static boolean hasNoErrors() {
        return errors.isEmpty();
    }

    public static List<String> getErrors() {
        return errors;
    }

    public static void clearErrors() {
        errors.clear();
    }
}
