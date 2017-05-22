INCLUDE \masm32\include\masm32rt.inc
INCLUDE \masm32\macros\macros.asm  ; for using inkey
INCLUDELIB \masm32\lib\Irvine32.lib

.486
.model flat, stdcall
.stack 1000h
ExitProcess PROTO, DwErrorCode:DWORD
WriteHex PROTO
Crlf PROTO

.data
  sum DWORD ?
.code
    mov eax, 5
   add eax, 6
   print "Hello!", 0dh, 0ah
  main PROC
   
   CALL WriteHex
   CALL Crlf
   inc eax
   jmp main
   
   
		
   
   
   ;inkey  then console build should be performed
   INVOKE ExitProcess, 0
  main ENDP
END main 