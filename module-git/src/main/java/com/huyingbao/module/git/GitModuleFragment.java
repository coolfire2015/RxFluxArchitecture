/*
 * Copyright (C) 2017 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huyingbao.module.git;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.huyingbao.core.common.CommonFragment;
import com.huyingbao.module.git.action.GitActionCreator;

import javax.inject.Inject;

/**
 * A {@link Fragment} that injects its members in {@link #onAttach(Context)} and can be used to
 * inject child {@link Fragment}s attached to it. Note that when this fragment gets reattached, its
 * members will be injected again.
 */
public abstract class GitModuleFragment extends CommonFragment {
    @Inject
    protected GitActionCreator mActionCreator;
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
}
