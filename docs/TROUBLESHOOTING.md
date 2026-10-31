# Troubleshooting Guide

Common development environment problems and how to fix them.

> **Note**: This guide covers development environment setup issues. Spring Boot and Hibernate troubleshooting will be added when those technologies are implemented.

---

### Java Problems

**Java not found / java command not recognized**

Check:
1. Is JDK installed? Download from [Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/)
2. Check JDK version: `java -version` (we need JDK 17 or higher)
3. Check JAVA_HOME environment variable: `echo %JAVA_HOME%` (Windows) or `echo $JAVA_HOME` (Mac/Linux)
4. Check PATH includes Java: The `bin` directory of your JDK should be in your system PATH

**Wrong Java version**
- Spring Boot 3.x requires JDK 17 or higher
- Check with: `java -version`
- If you have multiple Java versions, make sure JAVA_HOME points to the correct one

---

### Maven Problems

**Maven not found / mvn command not recognized**

Check:
1. Is Maven installed? Download from [Maven website](https://maven.apache.org/download.cgi)
2. Check version: `mvn -version`
3. Check that Maven's `bin` directory is in your system PATH
4. Alternatively, use the Maven Wrapper (`mvnw`) included in Spring Boot projects

**Dependencies not downloading**
- Check internet connection
- Try: `mvn clean install -U` (the `-U` forces update)
- Delete the `.m2/repository` folder and try again (it will re-download everything)
- Check `pom.xml` for typos in dependency declarations

**Build failing**
- Read the error message carefully
- Check `pom.xml` for syntax errors
- Make sure Java version in `pom.xml` matches your installed JDK

---

### Git Problems

**Git not tracking files**
- Check: `git status`
- Check `.gitignore` — is the file being ignored?
- Add files: `git add .`

**Accidentally committed sensitive files**
- Remove from tracking: `git rm --cached <filename>`
- Add to `.gitignore`
- Commit the change
- Note: The file will still be in Git history. For truly sensitive data, you may need to rewrite history

**Merge conflicts**
- Open the conflicting file
- Look for `<<<<<<<`, `=======`, `>>>>>>>` markers
- Choose which version to keep
- Remove the conflict markers
- Save, add, and commit

**Common Git commands**
```bash
git status          # See what's changed
git add .           # Stage all changes
git commit -m "msg" # Commit with message
git log --oneline   # View commit history
git diff            # See unstaged changes
git push            # Push to remote
git pull            # Pull from remote
```

---

### IDE Problems

**IntelliJ not recognizing project**
- File → Open → Select the `pom.xml` file → Open as Project
- Right-click `pom.xml` → Maven → Reimport

**VS Code not recognizing Java**
- Install the "Extension Pack for Java" extension
- Install the "Spring Boot Extension Pack" extension
- Reload the window after installing

---

### File Not Found / Path Problems (Windows)

- Use forward slashes `/` or double backslashes `\\` in configuration files
- Check that file names match exactly (including capitalization on Linux/Mac)
- Verify the working directory when running commands

---

### What's NOT Covered Yet

The following will be added to this guide in later stages:
- Spring Boot startup problems (Stage 3)
- Database connection issues (Stage 4)
- Hibernate/JPA errors (Stage 4)
- API endpoint issues (Stage 5)
- Authentication/Security issues (Stage 7)
