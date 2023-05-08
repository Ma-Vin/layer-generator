package de.ma_vin.util.layer.generator.generator;

import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Grouping;
import de.ma_vin.util.layer.generator.logging.ILogWrapper;
import lombok.Data;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.File;
import java.util.Optional;

@Data
public class ModelGenerator {

    public static final String MAPPER_DIRECTORY_NEEDED = "directory for mapper is empty but needed";
    public static final String MAPPER_SUB_PACKAGE = ".mapper";

    private Config config;
    private ILogWrapper logger;
    private boolean genDto;
    private boolean genDao;
    private boolean genDomain;
    private boolean generateJavaFileObject;


    private Optional<File> targetDir = Optional.empty();
    private Optional<File> dtoPackageDir = Optional.empty();
    private Optional<File> domainPackageDir = Optional.empty();
    private Optional<File> daoPackageDir = Optional.empty();
    private Optional<File> mapperPackageDir = Optional.empty();

    private DaoCreator daoCreator;
    private DomainCreator domainCreator;
    private DtoCreator dtoCreator;

    private CommonMapperCreator commonMapperCreator;
    private AccessMapperCreator accessMapperCreator;
    private TransportMapperCreator transportMapperCreator;

    /**
     * Constructor of the generator
     *
     * @param config    Configuration to use
     * @param logger    Logger to use
     * @param targetDir directory where to write at
     * @param genDto    {@code true} if data transport objects should be generated
     * @param genDomain {@code true} if domain objects should be generated
     * @param genDao    {@code true} if data access objects should be generated
     */
    public ModelGenerator(Config config, ILogWrapper logger, File targetDir, boolean genDto, boolean genDomain, boolean genDao) {
        this(config, logger, genDto, genDomain, genDao);
        this.targetDir = Optional.of(targetDir);
        setProcessingEnv(Optional.empty());
        setGenerateJavaFileObject(false);
        logger.debug("Create ModelGenerator with targetDir");
    }

    /**
     * Constructor of the generator
     *
     * @param config        Configuration to use
     * @param logger        Logger to use
     * @param processingEnv Processing environment provided by the tool framework. It will be used for source file creation
     * @param genDto        {@code true} if data transport objects should be generated
     * @param genDomain     {@code true} if domain objects should be generated
     * @param genDao        {@code true} if data access objects should be generated
     */
    public ModelGenerator(Config config, ILogWrapper logger, ProcessingEnvironment processingEnv, boolean genDto, boolean genDomain, boolean genDao) {
        this(config, logger, genDto, genDomain, genDao);
        setProcessingEnv(Optional.of(processingEnv));
        setGenerateJavaFileObject(true);
        logger.debug("Create ModelGenerator with processingEnv");
    }

    /**
     * Constructor of the generator
     *
     * @param config    Configuration to use
     * @param logger    Logger to use
     * @param genDto    {@code true} if data transport objects should be generated
     * @param genDomain {@code true} if domain objects should be generated
     * @param genDao    {@code true} if data access objects should be generated
     */
    public ModelGenerator(Config config, ILogWrapper logger, boolean genDto, boolean genDomain, boolean genDao) {
        this.config = config;
        this.logger = logger;
        this.genDto = genDto;
        this.genDomain = genDomain;
        this.genDao = genDao;

        daoCreator = createDaoCreator();
        domainCreator = createDomainCreator();
        dtoCreator = createDtoCreator();

        commonMapperCreator = createCommonMapperCreator();
        accessMapperCreator = createAccessMapperCreator();
        transportMapperCreator = createTransportMapperCreator();
    }

    public void setGenerateJavaFileObject(boolean generateJavaFileObject) {
        this.generateJavaFileObject = generateJavaFileObject;
        daoCreator.setGenerateJavaFileObject(generateJavaFileObject);
        domainCreator.setGenerateJavaFileObject(generateJavaFileObject);
        dtoCreator.setGenerateJavaFileObject(generateJavaFileObject);
        commonMapperCreator.setGenerateJavaFileObject(generateJavaFileObject);
        accessMapperCreator.setGenerateJavaFileObject(generateJavaFileObject);
        transportMapperCreator.setGenerateJavaFileObject(generateJavaFileObject);
    }

    public void setProcessingEnv(Optional<ProcessingEnvironment> processingEnv) {
        daoCreator.setProcessingEnv(processingEnv);
        domainCreator.setProcessingEnv(processingEnv);
        dtoCreator.setProcessingEnv(processingEnv);
        commonMapperCreator.setProcessingEnv(processingEnv);
        accessMapperCreator.setProcessingEnv(processingEnv);
        transportMapperCreator.setProcessingEnv(processingEnv);
    }

