# Jenkins Pipeline Setup - Step-by-Step Visual Guide

## Prerequisites ✅
- Jenkins running on `http://localhost:8080`
- Admin user created
- System plugins installed (Pipeline, Maven, TestNG, GitHub, etc.)

---

## Step 1: Create New Item

```
Jenkins Dashboard
    ↓
Left Sidebar: Click "New Item" or "Create a new job"
    ↓
Enter Job Name: AppiumFlutterDriverTest
    ↓
Select: Pipeline
    ↓
Click OK
```

**Visual Reference:**
```
┌─────────────────────────────┐
│ Jenkins Dashboard           │
├─────────────────────────────┤
│ Sidebar:                    │
│ ├─ New Item ← CLICK HERE   │
│ ├─ People                   │
│ ├─ Settings                 │
│ └─ Manage Jenkins            │
└─────────────────────────────┘
```

---

## Step 2: Configure the Pipeline

### Location: Pipeline Tab

Navigate to the **Pipeline** section (scroll down on the config page)

```
╔════════════════════════════════════════╗
║ Pipeline Configuration                 ║
╠════════════════════════════════════════╣
║                                        ║
║ Definition: ▼ Pipeline script from SCM ║
║                                        ║
║ SCM: ▼ Git                             ║
║                                        ║
║ Repository URL:                        ║
║ ┌──────────────────────────────────┐  ║
║ │https://github.com/saraniya-      │  ║
║ │automations/AppiumFlutter         │  ║
║ │DriverTest.git                    │  ║
║ └──────────────────────────────────┘  ║
║                                        ║
║ Credentials: [leave as-is]             ║
║                                        ║
║ Branch:                                ║
║ ┌──────────────────────────────────┐  ║
║ │*/main                            │  ║
║ └──────────────────────────────────┘  ║
║                                        ║
║ Script Path:                           ║
║ ┌──────────────────────────────────┐  ║
║ │Jenkinsfile                       │  ║
║ └──────────────────────────────────┘  ║
║                                        ║
╚════════════════════════════════════════╝
```

---

## Step 3: Save Configuration

1. Scroll to **bottom** of the page
2. Click **Save** button
3. You'll be taken to the job page

---

## Step 4: Run First Build

```
Job Page (AppiumFlutterDriverTest)
    ↓
Left Sidebar: Click "Build Now"
    ↓
Build #1 appears in "Build History"
    ↓
Click build #1
    ↓
Click "Console Output" to see logs
```

**Build Stages (shown in Console Output):**
```
[Main] Starting build
✓ Checkout - Cloning repository
✓ Setup - Verifying Java and Maven
✓ Build - Compiling project
✓ Start Appium - Starting Appium server
✓ Run Tests - Executing tests
✓ Generate Reports - Creating HTML reports
✓ Post Build - Cleanup and archiving
```

---

## What to Expect

### ✅ Successful Build
- Console shows: `[Main] BUILD SUCCESS`
- Build time: ~5-10 minutes (first run takes longer)
- Test results appear under "Test Results" tab
- Extent Report generated with screenshots

### ❌ First Build May Fail If:
- Appium can't start (need device connected)
- Device not available
- Port conflicts

**This is NORMAL!** See troubleshooting section below.

---

## After Configuration: What Each Section Means

### Build History
```
AppiumFlutterDriverTest
├─ #1 (First build - might fail, that's OK)
├─ #2 (After you fix issues)
└─ #3 (When all is configured properly)
```

Click any build to see:
- **Console Output**: Full execution logs
- **Test Results**: Passed/Failed test summary
- **Artifacts**: Downloaded reports and logs

### Typical Job Page Layout
```
┌─────────────────────────────────────────┐
│ AppiumFlutterDriverTest                  │
├─────────────────────────────────────────┤
│ Sidebar:                                │
│ ├─ Build Now          ← Run tests       │
│ ├─ Build History      ← See past builds │
│ ├─ #1, #2, #3...     ← Click for details
│ ├─ Last Build         ← Latest result   │
│ └─ Configure          ← Edit job        │
│                                         │
│ Main Area:                              │
│ ├─ Build #1 (SUCCESS/FAILURE)          │
│ ├─ Test Results: 5 passed, 0 failed    │
│ ├─ Build Duration: 5 min 30 sec        │
│ └─ Extent Report [link]                │
└─────────────────────────────────────────┘
```

---

## Troubleshooting First Build

### Issue: Build fails immediately
**Check:**
1. Java installed: `java -version`
2. Maven installed: `mvn -version`
3. Repository URL is correct
4. Script Path is exactly `Jenkinsfile`

