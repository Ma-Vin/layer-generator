package com.github.ma_vin.util.layer_generator.logging;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

/**
 * Implementation of maven plugin logging which delegates logging statements to {@link javax.annotation.processing.Messager}
 */
@Data
@NoArgsConstructor
public class MessagerLogImpl implements ILogWrapper {

    private Diagnostic.Kind logLevel;
    private Messager messager;

    public MessagerLogImpl(ProcessingEnvironment processingEnv, Diagnostic.Kind logLevel) {
        messager = processingEnv.getMessager();
        this.logLevel = logLevel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebugEnabled() {
        return logLevel == Diagnostic.Kind.OTHER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(String msg) {
        if (!isDebugEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.OTHER, msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(String msg, Throwable throwable) {
        if (!isDebugEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.OTHER, msg);
        messager.printMessage(Diagnostic.Kind.OTHER, throwable.getMessage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(Throwable throwable) {
        if (!isDebugEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.OTHER, throwable.getMessage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfoEnabled() {
        return isDebugEnabled() || logLevel == Diagnostic.Kind.NOTE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(String msg) {
        if (!isInfoEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(String msg, Throwable throwable) {
        if (!isInfoEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
        messager.printMessage(Diagnostic.Kind.NOTE, throwable.getMessage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(Throwable throwable) {
        if (!isInfoEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, throwable.getMessage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWarnEnabled() {
        return isInfoEnabled() || logLevel == Diagnostic.Kind.WARNING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(String msg) {
        if (!isWarnEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.WARNING, msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(String msg, Throwable throwable) {
        if (!isWarnEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.WARNING, msg);
        messager.printMessage(Diagnostic.Kind.WARNING, throwable.getMessage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(Throwable throwable) {
        if (!isWarnEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.WARNING, throwable.getMessage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isErrorEnabled() {
        return isWarnEnabled() || logLevel == Diagnostic.Kind.ERROR;
    }

    @Override
    public void error(String msg) {
        if (!isErrorEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.ERROR, msg);
    }

    @Override
    public void error(String msg, Throwable throwable) {
        if (!isErrorEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.ERROR, msg);
        messager.printMessage(Diagnostic.Kind.ERROR, throwable.getMessage());
    }

    @Override
    public void error(Throwable throwable) {
        if (!isErrorEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.ERROR, throwable.getMessage());
    }
}
