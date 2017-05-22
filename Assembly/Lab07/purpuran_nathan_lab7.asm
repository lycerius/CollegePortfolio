include \masm32\include\masm32rt.inc
include \masm32\include\Irvine32.inc
includelib \masm32\lib\Irvine32.lib
WriteInt proto
WriteChar proto
Crlf proto
    .data
    arrayToOutput sdword 25 DUP (?)
	testArray     sdword 4, 8, -3, 6, 5, 0, 9, 1, 1
	__static_message_howmany db "How Many: ",0
	__static_message_lower db "Lower: ",0
	__static_message_upper db "Upper: ",0
	__static_message_abs   db "ABS= ",0
	temp sdword ?
    .code
start:
    call main
    inkey
    exit
main proc
    cls
    mov ecx,2
	call Randomize
	calltwice:
		push ecx
		mov ecx, lengthof arrayToOutput
		mov edi, offset arrayToOutput
		call initializeWithRandom
		;mov edi, offset testArray
		;mov ecx, lengthof testArray
		call outputValues
		call Crlf
		mov edx, offset __static_message_lower
		call WriteString
		call ReadInt
		jo badinput
		mov ebx,eax
		mov edx, offset __static_message_upper
		call WriteString
		call ReadInt
		jo badinput
		mov edx,eax
		call sumInRange
		push eax
		call WriteInt
		call Crlf
		push edx
		mov edx, offset __static_message_abs
		call WriteString
		pop edx
		pop eax
		call sumInRangeAbs
		call WriteInt
		call Crlf
		pop ecx
		loop calltwice
    ret
	badinput:
	pop ecx
	loop calltwice
	ret
main endp

outputValues proc uses eax ecx edi
; EDI = array ptr
; ECX = number
; TYPE = sword
  dec ecx
  internalLoop:
    mov eax,sdword ptr[edi]
    call WriteInt
    mov eax, '|'
    call WriteChar
    add edi, 4
    loop internalLoop
	mov eax,sdword ptr[edi]
	call WriteInt
    ret
outputValues endp

;EDI = array ptr
;ECX = count
;EBX = lower
;EDX = upper
;RETURN EAX = SUM
sumInRange PROC USES edi ecx ebx edx
    mov eax,0
    push eax
    sub edi,4
    inc ecx
    continue:
        add edi,4
        dec ecx
        cmp ecx,0
        je stopIt
        mov eax, sdword ptr [edi]
        cmp eax, ebx
        jl continue
        cmp eax, edx
        jg continue
        pop eax
        add eax, sdword ptr[edi]
        ;call DumpRegs
        push eax
        jmp continue
    stopIt:
        ;call DumpRegs
        pop eax
        ret
sumInRange ENDP

;EDI = array ptr
;ECX = count
;EBX = lower
;EDX = upper
;RETURN EAX = SUM
sumInRangeAbs PROC USES edi ecx ebx edx
    mov eax,0
    push eax
    sub edi,4
    inc ecx
    continue:
        add edi,4
        dec ecx
        cmp ecx,0
        je stopIt
        mov eax, sdword ptr [edi]
		call absValue
		mov sdword ptr[temp], eax
		
        cmp eax, ebx
        jl continue
        cmp eax, edx
        jg continue
		;call WriteInt
        pop eax
        add eax, sdword ptr[temp]
        ;call DumpRegs
        push eax
        jmp continue
    stopIt:
        ;call DumpRegs
        pop eax
        ret
sumInRangeAbs ENDP


initializeWithRandom proc uses EDI ECX EAX EBX
    
    generate:
        mov eax, 51
        call RandomRange
        mov ebx, eax
        mov eax, 2
        call RandomRange
        cmp eax,1
        mov eax, ebx
        je negate
        jne putinto
        negate:
            neg eax
        putinto:
            ;call WriteInt
            ;call DumpRegs
            mov sdword ptr[edi], eax
            add edi,4
            loop generate
        ret
initializeWithRandom endp


absValue proc
	;call WriteInt
	cmp eax,0
	jge done
	neg eax
	;call WriteInt
	done:
	;call WriteInt
	;call Crlf
	ret
absValue endp
end start
