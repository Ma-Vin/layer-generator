package com.github.ma_vin.util.layer_generator.builder;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseAccessMapper;
import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.annotations.mapper.ExtendingAccessMapper;
import com.github.ma_vin.util.layer_generator.annotations.mapper.ExtendingTransportMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class MapperFactoryBuilderTest extends AbstractBuilderTest {
    private MapperFactoryBuilder cut;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        cut = new MapperFactoryBuilder() {
            @Override
            protected BufferedWriter createBufferedWriter(JavaFileObject javaFileObject) throws IOException {
                List<String> fileContent = new ArrayList<>();
                writtenFileContents.put(javaFileObject.getName(), fileContent);
                try {
                    // Assumption: after write is als a newLine statement
                    doAnswer(a -> fileContent.add(a.getArgument(0))).when(bufferedWriter).write(anyString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bufferedWriter;
            }
        };
        cut.init(processingEnv);
    }

    @AfterEach()
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testProcessOnlyBaseAccessMapper() {
        final String mapperPackageName = "com.github.ma_vin.mapper.test";

        TypeElement baseAccessMapperTypeElement = createTypeElementForAnnotationMock(BaseAccessMapper.class);
        TypeElement annotatedClassTypeElement = createTypeElementForAnnotatedBaseClassMock(BaseAccessMapper.class, "TestClassAccessMapper", mapperPackageName);

        when(roundEnv.getElementsAnnotatedWith(eq(baseAccessMapperTypeElement))).then(a -> Set.of(annotatedClassTypeElement));

        annotations.add(baseAccessMapperTypeElement);

        boolean result = cut.process(annotations, roundEnv);
        assertTrue(result, "The result should be true");
        assertEquals(1, writtenFileContents.size(), "Wrong number of written sources");
        assertTrue(writtenFileContents.containsKey(mapperPackageName + "." + MapperType.ACCESS.getFactoryClassName()), "The mapper object factory should be contained");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("package com.github.ma_vin.mapper.test;");
        expected.add("");
        expected.add("public class AccessMapperFactory {");
        expected.add("");
        expected.add("	private AccessMapperFactory() {");
        expected.add("	}");
        expected.add("");
        expected.add("	public static TestClassAccessMapper createTestClassAccessMapper() {");
        expected.add("		return new TestClassAccessMapper();");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        checkSingleFile(mapperPackageName + "." + MapperType.ACCESS.getFactoryClassName(), expected);
    }

    @Test
    public void testProcessOnlyExtendingAccessMapper() {
        TypeElement baseAccessMapperTypeElement = createTypeElementForAnnotationMock(BaseAccessMapper.class);
        TypeElement annotatedBaseClassTypeElement = createTypeElementForAnnotatedBaseClassMock(BaseAccessMapper.class, DUMMY_CLASS_NAME, DUMMY_PACKAGE_NAME);

        TypeElement extendingAccessMapperTypeElement = createTypeElementForAnnotationMock(ExtendingAccessMapper.class);
        TypeElement annotatedExtendingClassTypeElement = createTypeElementForAnnotatedExtendingClassMock(ExtendingAccessMapper.class, "TestClassAccessMapper", "com.github.ma_vin.mapper.test"
                , TypeKind.DECLARED, DUMMY_CLASS_NAME, DUMMY_PACKAGE_NAME);

        when(roundEnv.getElementsAnnotatedWith(eq(extendingAccessMapperTypeElement))).then(a -> Set.of(annotatedExtendingClassTypeElement));
        when(roundEnv.getElementsAnnotatedWith(eq(baseAccessMapperTypeElement))).then(a -> Set.of(annotatedBaseClassTypeElement));

        annotations.add(extendingAccessMapperTypeElement);
        annotations.add(baseAccessMapperTypeElement);

        boolean result = cut.process(annotations, roundEnv);
        assertTrue(result, "The result should be true");
        assertEquals(1, writtenFileContents.size(), "Wrong number of written sources");
        assertTrue(writtenFileContents.containsKey(DUMMY_PACKAGE_NAME + "." + MapperType.ACCESS.getFactoryClassName()), "The mapper object factory should be contained");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("package com.github.ma_vin.util.layer_generator.builder;");
        expected.add("");
        expected.add("import com.github.ma_vin.mapper.test.TestClassAccessMapper;");
        expected.add("");
        expected.add("public class AccessMapperFactory {");
        expected.add("");
        expected.add("	private AccessMapperFactory() {");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyBaseClass createDummyBaseClass() {");
        expected.add("		return new TestClassAccessMapper();");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        checkSingleFile(DUMMY_PACKAGE_NAME + "." + MapperType.ACCESS.getFactoryClassName(), expected);
    }

    @Test
    public void testProcessOnlyBaseTransportMapper() {
        final String mapperPackageName = "com.github.ma_vin.mapper.test";

        TypeElement baseTransportMapperTypeElement = createTypeElementForAnnotationMock(BaseTransportMapper.class);
        TypeElement annotatedClassTypeElement = createTypeElementForAnnotatedBaseClassMock(BaseTransportMapper.class, "TestClassTransportMapper", mapperPackageName);

        when(roundEnv.getElementsAnnotatedWith(eq(baseTransportMapperTypeElement))).then(a -> Set.of(annotatedClassTypeElement));

        annotations.add(baseTransportMapperTypeElement);

        boolean result = cut.process(annotations, roundEnv);
        assertTrue(result, "The result should be true");
        assertEquals(1, writtenFileContents.size(), "Wrong number of written sources");
        assertTrue(writtenFileContents.containsKey(mapperPackageName + "." + MapperType.TRANSPORT.getFactoryClassName()), "The mapper object factory should be contained");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("package com.github.ma_vin.mapper.test;");
        expected.add("");
        expected.add("public class TransportMapperFactory {");
        expected.add("");
        expected.add("	private TransportMapperFactory() {");
        expected.add("	}");
        expected.add("");
        expected.add("	public static TestClassTransportMapper createTestClassTransportMapper() {");
        expected.add("		return new TestClassTransportMapper();");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        checkSingleFile(mapperPackageName + "." + MapperType.TRANSPORT.getFactoryClassName(), expected);
    }

    @Test
    public void testProcessOnlyExtendingTransportMapper() {
        TypeElement baseTransportMapperTypeElement = createTypeElementForAnnotationMock(BaseTransportMapper.class);
        TypeElement annotatedBaseClassTypeElement = createTypeElementForAnnotatedBaseClassMock(BaseTransportMapper.class, DUMMY_CLASS_NAME, DUMMY_PACKAGE_NAME);

        TypeElement extendingTransportMapperTypeElement = createTypeElementForAnnotationMock(ExtendingTransportMapper.class);
        TypeElement annotatedExtendingClassTypeElement = createTypeElementForAnnotatedExtendingClassMock(ExtendingTransportMapper.class, "TestClassTransportMapper", "com.github.ma_vin.mapper.test"
                , TypeKind.DECLARED, DUMMY_CLASS_NAME, DUMMY_PACKAGE_NAME);

        when(roundEnv.getElementsAnnotatedWith(eq(extendingTransportMapperTypeElement))).then(a -> Set.of(annotatedExtendingClassTypeElement));
        when(roundEnv.getElementsAnnotatedWith(eq(baseTransportMapperTypeElement))).then(a -> Set.of(annotatedBaseClassTypeElement));

        annotations.add(extendingTransportMapperTypeElement);
        annotations.add(baseTransportMapperTypeElement);

        boolean result = cut.process(annotations, roundEnv);
        assertTrue(result, "The result should be true");
        assertEquals(1, writtenFileContents.size(), "Wrong number of written sources");
        assertTrue(writtenFileContents.containsKey(DUMMY_PACKAGE_NAME + "." + MapperType.TRANSPORT.getFactoryClassName()), "The mapper object factory should be contained");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("package com.github.ma_vin.util.layer_generator.builder;");
        expected.add("");
        expected.add("import com.github.ma_vin.mapper.test.TestClassTransportMapper;");
        expected.add("");
        expected.add("public class TransportMapperFactory {");
        expected.add("");
        expected.add("	private TransportMapperFactory() {");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyBaseClass createDummyBaseClass() {");
        expected.add("		return new TestClassTransportMapper();");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        checkSingleFile(DUMMY_PACKAGE_NAME + "." + MapperType.TRANSPORT.getFactoryClassName(), expected);
    }
}
