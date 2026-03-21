# Jenkins Job Configuration Guide

## Detailed Steps to Configure Your First Jenkins Job

### Prerequisites Checklist
- ✅ Jenkins installed and running
- ✅ Java 11+ configured
- ✅ Maven installed
- ✅ Appium installed and accessible via command line
- ✅ Git installed
- ✅ Your project pushed to a Git repository (GitHub, GitLab, Bitbucket, etc.)

---

## Method 1: Using Jenkinsfile (Recommended - Pipeline Job)

### Step 1: Create a New Job in Jenkins

1. Open Jenkins: `http://localhost:8080`
2. Click **New Item** (or **Create a new job**)
3. Enter job name: `AppiumFlutterDriverTest`
4. Select **Pipeline**
5. Click **OK**

### Step 2: Configure General Settings

**Job Configuration Page:**

1. **General Tab:**
   - ✅ Check **Discard old builds**
   - Days to keep builds: `30`
   - Max # of builds to keep: `50`

2. **Build Triggers Tab:**
   - Check **Poll SCM** (optional, for automatic builds)
   - Schedule: `H/15 * * * *` (polls every 15 minutes)
   - Or check **GitHub hook trigger** if using GitHub

### Step 3: Configure Pipeline

1. **Pipeline Tab:**
   - Definition: **Pipeline script from SCM**
   - SCM: **Git**
   - Repository URL: `https://github.com/your-username/AppiumFlutterDriverTest.git`
   - Branch: `*/main` (or your default branch)
   - Script Path: `Jenkinsfile`

2. **SSH Key Setup (if using SSH):**
   - In Jenkins: **Manage Jenkins** → **Manage Credentials**
   - Click **System** → **Global credentials**
   - Click **Add Credentials**
   - Type: **SSH Username with private key**
   - Username: `git`
   - Private key: Select **Enter directly** and paste your SSH key
   - Click **Create**
   
   Then in Pipeline configuration:
   - Credentials: Select the SSH credential you just created

### Step 4: Save and Test

1. Click **Save**
2. Click **Build Now**
3. Watch the build in **Build History** → Click the build #
4. Check **Console Output** for logs

---

## Method 2: Freestyle Job Configuration (Alternative)

### Step 1: Create a New Freestyle Job

1. In Jenkins, click **New Item**
2. Job name: `AppiumFlutterDriverTest-Freestyle`
3. Select **Freestyle job**
4. Click **OK**

### Step 2: Source Code Management

1. **Git**
   - Repository URL: `https://github.com/your-username/AppiumFlutterDriverTest.git`
   - Credentials: Select or create GitHub credentials
   - Branch: `*/main`

### Step 3: Build Environment

1. Check ✅ **Delete workspace before build starts**
2. Check ✅ **Abort the build if it's stuck**
3. Timeout: `1 hour`

### Step 4: Build Steps

Click **Add build step** → **Invoke top-level Maven targets**

**Step 1: Build**
```
Clean install
Goals: clean compile -DskipTests
```

**Step 2: Start Appium**

Add build step → **Execute shell**
```bash
#!/bin/bash
# Kill existing Appium processes
pkill -f "appium" || true
sleep 2

# Start Appium
appium --port 4723 --address 127.0.0.1 > "${WORKSPACE}/appium.log" 2>&1 &
APPIUM_PID=$!

# Wait for startup
sleep 5

# Verify it's running
if ! kill -0 $APPIUM_PID; then
    echo "Failed to start Appium"
    cat "${WORKSPACE}/appium.log"
    exit 1
fi

echo "Appium started (PID: $APPIUM_PID)"
```

**Step 3: Run Tests**

Add build step → **Invoke top-level Maven targets**
```
Goals: test
```

### Step 5: Post-Build Actions

1. **Publish TestNG Results**
   - Test Results: `target/surefire-reports/testng-results.xml`

2. **Archive the artifacts**
   - Files to archive: `test-output/**,target/surefire-reports/**,appium.log`

3. **Publish HTML Reports**
   - HTML directory to archive: `test-output/html`
   - Index page: `extentReport.html`
   - Report name: `Extent Report`

4. **Email Notification** (Optional)
   - Recipients: `your-email@example.com`
   - Send when build: Fails or Unstable

### Step 6: Save and Build

1. Click **Save**
2. Click **Build Now**
3. Check results

---

## Configuring Credentials in Jenkins

### Adding GitHub Credentials

1. Go to **Manage Jenkins** → **Manage Credentials**
2. Click **System** → **Global credentials (unrestricted)**
3. Click **Add Credentials**

