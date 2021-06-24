package com.investnow.util;

import java.util.concurrent.ThreadLocalRandom;

public class GenericUtil
{
    public static String generateTransactionId()
    {
        int random = ThreadLocalRandom.current().nextInt(999);
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String transactionId = "INVSTNW_".concat(timeStamp).concat("_").concat(String.valueOf(random));
        return transactionId;
    }
}
