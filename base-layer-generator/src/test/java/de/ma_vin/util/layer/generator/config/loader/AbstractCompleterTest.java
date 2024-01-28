package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Grouping;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public abstract class AbstractCompleterTest {

    protected AutoCloseable openMocks;

    @Mock
    protected Config config;
    @Mock
    protected Entity entity;
    @Mock
    protected Entity groupingEntity;
    @Mock
    protected Grouping grouping;


    protected final List<Entity> entities = new ArrayList<>();
    protected final List<Entity> groupingEntities = new ArrayList<>();
    protected final List<Grouping> groupings = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);

        entities.clear();
        groupings.clear();

        when(config.getEntities()).thenReturn(entities);
        when(config.getGroupings()).thenReturn(groupings);

        when(grouping.getEntities()).thenReturn(groupingEntities);

        entities.add(entity);
        groupingEntities.add(groupingEntity);
        groupings.add(grouping);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

}
