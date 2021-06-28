package com.example.hotelmanagmentapplication;

import java.util.regex.Pattern;

public class PasswordUtils
{
    public static final Pattern PASSWORD_PATTERN=
            Pattern.compile("^"
                    +"(?=.*[0-9])"          //minimum 0ne number
                    +"(?=.*[a-z])"          //minimum 0ne lower-case character
                    +"(?=.*[A-Z])"          //minimum 0ne Upper-case character
                    +"(?=.*[a-zA-Z])"       //any character
                    +"(?=.*[@#$%^&+=])"     //minimum one special character
                    +"(?=\\S+$)"            //no white spaces
                    +".{8,}"                //minimum length 8 characters
                    +"$");

    public static final Pattern PASSWORD_UPPERCASE_PATTERN=Pattern.compile("(?=.*[A-Z])"+".{1,}");
    public static final Pattern PASSWORD_LOWERCASE_PATTERN=Pattern.compile("(?=.*[a-z])"+".{1,}");
    public static final Pattern PASSWORD_NUMBER_PATTERN=Pattern.compile("(?=.*[0-9])"+".{1,}");
    public static final Pattern PASSWORD_SPECIALCHARACTER_PATTERN=Pattern.compile("(?=.*[@#$%^&+=])"+".{1,}");


}
