package de.ma_vin.util.layer.generator.generator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.logging.Log4jLogImpl;
import de.ma_vin.util.layer.generator.sources.TestUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.opentest4j.AssertionFailedError;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class CommonMapperCreatorTest {
    private AutoCloseable openMocks;

    @Mock
    private Config config;
    @Mock
    private File mapperPackageDir;
    @Mock
    private BufferedWriter bufferedWriter;

    private CommonMapperCreator cut;

    private Map<String, List<String>> writtenFileContents = new HashMap<>();

    public static final String MAPPER_PACKAGE_NAME = "de.test.package.mapper";

    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);
        writtenFileContents.clear();
        cut = new CommonMapperCreator(config, new Log4jLogImpl()) {
            @Override
            protected BufferedWriter createBufferedWriter(File classFile) {
                List<String> fileContent = new ArrayList<>();
                writtenFileContents.put(classFile.getName(), fileContent);
                try {
                    // Assumption: after write is als a newLine statement
                    doAnswer(a -> fileContent.add(a.getArgument(0))).when(bufferedWriter).write(anyString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bufferedWriter;
            }

            @Override
            protected File createFile(File dir, String fileName) {
                File createdFile = mock(File.class);
                when(createdFile.getName()).thenReturn(fileName);
                when(createdFile.getParentFile()).thenReturn(dir);
                return createdFile;
            }
        };

        when(mapperPackageDir.getName()).thenReturn("mapperPackageDir");
        when(mapperPackageDir.getParentFile()).thenReturn(null);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    public void testCreateAbstractMapper() {
        List<String> expected = new ArrayList<>();

        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Abstract basic mapper class which contains the common convertToMethod");
        expected.add(" */");
        expected.add("public abstract class AbstractMapper {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Converts an {@code convertFrom} to an {@code T} object");
        expected.add("	 *");
        expected.add("	 * @param convertFrom           object which is to converted");
        expected.add("	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code convertFrom} is contained, the found");
        expected.add("	 *                              {@code M} will be returned");
        expected.add("	 * @param objectCreator         functional interface which is called to create a new instance of {@code T} as result");
        expected.add("	 * @param valueMapper           functional interface which is called to set the values of {@code convertFrom} at result");
        expected.add("	 * @param singleReferenceMapper functional interface which is called to add the single references of {@code convertFrom} at result");
        expected.add("	 * @param multiReferenceMapper  functional interface which is called to add the multi references of {@code convertFrom} at result");
        expected.add("	 * @param identificationGetter  functional interface which is called to get the identification of {@code convertFrom}");
        expected.add("	 * @param identificationSetter  functional interface which is called to set the identification at result");
        expected.add("	 * @param <M>                   the type of the object at map");
        expected.add("	 * @param <S>                   the type of the source object");
        expected.add("	 * @param <T>                   the type of the target object");
        expected.add("	 * @return an equivalent new created object or the found one from the given map");
        expected.add("	 */");
        expected.add("	@SuppressWarnings(\"java:S107\")");
        expected.add("	protected static <M, S, T> T convertTo(S convertFrom, Map<String, M> mappedObjects, ObjectCreator<T> objectCreator, ValueMapper<S, T> valueMapper");
        expected.add("			, ReferenceMapper<S, T> singleReferenceMapper, ReferenceMapper<S, T> multiReferenceMapper, IdentificationGetter<S> identificationGetter");
        expected.add("			, IdentificationSetter<S, T> identificationSetter) {");
        expected.add("		if (convertFrom == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = identificationGetter.getIdentification(convertFrom);");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (T) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		T result = objectCreator.create();");
        expected.add("");
        expected.add("		identificationSetter.setIdentification(convertFrom, result);");
        expected.add("");
        expected.add("		valueMapper.mapValues(convertFrom, result);");
        expected.add("		singleReferenceMapper.mapReferences(convertFrom, result);");
        expected.add("		multiReferenceMapper.mapReferences(convertFrom, result);");
        expected.add("");
        expected.add("		mappedObjects.put(identification, (M) result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * functional interface which is called to get the identification of {@code S}");
        expected.add("	 *");
        expected.add("	 * @param <S> the type of the source object");
        expected.add("	 */");
        expected.add("	@FunctionalInterface");
        expected.add("	public interface IdentificationGetter<S> {");
        expected.add("");
        expected.add("		String getIdentification(S convertFrom);");
        expected.add("");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * functional interface which is called to set the identification at {@code T} from {@code S}");
        expected.add("	 *");
        expected.add("	 * @param <S> the type of the source object");
        expected.add("	 * @param <T> the type of the target object");
        expected.add("	 */");
        expected.add("	@FunctionalInterface");
        expected.add("	public interface IdentificationSetter<S, T> {");
        expected.add("");
        expected.add("		void setIdentification(S convertFrom, T convertTo);");
        expected.add("");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * functional interface which is called to create a new instance");
        expected.add("	 *");
        expected.add("	 * @param <T> the type of the created object");
        expected.add("	 */");
        expected.add("	@FunctionalInterface");
        expected.add("	public interface ObjectCreator<T> {");
        expected.add("");
        expected.add("		T create();");
        expected.add("");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * functional interface which is called to add references of {@code S} at {@code T}");
        expected.add("	 *");
        expected.add("	 * @param <S> the type of the source object");
        expected.add("	 * @param <T> the type of the target object");
        expected.add("	 */");
        expected.add("	@FunctionalInterface");
        expected.add("	public interface ReferenceMapper<S, T> {");
        expected.add("");
        expected.add("		void mapReferences(S convertFrom, T convertTo);");
        expected.add("");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * functional interface which is called to set the values of {@code S} at {@code T}");
        expected.add("	 *");
        expected.add("	 * @param <S> the type of the source object");
        expected.add("	 * @param <T> the type of the target object");
        expected.add("	 */");
        expected.add("	@FunctionalInterface");
        expected.add("	public interface ValueMapper<S, T> {");
        expected.add("");
        expected.add("		void mapValues(S convertFrom, T convertTo);");
        expected.add("");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createAbstractMapper(MAPPER_PACKAGE_NAME, mapperPackageDir));

        checkSingleFile(CommonMapperCreator.ABSTRACT_MAPPER_CLASS_NAME + ".java", expected);
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
}
