package de.ma_vin.util.layer.generator.logging;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.annotation.processing.Messager;
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
    public static final String THROWABLE_MESSAGE = "DummyMessage";

    private AutoCloseable openMocks;

    @InjectMocks
    private MessagerLogImpl cut;

    @Mock
    private Messager messager;
    @Mock
    private CharSequence charSequence;
    @Mock
    private Throwable throwable;

    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);

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
    public void testDebugCharSequenceActive() {
        cut.setLogLevel(Kind.OTHER);

        cut.debug(charSequence);
        checkMessageCharSequence(Kind.OTHER);
    }

    @Test
    public void testDebugCharSequenceInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.debug(charSequence);
        checkNoMessage();
    }

    @Test
    public void testDebugCharSequenceAndThrowableActive() {
        cut.setLogLevel(Kind.OTHER);

        cut.debug(charSequence, throwable);
        checkMessageCharSequenceAndThrowable(Kind.OTHER);
    }

    @Test
    public void testDebugCharSequenceAndThrowableInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.debug(charSequence, throwable);
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
    public void testInfoCharSequenceActive() {
        cut.setLogLevel(Kind.NOTE);

        cut.info(charSequence);
        checkMessageCharSequence(Kind.NOTE);
    }

    @Test
    public void testInfoCharSequenceInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.info(charSequence);
        checkNoMessage();
    }

    @Test
    public void testInfoCharSequenceAndThrowableActive() {
        cut.setLogLevel(Kind.NOTE);

        cut.info(charSequence, throwable);
        checkMessageCharSequenceAndThrowable(Kind.NOTE);
    }

    @Test
    public void testInfoCharSequenceAndThrowableInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.info(charSequence, throwable);
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
    public void testWarnCharSequenceActive() {
        cut.setLogLevel(Kind.WARNING);

        cut.warn(charSequence);
        checkMessageCharSequence(Kind.WARNING);
    }

    @Test
    public void testWarnCharSequenceInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.warn(charSequence);
        checkNoMessage();
    }

    @Test
    public void testWarnCharSequenceAndThrowableActive() {
        cut.setLogLevel(Kind.WARNING);

        cut.warn(charSequence, throwable);
        checkMessageCharSequenceAndThrowable(Kind.WARNING);
    }

    @Test
    public void testWarnCharSequenceAndThrowableInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.warn(charSequence, throwable);
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
    public void testErrorCharSequenceActive() {
        cut.setLogLevel(Kind.ERROR);

        cut.error(charSequence);
        checkMessageCharSequence(Kind.ERROR);
    }

    @Test
    public void testErrorCharSequenceInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.error(charSequence);
        checkNoMessage();
    }

    @Test
    public void testErrorCharSequenceAndThrowableActive() {
        cut.setLogLevel(Kind.ERROR);

        cut.error(charSequence, throwable);
        checkMessageCharSequenceAndThrowable(Kind.ERROR);
    }

    @Test
    public void testErrorCharSequenceAndThrowableInactive() {
        cut.setLogLevel(Kind.MANDATORY_WARNING);

        cut.error(charSequence, throwable);
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


    private void checkMessageCharSequence(Kind kind) {
        verify(messager).printMessage(eq(kind), eq(charSequence));
        verify(messager, never()).printMessage(eq(kind), eq(THROWABLE_MESSAGE));
    }

    private void checkMessageCharSequenceAndThrowable(Kind kind) {
        verify(messager).printMessage(eq(kind), eq(charSequence));
        verify(messager).printMessage(eq(kind), eq(THROWABLE_MESSAGE));
    }

    private void checkMessageThrowable(Kind kind) {
        verify(messager, never()).printMessage(eq(kind), eq(charSequence));
        verify(messager).printMessage(eq(kind), eq(THROWABLE_MESSAGE));
    }

    private void checkNoMessage() {
        verify(messager, never()).printMessage(any(), anyString());
    }
}
