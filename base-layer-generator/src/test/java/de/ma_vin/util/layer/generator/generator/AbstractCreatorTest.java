package de.ma_vin.util.layer.generator.generator;

import de.ma_vin.util.layer.generator.config.elements.*;
import de.ma_vin.util.layer.generator.config.elements.fields.Field;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import de.ma_vin.util.layer.generator.sources.TestUtil;
import lombok.extern.log4j.Log4j2;
import org.mockito.Mock;
import org.opentest4j.AssertionFailedError;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@Log4j2
public class AbstractCreatorTest {
    public static final String BASE_PACKAGE = "de.test.package";
    public static final String ENTITY_NAME = "Dummy";

    protected AutoCloseable openMocks;

    @Mock
    protected Config config;
    @Mock
    protected File basePackageDir;
    @Mock
    protected Entity entity;
    @Mock
    protected Field field;
    @Mock
    protected Entity targetEntity;
    @Mock
    protected Reference targetReference;
    @Mock
    protected Grouping grouping;
    @Mock
    protected BufferedWriter bufferedWriter;
    @Mock
    protected ProcessingEnvironment processingEnv;
    @Mock
    protected Filer filer;
    @Mock
    protected JavaFileObject javaFileObject;

    protected Map<String, List<String>> writtenFileContents = new HashMap<>();

    public void setUp() {
        openMocks = openMocks(this);
        initDefaultMock();
        writtenFileContents.clear();
    }

    protected void initDefaultMock() {
        when(entity.getBaseName()).thenReturn(ENTITY_NAME);
        when(entity.getTableName()).thenReturn(ENTITY_NAME);
        when(entity.getDescription()).thenReturn("Dummy description");
        when(entity.getIdentificationPrefix()).thenReturn("DU");
        when(entity.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(entity.getGrouping()).thenReturn(grouping);
        when(entity.hasParent()).thenReturn(Boolean.FALSE);
        when(entity.hasNoParent()).thenReturn(Boolean.TRUE);
        when(entity.getGenIdIfDto()).thenReturn(Boolean.TRUE);

        when(field.getFieldName()).thenReturn("anyField");
        when(field.getType()).thenReturn("String");
        when(field.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(field.getParentEntity()).thenReturn(entity);

        when(grouping.getGroupingPackage()).thenReturn("group");

        setMockReturnsReference(targetReference, "TargetRef", "Target", null, null, Boolean.TRUE, Boolean.TRUE);
        setMockReturnsReference(targetReference, null, targetEntity, null);

        when(targetEntity.getBaseName()).thenReturn("Target");
        when(targetEntity.getTableName()).thenReturn("Target");
        when(targetEntity.getDescription()).thenReturn("Target description");
        when(targetEntity.getIdentificationPrefix()).thenReturn("TA");
        when(targetEntity.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(targetEntity.getGrouping()).thenReturn(grouping);
        when(targetEntity.hasParent()).thenReturn(Boolean.FALSE);
        when(targetEntity.hasNoParent()).thenReturn(Boolean.TRUE);

        when(config.getBasePackage()).thenReturn(BASE_PACKAGE);
        when(config.getDaoPackage()).thenReturn("dao");
        when(config.getDtoPackage()).thenReturn("dto");
        when(config.getDomainPackage()).thenReturn("domain");

        when(basePackageDir.getName()).thenReturn("basePackageDir");
        when(basePackageDir.getParentFile()).thenReturn(null);

        when(processingEnv.getFiler()).thenReturn(filer);
        try {
            when(filer.createSourceFile(any())).then(a -> {
                when(javaFileObject.getName()).thenReturn(a.getArgument(0));
                return javaFileObject;
            });
        } catch (IOException e) {
            fail(e);
        }
    }

    protected BufferedWriter mockBufferedWriter(String fileKey) {
        List<String> fileContent = new ArrayList<>();
        writtenFileContents.put(fileKey, fileContent);
        try {
            // Assumption: after write is als a newLine statement
            doAnswer(a -> fileContent.add(a.getArgument(0))).when(bufferedWriter).write(anyString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedWriter;
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

    protected void setMockReturnsReference(Reference referenceMock, String referenceName, String targetEntity, String filterField
            , String filterFieldValue, Boolean isList, Boolean isOwner) {
        when(referenceMock.getReferenceName()).thenReturn(referenceName);
        when(referenceMock.getTargetEntity()).thenReturn(targetEntity);
        when(referenceMock.getFilterField()).thenReturn(filterField);
        when(referenceMock.getFilterFieldValue()).thenReturn(filterFieldValue);
        when(referenceMock.isList()).thenReturn(isList);
        when(referenceMock.isOwner()).thenReturn(isOwner);
        when(referenceMock.getIsOwner()).thenReturn(isOwner);
        when(referenceMock.isAggregated()).thenReturn(false);
        when(referenceMock.isReverse()).thenReturn(false);

        doCallRealMethod().when(referenceMock).copy();

        doAnswer(a -> when(referenceMock.getReferenceName()).thenReturn(a.getArgument(0))).when(referenceMock).setReferenceName(anyString());
        doAnswer(a -> when(referenceMock.getTargetEntity()).thenReturn(a.getArgument(0))).when(referenceMock).setTargetEntity(anyString());
        doAnswer(a -> when(referenceMock.getFilterField()).thenReturn(a.getArgument(0))).when(referenceMock).setFilterField(anyString());
        doAnswer(a -> when(referenceMock.getFilterFieldValue()).thenReturn(a.getArgument(0))).when(referenceMock).setFilterFieldValue(anyString());
        doAnswer(a -> when(referenceMock.isList()).thenReturn(a.getArgument(0))).when(referenceMock).setIsList(anyBoolean());
        doAnswer(a -> {
            when(referenceMock.isOwner()).thenReturn(a.getArgument(0));
            when(referenceMock.getIsOwner()).thenReturn(a.getArgument(0));
            return null;
        }).when(referenceMock).setIsOwner(anyBoolean());
        doAnswer(a -> when(referenceMock.isAggregated()).thenReturn(a.getArgument(0))).when(referenceMock).setAggregated(anyBoolean());
        doAnswer(a -> when(referenceMock.isReverse()).thenReturn(a.getArgument(0))).when(referenceMock).setReverse(anyBoolean());
    }

    protected void setMockReturnsReference(Reference mock, Entity parent, Entity realTargetEntity, Field realFilterField) {
        when(mock.getParent()).thenReturn(parent);
        when(mock.getRealTargetEntity()).thenReturn(realTargetEntity);
        when(mock.getRealFilterField()).thenReturn(realFilterField);

        doAnswer(a -> when(mock.getParent()).thenReturn(a.getArgument(0))).when(mock).setParent(any());
        doAnswer(a -> when(mock.getRealTargetEntity()).thenReturn(a.getArgument(0))).when(mock).setRealTargetEntity(any());
        doAnswer(a -> when(mock.getRealFilterField()).thenReturn(a.getArgument(0))).when(mock).setRealFilterField(any());
    }
}
