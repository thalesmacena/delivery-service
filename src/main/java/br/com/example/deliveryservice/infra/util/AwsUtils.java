package br.com.example.deliveryservice.infra.util;

import org.springframework.beans.factory.annotation.Value;

public class AwsUtils {

    public static String concatUrlWithConfig(String url, String config) {
        return String.format("%s%s", url, config);
    }

}
