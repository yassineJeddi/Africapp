/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author airaamane
 */
public class EmailValidator {

    private static Pattern pattern;
    private static Matcher matcher;

    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Validate mail with regular expression
     *
     * @param mail for validation
     * @return true valid mail, false invalid mail
     */
    public static boolean validate(final String mail) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mail);
        return matcher.matches();

    }

//    public static void main(String[] args) {
//
//        boolean a = validate("air@novwaycom");
//        System.out.println("VALUE : " + a);
//    }
}
