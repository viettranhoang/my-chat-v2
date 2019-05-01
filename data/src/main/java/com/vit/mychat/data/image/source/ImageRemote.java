package com.vit.mychat.data.image.source;

import java.io.File;

import io.reactivex.Single;

public interface ImageRemote {

    Single<String> updateImage(File image, String type);
}
