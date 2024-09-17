plugins {
    alias(libs.plugins.runner.android.feature.ui)
}

android {
    namespace = "com.example.run.presentation"

}

dependencies {

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