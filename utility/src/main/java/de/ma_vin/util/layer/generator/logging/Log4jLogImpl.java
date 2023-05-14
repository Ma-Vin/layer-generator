package de.ma_vin.util.layer.generator.logging;

import lombok.extern.log4j.Log4j2;
import org.apache.maven.plugin.logging.Log;

/**
 * Implementation of maven plugin logging which delegates logging statements to log4j
 */
@Log4j2
public class Log4jLogImpl implements ILogWrapper {

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public void debug(String msg) {
        log.debug(msg);
    }

    @Override
    public void debug(String msg, Throwable error) {
        log.debug(msg, error);
    }

    @Override
    public void debug(Throwable error) {
        log.debug("", error);
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public void info(String msg) {
        log.info(msg);
    }

    @Override
    public void info(String msg, Throwable error) {
        log.info(msg, error);
    }

    @Override
    public void info(Throwable error) {
        log.info("", error);
    }

    @Override
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    @Override
    public void warn(String msg) {
        log.warn(msg);
    }

    @Override
    public void warn(String msg, Throwable error) {
        log.warn(msg, error);
    }

    @Override
    public void warn(Throwable error) {
        log.warn("", error);
    }

    @Override
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    @Override
    public void error(String msg) {
        log.error(msg);
    }

    @Override
    public void error(String msg, Throwable error) {
        log.error(msg, error);
    }

    @Override
    public void error(Throwable error) {
        log.error("", error);
    }
}
