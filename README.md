# timestamps
The app that renames media files based on their creation data. You can run the app using Java installed on your local machine or use any pre-built package for Mac.

## Using package

### Run the app from sourcecode using Java

> **Note:** Be sure Java is installed on your machine by running it in your terminal.
> 
>> Java:
>> ```bash
>> java --version
>> ```
>> The output should be like:
>> ```
>> openjdk version "17.0.15" 2025-04-15
>> OpenJDK Runtime Environment Homebrew (build 17.0.15+0)
>> OpenJDK 64-Bit Server VM Homebrew (build 17.0.15+0, mixed mode, sharing)
>> ```

#### Build the package
```bash
./gradlew assemble
```
#### Go to `libs` folder
```bash
cd build/libs
```
#### Run the app to rename files
Run command with specifying the path to the directory with files as an argument:
```bash
java -jar timestamps-1.0-SNAPSHOT.jar <directory_path> --spring.profiles.active=cli
```
Example to rename files from `/Users/someuser/direcotryname` directory:
```bash
java -jar timestamps-1.0-SNAPSHOT.jar /Users/someuser/directoryname
```

### Run the app on macOS from the installed package
Run command with specifying the path to the directory with files as an argument:
```bash
timestamps <directory_path>
```
Example to rename files from `/Users/someuser/direcotryname` directory:
```bash
timestamps /Users/someuser/directoryname
```

## Removal

### macOS package
Delete manually the app `Timestamps` from the `Applications` folder and delete the symlink by running the command:
```bash
sudo rm /usr/local/bin/timestamps
```

## Using REST request

Start HTTP server:
```bash
./gradlew bootRun
```

Send the request with the file to rename:
```bash
curl --location 'localhost:8080/rename' \
--header 'Authorization: Bearer <token>' \
--form 'file=@"<path_to_file>"
```
- where `<token>` is the token you can get from the `application.security.token` param in `src/main/resources/application.properties` properties file.
- where `<path_to_file>` is the path to the file you want to rename. Example: `/Users/username/Downloads/photo.HEIC`