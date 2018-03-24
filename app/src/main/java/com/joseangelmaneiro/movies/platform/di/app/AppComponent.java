package com.joseangelmaneiro.movies.platform.di.app;

import android.app.Application;
import com.joseangelmaneiro.movies.platform.MoviesApp;
import com.joseangelmaneiro.movies.platform.di.ActivityBuilder;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(MoviesApp app);

}
