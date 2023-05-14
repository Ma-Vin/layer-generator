package de.ma_vin.util.sample.content.domain;

public class DomainObjectFactory {

	private DomainObjectFactory() {
	}

	public static RootEntity createRootEntity() {
		return new RootEntity();
	}

	public static SubEntity createSubEntity() {
		return new SubEntity();
	}

}
