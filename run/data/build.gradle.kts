plugins {
    alias(libs.plugins.runner.android.library)
}

android {
    namespace = "com.example.run.data"

}

dependencies {


//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}