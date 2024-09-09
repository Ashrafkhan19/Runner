plugins {
    alias(libs.plugins.runner.android.library)
}

android {
    namespace = "com.example.auth.data"

}

dependencies {

    implementation(libs.bundles.koin)

    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}