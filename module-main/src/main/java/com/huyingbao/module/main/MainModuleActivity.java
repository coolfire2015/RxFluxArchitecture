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

package com.huyingbao.module.main;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.huyingbao.core.common.CommonToolbarActivity;
import com.huyingbao.module.main.action.MainActionCreator;

import javax.inject.Inject;

/**
 * An {@link AppCompatActivity} that injects its members in {@link #onCreate(Bundle)} and can be
 * used to inject {@code Fragment}s attached to it.
 */
public abstract class MainModuleActivity extends CommonToolbarActivity {
    @Inject
    protected MainActionCreator mActionCreator;
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
}
