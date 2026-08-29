# Little Learners — Free GitHub APK Build

This project is prepared to build the Android APK entirely in GitHub Actions. No Android Studio, Android SDK, Gradle installation, or local build environment is required on your computer.

## Build it online

1. Create a free GitHub account at https://github.com/.
2. Create a new repository. For the simplest no-cost setup, make it **Public**.
3. Upload the **contents of this `LittleLearners` folder** to the repository (not the ZIP file itself).
4. Make sure `.github/workflows/build-apk.yml` is present.
5. Commit the files to the `main` branch.
6. Open the repository's **Actions** tab.
7. Select **Build Little Learners APK**.
8. The workflow will build the app automatically.
9. When it finishes successfully, open the workflow run and find the **Artifacts** section.
10. Download **LittleLearners-APK**. Inside it is `LittleLearners-debug.apk`.
11. Transfer the APK to an Android phone and install it.

The workflow uses Java 17, Gradle 8.9 and Android Gradle Plugin 8.7.3, matching the project configuration.

## Manual rebuild

The workflow runs automatically when code is pushed to `main` or `master`. You can also run it manually from **Actions → Build Little Learners APK → Run workflow**.

## Notes

- This is a debug APK intended for testing.
- A release-signed APK should be created later when the app is ready for public distribution.
- No passwords, API keys, or signing secrets are required for this debug build.
