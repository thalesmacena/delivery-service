package br.com.example.deliveryservice.domain.services.impl;

import br.com.example.deliveryservice.domain.services.MessageContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageContextServiceImpl implements MessageContextService {

    private final MessageSource messageSource;


    @Override
    public String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }

    @Override
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}
