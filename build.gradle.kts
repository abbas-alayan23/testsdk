import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.library) // Your existing plugin aliases
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish") // Apply Maven publish plugin
}

android {
    namespace = "com.app.sdkinit"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

// Load properties from github.properties if it exists
val githubProperties = Properties().apply {
    val githubPropertiesFile = rootProject.file("github.properties")
    if (githubPropertiesFile.exists()) {
        load(FileInputStream(githubPropertiesFile))
    }
}

// Function to get version name
fun getVersionName(): String {
    return "1.0.2" // Replace with actual version name
}

// Function to get artifact ID
fun getArtifactId(): String {
    return "sdkInit" // Replace with your SDK/library name
}

publishing {
    publications {
        create<MavenPublication>("bar") {
            groupId = "com.app.sdkinit" // Replace with your group ID
            artifactId = getArtifactId()
            version = getVersionName()

            // Specify the path to the generated AAR file
            artifact(layout.buildDirectory.file("outputs/aar/${getArtifactId()}-release.aar"))
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            // GitHub Packages repository URL
            url = uri("https://maven.pkg.github.com/abbas-alayan23/testsdk")

            credentials {
                // Load credentials from github.properties or environment variables
                username = githubProperties["gpr.usr"]?.toString() ?: System.getenv("GPR_USER")
                password = githubProperties["gpr.key"]?.toString() ?: System.getenv("GPR_API_KEY")
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.adjust.android)
    implementation(libs.androidx.core.ktx.v1101)
    implementation(libs.installreferrer)
    implementation(libs.adjust.android.webbridge)
    implementation(libs.play.services.ads.identifier)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
