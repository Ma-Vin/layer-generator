package de.ma_vin.util.layer.generator.config.elements;

import de.ma_vin.util.layer.generator.config.IConfigLog;
import de.ma_vin.util.layer.generator.config.elements.fields.Field;
import de.ma_vin.util.layer.generator.config.elements.fields.VersionField;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import de.ma_vin.util.layer.generator.config.elements.references.VersionReference;
import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
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
@ToString(exclude = {"parentEntity", "baseVersion", "addedFields", "addedReferences", "fields", "references", "versionEntity"})
public class Version implements IConfigLog {

    /**
     * (Optional) name of the versioned entity. If not set, it will be generated from {@link Entity#baseName} and {@link Version#versionId}
     */
    private String versionName;

    /**
     * The Entity whose version this is.
     */
    @XmlTransient
    private Entity parentEntity;

    /**
     * The derived Entity from this version.
     */
    @XmlTransient
    private Entity versionEntity;

    /**
     * identification of the version
     */
    @XmlAttribute(required = true)
    private String versionId;

    /**
     * (Optional) name of the version which is to use as reference to derive difference from. If not set, the owner entity will be used.
     */
    @XmlAttribute
    private String baseVersionId;

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
    private List<VersionField> addedFields;

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
    private List<VersionReference> addedReferences;

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
        return validateRequired(versionId, messages, "versionId")
                && checkBaseVersion(messages, parentEntity.getVersions())
                && validateNonRequired(versionName, messages, "versionName")
                && validateNamesCompareToElementList(removedFieldNames, messages, "removedFieldNames", determineParentFields(parentEntity), Field::getFieldName)
                && validateList(addedFields, messages, VersionField::isValid)
                && validateNamesCompareToElementList(removedReferenceNames, messages, "removedReferenceNames", determineBaseReferences(parentEntity), Reference::getReferenceName)
                && validateList(addedReferences, messages, VersionReference::isValid);
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
        if (baseVersionId != null && otherEntityVersions.stream().noneMatch(v -> baseVersionId.equals(v.versionId))) {
            messages.add(String.format("There does not exists an other version %s which is referenced as baseVersionId at %s", baseVersionId, versionId));
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
        sb.append(versionId.substring(0, 1).toUpperCase());
        if (versionId.length() > 1) {
            sb.append(versionId.substring(1));
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
        Optional<Version> determinedBaseVersion = determineBaseVersion(parentEntity);
        return determinedBaseVersion.isEmpty() ? parentEntity.getFields() : determinedBaseVersion.get().determineFields(parentEntity);
    }

    /**
     * Determines the parent version
     *
     * @param parentEntity the parent {@link Entity}
     * @return An optional of the parent version. if there is none {@link Optional#empty()} will be returned.
     */
    public Optional<Version> determineBaseVersion(Entity parentEntity) {
        return baseVersionId == null ? Optional.empty() : parentEntity.getVersions().stream().filter(v -> baseVersionId.equals(v.versionId)).findFirst();
    }

    /**
     * Determines all relevant {@link Field}s for this {@link Version}
     *
     * @param parentEntity the parent {@link Entity}
     * @return list of {@link Field}s by iterating through all parent {@link Version}s and the parent {@link Entity}.
     * They are reduced  by {@link Version#removedFieldNames} and completed  by {@link Version#addedFields}
     */
    public List<Field> determineFields(Entity parentEntity) {
        List<Field> result = new ArrayList<>(addedFields == null ? Collections.emptyList() : addedFields.stream().map(VersionField::getAsField).toList());

        determineParentFields(parentEntity).stream()
                .filter(f -> removedFieldNames == null || !removedFieldNames.contains(f.getFieldName()))
                .filter(f -> result.stream().noneMatch(f2 -> f2.getFieldName().equals(f.getFieldName())))
                .forEach(result::add);
        return result;
    }

    /**
     * Determines the {@link Reference}s which are inherited by the base version if exists, from parent {@link Entity} otherwise
     *
     * @param parentEntity the parent {@link Entity}
     * @return List of {@link Reference}s by iterating through all parent {@link Version}s and the parent {@link Entity}
     */
    private List<Reference> determineBaseReferences(Entity parentEntity) {
        Optional<Version> determinedBaseVersion = determineBaseVersion(parentEntity);
        return determinedBaseVersion.isEmpty() ? parentEntity.getReferences() : determinedBaseVersion.get().determineReferences(parentEntity);
    }

    /**
     * Determines all relevant {@link Reference}s for this {@link Version}
     *
     * @param parentEntity the parent {@link Entity}
     * @return list of {@link Reference}s by iterating through all parent {@link Version}s and the parent {@link Entity}.
     * They are reduced  by {@link Version#removedReferenceNames} and completed  by {@link Version#addedReferences}
     */
    public List<Reference> determineReferences(Entity parentEntity) {
        List<Reference> result = new ArrayList<>(addedReferences == null ? Collections.emptyList() : addedReferences.stream().map(VersionReference::getAsReference).toList());

        determineBaseReferences(parentEntity).stream()
                .filter(r -> removedReferenceNames == null || !removedReferenceNames.contains(r.getReferenceName()))
                .filter(r -> result.stream().noneMatch(r2 -> r2.getReferenceName().equals(r.getReferenceName())))
                .forEach(result::add);

        return result;
    }

    /**
     * Determines the version of the target for a given reference
     *
     * @param reference the references whose target version is asked for
     * @return an optional of the determined version. {@link Optional#empty()} if there is non
     */
    public Optional<Version> determineReferenceTargetVersion(Reference reference) {
        String targetVersionId = addedReferences.stream()
                .filter(r -> r.getReferenceName().equals(reference.getReferenceName()) && r.getDivergentTargetVersion() != null)
                .map(VersionReference::getDivergentTargetVersion)
                .findFirst()
                .orElse(getVersionId());

        List<Version> versions;
        if (reference.getRealTargetEntity().getActualVersion() != null) {
            versions = reference.getRealTargetEntity().getActualVersion().getParentEntity().getVersions();
        } else {
            versions = reference.getRealTargetEntity().getVersions();
        }
        return versions.stream()
                .filter(v -> v.getVersionId().equals(targetVersionId))
                .findFirst();
    }

    @Override
    public List<String> getFieldNamesToLogComplete() {
        return Collections.singletonList("versionEntity");
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public String getVersionName() {
        return versionName;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public String getVersionId() {
        return versionId;
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
    public Entity getParentEntity(){
        return parentEntity;
    }
}
