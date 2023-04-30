package de.ma_vin.util.layer.generator.logging;

import lombok.Data;
import org.apache.maven.plugin.logging.Log;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Implementation of maven plugin logging which delegates logging statements to {@link javax.annotation.processing.Messager}
 */
@Data
public class MessagerLogImpl implements Log {

    private Diagnostic.Kind logLevel;
    private Messager messager;

    @Override
    public boolean isDebugEnabled() {
        return logLevel == Diagnostic.Kind.OTHER;
    }

    @Override
    public void debug(CharSequence charSequence) {
        if (!isDebugEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.OTHER, charSequence);
    }

    @Override
    public void debug(CharSequence charSequence, Throwable throwable) {
        if (!isDebugEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.OTHER, charSequence);
        messager.printMessage(Diagnostic.Kind.OTHER, throwable.getMessage());
    }

    @Override
    public void debug(Throwable throwable) {
        if (!isDebugEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.OTHER, throwable.getMessage());
    }

    @Override
    public boolean isInfoEnabled() {
        return isDebugEnabled() || logLevel == Diagnostic.Kind.NOTE;
    }

    @Override
    public void info(CharSequence charSequence) {
        if (!isInfoEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, charSequence);
    }

    @Override
    public void info(CharSequence charSequence, Throwable throwable) {
        if (!isInfoEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, charSequence);
        messager.printMessage(Diagnostic.Kind.NOTE, throwable.getMessage());
    }

    @Override
    public void info(Throwable throwable) {
        if (!isInfoEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, throwable.getMessage());
    }

    @Override
    public boolean isWarnEnabled() {
        return isInfoEnabled() || logLevel == Diagnostic.Kind.WARNING;
    }

    @Override
    public void warn(CharSequence charSequence) {
        if (!isWarnEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.WARNING, charSequence);
    }

    @Override
    public void warn(CharSequence charSequence, Throwable throwable) {
        if (!isWarnEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.WARNING, charSequence);
        messager.printMessage(Diagnostic.Kind.WARNING, throwable.getMessage());
    }

    @Override
    public void warn(Throwable throwable) {
        if (!isWarnEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.WARNING, throwable.getMessage());
    }

    @Override
    public boolean isErrorEnabled() {
        return isWarnEnabled() || logLevel == Diagnostic.Kind.ERROR;
    }

    @Override
    public void error(CharSequence charSequence) {
        if (!isErrorEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.ERROR, charSequence);
    }

    @Override
    public void error(CharSequence charSequence, Throwable throwable) {
        if (!isErrorEnabled()) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.ERROR, charSequence);
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
