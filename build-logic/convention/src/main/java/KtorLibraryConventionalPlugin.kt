import com.android.build.api.dsl.LibraryExtension
import com.example.convention.ExtensionType
import com.example.convention.configureBuildType
import com.example.convention.configureKotlinAndroid
import com.example.convention.configureKotlinJvm
import com.example.convention.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class KtorLibraryConventionalPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                add("implementation", libs.findBundle("ktor").get())
            }
        }
    }

}