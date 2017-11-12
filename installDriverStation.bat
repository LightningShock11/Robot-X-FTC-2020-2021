SET adb=%LOCALAPPDATA%/Android/Sdk/platform-tools/adb.exe

"%adb%" usb

"%adb%" install doc/apk/FtcDriverStation-release.apk

PAUSE