### Issue: "Appium not found" error
**Solution:**
```bash
# Install Appium globally
npm install -g appium

# Verify
appium --version
```

### Issue: Tests fail - "Device not found"
**Solution:**
```bash
# Start emulator or connect physical device
adb devices

# Should show at least one device
```

### Issue: Port 4723 already in use
**Solution:**
```bash
# Kill existing Appium
pkill -f appium

# Or change port in Jenkinsfile (line 20-21)
```

### Issue: Jenkins can't find test reports
**Solution:**
- Verify `run-test.xml` exists in project root
- Make sure tests actually ran (check console output)
- May need to wait for full build to complete

---

## Monitoring Real-Time Build

### Method 1: From Jenkins UI
1. Go to job page
2. Click the running build #X
3. Click **Console Output**
4. Scroll to bottom to see live logs
5. Refresh page for updates

### Method 2: SSH into Jenkins
```bash
tail -f ~/.jenkins/jobs/AppiumFlutterDriverTest/builds/1/log
```

### What to Look For in Logs
```
✓ "[Main] Checking out source code"      ← Repository cloned
✓ "[Main] Java Version: openjdk 17..."   ← Java found
✓ "[Main] Maven Version: 3.x.x"          ← Maven found
✓ "Appium started successfully"          ← Appium running
✓ "Tests completed"                      ← Tests executed
✓ "Extent Report generated"              ← Reports created
```

---

## Next: Accessing Test Results

### After Build Completes (Successfully):

1. **View Test Summary:**
   - Job page → Click "Test Results"
   - Shows: Passed/Failed/Skipped count

2. **View Detailed Report:**
   - Job page → Look for "Extent Report" link
   - Full test execution with screenshots

3. **Download Artifacts:**
   - Job page → Scroll down to "Build Artifacts"
   - Download HTML reports, logs, screenshots

---

## Common Build Results

### ✅ SUCCESS
- Green checkmark
- All tests executed
- Test results visible
- Reports generated

### ⚠️ UNSTABLE
- Yellow ball
- Some tests failed
- But build configuration is correct
- Fix your tests and rebuild

### ❌ FAILURE
- Red X
- Build couldn't complete
- Check console output for errors
- Common: Java not found, Appium not started

---

## Setting Build Triggers (Optional - Later)

Once your build works once, you can set it to run automatically:

**Job Configuration → Build Triggers:**
```
☑ Poll SCM
  Schedule: H/15 * * * *
  (Runs every 15 minutes if code changes)

OR

☑ GitHub hook trigger for GITScm polling
  (Runs when you push to GitHub)
```

---

## Environment Variables Available in Jenkinsfile

Your Jenkinsfile has these pre-configured:

```groovy
environment {
    APPIUM_URL = 'http://127.0.0.1:4723/wd/hub'
    PLATFORM_NAME = 'Android'
    AUTOMATION_NAME = 'Flutter'
}
```

You can modify these in the Jenkinsfile if needed.

---

## Quick Reference Card

| Task | Steps |
|------|-------|
| **Create Job** | New Item → Pipeline → OK |
| **Configure** | Pipeline tab → SCM Git → Script Path: Jenkinsfile |
| **Run Build** | Click "Build Now" |
| **View Logs** | Build #X → Console Output |
| **See Tests** | Job page → Test Results |
| **View Report** | Job page → Extent Report |
| **Debug** | Console Output + Appium logs |

---

## Success Indicators ✅

After your first successful build:
- Build shows "✓ SUCCESS" (green)
- Test Results tab shows test count
- Extent Report is accessible
- Build artifacts are archived
- Jenkins Dashboard shows job as healthy

**Congratulations!** Your pipeline is now ready for continuous testing! 🎉

---

## Next Steps After Setup Works

1. **Schedule Builds:**
   - Set Poll SCM to run nightly
   - Or use GitHub webhooks

2. **Add Email Notifications:**
   - Configure SMTP in Jenkins
   - Get results emailed to you

3. **Expand Tests:**
   - Add more test cases
   - Modify `run-test.xml` to include them

4. **Scale Up:**
   - Add Jenkins agents
   - Run parallel builds

5. **Monitor Trends:**
   - Track pass rates over time
   - Identify flaky tests

---

For more details, see:
- `JENKINS_SETUP.md` - Full installation guide
- `JENKINS_JOB_SETUP.md` - Advanced configuration
- `Jenkinsfile` - Pipeline script details
