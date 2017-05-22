@echo off

    if exist "purpuran_nathan_lab7.obj" del "purpuran_nathan_lab8.obj"
    if exist "purpuran_nathan_lab7.exe" del "purpuran_nathan_lab8.exe"

    \masm32\bin\ml /c /coff "purpuran_nathan_lab8.asm"
    if errorlevel 1 goto errasm

    \masm32\bin\PoLink /SUBSYSTEM:CONSOLE "purpuran_nathan_lab8.obj"
    if errorlevel 1 goto errlink
    dir "purpuran_nathan_lab8.*"
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
