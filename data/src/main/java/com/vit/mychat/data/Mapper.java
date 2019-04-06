package com.vit.mychat.data;

public interface Mapper<E, M> {

    E mapToEntity(M type);

    M mapFromEntity(E type);
}
