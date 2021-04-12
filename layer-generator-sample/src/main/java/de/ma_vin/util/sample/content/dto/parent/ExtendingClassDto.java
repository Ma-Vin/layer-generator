package de.ma_vin.util.sample.content.dto.parent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of ExtendingClass
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuppressWarnings({"java:S2160", "java:S1068"})
@ToString(callSuper = true)
public class ExtendingClassDto extends SuperClassDto {

	private String additionalDescription;

	/**
	 * Identification of ExtendingClass
	 */
	private String identification;

}
