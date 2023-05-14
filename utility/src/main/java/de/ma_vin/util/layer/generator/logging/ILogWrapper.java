package de.ma_vin.util.layer.generator.logging;

/**
 * Interface to wrap different logging apis
 */
public interface ILogWrapper {
    /**
     * @return {@code true} if the log level {@code DEBUG} is enabled, otherwise {@code false}
     */
    boolean isDebugEnabled();

    /**
     * logs a message with severity {@code DEBUG}
     *
     * @param msg the message to log
     */
    void debug(String msg);

    /**
     * logs a message with severity {@code DEBUG} with a throwable
     *
     * @param msg   the message to log
     * @param error the throwable to log
     */
    void debug(String msg, Throwable error);

    /**
     * logs a throwable with severity {@code DEBUG}
     *
     * @param error the throwable to log
     */
    void debug(Throwable error);

    /**
     * @return {@code true} if the log level {@code INFO} is enabled, otherwise {@code false}
     */
    boolean isInfoEnabled();

    /**
     * logs a message with severity {@code INFO}
     *
     * @param msg the message to log
     */
    void info(String msg);

    /**
     * logs a message with severity {@code INFO} with a throwable
     *
     * @param msg   the message to log
     * @param error the throwable to log
     */
    void info(String msg, Throwable error);

    /**
     * logs a throwable with severity {@code INFO}
     *
     * @param error the throwable to log
     */
    void info(Throwable error);

    /**
     * @return {@code true} if the log level {@code WARN} is enabled, otherwise {@code false}
     */
    boolean isWarnEnabled();

    /**
     * logs a message with severity {@code WARN}
     *
     * @param msg the message to log
     */
    void warn(String msg);

    /**
     * logs a message with severity {@code WARN} with a throwable
     *
     * @param msg   the message to log
     * @param error the throwable to log
     */
    void warn(String msg, Throwable error);

    /**
     * logs a throwable with severity {@code WARN}
     *
     * @param error the throwable to log
     */
    void warn(Throwable error);

    /**
     * @return {@code true} if the log level {@code ERROR} is enabled, otherwise {@code false}
     */
    boolean isErrorEnabled();

    /**
     * logs a message with severity {@code ERROR}
     *
     * @param msg the message to log
     */
    void error(String msg);

    /**
     * logs a message with severity {@code ERROR} with a throwable
     *
     * @param msg   the message to log
     * @param error the throwable to log
     */
    void error(String msg, Throwable error);

    /**
     * logs a throwable with severity {@code ERROR}
     *
     * @param error the throwable to log
     */
    void error(Throwable error);
}
