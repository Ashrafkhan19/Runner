plugins {
    `kotlin-dsl`
}

group = "com.emir.runner.buildlogic"

dependencies {

    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "runner.android.application"
            implementationClass = "AndroidApplicationConventionalPlugin"
        }

        register("androidApplicationCompose") {
            id = "runner.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionalPlugin"
        }

        register("androidLibrary") {
            id = "runner.android.library"
            implementationClass = "AndroidLibraryConventionalPlugin"
        }
        register("androidLibraryCompose") {
            id = "runner.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionalPlugin"
        }
        register("androidFeatureUi") {
            id = "runner.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionalPlugin"
        }
        register("androidRoom") {
            id = "runner.android.room"
            implementationClass = "AndroidRoomConventionalPlugin"
        }
        register("jvmLibrary") {
            id = "runner.jvm.library"
            implementationClass = "JvmLibraryConventionalPlugin"
        }
        register("jvmKtor") {
            id = "runner.jvm.ktor"
            implementationClass = "KtorLibraryConventionalPlugin"
        }
    }
}

