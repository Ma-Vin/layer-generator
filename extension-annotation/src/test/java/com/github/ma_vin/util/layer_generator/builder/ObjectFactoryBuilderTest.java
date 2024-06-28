package com.github.ma_vin.util.layer_generator.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.github.ma_vin.util.layer_generator.annotations.model.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

@Log4j2
public class ObjectFactoryBuilderTest extends AbstractBuilderTest {

    private ObjectFactoryBuilder cut;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        cut = new ObjectFactoryBuilder() {
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
    public void testProcessOnlyBaseDao() {
        final String daoPackageName = "com.github.ma_vin.dao";

        TypeElement baseDaoTypeElement = createTypeElementForAnnotationMock(BaseDao.class);
        TypeElement annotatedClassTypeElement = createTypeElementForAnnotatedBaseClassMock(BaseDao.class, "TestClassDao", "com.github.ma_vin.dao.test"
                , (s, c) -> when(c.value()).thenReturn(s), daoPackageName);

        when(roundEnv.getElementsAnnotatedWith(eq(baseDaoTypeElement))).then(a -> Set.of(annotatedClassTypeElement));

        annotations.add(baseDaoTypeElement);

        boolean result = cut.process(annotations, roundEnv);
        assertTrue(result, "The result should be true");
        assertEquals(1, writtenFileContents.size(), "Wrong number of written sources");
        assertTrue(writtenFileContents.containsKey(daoPackageName + "." + ModelType.DAO.getFactoryClassName()), "The dao object factory should be contained");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("package com.github.ma_vin.dao;");
        expected.add("");
        expected.add("import com.github.ma_vin.dao.test.TestClassDao;");
        expected.add("");
        expected.add("public class DaoObjectFactory {");
        expected.add("");
        expected.add("	private DaoObjectFactory() {");
        expected.add("	}");
        expected.add("");
        expected.add("	public static TestClassDao createTestClassDao() {");
        expected.add("		return new TestClassDao();");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        checkSingleFile(daoPackageName + "." + ModelType.DAO.getFactoryClassName(), expected);
    }

    @Test
    public void testProcessOnlyExtendingDao() {
        final String daoPackageName = "com.github.ma_vin.dao";

        TypeElement baseDaoTypeElement = createTypeElementForAnnotationMock(BaseDao.class);
        TypeElement annotatedBaseClassTypeElement = createTypeElementForAnnotatedBaseClassMock(BaseDao.class, DUMMY_CLASS_NAME, DUMMY_PACKAGE_NAME
                , (s, c) -> when(c.value()).thenReturn(s), daoPackageName);

        TypeElement extendingDaoTypeElement = createTypeElementForAnnotationMock(ExtendingDao.class);
        TypeElement annotatedExtendingClassTypeElement = createTypeElementForAnnotatedExtendingClassMock(ExtendingDao.class, "TestClassDao", "com.github.ma_vin.dao.test"
                , TypeKind.DECLARED, DUMMY_CLASS_NAME, DUMMY_PACKAGE_NAME);

        when(roundEnv.getElementsAnnotatedWith(eq(extendingDaoTypeElement))).then(a -> Set.of(annotatedExtendingClassTypeElement));
        when(roundEnv.getElementsAnnotatedWith(eq(baseDaoTypeElement))).then(a -> Set.of(annotatedBaseClassTypeElement));

        annotations.add(extendingDaoTypeElement);
        annotations.add(baseDaoTypeElement);

        boolean result = cut.process(annotations, roundEnv);
        assertTrue(result, "The result should be true");
        assertEquals(1, writtenFileContents.size(), "Wrong number of written sources");
        assertTrue(writtenFileContents.containsKey(daoPackageName + "." + ModelType.DAO.getFactoryClassName()), "The dao object factory should be contained");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("package com.github.ma_vin.dao;");
        expected.add("");
        expected.add("import com.github.ma_vin.dao.test.TestClassDao;");
        expected.add("import com.github.ma_vin.util.layer_generator.builder.DummyBaseClass;");
        expected.add("");
        expected.add("public class DaoObjectFactory {");
        expected.add("");
        expected.add("	private DaoObjectFactory() {");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyBaseClass createDummyBaseClass() {");
        expected.add("		return new TestClassDao();");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        checkSingleFile(daoPackageName + "." + ModelType.DAO.getFactoryClassName(), expected);
    }

    @Test
    public void testProcessOnlyBaseDomain() {
        final String domainPackageName = "com.github.ma_vin.domain";

        TypeElement baseDomainTypeElement = createTypeElementForAnnotationMock(BaseDomain.class);
        TypeElement annotatedClassTypeElement = createTypeElementForAnnotatedBaseClassMock(BaseDomain.class, "TestClassDomain", "com.github.ma_vin.domain.test"
                , (s, c) -> when(c.value()).thenReturn(s), domainPackageName);

        when(roundEnv.getElementsAnnotatedWith(eq(baseDomainTypeElement))).then(a -> Set.of(annotatedClassTypeElement));

        annotations.add(baseDomainTypeElement);

        boolean result = cut.process(annotations, roundEnv);
        assertTrue(result, "The result should be true");
        assertEquals(1, writtenFileContents.size(), "Wrong number of written sources");
        assertTrue(writtenFileContents.containsKey(domainPackageName + "." + ModelType.DOMAIN.getFactoryClassName()), "The dao object factory should be contained");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("package com.github.ma_vin.domain;");
        expected.add("");
        expected.add("import com.github.ma_vin.domain.test.TestClassDomain;");
        expected.add("");
        expected.add("public class DomainObjectFactory {");
        expected.add("");
        expected.add("	private DomainObjectFactory() {");
        expected.add("	}");
        expected.add("");
        expected.add("	public static TestClassDomain createTestClassDomain() {");
        expected.add("		return new TestClassDomain();");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        checkSingleFile(domainPackageName + "." + ModelType.DOMAIN.getFactoryClassName(), expected);
    }

    @Test
    public void testProcessOnlyExtendingDomain() {
        final String domainPackageName = "com.github.ma_vin.domain";

        TypeElement baseDomainTypeElement = createTypeElementForAnnotationMock(BaseDomain.class);
        TypeElement annotatedBaseClassTypeElement = createTypeElementForAnnotatedBaseClassMock(BaseDomain.class, DUMMY_CLASS_NAME, DUMMY_PACKAGE_NAME
                , (s, c) -> when(c.value()).thenReturn(s), domainPackageName);

        TypeElement extendingDomainTypeElement = createTypeElementForAnnotationMock(ExtendingDomain.class);
        TypeElement annotatedExtendingClassTypeElement = createTypeElementForAnnotatedExtendingClassMock(ExtendingDomain.class, "TestClassDomain", "com.github.ma_vin.domain.test"
                , TypeKind.DECLARED, DUMMY_CLASS_NAME, DUMMY_PACKAGE_NAME);

        when(roundEnv.getElementsAnnotatedWith(eq(extendingDomainTypeElement))).then(a -> Set.of(annotatedExtendingClassTypeElement));
        when(roundEnv.getElementsAnnotatedWith(eq(baseDomainTypeElement))).then(a -> Set.of(annotatedBaseClassTypeElement));

        annotations.add(extendingDomainTypeElement);
        annotations.add(baseDomainTypeElement);

        boolean result = cut.process(annotations, roundEnv);
        assertTrue(result, "The result should be true");
        assertEquals(1, writtenFileContents.size(), "Wrong number of written sources");
        assertTrue(writtenFileContents.containsKey(domainPackageName + "." + ModelType.DOMAIN.getFactoryClassName()), "The dao object factory should be contained");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("package com.github.ma_vin.domain;");
        expected.add("");
        expected.add("import com.github.ma_vin.domain.test.TestClassDomain;");
        expected.add("import com.github.ma_vin.util.layer_generator.builder.DummyBaseClass;");
        expected.add("");
        expected.add("public class DomainObjectFactory {");
        expected.add("");
        expected.add("	private DomainObjectFactory() {");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyBaseClass createDummyBaseClass() {");
        expected.add("		return new TestClassDomain();");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        checkSingleFile(domainPackageName + "." + ModelType.DOMAIN.getFactoryClassName(), expected);
    }


    @Test
    public void testProcessOnlyBaseDto() {
        final String dtoPackageName = "com.github.ma_vin.dto";

        TypeElement baseDtoTypeElement = createTypeElementForAnnotationMock(BaseDto.class);
        TypeElement annotatedClassTypeElement = createTypeElementForAnnotatedBaseClassMock(BaseDto.class, "TestClassDto", "com.github.ma_vin.dto.test"
                , (s, c) -> when(c.value()).thenReturn(s), dtoPackageName);

        when(roundEnv.getElementsAnnotatedWith(eq(baseDtoTypeElement))).then(a -> Set.of(annotatedClassTypeElement));

        annotations.add(baseDtoTypeElement);

        boolean result = cut.process(annotations, roundEnv);
        assertTrue(result, "The result should be true");
        assertEquals(1, writtenFileContents.size(), "Wrong number of written sources");
        assertTrue(writtenFileContents.containsKey(dtoPackageName + "." + ModelType.DTO.getFactoryClassName()), "The dao object factory should be contained");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("package com.github.ma_vin.dto;");
        expected.add("");
        expected.add("import com.github.ma_vin.dto.test.TestClassDto;");
        expected.add("");
        expected.add("public class DtoObjectFactory {");
        expected.add("");
        expected.add("	private DtoObjectFactory() {");
        expected.add("	}");
        expected.add("");
        expected.add("	public static TestClassDto createTestClassDto() {");
        expected.add("		return new TestClassDto();");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        checkSingleFile(dtoPackageName + "." + ModelType.DTO.getFactoryClassName(), expected);
    }

    @Test
    public void testProcessOnlyExtendingDto() {
        final String dtoPackageName = "com.github.ma_vin.dto";

        TypeElement baseDtoTypeElement = createTypeElementForAnnotationMock(BaseDto.class);
        TypeElement annotatedBaseClassTypeElement = createTypeElementForAnnotatedBaseClassMock(BaseDto.class, DUMMY_CLASS_NAME, DUMMY_PACKAGE_NAME
                , (s, c) -> when(c.value()).thenReturn(s), dtoPackageName);

        TypeElement extendingDtoTypeElement = createTypeElementForAnnotationMock(ExtendingDto.class);
        TypeElement annotatedExtendingClassTypeElement = createTypeElementForAnnotatedExtendingClassMock(ExtendingDto.class, "TestClassDto", "com.github.ma_vin.dto.test"
                , TypeKind.DECLARED, DUMMY_CLASS_NAME, DUMMY_PACKAGE_NAME);

        when(roundEnv.getElementsAnnotatedWith(eq(extendingDtoTypeElement))).then(a -> Set.of(annotatedExtendingClassTypeElement));
        when(roundEnv.getElementsAnnotatedWith(eq(baseDtoTypeElement))).then(a -> Set.of(annotatedBaseClassTypeElement));

        annotations.add(extendingDtoTypeElement);
        annotations.add(baseDtoTypeElement);

        boolean result = cut.process(annotations, roundEnv);
        assertTrue(result, "The result should be true");
        assertEquals(1, writtenFileContents.size(), "Wrong number of written sources");
        assertTrue(writtenFileContents.containsKey(dtoPackageName + "." + ModelType.DTO.getFactoryClassName()), "The dao object factory should be contained");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("package com.github.ma_vin.dto;");
        expected.add("");
        expected.add("import com.github.ma_vin.dto.test.TestClassDto;");
        expected.add("import com.github.ma_vin.util.layer_generator.builder.DummyBaseClass;");
        expected.add("");
        expected.add("public class DtoObjectFactory {");
        expected.add("");
        expected.add("	private DtoObjectFactory() {");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyBaseClass createDummyBaseClass() {");
        expected.add("		return new TestClassDto();");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        checkSingleFile(dtoPackageName + "." + ModelType.DTO.getFactoryClassName(), expected);
    }
}
