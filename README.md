# foodinger-api
Backend api for foodinger

> Your _face_ is a foodinger!

## Building

Normal Java w/ Maven project, so no great surprises:

    $ mvn --version
    Apache Maven 3.6.0
    Maven home: /usr/share/maven
    Java version: 11.0.6, vendor: Ubuntu, runtime: /usr/lib/jvm/java-11-openjdk-amd64
    Default locale: en_US, platform encoding: UTF-8
    OS name: "linux", version: "4.15.0-1073-oem", arch: "amd64", family: "unix"
    
    $ mvn clean package
    [INFO] Scanning for projects...
    [INFO] 
    [INFO] ---------------< com.brennaswitzer.foodinger:foodinger >----------------
    [INFO] Building foodinger 0.0.1-SNAPSHOT
    ... much output omitted ...
    [INFO] --- maven-jar-plugin:3.1.2:jar (default-jar) @ foodinger ---
    [INFO] Building jar: /home/barneyb/Documents/foodinger-api/target/foodinger-0.0.1-SNAPSHOT.jar
    [INFO] 
    [INFO] --- spring-boot-maven-plugin:2.2.5.RELEASE:repackage (repackage) @ foodinger ---
    [INFO] Replacing main artifact with repackaged archive
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
