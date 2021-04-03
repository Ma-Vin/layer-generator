package de.ma_vin.util.layer.generator.sources;

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
    protected String getDeclaration() {
        return String.format("%s %s(%s) {", qualifier.getText(), methodName, getParametersText(parameters));
    }
}
