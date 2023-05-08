package de.ma_vin.util.layer.generator;

import de.ma_vin.util.layer.generator.logging.Log4jLogImpl;
import org.apache.maven.plugin.logging.Log;

public class MavenLog4jLogImpl extends Log4jLogImpl implements Log {
    @Override
    public void debug(CharSequence content) {
        super.debug("" + content);
    }

    @Override
    public void debug(CharSequence content, Throwable error) {
        super.debug("" + content, error);
    }

    @Override
    public void info(CharSequence content) {
        super.info("" + content);
    }

    @Override
    public void info(CharSequence content, Throwable error) {
        super.info("" + content, error);
    }

    @Override
    public void warn(CharSequence content) {
        super.warn("" + content);
    }

    @Override
    public void warn(CharSequence content, Throwable error) {
        super.warn("" + content, error);
    }

    @Override
    public void error(CharSequence content) {
        super.error("" + content);
    }

    @Override
    public void error(CharSequence content, Throwable error) {
        super.error("" + content, error);
    }
}
