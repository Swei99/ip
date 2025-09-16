# WeiWeiBot

> A tiny Java task-tracking chatbot with and **JavaFX** UI.  
> Stores tasks to disk, supports todos, deadlines, and events, and prevents duplicates.

---

## Features ✨

- Add / list / find / mark / unmark / delete tasks  
- Three task types: **Todo**, **Deadline**, **Event**  
- **Duplicate detection** on add (same type + matching identity)  
- **Persistent storage** in `data/tasks.txt`  
- **JavaFX GUI** (`weiweibot.gui.Main`)
---

## 🚀 Quick Start

### Prerequisites
- **JDK 17+**
- **Gradle** (wrapper included: `gradlew` / `gradlew.bat`)

### How to start
- Ensure all pre requisites are settled
- Download latest `jar` 
- Place the 'jar' file at the home folder and run command `java -jar WeiWeiBot.jar`

### Build
```bash
# Windows
.\gradlew clean build

# macOS / Linux
./gradlew clean build
```

### Usage
Commands:
```bash
todo <description>
deadline <description> /by <d-M-yyyy [HHmm] | yyyy-MM-dd>
event <description> /from <d-M-yyyy HHmm> /to <d-M-yyyy HHmm>

list
find <keywords>
mark <n>
unmark <n>
delete <n>
help
bye
```

### Examples
```bash
todo Buy milk
deadline Submit report /by 31-12-2025 1800
event Team meeting /from 1-1-2026 0900 /to 1-1-2026 1030
find report
mark 1
delete 2
```
