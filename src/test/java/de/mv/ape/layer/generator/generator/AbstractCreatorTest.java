package de.mv.ape.layer.generator.generator;

import de.mv.ape.layer.generator.config.elements.*;
import de.mv.ape.layer.generator.sources.TestUtil;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mock;

import java.io.BufferedWriter;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@Slf4j
public class AbstractCreatorTest {
    public static final String BASE_PACKAGE = "de.test.package";
    public static final String ENTITY_NAME = "Dummy";

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

    protected Map<String, List<String>> writtenFileContents = new HashMap<>();

    public void setUp() {
        initMocks(this);
        initDefaultMock();
        writtenFileContents.clear();
    }

    protected void initDefaultMock() {
        when(entity.getBaseName()).thenReturn(ENTITY_NAME);
        when(entity.getDescription()).thenReturn("Dummy description");
        when(entity.getIdentificationPrefix()).thenReturn("DU");
        when(entity.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(entity.getGrouping()).thenReturn(grouping);

        when(field.getFieldName()).thenReturn("anyField");
        when(field.getType()).thenReturn("String");
        when(field.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);

        when(grouping.getGroupingPackage()).thenReturn("group");
        when(targetReference.getTargetEntity()).thenReturn("Target");
        when(targetReference.getRealTargetEntity()).thenReturn(targetEntity);
        when(targetReference.isList()).thenReturn(Boolean.TRUE);
        when(targetReference.isOwner()).thenReturn(Boolean.TRUE);
        when(targetReference.getReferenceName()).thenReturn("TargetRef");

        when(targetEntity.getBaseName()).thenReturn("Target");
        when(targetEntity.getDescription()).thenReturn("Target description");
        when(targetEntity.getIdentificationPrefix()).thenReturn("TA");
        when(targetEntity.getModels()).thenReturn(Models.DAO);
        when(targetEntity.getGrouping()).thenReturn(grouping);

        when(config.getBasePackage()).thenReturn(BASE_PACKAGE);
        when(config.getDaoPackage()).thenReturn("dao");
        when(config.getDtoPackage()).thenReturn("dto");
        when(config.getDomainPackage()).thenReturn("domain");

        when(basePackageDir.getName()).thenReturn("basePackageDir");
        when(basePackageDir.getParentFile()).thenReturn(null);
    }

    protected void checkSingleFile(String fileName, List<String> expectedLines) {
        assertEquals(1, writtenFileContents.size(), "Wrong number of files");
        assertTrue(writtenFileContents.containsKey(fileName));

        if (expectedLines.size() != writtenFileContents.get(fileName).size()) {
            logFileContents();
        }
        TestUtil.checkList(expectedLines, writtenFileContents.get(fileName));
    }

    protected void logFileContents() {
        writtenFileContents.entrySet().forEach(entry -> {
            log.error("File {} was written:", entry.getKey());
            log.error("");
            entry.getValue().forEach(line -> log.error(line));
            log.error("-------------");
        });
    }
}
