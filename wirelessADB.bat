SET adb=%LOCALAPPDATA%/Android/Sdk/platform-tools/adb.exe

"%adb%" tcpip 5555

PAUSE

"%adb%" connect 192.168.49.51:5555

PAUSE
