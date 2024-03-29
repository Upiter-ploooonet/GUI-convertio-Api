# Caster - Конвертер Файлов

Caster - это приложение для конвертации файлов, которое использует [API Convertio](https://convertio.co) для удобной конвертации различных типов файлов.

## Выбор Версии

1. **code**: Содержит полный исходный код проекта.
2. **jar**: Включает в себя JAR-файл, ресурсы приложения и скрипт для запуска JAR.
3. **exe**: Представляет собой полностью готовый файл EXE для запуска на Windows вместе с ресурсами приложения.
4. **exe(ПОРТАТИВНЫЙ)**:Полностью готове EXE, для запуска вам нужно будет скачать jdk(следуйте инструкции по пути "resourses/jdk").

## Начало Работы

1. Откройте настройки приложения.
2. В окне настроек нажмите "Get API key", чтобы перейти на веб-сайт [Convertio](https://developers.convertio.co/).
3. Пройдите регистрацию для получения API ключа.
4. Скопируйте полученный API ключ и вставьте его в поле "Enter your API key" или аналогичное.
5. Нажмите "OK", чтобы сохранить ваш API ключ.

## Использование

1. Добавьте или перетащите файл, который нужно конвертировать.
2. Выберите тип конвертации.
3. Нажмите "Start".
4. После конвертации выберите место сохранения полученного файла.

## Возможности и Настройки Caster

1. Поддержка конвертации более 300 форматов.
2. Возможность добавления нескольких файлов сразу.
3. Поддержка перетаскивания файлов.
4. Пользовательская цветовая палитра с возможностью сброса до дефолтных настроек.
5. Изменение языка приложения в настройках.
6. Добавление собственных языковых файлов для выбора в настройках.

## Остальная Информация

1. Caster использует JavaFX для UI и библиотеки [Moshi](https://github.com/Upiter-ploooonet/GUI-convertio-Api/issues) и [Okhttp](https://github.com/square/okhttp) для работы онлайн.
2. Лицензия: [Apache Version 2.0](https://www.apache.org/licenses/LICENSE-2.0) позволяет вам использовать данное приложение по своему усмотрению.
3. У Convertio доступно 25 минут конвертации в день. При превышении этого лимита будет выдаваться ошибка "No conversion minutes left" или аналогичная.
4. Файл конфигурации хранит ваш API ключ и настройки цветов. Если передаете приложение, не забудьте удалить ваш API ключ.
