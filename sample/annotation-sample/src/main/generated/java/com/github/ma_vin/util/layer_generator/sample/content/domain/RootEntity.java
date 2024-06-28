package com.github.ma_vin.util.layer_generator.sample.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import java.util.Collection;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Generated domain class of RootEntity
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@EqualsAndHashCode(exclude = {"subEntities"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"subEntities"})
public class RootEntity implements IIdentifiable {

	public static final String ID_PREFIX = "R";

	private String description;

	/**
	 * Identification of RootEntity
	 */
	private String identification;

	private String rootName;

	@Setter(AccessLevel.PROTECTED)
	private Collection<SubEntity> subEntities = new HashSet<>();

	/**
	 * Adds a SubEntity to subEntities
	 * 
	 * @param subEntity SubEntity to add
	 */
	public boolean addSubEntities(SubEntity subEntity) {
		return subEntities.add(subEntity);
	}

	/**
	 * Removes a SubEntity from subEntities
	 * 
	 * @param subEntity SubEntity to remove
	 */
	public boolean removeSubEntities(SubEntity subEntity) {
		return subEntities.remove(subEntity);
	}

}
