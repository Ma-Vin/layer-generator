package com.github.ma_vin.util.layer_generator.processor;

import com.github.ma_vin.util.layer_generator.annotations.LayerGenerator;
import com.github.ma_vin.util.layer_generator.generator.generator.CommonGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * {@link LayerGeneratorProcessor} is the class under test
 */
public class LayerGeneratorProcessorTest {

    public static final String DEFAULT_PATH = "some/path";
    public static final String DEFAULT_FILE_NAME = "test.xml";

    private AutoCloseable openMocks;


    private LayerGeneratorProcessor cut;
    @Mock
    private ProcessingEnvironment processingEnv;
    @Mock
    private Filer filer;
    @Mock
    private Messager messager;
    @Mock
    private FileObject fileObject;
    @Mock
    private URI uri;
    @Mock
    private RoundEnvironment roundEnv;
    @Mock
    private TypeElement annotation;
    @Mock
    private TypeElement classElement;
    @Mock
    private LayerGenerator layerGenerator;
    @Mock
    private CommonGenerator commonGenerator;

    private Set<TypeElement> annotations = new HashSet<>();
    private Set<Element> elementsWithAnnotation = new HashSet<>();

    @BeforeEach
    public void setUp() throws IOException {
        openMocks = openMocks(this);

        cut = new LayerGeneratorProcessor() {
            @Override
            protected CommonGenerator createCommonGenerator() {
                return commonGenerator;
            }
        };
        cut.init(processingEnv);

        when(processingEnv.getMessager()).thenReturn(messager);
        when(processingEnv.getFiler()).thenReturn(filer);
        when(filer.getResource(eq(StandardLocation.CLASS_OUTPUT), any(), eq(DEFAULT_PATH + "/" + DEFAULT_FILE_NAME))).thenReturn(fileObject);
        when(fileObject.toUri()).thenReturn(uri);
        when(uri.getPath()).thenReturn(DEFAULT_PATH + "/" + DEFAULT_FILE_NAME);

        when(layerGenerator.modelDefinitionDirectory()).thenReturn(DEFAULT_PATH);
        when(layerGenerator.modelDefinitionFilename()).thenReturn(DEFAULT_FILE_NAME);
        when(layerGenerator.logLevel()).thenReturn(Diagnostic.Kind.WARNING);
        when(layerGenerator.generateDto()).thenReturn(Boolean.TRUE);
        when(layerGenerator.generateDao()).thenReturn(Boolean.TRUE);
        when(layerGenerator.generateDomain()).thenReturn(Boolean.TRUE);

        annotations.clear();
        annotations.add(annotation);
        elementsWithAnnotation.clear();
        elementsWithAnnotation.add(classElement);

        when(roundEnv.getElementsAnnotatedWith(eq(annotation))).then(a -> elementsWithAnnotation);
        when(classElement.getAnnotation(eq(LayerGenerator.class))).thenReturn(layerGenerator);

        when(commonGenerator.loadConfig()).thenReturn(Boolean.TRUE);
        when(commonGenerator.generate(eq(processingEnv))).thenReturn(Boolean.TRUE);
        doAnswer(a -> {
            when(commonGenerator.getModelFile()).thenReturn(a.getArgument(0));
            return null;
        }).when(commonGenerator).setModelFile(any());
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @DisplayName("Load and generate successful sources")
    @Test
    public void testProcessLoadAndGenerateSuccessful() {
        boolean result = cut.process(annotations, roundEnv);

        assertTrue(result, "The result should be true");

        verify(commonGenerator).setGenerateDto(eq(true));
        verify(commonGenerator).setGenerateDao(eq(true));
        verify(commonGenerator).setGenerateDomain(eq(true));
        verify(commonGenerator).setModelFile(any());
        verify(commonGenerator).loadConfig();
        verify(commonGenerator).generate(eq(processingEnv));
        verify(commonGenerator, never()).generate();
    }

    @DisplayName("Load and generate successful sources with separator at directory")
    @Test
    public void testProcessLoadAndGenerateSuccessfulDirectoryWithSeparator() {
        when(layerGenerator.modelDefinitionDirectory()).thenReturn(DEFAULT_PATH + "/");

        boolean result = cut.process(annotations, roundEnv);

        assertTrue(result, "The result should be true");

        verify(commonGenerator).setGenerateDto(eq(true));
        verify(commonGenerator).setGenerateDao(eq(true));
        verify(commonGenerator).setGenerateDomain(eq(true));
        verify(commonGenerator).setModelFile(any());
        verify(commonGenerator).loadConfig();
        verify(commonGenerator).generate(eq(processingEnv));
        verify(commonGenerator, never()).generate();
    }


    @DisplayName("Load and generate successful sources without directory")
    @Test
    public void testProcessLoadAndGenerateSuccessfulWithoutDirectory() throws IOException {
        when(layerGenerator.modelDefinitionDirectory()).thenReturn(null);
        when(filer.getResource(eq(StandardLocation.CLASS_OUTPUT), any(), eq(DEFAULT_FILE_NAME))).thenReturn(fileObject);

        boolean result = cut.process(annotations, roundEnv);

        assertTrue(result, "The result should be true");

        verify(commonGenerator).setGenerateDto(eq(true));
        verify(commonGenerator).setGenerateDao(eq(true));
        verify(commonGenerator).setGenerateDomain(eq(true));
        verify(commonGenerator).setModelFile(any());
        verify(commonGenerator).loadConfig();
        verify(commonGenerator).generate(eq(processingEnv));
        verify(commonGenerator, never()).generate();
    }

    @DisplayName("Load and generate successful sources with empty directory")
    @Test
    public void testProcessLoadAndGenerateSuccessfulWithEmptyDirectory() throws IOException {
        when(layerGenerator.modelDefinitionDirectory()).thenReturn("");
        when(filer.getResource(eq(StandardLocation.CLASS_OUTPUT), any(), eq(DEFAULT_FILE_NAME))).thenReturn(fileObject);

        boolean result = cut.process(annotations, roundEnv);

        assertTrue(result, "The result should be true");

        verify(commonGenerator).setGenerateDto(eq(true));
        verify(commonGenerator).setGenerateDao(eq(true));
        verify(commonGenerator).setGenerateDomain(eq(true));
        verify(commonGenerator).setModelFile(any());
        verify(commonGenerator).loadConfig();
        verify(commonGenerator).generate(eq(processingEnv));
        verify(commonGenerator, never()).generate();
    }

    @DisplayName("process with not supported type")
    @Test
    public void testProcessUnsupportedTypeElement() {
        elementsWithAnnotation.clear();
        elementsWithAnnotation.add(mock(Element.class));

        boolean result = cut.process(annotations, roundEnv);

        assertTrue(result, "The result should be true");

        verify(commonGenerator, never()).setGenerateDto(anyBoolean());
        verify(commonGenerator, never()).setGenerateDao(anyBoolean());
        verify(commonGenerator, never()).setGenerateDomain(anyBoolean());
        verify(commonGenerator, never()).setModelFile(any());
        verify(commonGenerator, never()).loadConfig();
        verify(commonGenerator, never()).generate(any());
        verify(commonGenerator, never()).generate();
    }

    @DisplayName("failed to get model file from resource")
    @Test
    public void testProcessFailedToGetModelFileResource() throws IOException {
        when(filer.getResource(eq(StandardLocation.CLASS_OUTPUT), any(), eq(DEFAULT_PATH + "/" + DEFAULT_FILE_NAME))).thenThrow(new IOException("TestException"));

        boolean result = cut.process(annotations, roundEnv);

        assertFalse(result, "The result should be false");

        verify(commonGenerator, never()).setGenerateDto(anyBoolean());
        verify(commonGenerator, never()).setGenerateDao(anyBoolean());
        verify(commonGenerator, never()).setGenerateDomain(anyBoolean());
        verify(commonGenerator, never()).setModelFile(any());
        verify(commonGenerator, never()).loadConfig();
        verify(commonGenerator, never()).generate(any());
        verify(commonGenerator, never()).generate();
    }

    @DisplayName("failed to load config from model file")
    @Test
    public void testProcessFailedToLoadConfigFromModelFile() {
        when(commonGenerator.loadConfig()).thenReturn(Boolean.FALSE);

        boolean result = cut.process(annotations, roundEnv);

        assertFalse(result, "The result should be false");

        verify(commonGenerator).setGenerateDto(eq(true));
        verify(commonGenerator).setGenerateDao(eq(true));
        verify(commonGenerator).setGenerateDomain(eq(true));
        verify(commonGenerator).setModelFile(any());
        verify(commonGenerator).loadConfig();
        verify(commonGenerator, never()).generate(any());
        verify(commonGenerator, never()).generate();
    }

    @DisplayName("failed to generate sources")
    @Test
    public void testProcessFailedToGenerate() {
        when(commonGenerator.generate(eq(processingEnv))).thenReturn(Boolean.FALSE);

        boolean result = cut.process(annotations, roundEnv);

        assertFalse(result, "The result should be false");

        verify(commonGenerator).setGenerateDto(eq(true));
        verify(commonGenerator).setGenerateDao(eq(true));
        verify(commonGenerator).setGenerateDomain(eq(true));
        verify(commonGenerator).setModelFile(any());
        verify(commonGenerator).loadConfig();
        verify(commonGenerator).generate(eq(processingEnv));
        verify(commonGenerator, never()).generate();
    }
}
