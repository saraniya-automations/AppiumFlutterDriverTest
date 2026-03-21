# Maven Configuration for Jenkins Integration

## POM.xml Enhancements for Jenkins

Your current `pom.xml` is already good! Here are optional enhancements for better Jenkins integration:

### Optional 1: Add Surefire Report Plugin Configuration

Add this to `<plugins>` section in pom.xml to improve test reporting:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-report-plugin</artifactId>
    <version>3.1.2</version>
    <configuration>
        <outputDirectory>${project.build.directory}/test-reports</outputDirectory>
    </configuration>
</plugin>
```

### Optional 2: Add Failsafe Plugin for Integration Tests

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-failsafe-plugin</artifactId>
    <version>3.1.2</version>
    <configuration>
        <suiteXmlFiles>
            <suiteXmlFile>run-test.xml</suiteXmlFile>
        </suiteXmlFiles>
    </configuration>
</plugin>
```

### Optional 3: Add Compiler Plugin with Source/Target

Already in your pom.xml but ensure it's properly configured:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.11.0</version>
    <configuration>
        <source>11</source>
        <target>11</target>
        <encoding>UTF-8</encoding>
    </configuration>
</plugin>
```

### Optional 4: Add Assembly Plugin (for creating fat JARs if needed)

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-assembly-plugin</artifactId>
    <version>3.6.0</version>
    <configuration>
        <descriptorRefs>
            <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
    </configuration>
</plugin>
```

### Optional 5: System Properties for Jenkins

Add to `<properties>` section:

```xml
<!-- TestNG Configuration -->
<testng.timeout.ms>300000</testng.timeout.ms>
<appium.url>http://127.0.0.1:4723/wd/hub</appium.url>
<platform.name>Android</platform.name>
<automation.name>Flutter</automation.name>
```

Then access in Java code:
```java
String appiumUrl = System.getProperty("appium.url", "http://127.0.0.1:4723/wd/hub");
```

### Optional 6: Add Profiles for Different Environments

```xml
<profiles>
    <profile>
        <id>local</id>
        <properties>
            <appium.url>http://127.0.0.1:4723/wd/hub</appium.url>
            <device.name>emulator-5554</device.name>
        </properties>
    </profile>
    
    <profile>
        <id>jenkins</id>
        <properties>
            <appium.url>http://jenkins-agent.local:4723/wd/hub</appium.url>
            <device.name>physical-device-001</device.name>
        </properties>
    </profile>
</profiles>
```

Run with: `mvn clean test -Pjenkins`

---

## Running Maven from Jenkins

### In Freestyle Job

Build Step → Invoke top-level Maven targets:

```
Goals: clean test
```

### In Jenkinsfile

```groovy
stage('Run Tests') {
    steps {
        sh 'mvn clean test -X'
    }
}
```

---

## Common Maven Commands for Jenkins

```bash
# Clean build and run tests
mvn clean test

# Skip tests during build
mvn clean compile -DskipTests

# Run specific test class
mvn test -Dtest=LoginTest

# Run specific test method
mvn test -Dtest=LoginTest#testValidLogin

# Run with detailed output
mvn test -X

# Run with specific profile
mvn clean test -Plocal

# Run tests in parallel (4 threads)
mvn test -DThreadCount=4

# Skip certain tests
mvn test -DskipITs

# Force rebuild
mvn clean test -U
```

---

## Troubleshooting Maven in Jenkins

### Issue: Maven not found
```bash
# Install Maven
brew install maven

# Or add to Jenkins Global Tools:
# Manage Jenkins → Global Tool Configuration → Add Maven
# Name: Maven 3.9.0
# MAVEN_HOME: /usr/local/opt/maven
```

### Issue: Tests timeout
Add to Jenkinsfile:
```groovy
environment {
    MAVEN_OPTS = '-Xmx1024m -Xms512m'
}
```

Or in pom.xml properties:
```xml
<testng.timeout.ms>600000</testng.timeout.ms>
```

### Issue: Out of Memory (OOM)
```groovy
environment {
    MAVEN_OPTS = '-Xmx2048m -Xms1024m'
}
```

### Issue: Jenkins can't find test results
Verify in pom.xml:
```xml
<configuration>
    <suiteXmlFiles>
        <suiteXmlFile>run-test.xml</suiteXmlFile>
    </suiteXmlFiles>
</configuration>
```

And in Jenkinsfile post-build:
```groovy
publishHTML target: [
    reportDir: 'target/surefire-reports',
    reportFiles: 'index.html',
    reportName: 'TestNG Report'
]
```

---

## Performance Tips

1. **Parallel Test Execution:**
   ```
   mvn test -DThreadCount=4
   ```

2. **Maven Offline Mode (faster builds):**
   ```
   mvn test -o
   ```

3. **Skip Javadoc/Sources in Jenkins:**
   ```xml
   <configuration>
       <skipJavadoc>true</skipJavadoc>
       <skipSources>true</skipSources>
   </configuration>
   ```

4. **Skip Tests in Non-Test Builds:**
   ```
   mvn clean compile -DskipTests
   ```

---

## Recommended pom.xml Structure

Your current pom.xml should have this structure:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>io-appium</groupId>
    <artifactId>io-appium</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    <properties>
        <!-- Versions -->
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        
        <!-- Test properties -->
        <appium.url>http://127.0.0.1:4723/wd/hub</appium.url>
    </properties>
    
    <dependencies>
        <!-- Your dependencies -->
    </dependencies>
    
    <build>
        <plugins>
            <!-- Maven Compiler Plugin -->
            <!-- Maven Surefire Plugin (for TestNG) -->
            <!-- Other plugins -->
        </plugins>
    </build>
</project>
```

---

## CI/CD Best Practices

1. **Always** run `mvn clean test` to ensure fresh builds
2. **Use** consistent test suite definitions in `run-test.xml`
3. **Never** skip tests in production builds
4. **Archive** test reports and logs
5. **Monitor** test execution time trends
6. **Parallel** execution for faster feedback
7. **Cache** Maven dependencies:
   ```groovy
   environment {
       MAVEN_USER_HOME = '${WORKSPACE}/.m2'
   }
   ```

---

## Example Jenkins Stages with Maven

```groovy
stage('Compile') {
    steps {
        sh 'mvn clean compile -DskipTests'
    }
}

stage('Unit Tests') {
    steps {
        sh 'mvn test'
    }
}

stage('Integration Tests') {
    steps {
        sh 'mvn verify'
    }
}

stage('Generate Reports') {
    steps {
        sh 'mvn surefire-report:report'
    }
}
```

---

For more info: https://maven.apache.org/guides/
