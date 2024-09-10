plugins {
    alias(libs.plugins.runner.android.application.compose)
    alias(libs.plugins.runner.jvm.ktor)
    alias(libs.plugins.mapsplatform.secrets.plugin)
}

android {
    namespace = "com.emir.runner"

}

dependencies {

    // Crypto
    implementation(libs.androidx.security.crypto.ktx)

    // Coil
    implementation(libs.coil.compose)

    //Timber
    implementation(libs.timber)

    //koin
    implementation(libs.bundles.koin)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(projects.core.presentation.designsystem)
    implementation(projects.core.data)
    implementation(projects.auth.domain)
    implementation(projects.auth.presentation)
    implementation(projects.auth.data)


    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
}