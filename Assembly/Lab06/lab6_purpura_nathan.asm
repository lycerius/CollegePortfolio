; ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤
    include \masm32\include\masm32rt.inc
	include \masm32\include\Irvine32.inc
	includelib \masm32\lib\Irvine32.lib
; ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤

WriteInt proto
WriteChar proto
Crlf      proto
WriteString proto
comment * -----------------------------------------------------
                     Build this console app with
                  "MAKEIT.BAT" on the PROJECT menu.
        ----------------------------------------------------- *

    .data
		array WORD 8 DUP(8 DUP (?))
		__static_message_sumof byte "The sum of the diagonal elements = ",0
		__static_message_arraycont byte "The contents of the array = ",0
	.code

start:
   
; ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤

    call main
    inkey
    exit

; ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤

main proc
	
	mov edx, offset __static_message_arraycont
	call WriteString
	call Crlf
	mov ax,101; eax=counter
	mov ebx,0;   ebx=row
	mov edx,0;   ecx=column
	mov ecx, 8
	
	arrayloop:
		push ecx ;save ecx state
		mov ecx,8
		mov edx,0
		arrayloop_column:
			mov esi, ebx
			imul esi, 8
			add esi, edx
			mov word ptr [array+esi*type array],ax
			inc eax
			inc edx
			loop arrayloop_column
		pop ecx ;revert ecx state
		inc ebx
		loop arrayloop
	
	
	mov eax,0
	mov ebx,0 ;ebx=row
	mov edx,0 ;edx=column
	mov ecx,8
	
	printarray:
		push ecx
		mov ebx,0
		mov ecx,8
		mov ax, '|'
		call WriteChar
		printarray_column:
			mov esi, ebx
			imul esi, 8
			add esi, edx
			
			mov ax, word ptr [array+esi*type array]
			call WriteInt
			mov ax, '|'
			call WriteChar
			inc ebx
			loop printarray_column
		call Crlf
		pop ecx
		inc edx
		loop printarray
		call Crlf
		mov edx, offset __static_message_sumof
		call WriteString
		
		mov eax,0
		mov ebx,0; ebx=row/column
		mov ecx,8
		calcdiagonals:
			calcdiagonals_sum:
			mov esi, ebx
			imul esi, 9
			add ax, word ptr[array+esi*type array]
			inc ebx
			loop calcdiagonals_sum
		call WriteInt
		call Crlf
		
		ret

main endp
; ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤
end start
