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
        // Java configuration
        JAVA_HOME = '/usr/local/opt/openjdk@17'
        PATH = "${JAVA_HOME}/bin:${PATH}"
        
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
                    echo "Java Version:"
                    java -version
                    echo ""
                    echo "Maven Version:"
                    mvn -version
                '''
            }
        }
        
        stage('Build') {
            steps {
                echo "=== Building project ==="
                sh 'mvn clean compile -DskipTests'
            }
        }
        
        stage('Verify Services') {
            steps {
                echo "=== Checking Appium and Emulator ==="
                sh '''
                    echo "Checking if Appium is running..."
                    if curl -s http://127.0.0.1:4723/wd/hub/status > /dev/null; then
                        echo "✓ Appium is running"
                    else
                        echo "✗ Appium is NOT running!"
                        echo "Start Appium with: appium"
                        exit 1
                    fi
                    
                    echo ""
                    echo "Checking emulator/device..."
                    adb devices
                    
                    if adb devices | grep -q "device$"; then
                        echo "✓ Emulator/Device is connected"
                    else
                        echo "✗ No emulator/device found!"
                        echo "Start emulator: emulator -avd Medium_Phone_API_36.1 &"
                        exit 1
                    fi
                '''
            }
        }
        
        stage('Run Tests') {
            steps {
                echo "=== Running Flutter Tests ==="
                sh '''
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
            echo "❌ BUILD FAILED - Verify Appium and emulator are running"
            echo "Start services with:"
            echo "  1. emulator -avd Medium_Phone_API_36.1 &"
            echo "  2. appium"
            echo "Then rebuild"
        }
    }
}
