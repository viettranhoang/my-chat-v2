package com.vit.mychat.domain.usecase.group;

import com.vit.mychat.domain.SingleUseCase;
import com.vit.mychat.domain.usecase.group.model.Group;
import com.vit.mychat.domain.usecase.group.repository.GroupRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;

@Singleton
public class CreateGroupUseCase extends SingleUseCase<Group, CreateGroupUseCase.Params> {

    @Inject
    GroupRepository groupRepository;

    @Inject
    public CreateGroupUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                        @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<Group> buildUseCaseSingle(Params params) {
        return groupRepository.createGroup(params.group);
    }

    public static final class Params {
        private final Group group;

        public Params(Group group) {
            this.group = group;
        }

        public static Params forCreateGroup(Group group) {
            return new Params(group);
        }
    }
}
