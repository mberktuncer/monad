package com.mberktuncer.monad.core.mapper;

public interface MapperUtil {
    <T> T mapSourceToDestinationType(Object source,Class<T> destinationType);
}
