# Tic-Tac-Toe
A Java Swing based Tic Tac Toe desktop game with GUI and login based gameplay.
# 🎮 Tic-Tac-Toe (Java Swing + SQL Database)

A desktop-based **Tic Tac Toe game** developed using **Java Swing** with **MySQL database integration**.  
The project includes a graphical user interface, user login system, and persistent score tracking.

---

## 📌 About the Game

This is a classic **Tic Tac Toe (X and O)** game played on a 3×3 grid.

- Users log in using **User ID and Password**
- Gameplay is done using mouse clicks
- After each match, the result (Win / Lose / Draw) is detected
- Player statistics are stored permanently in a SQL database

This project demonstrates Java GUI programming and database connectivity using JDBC.

---

## ✨ Features

- Java Swing based GUI
- User login system
- One-player Tic Tac Toe gameplay
- Mouse-based interaction
- Win, Lose, and Draw detection
- Persistent score storage
- Automatic score calculation using SQL triggers

---

## 🛠 Technologies Used

- Java (JDK 8 or above)
- Swing & AWT
- MySQL Database
- JDBC (Java Database Connectivity)

---

## 🗄️ SQL Database Setup

### 📥 Install MySQL
1. Download MySQL Community Server from:
   https://dev.mysql.com/downloads/
2. Install MySQL and note your username and password
3. Ensure MySQL server is running
4. Then copy paste below SQL code.
---
```sql
CREATE DATABASE tic_tac_toe;
USE tic_tac_toe;
CREATE TABLE user (
    user_id  VARCHAR(100) PRIMARY KEY,
    win      INT DEFAULT 0,
    lose     INT DEFAULT 0,
    draw     INT DEFAULT 0,
    password VARCHAR(50),
    score    INT DEFAULT 0
);
DELIMITER //
CREATE TRIGGER score_cal
BEFORE INSERT ON user
FOR EACH ROW
BEGIN
    SET NEW.score = NEW.win - NEW.lose;
END;
//
DELIMITER ;
DELIMITER //
CREATE TRIGGER score_cal_up
BEFORE UPDATE ON user
FOR EACH ROW
BEGIN
    SET NEW.score = NEW.win - NEW.lose;
END;
//
DELIMITER ;
```
##Database Connection Configuration (IMPORTANT)

The database connection details are written in MainFrame.java.

✏️ What You Must Change in MainFrame.java

Open MainFrame.java and update the following values according to your MySQL setup:
```java
con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tic_tac_toe","/*User ID*/","/*password*/");
```

⚠️ If these values are incorrect:
  Login will not work
  Database will not connect
  Make sure:
  Database name is tic_tac_toe
  MySQL server is running
  User ID and password match your MySQL credentials



## How to Run the Project

1. Install **Java JDK (8 or above)** on your system.
2. Install **MySQL** and make sure the MySQL server is running.
3. Create the database and tables using the SQL script provided in this project.
4. Open `MainFrame.java` and update the database username and password according to your MySQL setup.
5. Compile the project:
 ```bash
   javac src/Main.java
```
6. Run the application:
 ```bash
  java src.Main
```
7. The Tic-Tac-Toe game window will open and you can log in and start playing.

## Controls
1. Mouse Click → Place X or O
2. Login Button → Authenticate user
3. Restart / Play → Start a new game

## Academic Purpose
1. This project was developed for academic learning to understand:
2. Java Swing GUI programming
3. JDBC and SQL database connectivity
4. Database triggers
5. Event-driven applications
6. Game logic implementation

## Author
Pranavraj Chintakindi

## License
This project is licensed under the MIT License.
