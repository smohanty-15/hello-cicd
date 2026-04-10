pipeline {
    agent any

    // Variables used across all stages
    environment {
        APP_NAME    = "hello-cicd"
        VERSION     = "1.${BUILD_NUMBER}"   // e.g. 1.3, 1.4, 1.5 — auto increments
        DEV_PORT    = "8081"
        UAT_PORT    = "8082"
        STAGING_PORT= "8083"
        PROD_PORT   = "8080"
    }

    tools {
        maven 'Maven3'
        jdk   'JDK17'
    }

    stages {

        // ─────────────────────────────────────────
        // STAGE 1 — Pull the latest code
        // ─────────────────────────────────────────
        stage('Checkout Code') {
            steps {
                echo "======================================"
                echo " Checking out version: ${VERSION}"
                echo "======================================"
                git branch: 'main',
                    url: 'https://github.com/YOUR_USERNAME/hello-cicd.git'
            }
        }

        // ─────────────────────────────────────────
        // STAGE 2 — Compile the code
        // ─────────────────────────────────────────
        stage('Build') {
            steps {
                echo "======================================"
                echo " Building ${APP_NAME} v${VERSION}"
                echo "======================================"
                bat 'mvn clean package -DskipTests'
                echo "Build SUCCESS — .jar file created"
            }
        }

        // ─────────────────────────────────────────
        // STAGE 3 — Run unit tests
        // ─────────────────────────────────────────
        stage('Unit Tests') {
            steps {
                echo "======================================"
                echo " Running Unit Tests"
                echo "======================================"
                bat 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
                success {
                    echo "All unit tests PASSED!"
                }
                failure {
                    echo "Unit tests FAILED — pipeline stopped here"
                    echo "Fix the failing tests before deploying anywhere"
                }
            }
        }

        // ─────────────────────────────────────────
        // STAGE 4 — Code quality check
        // ─────────────────────────────────────────
        stage('Code Quality Check') {
            steps {
                echo "======================================"
                echo " Checking Code Quality"
                echo "======================================"
                // Checks for code style issues
                // "|| true" means: warn but don't fail the pipeline
                bat 'mvn checkstyle:check || true'
                echo "Code quality check complete"
            }
        }

        // ─────────────────────────────────────────
        // STAGE 5 — Save the build output
        // ─────────────────────────────────────────
        stage('Archive Artifact') {
            steps {
                echo "======================================"
                echo " Saving build artifact v${VERSION}"
                echo "======================================"
                archiveArtifacts artifacts: 'target/*.jar',
                                 fingerprint: true
                echo "Artifact saved — downloadable from Jenkins"
            }
        }

        // ─────────────────────────────────────────
        // STAGE 6 — Deploy to DEV
        // Automatic. Runs on every single push.
        // Developers test here first.
        // ─────────────────────────────────────────
        stage('Deploy to DEV') {
            steps {
                echo "======================================"
                echo " DEPLOYING to DEV environment"
                echo " App: ${APP_NAME}  Version: ${VERSION}"
                echo " URL: http://dev.mycompany.com:${DEV_PORT}"
                echo "======================================"
                echo "[DUMMY] Stopping old DEV app..."
                echo "[DUMMY] Starting new DEV app v${VERSION}..."
                echo "[DUMMY] Running health check on DEV..."
                echo "DEV deployment SUCCESSFUL!"
                echo "Developers can now test at DEV environment"
            }
        }

        // ─────────────────────────────────────────
        // STAGE 7 — Deploy to UAT
        // Automatic after DEV passes.
        // QA testers and clients check features here.
        // ─────────────────────────────────────────
        stage('Deploy to UAT') {
            steps {
                echo "======================================"
                echo " DEPLOYING to UAT environment"
                echo " App: ${APP_NAME}  Version: ${VERSION}"
                echo " URL: http://uat.mycompany.com:${UAT_PORT}"
                echo "======================================"
                echo "[DUMMY] Stopping old UAT app..."
                echo "[DUMMY] Starting new UAT app v${VERSION}..."
                echo "[DUMMY] Notifying QA team to begin testing..."
                echo "UAT deployment SUCCESSFUL!"
                echo "QA team can now run acceptance tests"
            }
        }

        // ─────────────────────────────────────────
        // STAGE 8 — Deploy to STAGING
        // Automatic after UAT.
        // Exact copy of production — final check.
        // ─────────────────────────────────────────
        stage('Deploy to Staging') {
            steps {
                echo "======================================"
                echo " DEPLOYING to STAGING environment"
                echo " App: ${APP_NAME}  Version: ${VERSION}"
                echo " URL: http://staging.mycompany.com:${STAGING_PORT}"
                echo "======================================"
                echo "[DUMMY] Stopping old STAGING app..."
                echo "[DUMMY] Starting new STAGING app v${VERSION}..."
                echo "[DUMMY] Running smoke tests on STAGING..."
                echo "[DUMMY] Notifying management for review..."
                echo "STAGING deployment SUCCESSFUL!"
                echo "Management can review before production"
            }
        }

        // ─────────────────────────────────────────
        // STAGE 9 — Deploy to PRODUCTION
        // NOT automatic. A human must approve.
        // This is what protects real customers.
        // ─────────────────────────────────────────
        stage('Deploy to PRODUCTION') {
            steps {
                echo "======================================"
                echo " PRODUCTION DEPLOYMENT"
                echo " App: ${APP_NAME}  Version: ${VERSION}"
                echo "======================================"

                // THIS IS THE KEY LINE — Jenkins STOPS here
                // and waits for a human to click Proceed or Abort
                input message: "Deploy version ${VERSION} to PRODUCTION?",
                      ok: "YES — Deploy to Production",
                      submitter: "admin"

                echo "[DUMMY] Taking production backup first..."
                echo "[DUMMY] Stopping old PRODUCTION app..."
                echo "[DUMMY] Starting new PRODUCTION app v${VERSION}..."
                echo "[DUMMY] Running production health check..."
                echo "[DUMMY] Sending deployment notification to team..."
                echo ""
                echo "PRODUCTION deployment SUCCESSFUL!"
                echo "Version ${VERSION} is now LIVE!"
                echo "URL: http://www.mycompany.com:${PROD_PORT}"
            }
        }

    }

    // ─────────────────────────────────────────
    // POST — Runs after entire pipeline finishes
    // ─────────────────────────────────────────
    post {
        success {
            echo ""
            echo "########################################"
            echo "  FULL PIPELINE PASSED!"
            echo "  ${APP_NAME} v${VERSION} is deployed"
            echo "########################################"
        }
        failure {
            echo ""
            echo "########################################"
            echo "  PIPELINE FAILED!"
            echo "  Check the RED stage above for errors"
            echo "  Nothing was deployed to Production"
            echo "########################################"
        }
        always {
            echo "Pipeline finished for ${APP_NAME} v${VERSION}"
        }
    }
}