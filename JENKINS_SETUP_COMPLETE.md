# Complete Jenkins Pipeline Setup - Step-by-Step Instructions

## ✅ Your System Status

| Component | Status | Version |
|-----------|--------|---------|
| Java | ✅ Installed | 25.0.2 |
| Maven | ✅ Installed | 3.9.12 |
| Appium | ✅ Installed | 3.2.0 |
| Jenkins | ✅ Running | http://localhost:8080 |
| Git | ✅ Installed | (from repo status) |
| Project Files | ✅ Pushed | GitHub synced |

**⚠️ Note:** Android Emulator is not currently running. You'll need to start one before tests can run.

---

## 🚀 PART 1: Start Android Emulator (IMPORTANT!)

Your tests need an Android device/emulator. Follow this FIRST:

### Option A: Start Android Emulator via Command Line

```bash
# List available emulators
emulator -list-avds

# Start the emulator (use your emulator name)
emulator -avd emulator-5554 &
# OR if you have a different emulator:
emulator -avd <your-emulator-name> &

# Wait 30 seconds for it to fully boot
sleep 30

# Verify device is ready
adb devices

# You should see:
# List of attached devices
# emulator-5554     device
```

### Option B: Start Android Emulator via Android Studio

1. Open Android Studio
2. Click **Tools → Device Manager** or **AVD Manager**
3. Find your emulator (e.g., `emulator-5554`)
4. Click the **Play button** (▶) to start it
5. Wait 30 seconds for full boot

### Option C: If You Don't Have an Emulator

Create a new Android Virtual Device:

```bash
# List available system images
android list sdk

# Create new emulator
avdmanager create avd -n TestDevice -k "system-images;android;29;armeabi-v7a"

# Start it
emulator -avd TestDevice &
```

**⚠️ Verify Emulator Started:**
```bash
adb devices
```
Should show at least one device with status `device` (not `offline`)

---

## 🔧 PART 2: Create Jenkins Pipeline Job

### Step 1: Access Jenkins Dashboard

1. Open browser: `http://localhost:8080`
2. You should see Jenkins dashboard after login

**Screenshot Visual:**
```
┌────────────────────────────────────────┐
│ Jenkins Dashboard                       │
├────────────────────────────────────────┤
│                                        │
│   [New Item] [People] [Settings]       │
│   [Manage Jenkins]                     │
│                                        │
│   No builds in history yet              │
│                                        │
└────────────────────────────────────────┘
```

---

### Step 2: Create New Job

**Exact Steps:**

1. Click **New Item** (in the left sidebar)
   
   ```
   Jenkins Dashboard Left Sidebar:
   ┌─────────────────────┐
   │ + New Item  ← CLICK │
   │ - People            │
   │ - Manage Jenkins    │
   └─────────────────────┘
   ```

2. **Enter Job Name:**
   - Type: `AppiumFlutterDriverTest`
   
   ```
   ┌────────────────────────────────────┐
   │ Job Name:                          │
   │ [AppiumFlutterDriverTest         ] │
   └────────────────────────────────────┘
   ```

3. **Select Job Type:**
   - Find and click **"Pipeline"** option
   
   ```
   Job Types (select one):
   ☐ Freestyle job
   ☐ Pipeline          ← CLICK THIS
   ☐ Multibranch Pipeline
   ☐ Multibranch Pipeline (GitHub)
   ```

4. **Click OK Button**
   - Located at bottom of form

**Result:** You'll be taken to the job configuration page

---

### Step 3: Configure Pipeline - Critical Settings

**On the Configuration Page:**

Scroll down to the **Pipeline** section. You should see:

```
Pipeline

Definition: [Dropdown] ▼

Currently shows: "Pipeline script"
CHANGE TO: "Pipeline script from SCM"
```

**a) Change Definition:**
1. Click the **Definition** dropdown
2. Select **"Pipeline script from SCM"**

```
┌──────────────────────────────────┐
│ Definition: ▼                    │
│ ┌──────────────────────────────┐ │
│ │ Pipeline script              │ │
│ │ Pipeline script from SCM  ← ✓ │
│ └──────────────────────────────┘ │
└──────────────────────────────────┘
```

**b) Configure SCM (Source Code Management):**

After changing to "Pipeline script from SCM", you'll see:

```
SCM: [Dropdown] ▼

Currently shows: "-None-"
CHANGE TO: "Git"
```

