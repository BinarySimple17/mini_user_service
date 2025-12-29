plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.dependency.management)
}

springBoot {
	buildInfo()
}

group = "ru.binarysimple"
version = "0.0.5"
description = "Users service"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation(libs.liquibase.core)
	implementation(libs.jakarta.validation.api)
	implementation(libs.spring.boot.actuator)
	implementation(libs.postgresql)
	implementation(libs.springdoc.openapi.ui)
	compileOnly(libs.lombok)
	annotationProcessor(libs.lombok)
	testImplementation(libs.junit.api)
	testRuntimeOnly(libs.junit.engine)
	testImplementation(libs.spring.boot.starter.test)
	compileOnly("org.mapstruct:mapstruct:1.6.0")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0")
	implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.micrometer:micrometer-registry-prometheus")
}

tasks.register("printVersion") {
    doLast {
        println(project.version)
    }
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootJar {
	enabled = true
	archiveFileName.set("${project.name}-${project.version}.jar")
}

tasks.jar {
	manifest {
		attributes.apply {
			put("Implementation-Title", project.name)
			put("Implementation-Version", project.version)
			put("Built-By", System.getProperty("user.name"))
			put("Built-JDK", System.getProperty("java.version"))
			put("Spring-Boot-Version", libs.versions.spring.boot.get())
		}
	}
}
