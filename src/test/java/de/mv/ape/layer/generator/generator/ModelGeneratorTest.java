package de.mv.ape.layer.generator.generator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.*;

import de.mv.ape.layer.generator.config.elements.Config;
import de.mv.ape.layer.generator.config.elements.Entity;
import de.mv.ape.layer.generator.config.elements.Grouping;
import de.mv.ape.layer.generator.log.LogImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.Arrays;

public class ModelGeneratorTest {

    @Mock
    private Config config;
    @Mock
    private File targetDir;
    @Mock
    private File packageDir;
    @Mock
    private DaoCreator daoCreator;
    @Mock
    private DtoCreator dtoCreator;
    @Mock
    private DomainCreator domainCreator;
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

    @BeforeEach
    public void setUp() {
        initMocks(this);
        cut = new ModelGenerator(config, new LogImpl(), targetDir, true, true, true) {

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
            protected TransportMapperCreator createTransportMapperCreator() {
                return transportMapperCreator;
            }

            @Override
            protected AccessMapperCreator createAccessMapperCreator() {
                return accessMapperCreator;
            }

            @Override
            protected File createFile(File dir, String fileName) {
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
        when(accessMapperCreator.createAccessMapper(anyList(), any(), anyString(), anyString(), anyString(), any())).thenReturn(Boolean.TRUE);
        when(transportMapperCreator.createTransportMapper(anyList(), any(), anyString(), anyString(), anyString(), any())).thenReturn(Boolean.TRUE);
    }

    private void createDefaultConfigMock() {
        when(config.getBasePackage()).thenReturn("de.abc");
        when(config.getDaoPackage()).thenReturn("persistence");
        when(config.getDtoPackage()).thenReturn("transport");
        when(config.getDomainPackage()).thenReturn("domain");

        when(config.getEntities()).thenReturn(Arrays.asList(entity));
        when(config.getGroupings()).thenReturn(Arrays.asList(grouping));

        when(grouping.getEntities()).thenReturn(Arrays.asList(groupingEntity));
    }

    @Test
    public void testGenerateDefault() {
        assertTrue(cut.generate(), "The generation should be successful");

        verify(daoCreator).createDataAccessObjectInterface(any(), any());
        verify(daoCreator, times(2)).createDataAccessObject(any(), any(), any());
        verify(dtoCreator).createDataTransportObjectInterface(any(), any());
        verify(dtoCreator, times(2)).createDataTransportObject(any(), any(), any());
        verify(domainCreator).createDomainObjectInterface(any(), any());
        verify(domainCreator, times(2)).createDomainObject(any(), any(), any());
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
    }

}
