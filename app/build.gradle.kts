plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
}

android {
    namespace = "io.payflow.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.payflow.android"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val httpProxyHost = project.findProperty("PAYFLOW_HTTP_PROXY_HOST")?.toString() ?: ""
        val httpProxyPort = project.findProperty("PAYFLOW_HTTP_PROXY_PORT")?.toString()?.toIntOrNull() ?: 0
        val enableUnsafeSsl = project.findProperty("PAYFLOW_ENABLE_UNSAFE_SSL")?.toString()?.toBoolean() ?: false
        val dnsOverrideHost = project.findProperty("PAYFLOW_DNS_OVERRIDE_HOST")?.toString() ?: ""
        val dnsOverrideAddresses = project.findProperty("PAYFLOW_DNS_OVERRIDE_ADDRESSES")?.toString() ?: ""

        buildConfigField("String", "HTTP_PROXY_HOST", "\"$httpProxyHost\"")
        buildConfigField("int", "HTTP_PROXY_PORT", httpProxyPort.toString())
        buildConfigField("boolean", "ENABLE_UNSAFE_SSL", enableUnsafeSsl.toString())
        buildConfigField("String", "DNS_OVERRIDE_HOST", "\"$dnsOverrideHost\"")
        buildConfigField("String", "DNS_OVERRIDE_ADDRESSES", "\"$dnsOverrideAddresses\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            // Workaround para testes no ambiente corporativo.
            // Se quiser desligar o bypass de certificado, defina
            // PAYFLOW_ENABLE_UNSAFE_SSL=false no gradle.properties
            // ou remova este buildConfigField.
            val debugUnsafeSsl = project.findProperty("PAYFLOW_ENABLE_UNSAFE_SSL")?.toString()?.toBoolean() ?: true
            buildConfigField("boolean", "ENABLE_UNSAFE_SSL", debugUnsafeSsl.toString())
        }

        release {
            // Em release o bypass deve permanecer desligado.
            buildConfigField("boolean", "ENABLE_UNSAFE_SSL", "false")
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
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx")
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.coil.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}