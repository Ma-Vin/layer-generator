package de.ma_vin.util.layer.generator.config.elements;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.ma_vin.util.layer.generator.config.ConfigElementsUtil.*;

/**
 * class to provide versioning of the data transport objects
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
@EqualsAndHashCode(exclude = {"parentEntity", "baseVersion", "fields", "references"})
@ToString(exclude = {"parentEntity", "baseVersion", "addedFields", "addedReferences", "fields", "references"})
public class Version {

    /**
     * (Optional) name of the versioned entity. If not set, it will be generated from {@link Entity#baseName} and {@link Version#version}
     */
    private String versionName;

    /**
     * The Entity whose version this is.
     */
    @XmlTransient
    private Entity parentEntity;

    /**
     * identification of the version
     */
    @XmlAttribute(required = true)
    private String version;

    /**
     * (Optional) name of the version which is to use as reference to derive difference from. If not set, the owner entity will be used.
     */

    private String baseVersionName;

    /**
     * (Optional) version which is to use as reference to derive difference from. If not set, the owner entity will be used.
     */
    @XmlTransient
    private Optional<Version> baseVersion;

    /**
     * List of {@link Field#fieldName} which should be removed
     */
    @XmlElementWrapper
    @XmlElement(name = "removedField")
    private List<String> removedFieldNames;

    /**
     * List of {@link Field}s which should be added
     */
    @XmlElementWrapper
    @XmlElement(name = "addedField")
    private List<Field> addedFields;

    /**
     * List of all fields which contains parents fields also
     */
    @XmlTransient
    private List<Field> fields;

    /**
     * List of single {@link Reference#referenceName} which should be removed
     */
    @XmlElementWrapper
    @XmlElement(name = "removedReference")
    private List<String> removedReferenceNames;

    /**
     * List of single {@link Reference}s which should be added
     */
    @XmlElementWrapper
    @XmlElement(name = "addedReference")
    private List<Reference> addedReferences;

    /**
     * List of all references which contains parents references also
     */
    @XmlTransient
    private List<Reference> references;


    /**
     * Validates this version object
     *
     * @param messages     list where to a new messages at
     * @param parentEntity list of versions which exists at the parent {@link Entity}
     * @return {@code true} if this version is valid.
     * Otherwise {@code false}
     */
    public boolean isValid(List<String> messages, Entity parentEntity) {
        return validateRequired(version, messages, "version")
                && checkBaseVersion(messages, parentEntity.getVersions())
                && validateNonRequired(versionName, messages, "versionName")
                && validateNamesCompareToElementList(removedFieldNames, messages, "removedFieldNames", determineParentFields(parentEntity), Field::getFieldName)
                && validateList(addedFields, messages, Field::isValid)
                && validateNamesCompareToElementList(removedReferenceNames, messages, "removedReferenceNames", determineParentReferences(parentEntity), Reference::getReferenceName)
                && validateList(addedReferences, messages, Reference::isValid);
    }

    /**
     * checks whether the base version which is used to derive this version exists or not
     *
     * @param messages            list where to a new messages at
     * @param otherEntityVersions list of version existing at parent {@link Entity}
     * @return {@code true} if the base version exists.
     * Otherwise {@code false}
     */
    private boolean checkBaseVersion(List<String> messages, List<Version> otherEntityVersions) {
        if (baseVersionName != null && otherEntityVersions.stream().noneMatch(v -> baseVersionName.equals(v.versionName))) {
            messages.add(String.format("There does not exists an other version %s which is referenced as baseVersionName at %s", baseVersionName, versionName));
            return false;
        }
        return true;
    }

    /**
     * Generates a version name if {@link Version#versionName} is not {@code null}
     */
    public void generateVersionName() {
        if (versionName != null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(parentEntity.getBaseName());
        sb.append(version.substring(0, 1).toUpperCase());
        if (version.length() > 1) {
            sb.append(version.substring(1));
        }
        versionName = sb.toString();
    }

    /**
     * Determines the {@link Field}s which are inherited by the base version if exists, from parent {@link Entity} otherwise
     *
     * @param parentEntity the parent {@link Entity}
     * @return List of {@link Field}s by iterating through all parent {@link Version}s and the parent {@link Entity}
     */
    private List<Field> determineParentFields(Entity parentEntity) {
        Optional<Version> baseVersion = determineBaseVersion(parentEntity);
        return baseVersion.isEmpty() ? parentEntity.getFields() : baseVersion.get().determineFields(parentEntity);
    }

    /**
     * Determines the parent version
     *
     * @param parentEntity the parent {@link Entity}
     * @return An optional of the parent version. if there is none {@link Optional#empty()} will be returned.
     */
    public Optional<Version> determineBaseVersion(Entity parentEntity) {
        return baseVersionName == null ? Optional.empty() : parentEntity.getVersions().stream().filter(v -> baseVersionName.equals(v.versionName)).findFirst();
    }

    /**
     * Determines all relevant {@link Field}s for this {@link Version}
     *
     * @param parentEntity the parent {@link Entity}
     * @return list of {@link Field}s by iterating through all parent {@link Version}s and the parent {@link Entity}.
     * They are reduced  by {@link Version#removedFieldNames} and completed  by {@link Version#addedFields}
     */
    public List<Field> determineFields(Entity parentEntity) {
        List<Field> result = new ArrayList<>(addedFields);
        determineParentFields(parentEntity).stream()
                .filter(f -> !removedFieldNames.contains(f.getFieldName()))
                .forEach(result::add);
        return result;
    }

    /**
     * Determines the {@link Reference}s which are inherited by the base version if exists, from parent {@link Entity} otherwise
     *
     * @param parentEntity the parent {@link Entity}
     * @return List of {@link Reference}s by iterating through all parent {@link Version}s and the parent {@link Entity}
     */
    private List<Reference> determineParentReferences(Entity parentEntity) {
        Optional<Version> baseVersion = determineBaseVersion(parentEntity);
        return baseVersion.isEmpty() ? parentEntity.getReferences() : baseVersion.get().determineReferences(parentEntity);
    }

    /**
     * Determines all relevant {@link Reference}s for this {@link Version}
     *
     * @param parentEntity the parent {@link Entity}
     * @return list of {@link Reference}s by iterating through all parent {@link Version}s and the parent {@link Entity}.
     * They are reduced  by {@link Version#removedReferenceNames} and completed  by {@link Version#addedReferences}
     */
    public List<Reference> determineReferences(Entity parentEntity) {
        List<Reference> result = new ArrayList<>(addedReferences);
        determineParentReferences(parentEntity).stream()
                .filter(r -> !removedReferenceNames.contains(r.getReferenceName()))
                .forEach(result::add);
        return result;
    }
}
