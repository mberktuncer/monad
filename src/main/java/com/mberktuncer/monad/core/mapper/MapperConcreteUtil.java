package com.mberktuncer.monad.core.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class MapperConcreteUtil implements MapperUtil{
    private final ObjectMapper objectMapper;
    @Override
    public <T> T mapSourceToDestinationType(Object source, Class<T> destinationType) {
        T destinationObject;
        try{
            destinationObject = objectMapper.convertValue(source, destinationType);
        }catch (IllegalArgumentException e){
            log.error("Mapping failure exception:",e);
            throw new RuntimeException(e);
        }
        return destinationObject;
    }
}
