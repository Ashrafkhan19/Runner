plugins {
    alias(libs.plugins.runner.android.library)
    alias(libs.plugins.runner.android.room)
}

android {
    namespace = "com.example.core.database"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}