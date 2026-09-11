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

rem Avoid starting a second application when port 9100 is already active.
rem netstat is available on standard Windows installations.
netstat -ano | findstr /R /C:":9100 .*LISTENING" >nul
if not errorlevel 1 (
    echo Port 9100 is already in use. The application may already be running.
    start "" "http://localhost:9100"
    pause
    exit /b 0
)

echo.
echo Starting Insulin Pump Simulation on port 9100...
echo Project: %CD%
echo.
echo Keep this window open to view Spring Boot logs.
echo Press Ctrl+C to stop the application.
echo.

rem Open the homepage automatically after Spring Boot starts listening.
rem The watcher runs separately so Maven logs remain visible in this window.
start "" powershell.exe -NoProfile -WindowStyle Hidden -ExecutionPolicy Bypass -Command "$deadline=(Get-Date).AddSeconds(90); do { Start-Sleep -Seconds 1; $listening=netstat -ano | Select-String ':9100 .*LISTENING' } while(-not $listening -and (Get-Date) -lt $deadline); if($listening){ Start-Process 'http://localhost:9100' }"

rem .env is read by the application when DeepSeek integration is enabled.
rem Use the Windows Maven command explicitly so double-click launch works reliably.
call mvn.cmd spring-boot:run

echo.
echo Spring Boot has stopped. Check the log above for details.
pause
endlocal
