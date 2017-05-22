; �������������������������������������������������������������������������
    include \masm32\include\masm32rt.inc
    include \masm32\include\Irvine32.inc
    includelib \masm32\include\Irvine32.lib
; �������������������������������������������������������������������������



.data

  reverseArray dword 1,2,3,4,5

.code

jmp main

lab05_problem1: #eax=top array; ebx=bottom array; edx=bottom temp; esi = top temp
  mov eax,lengthof reverseArray
  idiv 2
  mov ecx, eax
  mov eax, lengthof reverseArray
  sub eax,1
  mov ebx,0
  lab05_problem1_loop:
  mov edx, dword ptr[reverseArray+ebx]
  mov esi, dword ptr[reverseArray+eax]
  mov dword ptr[reverseArray+ebx],esi
  mov dword ptr[reverseArray+eax], edx
  dec eax
  inc ebx
  loop lab05_problem1_loop
  ret

main:
  call lab05_problem1
  ret
