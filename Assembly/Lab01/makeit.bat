@echo off

    if exist "my_first_add.asm.obj" del "my_first_add.asm.obj"
    if exist "my_first_add.asm.exe" del "my_first_add.asm.exe"

    \masm32\bin\ml /c /coff "my_first_add.asm"
    if errorlevel 1 goto errasm

    \masm32\bin\PoLink /SUBSYSTEM:CONSOLE "my_first_add.asm.obj"
    if errorlevel 1 goto errlink
    dir "my_first_add.*"
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