    /**
     * starts the generating workflow
     *
     * @return {@code true} if the generation was successful. Otherwise {@code false}
     */
    public boolean generate() {
        if (!createPackages()) {
            logger.error("packages could not be created");
            return false;
        }
        if (!createDataAccessObjects()) {
            logger.error("data access objects could not be created");
            return false;
        }
        if (!createDomainObjects()) {
            logger.error("domain objects could not be created");
            return false;
        }
        if (!createDataTransportObjects()) {
            logger.error("data transport objects could not be created");
            return false;
        }
        if (!createAbstractMapper()) {
            logger.error("abstract mapper for access and transport mappers could not be created");
            return false;
        }
        if (!createAccessMapper()) {
            logger.error("access mapper for domain and data access objects could not be created");
            return false;
        }
        if (!createTransportMapper()) {
            logger.error("access mapper for domain and data transport objects could not be created");
            return false;
        }
        return true;
    }

    /**
     * Creates the packages where to generate java sources at
     *
     * @return {@code true} if the generation was successful. Otherwise {@code false}
     */
    private boolean createPackages() {
        if (targetDir.isEmpty() && generateJavaFileObject) {
            logger.debug("No target directory defined, skip creating package directories");
            return true;
        } else if (targetDir.isEmpty()) {
            logger.error("No target directory defined, but needed for package creation");
            return false;
        }
        Optional<File> basePackageDir = createDirForPackage(targetDir.get(), config.getBasePackage().replace(".", File.separator), "base");
        if (basePackageDir.isEmpty()) {
            return false;
        }

        return createPackage(genDto, config.getDtoPackage(), basePackageDir.get(), "dto", f -> dtoPackageDir = Optional.of(f))
                && createPackage(genDomain, config.getDomainPackage(), basePackageDir.get(), "domain", f -> domainPackageDir = Optional.of(f))
                && createPackage(genDao, config.getDaoPackage(), basePackageDir.get(), "dao", f -> daoPackageDir = Optional.of(f))
                && createPackage((genDao || genDto) && genDomain, "mapper", basePackageDir.get(), "mapper", f -> mapperPackageDir = Optional.of(f));
    }

    /**
     * Creates the directory for a package if needed
     *
     * @param modelIndicator       general indicator if to create the directory
     * @param modelPackageSubName  sub package name to at a base package name
     * @param basePackageDir       base package name
     * @param messageTextOfPackage Message text for logger
     * @param packageFileSetter    Functional interface to set the optional of the package directory
     * @return {@code true} if creation was successful
     */
    private boolean createPackage(boolean modelIndicator, String modelPackageSubName, File basePackageDir
            , String messageTextOfPackage, PackageFileSetter packageFileSetter) {
        if (modelIndicator && modelPackageSubName != null && !modelPackageSubName.isEmpty()) {
            Optional<File> packageDir = createDirForPackage(basePackageDir, modelPackageSubName, messageTextOfPackage);
            if (packageDir.isEmpty()) {
                return false;
            }
            packageFileSetter.set(packageDir.get());
        }
        return true;
    }

    /**
     * creates the directory for a given package name
     *
     * @param baseDir              directory where to start path from
     * @param dir                  directory text for package
     * @param messageTextOfPackage Message text for logger
     * @return Optional of created directory. If not successful the result will be {@link Optional#empty()}
     */
    private Optional<File> createDirForPackage(File baseDir, String dir, String messageTextOfPackage) {
        if (dir.contains(".")) {
            dir = dir.replace(".", File.separator);
        }
        File packageDir = createFile(baseDir, dir);
        if (!packageDir.exists()) {
            logger.debug(String.format("%s package does not exists and will be created at \"%s\"", messageTextOfPackage, packageDir.getAbsoluteFile()));
            if (!packageDir.mkdirs()) {
                logger.error(String.format("Could not create %s package \"%s\"", messageTextOfPackage, packageDir.getAbsoluteFile()));
                return Optional.empty();
            }
        }
        logger.debug(String.format("Use %s package \"%s\"", messageTextOfPackage, packageDir.getAbsoluteFile()));
        return Optional.of(packageDir);
    }

