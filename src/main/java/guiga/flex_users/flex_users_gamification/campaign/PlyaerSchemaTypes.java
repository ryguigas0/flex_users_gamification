package guiga.flex_users.flex_users_gamification.campaign;

import java.util.ArrayList;
import java.util.List;

public class PlyaerSchemaTypes {
    private static List<String> avaliableTypes = new ArrayList<String>();

    static {
        avaliableTypes.add("String");
        avaliableTypes.add("Integer");
        avaliableTypes.add("Double");

    }

    public static boolean isAvaliableType(String typeName) {
        return avaliableTypes.stream().anyMatch(avalType -> typeName.equals(avalType));
    }
}
