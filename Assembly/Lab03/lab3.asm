; �������������������������������������������������������������������������
    include \masm32\include\masm32rt.inc
    include \masm32\include\Irvine32.inc
    includelib \masm32\lib\Irvine32.lib
    include \masm32\include\debug.inc
; �������������������������������������������������������������������������
WriteHex PROTO
Crlf PROTO
WriteString proto
comment * -----------------------------------------------------
                     Build this console app with
                  "MAKEIT.BAT" on the PROJECT menu.
        ----------------------------------------------------- *
.486
    .data
      problem1_myarray DWORD 6,?,?,?,?,6
      three DWORD 12345678h,56781234h,13245768h
      threetemp DWORD ?
      bigEndian BYTE 12h, 34h, 56h, 78h
      littleEndian DWORD ?
      myString BYTE "ABCDE", 0

    .code

start:

; �������������������������������������������������������������������������

    print "Problem 1:", 0
    call Crlf
    call problem1

    print "Problem 2:",0
    call Crlf
    call problem2
    inkey
    exit

; �������������������������������������������������������������������������

problem1 proc


  ret
problem1 endp

problem2 proc

  print "Part 1: ",0
  call Crlf
  ;Part 1
  ;We take each part individually into 2 seperate registers
  ;We then insert these 2 values in reverse order into a memory slot
  ;We then loop for the length of the array
  mov edx,0
  problem2_loop:
  mov bx, WORD PTR[three+edx]
  mov cx, WORD PTR[three+edx+2]
  mov WORD PTR[threetemp],cx
  mov WORD PTR[threetemp+2],bx
  mov eax, threetemp
  call WriteHex
  call Crlf
  add edx,4
  cmp edx,12
  jl problem2_loop

  print "Part 2:", 0
  call Crlf

  ;Part 2
  ;Basically we mov the whole dword (4 bytes) into ebx
  mov ebx, dword ptr[bigEndian]

  ;Then we move the lower half into the last byte of littleEndian
  ;And shift right to put the next set into the lowe half
  ;Repeat for 4
  mov byte ptr[littleEndian+3], bl
  shr ebx,8
  mov byte ptr[littleEndian+2], bl
  shr ebx,8
  mov byte ptr[littleEndian+1], bl
  shr ebx,8
  mov byte ptr[littleEndian], bl
  shr ebx,8
  mov eax, dword ptr[littleEndian]
  call WriteHex
  call Crlf

  print "Part 3: ", 0
  call Crlf
  ;Part 3
  ;We manually put the characters 'Hello' into the existing string
  mov byte ptr[myString],   'H'
  mov byte ptr[myString+1], 'E'
  mov byte ptr[myString+2], 'L'
  mov byte ptr[myString+3], 'L'
  mov byte ptr[myString+4], 'O'
  mov edx, offset myString
  call WriteString
  call Crlf
  ret
problem2 endp

; �������������������������������������������������������������������������

end start