1. Click the **SCM** dropdown
2. Select **"Git"**

**c) Fill in Git Repository Details:**

After selecting Git, you'll see new fields:

```
┌─────────────────────────────────────────────┐
│ Repository URL:                             │
│ [https://github.com/saraniya-automations/  │
│  AppiumFlutterDriverTest.git              ] │
│                                             │
│ Credentials:                                │
│ [- None -] ▼  (leave as default)           │
│                                             │
│ Branches to build:                          │
│ Branch Specifier (blank for 'any'):        │
│ [*/main                                   ] │
│                                             │
│ Script Path:                                │
│ [Jenkinsfile                              ] │
│                                             │
└─────────────────────────────────────────────┘
```

**Fill in These Fields:**

| Field | Value |
|-------|-------|
| Repository URL | `https://github.com/saraniya-automations/AppiumFlutterDriverTest.git` |
| Credentials | Leave as "-None-" or select if prompted |
| Branch Specifier | `*/main` |
| Script Path | `Jenkinsfile` |

**Example Completed Form:**
```
┌─────────────────────────────────────────────────┐
│ Git                                             │
├─────────────────────────────────────────────────┤
│                                                 │
│ Repository URL:                                 │
│ https://github.com/saraniya-automations/       │
│ AppiumFlutterDriverTest.git            ✓ FILLED │
│                                                 │
│ Script Path:                                    │
│ Jenkinsfile                            ✓ FILLED │
│                                                 │
│ Branch:                                         │
│ */main                                 ✓ FILLED │
│                                                 │
└─────────────────────────────────────────────────┘
```

---

### Step 4: Save Configuration

1. **Scroll to the bottom** of the page
2. Look for **Save** button
3. Click **Save**

```
Bottom of Configuration Page:
┌──────────────────────────────┐
│ [Save]  [Preview]  [Cancel]  │
└──────────────────────────────┘
         Click Save →
```

**Result:** You'll be redirected to the job page

---

## 🎬 PART 3: Run Your First Build

### Step 1: Trigger Build

On the job page (**AppiumFlutterDriverTest**), you'll see:

```
Left Sidebar:
┌─────────────────────┐
│ Build Now      ← CLICK
│ Build History       │
│ Workspace           │
│ Configure           │
│ Delete              │
└─────────────────────┘
```

Click **Build Now**

