package com.github.ma_vin.util.layer_generator.logging;

import org.apache.maven.plugin.logging.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * {@link MavenLogImpl} is the class under test
 */
public class MavenrLogImplTest {
    public static final String THROWABLE_MESSAGE = "DummyThrowableMessage";
    public static final String MESSAGE = "DummyMessage";

    private AutoCloseable openMocks;

    @InjectMocks
    private MavenLogImpl cut;

    @Mock
    private Log log;
    @Mock
    private Throwable throwable;

    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);
        cut.setLogger(log);
        when(throwable.getMessage()).thenReturn(THROWABLE_MESSAGE);

    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    public void testIsDebugEnabled() {
        when(log.isDebugEnabled()).thenReturn(Boolean.TRUE);
        assertTrue(cut.isDebugEnabled(), "debug should be enabled");
    }

    @Test
    public void testIsDebugDisabled() {
        when(log.isDebugEnabled()).thenReturn(Boolean.FALSE);
        assertFalse(cut.isDebugEnabled(), "debug should be disabled");

    }

    @Test
    public void testIsInfoEnabled() {
        when(log.isInfoEnabled()).thenReturn(Boolean.TRUE);
        assertTrue(cut.isInfoEnabled(), "info should be enabled");
    }

    @Test
    public void testIsInfoDisabled() {
        when(log.isInfoEnabled()).thenReturn(Boolean.FALSE);
        assertFalse(cut.isInfoEnabled(), "info should be disabled");
    }

    @Test
    public void testIsWarnEnabled() {
        when(log.isWarnEnabled()).thenReturn(Boolean.TRUE);
        assertTrue(cut.isWarnEnabled(), "warn should be enabled");
    }

    @Test
    public void testIsWarnDisabled() {
        when(log.isWarnEnabled()).thenReturn(Boolean.FALSE);
        assertFalse(cut.isWarnEnabled(), "warn should be disabled for");
    }

    @Test
    public void testIsErrorEnabled() {
        when(log.isErrorEnabled()).thenReturn(Boolean.TRUE);
        assertTrue(cut.isErrorEnabled(), "error should be enabled");
    }

    @Test
    public void testIsErrorDisabled() {
        when(log.isErrorEnabled()).thenReturn(Boolean.FALSE);
        assertFalse(cut.isErrorEnabled(), "error should be disabled");

    }

    @Test
    public void testDebugStringActive() {
        cut.debug(MESSAGE);
        verify(log).debug(eq(MESSAGE));
    }

    @Test
    public void testDebugStringAndThrowableActive() {
        cut.debug(MESSAGE, throwable);
        verify(log).debug(eq(MESSAGE), eq(throwable));
    }

    @Test
    public void testDebugThrowableActive() {
        cut.debug(throwable);
        verify(log).debug(eq(throwable));
    }

    @Test
    public void testInfoStringActive() {
        cut.info(MESSAGE);
        verify(log).info(eq(MESSAGE));
    }

    @Test
    public void testInfoStringAndThrowableActive() {
        cut.info(MESSAGE, throwable);
        verify(log).info(eq(MESSAGE), eq(throwable));
    }

    @Test
    public void testInfoThrowableActive() {
        cut.info(throwable);
        verify(log).info(eq(throwable));
    }

    @Test
    public void testWarnStringActive() {
        cut.warn(MESSAGE);
        verify(log).warn(eq(MESSAGE));
    }

    @Test
    public void testWarnStringAndThrowableActive() {
        cut.warn(MESSAGE, throwable);
        verify(log).warn(eq(MESSAGE), eq(throwable));
    }

    @Test
    public void testWarnThrowableActive() {
        cut.warn(throwable);
        verify(log).warn(eq(throwable));
    }

    @Test
    public void testErrorStringActive() {
        cut.error(MESSAGE);
        verify(log).error(eq(MESSAGE));
    }

    @Test
    public void testErrorStringAndThrowableActive() {
        cut.error(MESSAGE, throwable);
        verify(log).error(eq(MESSAGE), eq(throwable));
    }

    @Test
    public void testErrorThrowableActive() {
        cut.error(throwable);
        verify(log).error(eq(throwable));
    }
}
