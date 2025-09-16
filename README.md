# WeiWeiBot

> A tiny Java task-tracking chatbot with and **JavaFX** UI.  
> Stores tasks to disk, supports todos, deadlines, and events, and prevents duplicates.

---

## ✨ Features

- Add / list / find / mark / unmark / delete tasks  
- Three task types: **Todo**, **Deadline**, **Event**  
- **Duplicate detection** on add (same type + matching identity)  
- **Persistent storage** in `data/tasks.txt`  
- **JavaFX GUI** (`weiweibot.gui.Main`)
- **JUnit 5** tests  

---

## 🚀 Quick Start

### Prerequisites
- **JDK 17+**
- **Gradle** (wrapper included: `gradlew` / `gradlew.bat`)

### Build
```bash
# Windows
.\gradlew clean build

# macOS / Linux
./gradlew clean build
