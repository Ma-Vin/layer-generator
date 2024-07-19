package com.github.ma_vin.util.layer_generator.sample.extension.content.domain;

import com.github.ma_vin.util.layer_generator.sample.extension.ExtendedEntity;

public class DomainObjectFactory {

	private DomainObjectFactory() {
	}

	public static ToExtendEntity createToExtendEntity() {
		return new ExtendedEntity();
	}

}
