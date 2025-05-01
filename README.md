# timestamps
The app that renames media files based on their creation data. You can run the app using Java installed on your local machine or use any pre-built package for Mac.

## Installation
Skip this if you like to run app from the source code.

### macOS package
#### Install package to your system
Download [PKG file](https://github.com/shedenys/timestamps/raw/refs/heads/artifacts/Timestamps-1.0.0-beta.1.pkg), open and follow the instructions.
#### Create symlink
```bash
sudo ln -s "/Applications/Timestamps.app/Contents/MacOS/timestamps" /usr/local/bin/timestamps
```

## Usage

### Run the app from sourcecode using Java and Maven

> **Note:** Be sure Java and Maven are installed on your machine by running in your terminal.
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
>
>> Maven:
>> ```bash
>> mvn --version
>> ```
>> The output should be like:
>> ```
>> Maven home: /usr/local/Cellar/maven/3.9.9/libexec
>> Java version: 23.0.2, vendor: Homebrew, runtime: /usr/local/Cellar/openjdk/23.0.2/libexec/openjdk.jdk/Contents/Home
>> Default locale: en_UA, platform encoding: UTF-8
>> OS name: "mac os x", version: "15.4.1", arch: "x86_64", family: "mac"
>>```

#### Build the package
```bash
mvn package
```
#### Go to `target` folder
```bash
cd target
```
#### Run the app to rename files
Run command with specifying the path to the directory with files as an argument:
```bash
java -jar timestamps-1.0-SNAPSHOT.jar <directory_path>
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