package com.vit.mychat.data.image;

import com.vit.mychat.data.image.source.ImageRemote;
import com.vit.mychat.domain.usecase.image.repository.ImageRepository;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class ImageRepositoryImpl implements ImageRepository {

    @Inject
    ImageRemote imageRemote;

    @Inject
    public ImageRepositoryImpl() {
    }

    @Override
    public Single<String> updateImage(File image, String type) {
        return imageRemote.updateImage(image, type);
    }
}
