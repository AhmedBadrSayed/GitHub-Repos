package com.mondiamedia.ahmedbadr.githubreopos.di;

import com.mondiamedia.ahmedbadr.githubreopos.di.modules.NetworkModule;
import com.mondiamedia.ahmedbadr.githubreopos.di.modules.OkHttpClientModule;
import com.mondiamedia.ahmedbadr.githubreopos.views.activities.MainActivity;

import dagger.Component;

@Component(modules = {NetworkModule.class, OkHttpClientModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
}
