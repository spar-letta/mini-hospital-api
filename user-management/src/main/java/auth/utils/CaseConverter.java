package auth.utils;

import org.apache.commons.lang3.StringUtils;

public class CaseConverter {

    public static String camelCaseToSentence(String value) {
        return StringUtils.join(
                StringUtils.splitByCharacterTypeCamelCase(value),
                ' ');
    }
}
