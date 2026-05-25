# Data Structures & Algorithms (Java)

Coursework from SER222 — Data Structures and Algorithms. Includes implementations of core data structures, algorithm analysis, and a standalone project.

## Assignments

| Folder | Topic |
|--------|-------|
| `homework/ser222hw2` | **Deque** — double-ended queue implemented from scratch with a node-based structure |
| `homework/ser222hw3` | **Linked List & Ordered List** — singly-linked list and a sorted ordered list variant |
| `homework/ser222hw5` | **Benchmarking** — stopwatch-based tool for empirical Big-O analysis |
| `homework/ser222hw6` | **Merging Algorithms** — merge sort and k-way merge implementations |

## Project: Snakes and Ladders

A console-based Snakes and Ladders game with human vs. computer gameplay.

```
projects/snakesAndLadders.java/src/
├── core/
│   ├── ComputerPlayer.java   # AI player logic
│   └── GameLogic.java        # Board, snakes, and ladders rules
└── ui/
    └── GameConsoleUI.java    # Console interface
```

## How to Run (any assignment)

```bash
cd homework/ser222hwX/src
javac *.java
java App
```

## Requirements

- Java 11+
