; �������������������������������������������������������������������������

    INCLUDE \masm32\include\masm32rt.inc
    INCLUDE \masm32\include\Irvine32.inc
    INCLUDELIB \masm32\lib\Irvine32.lib
; �������������������������������������������������������������������������

comment * -----------------------------------------------------
                     Build this console app with
                  "MAKEIT.BAT" on the PROJECT menu.
        ----------------------------------------------------- *

    .data
    barray BYTE 0, 0, 11110000b, 11001000b, 10001101b, 00000000b
    dwarray WORD 3 DUP (?), 3039h, 4 DUP (?)
    TOTAL_LENGTH = $-barray
    bdmessage BYTE "putting bytes into double word", 0dh, 0ah, 0
    dwmessage BYTE 0dh, 0ah, "cutting double words int single word", 0dh, 0ah, 0
    lengthmessage BYTE 0dh, 0ah, "total bytes used: ", 0
    finalmessage BYTE 0dh, 0ah, "Press any key to continue ...", 0

    .code

start:

; �������������������������������������������������������������������������

    call main
    inkey
    exit

; �������������������������������������������������������������������������

main proc
  MOV edx, OFFSET bdmessage
  CALL WriteString
  MOV eax, DWORD PTR [barray + 2]
  CALL WriteDec
  MOV edx, OFFSET dwmessage
  CALL WriteString
  MOVZX eax, WORD PTR [dwarray + 6]
  CALL WriteDec
  MOV edx, OFFSET lengthmessage
  CALL WriteString
  MOV eax, TOTAL_LENGTH
  CALL WriteDec
  MOV edx, OFFSET finalmessage
  CALL WriteString

  CALL ReadChar
  INVOKE ExitProcess, 0
main endp

; �������������������������������������������������������������������������

end start
