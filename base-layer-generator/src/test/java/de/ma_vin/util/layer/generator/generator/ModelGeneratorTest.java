package de.ma_vin.util.layer.generator.generator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.mockito.Mockito.*;

import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Grouping;
import de.ma_vin.util.layer.generator.logging.Log4jLogImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ModelGeneratorTest {
    private AutoCloseable openMocks;

    @Mock
    private Config config;
    @Mock
    private File targetDir;
    @Mock
    private File packageDir;
    @Mock
    private ProcessingEnvironment processingEnv;
    @Mock
    private DaoCreator daoCreator;
    @Mock
    private DtoCreator dtoCreator;
    @Mock
    private DomainCreator domainCreator;
    @Mock
    private CommonMapperCreator commonMapperCreator;
    @Mock
    private AccessMapperCreator accessMapperCreator;
    @Mock
    private TransportMapperCreator transportMapperCreator;
    @Mock
    private Entity entity;
    @Mock
    private Entity groupingEntity;
    @Mock
    private Grouping grouping;

    private ModelGenerator cut;

    private final List<String> requestFilesToCreate = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);
        cut = new ModelGenerator(config, new Log4jLogImpl(), targetDir, true, true, true) {

            @Override
            protected DaoCreator createDaoCreator() {
                return daoCreator;
            }

            @Override
            protected DtoCreator createDtoCreator() {
                return dtoCreator;
            }

            @Override
            protected DomainCreator createDomainCreator() {
                return domainCreator;
            }

            @Override
            protected CommonMapperCreator createCommonMapperCreator() {
                return commonMapperCreator;
            }

            @Override
            protected TransportMapperCreator createTransportMapperCreator() {
                return transportMapperCreator;
            }

            @Override
            protected AccessMapperCreator createAccessMapperCreator() {
                return accessMapperCreator;
            }

            @Override
            protected File createFile(File dir, String fileName) {
                requestFilesToCreate.add(fileName);
                return packageDir;
            }
        };

        createDefaultConfigMock();
        when(packageDir.exists()).thenReturn(Boolean.TRUE);
        when(packageDir.mkdirs()).thenReturn(Boolean.TRUE);
        when(daoCreator.createDataAccessObjectInterface(any(), any())).thenReturn(Boolean.TRUE);
        when(daoCreator.createDataAccessObject(any(), any(), any())).thenReturn(Boolean.TRUE);
        when(dtoCreator.createDataTransportObjectInterface(any(), any())).thenReturn(Boolean.TRUE);
        when(dtoCreator.createDataTransportObject(any(), any(), any())).thenReturn(Boolean.TRUE);
        when(domainCreator.createDomainObjectInterface(any(), any())).thenReturn(Boolean.TRUE);
        when(domainCreator.createDomainObject(any(), any(), any())).thenReturn(Boolean.TRUE);
        when(commonMapperCreator.createAbstractMapper(anyString(), any())).thenReturn(Boolean.TRUE);
        when(accessMapperCreator.createAbstractAccessMapper(anyString(), any(), anyString(), anyString())).thenReturn(Boolean.TRUE);
        when(accessMapperCreator.createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any())).thenReturn(Boolean.TRUE);
        when(transportMapperCreator.createAbstractTransportMapper(anyString(), any(), anyString(), anyString())).thenReturn(Boolean.TRUE);
        when(transportMapperCreator.createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any())).thenReturn(Boolean.TRUE);

        requestFilesToCreate.clear();
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    private void createDefaultConfigMock() {
        when(config.getBasePackage()).thenReturn("de.abc");
        when(config.getDaoPackage()).thenReturn("persistence");
        when(config.getDtoPackage()).thenReturn("transport");
        when(config.getDomainPackage()).thenReturn("domain");

        when(config.getEntities()).thenReturn(Collections.singletonList(entity));
        when(config.getGroupings()).thenReturn(Collections.singletonList(grouping));
        when(grouping.getGroupingPackage()).thenReturn("group");

        when(grouping.getEntities()).thenReturn(Collections.singletonList(groupingEntity));
    }

    @Test
    public void testGenerateDefault() {
        assertTrue(cut.generate(), "The generation should be successful");

        verify(daoCreator).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, times(2)).createDataAccessObject(any(), any(), any());
        verify(daoCreator).setProcessingEnv(eq(Optional.empty()));
        verify(daoCreator).setGenerateJavaFileObject(eq(Boolean.FALSE));
        verify(dtoCreator).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, times(2)).createDataTransportObject(any(), any(), any());
        verify(dtoCreator).setProcessingEnv(eq(Optional.empty()));
        verify(dtoCreator).setGenerateJavaFileObject(eq(Boolean.FALSE));
        verify(domainCreator).createDomainObjectInterface(any(), any());
        verify(domainCreator, times(2)).createDomainObject(any(), any(), any());
        verify(domainCreator).setProcessingEnv(eq(Optional.empty()));
        verify(domainCreator).setGenerateJavaFileObject(eq(Boolean.FALSE));
        verify(commonMapperCreator).createAbstractMapper(anyString(), any());
        verify(commonMapperCreator).setProcessingEnv(eq(Optional.empty()));
        verify(commonMapperCreator).setGenerateJavaFileObject(eq(Boolean.FALSE));
        verify(accessMapperCreator).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, times(2)).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(accessMapperCreator).setProcessingEnv(eq(Optional.empty()));
        verify(accessMapperCreator).setGenerateJavaFileObject(eq(Boolean.FALSE));
        verify(transportMapperCreator).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, times(2)).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator).setProcessingEnv(eq(Optional.empty()));
        verify(transportMapperCreator).setGenerateJavaFileObject(eq(Boolean.FALSE));
    }

    @Test
    public void testGenerateWithProcessingEnv() {
        cut = new ModelGenerator(config, new Log4jLogImpl(), processingEnv, true, true, true) {

            @Override
            protected DaoCreator createDaoCreator() {
                return daoCreator;
            }

            @Override
            protected DtoCreator createDtoCreator() {
                return dtoCreator;
            }

            @Override
            protected DomainCreator createDomainCreator() {
                return domainCreator;
            }

            @Override
            protected CommonMapperCreator createCommonMapperCreator() {
                return commonMapperCreator;
            }

            @Override
            protected TransportMapperCreator createTransportMapperCreator() {
                return transportMapperCreator;
            }

            @Override
            protected AccessMapperCreator createAccessMapperCreator() {
                return accessMapperCreator;
            }

            @Override
            protected File createFile(File dir, String fileName) {
                fail("Should never be invoked");
                return null;
            }
        };

        assertTrue(cut.generate(), "The generation should be successful");

        verify(daoCreator).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, times(2)).createDataAccessObject(any(), any(), any());
        verify(daoCreator).setProcessingEnv(eq(Optional.of(processingEnv)));
        verify(daoCreator).setGenerateJavaFileObject(eq(Boolean.TRUE));
        verify(dtoCreator).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, times(2)).createDataTransportObject(any(), any(), any());
        verify(dtoCreator).setProcessingEnv(eq(Optional.of(processingEnv)));
        verify(dtoCreator).setGenerateJavaFileObject(eq(Boolean.TRUE));
        verify(domainCreator).createDomainObjectInterface(any(), any());
        verify(domainCreator, times(2)).createDomainObject(any(), any(), any());
        verify(domainCreator).setProcessingEnv(eq(Optional.of(processingEnv)));
        verify(domainCreator).setGenerateJavaFileObject(eq(Boolean.TRUE));
        verify(commonMapperCreator).createAbstractMapper(anyString(), any());
        verify(commonMapperCreator).setProcessingEnv(eq(Optional.of(processingEnv)));
        verify(commonMapperCreator).setGenerateJavaFileObject(eq(Boolean.TRUE));
        verify(accessMapperCreator).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, times(2)).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(accessMapperCreator).setProcessingEnv(eq(Optional.of(processingEnv)));
        verify(accessMapperCreator).setGenerateJavaFileObject(eq(Boolean.TRUE));
        verify(transportMapperCreator).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, times(2)).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator).setProcessingEnv(eq(Optional.of(processingEnv)));
        verify(transportMapperCreator).setGenerateJavaFileObject(eq(Boolean.TRUE));
    }

    @Test
    public void testGenerateBasePackageDirNotCreated() {
        when(packageDir.exists()).thenReturn(Boolean.FALSE);
        when(packageDir.mkdirs()).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator, never()).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, never()).createDataAccessObject(any(), any(), any());
        verify(dtoCreator, never()).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, never()).createDataTransportObject(any(), any(), any());
        verify(domainCreator, never()).createDomainObjectInterface(any(), any());
        verify(domainCreator, never()).createDomainObject(any(), any(), any());
        verify(commonMapperCreator, never()).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator, never()).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, never()).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateDtoPackageDirNotCreated() {
        when(packageDir.exists()).thenReturn(Boolean.FALSE);
        when(packageDir.mkdirs()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator, never()).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, never()).createDataAccessObject(any(), any(), any());
        verify(dtoCreator, never()).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, never()).createDataTransportObject(any(), any(), any());
        verify(domainCreator, never()).createDomainObjectInterface(any(), any());
        verify(domainCreator, never()).createDomainObject(any(), any(), any());
        verify(commonMapperCreator, never()).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator, never()).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, never()).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateDomainPackageDirNotCreated() {
        when(packageDir.exists()).thenReturn(Boolean.FALSE);
        when(packageDir.mkdirs()).thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator, never()).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, never()).createDataAccessObject(any(), any(), any());
        verify(dtoCreator, never()).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, never()).createDataTransportObject(any(), any(), any());
        verify(domainCreator, never()).createDomainObjectInterface(any(), any());
        verify(domainCreator, never()).createDomainObject(any(), any(), any());
        verify(commonMapperCreator, never()).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator, never()).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, never()).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateDaoPackageDirNotCreated() {
        when(packageDir.exists()).thenReturn(Boolean.FALSE);
        when(packageDir.mkdirs()).thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE)
                .thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator, never()).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, never()).createDataAccessObject(any(), any(), any());
        verify(dtoCreator, never()).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, never()).createDataTransportObject(any(), any(), any());
        verify(domainCreator, never()).createDomainObjectInterface(any(), any());
        verify(domainCreator, never()).createDomainObject(any(), any(), any());
        verify(commonMapperCreator, never()).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator, never()).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, never()).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateGroupingPackageDirNotCreated() {
        when(packageDir.exists()).thenReturn(Boolean.FALSE);
        when(packageDir.mkdirs()).thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE)
                .thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator).createDataAccessObjectInterface(any(), any());
        // first Entry will be created before first grouping fails
        verify(daoCreator).createDataAccessObject(any(), any(), any());
        verify(dtoCreator, never()).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, never()).createDataTransportObject(any(), any(), any());
        verify(domainCreator, never()).createDomainObjectInterface(any(), any());
        verify(domainCreator, never()).createDomainObject(any(), any(), any());
        verify(commonMapperCreator, never()).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator, never()).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, never()).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateMapperPackageDirNotCreated() {
        when(packageDir.exists()).thenReturn(Boolean.FALSE);
        when(packageDir.mkdirs()).thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE)
                .thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator, never()).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, never()).createDataAccessObject(any(), any(), any());
        verify(dtoCreator, never()).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, never()).createDataTransportObject(any(), any(), any());
        verify(domainCreator, never()).createDomainObjectInterface(any(), any());
        verify(domainCreator, never()).createDomainObject(any(), any(), any());
        verify(commonMapperCreator, never()).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator, never()).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, never()).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateNothing() {
        cut.setGenDao(false);
        cut.setGenDto(false);
        cut.setGenDomain(false);

        assertTrue(cut.generate(), "The generation should be successful");

        verify(daoCreator, never()).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, never()).createDataAccessObject(any(), any(), any());
        verify(dtoCreator, never()).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, never()).createDataTransportObject(any(), any(), any());
        verify(domainCreator, never()).createDomainObjectInterface(any(), any());
        verify(domainCreator, never()).createDomainObject(any(), any(), any());
        verify(commonMapperCreator, never()).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator, never()).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, never()).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateDaoFail() {
        cut.setGenDto(false);
        cut.setGenDomain(false);
        when(daoCreator.createDataAccessObject(any(), any(), any())).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, times(2)).createDataAccessObject(any(), any(), any());
        verify(dtoCreator, never()).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, never()).createDataTransportObject(any(), any(), any());
        verify(domainCreator, never()).createDomainObjectInterface(any(), any());
        verify(domainCreator, never()).createDomainObject(any(), any(), any());
        verify(commonMapperCreator, never()).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator, never()).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, never()).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateDaoInterfaceFail() {
        cut.setGenDto(false);
        cut.setGenDomain(false);
        when(daoCreator.createDataAccessObjectInterface(any(), any())).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, never()).createDataAccessObject(any(), any(), any());
        verify(dtoCreator, never()).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, never()).createDataTransportObject(any(), any(), any());
        verify(domainCreator, never()).createDomainObjectInterface(any(), any());
        verify(domainCreator, never()).createDomainObject(any(), any(), any());
        verify(commonMapperCreator, never()).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator, never()).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, never()).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateDtoFail() {
        cut.setGenDao(false);
        cut.setGenDomain(false);
        when(dtoCreator.createDataTransportObject(any(), any(), any())).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator, never()).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, never()).createDataAccessObject(any(), any(), any());
        verify(dtoCreator).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, times(2)).createDataTransportObject(any(), any(), any());
        verify(domainCreator, never()).createDomainObjectInterface(any(), any());
        verify(domainCreator, never()).createDomainObject(any(), any(), any());
        verify(commonMapperCreator, never()).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator, never()).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, never()).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateDomainFail() {
        cut.setGenDao(false);
        cut.setGenDto(false);
        when(domainCreator.createDomainObject(any(), any(), any())).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator, never()).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, never()).createDataAccessObject(any(), any(), any());
        verify(dtoCreator, never()).createDataTransportObject(any(), any(), any());
        verify(domainCreator).createDomainObjectInterface(any(), any());
        verify(domainCreator, times(2)).createDomainObject(any(), any(), any());
        verify(commonMapperCreator, never()).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator, never()).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, never()).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateDomainInterfaceFail() {
        cut.setGenDao(false);
        cut.setGenDto(false);
        when(domainCreator.createDomainObjectInterface(any(), any())).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator, never()).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, never()).createDataAccessObject(any(), any(), any());
        verify(dtoCreator, never()).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, never()).createDataTransportObject(any(), any(), any());
        verify(domainCreator).createDomainObjectInterface(any(), any());
        verify(domainCreator, never()).createDomainObject(any(), any(), any());
        verify(commonMapperCreator, never()).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator, never()).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, never()).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateGroupingWithDots() {
        when(grouping.getGroupingPackage()).thenReturn("group.subgroup");
        assertTrue(cut.generate(), "The generation should be successful");

        assertFalse(requestFilesToCreate.contains("group.subgroup"), "Not any directories with dots should be created");
        assertTrue(requestFilesToCreate.contains(String.format("group%ssubgroup", File.separator)), "Dot should be replaced by backslash");

        verify(daoCreator).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, times(2)).createDataAccessObject(any(), any(), any());
        verify(dtoCreator).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, times(2)).createDataTransportObject(any(), any(), any());
        verify(domainCreator).createDomainObjectInterface(any(), any());
        verify(domainCreator, times(2)).createDomainObject(any(), any(), any());
        verify(commonMapperCreator).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, times(2)).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, times(2)).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateCreateAbstractMapperFailed() {
        when(commonMapperCreator.createAbstractMapper(anyString(), any())).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, times(2)).createDataAccessObject(any(), any(), any());
        verify(dtoCreator).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, times(2)).createDataTransportObject(any(), any(), any());
        verify(domainCreator).createDomainObjectInterface(any(), any());
        verify(domainCreator, times(2)).createDomainObject(any(), any(), any());
        verify(commonMapperCreator).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator, never()).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, never()).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }


    @Test
    public void testGenerateCreateAbstractAccessMapperFailed() {
        when(accessMapperCreator.createAbstractAccessMapper(anyString(), any(), anyString(), anyString())).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, times(2)).createDataAccessObject(any(), any(), any());
        verify(dtoCreator).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, times(2)).createDataTransportObject(any(), any(), any());
        verify(domainCreator).createDomainObjectInterface(any(), any());
        verify(domainCreator, times(2)).createDomainObject(any(), any(), any());
        verify(commonMapperCreator).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, times(2)).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateCreateAccessMapperFailed() {
        when(accessMapperCreator.createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any())).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, times(2)).createDataAccessObject(any(), any(), any());
        verify(dtoCreator).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, times(2)).createDataTransportObject(any(), any(), any());
        verify(domainCreator).createDomainObjectInterface(any(), any());
        verify(domainCreator, times(2)).createDomainObject(any(), any(), any());
        verify(commonMapperCreator).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, times(2)).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator, never()).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, never()).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateCreateAbstractTransportMapperFailed() {
        when(transportMapperCreator.createAbstractTransportMapper(anyString(), any(), anyString(), anyString())).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, times(2)).createDataAccessObject(any(), any(), any());
        verify(dtoCreator).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, times(2)).createDataTransportObject(any(), any(), any());
        verify(domainCreator).createDomainObjectInterface(any(), any());
        verify(domainCreator, times(2)).createDomainObject(any(), any(), any());
        verify(commonMapperCreator).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, times(2)).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, times(2)).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }

    @Test
    public void testGenerateCreateTransportMapperFailed() {
        when(transportMapperCreator.createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any())).thenReturn(Boolean.FALSE);

        assertFalse(cut.generate(), "The generation should not be successful");

        verify(daoCreator).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, times(2)).createDataAccessObject(any(), any(), any());
        verify(dtoCreator).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, times(2)).createDataTransportObject(any(), any(), any());
        verify(domainCreator).createDomainObjectInterface(any(), any());
        verify(domainCreator, times(2)).createDomainObject(any(), any(), any());
        verify(commonMapperCreator).createAbstractMapper(anyString(), any());
        verify(accessMapperCreator).createAbstractAccessMapper(anyString(), any(), anyString(), anyString());
        verify(accessMapperCreator, times(2)).createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
        verify(transportMapperCreator).createAbstractTransportMapper(anyString(), any(), anyString(), anyString());
        verify(transportMapperCreator, times(2)).createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any());
    }
}
