Write-Host "Checking Java, Node, and project dependencies..."

# Check winget
if (-not (Get-Command winget -ErrorAction SilentlyContinue)) {
    Write-Host "winget not found. Install App Installer from Microsoft Store first."
    exit 1
}

# Check Java
if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    Write-Host "Java not found. Installing Java 21..."
    winget install EclipseAdoptium.Temurin.21.JDK -e
} else {
    Write-Host "Java found."
}

# Try to find JAVA_HOME
$javaPath = (Get-Command java -ErrorAction SilentlyContinue).Source

if ($javaPath) {
    $javaBin = Split-Path $javaPath
    $javaHome = Split-Path $javaBin

    Write-Host "Detected JAVA_HOME: $javaHome"

    [Environment]::SetEnvironmentVariable("JAVA_HOME", $javaHome, "User")

    $userPath = [Environment]::GetEnvironmentVariable("Path", "User")

    if ($userPath -notlike "*%JAVA_HOME%\bin*") {
        [Environment]::SetEnvironmentVariable("Path", "$userPath;%JAVA_HOME%\bin", "User")
        Write-Host "Added JAVA_HOME to user PATH."
    }
}

# Check Node
if (-not (Get-Command node -ErrorAction SilentlyContinue)) {
    Write-Host "Node.js not found. Installing Node.js LTS..."
    winget install OpenJS.NodeJS.LTS -e
} else {
    Write-Host "Node.js found."
}

# Install root dependencies
Write-Host "Installing root dependencies..."
npm install

# Install frontend dependencies
Write-Host "Installing frontend dependencies..."
cd frontend
npm install
cd ..

Write-Host ""
Write-Host "Setup complete."
Write-Host "Close and reopen PowerShell, then run:"
Write-Host "npm run dev"