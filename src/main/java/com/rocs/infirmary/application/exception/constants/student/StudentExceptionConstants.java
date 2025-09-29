package com.rocs.infirmary.application.exception.constants.student;
/**
 * {@code StudentExceptionConstants} holds the constant messages for student health profile exception
 * */
public class StudentExceptionConstants {
    /** Error message when LRN is missing or empty */
    public static final String LRN_REQUIRED = "LRN is required and cannot be empty";

    /** Error message when first name is missing or empty */
    public static final String FIRST_NAME_REQUIRED = "First name is required and cannot be empty";

    /** Error message when last name is missing or empty */
    public static final String LAST_NAME_REQUIRED = "Last name is required and cannot be empty";

    /** Error message when email is missing or empty */
    public static final String EMAIL_REQUIRED = "Email is required and cannot be empty";

    /** Error message when student with the same LRN already exists */
    public static final String STUDENT_ALREADY_EXISTS = "Student with this LRN already exists";

    /** Error message when birthday is missing or empty */
    public static final String BIRTHDAY_REQUIRED = "Birthday is required and cannot be empty";

    /** Error message when address is missing or empty */
    public static final String ADDRESS_REQUIRED = "Address is required and cannot be empty";

    /** Error message when gender is missing or empty */
    public static final String GENDER_REQUIRED = "Gender is required and cannot be empty";

    /** Error message when contact number is missing or empty */
    public static final String CONTACT_NUMBER_REQUIRED = "Contact Number is required and cannot be empty";

    /** Error message when grade level is missing or empty */
    public static final String GRADE_LEVEL_REQUIRED = "GradeLevel is required and cannot be empty";

    /** Error message when section is missing or empty */
    public static final String SECTION_REQUIRED = "Section is required and cannot be empty";
}
