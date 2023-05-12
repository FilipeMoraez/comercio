package br.com.comercio.saldo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private DateUtils(){}

    public static String convertData(Date d){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return simpleDateFormat.format(d);
    }
}
