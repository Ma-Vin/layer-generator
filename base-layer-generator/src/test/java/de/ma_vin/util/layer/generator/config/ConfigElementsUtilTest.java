package de.ma_vin.util.layer.generator.config;

import de.ma_vin.util.layer.generator.config.elements.Grouping;
import de.ma_vin.util.layer.generator.config.elements.Models;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import de.ma_vin.util.layer.generator.logging.ILogWrapper;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * {@link ConfigElementsUtil} is the class under test
 */
public class ConfigElementsUtilTest {

    private AutoCloseable openMocks;
    @Mock
    private ILogWrapper logWrapper;

    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @DisplayName("format indented text")
    @Test
    public void testFormatIndented() {
        String result = ConfigElementsUtil.format("%s test", 1, "a");
        assertNotNull(result, "There should be a result");
        assertEquals("  a test", result, "wrong result");
    }

    @DisplayName("format indented text, but with negative indent")
    @Test
    public void testFormatIndentedNegativeIndent() {
        String result = ConfigElementsUtil.format("%s test", -1, "a");
        assertNotNull(result, "There should be a result");
        assertEquals("a test", result, "wrong result");
    }

    @DisplayName("get simple name derived from field ending with \"Name\"")
    @Test
    public void testGetSimpleNameDerivedFromField() {
        final String referenceName = "TestRef";
        Reference reference = new Reference();
        reference.setReferenceName(referenceName);

        String result = ConfigElementsUtil.getSimpleLogName(reference);
        assertNotNull(result, "There should be a result");
        assertEquals(referenceName, result, "wrong result");
    }

    @DisplayName("get simple name without fields ending with \"Name\"")
    @Test
    public void testGetSimpleNameNoFieldToDeriveFrom() {
        final String groupingPackage = "de.ma_vin.test.package";
        Grouping grouping = new Grouping();
        grouping.setGroupingPackage(groupingPackage);

        String result = ConfigElementsUtil.getSimpleLogName(grouping);
        assertNotNull(result, "There should be a result");
        assertEquals(grouping.toString(), result, "wrong result");
    }

    @DisplayName("log config with primitive fields")
    @Test
    public void testLogConfigElementPrimitiveFields() {
        PrimitiveTest primitiveTest = new PrimitiveTest();
        primitiveTest.setTestBooleanField(true);
        primitiveTest.setTestByteField((byte) 32);
        primitiveTest.setTestIntField(42);
        primitiveTest.setTestShortField((short) 12);
        primitiveTest.setTestLongField(100L);
        primitiveTest.setTestFloatField(0.123f);
        primitiveTest.setTestDoubleField(1.23);
        primitiveTest.setTestCharField('a');

        ConfigElementsUtil.logConfigElement(primitiveTest, logWrapper, 0);

        verify(logWrapper, times(9)).debug(anyString());
        verify(logWrapper).debug(eq("PrimitiveTest:"));
        verify(logWrapper).debug(eq("  testBooleanField = true"));
        verify(logWrapper).debug(eq("  testByteField = 32"));
        verify(logWrapper).debug(eq("  testIntField = 42"));
        verify(logWrapper).debug(eq("  testShortField = 12"));
        verify(logWrapper).debug(eq("  testLongField = 100"));
        verify(logWrapper).debug(eq("  testFloatField = 0.123"));
        verify(logWrapper).debug(eq("  testDoubleField = 1.23"));
        verify(logWrapper).debug(eq("  testCharField = a"));
    }

    @DisplayName("log config with enum fields")
    @Test
    public void testLogConfigElementEnumFields() {
        EnumTest enumTest = new EnumTest();
        enumTest.setTestEnum(Models.DOMAIN_DAO_DTO);

        ConfigElementsUtil.logConfigElement(enumTest, logWrapper, 0);

        verify(logWrapper, times(2)).debug(anyString());
        verify(logWrapper).debug(eq("EnumTest:"));
        verify(logWrapper).debug(eq("  testEnum = DOMAIN_DAO_DTO"));
    }

    @DisplayName("log config with fields of type IConfigLog")
    @Test
    public void testLogConfigElementInterfaceConfigLogFields() {
        ConfigLogTest configLogTest = new ConfigLogTest();
        configLogTest.setTestName("SomeTestName");

        ConfigLogOwnerTest configLogOwnerTest = new ConfigLogOwnerTest();
        configLogOwnerTest.setConfigLog(configLogTest);
        configLogOwnerTest.setConfigLogFull(configLogTest);

        ConfigElementsUtil.logConfigElement(configLogOwnerTest, logWrapper, 0);

        verify(logWrapper, times(5)).debug(anyString());
        verify(logWrapper).debug(eq("ConfigLogOwnerTest:"));
        verify(logWrapper).debug(eq("  configLog = SomeTestName"));
        verify(logWrapper).debug(eq("  configLogFull ="));
        verify(logWrapper).debug(eq("    ConfigLogTest:"));
        verify(logWrapper).debug(eq("      testName = SomeTestName"));
    }

    @DisplayName("log config with fields of type IConfigLog, but null")
    @Test
    public void testLogConfigElementInterfaceConfigLogFieldsNull() {
        ConfigLogOwnerTest configLogOwnerTest = new ConfigLogOwnerTest();
        configLogOwnerTest.setConfigLog(null);
        configLogOwnerTest.setConfigLogFull(null);

        ConfigElementsUtil.logConfigElement(configLogOwnerTest, logWrapper, 0);

        verify(logWrapper, times(3)).debug(anyString());
        verify(logWrapper).debug(eq("ConfigLogOwnerTest:"));
        verify(logWrapper).debug(eq("  configLog = null"));
        verify(logWrapper).debug(eq("  configLogFull = null"));
    }

