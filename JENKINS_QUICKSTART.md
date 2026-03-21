# Quick Start Guide - Jenkins Setup (5 Minutes)

## TL;DR Installation & Setup

### 1. Install Jenkins (Choose One Method)

**Using Homebrew (Easiest):**
```bash
brew install jenkins-lts
brew services start jenkins-lts
```

**Or Manual:**
```bash
java -jar ~/Downloads/jenkins.war
```

### 2. Access Jenkins
Open browser: `http://localhost:8080`

Get admin password:
```bash
cat ~/.jenkins/secrets/initialAdminPassword
```

### 3. Install Plugins

In Jenkins: **Manage Jenkins** → **Manage Plugins** → Install:
- Pipeline
- Maven Integration
- TestNG Results Plugin
- GitHub Integration (if using GitHub)
- Log Parser Plugin

### 4. Create New Pipeline Job

In Jenkins: **New Item** → `AppiumFlutterDriverTest` → **Pipeline** → OK

Configuration:
```
Pipeline → Pipeline script from SCM
SCM: Git
Repository URL: <your-repo-url>
Branch: */main
Script Path: Jenkinsfile
```

### 5. Save & Build
Click **Save** → **Build Now**

### 6. View Results
Click build #1 → **Console Output**

---

## File Structure

Your project now has:

```
AppiumFlutterDriverTest/
├── Jenkinsfile                          # Pipeline configuration
├── JENKINS_SETUP.md                    # Installation & setup guide
├── JENKINS_JOB_SETUP.md               # Detailed job configuration
├── scripts/
│   ├── start-appium.sh                # Appium startup script
│   └── stop-appium.sh                 # Appium cleanup script
├── src/
│   └── test/
│       ├── java/                      # Your test code
│       └── resources/                 # Test resources
├── pom.xml                            # Maven build configuration
└── run-test.xml                       # TestNG suite configuration
```

---

## Common Commands

```bash
# Start Appium manually
appium

# Run tests locally
mvn clean test

# Run specific test class
mvn test -Dtest=LoginTest

# View Jenkins logs
tail -f ~/.jenkins/logs/jenkins.log

# Visit Jenkins
open http://localhost:8080

# Stop Jenkins
brew services stop jenkins-lts

# Check if services running
brew services list
```

---

## Environment Setup (One-Time)

```bash
# Install Java 11
brew install openjdk@11

# Install Maven
brew install maven

# Install Appium
npm install -g appium

# Verify installations
java -version
mvn -version
appium --version
```

---

## Troubleshooting in 30 Seconds

| Issue | Solution |
|-------|----------|
| Can't access Jenkins | `brew services start jenkins-lts` |
| Tests fail locally | `appium &` then `mvn clean test` |
| "Java not found" | `brew install openjdk@11` |
| Port 8080 in use | `brew services restart jenkins-lts` or use `--httpPort=8090` |
| Appium won't connect | Verify: `ps aux \| grep appium` and `adb devices` |

---

## Monitoring Your Job

After running a build:

1. **Test Results:** Click job → **Test Results** tab
2. **Console Logs:** Click build # → **Console Output**
3. **Reports:** Click job → **Extent Report**
4. **Artifacts:** Click build → **Build Artifacts** section

---

## Success Indicators ✅

- Build completes without errors
- Test results show in Jenkins dashboard
- `Extent Report` is generated
- Appium logs show device connection

---

## Next: Advanced Configuration

Once basic setup works, see detailed guides:
- **JENKINS_SETUP.md** - Full installation guide
- **JENKINS_JOB_SETUP.md** - Advanced job configuration
- **Jenkinsfile** - Pipeline script reference

---

## Support Resources

- Jenkins Docs: https://www.jenkins.io/doc/
- Appium Docs: https://appium.io/
- Maven Guide: https://maven.apache.org/guides/
- TestNG Docs: https://testng.org/

---

**Ready?** Start with step 1 above, then come back here if stuck! 🚀
