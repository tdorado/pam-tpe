package com.td.wallendar.di;

import android.content.Context;

import androidx.annotation.VisibleForTesting;

public class DependenciesContainerLocator {

    private static DependenciesContainer dependenciesContainer;

    private DependenciesContainerLocator() {
        // no-op
    }

    public static DependenciesContainer locateComponent(final Context context) {
        if (dependenciesContainer == null) {
            setComponent(new ProductionDependenciesContainer(context));
        }

        return dependenciesContainer;
    }

    @VisibleForTesting
    public static void setComponent(final DependenciesContainer dependenciesContainer) {
        DependenciesContainerLocator.dependenciesContainer = dependenciesContainer;
    }
}