    @DisplayName("log config with primitive wrapper fields")
    @Test
    public void testLogConfigElementPrimitiveWrapperFields() {
        PrimitiveWrapperTest primitiveTest = new PrimitiveWrapperTest();
        primitiveTest.setTestBooleanField(true);
        primitiveTest.setTestByteField((byte) 32);
        primitiveTest.setTestIntField(42);
        primitiveTest.setTestShortField((short) 12);
        primitiveTest.setTestLongField(100L);
        primitiveTest.setTestFloatField(0.123f);
        primitiveTest.setTestDoubleField(1.23);
        primitiveTest.setTestBigDecimalField(BigDecimal.valueOf(3.21));
        primitiveTest.setTestCharField('a');

        ConfigElementsUtil.logConfigElement(primitiveTest, logWrapper, 0);

        verify(logWrapper, times(10)).debug(anyString());
        verify(logWrapper).debug(eq("PrimitiveWrapperTest:"));
        verify(logWrapper).debug(eq("  testBooleanField = true"));
        verify(logWrapper).debug(eq("  testByteField = 32"));
        verify(logWrapper).debug(eq("  testIntField = 42"));
        verify(logWrapper).debug(eq("  testShortField = 12"));
        verify(logWrapper).debug(eq("  testLongField = 100"));
        verify(logWrapper).debug(eq("  testFloatField = 0.123"));
        verify(logWrapper).debug(eq("  testDoubleField = 1.23"));
        verify(logWrapper).debug(eq("  testBigDecimalField = 3.21"));
        verify(logWrapper).debug(eq("  testCharField = a"));
    }

    @DisplayName("log config with list fields")
    @Test
    public void testLogConfigElementListFields() {
        StringListTest stringListTest = new StringListTest();
        stringListTest.setStringList(Arrays.asList("abc", "fed"));

        ConfigElementsUtil.logConfigElement(stringListTest, logWrapper, 0);

        verify(logWrapper, times(5)).debug(anyString());
        verify(logWrapper).debug(eq("StringListTest:"));
        verify(logWrapper).debug(eq("  #stringList = 2"));
        verify(logWrapper).debug(eq("  stringList:"));
        verify(logWrapper).debug(eq("    abc"));
        verify(logWrapper).debug(eq("    fed"));
    }

    @DisplayName("log config with list fields of type IConfigLog")
    @Test
    public void testLogConfigElementListInterfaceConfigLogFields() {
        ConfigLogTest configLogTest = new ConfigLogTest();
        configLogTest.setTestName("SomeTestName");

        ConfigLogListTest configLogListTest = new ConfigLogListTest();
        configLogListTest.setConfigLogTestList(Collections.singletonList(configLogTest));

        ConfigElementsUtil.logConfigElement(configLogListTest, logWrapper, 0);

        verify(logWrapper, times(5)).debug(anyString());
        verify(logWrapper).debug(eq("ConfigLogListTest:"));
        verify(logWrapper).debug(eq("  #configLogTestList = 1"));
        verify(logWrapper).debug(eq("  configLogTestList:"));
        verify(logWrapper).debug(eq("    ConfigLogTest:"));
        verify(logWrapper).debug(eq("      testName = SomeTestName"));
    }

    @DisplayName("log config with list fields of type IConfigLog, but null")
    @Test
    public void testLogConfigElementListInterfaceConfigLogFieldsNull() {
        ConfigLogListTest configLogListTest = new ConfigLogListTest();
        configLogListTest.setConfigLogTestList(null);

        ConfigElementsUtil.logConfigElement(configLogListTest, logWrapper, 0);

        verify(logWrapper, times(2)).debug(anyString());
        verify(logWrapper).debug(eq("ConfigLogListTest:"));
        verify(logWrapper).debug(eq("  #configLogTestList = 0"));
    }

    @Data
    private static class PrimitiveTest {
        private boolean testBooleanField;
        private byte testByteField;
        private int testIntField;
        private short testShortField;
        private long testLongField;
        private float testFloatField;
        private double testDoubleField;
        private char testCharField;
    }

    @Data
    private static class PrimitiveWrapperTest {
        private Boolean testBooleanField;
        private Byte testByteField;
        private Integer testIntField;
        private Short testShortField;
        private Long testLongField;
        private Float testFloatField;
        private Double testDoubleField;
        private BigDecimal testBigDecimalField;
        private Character testCharField;
    }

    @Data
    private static class EnumTest {
        private Models testEnum;
    }

    @Data
    private static class ConfigLogOwnerTest implements IConfigLog{
        private ConfigLogTest configLog;
        private ConfigLogTest configLogFull;

        @Override
        public List<String> getFieldNamesToLogComplete() {
            return Collections.singletonList("configLogFull");
        }
    }

    @Data
    private static class ConfigLogTest implements IConfigLog{
        private String testName;
    }

    @Data
    private static class StringListTest{
        private List<String> stringList;
    }

    @Data
    private static class  ConfigLogListTest{
        private List<ConfigLogTest> configLogTestList;
    }
}
