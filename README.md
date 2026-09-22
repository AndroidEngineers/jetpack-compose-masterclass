# Jetpack Compose Masterclass

Learn Compose by exploring **Masterly**, a skill-practice tracker with a dashboard, skill details, a session timer, and local persistence. This repository contains course code and teaching notes from Android Engineers.

## Start here

1. Know basic Kotlin: functions, classes, nullability, and collections.
2. Open the `Masterly` directory in Android Studio, not the repository root.
3. Follow the setup below, then trace one feature using the code tour.

[Follow a guided learning path at Android Engineers Academy](https://www.androidengineers.in/roadmap?utm_source=github&utm_medium=repository&utm_campaign=jetpack-compose-masterclass)

## Setup

The checked-in project declares these versions; this documentation update has not revalidated an Android build or device run.

| Component | Checked-in configuration |
| --- | --- |
| Android Gradle Plugin | 8.13.1 |
| Gradle wrapper | 8.13 |
| Kotlin | 2.2.21 |
| Compose BOM | 2025.11.01 |
| Android SDK | Compile/target 36; minimum 24 |
| Room / Hilt | 2.8.4 / 2.57.2 |

Use an Android Studio version compatible with AGP 8.13, Android SDK 36, and **JDK 17 for Gradle**. The app's Java/Kotlin bytecode target is 11; that is separate from the JDK used to run Gradle. See the [AGP compatibility requirements](https://developer.android.com/build/releases/agp-8-13-0-release-notes).

```sh
git clone https://github.com/AndroidEngineers/jetpack-compose-masterclass.git
cd jetpack-compose-masterclass/Masterly
./gradlew --version
./gradlew :app:assembleDebug :app:testDebugUnitTest
```

On Windows use `gradlew.bat`. If your checkout does not preserve executable permissions, use `bash gradlew`. In Android Studio, allow Gradle sync to finish, select the `app` configuration, and run on an API 24+ emulator or device. Instrumented tests require a connected device: `./gradlew :app:connectedDebugAndroidTest`.

The version catalog is in [libs.versions.toml](Masterly/gradle/libs.versions.toml); app settings are in [app/build.gradle.kts](Masterly/app/build.gradle.kts). Report sync/build failures with the command, JDK, SDK, and first meaningful error.

## Guided code tour

| Learn | Read | Try |
| --- | --- | --- |
| State-driven UI | [HomeScreen](Masterly/app/src/main/java/com/androidengineers/masterly/ui/screens/home/HomeScreen.kt) | Explain the loading, empty, error, and content branches. |
| ViewModel state | [HomeScreenViewModel](Masterly/app/src/main/java/com/androidengineers/masterly/ui/screens/home/HomeScreenViewModel.kt) | Trace how a database change reaches the screen. |
| Local persistence | [Skill](Masterly/app/src/main/java/com/androidengineers/masterly/data/local/Skill.kt) and [SkillDao](Masterly/app/src/main/java/com/androidengineers/masterly/data/local/SkillDao.kt) | Follow a skill from its entity to a displayed card. |
| Repository boundary | [SkillsRepository](Masterly/app/src/main/java/com/androidengineers/masterly/domain/repository/SkillsRepository.kt) | Explain which layer depends on this interface. |
| Navigation | [AppNavHost](Masterly/app/src/main/java/com/androidengineers/masterly/ui/navigation/AppNavHost.kt) | Trace a skill identifier from dashboard to detail. |
| Timed sessions | [TimerViewModel](Masterly/app/src/main/java/com/androidengineers/masterly/ui/screens/timer/TimerViewModel.kt) | Trace start, pause, reset, and saving accumulated practice. |

## First exercise: explain and improve the retry action

Open `HomeScreen.kt`. The error branch renders a Retry button whose callback is currently empty. Trace the ViewModel and repository before proposing what retry should do. Write down how you would reproduce a failed load, trigger a retry, and verify recovery without duplicating collectors. Include your expected UI states in a pull request.

For a second exercise, inspect the timer and document what happens when navigating away, rotating the device, or restarting the process. Distinguish the current implementation from the behavior you want to add.

## Teaching notes and visuals

- [Day 1 topic outline](Day-1-plan.txt) — an outline for discussing composition, state, and recomposition; it is not a standalone complete lesson.
- [Existing screenshot gallery](screenshot/) — historical course images; these may differ from the current app.

## Contribute

Keep changes focused on one lesson, bug, or feature. Include reproduction steps, a short explanation of the engineering decision, and the commands or device checks you actually ran. For UI changes, attach a screenshot and describe empty/loading/error behavior.

Licensed under [Apache 2.0](LICENSE).
