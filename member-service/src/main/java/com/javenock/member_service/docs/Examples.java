package com.javenock.member_service.docs;

public class Examples {
    public static final String CREATE_PATIENT_REQUEST = "{\"nationalId\":\"9222229999\",\"firstName\":\"kamau\",\"lastName\":\"name2\",\"otherName\":\"jina nyingine\",\"gender\":\"MALE\",\"emailAddress\":\"kamau.name2@gmail.com\",\"phoneNumber\":\"0786512343\",\"dateOfBirth\":\"29-09-2004\",\"maritalStatus\":\"MARRIED\",\"patientAddress\":{\"postalCode\":\"12345\",\"physicalAddress\":\"Malysie Kenya\",\"country\":\"KENYA\"}}";
    public static final String PATIENT_RESPONSE = "{\"publicId\":\"abd511ba-c963-4bfc-88d4-1d8a534bca70\",\"dateCreated\":\"2024-09-29 18:58:26\",\"createdBy\":null,\"modifiedBy\":null,\"deleted\":false,\"nationalId\":9222229999,\"firstName\":\"kamau\",\"lastName\":\"name2\",\"otherName\":\"jina nyingine\",\"gender\":\"MALE\",\"emailAddress\":\"kamau.name2@gmail.com\",\"phoneNumber\":\"0786512343\",\"dateOfBirth\":\"29/09/2004\",\"maritalStatus\":\"MARRIED\",\"address\":[],\"fullName\":\"kamau name2 jina nyingine\",\"age\":20}";
    public static final String SINGLE_PATIENT_RESPONSE = "{\"publicId\":\"abd511ba-c963-4bfc-88d4-1d8a534bca70\",\"dateCreated\":\"2024-09-29 18:58:26\",\"createdBy\":null,\"modifiedBy\":null,\"deleted\":false,\"nationalId\":9222229999,\"firstName\":\"kamau\",\"lastName\":\"name2\",\"otherName\":\"jina nyingine\",\"gender\":\"MALE\",\"emailAddress\":\"kamau.name2@gmail.com\",\"phoneNumber\":\"0786512343\",\"dateOfBirth\":\"29/09/2004\",\"maritalStatus\":\"MARRIED\",\"address\":[{\"postalCode\":\"12345\",\"physicalAddress\":\"Malysie Kenya\",\"country\":\"KENYA\"}],\"fullName\":\"kamau name2 jina nyingine\",\"age\":20}";
    public static final String PATIENTS_RESPONSE = "{\"totalElements\":1,\"pageSize\":1,\"totalPages\":1,\"last\":true,\"first\":true,\"pageNumber\":0,\"content\":[{\"publicId\":\"05812d73-dcbd-4abd-8d2f-a4f0c0b64d20\",\"dateCreated\":\"2024-09-30 09:12:13\",\"createdBy\":null,\"modifiedBy\":null,\"deleted\":false,\"nationalId\":9222229999,\"firstName\":\"kamau\",\"lastName\":\"name2\",\"otherName\":\"jina nyingine\",\"gender\":\"MALE\",\"emailAddress\":\"kamau.name2@gmail.com\",\"phoneNumber\":\"0786512343\",\"dateOfBirth\":\"30/09/2004\",\"maritalStatus\":\"MARRIED\",\"address\":[{\"postalCode\":\"12345\",\"physicalAddress\":\"Malysie Kenya\",\"country\":\"KENYA\"}],\"fullName\":\"kamau name2 jina nyingine\",\"age\":20}]}";
    public static final String UPDATE_PATIENT_RESPONSE = "{\"publicId\":\"917e5977-a736-4154-9bd3-138fea18a90d\",\"dateCreated\":\"2024-09-30 16:14:44\",\"createdBy\":null,\"modifiedBy\":null,\"deleted\":false,\"nationalId\":1001111,\"firstName\":\"Pamela\",\"lastName\":\"Mwakili\",\"otherName\":\"Jimon nyingine\",\"gender\":\"FEMALE\",\"emailAddress\":\"caren2@gmail.com\",\"phoneNumber\":\"0708543231\",\"dateOfBirth\":\"30/09/2001\",\"maritalStatus\":\"SINGLE\",\"address\":[{\"postalCode\":\"70-2000\",\"physicalAddress\":\"Mwea Soko\",\"country\":\"TANZANIA\"},{\"postalCode\":\"24000\",\"physicalAddress\":\"Mayanja\",\"country\":\"KENYA\"}],\"fullName\":\"Pamela Mwakili Jimon nyingine\",\"age\":23}";
    public static final String UPDATE_PATIENT_BASIC_DETAILS_REQUEST = "{\"nationalId\":\"1001111\",\"firstName\":\"Pamela\",\"lastName\":\"Mwakili\",\"otherName\":\"Jimon nyingine\",\"gender\":\"FEMALE\",\"emailAddress\":\"caren2@gmail.com\",\"phoneNumber\":\"0708543231\",\"dateOfBirth\":\"30-09-2001\",\"maritalStatus\":\"SINGLE\",\"patientAddress\":{\"postalCode\":\"70-2000\",\"physicalAddress\":\"Mwea Soko\",\"country\":\"TANZANIA\"}}";
    public static final String UPDATE_PATIENT_ADDRESS_DETAILS_REQUEST = "";
}