**For HTTPS Access:**
- Kind: **Username with password**
- Username: `your-github-username`
- Password: `your-github-personal-access-token`
- ID: `github-credentials`

**For SSH Access:**
- Kind: **SSH Username with private key**
- Username: `git`
- Private Key: Paste your SSH key
- ID: `github-ssh-key`

---

## Configuring Global Environment Variables

These apply to all jobs:

1. Go to **Manage Jenkins** → **System Configuration**
2. Scroll to **Global properties**
3. Check ✅ **Environment variables**
4. Click **Add**

Add these variables:

```
APPIUM_PORT = 4723
APPIUM_HOST = 127.0.0.1
PLATFORM_NAME = Android
AUTOMATION_NAME = Flutter
```

---

## Configuring Device-Specific Credentials

For storing device information securely:

1. **Manage Jenkins** → **Manage Credentials**
2. **Global credentials** → **Add Credentials**
3. **Kind:** Secret text
4. **Secret:** Your device ID
5. **ID:** `device-name`

Then in your Jenkinsfile or job configuration:
```groovy
environment {
    DEVICE_NAME = credentials('device-name')
}
```

---

## Monitoring Your Job

### Build Dashboard
- Shows build history with status (✅ Success, ❌ Failed, ⚠️ Unstable)
- Click any build to see details

### Console Output
- Real-time logs from your build
- Shows Maven compilation, test execution, and Appium logs

### Test Results
- Automatic TestNG results display
- Shows passed/failed tests
- Click failed tests for stack traces

### Artifacts
- Download generated reports
- Access `extentReport.html` for detailed test execution

---

## Troubleshooting Common Issues

### Issue 1: Build fails with "Java not found"
**Solution:**
1. Verify Java installation: `java -version`
2. In Jenkins: **Manage Jenkins** → **Global Tool Configuration**
3. Add JDK:
   - Name: `OpenJDK 11`
   - JAVA_HOME: `/usr/local/opt/openjdk@11`
4. In Job Configuration → **Build Environment**, select this JDK

### Issue 2: Maven build fails
**Solution:**
1. Check Maven is installed: `mvn -version`
2. In Jenkins: **Manage Jenkins** → **Global Tool Configuration**
3. Add Maven:
   - Name: `Maven 3.9.0`
   - MAVEN_HOME: `/usr/local/opt/maven`

### Issue 3: Tests can't connect to Appium
**Solution:**
1. Verify Appium is running: `ps aux | grep appium`
2. Check Appium URL in `config.properties`: `http://127.0.0.1:4723/wd/hub`
3. Verify device is connected: `adb devices`
4. Check mobile device automation is enabled

### Issue 4: "No space left on device" error
**Solution:**
```bash
# Delete old Jenkins build artifacts
rm -rf ~/.jenkins/jobs/*/builds/*

# Or configure auto cleanup in Job: Discard old builds → Keep last 10
```

### Issue 5: Build takes too long or times out
**Solution:**
1. Increase timeout in Jenkinsfile (currently 1 hour)
2. Use parallel test execution:
   ```
   mvn test -X -DThreadCount=4 -DThreadCountForks=4
   ```

---

## Running Tests Manually for Testing

Before relying on Jenkins, test locally:

```bash
# Start Appium
appium &

# Run tests
mvn clean test

# Stop Appium
pkill -f appium
```

---

## Advanced: Setting Up Test Email Notifications

### Install Email Extension Plugin

1. **Manage Jenkins** → **Manage Plugins**
2. Search "Email Extension Plugin"
3. Install and restart Jenkins

### Configure Email

1. **Manage Jenkins** → **System Configuration**
2. Find **Extended E-mail Notification**

```
SMTP Server: smtp.gmail.com
SMTP Port: 465
Use SSL: ✅
SMTP Username: your-email@gmail.com
SMTP Password: your-app-password (not your Gmail password)
```

### Add Email Notification to Job

In Job Post-build Actions → **Editable Email Notification**

```
Project Recipient List: your-email@gmail.com
Default Subject: Build $BUILD_NUMBER - Test Report
Default Content: 

Build Status: $BUILD_STATUS
Build #: $BUILD_NUMBER

Test Summary:
_____________________

View Full Report: $BUILD_URL
```

---

## Next Steps

1. ✅ Commit your Jenkinsfile to your repository
2. ✅ Configure the Jenkins job using one of the methods above
3. ✅ Run your first build
4. ✅ Check test reports
5. ✅ Schedule recurring builds
6. ✅ Set up email notifications