    /**
     * Creates all data access objects
     *
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    private boolean createDataAccessObjects() {
        if (!genDao) {
            logger.debug("skip dao generation");
            return true;
        }
        String packageName = config.getBasePackage() + "." + config.getDaoPackage();
        if (daoPackageDir.isEmpty() && !generateJavaFileObject) {
            logger.error("Empty daoPackageDir");
            return false;
        }
        if (!daoCreator.createDataAccessObjectInterface(packageName, daoPackageDir)) {
            logger.error("Dao interface could not be created");
            return false;
        }
        return createEntitiesObjects(e -> createDataAccessObject(e, packageName, daoPackageDir), daoPackageDir);
    }

    /**
     * Creates the data access objects
     *
     * @param entity      Entity to generate
     * @param packageName name of the package to use
     * @param packageDir  directory where to write at
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    private boolean createDataAccessObject(Entity entity, String packageName, Optional<File> packageDir) {
        return daoCreator.createDataAccessObject(entity, packageName, packageDir);
    }

    /**
     * Creates all data transport objects
     *
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    private boolean createDataTransportObjects() {
        if (!genDto) {
            logger.debug("skip dto generation");
            return true;
        }
        String packageName = config.getBasePackage() + "." + config.getDtoPackage();
        if (dtoPackageDir.isEmpty() && !generateJavaFileObject) {
            logger.error("Empty dtoPackageDir");
            return false;
        }
        if (!dtoCreator.createDataTransportObjectInterface(packageName, dtoPackageDir)) {
            logger.error("Dto interface could not be created");
            return false;
        }
        return createEntitiesObjects(e -> createDataTransportObject(e, packageName, dtoPackageDir), dtoPackageDir);
    }

    /**
     * Creates the data transport objects
     *
     * @param entity      Entity to generate
     * @param packageName name of the package to use
     * @param packageDir  directory where to write at
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    private boolean createDataTransportObject(Entity entity, String packageName, Optional<File> packageDir) {
        return dtoCreator.createDataTransportObject(entity, packageName, packageDir);
    }

    /**
     * Creates all domain objects
     *
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    private boolean createDomainObjects() {
        if (!genDomain) {
            logger.debug("skip domain generation");
            return true;
        }
        String packageName = config.getBasePackage() + "." + config.getDomainPackage();
        if (domainPackageDir.isEmpty() && !generateJavaFileObject) {
            logger.error("Empty domainPackageDir");
            return false;
        }
        if (!domainCreator.createDomainObjectInterface(packageName, domainPackageDir)) {
            logger.error("Domain interface could not be created");
            return false;
        }
        return createEntitiesObjects(e -> createDomainObject(e, packageName, domainPackageDir), domainPackageDir);
    }

    /**
     * Creates the domain objects
     *
     * @param entity      Entity to generate
     * @param packageName name of the package to use
     * @param packageDir  directory where to write at
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    private boolean createDomainObject(Entity entity, String packageName, Optional<File> packageDir) {
        return domainCreator.createDomainObject(entity, packageName, packageDir);
    }

    /**
     * Iterates throw the entities of the config to create their sources
     *
     * @param createEntityCaller Functional interface to to create the the source for some entity
     * @param packageDir         the actual directory of the package where to write at
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    private boolean createEntitiesObjects(CreateEntityCaller createEntityCaller, Optional<File> packageDir) {
        boolean result = true;

        logger.debug(String.format("%d entities are to generate", config.getEntities().size()));
        for (Entity e : config.getEntities()) {
            if (!createEntityCaller.create(e)) {
                result = false;
            }
        }

        logger.debug(String.format("%d groupings are to generate", config.getGroupings().size()));
        for (Grouping g : config.getGroupings()) {
            if (!generateJavaFileObject && packageDir.isPresent()) {
                Optional<File> groupingDir = createDirForPackage(packageDir.get(), g.getGroupingPackage()
                        , "dao " + g.getGroupingPackage());
                if (groupingDir.isEmpty()) {
                    logger.debug("skip entities of grouping " + g.getGroupingPackage());
                    result = false;
                    continue;
                }
            }
            logger.debug(String.format("%d entities are to generate at grouping %s", config.getEntities().size(), g.getGroupingPackage()));
            for (Entity e : g.getEntities()) {
                if (!createEntityCaller.create(e)) {
                    result = false;
                }
            }
        }
        return result;
    }

    /**
     * Creates the access mapper if data access and domain objects are to generate
     *
     * @return {@code true} if generation of the mapper was successful
     */
    private boolean createAccessMapper() {
        if (!genDao || !genDomain) {
            logger.debug("skip access mapper generation");
            return true;
        }
        if (mapperPackageDir.isEmpty() && !generateJavaFileObject) {
            logger.error(MAPPER_DIRECTORY_NEEDED);
            return false;
        }

        String mapperPackageName = config.getBasePackage() + MAPPER_SUB_PACKAGE;
        String daoPackageName = config.getBasePackage() + "." + config.getDaoPackage();
        String domainPackageName = config.getBasePackage() + "." + config.getDomainPackage();

        logger.debug("create abstract access mapper");
        boolean result = accessMapperCreator.createAbstractAccessMapper(mapperPackageName, mapperPackageDir, daoPackageName, domainPackageName);

        logger.debug(String.format("%d entities are put at common access mapper", config.getEntities().size()));
        result = accessMapperCreator.createAccessMapper(config.getEntities(), null, mapperPackageName
                , daoPackageName, domainPackageName, mapperPackageDir)
                && result;

        logger.debug(String.format("%d groupings getting their own access mapper", config.getGroupings().size()));
        for (Grouping g : config.getGroupings()) {
            logger.debug(String.format("%d entities are put at %s groupings access mapper", config.getEntities().size(), g.getGroupingPackage()));
            if (!accessMapperCreator.createAccessMapper(g.getEntities(), g.getGroupingPackage(), mapperPackageName
                    , daoPackageName, domainPackageName, mapperPackageDir)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Creates the access mapper if data transport and domain objects are to generate
     *
     * @return {@code true} if generation of the mapper was successful
     */
    private boolean createTransportMapper() {
        if (!genDto || !genDomain) {
            logger.debug("skip transport mapper generation");
            return true;
        }
        if (mapperPackageDir.isEmpty() && !generateJavaFileObject) {
            logger.error(MAPPER_DIRECTORY_NEEDED);
            return false;
        }

        String mapperPackageName = config.getBasePackage() + MAPPER_SUB_PACKAGE;
        String dtoPackageName = config.getBasePackage() + "." + config.getDtoPackage();
        String domainPackageName = config.getBasePackage() + "." + config.getDomainPackage();

        logger.debug("create abstract transport mapper");
        boolean result = transportMapperCreator.createAbstractTransportMapper(mapperPackageName, mapperPackageDir, dtoPackageName, domainPackageName);

        logger.debug(String.format("%d entities are put at common transport mapper", config.getEntities().size()));
        result = transportMapperCreator.createTransportMapper(config.getEntities(), null, mapperPackageName
                , dtoPackageName, domainPackageName, mapperPackageDir)
                && result;

        logger.debug(String.format("%d groupings getting their own transport mapper", config.getGroupings().size()));
        for (Grouping g : config.getGroupings()) {
            logger.debug(String.format("%d entities are put at %s groupings transport mapper", config.getEntities().size(), g.getGroupingPackage()));
            if (!transportMapperCreator.createTransportMapper(g.getEntities(), g.getGroupingPackage(), mapperPackageName
                    , dtoPackageName, domainPackageName, mapperPackageDir)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Creates the abstract mapper if "data transport and domain objects" or "data transport and domain objects" are to generate
     *
     * @return {@code true} if generation of the mapper was successful
     */
    private boolean createAbstractMapper() {
        if ((!genDao || !genDomain) && (!genDto || !genDomain)) {
            logger.debug("skip abstract mapper generation");
            return true;
        }
        if (mapperPackageDir.isEmpty() && !generateJavaFileObject) {
            logger.error(MAPPER_DIRECTORY_NEEDED);
            return false;
        }

        String mapperPackageName = config.getBasePackage() + MAPPER_SUB_PACKAGE;

        logger.debug("create abstract transport mapper");
        return commonMapperCreator.createAbstractMapper(mapperPackageName, mapperPackageDir);
    }

    @FunctionalInterface
    private interface CreateEntityCaller {
        boolean create(Entity entity);
    }

    /**
     * Creator Method to be make mocking easier at unit test
     *
     * @return created dao creator
     */
    protected DaoCreator createDaoCreator() {
        return new DaoCreator(config, logger);
    }

    /**
     * Creator Method to be make mocking easier at unit test
     *
     * @return created dto creator
     */
    protected DtoCreator createDtoCreator() {
        return new DtoCreator(config, logger);
    }

    /**
     * Creator Method to be make mocking easier at unit test
     *
     * @return created dao creator
     */
    protected DomainCreator createDomainCreator() {
        return new DomainCreator(config, logger);
    }

    /**
     * Creator Method to be make mocking easier at unit test
     *
     * @return created access mapper creator
     */
    protected CommonMapperCreator createCommonMapperCreator() {
        return new CommonMapperCreator(config, logger);
    }

    /**
     * Creator Method to be make mocking easier at unit test
     *
     * @return created access mapper creator
     */
    protected AccessMapperCreator createAccessMapperCreator() {
        return new AccessMapperCreator(config, logger);
    }

    /**
     * Creator Method to be make mocking easier at unit test
     *
     * @return created access mapper creator
     */
    protected TransportMapperCreator createTransportMapperCreator() {
        return new TransportMapperCreator(config, logger);
    }

    /**
     * Creator Method to be make mocking easier at unit test
     *
     * @param dir      directory of the file
     * @param fileName name of the file
     * @return created file
     */
    protected File createFile(File dir, String fileName) {
        return new File(dir, fileName);
    }

    @FunctionalInterface
    public interface PackageFileSetter {
        void set(File packageFile);
    }
}
