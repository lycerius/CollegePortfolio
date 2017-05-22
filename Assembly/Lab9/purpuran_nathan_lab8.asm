include \masm32\include\masm32rt.inc
include \masm32\include\Irvine32.inc
includelib \masm32\lib\Irvine32.lib
WriteInt proto
WriteChar proto
WriteString proto
Crlf proto
    .data
		__static_message_firstnumber byte "First Number: ",0
		__static_message_secondnumber byte "Second Number: ",0
		__static_message_gcd byte "The greatest common divisor is ",0
		__static_message_lcm byte "The lowest common multiple is ",0
		__static_message_continue byte "Continue ? (y/n) ",0
		__static_message_nozero byte "Number can't be zero",0
		
    .code
GCD proc uses eax ebx, x:dword, y:dword

	mov eax,y
	cmp eax, 0
	jg cont1
	neg eax
	cont1:
	mov dword ptr[y],eax
	mov eax,x
	cmp eax, 0
	jg cont2
	neg eax
	cont2:
	mov dword ptr[x],eax
	do_while:
		mov ecx, dword ptr[y]
		cmp ecx,0
		je ender
		mov eax, dword ptr[x]
		mov ecx, dword ptr[y]
		push ecx
		push eax
		xor     edx, edx
		div     ecx
		pop     eax
		pop     ecx
		mov dword ptr[x], ecx
		mov dword ptr[y], edx
		jmp do_while
		ender:
		mov edx,dword ptr[x]
		
		ret
GCD endp

LCM proc uses eax ebx, x:dword, y:dword

	mov eax,y
	cmp eax, 0
	jg cont1
	neg eax
	cont1:
	mov dword ptr[y],eax
	mov eax,x
	cmp eax, 0
	jg cont2
	neg eax
	cont2:
	mov dword ptr[x],eax

mov eax, x
mov ebx, y
mul ebx

invoke GCD, x, y
mov ebx, edx
sub edx,edx
div ebx
mov edx, eax
ret
LCM endp


start:
    call main
    inkey
    exit
	
main proc
	keepgoing:
		mov edx,offset __static_message_firstnumber
		call WriteString
		call ReadInt
		jo err
		cmp eax,0
		je err
		push eax
		mov edx,offset __static_message_secondnumber
		call WriteString
		call ReadInt
		jo err
		cmp eax,0
		je err
		push eax
		mov edx, offset __static_message_gcd
		call WriteString
		
		pop ebx
		pop eax
		push ebx
		push eax
		invoke GCD, eax, ebx
		mov eax,edx
		call WriteInt
		
		call Crlf
		mov edx, offset __static_message_lcm
		call WriteString
		
		pop ebx
		pop eax
		invoke LCM, eax, ebx
		mov eax,edx
		call WriteInt
		
		
		call Crlf
		mov edx, offset __static_message_continue
		call WriteString
		call Crlf
		call ReadChar
		cmp al,'y'
		je keepgoing
		ret
	err:
		mov edx,offset __static_message_nozero
		call WriteString
		call Crlf
		jmp keepgoing
main endp


end start

