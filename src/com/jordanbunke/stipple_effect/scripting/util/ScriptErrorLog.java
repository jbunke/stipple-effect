package com.jordanbunke.stipple_effect.scripting.util;

import java.util.ArrayList;
import java.util.List;

public final class ScriptErrorLog {
    private static final List<String> errors;

    static {
        errors = new ArrayList<>();
    }

    private enum ErrorClass {
        COMPILE, RUNTIME, IO;

        private String prefix() {
            return name() + " ERROR: ";
        }
    }

    public enum Message {
        CUSTOM_CT, CUSTOM_RT,
        NOT_HOF,
        ARG_PARAM_MISMATCH,
        INVALID_ARG_TYPE,
        ARGS_SIGNATURE_MISMATCH,
        CANNOT_REDUCE_EMPTY_COL,
        NAN,
        PATH_DOES_NOT_CONTAIN_IMAGE,
        DIFFERENT_TYPES,
        OPERAND_NAN_RT, OPERAND_NAN_SEM,
        INVALID_OPERAND_PLUS,
        OPERAND_NOT_A_COLLECTION_RT, OPERAND_NOT_A_COLLECTION_SEM,
        OPERAND_NOT_BOOL,
        DIV_BY_ZERO,
        ARR_LENGTH_NOT_INT,
        ARR_LENGTH_NEGATIVE,
        COLOR_CHANNEL_NOT_INT,
        COLOR_CHANNEL_OUT_OF_BOUNDS,
        EXPECTED_FOR_CALL,
        ARG_NOT_TYPE,
        PIX_ARG_OUT_OF_BOUNDS,
        NON_POSITIVE_IMAGE_BOUND,
        MAP_DOES_NOT_CONTAIN_ELEMENT,
        ARGS_PARAMS_MISMATCH,
        UNINITIALIZED_VAR,
        UNDEFINED_VAR, UNDEFINED_FUNC,
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
        RETURN_TYPE_MISMATCH,
        VAR_ALREADY_DEFINED,
        ADD_TO_ARRAY,
        REMOVE_FROM_SET_OR_ARRAY,
        ASSIGN_EXPR_NOT_NUM,
        ASSIGN_EXPR_NOT_STRING,
        NOT_ITERABLE,
        COULD_NOT_READ,
        UNEXPECTED_RUNTIME_ERROR,
        SUB_BEG_OUT_OF_BOUNDS,
        SUB_END_OUT_OF_BOUNDS,
        SUB_END_GEQ_BEG
        ;

        private String get(final String[] args) {
            return errorClass().prefix() + switch (this) {
                case CUSTOM_CT, CUSTOM_RT -> args[0];
                case NOT_HOF -> "Treating the expression \"" + args[0] +
                        "\" as a higher-order function although it is " +
                        "of type \"" + args[1] + "\"";
                case ARG_PARAM_MISMATCH -> "Attempting to pass an argument" +
                        " into the script that does not comply with the type" +
                        " of the parameter: \"" + args[0] + "\"";
                case INVALID_ARG_TYPE ->
                        "Attempting to pass an invalid data type into the " +
                                "script: \"" + args[0] + "\"";
                case ARGS_SIGNATURE_MISMATCH ->
                        "Attempting to call the function \"" + args[0] +
                                "\" with a set of arguments that do not " +
                                "match its signature";
                case INVALID_OPERAND_PLUS -> "Operand of type \"" + args[0] +
                        "\" can neither be added nor concatenated (+)";
                case CANNOT_REDUCE_EMPTY_COL ->
                        "Attempted to reduce an empty collection to a value";
                case NAN -> {
                    final String description = args[0], actualType = args[1];

                    yield typeMismatch(description + " is not a number",
                            "int\" or \"float", actualType);
                }
                case SUB_BEG_OUT_OF_BOUNDS -> {
                    final String index = args[0];

                    yield "Substring beginning index argument was evaluated " +
                            "as " + index + ", but cannot be < 0";
                }
                case SUB_END_OUT_OF_BOUNDS -> {
                    final String l = args[0], index = args[1];

                    yield "Substring end index argument (" + index + ") " +
                            "is greater than the string's length (" + l + ")";
                }
                case SUB_END_GEQ_BEG -> {
                    final String beg = args[0], end = args[1];

                    yield "Substring beginning index argument (" + beg +
                            ") is greater than or equal to end index " +
                            "argument (" + end + ")";
                }
                case COULD_NOT_READ -> "Couldn't read the script file";
                case UNEXPECTED_RUNTIME_ERROR -> "Unknown and unexpected";
                case NOT_ITERABLE ->
                    typeMismatch("non-iterable type used in iterator loop",
                            args[0], args[1]);
                case PATH_DOES_NOT_CONTAIN_IMAGE -> {
                    final String filepath = args[0];

                    yield "No image was found at the filepath \"" +
                            filepath + "\"";
                }
                case DIFFERENT_TYPES -> {
                    final String description = args[0],
                            a = args[1], b = args[2],
                            typeA = args[3], typeB = args[4];

                    yield description + " are of different types; " + a +
                            " is \"" + typeA + "\" and " + b + " is \"" +
                            typeB + "\"";

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
                            value + ", which is out of bounds (0 <= c <= 255)";
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
                case ARG_NOT_TYPE -> {
                    final String arg = args[0], expectedType = args[1],
                            actualType = args[2];

                    yield typeMismatch(arg + " argument",
                            expectedType, actualType);
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
                case UNDEFINED_FUNC -> "Attempted to call function \"" +
                        args[0] + "\" that is not defined in this script";
                case UNINITIALIZED_VAR ->
                        "Variable \"" + args[0] +
                                "\" has not been initialized";
                case INDEX_NOT_INT -> typeMismatch("collection index",
                        "int", args[0]);
                case INDEX_OUT_OF_BOUNDS -> {
                    final int index = Integer.parseInt(args[0]),
                            size = Integer.parseInt(args[1]);
                    final boolean include = args.length > 2 &&
                            Boolean.parseBoolean(args[2]);

                    if (!include && size == 0)
                        yield "Empty collection cannot be indexed";

                    yield "Collection index " + index + " is out of bounds" +
                            " for collection of size " + size +
                            "; (0 <= i <" + (include ? "= " : " ") +
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
                        "at index " + args[0] + " of explicit " +
                                args[1] + " initialization", args[2], args[3]);
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
                        INDEX_OUT_OF_BOUNDS,
                        SUB_BEG_OUT_OF_BOUNDS,
                        SUB_END_GEQ_BEG,
                        SUB_END_OUT_OF_BOUNDS,
                        CUSTOM_RT ->
                        ErrorClass.RUNTIME;
                case COULD_NOT_READ -> ErrorClass.IO;
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

    public static String[] getErrors() {
        return errors.toArray(String[]::new);
    }

    public static void clearErrors() {
        errors.clear();
    }
}
