@echo off

    if exist "lab6_purpura_nathan.obj" del "lab6_purpura_nathan.obj"
    if exist "lab6_purpura_nathan.exe" del "lab6_purpura_nathan.exe"

    \masm32\bin\ml /c /coff "lab6_purpura_nathan.asm"
    if errorlevel 1 goto errasm

    \masm32\bin\PoLink /SUBSYSTEM:CONSOLE "lab6_purpura_nathan.obj"
    if errorlevel 1 goto errlink
    dir "lab6_purpura_nathan.*"
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
