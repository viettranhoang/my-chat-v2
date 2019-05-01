package com.vit.mychat.remote.feature.image;

import com.vit.mychat.data.image.source.ImageRemote;
import com.vit.mychat.remote.feature.MyChatFirestore;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class ImageRemoteImpl implements ImageRemote {

    @Inject
    MyChatFirestore myChatFirestore;

    @Inject
    public ImageRemoteImpl() {
    }

    @Override
    public Single<String> updateImage(File image, String type) {
        return myChatFirestore.updateImage(image, type);
    }
}
