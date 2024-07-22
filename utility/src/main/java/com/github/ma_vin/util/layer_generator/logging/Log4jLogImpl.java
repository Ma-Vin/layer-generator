package com.github.ma_vin.util.layer_generator.logging;

import lombok.extern.log4j.Log4j2;

/**
 * Implementation of maven plugin logging which delegates logging statements to log4j
 */
@Log4j2
public class Log4jLogImpl implements ILogWrapper {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(String msg) {
        log.debug(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(String msg, Throwable error) {
        log.debug(msg, error);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(Throwable error) {
        log.debug("", error);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(String msg) {
        log.info(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(String msg, Throwable error) {
        log.info(msg, error);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(Throwable error) {
        log.info("", error);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(String msg) {
        log.warn(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(String msg, Throwable error) {
        log.warn(msg, error);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(Throwable error) {
        log.warn("", error);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(String msg) {
        log.error(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(String msg, Throwable error) {
        log.error(msg, error);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(Throwable error) {
        log.error("", error);
    }
}
