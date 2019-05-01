package com.vit.mychat.domain.usecase.image;


import com.vit.mychat.domain.SingleUseCase;
import com.vit.mychat.domain.usecase.image.repository.ImageRepository;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;

@Singleton
public class UploadImageUseCase extends SingleUseCase<String, UploadImageUseCase.Params> {

    @Inject
    ImageRepository imageRepository;

    @Inject
    public UploadImageUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                              @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<String> buildUseCaseSingle(Params params) {
        return imageRepository.updateImage(params.file, params.type);
    }

    public static final class Params {
        private final File file;

        private final String type;

        public Params(File file, String type) {
            this.file = file;
            this.type = type;
        }

        public static Params forUpdateImage(File image, String type) {
            return new Params(image, type);
        }
    }
}
