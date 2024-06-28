package de.ma_vin.util.layer.generator.config.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ma_vin.util.layer.generator.config.elements.Config;
import com.github.ma_vin.util.layer_generator.logging.Log4jLogImpl;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.xml.sax.SAXException;
import org.yaml.snakeyaml.Yaml;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * {@link ConfigFileLoader} is the class under test
 */
public class ConfigFileLoaderTest {

    public static final String DEFAULT_FILE_BASE_NAME = "testFile";

    private ConfigFileLoader cut;

    private AutoCloseable openMocks;

    @Mock
    private File configFile;
    @Mock
    private File schemaFile;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private FileReader fileReader;
    @Mock
    private Config config;
    @Mock
    private JAXBContext jaxbContext;
    @Mock
    private SchemaFactory schemaFactory;
    @Mock
    private Unmarshaller unmarshaller;
    @Mock
    private Schema schema;
    @Mock
    private Yaml yaml;
    @Mock
    private FileInputStream fileInputStream;


    private boolean throwFileNotFoundException;

    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);
        cut = new ConfigFileLoader(configFile, schemaFile, new Log4jLogImpl()) {
            @Override
            protected ObjectMapper createObjectMapper() {
                return objectMapper;
            }

            @Override
            protected FileReader createFileReader(File file) throws FileNotFoundException {
                if (throwFileNotFoundException) {
                    throw new FileNotFoundException("TestException");
                }
                return fileReader;
            }

            @Override
            protected Yaml createYaml() {
                return yaml;
            }

            @Override
            protected FileInputStream createFileInputStream(File file) throws FileNotFoundException {
                if (throwFileNotFoundException) {
                    throw new FileNotFoundException("TestException");
                }
                return fileInputStream;
            }
        };

        throwFileNotFoundException = false;

    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }


    @DisplayName("Load json config file")
    @Test
    public void testLoadJson() throws IOException {
        when(configFile.getName()).thenReturn(DEFAULT_FILE_BASE_NAME + ".json");
        when(objectMapper.readValue(eq(configFile), eq(Config.class))).thenReturn(config);

        Optional<Config> result = cut.load();

        assertTrue(result.isPresent(), "There should be a result");
        assertEquals(config, result.get(), "Wrong config as result");

        verify(objectMapper).readValue(eq(configFile), eq(Config.class));
    }

    @DisplayName("IOException while reading json config file")
    @Test
    public void testLoadJsonIOException() throws IOException {
        when(configFile.getName()).thenReturn(DEFAULT_FILE_BASE_NAME + ".json");
        when(objectMapper.readValue(eq(configFile), eq(Config.class))).thenThrow(new IOException("TestException"));

        Optional<Config> result = cut.load();

        assertTrue(result.isEmpty(), "There should not be any result");

        verify(objectMapper).readValue(eq(configFile), eq(Config.class));
    }


    @DisplayName("Load xml config file with schema")
    @Test
    public void testLoadXml() {
        try (MockedStatic<JAXBContext> jaxbContextMockedStatic = Mockito.mockStatic(JAXBContext.class);
             MockedStatic<SchemaFactory> schemaFactoryMockedStatic = Mockito.mockStatic(SchemaFactory.class)) {

            mockLoadXmlDefault(jaxbContextMockedStatic, schemaFactoryMockedStatic);

            Optional<Config> result = cut.load();

            assertTrue(result.isPresent(), "There should be a result");
            assertEquals(config, result.get(), "Wrong config as result");

            verify(unmarshaller).setSchema(eq(schema));
            verify(unmarshaller).unmarshal(eq(fileReader));

        } catch (JAXBException | SAXException e) {
            fail(e);
        }
    }

    @DisplayName("Load xml config file without schema")
    @Test
    public void testLoadXmlWithoutSchema() {
        cut = new ConfigFileLoader(configFile, new Log4jLogImpl()) {
            @Override
            protected FileReader createFileReader(File file) {
                return fileReader;
            }
        };

        try (MockedStatic<JAXBContext> jaxbContextMockedStatic = Mockito.mockStatic(JAXBContext.class);
             MockedStatic<SchemaFactory> schemaFactoryMockedStatic = Mockito.mockStatic(SchemaFactory.class)) {

            mockLoadXmlDefault(jaxbContextMockedStatic, schemaFactoryMockedStatic);

            Optional<Config> result = cut.load();

            assertTrue(result.isPresent(), "There should be a result");
            assertEquals(config, result.get(), "Wrong config as result");

            verify(unmarshaller, never()).setSchema(eq(schema));
            verify(unmarshaller).unmarshal(eq(fileReader));

        } catch (JAXBException | SAXException e) {
            fail(e);
        }
    }

    @DisplayName("FileNotFoundException while reading xml config file")
    @Test
    public void testLoadXmlFileNotFoundException() {
        throwFileNotFoundException = true;

        try (MockedStatic<JAXBContext> jaxbContextMockedStatic = Mockito.mockStatic(JAXBContext.class);
             MockedStatic<SchemaFactory> schemaFactoryMockedStatic = Mockito.mockStatic(SchemaFactory.class)) {

            mockLoadXmlDefault(jaxbContextMockedStatic, schemaFactoryMockedStatic);

            Optional<Config> result = cut.load();

            assertTrue(result.isEmpty(), "There should not be any result");

            verify(unmarshaller).setSchema(eq(schema));
            verify(unmarshaller, never()).unmarshal(eq(fileReader));

        } catch (JAXBException | SAXException e) {
            fail(e);
        }
    }

    @DisplayName("JAXBException while reading xml config file")
    @Test
    public void testLoadXmlJAXBException() {
        try (MockedStatic<JAXBContext> jaxbContextMockedStatic = Mockito.mockStatic(JAXBContext.class);
             MockedStatic<SchemaFactory> schemaFactoryMockedStatic = Mockito.mockStatic(SchemaFactory.class)) {

            mockLoadXmlDefault(jaxbContextMockedStatic, schemaFactoryMockedStatic);
            jaxbContextMockedStatic.when(() -> JAXBContext.newInstance(eq(Config.class))).thenThrow(new JAXBException("TestException"));

            Optional<Config> result = cut.load();

            assertTrue(result.isEmpty(), "There should not be any result");

            verify(unmarshaller, never()).setSchema(eq(schema));
            verify(unmarshaller, never()).unmarshal(eq(fileReader));

        } catch (JAXBException | SAXException e) {
            fail(e);
        }
    }

    @DisplayName("SAXException while reading xml config file")
    @Test
    public void testLoadXmlSAXException() {
        try (MockedStatic<JAXBContext> jaxbContextMockedStatic = Mockito.mockStatic(JAXBContext.class);
             MockedStatic<SchemaFactory> schemaFactoryMockedStatic = Mockito.mockStatic(SchemaFactory.class)) {

            mockLoadXmlDefault(jaxbContextMockedStatic, schemaFactoryMockedStatic);
            when(schemaFactory.newSchema(eq(schemaFile))).thenThrow(new SAXException("TestException"));

            Optional<Config> result = cut.load();

            assertTrue(result.isEmpty(), "There should not be any result");

            verify(unmarshaller, never()).setSchema(eq(schema));
            verify(unmarshaller, never()).unmarshal(eq(fileReader));

        } catch (JAXBException | SAXException e) {
            fail(e);
        }
    }

    private void mockLoadXmlDefault(MockedStatic<JAXBContext> jaxbContextMockedStatic, MockedStatic<SchemaFactory> schemaFactoryMockedStatic) throws JAXBException, SAXException {
        when(configFile.getName()).thenReturn(DEFAULT_FILE_BASE_NAME + ".xml");

        jaxbContextMockedStatic.when(() -> JAXBContext.newInstance(eq(Config.class))).thenReturn(jaxbContext);
        schemaFactoryMockedStatic.when(() -> SchemaFactory.newInstance(anyString())).thenReturn(schemaFactory);

        when(jaxbContext.createUnmarshaller()).thenReturn(unmarshaller);
        when(schemaFactory.newSchema(eq(schemaFile))).thenReturn(schema);
        when(unmarshaller.unmarshal(eq(fileReader))).thenReturn(config);
    }


    @DisplayName("Load yaml config file")
    @Test
    public void testLoadYaml() {
        when(configFile.getName()).thenReturn(DEFAULT_FILE_BASE_NAME + ".yaml");
        when(yaml.load(eq(fileInputStream))).thenReturn(config);

        Optional<Config> result = cut.load();

        assertTrue(result.isPresent(), "There should be a result");
        assertEquals(config, result.get(), "Wrong config as result");

        verify(yaml).load(eq(fileInputStream));
    }

    @DisplayName("FileNotFoundException while reading yaml config file")
    @Test
    public void testLoadYamlFileNotFoundException() {
        throwFileNotFoundException = true;
        when(configFile.getName()).thenReturn(DEFAULT_FILE_BASE_NAME + ".yaml");
        when(yaml.load(eq(fileInputStream))).thenReturn(config);

        Optional<Config> result = cut.load();

        assertTrue(result.isEmpty(), "There should not be any result");

        verify(yaml, never()).load(eq(fileInputStream));
    }


    @DisplayName("Load wrong file type")
    @Test
    public void testLoadInvalidFileType() {
        when(configFile.getName()).thenReturn(DEFAULT_FILE_BASE_NAME + ".txt");

        assertFalse(cut.load().isPresent(), "A wrong file type should not be loaded");
    }

    @DisplayName("Load file without file ending")
    @Test
    public void testLoadWithoutFileEnding() {
        when(configFile.getName()).thenReturn(DEFAULT_FILE_BASE_NAME);

        assertFalse(cut.load().isPresent(), "A file without file ending should not be loaded");
    }
}
