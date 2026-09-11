@echo off
setlocal

rem One-click launcher for the Insulin Pump simulation project.
cd /d "%~dp0"

title Insulin Pump Simulation - Spring Boot Logs

where java >nul 2>&1
if errorlevel 1 (
    echo Java was not found. Please install Java 8 and configure PATH.
    pause
    exit /b 1
)

where mvn >nul 2>&1
if errorlevel 1 (
    echo Maven was not found. Please install Maven and configure PATH.
    pause
    exit /b 1
)

echo.
echo Starting Insulin Pump Simulation on port 9100...
echo Project: %CD%
echo.
echo Keep this window open to view Spring Boot logs.
echo Press Ctrl+C to stop the application.
echo.

rem .env is read by the application when DeepSeek integration is enabled.
mvn spring-boot:run

echo.
echo Spring Boot has stopped. Check the log above for details.
pause
endlocal
