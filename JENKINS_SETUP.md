# Jenkins Installation & Integration Guide

## Prerequisites
- macOS 10.15 or later
- Java 11+ (required for your project)
- Git
- Homebrew (optional but recommended)

## Step 1: Install Java (if not already installed)

```bash
# Check if Java is installed
java -version

# If not installed, install via Homebrew
brew install openjdk@11

# Add Java to your PATH
export JAVA_HOME=/usr/local/opt/openjdk@11
export PATH="$JAVA_HOME/bin:$PATH"

# Verify installation
java -version
```

## Step 2: Install Jenkins

### Option A: Using Homebrew (Recommended)

```bash
# Install Jenkins
brew install jenkins-lts

# Start Jenkins
brew services start jenkins-lts

# Stop Jenkins
brew services stop jenkins-lts

# Restart Jenkins
brew services restart jenkins-lts
```

### Option B: Manual Installation

```bash
# Create Jenkins directory
mkdir -p ~/Library/Jenkins

# Download Jenkins WAR file
wget https://mirrors.jenkins.io/war-stable/latest/jenkins.war -O ~/Library/Jenkins/jenkins.war

# Run Jenkins
java -jar ~/Library/Jenkins/jenkins.war
```

## Step 3: Access Jenkins

1. Open your browser and go to: `http://localhost:8080`
2. Get the initial admin password:
   ```bash
   # For Homebrew installation
   cat /Users/$(whoami)/.jenkins/secrets/initialAdminPassword
   ```
3. Follow the setup wizard:
   - Install suggested plugins
   - Create first admin user
   - Configure Jenkins URL (use `http://localhost:8080`)

## Step 4: Install Required Plugins

1. Go to **Manage Jenkins** → **Manage Plugins**
2. Search and install these plugins:
   - **Pipeline** (for Declarative Pipeline support)
   - **GitHub Integration** (if using GitHub)
   - **Maven Integration**
   - **TestNG Results Plugin**
   - **Log Parser Plugin**
   - **Email Extension Plugin**
   - **Cobertura Plugin** (for code coverage)

## Step 5: Configure Global Tools

1. Go to **Manage Jenkins** → **Global Tool Configuration**
2. **Maven Configuration:**
   - Name: `Maven 3.9.0` (or your version)
   - MAVEN_HOME: `/usr/local/opt/maven` (or your Maven path)
3. **JDK Configuration:**
   - Name: `OpenJDK 11`
   - JAVA_HOME: `/usr/local/opt/openjdk@11`

## Step 6: Create a New Jenkins Job

### Option A: Using Jenkinsfile (Pipeline Job - Recommended)

1. Create a new item in Jenkins
2. Select **Pipeline**
3. In **Pipeline Definition**, select **Pipeline script from SCM**
4. Select **Git** as SCM
5. Add your repository URL
6. Set Script Path to `Jenkinsfile`
7. Save and run

### Option B: Using Freestyle Job

1. Create a new item → **Freestyle job**
2. Configure the job:
   - **Source Code Management:** Git (add your repo)
   - **Build:** Add build step → **Invoke top-level Maven targets**
     - Goals: `clean test`
   - **Post-build Actions:**
     - **Publish TestNG Results XML report**
     - Test Results XML: `target/surefire-reports/testng-results.xml`

## Step 7: Environment Variables

Add these global environment variables in Jenkins:

**Manage Jenkins** → **System** → **Global properties** → **Environment variables**

```
DEVICE_NAME=your_device_name
PLATFORM_VERSION=your_platform_version
AUTOMATION_NAME=Flutter
```

Or store them in `~/.jenkins/environment.properties`:

```properties
DEVICE_NAME=emulator-5554
PLATFORM_VERSION=14.0
AUTOMATION_NAME=Flutter
APP_PACKAGE=com.example.app
APP_ACTIVITY=.MainActivity
```

## Step 8: Run Appium Server

For your Appium tests to run, you need Appium running. Options:

### Option A: Start Appium manually before running tests

```bash
appium
```

### Option B: Start Appium in Jenkins pre-build step

Create a helper script in your project: `scripts/start-appium.sh`

```bash
#!/bin/bash
#!/bin/bash
# Kill any existing appium processes
pkill -f "appium" || true

# Wait for any processes to die
sleep 2

# Start Appium
appium > appium.log 2>&1 &
APPIUM_PID=$!

# Wait for Appium to be ready
sleep 5

# Check if Appium started successfully
if ! kill -0 $APPIUM_PID 2>/dev/null; then
    echo "Failed to start Appium"
    exit 1
fi

echo "Appium started with PID: $APPIUM_PID"
```

Then add this to your Jenkinsfile pre-build step.

## Step 9: Configure Test Execution

Your tests run with: `mvn clean test`

This will:
1. Compile your code
2. Run TestNG tests from `run-test.xml`
3. Generate reports in `target/surefire-reports/`

## Step 10: View Reports

After tests run:
1. Go to Job → **Test Results** to see TestNG results
2. Go to **Console Output** to see build logs
3. Check `target/test-output/extentReport.html` for Extent Reports

## Troubleshooting

### Build fails with "Java not found"
```bash
# Add Java to Jenkins environment
# Create/edit ~/.jenkins/jenkins.sh
export JAVA_HOME=/usr/local/opt/openjdk@11
```

### Appium connection errors
- Ensure Appium is running: `appium`
- Check your `config.properties` for correct Appium URL
- Verify device is connected: `adb devices`

### Tests timeout
- Increase Maven timeout in pom.xml:
  ```xml
  <systemPropertyVariables>
      <testng.timeout.ms>300000</testng.timeout.ms>
  </systemPropertyVariables>
  ```

### Port 8080 already in use
```bash
# Change Jenkins port
java -jar jenkins.war --httpPort=8090
```

## Backup Jenkins Configuration

```bash
# Backup Jenkins home directory
cp -r ~/.jenkins ~/.jenkins.backup

# Restore from backup
rm -rf ~/.jenkins
cp -r ~/.jenkins.backup ~/.jenkins
```

## Useful Jenkins CLI Commands

```bash
# Get Jenkins version
java -jar jenkins-cli.jar -s http://localhost:8080 version

# List jobs
java -jar jenkins-cli.jar -s http://localhost:8080 list-jobs

# Build a job
java -jar jenkins-cli.jar -s http://localhost:8080 build AppiumTests
```

## Next Steps

1. Push the `Jenkinsfile` to your repository
2. Create a new Pipeline job in Jenkins pointing to your repo
3. Configure your device/emulator credentials
4. Run your first build!
