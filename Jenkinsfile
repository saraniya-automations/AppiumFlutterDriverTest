pipeline {
    agent any
    
    options {
        // Keep last 30 builds
        buildDiscarder(logRotator(numToKeepStr: '30'))
        // Timeout after 1 hour
        timeout(time: 1, unit: 'HOURS')
        // Add timestamps to console output
        timestamps()
    }
    
    environment {
        // Appium configuration
        APPIUM_URL = 'http://127.0.0.1:4723/wd/hub'
        PLATFORM_NAME = 'Android'
        DEVICE_NAME = 'Medium_Phone_API_36.1'
        AUTOMATION_NAME = 'Flutter'
        
        // Project configuration
        PROJECT_NAME = 'AppiumFlutterDriverTest'
        REPORT_DIR = '${WORKSPACE}/test-output'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo "=== Checking out source code ==="
                checkout scm
            }
        }
        
        stage('Setup') {
            steps {
                echo "=== Verifying Environment ==="
                sh '''
                    export JAVA_HOME=/opt/homebrew/opt/openjdk@17
                    export PATH="${JAVA_HOME}/bin:/opt/homebrew/bin:/usr/local/bin:${PATH}"
                    
                    echo "JAVA_HOME: $JAVA_HOME"
                    echo "Java Path: $(which java)"
                    echo "Java Version:"
                    java -version
                    echo ""
                    echo "Maven Version:"
                    mvn -version
                    echo ""
                    echo "Appium availability:"
                    which appium || echo "⚠ Appium not in PATH"
                    echo ""
                    echo "ADB Version:"
                    adb version | head -1
                '''
            }
        }
        
        stage('Build') {
            steps {
                echo "=== Building project ==="
                sh '''
                    export JAVA_HOME=/opt/homebrew/opt/openjdk@17
                    export PATH="${JAVA_HOME}/bin:/opt/homebrew/bin:/usr/local/bin:${PATH}"
                    
                    echo "Using Java: $(java -version 2>&1 | head -1)"
                    mvn clean compile -DskipTests
                '''
            }
        }
        
        stage('Start Services') {
            steps {
                echo "=== Starting Services ==="
                sh '''
                    export PATH="/opt/homebrew/bin:/usr/local/bin:${PATH}"
                    
                    # Kill any existing Appium
                    pkill -f "appium" 2>/dev/null || true
                    sleep 2
                    
                    # Start Appium in background
                    echo "Starting Appium server..."
                    appium > /tmp/appium.log 2>&1 &
                    APPIUM_PID=$!
                    echo "Appium PID: $APPIUM_PID"
                    
                    # Wait for Appium to initialize
                    for i in {1..15}; do
                        if curl -s http://127.0.0.1:4723/wd/hub/status > /dev/null 2>&1; then
                            echo "✓ Appium is responding"
                            break
                        fi
                        if [ $i -lt 15 ]; then
                            echo "Waiting for Appium to start... ($i/15)"
                            sleep 1
                        fi
                    done
                    
                    # Final check
                    if ! curl -s http://127.0.0.1:4723/wd/hub/status > /dev/null 2>&1; then
                        echo "✗ Appium failed to start"
                        echo "=== Appium Log ==="
                        cat /tmp/appium.log
                        exit 1
                    fi
                    
                    # Check emulator/device
                    echo ""
                    echo "Checking for connected emulator/device..."
                    sleep 2
                    adb devices
                    sleep 2
                    
                    DEVICE_COUNT=$(adb devices | grep -c "device$")
                    if [ $DEVICE_COUNT -gt 0 ]; then
                        echo "✓ Found $DEVICE_COUNT connected device(s)"
                    else
                        echo "✗ No emulator/device connected"
                        echo "Start emulator with: emulator -avd Medium_Phone_API_36.1 &"
                        exit 1
                    fi
                '''
            }
        }
        
        stage('Run Tests') {
            steps {
                echo "=== Running Flutter Tests ==="
                sh '''
                    export JAVA_HOME=/opt/homebrew/opt/openjdk@17
                    export PATH="${JAVA_HOME}/bin:/opt/homebrew/bin:/usr/local/bin:${PATH}"
                    
                    echo "Using Java: $(java -version 2>&1 | head -1)"
                    mvn clean test
                '''
            }
        }
        
        stage('Generate Reports') {
            steps {
                echo "=== Test Reports Ready ==="
                sh '''
                    if [ -d "target/test-output" ]; then
                        echo "✓ Test reports generated in target/test-output/"
                    else
                        echo "Note: No test-output directory found"
                    fi
                    
                    if [ -d "target/surefire-reports" ]; then
                        echo "✓ Surefire reports available in target/surefire-reports/"
                    fi
                '''
            }
        }
    }
    
    post {
        always {
            echo "=== Build Cleanup ==="
            
            // Kill Appium
            sh 'pkill -f "appium" || true'
            
            // Archive test reports and artifacts
            archiveArtifacts artifacts: 'target/surefire-reports/**,target/test-output/**,test-output/**', 
                             allowEmptyArchive: true
            
            // Publish test results if available
            junit testResults: 'target/surefire-reports/**/*.xml', 
                  allowEmptyResults: true
        }
        
        success {
            echo "✅ BUILD SUCCESS - Tests passed!"
        }
        
        failure {
            echo "❌ BUILD FAILED"
            echo "Most likely cause: Emulator not running"
            echo ""
            echo "Fix and retry:"
            echo "  1. Start emulator: emulator -avd Medium_Phone_API_36.1 &"
            echo "  2. Wait 30 seconds for full boot"
            echo "  3. Click 'Build Now' in Jenkins"
            echo ""
            echo "Jenkins will start Appium automatically"
        }
    }
}
