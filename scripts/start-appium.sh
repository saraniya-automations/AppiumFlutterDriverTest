#!/bin/bash

##
# Utility script to start Appium server for Jenkins
# Usage: ./scripts/start-appium.sh
##

set -e

APPIUM_LOG="${WORKSPACE}/appium.log"
APPIUM_PID_FILE="${WORKSPACE}/.appium.pid"

echo "Starting Appium Server..."

# Kill any existing Appium processes
echo "Cleaning up existing Appium processes..."
pkill -f "appium" || true
sleep 2

# Remove old PID file if it exists
rm -f "$APPIUM_PID_FILE"

# Start Appium in background
echo "Starting Appium on port 4723..."
appium --port 4723 --address 127.0.0.1 > "$APPIUM_LOG" 2>&1 &
APPIUM_PID=$!

# Save PID
echo $APPIUM_PID > "$APPIUM_PID_FILE"

# Wait for Appium to be ready
echo "Waiting for Appium to initialize..."
sleep 5

# Verify Appium is running
if ! kill -0 $APPIUM_PID 2>/dev/null; then
    echo "ERROR: Failed to start Appium"
    echo "Appium Log:"
    cat "$APPIUM_LOG"
    exit 1
fi

echo "✅ Appium started successfully (PID: $APPIUM_PID)"

# Give it more time to fully initialize
sleep 3

# Verify Appium is responding
if curl -s http://127.0.0.1:4723/wd/hub/sessions > /dev/null; then
    echo "✅ Appium is responding to requests"
else
    echo "⚠️ Appium started but not responding yet (might still be initializing)"
fi

exit 0
