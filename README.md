# resume-manager
Server side.

At first, change Java version to 17:\
https://www.happycoders.eu/java/how-to-switch-multiple-java-versions-windows/

To run project with "Run" button:
1. You'll see "maven build script found", press "load".
2. If there's no such message, delete ".idea" folder and reopen Intelij.
3. Open "ResumeManagerApplication" file and press "trust project".
4. Wait for the dependencies to load.

You also need to set enviroment variables before start.
They are:
1. MYSQL_DB_HOST
2. MYSQL_DB_PORT
3. MYSQL_DB_NAME
4. MYSQL_DB_USERNAME
5. MYSQL_DB_PASSWORD
6. MY_SECRET_KEY

Steps:
1. "Edit Configurations" on the right of build button.
2. Press on "Enviroment variables" field.
3. Shift+Enter
4. Add variables names and values
5. Press "OK" -> "Apply" -> "OK"

That's all.