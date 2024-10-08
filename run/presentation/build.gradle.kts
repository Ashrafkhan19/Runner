plugins {
    alias(libs.plugins.runner.android.feature.ui)
    alias(libs.plugins.mapsplatform.secrets.plugin)
}

android {
    namespace = "com.example.run.presentation"

}

dependencies {

    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation(libs.google.maps.android.compose)

    implementation(projects.core.domain)
    implementation(projects.run.domain)
    //implementation(projects.core.data)
    /*implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)*/
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}