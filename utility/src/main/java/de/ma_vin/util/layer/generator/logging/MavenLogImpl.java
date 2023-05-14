package de.ma_vin.util.layer.generator.logging;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.maven.plugin.logging.Log;

@AllArgsConstructor
@Data
public class MavenLogImpl implements ILogWrapper, Log {
    private Log logger;

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(CharSequence content) {
        logger.debug(content);
    }

    @Override
    public void debug(CharSequence content, Throwable error) {
        logger.debug(content, error);
    }

    @Override
    public void debug(String msg) {
        logger.debug(msg);
    }

    @Override
    public void debug(String msg, Throwable error) {
        logger.debug(msg, error);
    }

    @Override
    public void debug(Throwable error) {
        logger.debug(error);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(CharSequence content) {
        logger.debug(content);
    }

    @Override
    public void info(CharSequence content, Throwable error) {
        logger.info(content, error);
    }

    @Override
    public void info(String msg) {
        logger.info(msg);
    }

    @Override
    public void info(String msg, Throwable error) {
        logger.info(msg, error);
    }

    @Override
    public void info(Throwable error) {
        logger.info(error);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void warn(CharSequence content) {
        logger.warn(content);
    }

    @Override
    public void warn(CharSequence content, Throwable error) {
        logger.warn(content, error);
    }

    @Override
    public void warn(String msg) {
        logger.warn(msg);
    }

    @Override
    public void warn(String msg, Throwable error) {
        logger.warn(msg, error);
    }

    @Override
    public void warn(Throwable error) {
        logger.warn(error);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void error(CharSequence content) {
        logger.error(content);
    }

    @Override
    public void error(CharSequence content, Throwable error) {
        logger.error(content, error);
    }

    @Override
    public void error(String msg) {
        logger.error(msg);
    }

    @Override
    public void error(String msg, Throwable error) {
        logger.error(msg, error);
    }

    @Override
    public void error(Throwable error) {
        logger.error(error);
    }
}
