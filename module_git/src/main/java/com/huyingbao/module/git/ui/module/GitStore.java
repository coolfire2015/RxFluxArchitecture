package com.huyingbao.module.git.ui.module;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.module.git.ui.model.GitRepo;
import com.huyingbao.module.git.ui.model.GitUser;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class GitStore extends RxStore {
    public final MutableLiveData<List<GitRepo>> mGitRepoList = new MutableLiveData<>();
    public final MutableLiveData<GitUser> mGitUser = new MutableLiveData<>();

    @Inject
    GitStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

//    @Override
//    public void onRxAction(RxAction action) {
//        switch (action.getTag()) {
//            case GitActions.GET_GIT_REPO_LIST:
//                mGitRepoList.setValue(action.get(RxActionCreator.RESPONSE));
//                return;
//            case GitActions.GET_GIT_USER:
//                mGitUser.setValue(action.get(RxActionCreator.RESPONSE));
//                return;
//            case GitActions.TO_GIT_USER:
//                break;
//            default://此处不能省略，不是本模块的逻辑，直接返回，不发送RxStoreChange
//                return;
//        }
//        postChange(new RxChange(getClass().getSimpleName(), action));
//    }
}
