; ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤
    include \masm32\include\masm32rt.inc
    include \masm32\include\debug.inc
    include \masm32\include\Irvine32.inc
    includelib \masm32\lib\debug.lib
    includelib \masm32\lib\Irvine32.lib
; ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤
WriteHex PROTO
WriteInt PROTO
WriteString PROTO
ExitProcess PROTO, DwErrorCode:DWORD
Crlf PROTO
comment * -----------------------------------------------------
                     Build this console app with
                  "MAKEIT.BAT" on the PROJECT menu.
        ----------------------------------------------------- *

    .data
        p1_expression dword ?
		
		
		

    .code

start:
   
; ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤

    print "Problem 1",0
    call Crlf
    call problem1
	print "Problem 2",0
	call CrLf
	mov edx, offset p2_monday
	call WriteString
	call Crlf
	
    inkey
    INVOKE ExitProcess,0

; ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤

problem1 proc

    ; A = (A + B) - (B + C)
    ; A = EAX
    ; B = EBX
    ; C = ECX
    ; D = TEMP

    ; A = 0
    ; B = 3
    ; C = 2
    mov eax,0
    mov ebx,3
    mov ecx,2

    mov edx,eax
    add edx,ebx
    mov eax,edx
    mov edx,ebx
    add ebx,ecx
    sub eax,ebx
    mov p1_expression,eax
    call WriteInt
    call Crlf
    
    ret

    
problem1 endp


; ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤

end start
