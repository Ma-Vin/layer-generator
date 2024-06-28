package de.ma_vin.util.layer.generator.generator;

import static de.ma_vin.util.layer.generator.generator.AbstractMapperCreator.MAPPED_OBJECTS_PARAMETER_TEXT;

import de.ma_vin.util.layer.generator.config.elements.Config;
import com.github.ma_vin.util.layer_generator.logging.ILogWrapper;
import de.ma_vin.util.layer.generator.sources.*;

import java.io.File;
import java.util.Map;
import java.util.Optional;

/**
 * Creator for common mapper objects
 */
public class CommonMapperCreator extends AbstractCreator {

    public static final String ABSTRACT_MAPPER_CLASS_NAME = "AbstractMapper";
    public static final String CONVERT_TO_METHOD_NAME = "convertTo";
    public static final String SOURCE_GENERIC = "S";
    public static final String TARGET_GENERIC = "T";
    public static final String MAPPED_GENERIC = "M";
    public static final String OBJECT_CREATOR_INTERFACE = "ObjectCreator";
    public static final String VALUE_MAPPER_INTERFACE = "ValueMapper";
    public static final String REFERENCE_MAPPER_INTERFACE = "ReferenceMapper";
    public static final String IDENTIFICATION_GETTER_INTERFACE = "IdentificationGetter";
    public static final String IDENTIFICATION_SETTER_INTERFACE = "IdentificationSetter";

    public static final String SOURCE_VARIABLE = "convertFrom";
    public static final String TARGET_VARIABLE = "result";
    public static final String TARGET_PARAMETER = "convertTo";
    public static final String OBJECT_CREATOR_PARAMETER = "objectCreator";
    public static final String VALUE_MAPPER_PARAMETER = "valueMapper";
    public static final String SINGLE_REFERENCE_MAPPER_PARAMETER = "singleReferenceMapper";
    public static final String MULTI_REFERENCE_MAPPER_PARAMETER = "multiReferenceMapper";
    public static final String IDENTIFICATION_GETTER_PARAMETER = "identificationGetter";
    public static final String IDENTIFICATION_SETTER_PARAMETER = "identificationSetter";

    public static final String CLASS_ONE_GENERIC = "%s<%s>";
    public static final String CLASS_TWO_GENERICS = "%s<%s, %s>";

    public static final String GENERIC_JAVA_DOC = "<%s>";
    public static final String SOURCE_GENERIC_DESCRIPTION = "the type of the source object";
    public static final String TARGET_GENERIC_DESCRIPTION = "the type of the target object";

    public CommonMapperCreator(Config config, ILogWrapper logger) {
        super(config, logger);
    }

    /**
     * Creates the abstract mapper which is used by transport and access mapper
     *
     * @param mapperPackageName package of the mapper to use
     * @param mapperPackageDir  directory of the package
     * @return {@code true} if generation was successful
     */
    public boolean createAbstractMapper(String mapperPackageName, Optional<File> mapperPackageDir) {
        Clazz mapperClass = new Clazz(mapperPackageName, ABSTRACT_MAPPER_CLASS_NAME);
        logger.debug("Create abstract mapper " + mapperClass.getClassName());
        mapperClass.setAbstract(true);
        mapperClass.setDescription("Abstract basic mapper class which contains the common convertToMethod");

        createAndAddConvertToMethod(mapperClass);
        createAndAddFunctionalInterfaces(mapperClass);

        return writeClassFile(mapperPackageDir, mapperClass);
    }

