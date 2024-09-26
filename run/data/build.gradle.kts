plugins {
    alias(libs.plugins.runner.android.library)
}

android {
    namespace = "com.example.run.data"

}

dependencies {

    implementation(libs.androidx.work)
    implementation(libs.bundles.koin)
    implementation(libs.timber)

    implementation(projects.core.domain)
    implementation(projects.core.database)


//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}