# Student Projects 🚀

Welcome to my student projects repository! This is a collection of programs and systems I've built during my first-year Computer Science journey. The projects are organized by language and domain, demonstrating my progress and learning.

## 📁 Folder Structure

```text
student_projects/
├── cpp_projects/      # C++ programs including console games
├── finals_system/     # Java POS final project system
└── java_projects/     # Introductory Java programs
```

## 🛠️ Projects Overview

### ☕ Java Projects (`java_projects/`)
- **SignIn System:** A simple Java sign-in simulation program exploring a user authentication loop, input handling, and basic control flow.

### 👾 C++ Projects (`cpp_projects/`)
- **Rock Paper Scissors:** A classic console-based game where the user plays against the computer.
- **Simple Calculator:** A basic command-line calculator for standard arithmetic operations.

### 🎓 Finals System (`finals_system/`)
- **POS System:** My comprehensive final project! It's a full Java-based Point of Sale system featuring GUI components (`POSMainFrame`), fuel products, order processing, and receipt generation.

## 💻 Technologies Used
- **Languages:** Java, C++
- **Tools/Compilers:** `javac` / `java` (for Java), `clang++` (for macOS C++ compilation)

## 🚀 How to Compile and Run (Mac)
  
### Java Projects
Navigate to the directory containing the `.java` files, then compile and run using the macOS terminal.

For single files (e.g., in `java_projects/`):
```bash
# Compile
javac signIn.java

# Run
java signIn
```

For the finals system (`finals_system/`), compile all files at once and run the main class:
```bash
# Compile all Java files
cd finals_system
javac *.java

# Run the main program
java Main
```

### C++ Projects
Navigate to the `cpp_projects/` folder and use `clang++` with the `-lc++` flag to compile:
```bash
cd cpp_projects

# Compile using clang++ with the C++ standard library flag
clang++ -lc++ rockpaperscissors.cpp -o rockpaperscissors

# Run the compiled executable
./rockpaperscissors
```

## 👨‍💻 Author
**Nell**
*First-year CS Student*
📝 GitHub: [@nelldot-hub](https://github.com/nelldot-hub)
