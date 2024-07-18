package com.github.ma_vin.util.layer_generator.sample.entity.content.domain;

public class DomainObjectFactory {

	private DomainObjectFactory() {
	}

	public static ExtendingEntity createExtendingEntity() {
		return new ExtendingEntity();
	}

	public static IndexEntity createIndexEntity() {
		return new IndexEntity();
	}

	public static RootEntity createRootEntity() {
		return new RootEntity();
	}

}
