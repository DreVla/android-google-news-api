import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization")
    id("com.google.protobuf")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.gnewsapi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gnewsapi"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        // Code to retrieve hidden API key. There are multiple ways to do this, but this is my favorite.
        // source: https://gist.github.com/MAshhal/636be4a3292f353f21547d02fdaef096
        // Read the local.properties file to get the hidden API key
        val localPropertiesFile = rootProject.file("local.properties")
        val properties = Properties()
        properties.load(localPropertiesFile.inputStream())

        // Return a help message in case the API key is missing
        val apiKey = properties.getProperty("NEWS_API_KEY")
            ?: "MISSING API KEY, PLEASE ADD TO LOCAL.PROPERTIES AS SUCH: NEWS_API_KEY = \"YOUR_API_KEY\""

        // For accessing the property using BuildConfig
        buildConfigField(type = "String", name = "NEWS_API_KEY", value = apiKey)

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // I decided to go with navigation compose because I wanted the UI to be built using jetpack
    // compose. I know fragments are not deprecated and are still widely used in Android, but I
    // wanted to also experiment with navigation compose.
    // source - https://developer.android.com/develop/ui/compose/navigation#kts
    // Also, there is nav 3 in alpha right now, which I just found out about:
    // https://developer.android.com/guide/navigation/navigation-3
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    // Protobuff
    implementation(libs.protobuf.javalite)

    // Saving articles offline
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(kotlin("test"))
}

/**
 * Did not know about protobuf before, so I looked it up.
 *
 * [source](https://github.com/google/protobuf-gradle-plugin/tree/master)
 * [source](https://protobuf.dev/programming-guides/editions/)
 * [source](https://medium.com/@zekromvishwa56789/setting-up-protocol-buffers-in-an-android-project-8f7bad31981f)
 * [source](https://developer.android.com/codelabs/android-proto-datastore#4)
 */
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.5"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}