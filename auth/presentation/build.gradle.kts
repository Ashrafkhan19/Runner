plugins {
    alias(libs.plugins.runner.android.feature.ui)
}

android {
    namespace = "com.example.auth.presentation"

}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    //implementation(projects.core.data)
}