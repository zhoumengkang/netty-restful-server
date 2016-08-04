package net.mengkang.demo.service;

import net.mengkang.demo.bo.Icon;
import net.mengkang.nettyrest.ApiProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;


public class IconService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(IconService.class);

    public IconService(ApiProtocol apiProtocol) {
        super(apiProtocol);
    }

    // format : /upload/image/{yyyy}/{mm}{dd}/{time}{rand:6}
    private static final String baseUrl = "http://static.mengkang.net/upload/image/";

    public static String icon2Url(String icon) {

        if (icon == null || icon.length() != 16) {
            try {
                throw new Exception("illegal icon");
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            return icon;
        }

        String           format = "yyyy-MM-dd";
        SimpleDateFormat sdf    = new SimpleDateFormat(format);
        String           data   = sdf.format(new Date(Long.valueOf(icon.substring(1, 10) + "000")));

        return baseUrl + data.substring(1, 4) + "/" + data.substring(6, 7) + data.substring(9, 10) + "/" + icon + ".jpg";

    }

    public static Icon get(String icon){
        return new Icon(100,100,icon2Url(icon));
    }


}
