package br.com.example.deliveryservice.infra.util;

import feign.Response;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@UtilityClass
@Slf4j
public class HttpUtils {
    public static HttpStatus getHttpStatus(Response response) {

        if (Objects.isNull(response)) {
            log.warn("The response is null");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.valueOf(response.status());
    }
}
