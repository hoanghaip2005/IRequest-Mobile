plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.compose.compiler)
    //alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
    id("com.diffplug.spotless")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint("1.0.1").editorConfigOverride(
            mapOf(
                "indent_size" to "4",
                "max_line_length" to "120"
            )
        )
        trimTrailingWhitespace()
        endWithNewline()
    }
}

android {
    namespace = "com.project.irequest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.project.irequest"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        buildConfigField("String", "BASE_URL", "\"https://your-api-domain.azurewebsites.net/api/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:5000/api/\"")
        }
    }
    
    buildFeatures {
        compose = true
        buildConfig = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    
    // KSP configuration for Room - disable schema verification on Windows
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
        arg("room.incremental", "true")
        arg("room.expandProjection", "true")
        arg("room.generateKotlin", "true")
        // Fix SQLite native library error on Windows
        arg("room.verifyDatabase", "false")
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    
    // Compose Navigation
    implementation(libs.androidx.navigation.compose)
    
    // Hilt Dependency Injection (temporarily disabled)
    //implementation(libs.hilt.android)
    //implementation(libs.androidx.hilt.navigation.compose)
    //ksp(libs.hilt.compiler)
    
    // Retrofit & Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    
    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)
    
    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    
    // ViewModel & LiveData
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)
    
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    
    // Image Loading
    implementation(libs.coil.compose)
    
    // Permissions
    implementation(libs.accompanist.permissions)
    
    // Date Picker
    implementation(libs.androidx.material3.datetime)
    
    // DataStore
    implementation(libs.androidx.datastore.preferences)
    
    // SwipeRefresh
    implementation(libs.accompanist.swiperefresh)
    
    // WebSocket (SignalR)
    implementation(libs.signalr)
    
    // File Picker
    implementation(libs.activity.result)
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}