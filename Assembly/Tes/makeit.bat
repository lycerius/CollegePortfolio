@echo off

    if exist "Test.obj" del "Test.obj"
    if exist "Test.exe" del "Test.exe"

    \masm32\bin\ml /c /coff "Test.asm"
    if errorlevel 1 goto errasm

    \masm32\bin\PoLink /SUBSYSTEM:CONSOLE "Test.obj"
    if errorlevel 1 goto errlink
    dir "Test.*"
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
