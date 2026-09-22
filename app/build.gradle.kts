plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.sofia.miformacionctma"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.sofia.miformacionctma"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            // Semana 8 - Cobertura de pruebas unitarias
            enableUnitTestCoverage = true
        }

        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

    // Semana 9 - Los esquemas de Room quedan disponibles
    // para las pruebas instrumentadas de migración.
    sourceSets {
        getByName("androidTest") {
            assets.srcDir("$projectDir/schemas")
        }
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {

    // ---------------------------------------------------------
    // Android y Jetpack Compose
    // ---------------------------------------------------------

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)

    // ---------------------------------------------------------
    // Lifecycle y ViewModel
    // ---------------------------------------------------------

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // ---------------------------------------------------------
    // Navegación
    // ---------------------------------------------------------

    implementation(libs.androidx.navigation.compose)

    // ---------------------------------------------------------
    // Room 3 + SQLite
    // ---------------------------------------------------------

    implementation(libs.androidx.room3.runtime)
    implementation(libs.androidx.sqlite.bundled)
    ksp(libs.androidx.room3.compiler)

    // ---------------------------------------------------------
    // DataStore
    // ---------------------------------------------------------

    implementation(libs.androidx.datastore.preferences)

    // ---------------------------------------------------------
    // Semana 8 - Servicios Web
    // ---------------------------------------------------------

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)

    // ---------------------------------------------------------
    // Pruebas unitarias
    // ---------------------------------------------------------

    testImplementation("junit:junit:4.13.2")
    testImplementation("com.squareup.okhttp3:mockwebserver3:5.5.0")
    testImplementation(libs.kotlinx.coroutines.test)

    // ---------------------------------------------------------
    // Pruebas instrumentadas
    // ---------------------------------------------------------

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // Semana 9 - Pruebas de migración Room
    androidTestImplementation("androidx.room3:room3-testing:3.0.3")
    androidTestImplementation(libs.kotlinx.coroutines.test)

    // ---------------------------------------------------------
    // Herramientas de Compose
    // ---------------------------------------------------------

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}