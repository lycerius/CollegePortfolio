include \masm32\include\masm32rt.inc
include \masm32\include\Irvine32.inc
includelib \masm32\lib\Irvine32.lib
WriteInt proto
WriteChar proto
Crlf proto
    .data
    
    .code
start:
    call main
    inkey
    exit
main proc

main endp

end start
