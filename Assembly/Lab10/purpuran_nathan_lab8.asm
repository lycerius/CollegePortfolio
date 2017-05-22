include \masm32\include\masm32rt.inc
include \masm32\include\Irvine32.inc
includelib \masm32\lib\Irvine32.lib
WriteInt proto
WriteChar proto
FindTriplets proto, ptrArray:PTR DWORD, arrSize:DWORD, key:DWORD
Crlf proto
    .data
		Testarray1 DWORD 12h, 23h, 23h, 23h, 12h, 32h, 32h, 32h, 23h, 23h, 23h, 23h, 11h
		
	
    .code
	start:
	mov eax, offset Testarray1
	call WriteInt
	mov eax, dword ptr[Testarray1]
	call WriteInt
    invoke FindTriplets, addr Testarray1, 13, 32h
	call WriteInt
    inkey
    exit


;Create
;a procedure named FindTriplets that returns 1 if an array has three consecutive values of
;M starting at some position. Otherwise, return 0. The procedure’s input parameter list contains
;a pointer to the
;DWORD
;array, the array size
;and the key value M. Use the PROC directive with a
;parameter list when declaring the procedure. Preserve all registers (except EAX) that are 
;modified by the procedure. Write a test program that calls FindTriplets several times with
;different arrays.

FindTriplets proc uses ebx ecx edx edi esi, ptrArray:PTR DWORD, arrSize:DWORD, key:DWORD
	
	
	mov edx,ptrArray
	mov ebx, 0
	mov edi,0
	_test:
		cmp edi,arrSize
		jge _set0
		mov ecx,dword ptr[ptrArray+edi*4];ecx = ptrArray[i]
		push eax
		mov eax,ecx
		call WriteInt
		pop eax
		cmp ecx,key
		je _continue
	_reset:
		mov eax,0
		add edx,4
		inc edi
		jmp _test
	_continue:
	push eax
	mov eax,','
	call WriteChar
	pop eax
		inc edi
		inc eax
		cmp eax,3
		jne _test
		mov eax,1
		jmp _done
	_set0:
		mov eax,0
	_done:
	ret
FindTriplets endp
end start

