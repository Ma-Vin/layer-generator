package de.ma_vin.util.layer.generator.logging;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic.Kind;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * {@link MessagerLogImpl} is the class under test
 */
public class MessagerLogImplTest {
    public static final String THROWABLE_MESSAGE = "DummyThrowableMessage";
    public static final String MESSAGE = "DummyMessage";

    private AutoCloseable openMocks;

    @InjectMocks
    private MessagerLogImpl cut;

    @Mock
    private ProcessingEnvironment processingEnv;
    @Mock
    private Messager messager;
    @Mock
    private Throwable throwable;

    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);
        cut.setMessager(messager);
        when(processingEnv.getMessager()).thenReturn(messager);
        when(throwable.getMessage()).thenReturn(THROWABLE_MESSAGE);

    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    public void testIsDebugEnabled() {
        cut.setLogLevel(Kind.OTHER);
        assertTrue(cut.isDebugEnabled(), "debug should be enabled");
    }

    @Test
    public void testIsDebugDisabled() {
        for (Kind k : Kind.values()) {
            if (k == Kind.OTHER) {
                continue;
            }
            cut.setLogLevel(k);
            assertFalse(cut.isDebugEnabled(), "debug should be disabled for " + k.name());
        }
    }

    @Test
    public void testIsInfoEnabled() {
        for (Kind k : Arrays.asList(Kind.OTHER, Kind.NOTE)) {
            cut.setLogLevel(k);
            assertTrue(cut.isInfoEnabled(), "info should be enabled for " + k.name());
        }
    }

    @Test
    public void testIsInfoDisabled() {
        for (Kind k : Kind.values()) {
            if (Arrays.asList(Kind.OTHER, Kind.NOTE).contains(k)) {
                continue;
            }
            cut.setLogLevel(k);
            assertFalse(cut.isInfoEnabled(), "info should be disabled for " + k.name());
        }
    }

    @Test
    public void testIsWarnEnabled() {
        for (Kind k : Arrays.asList(Kind.OTHER, Kind.NOTE, Kind.WARNING)) {
            cut.setLogLevel(k);
            assertTrue(cut.isWarnEnabled(), "warn should be enabled for " + k.name());
        }
    }

    @Test
    public void testIsWarnDisabled() {
        for (Kind k : Kind.values()) {
            if (Arrays.asList(Kind.OTHER, Kind.NOTE, Kind.WARNING).contains(k)) {
                continue;
            }
            cut.setLogLevel(k);
            assertFalse(cut.isWarnEnabled(), "warn should be disabled for " + k.name());
        }
    }

    @Test
    public void testIsErrorEnabled() {
        for (Kind k : Arrays.asList(Kind.OTHER, Kind.NOTE, Kind.WARNING, Kind.ERROR)) {
            cut.setLogLevel(k);
            assertTrue(cut.isErrorEnabled(), "error should be enabled for " + k.name());
        }
    }

    @Test
    public void testIsErrorDisabled() {
        for (Kind k : Kind.values()) {
            if (Arrays.asList(Kind.OTHER, Kind.NOTE, Kind.WARNING, Kind.ERROR).contains(k)) {
                continue;
            }
            cut.setLogLevel(k);
            assertFalse(cut.isErrorEnabled(), "error should be disabled for " + k.name());
        }
    }


    @Test
    public void testDebugStringActive() {
        cut.setLogLevel(Kind.OTHER);

        cut.debug(MESSAGE);
        checkMessageString(Kind.OTHER);
    }

    @Test
    public void testDebugStringInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.debug(MESSAGE);
        checkNoMessage();
    }

    @Test
    public void testDebugStringAndThrowableActive() {
        cut.setLogLevel(Kind.OTHER);

        cut.debug(MESSAGE, throwable);
        checkMessageStringAndThrowable(Kind.OTHER);
    }

    @Test
    public void testDebugStringAndThrowableInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.debug(MESSAGE, throwable);
        checkNoMessage();
    }

    @Test
    public void testDebugThrowableActive() {
        cut.setLogLevel(Kind.OTHER);

        cut.debug(throwable);
        checkMessageThrowable(Kind.OTHER);
    }

    @Test
    public void testDebugThrowableInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.debug(throwable);
        checkNoMessage();
    }


    @Test
    public void testInfoStringActive() {
        cut.setLogLevel(Kind.NOTE);

        cut.info(MESSAGE);
        checkMessageString(Kind.NOTE);
    }

    @Test
    public void testInfoStringInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.info(MESSAGE);
        checkNoMessage();
    }

    @Test
    public void testInfoStringAndThrowableActive() {
        cut.setLogLevel(Kind.NOTE);

        cut.info(MESSAGE, throwable);
        checkMessageStringAndThrowable(Kind.NOTE);
    }

    @Test
    public void testInfoStringAndThrowableInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.info(MESSAGE, throwable);
        checkNoMessage();
    }

    @Test
    public void testInfoThrowableActive() {
        cut.setLogLevel(Kind.NOTE);

        cut.info(throwable);
        checkMessageThrowable(Kind.NOTE);
    }

    @Test
    public void testInfoThrowableInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.info(throwable);
        checkNoMessage();
    }


    @Test
    public void testWarnStringActive() {
        cut.setLogLevel(Kind.WARNING);

        cut.warn(MESSAGE);
        checkMessageString(Kind.WARNING);
    }

    @Test
    public void testWarnStringInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.warn(MESSAGE);
        checkNoMessage();
    }

    @Test
    public void testWarnStringAndThrowableActive() {
        cut.setLogLevel(Kind.WARNING);

        cut.warn(MESSAGE, throwable);
        checkMessageStringAndThrowable(Kind.WARNING);
    }

    @Test
    public void testWarnStringAndThrowableInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.warn(MESSAGE, throwable);
        checkNoMessage();
    }

    @Test
    public void testWarnThrowableActive() {
        cut.setLogLevel(Kind.WARNING);

        cut.warn(throwable);
        checkMessageThrowable(Kind.WARNING);
    }

    @Test
    public void testWarnThrowableInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.warn(throwable);
        checkNoMessage();
    }


    @Test
    public void testErrorStringActive() {
        cut.setLogLevel(Kind.ERROR);

        cut.error(MESSAGE);
        checkMessageString(Kind.ERROR);
    }

    @Test
    public void testErrorStringInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.error(MESSAGE);
        checkNoMessage();
    }

    @Test
    public void testErrorStringAndThrowableActive() {
        cut.setLogLevel(Kind.ERROR);

        cut.error(MESSAGE, throwable);
        checkMessageStringAndThrowable(Kind.ERROR);
    }

    @Test
    public void testErrorStringAndThrowableInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.error(MESSAGE, throwable);
        checkNoMessage();
    }

    @Test
    public void testErrorThrowableActive() {
        cut.setLogLevel(Kind.ERROR);

        cut.error(throwable);
        checkMessageThrowable(Kind.ERROR);
    }

    @Test
    public void testErrorThrowableInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.error(throwable);
        checkNoMessage();
    }


    private void checkMessageString(Kind kind) {
        verify(messager).printMessage(eq(kind), eq(MESSAGE));
        verify(messager, never()).printMessage(eq(kind), eq(THROWABLE_MESSAGE));
    }

    private void checkMessageStringAndThrowable(Kind kind) {
        verify(messager).printMessage(eq(kind), eq(MESSAGE));
        verify(messager).printMessage(eq(kind), eq(THROWABLE_MESSAGE));
    }

    private void checkMessageThrowable(Kind kind) {
        verify(messager, never()).printMessage(eq(kind), eq(MESSAGE));
        verify(messager).printMessage(eq(kind), eq(THROWABLE_MESSAGE));
    }

    private void checkNoMessage() {
        verify(messager, never()).printMessage(any(), anyString());
    }
}
