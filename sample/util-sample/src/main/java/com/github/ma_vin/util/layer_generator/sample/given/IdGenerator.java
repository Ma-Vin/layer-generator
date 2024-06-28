package com.github.ma_vin.util.layer_generator.sample.given;

/**
 * Some Dummy id generator
 */
public class IdGenerator {

    private IdGenerator(){
    }

    public static String generateIdentification(Long id, String prefix) {
        return prefix + id.toString();
    }

    public static Long generateId(String identification, String prefix) {
        if (identification.startsWith(prefix)) {
            return Long.parseLong(identification.substring(prefix.length()));
        }
        return 0L;
    }
}
