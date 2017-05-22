@echo off

    if exist "Lab02.obj" del "Lab02.obj"
    if exist "Lab02.exe" del "Lab02.exe"

    \masm32\bin\ml /c /coff "Lab02.asm"
    if errorlevel 1 goto errasm

    \masm32\bin\PoLink /SUBSYSTEM:CONSOLE "Lab02.obj"
    if errorlevel 1 goto errlink
    dir "Lab02.*"
    goto TheEnd

  :errlink
    echo _
    echo Link error
    goto TheEnd

  :errasm
    echo _
    echo Assembly Error
    goto TheEnd
    
  :TheEnd

pause
