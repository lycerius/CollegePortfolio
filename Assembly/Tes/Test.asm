includelib \masm32\lib\Irvine32.lib
.386
; いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい�
    include \masm32\include\masm32rt.inc
; いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい�

comment * -----------------------------------------------------
                     Build this console app with
                  "MAKEIT.BAT" on the PROJECT menu.
        ----------------------------------------------------- *

    .data?
      value dd ?
	

    .data
      item dd 0
	hello dword ?
	
    .code

	

start:
   
; いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい�

    call main
    inkey
    exit

; いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい�

main proc

    cls
    print "Hello World",13,10

 	mov eax,3
	mov ebx,4
	imul ebx
	mov hello,eax
	invoke StdOut, addr hello
    ret

main endp

; いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい�

end start
