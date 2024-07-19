package com.github.ma_vin.util.layer_generator.sample.reference.content.domain;

public class DomainObjectFactory {

	private DomainObjectFactory() {
	}

	public static SourceEntityFilter createSourceEntityFilter() {
		return new SourceEntityFilter();
	}

	public static SourceEntityFilterNotAtTarget createSourceEntityFilterNotAtTarget() {
		return new SourceEntityFilterNotAtTarget();
	}

	public static SourceEntityManyToMany createSourceEntityManyToMany() {
		return new SourceEntityManyToMany();
	}

	public static SourceEntityManyToOne createSourceEntityManyToOne() {
		return new SourceEntityManyToOne();
	}

	public static SourceEntityOneToMany createSourceEntityOneToMany() {
		return new SourceEntityOneToMany();
	}

	public static SourceEntityOneToOne createSourceEntityOneToOne() {
		return new SourceEntityOneToOne();
	}

	public static TargetEntityFilter createTargetEntityFilter() {
		return new TargetEntityFilter();
	}

	public static TargetEntityFilterNotAtTarget createTargetEntityFilterNotAtTarget() {
		return new TargetEntityFilterNotAtTarget();
	}

	public static TargetEntityManyToMany createTargetEntityManyToMany() {
		return new TargetEntityManyToMany();
	}

	public static TargetEntityOneToMany createTargetEntityOneToMany() {
		return new TargetEntityOneToMany();
	}

	public static TargetEntityOneToOne createTargetEntityOneToOne() {
		return new TargetEntityOneToOne();
	}

}
