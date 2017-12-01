package com.ghifa.mobile.kelompokproyek.component;

import java.security.MessageDigest;

/**
 * Created by ghifa on 25/11/16.
 */

public class Fungsi {


    public static String MD5_Hash(String password) {

//        String password = "12131123984335";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            System.out.println("Digest(in hex format):: " + sb.toString());

            //convert the byte to hex format method 2
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            System.out.println("Digest(in hex format):: " + hexString.toString());

            return hexString.toString();

        } catch (Exception e) {
            // TODO: handle exception
        }

        return "";
    }


    public static String getRupiahFormat(String number) {
        String displayedString = "";

        if (number.length() == 0) {
            displayedString = "Rp. 0";
        } else {
            if (number.length() > 3) {
                int length = number.length();

                for (int i = length; i > 0; i -= 3) {
                    if (i > 3) {
                        String myStringPrt1 = number.substring(0, i - 3);
                        String myStringPrt2 = number.substring(i - 3);

                        String combinedString;

                        combinedString = myStringPrt1 + ".";

                        combinedString += myStringPrt2;
                        number = combinedString;

                        displayedString = "Rp. " + combinedString;
                    }
                }
            } else {
                displayedString = "Rp. " + number;
            }
        }
        return displayedString;
    }

}
