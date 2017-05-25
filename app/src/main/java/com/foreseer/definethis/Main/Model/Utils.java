package com.foreseer.definethis.Main.Model;

/**
 * Created by Foreseer on 25/05/2017.
 * For any questions, feel free to reach me using any of my contacts.
 * Contacts:
 * e-mail (preferred): fforeseer@gmail.com
 */

public class Utils {
    private Utils() {}

    public static String parseHeadwordOutOfURL(String url){
        return url.substring(url.indexOf("=") + 1, url.indexOf("&"));
    }
}
