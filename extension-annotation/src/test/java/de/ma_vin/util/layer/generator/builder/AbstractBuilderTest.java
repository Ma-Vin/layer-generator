package de.ma_vin.util.layer.generator.builder;

import com.github.ma_vin.util.layer_generator.sources.TestUtil;
import lombok.extern.log4j.Log4j2;
import org.mockito.Mock;
import org.opentest4j.AssertionFailedError;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@Log4j2
public abstract class AbstractBuilderTest {

    public static final String DUMMY_CLASS_NAME = "DummyBaseClass";
    public static final String DUMMY_PACKAGE_NAME = "de.ma_vin.util.layer.generator.builder";

    protected AutoCloseable openMocks;

    @Mock
    protected RoundEnvironment roundEnv;
    @Mock
    protected ProcessingEnvironment processingEnv;
    @Mock
    protected Types utilTypes;
    @Mock
    protected Filer filer;
    @Mock
    protected JavaFileObject javaFileObject;
    @Mock
    protected BufferedWriter bufferedWriter;

    protected Set<TypeElement> annotations = new HashSet<>();

    protected Map<String, List<String>> writtenFileContents = new HashMap<>();

    protected void setUp() {
        writtenFileContents.clear();
        annotations.clear();
        openMocks = openMocks(this);
        initDefaultMocks();
    }

    protected void tearDown() throws Exception {
        openMocks.close();
    }

    protected void initDefaultMocks() {
        when(processingEnv.getFiler()).thenReturn(filer);
        when(processingEnv.getTypeUtils()).thenReturn(utilTypes);
        try {
            when(filer.createSourceFile(any())).then(a -> {
                when(javaFileObject.getName()).thenReturn(a.getArgument(0));
                return javaFileObject;
            });
            when(filer.createSourceFile(any(), any())).then(a -> {
                when(javaFileObject.getName()).thenReturn(a.getArgument(0));
                return javaFileObject;
            });
        } catch (IOException e) {
            fail("Exception while create createClassFile mock return: " + e.getMessage());
        }
    }

    protected void checkSingleFile(String fileName, List<String> expectedLines) {
        assertEquals(1, writtenFileContents.size(), "Wrong number of files");
        assertTrue(writtenFileContents.containsKey(fileName));

        if (expectedLines.size() != writtenFileContents.get(fileName).size()) {
            for (int i = 0; i < expectedLines.size() && i < writtenFileContents.get(fileName).size(); i++) {
                if (!expectedLines.get(i).equals(writtenFileContents.get(fileName).get(i))) {
                    log.error("First Diff Value at line " + i);
                    log.error("Expected: " + expectedLines.get(i));
                    log.error("Actual:   " + writtenFileContents.get(fileName).get(i));
                    log.error("-------------");
                    break;
                }
            }
        }
        try {
            TestUtil.checkList(expectedLines, writtenFileContents.get(fileName));
        } catch (AssertionFailedError e) {
            logFileContents();
            throw e;
        }
    }

    protected void logFileContents() {
        writtenFileContents.entrySet().forEach(entry -> {
            log.error("File {} was written:", entry.getKey());
            log.error("");
            for (int i = 0; i < entry.getValue().size(); i++) {
                log.error("{}\t{}", i, entry.getValue().get(i));
            }
            log.error("-------------");
        });
    }


    protected <T> TypeElement createTypeElementForAnnotationMock(Class<T> annotation) {
        TypeElement typeElement = mock(TypeElement.class);
        Name simpleName = mock(Name.class);
        when(typeElement.getSimpleName()).thenReturn(simpleName);
        when(simpleName.toString()).thenReturn(annotation.getSimpleName());
        return typeElement;
    }

    protected <T> TypeElement createTypeElementForAnnotatedBaseClassMock(Class<T> annotationClass, String className, String packageName) {

        TypeElement annotatedClassTypeElement = mock(TypeElement.class);

        Name annotatedClassSimpleName = mock(Name.class);
        Name annotatedClassQualifiedName = mock(Name.class);
        T annotation = mock(annotationClass);

        when(annotatedClassTypeElement.getSimpleName()).thenReturn(annotatedClassSimpleName);
        when(annotatedClassTypeElement.getQualifiedName()).thenReturn(annotatedClassQualifiedName);
        when(annotatedClassSimpleName.toString()).thenReturn(className);
        when(annotatedClassQualifiedName.toString()).thenReturn(packageName + "." + className);
        when(annotatedClassTypeElement.getAnnotation(any())).thenReturn(annotation);

        return annotatedClassTypeElement;
    }

    protected <T> TypeElement createTypeElementForAnnotatedBaseClassMock(Class<T> annotationClass, String className, String packageName
            , ValueBaseAnnotationMock<T> valueMock, String factoryPackage) {

        TypeElement annotatedClassTypeElement = mock(TypeElement.class);

        Name annotatedClassSimpleName = mock(Name.class);
        Name annotatedClassQualifiedName = mock(Name.class);
        T annotation = mock(annotationClass);

        when(annotatedClassTypeElement.getSimpleName()).thenReturn(annotatedClassSimpleName);
        when(annotatedClassTypeElement.getQualifiedName()).thenReturn(annotatedClassQualifiedName);
        when(annotatedClassSimpleName.toString()).thenReturn(className);
        when(annotatedClassQualifiedName.toString()).thenReturn(packageName + "." + className);
        when(annotatedClassTypeElement.getAnnotation(any())).thenReturn(annotation);
        valueMock.mockValue(factoryPackage, annotation);

        return annotatedClassTypeElement;
    }

    protected <T> TypeElement createTypeElementForAnnotatedExtendingClassMock(Class<T> annotationClass, String className
            , String packageName, TypeKind superTypeKind, String superClassName, String superPackageName) {

        TypeElement annotatedClassTypeElement = mock(TypeElement.class);
        TypeElement superClassTypeElement = mock(TypeElement.class);

        TypeMirror superTypeMirror = mock(TypeMirror.class);

        Name annotatedClassSimpleName = mock(Name.class);
        Name annotatedClassQualifiedName = mock(Name.class);
        Name superClassSimpleName = mock(Name.class);
        Name superClassQualifiedName = mock(Name.class);

        T annotation = mock(annotationClass);

        when(annotatedClassTypeElement.getSimpleName()).thenReturn(annotatedClassSimpleName);
        when(annotatedClassTypeElement.getQualifiedName()).thenReturn(annotatedClassQualifiedName);
        when(annotatedClassSimpleName.toString()).thenReturn(className);
        when(annotatedClassQualifiedName.toString()).thenReturn(packageName + "." + className);
        when(annotatedClassTypeElement.getAnnotation(any())).thenReturn(annotation);

        when(superClassSimpleName.toString()).thenReturn(superClassName);
        when(superClassQualifiedName.toString()).thenReturn(superPackageName + "." + superClassName);
        when(superClassTypeElement.getSimpleName()).thenReturn(superClassSimpleName);
        when(superClassTypeElement.getQualifiedName()).thenReturn(superClassQualifiedName);

        when(annotatedClassTypeElement.getSuperclass()).thenReturn(superTypeMirror);
        when(superTypeMirror.getKind()).thenReturn(superTypeKind);
        when(utilTypes.asElement(eq(superTypeMirror))).thenReturn(superClassTypeElement);

        return annotatedClassTypeElement;
    }

    @FunctionalInterface
    protected interface ValueBaseAnnotationMock<T> {
        void mockValue(String value, T annotation);
    }

}
