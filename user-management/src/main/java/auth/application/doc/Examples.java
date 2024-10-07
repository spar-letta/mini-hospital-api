package auth.application.doc;

public class Examples {
    public static final String MISSING_EXAMPLE = "Missing Example";
    public static final String GENERIC_200_ERROR = "{}";
    public static final String GENERIC_400_ERROR = "{\"messages\":[\"\"]}";
    public static final String GENERIC_401_ERROR = "{\"messages\":[\"\"]}";
    public static final String GENERIC_403_ERROR = "{\"messages\":[\"\"]}";
    public static final String GENERIC_500_ERROR = "{\"messages\":[\"\"]}";
    public static final String USER_OK_RESPONSE =  "{\"publicId\":\"81c131a7-db3d-4613-a70c-8f017e6eaabf\",\"dateCreated\":\"2023-12-22 13:12:09\",\"dateModified\":\"2023-12-22 13:12:09\",\"createdBy\":\"anonymousUser\",\"modifiedBy\":\"anonymousUser\",\"firstName\":\"Sample Firstname\",\"lastName\":\"Sample Lastname\",\"otherName\":\"Sample Othername\",\"userName\":\"Sample User\",\"contactEmail\":\"sample@Contacte.mail\",\"contactPhonenumber\":\"+2547291057862\",\"verifiedEmail\":false,\"verifiedPhonenumber\":false,\"status\":\"Active\"}";
    public static final String CREATE_USER_REQUEST = "{\"firstName\":\"Sample Firstname\",\"lastName\":\"Sample Lastname\",\"otherName\":\"Sample Othername\",\"userName\":\"Sample User\",\"contactEmail\":\"sample@Contacte.mail\",\"contactPhonenumber\":\"+2547291057862\"}";
    public static final String USERS_OK_RESPONSE = "";
    public static final String USER_APPLICATION_PROFILE_OK_RESPONSE = "{\"user\":{\"fullName\":\"Test Firstname Test Lastname Test Othername\"},\"tenant\":{\"tenantId\":\"14xLRTFnVsYUalMlHsywISerMEwuBXKRaNCa\",\"name\":\"Thika Institute\",\"description\":\"Thika Institute\",\"metaData\":{}},\"application\":{\"name\":\"Sample Application 5\"},\"role\":{\"name\":\"Role 5\"}}";
    public static final String UPDATE_PROFILE_REQUEST = "";
}