    /**
     * Creates the convertTo method which will be used for value and reference mapping
     * <br>
     * SuppressWarnings("java:S107"): allow more than 7 parameters. Actual there are 8
     *
     * @param mapperClass class where to add the created method
     */
    private void createAndAddConvertToMethod(Clazz mapperClass) {
        mapperClass.addImport(Map.class.getName());

        Method convertToMethod = new Method(CONVERT_TO_METHOD_NAME);
        convertToMethod.addAnnotation(SuppressWarnings.class.getSimpleName(), null, "\"java:S107\"");
        convertToMethod.setQualifier(Qualifier.PROTECTED);
        convertToMethod.setStatic(true);
        convertToMethod.addGeneric(SOURCE_GENERIC);
        convertToMethod.addGeneric(TARGET_GENERIC);
        convertToMethod.addGeneric(MAPPED_GENERIC);
        convertToMethod.setMethodType(TARGET_GENERIC);
        convertToMethod.addParameter(SOURCE_GENERIC, SOURCE_VARIABLE);
        convertToMethod.addParameter(String.format(CLASS_TWO_GENERICS, Map.class.getSimpleName(), String.class.getSimpleName(), MAPPED_GENERIC), MAPPED_OBJECTS_PARAMETER_TEXT);
        convertToMethod.addParameter(String.format(CLASS_ONE_GENERIC, OBJECT_CREATOR_INTERFACE, TARGET_GENERIC), OBJECT_CREATOR_PARAMETER);
        convertToMethod.addParameter(String.format(CLASS_TWO_GENERICS, VALUE_MAPPER_INTERFACE, SOURCE_GENERIC, TARGET_GENERIC), VALUE_MAPPER_PARAMETER);
        convertToMethod.addParameter(String.format(CLASS_TWO_GENERICS, REFERENCE_MAPPER_INTERFACE, SOURCE_GENERIC, TARGET_GENERIC), SINGLE_REFERENCE_MAPPER_PARAMETER);
        convertToMethod.addParameter(String.format(CLASS_TWO_GENERICS, REFERENCE_MAPPER_INTERFACE, SOURCE_GENERIC, TARGET_GENERIC), MULTI_REFERENCE_MAPPER_PARAMETER);
        convertToMethod.addParameter(String.format(CLASS_ONE_GENERIC, IDENTIFICATION_GETTER_INTERFACE, SOURCE_GENERIC), IDENTIFICATION_GETTER_PARAMETER);
        convertToMethod.addParameter(String.format(CLASS_TWO_GENERICS, IDENTIFICATION_SETTER_INTERFACE, SOURCE_GENERIC, TARGET_GENERIC), IDENTIFICATION_SETTER_PARAMETER);

        createAndAddConvertToMethodJavaDoc(convertToMethod);

        addConvertToMethodBody(convertToMethod);

        mapperClass.addMethod(convertToMethod);
    }

    /**
     * Adds the javadoc description
     *
     * @param convertToMethod
     */
    private void createAndAddConvertToMethodJavaDoc(Method convertToMethod) {
        JavaDoc javaDoc = new JavaDoc();
        javaDoc.addLine("Converts an {@code %s} to an {@code %s} object", SOURCE_VARIABLE, TARGET_GENERIC);

        javaDoc.addParams(SOURCE_VARIABLE, "object which is to converted");
        javaDoc.addParams(MAPPED_OBJECTS_PARAMETER_TEXT
                , "map which contains already mapped objects. If an identification of {@code %s} is contained, the found {@code %s} will be returned"
                , SOURCE_VARIABLE, MAPPED_GENERIC);
        javaDoc.addParams(OBJECT_CREATOR_PARAMETER, "functional interface which is called to create a new instance of {@code %s} as result"
                , TARGET_GENERIC);
        javaDoc.addParams(VALUE_MAPPER_PARAMETER, "functional interface which is called to set the values of {@code %s} at result"
                , SOURCE_VARIABLE);
        javaDoc.addParams(SINGLE_REFERENCE_MAPPER_PARAMETER, "functional interface which is called to add the single references of {@code %s} at result"
                , SOURCE_VARIABLE);
        javaDoc.addParams(MULTI_REFERENCE_MAPPER_PARAMETER, "functional interface which is called to add the multi references of {@code %s} at result"
                , SOURCE_VARIABLE);
        javaDoc.addParams(IDENTIFICATION_GETTER_PARAMETER, "functional interface which is called to get the identification of {@code %s}"
                , SOURCE_VARIABLE);
        javaDoc.addParams(IDENTIFICATION_SETTER_PARAMETER, "functional interface which is called to set the identification at result");

        javaDoc.addParams(String.format(GENERIC_JAVA_DOC, MAPPED_GENERIC), "the type of the object at map");
        javaDoc.addParams(String.format(GENERIC_JAVA_DOC, SOURCE_GENERIC), SOURCE_GENERIC_DESCRIPTION);
        javaDoc.addParams(String.format(GENERIC_JAVA_DOC, TARGET_GENERIC), TARGET_GENERIC_DESCRIPTION);

        javaDoc.setReturnDescription("an equivalent new created object or the found one from the given map");

        convertToMethod.setJavaDoc(javaDoc);
    }

