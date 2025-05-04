#!/usr/bin/env sh

set -e

# Find the directory of this script
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Run Gradle with the wrapper
exec "$SCRIPT_DIR/gradlew" "$@"
