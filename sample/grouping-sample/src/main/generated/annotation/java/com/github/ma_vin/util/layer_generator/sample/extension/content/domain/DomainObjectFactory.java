package com.github.ma_vin.util.layer_generator.sample.extension.content.domain;

import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.sub.SubEntity;

public class DomainObjectFactory {

	private DomainObjectFactory() {
	}

	public static CommonEntity createCommonEntity() {
		return new CommonEntity();
	}

	public static SubEntity createSubEntity() {
		return new SubEntity();
	}

}
