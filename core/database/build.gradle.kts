plugins {
    alias(libs.plugins.runner.android.library)
    alias(libs.plugins.runner.android.room)
}

android {
    namespace = "com.example.core.database"
}

dependencies {

    implementation(libs.org.mongodb.bson)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}