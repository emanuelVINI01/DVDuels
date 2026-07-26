# ⚔️ DVDuels

[![Java](https://img.shields.io/badge/Java-16-orange.svg)](https://www.oracle.com/java/)
[![Spigot](https://img.shields.io/badge/Spigot-1.19.4--R0.1-yellow.svg)](https://hub.spigotmc.org/)
[![Build](https://img.shields.io/badge/Maven-3.x-blue.svg)](https://maven.apache.org/)
[![Database](https://img.shields.io/badge/HikariCP-MySQL-green.svg)](https://github.com/brettwooldridge/HikariCP)
[![DevRoom](https://img.shields.io/badge/DevRoom-Trial%20Project%20(2022--2023)-purple.svg)]()

> 📜 **Historical Note:** This repository contains a legacy project developed between **2022 and 2023** as part of the practical admission challenge (*trial*) for **DevRoom**. The codebase reflects an early phase of my learning journey with Java and the Bukkit/Spigot API, preserved publicly for historical value and to document technical evolution over time.

---

## 📌 Project Overview

**DVDuels** is a customizable 1v1 duel plugin built for Minecraft servers (Spigot/Paper 1.19.4). It provides a complete arena PvP infrastructure featuring player visibility isolation, countdown timers, support for multiple YAML-configurable kits, and player stats persistence in a MySQL database powered by HikariCP connection pooling and Caffeine caching.

### 🌟 Key Features

* ⚔️ **1v1 Duel Request System**: Challenge any online player with a specific custom kit or the default kit.
* 👻 **Arena Player Isolation**: Dynamic invisibility for external players during matches (`Player#hidePlayer`), allowing multiple duels in parallel arenas without visual distraction.
* 🎒 **Flexible Kit Management**: Support for custom kits defined in `.yml` files (armor, inventory contents, and loadouts).
* ⏱️ **Countdown & Movement Freeze**: ActionBar countdown timer prior to match start, locking player movement until the match officially begins.
* 📊 **Stats Persistence (MySQL + HikariCP + Caffeine)**: Real-time database tracking for Kills, Deaths, Wins, Losses, and Win Streaks with in-memory caching.
* 🔄 **Automatic State Restoration**: Upon duel completion (via death or disconnect), players are automatically teleported back to their original locations.

---

## 💻 Commands & Usage

| Command | Aliases | Description | Usage |
| :--- | :--- | :--- | :--- |
| `/invite` | — | Sends a duel invite to a player. | `/invite <player> [kit]` |
| `/accept` | — | Accepts a pending duel invite from a player. | `/accept <player>` |
| `/stats` | `/dstats` | Displays PvP statistics (Kills, Deaths, Wins, Losses, Win Streak). | `/stats [player]` |

---

## 🛠️ Stack & Dependencies

* **Language**: Java 16
* **Target API**: Spigot API (`1.19.4-R0.1-SNAPSHOT`)
* **Build Tool**: Apache Maven
* **Database & Pooling**: MySQL + [HikariCP](https://github.com/brettwooldridge/HikariCP) (`5.0.1`)
* **Cache**: [Caffeine Cache](https://github.com/ben-manes/caffeine) (`2.9.0`)
* **Boilerplate Helper**: [Lombok](https://projectlombok.org/) (`1.18.26`)

---

## 📁 Configuration File Structure

The plugin auto-generates and manages the following files in its data folder:

```text
plugins/DVDuels/
├── configuration.yml   # General settings (default kit, countdown duration)
├── arena.yml           # Arena spawn points (Position 1 and Position 2)
├── database.yml        # MySQL credentials and connection pool settings
├── messages.yml        # Customizable in-game messages
└── kits/
    └── example.yml     # Custom kit configuration files
```

---

## 🏗️ Building & Installation

### Prerequisites
* **JDK 16** or higher
* **Apache Maven 3.x**
* Spigot or Paper server running **1.19.4**
* Active **MySQL** database instance

### Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/emanuelVINI/dv-duels.git
   cd dv-duels
   ```

2. **Build with Maven:**
   ```bash
   mvn clean package
   ```
   The compiled `.jar` artifact will be placed in `target/DVDuels-1.0-SNAPSHOT.jar`.

3. **Deploy to server:**
   - Copy the `.jar` file to your server's `plugins/` directory.
   - Start the server to generate default configuration files.
   - Edit `plugins/DVDuels/database.yml` with your MySQL connection details.
   - Restart or reload the server.

---

## 👤 Author

* **Emanuel** ([@emanuelVINI](https://github.com/emanuelVINI))
* Developed as an entry trial project for **DevRoom** (2022–2023).


