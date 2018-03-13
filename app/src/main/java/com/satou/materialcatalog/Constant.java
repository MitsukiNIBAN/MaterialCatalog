package com.satou.materialcatalog;

/**
 * Created by Mitsuki on 2018/3/12.
 */

public class Constant {
    public static final int OVA = 1;
    public static final int OP = 2;
    public static final int ED = 3;

    public static String getName(int i) {
        switch (i) {
            case OVA:
                return "OVA";
            case OP:
                return "OP";
            case ED:
                return "ED";
            default:
                return "";
        }
    }

    public static final int REQUEST_CODE_ADD_TYPE = 1;
    public static final int REQUEST_CODE_ADD_SCENE = 2;
}
