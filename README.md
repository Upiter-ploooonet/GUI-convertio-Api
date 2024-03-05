# Caster - File Converter

Caster is an application for file conversion that utilizes the [Convertio API](https://convertio.co) for convenient conversion of various file types.

## Version Selection

1. **"code"**: Contains the full source code of the project.
2. **"jar"**: Includes the JAR file, application resources, and a script for launching the JAR.
3. **"exe"**: Represents a fully prepared EXE file for Windows with application resources.
4.**"exe(PORTABLE)"**: Fully prepared EXE, to run it, you will need to download JDK (follow the instructions in the "resources/jdk" path).

## Getting Started

1. Open the application settings.
2. In the settings window, click "Get API key" to be redirected to the [Convertio website](https://developers.convertio.co/).
3. Complete the registration process to obtain your API key.
4. Copy the received API key and paste it into the "Enter your API key" field or a similar one.
5. Click "OK" to save your API key.

## Usage

1. Add or drag-and-drop the file you want to convert.
2. Specify the target format for conversion.
3. Click "Start".
4. After conversion, select the location to save the resulting file.

## Features and Settings of Caster

1. Support for the conversion of over 300 formats.
2. Ability to add multiple files at once.
3. Support for file drag-and-drop.
4. Custom color palette
5. Change the application language in the settings.
6. Add your own language files for selection in the settings.

## Additional Information

1. Caster uses JavaFX for the UI and libraries [Moshi](https://github.com/Upiter-ploooonet/GUI-convertio-Api/issues) and [Okhttp](https://github.com/square/okhttp) for online functionality.
2. License: [Apache Version 2.0](https://www.apache.org/licenses/LICENSE-2.0) allows you to use this application at your discretion.
3. Convertio provides 25 minutes of conversion per day. Exceeding this limit will result in an error like "No conversion minutes left" or a similar message.
4. The configuration file stores your API key and color settings. If passing on the application, remember to remove your API key.
