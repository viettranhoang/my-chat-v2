package com.vit.mychat.domain.usecase.image.repository;

import java.io.File;

import io.reactivex.Single;

public interface ImageRepository {

    Single<String> updateImage(File image, String type);
}
