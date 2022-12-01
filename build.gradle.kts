plugins {
    id("java")
}

group = "dev.kingtux"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
tasks{
    jar {
        manifest {
            attributes(
                "Main-Class" to "dev.kingtux.recordmanager.Main"
            )
        }
    }
}
dependencies {}
