#!/bin/bash

##
# Utility script to stop Appium server
# Usage: ./scripts/stop-appium.sh
##

APPIUM_PID_FILE="${WORKSPACE}/.appium.pid"

echo "Stopping Appium Server..."

# If PID file exists, use it
if [ -f "$APPIUM_PID_FILE" ]; then
    APPIUM_PID=$(cat "$APPIUM_PID_FILE")
    if kill -0 $APPIUM_PID 2>/dev/null; then
        echo "Killing Appium process (PID: $APPIUM_PID)"
        kill $APPIUM_PID
        sleep 2
    fi
    rm -f "$APPIUM_PID_FILE"
fi

# Kill any remaining Appium processes
if pkill -f "appium"; then
    echo "Killed remaining Appium processes"
    sleep 2
else
    echo "No Appium processes found"
fi

echo "✅ Appium stopped"