**Result:** A new build (#1) will appear in Build History

### Step 2: Monitor Build Execution

1. In **Build History**, click the new build **#1**
   
   ```
   Build History:
   #1 (Running...)  ← CLICK THIS
   ```

2. You'll see the build details page with **Console Output**

3. **Click "Console Output"** to see real-time logs

```
Build #1 Details Page:
┌─────────────────────────────────┐
│ Status: IN PROGRESS             │
│ Updates (from latest):          │
│ ...                              │
│                                 │
│ [Console Output]  ← CLICK HERE  │
│ [Edit Build Info]               │
└─────────────────────────────────┘
```

### Step 3: Watch the Build Process

The console output should show stages like:

```
========================================
Started by user admin
========================================
[Pipeline] Start of Pipeline

[Pipeline] Stage Checkout
[Pipeline] Checking out source code
Cloning the repository...
...

[Pipeline] Stage Setup
Java Version: openjdk version "25.0.2"...
Maven Version: Apache Maven 3.9.12...

[Pipeline] Stage Build
Building...

[Pipeline] Stage Start Appium
Starting Appium Server...

[Pipeline] Stage Run Tests
Running tests...

[Pipeline] Stage Generate Reports
...
```

---

## ✅ Expected Results

### If Build SUCCEEDS (Green ✓)

After ~10-15 minutes, you should see:

```
[Pipeline] End of Pipeline
BUILD SUCCESS

Total time: 10 min 45 sec
```

**Then check:**
1. Go back to Job page (click job name in breadcrumb)
2. Click **"Test Results"** tab (if visible)
3. Should show test count (e.g., "5 tests passed")
4. Look for **Extent Report** link to view detailed results

---

### If Build FAILS (Red ✗)

Don't worry! Common issues on first run:

**Issue #1: "emulator-5554 not found"**
```
Solution: Start Android Emulator first (Part 1 above)
```

**Issue #2: "Appium connection refused"**
```
Solution: Verify emulator is fully booted
Command: adb devices
Should show "emulator-5554 device"
```

**Issue #3: "APK not found"**
```
Error: provider_shopper_flutter_sample.apk not found
Solution: Verify APK path in config.properties:
appPath=/Users/ajaysara/Documents/AppiumFlutterDriverTest/...
```

**Issue #4: "No tests found"**
```
Solution: Verify run-test.xml exists in project root
And check it includes test methods
```

**Issue #5: Jenkins can't find Java/Maven**
```
Solution: Check Jenkins Global Tool Configuration
Manage Jenkins → Global Tool Configuration
Set JAVA_HOME and MAVEN_HOME paths
```

---

## 🔍 Accessing Build Reports

### After Successful Build:

**1. Test Summary (Dashboard)**
```
Job: AppiumFlutterDriverTest
├─ Last Build: SUCCESS
├─ Test Result: 5 passed, 0 failed
└─ Duration: 10 min 45 sec
```

**2. Console Output**
- Job page → Build #1 → Console Output
- Shows all execution logs

**3. Test Results Details**
- Job page → Test Results tab
- Lists individual test results

**4. Extent HTML Report**
- Job page → Extent Report (link)
- Detailed screenshots and execution data

**5. Download Artifacts**
- Job page → Build #1 → Build Artifacts
- Download reports, logs, screenshots

---

## 🔄 Rebuilding After Fixing Issues

If your first build fails:

1. **Fix the issue** (e.g., start emulator, fix APK path)
2. **Go to job page**
3. **Click "Build Now"** again
4. **Check console output** for the new build

Each build is numbered (#1, #2, #3, etc.)

---

## 📋 Complete Checklist

Before clicking **Build Now**:

- [ ] Android Emulator is running (`adb devices` shows a device)
- [ ] APK file exists at path specified in config.properties
- [ ] Jenkinsfile is pushed to main branch
- [ ] Jenkins job is configured with:
  - [ ] Repository URL set
  - [ ] Branch: `*/main`
  - [ ] Script Path: `Jenkinsfile`
  - [ ] Definition: "Pipeline script from SCM"

---

## Quick Reference Commands

Keep these handy while setting up:

```bash
# Start emulator
emulator -avd emulator-5554 &

# Check emulator status
adb devices

# View Jenkins logs
tail -f ~/.jenkins/logs/jenkins.log

# Stop Jenkins if needed
brew services stop jenkins-lts

# Restart Jenkins
brew services restart jenkins-lts

# Run tests locally (optional, for testing)
mvn clean test
```

---

## ✨ Success Indicators

Your pipeline is working when:

✅ Jenkins successfully clones your Git repository  
✅ Maven builds the project without errors  
✅ Appium server starts and connects  
✅ Tests run and generate results  
✅ HTML reports are created  
✅ Build shows BUILD SUCCESS (green)

---

## 🎓 Next Steps After First Success

1. **Schedule Regular Builds:**
   - Job Configuration → Build Triggers
   - Check "Poll SCM"
   - Schedule: `H/15 * * * *` (every 15 min)

2. **Set Up Email Notifications:**
   - Job Configuration → Post-build Actions
   - Add "Email Notification"
   - Enter your email

3. **Monitor Build Trends:**
   - View build history
   - Track pass/fail rates

4. **Add More Tests:**
   - Modify `run-test.xml`
   - Add new test classes
   - Rebuild to run them

---

## 📞 Troubleshooting Help

| Problem | See Section |
|---------|-------------|
| Emulator won't start | "Part 1: Start Android Emulator" |
| Can't configure pipeline | "Part 2: Create Jenkins Pipeline Job" |
| Tests fail on first run | "If Build FAILS (Red ✗)" |
| Can't see test results | "Accessing Build Reports" |
| Need to rebuild | "Rebuilding After Fixing Issues" |

---

## One More Thing: Making Scripts Executable

The helper scripts need to be executable:

```bash
chmod +x ./scripts/start-appium.sh
chmod +x ./scripts/stop-appium.sh
```

Then commit:
```bash
git add scripts/
git commit -m "Make scripts executable"
git push origin main
```

---

**Ready to build?** Follow the steps above starting with Part 1! 🚀

When you click **Build Now**, watch the Console Output and let me know if:
- ✅ Build succeeds
- ❌ Build fails (show me the error from Console Output)
- ❓ Anything is unclear
