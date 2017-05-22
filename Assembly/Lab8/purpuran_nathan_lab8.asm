include \masm32\include\masm32rt.inc
include \masm32\include\Irvine32.inc
includelib \masm32\lib\Irvine32.lib
WriteInt proto
WriteChar proto
WriteString proto

Crlf proto
    .data
	MAX = 80                     ;max chars to read
	stringIn BYTE MAX+1 DUP (?)  ;room for null
      myString byte "To be or not to be - that is the question",0
      key      byte 6,4,7,1,5,4,3,2,1,7
	__static_message_EnterText byte "Please Enter Text to be Encrypted: ",0
    .code
start:
    call main
    inkey
    exit
main proc
  mov edx, offset myString
  call WriteString
  call Crlf
  mov edi, offset key
  mov ecx, lengthof key
  mov ebx,0
  call Crypt
  mov edx, offset myString
  call WriteString
  call Crlf
  mov ebx,1
  call Crypt
  mov edx, offset myString
  call WriteString
  call Crlf
  mov edx, offset __static_message_EnterText
  call WriteString
  mov  edx,OFFSET stringIn
  mov  ecx,MAX            ;buffer size - 1
  call ReadString
  mov edx, offset stringIn
  call WriteString
  call Crlf
  mov edi, offset key
  mov ecx, lengthof key
  mov ebx,0
  call Crypt
  mov edx, offset stringIn
  call WriteString
  call Crlf
  mov ebx,1
  call Crypt
  mov edx, offset stringIn
  call WriteString
  call Crlf
  ret
  
main endp

Crypt PROC uses EDX ECX EDI EBX ;EDX=TextPtr ECX=SizeOfKey ESI=KeyPtr EBX=1(Right)|0(Left)
	jmp check
	CryptLeft:
		mov al,byte ptr[edx]
		cmp al,0
		je crypt_end
		push ecx
		mov cl,byte ptr[edi]
		rol al,cl
		mov [edx],al
		pop ecx
		inc edx
		inc edi
		loop CryptLeft
		jmp check
	CryptRight:
		mov al,byte ptr[edx]
		cmp al,0
		je crypt_end
		push ecx
		mov cl,byte ptr[edi]
		ror al,cl
		mov [edx],al
		pop ecx
		inc edx
		inc edi
		loop CryptRight
		jmp check
	check:
		mov eax,[edx]
		cmp eax,0
		je crypt_end
		mov ecx,10
		cmp ebx,1
		je CryptRight
		jmp CryptLeft
	crypt_end:
	
	ret
Crypt endp
end start
