package com.td.wallendar.di

import android.content.Context
import androidx.annotation.VisibleForTesting

object DependenciesContainerLocator {
    private var dependenciesContainer: DependenciesContainer? = null
    fun locateComponent(context: Context): DependenciesContainer {
        if (dependenciesContainer == null) {
            setComponent(ProductionDependenciesContainer(context))
        }
        return dependenciesContainer!!
    }

    @VisibleForTesting
    fun setComponent(dependenciesContainer: DependenciesContainer) {
        DependenciesContainerLocator.dependenciesContainer = dependenciesContainer
    }
}