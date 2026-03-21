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
        JAVA_HOME = '/usr/local/opt/openjdk@11'
        PATH = "${JAVA_HOME}/bin:${PATH}"
        
        // Appium configuration
        APPIUM_URL = 'http://127.0.0.1:4723/wd/hub'
        PLATFORM_NAME = 'Android'
        DEVICE_NAME = credentials('device-name') // Configure this in Jenkins
        AUTOMATION_NAME = 'Flutter'
        
        // Project configuration
        PROJECT_NAME = 'AppiumFlutterDriverTest'
        REPORT_DIR = '${WORKSPACE}/test-output'
    }
    
    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "=== Checking out source code ==="
                    checkout scm
                }
            }
        }
        
        stage('Setup') {
            steps {
                script {
                    echo "=== Setting up environment ==="
                    sh '''
                        echo "Java Version:"
                        java -version
                        echo ""
                        echo "Maven Version:"
                        mvn -version
                    '''
                }
            }
        }
        
        stage('Build') {
            steps {
                script {
                    echo "=== Building project ==="
                    sh '''
                        mvn clean compile -DskipTests
                    '''
                }
            }
        }
        
        stage('Start Appium') {
            steps {
                script {
                    echo "=== Starting Appium Server ==="
                    sh '''
                        # Kill any existing appium processes
                        pkill -f "appium" || true
                        sleep 2
                        
                        # Start Appium in background
                        appium > "${WORKSPACE}/appium.log" 2>&1 &
                        APPIUM_PID=$!
                        
                        # Wait for Appium to start
                        sleep 5
                        
                        # Verify Appium is running
                        if ! kill -0 $APPIUM_PID 2>/dev/null; then
                            echo "Failed to start Appium"
                            cat "${WORKSPACE}/appium.log"
                            exit 1
                        fi
                        
                        echo "Appium started successfully with PID: $APPIUM_PID"
                    '''
                }
            }
        }
        
        stage('Run Tests') {
            steps {
                script {
                    echo "=== Running tests ==="
                    sh '''
                        # Give Appium more time to initialize
                        sleep 3
                        
                        # Run Maven tests
                        mvn test -X 2>&1 | tee "${WORKSPACE}/test-execution.log"
                    '''
                }
            }
        }
        
        stage('Generate Reports') {
            steps {
                script {
                    echo "=== Generating test reports ==="
                    sh '''
                        # Create report directory if it doesn't exist
                        mkdir -p "${WORKSPACE}/test-output"
                        
                        # Copy Extent reports
                        if [ -d "target/test-output" ]; then
                            cp -r target/test-output/* "${WORKSPACE}/test-output/" 2>/dev/null || true
                        fi
                        
                        # List generated reports
                        echo "Generated reports:"
                        ls -la "${WORKSPACE}/test-output/" 2>/dev/null || echo "No reports found"
                    '''
                }
            }
        }
    }
    
    post {
        always {
            script {
                echo "=== Cleanup ==="
                sh '''
                    # Kill Appium process
                    pkill -f "appium" || true
                    
                    # Archive Appium logs
                    if [ -f "${WORKSPACE}/appium.log" ]; then
                        mkdir -p "${WORKSPACE}/logs"
                        cp "${WORKSPACE}/appium.log" "${WORKSPACE}/logs/"
                    fi
                    
                    # Archive test execution logs
                    if [ -f "${WORKSPACE}/test-execution.log" ]; then
                        mkdir -p "${WORKSPACE}/logs"
                        cp "${WORKSPACE}/test-execution.log" "${WORKSPACE}/logs/"
                    fi
                '''
                
                // Archive test reports
                archiveArtifacts artifacts: 'test-output/**,logs/**,target/surefire-reports/**', 
                                 allowEmptyArchive: true
                
                // Publish TestNG results
                publishHTML target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'test-output/html',
                    reportFiles: 'extentReport.html',
                    reportName: 'Extent Report'
                ]
            }
        }
        
        success {
            script {
                echo "✅ Tests completed successfully!"
                // Send success email/notification here
            }
        }
        
        failure {
            script {
                echo "❌ Tests failed!"
                // Send failure email/notification here
                sh '''
                    echo "=== Test Failure Summary ==="
                    echo "Appium Log:"
                    tail -50 "${WORKSPACE}/appium.log" 2>/dev/null || echo "No Appium logs"
                    echo ""
                    echo "Test Execution Log:"
                    tail -100 "${WORKSPACE}/test-execution.log" 2>/dev/null || echo "No test logs"
                '''
            }
        }
        
        unstable {
            script {
                echo "⚠️ Build is unstable"
            }
        }
    }
}
