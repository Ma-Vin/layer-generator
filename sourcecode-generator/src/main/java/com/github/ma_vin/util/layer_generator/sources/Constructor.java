package com.github.ma_vin.util.layer_generator.sources;

public class Constructor extends Method {

    public Constructor(String className) {
        super(getUpperFirst(className));
        isStatic = false;
        methodType = "";
    }

    public Constructor(String className, String constructorLine) {
        super(getUpperFirst(className), constructorLine);
        isStatic = false;
        methodType = "";
    }


    @Override
    protected String getBaseDeclaration() {
        return String.format("%s %s(", qualifier.getText(), methodName);
    }
}
