<h1 align="center">
  Files Tree Searcher 🔍
</h1>

<p align="center">
  <a href="#-description">Description</a> •
  <a href="#-technologies">Technologies</a> •
  <a href="#-how-to-start-the-program">How to start the program</a>
</p>

## 📃 Description
This is a console application that searches for files at a given depth in the file system tree with a given filename mask. The program starts a telnet server and supports multi-user mode.

If several users connect to the server, the program will be able to search for each client in parallel. Also, only one thread exists in the program, which specifically searches for files for all users.

## 🧑‍💻 Technologies
| Technology | Version |
|:-----------|:--------|
| JDK        | 17      |
| Maven      | 4.0.0   |

## 📎 How to start the program
1. Clone the project from GitHub
2. Run in project folder in terminal: `mvn clean package`
3. Go to target folder: `cd target`
4. Start the program: `java -jar <file>.jar <rootPath> <port>`, where *file* - compiled jar file, *rootPath* - root path for searching, *port* - port for the telnet server
5. Now we can run our telnet client and connect it to `127.0.0.1` with the port
6. Enter a command in format: `<depth> <mask>`, where *depth* - file search depth, *mask* - mask for searching files by name

To start the program immediately, you can download [build](https://github.com/pavlogook/files-tree-searcher/releases/tag/build) and execute commands from the 4th point
