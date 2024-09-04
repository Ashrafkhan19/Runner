package com.example.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*,*,*,*,*,*>
) {

    commonExtension.buildFeatures {
        compose = true

    }

    dependencies {

        val bom = libs.findLibrary("androidx.compose.bom").get()
        "implementation"(platform(bom))
        "androidTestImplementation"(platform(bom))
        "debugImplementation"(libs.findLibrary("androidx.compose.ui.tooling.preview").get())

    }

}