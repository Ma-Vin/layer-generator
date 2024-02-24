package de.ma_vin.util.sample.content.domain;

public class DomainObjectFactory {

	private DomainObjectFactory() {
	}

	public static Root createRoot() {
		return new Root();
	}

	public static RootExt createRootExt() {
		return new RootExt();
	}

	public static SingleRefOne createSingleRefOne() {
		return new SingleRefOne();
	}

	public static SingleRefTwo createSingleRefTwo() {
		return new SingleRefTwo();
	}

}