    /**
     * Creates the body for the convertTo method
     *
     * @param convertToMethod method where to add lines
     */
    private void addConvertToMethodBody(Method convertToMethod) {
        convertToMethod.addLine("if (%s == null) {", SOURCE_VARIABLE);
        convertToMethod.addTabbedLine("return null;", 1);
        convertToMethod.addLine("}");
        convertToMethod.addEmptyLine();
        convertToMethod.addLine("String identification = identificationGetter.getIdentification(%s);", SOURCE_VARIABLE);
        convertToMethod.addLine("if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        convertToMethod.addTabbedLine("return (%s) mappedObjects.get(identification);", 1, TARGET_GENERIC);
        convertToMethod.addLine("}");
        convertToMethod.addEmptyLine();
        convertToMethod.addLine("%s %s = objectCreator.create();", TARGET_GENERIC, TARGET_VARIABLE);
        convertToMethod.addEmptyLine();
        convertToMethod.addLine("identificationSetter.setIdentification(%s, %s);", SOURCE_VARIABLE, TARGET_VARIABLE);
        convertToMethod.addEmptyLine();
        convertToMethod.addLine("valueMapper.mapValues(%s, %s);", SOURCE_VARIABLE, TARGET_VARIABLE);
        convertToMethod.addLine("singleReferenceMapper.mapReferences(%s, %s);", SOURCE_VARIABLE, TARGET_VARIABLE);
        convertToMethod.addLine("multiReferenceMapper.mapReferences(%s, %s);", SOURCE_VARIABLE, TARGET_VARIABLE);
        convertToMethod.addEmptyLine();
        convertToMethod.addLine("mappedObjects.put(identification, (%s) %s);", MAPPED_GENERIC, TARGET_VARIABLE);
        convertToMethod.addLine("return %s;", TARGET_VARIABLE);
    }

    /**
     * Creates functional interfaces which will be used by the convertTo method
     *
     * @param mapperClass class where to add the created interfaces
     */
    private void createAndAddFunctionalInterfaces(Clazz mapperClass) {
        createAndAddObjectCreator(mapperClass);
        createAndAddIdentificationGetter(mapperClass);
        createAndAddIdentificationSetter(mapperClass);
        createAndAddValueMapper(mapperClass);
        createAndAddReferenceMapper(mapperClass);
    }

    /**
     * Creates the functional interface for the object creator
     *
     * @param mapperClass class where to add the created  functional interface
     */
    private void createAndAddObjectCreator(Clazz mapperClass) {
        Interface objectCreator = new Interface(OBJECT_CREATOR_INTERFACE);
        objectCreator.addAnnotation(FunctionalInterface.class.getSimpleName());
        objectCreator.addGeneric(TARGET_GENERIC);
        objectCreator.addMethodDeclaration(TARGET_GENERIC, "create");
        objectCreator.setDescription("functional interface which is called to create a new instance");
        objectCreator.getDescription().addParams(String.format(GENERIC_JAVA_DOC, TARGET_GENERIC), "the type of the created object");
        mapperClass.addInnerInterface(objectCreator);
    }

    /**
     * Creates the functional interface for the getter of identification
     *
     * @param mapperClass class where to add the created  functional interface
     */
    private void createAndAddIdentificationGetter(Clazz mapperClass) {
        Interface identificationGetter = new Interface(IDENTIFICATION_GETTER_INTERFACE);
        identificationGetter.addAnnotation(FunctionalInterface.class.getSimpleName());
        identificationGetter.addGeneric(SOURCE_GENERIC);
        identificationGetter.addMethodDeclaration(String.class.getSimpleName(), "getIdentification", SOURCE_GENERIC, SOURCE_VARIABLE);
        identificationGetter.setDescription("functional interface which is called to get the identification of {@code %s}", SOURCE_GENERIC);
        identificationGetter.getDescription().addParams(String.format(GENERIC_JAVA_DOC, SOURCE_GENERIC), SOURCE_GENERIC_DESCRIPTION);
        mapperClass.addInnerInterface(identificationGetter);
    }

    /**
     * Creates the functional interface for the setter of identification
     *
     * @param mapperClass class where to add the created  functional interface
     */
    private void createAndAddIdentificationSetter(Clazz mapperClass) {
        Interface identificationSetter = new Interface(IDENTIFICATION_SETTER_INTERFACE);
        identificationSetter.addAnnotation(FunctionalInterface.class.getSimpleName());
        identificationSetter.addGeneric(SOURCE_GENERIC);
        identificationSetter.addGeneric(TARGET_GENERIC);
        identificationSetter.addMethodDeclaration("void", "setIdentification", SOURCE_GENERIC, SOURCE_VARIABLE, TARGET_GENERIC, TARGET_PARAMETER);
        identificationSetter.setDescription("functional interface which is called to set the identification at {@code %s} from {@code %s}", TARGET_GENERIC, SOURCE_GENERIC);
        identificationSetter.getDescription().addParams(String.format(GENERIC_JAVA_DOC, SOURCE_GENERIC), SOURCE_GENERIC_DESCRIPTION);
        identificationSetter.getDescription().addParams(String.format(GENERIC_JAVA_DOC, TARGET_GENERIC), TARGET_GENERIC_DESCRIPTION);
        mapperClass.addInnerInterface(identificationSetter);
    }

    /**
     * Creates the functional interface for the value mapper
     *
     * @param mapperClass class where to add the created  functional interface
     */
    private void createAndAddValueMapper(Clazz mapperClass) {
        Interface valueMapper = new Interface(VALUE_MAPPER_INTERFACE);
        valueMapper.addAnnotation(FunctionalInterface.class.getSimpleName());
        valueMapper.addGeneric(SOURCE_GENERIC);
        valueMapper.addGeneric(TARGET_GENERIC);
        valueMapper.addMethodDeclaration("void", "mapValues", SOURCE_GENERIC, SOURCE_VARIABLE, TARGET_GENERIC, TARGET_PARAMETER);
        valueMapper.setDescription("functional interface which is called to set the values of {@code %s} at {@code %s}", SOURCE_GENERIC, TARGET_GENERIC);
        valueMapper.getDescription().addParams(String.format(GENERIC_JAVA_DOC, SOURCE_GENERIC), SOURCE_GENERIC_DESCRIPTION);
        valueMapper.getDescription().addParams(String.format(GENERIC_JAVA_DOC, TARGET_GENERIC), TARGET_GENERIC_DESCRIPTION);
        mapperClass.addInnerInterface(valueMapper);
    }

    /**
     * Creates the functional interface for the reference mapper
     *
     * @param mapperClass class where to add the created  functional interface
     */
    private void createAndAddReferenceMapper(Clazz mapperClass) {
        Interface referenceMapper = new Interface(REFERENCE_MAPPER_INTERFACE);
        referenceMapper.addAnnotation(FunctionalInterface.class.getSimpleName());
        referenceMapper.addGeneric(SOURCE_GENERIC);
        referenceMapper.addGeneric(TARGET_GENERIC);
        referenceMapper.addMethodDeclaration("void", "mapReferences", SOURCE_GENERIC, SOURCE_VARIABLE, TARGET_GENERIC, TARGET_PARAMETER);
        referenceMapper.setDescription("functional interface which is called to add references of {@code %s} at {@code %s}", SOURCE_GENERIC, TARGET_GENERIC);
        referenceMapper.getDescription().addParams(String.format(GENERIC_JAVA_DOC, SOURCE_GENERIC), SOURCE_GENERIC_DESCRIPTION);
        referenceMapper.getDescription().addParams(String.format(GENERIC_JAVA_DOC, TARGET_GENERIC), TARGET_GENERIC_DESCRIPTION);
        mapperClass.addInnerInterface(referenceMapper);
    }
}
