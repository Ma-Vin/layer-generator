package de.ma_vin.util.layer.generator.config.loader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.logging.Log4jLogImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

/**
 * {@link ConfigLoader} is the class under test
 */
public class ConfigLoaderTest {

    private AutoCloseable openMocks;

    @Mock
    private ConfigFileLoader configFileLoader;
    @Mock
    private Config config;
    @Mock
    private AbstractCompleter completer;


    private ConfigLoader cut;


    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);
        cut = new ConfigLoader(configFileLoader, new Log4jLogImpl());

        when(configFileLoader.load()).thenReturn(Optional.of(config));
        when(config.isValid(anyList())).thenReturn(Boolean.TRUE);
        when(completer.getExecutionPriority()).thenReturn(1);
        when(completer.complete(eq(config))).thenReturn(Boolean.TRUE);

        assertEquals(7, cut.getCompleter().size(), "Wrong number of completer");

        cut.getCompleter().clear();
        cut.getCompleter().add(completer);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }


    @DisplayName("Load successful")
    @Test
    public void testLoadSuccessful() {
        assertTrue(cut.load(), "The result should be true");

        verify(configFileLoader).load();
        verify(config).isValid(anyList());
        verify(completer).complete(eq(config));
    }

    @DisplayName("Fail to read config file")
    @Test
    public void testLoadFailedToRead() {
        when(configFileLoader.load()).thenReturn(Optional.empty());

        assertFalse(cut.load(), "The result should be false");

        verify(configFileLoader).load();
        verify(config, never()).isValid(anyList());
        verify(completer, never()).complete(any());
    }

    @DisplayName("Invalid config")
    @Test
    public void testLoadInvalidConfig() {
        when(config.isValid(anyList())).thenReturn(Boolean.FALSE);

        assertFalse(cut.load(), "The result should be false");

        verify(configFileLoader).load();
        verify(config).isValid(anyList());
        verify(completer, never()).complete(any());
    }

    @DisplayName("Completion was unsuccessful")
    @Test
    public void testLoadFailedToComplete() {
        when(completer.complete(eq(config))).thenReturn(Boolean.FALSE);

        assertFalse(cut.load(), "The result should be false");

        verify(configFileLoader).load();
        verify(config).isValid(anyList());
        verify(completer).complete(eq(config));
    }
}
