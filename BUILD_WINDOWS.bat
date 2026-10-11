@echo off
cd /d "%~dp0"
echo SkullBridge Forge 1.20.1 build
java -version
where gradle >nul 2>&1
if exist gradlew.bat (
  call gradlew.bat build
) else (
  if errorlevel 1 (
    echo.
    echo Gradle wrapper not found and gradle not installed.
    echo Please use an official Forge 1.20.1 MDK gradlew.bat and gradle/ wrapper files.
    echo Copy those official files into this folder and try again.
    pause
    exit /b 1
  )
  call gradle build
)
if errorlevel 1 (
  echo Build failed. Take a screenshot of the error.
  pause
  exit /b 1
)
echo.
echo Look in build\libs for the SkullBridge jar.
pause
