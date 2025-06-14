package com.github.ma_vin.util.layer_generator.config.elements;

import static com.github.ma_vin.util.layer_generator.config.ConfigElementsUtil.*;

import com.github.ma_vin.util.layer_generator.config.elements.fields.Field;
import com.github.ma_vin.util.layer_generator.config.elements.references.Reference;
import com.github.ma_vin.util.layer_generator.config.IConfigLog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.xml.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Description of an entity which will be used to generate domain object, dto or dao
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "com.github.ma_vin.util.layer_generator.gen.model")
@Data
@EqualsAndHashCode(exclude = {"grouping", "parentRefs", "realParent", "versions"})
@ToString(exclude = {"references", "parentRefs", "fields", "indices", "realParent", "versions"})
public class Entity implements IConfigLog {

    /**
     * Base name of the objects, which will be extended by some postfix for dto or dao
     */
    @XmlAttribute(required = true)
    private String baseName;

    /**
     * Name of Table at database. if {@code null} the {@code baseName} is used
     */
    @XmlAttribute
    private String tableName;

    /**
     * Which objects should be generated
     */
    @XmlAttribute
    private Models models;

    /**
     * Description of the attribute
     */
    @XmlAttribute
    private String description;

    /**
     * Prefix which will be added in front of dto and domain id
     */
    @XmlAttribute
    private String identificationPrefix;

    /**
     * super entity
     */
    @XmlAttribute
    private String parent;

    /**
     * indication if the generated java class should be abstract
     */
    @XmlAttribute
    private Boolean isAbstract = Boolean.FALSE;

    /**
     * Entity to derive from.
     * Generated Mapper work only one way, from domain model to transport one
     */
    @XmlAttribute
    private String derivedFrom;

    /**
     * Indicator whether to generate identification at entity, if the entity exists only at dto model
     */
    @XmlAttribute
    private Boolean genIdIfDto = Boolean.TRUE;

    /**
     * Attributes of the entity
     */
    @XmlElementWrapper
    @XmlElement(name = "field")
    private List<Field> fields;

    /**
     * Indices of the entity
     */
    @XmlElementWrapper
    @XmlElement(name = "index")
    private List<Index> indices;

    /**
     * References to other entities
     */
    @XmlElementWrapper
    @XmlElement(name = "reference")
    private List<Reference> references;

    /**
     * Versions of data transport objects for this entity
     */
    @XmlElementWrapper
    @XmlElement(name = "version")
    private List<Version> versions;

    @XmlTransient
    private Version actualVersion;

    @XmlTransient
    private List<Reference> parentRefs;

    @XmlTransient
    private Entity realParent;

    @XmlTransient
    private Entity realDerivedFrom;

    @XmlTransient
    private Grouping grouping;

    /**
     * Filters the parent references and removes versioned parents
     *
     * @return the filtered list
     */
    public List<Reference> getNonVersionedParentRefs() {
        return parentRefs.stream().filter(r -> r.getRealTargetEntity().getActualVersion() == null).toList();
    }

    public boolean hasParent() {
        return parent != null && !parent.trim().isEmpty();
    }

    public boolean hasNoParent() {
        return !hasParent();
    }

    /**
     * Validates this entity
     *
     * @param messages List where to add messages
     * @return {@code true} if this entity is valid. {@code false} otherwise
     */
    public boolean isValid(List<String> messages) {
        return validateRequired(baseName, messages, "baseName")
                && validateNonRequired(tableName, messages, "tableName")
                && validateNonRequired(description, messages, "description")
                && validateNonRequired(identificationPrefix, messages, "identificationPrefix")
                && validateNonRequired(parent, messages, "parent")
                && validateNonRequired(derivedFrom, messages, "derivedFrom")
                && (fields == null || fields.stream().allMatch(f -> f.isValid(messages)))
                && (indices == null || indices.stream().allMatch(i -> i.isValid(messages)))
                && (references == null || (references.stream().allMatch(r -> r.isValid(messages))
                && Reference.isFilterFieldValid(baseName, references, messages)))
                && areVersionsValid(messages);
    }

    /**
     * Validates the {@code versions} property
     *
     * @param messages List where to add messages
     * @return {@code true} if the list {@code versions} is valid. {@code false} otherwise
     */
    private boolean areVersionsValid(List<String> messages) {
        final String propertyName = "versions";
        return (derivedFrom == null || checkNullOrEmpty(versions, messages, propertyName, "derivedFrom property is set"))
                && (!isAbstract || checkNullOrEmpty(versions, messages, propertyName, "isAbstract is true"))
                && (getModels().isDto() || checkNullOrEmpty(versions, messages, propertyName, "its not a data transport model"))
                && (versions == null || versions.stream().allMatch(r -> r.isValid(messages, this)));
    }

    /**
     * Creates a copy of this object and considers the modifications by a given version
     *
     * @param version a version which modifies the entity
     * @return a new modified instance of the actual object
     */
    public Entity copyForVersion(Version version) {
        Entity result = new Entity();

        result.actualVersion = version;
        result.baseName = getBaseName();
        result.tableName = getTableName();
        result.models = getModels();
        result.description = getDescription();
        result.identificationPrefix = getIdentificationPrefix();
        result.parent = null;
        result.isAbstract = Boolean.FALSE;
        result.derivedFrom = null;
        result.genIdIfDto = getGenIdIfDto();
        result.fields = version.getFields();
        result.indices = Collections.emptyList();
        result.references = version.getReferences();
        result.versions = Collections.emptyList();
        result.parentRefs = Collections.emptyList();
        result.realParent = null;
        result.realDerivedFrom = null;
        result.grouping = getGrouping();

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSimpleLogName() {
        return getBaseName();
    }

    public Models getModels() {
        if (derivedFrom != null) {
            return Models.DTO;
        }
        return models != null ? models : Models.DOMAIN_DAO_DTO;
    }

    /**
     * @return the name of the actual version of this entity if there exists any. the basename otherwise
     */
    public String getBaseName() {
        return actualVersion == null ? baseName : actualVersion.getVersionName();
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public String getTableName() {
        return tableName;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public String getDescription() {
        return description;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public String getIdentificationPrefix() {
        return identificationPrefix;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public Boolean getGenIdIfDto() {
        return genIdIfDto;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public List<Field> getFields() {
        return fields;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public List<Reference> getReferences() {
        return references;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public List<Version> getVersions() {
        return versions;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public Grouping getGrouping() {
        return grouping;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public Version getActualVersion() {
        return actualVersion;
    }
}
