#!/bin/bash

echo "Checking Java, Node, and project dependencies..."

# Check Homebrew
if ! command -v brew &> /dev/null
then
  echo "Homebrew not found."
  echo "Install Homebrew first from https://brew.sh"
  exit 1
fi

# Check Java
if ! command -v java &> /dev/null
then
  echo "Java not found. Installing Java 21..."
  brew install openjdk@21
else
  echo "Java found."
fi

# Set JAVA_HOME for current shell
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
export PATH="$JAVA_HOME/bin:$PATH"

# Save JAVA_HOME to shell profile
SHELL_PROFILE="$HOME/.zshrc"

if ! grep -q "JAVA_HOME" "$SHELL_PROFILE"; then
  echo "" >> "$SHELL_PROFILE"
  echo "# Java 21" >> "$SHELL_PROFILE"
  echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 21)' >> "$SHELL_PROFILE"
  echo 'export PATH="$JAVA_HOME/bin:$PATH"' >> "$SHELL_PROFILE"
  echo "Added JAVA_HOME to ~/.zshrc"
fi

# Check Node
if ! command -v node &> /dev/null
then
  echo "Node.js not found. Installing Node.js..."
  brew install node
else
  echo "Node.js found."
fi

# Install root dependencies
echo "Installing root dependencies..."
npm install

# Install frontend dependencies
echo "Installing frontend dependencies..."
cd frontend || exit
npm install
cd ..

# Make Maven wrapper executable
if [ -f "backend/mvnw" ]; then
  chmod +x backend/mvnw
fi

echo ""
echo "Setup complete."
echo "Run this now:"
echo "source ~/.zshrc"
echo "npm run dev"