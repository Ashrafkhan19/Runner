import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.ExtensionType
import com.example.convention.configureBuildType
import com.example.convention.configureKotlinAndroid
import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionalPlugin: Plugin<Project> {
    override fun apply(target: Project) {

        target.run {
            plugins.apply {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {

                defaultConfig {

                    applicationId = libs.findVersion("projectApplicationId").get().toString()

                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()

                    versionName = libs.findVersion("projectVersionName").get().toString()
                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                }

                configureKotlinAndroid(this)

                configureBuildType(this, ExtensionType.APPLICATION)

            }


        }

    }
}