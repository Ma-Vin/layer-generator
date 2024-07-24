package com.github.ma_vin.util.layer_generator.sources;

/**
 * Constructor representation for a {@link Clazz}
 */
public class Constructor extends Method {

    /**
     * Constructor for a non-static class
     *
     * @param className Name of the class
     */
    public Constructor(String className) {
        super(getUpperFirst(className));
        isStatic = false;
        methodType = "";
    }

    /**
     * Constructor for a non-static class
     *
     * @param className       Name of the class
     * @param constructorLine one line body of constructor
     */
    public Constructor(String className, String constructorLine) {
        super(getUpperFirst(className), constructorLine);
        isStatic = false;
        methodType = "";
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected String getBaseDeclaration() {
        return String.format("%s %s(", qualifier.getText(), methodName);
    }
}
