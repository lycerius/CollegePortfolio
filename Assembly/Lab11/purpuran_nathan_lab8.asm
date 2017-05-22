include \masm32\include\masm32rt.inc
include \masm32\include\Irvine32.inc
includelib \masm32\lib\Irvine32.lib
Coord STRUCT
  align WORD
  X WORD ?
  align WORD
  Y WORD ?
Coord ENDS

Rectang STRUCT
  Direction BYTE ?
  Vertex1 Coord <>
  Vertex2 Coord <>
Rectang ENDS

WriteInt proto
WriteChar proto
WriteString proto
ReadInt proto
ReadChar proto
OutputRectangle proto Rect:Rectang

Crlf proto
    .data
   UserRectangle Rectang < ?, {?,?}, {?,?} >
   __STR_NE db "NE",0
   __STR_NW db "NW",0
   __STR_SE db "SE",0
   __STR_SW db "SW",0
   
   __STR_EnterCoords db "Enter in the coordinates for the rectangle (Direction will be calculated) (X1, Y1, X2, Y2): ",0
   __STR_Reordered   db "Reordered...",0
   __STR_Continue    db "Would you like to continue? (y): ",0
   __STR_Invalid     db "Invalid input... start over",0
   .code
start:
    call main
    inkey
    exit

main proc
  _CONTINUE:
  mov edx, OFFSET __STR_EnterCoords
  call WriteString
  call Crlf
  call ReadInt
  jo _INVALID
  mov UserRectangle.Vertex1.X, ax
  
  call ReadInt
  jo _INVALID
  mov UserRectangle.Vertex1.Y, ax
  call ReadInt
  jo _INVALID
  mov UserRectangle.Vertex2.X, ax
  call ReadInt
  jo _INVALID
  mov UserRectangle.Vertex2.Y, ax
  
  mov ax, UserRectangle.Vertex1.X
  mov bx, UserRectangle.Vertex2.X
  cmp ax,bx
  jg _DIR_P1
  
  _CHECK_Y:
  mov ax, UserRectangle.Vertex1.Y
  mov bx, UserRectangle.Vertex2.Y
  cmp ax,bx
  jg _DIR_P2
  
  _DONE:
  invoke OutputRectangle, UserRectangle
  call Crlf
  call _REORDER
  mov edx, OFFSET __STR_Reordered
  call WriteString
  call Crlf
  invoke OutputRectangle, UserRectangle
  call Crlf
  mov edx, OFFSET __STR_Continue
  call WriteString
  call Crlf
  call ReadChar
  cmp al, 'y'
  je _CONTINUE
  ret
  
  _DIR_P1:
  mov al, UserRectangle.Direction
  add al,1
  mov UserRectangle.Direction, al
  jmp _CHECK_Y
  
  _DIR_P2:
  mov al, UserRectangle.Direction
  add al,2
  mov UserRectangle.Direction, al
  jmp _DONE
  
  _REORDER:
  
  mov ax, UserRectangle.Vertex1.X
  mov bx, UserRectangle.Vertex1.Y
  mov cx, UserRectangle.Vertex2.X
  mov dx, UserRectangle.Vertex2.Y
  
  cmp ax,cx
  jg _SWITCH_X
  _R_CHECK_Y:
  cmp bx,dx
  jg _SWITCH_Y
  
  _R_DONE:
  mov UserRectangle.Direction, 0
  ret
  
  _SWITCH_X:
  mov UserRectangle.Vertex1.X,cx
  mov UserRectangle.Vertex2.X,ax
  jmp _R_CHECK_Y
  _SWITCH_Y:
  mov UserRectangle.Vertex1.Y,dx
  mov UserRectangle.Vertex2.Y,bx
  jmp _R_DONE
  
  _INVALID:
  mov edx, OFFSET __STR_Invalid
  call WriteString
  call Crlf
  jmp _CONTINUE
  
main endp

OutputRectangle proc uses eax edx, Rect:Rectang
	mov eax,0
	mov al, '<'
	call WriteChar
	
	call WriteChar
	mov ax, Rect.Vertex1.X
	call WriteInt
	mov al, ','
	call WriteChar
	mov ax, Rect.Vertex1.Y
	call WriteInt
	mov al, '>'
	call WriteChar
	mov al, ','
	call WriteChar
	mov al,' '
	call WriteChar
	
	mov al, '<'
	call WriteChar
	mov ax, Rect.Vertex2.X
	call WriteInt
	mov al, ','
	call WriteChar
	mov ax, Rect.Vertex2.Y
	call WriteInt
	mov al, '>'
	call WriteChar
	mov al, ','
	call WriteChar
	mov al, ' '
	call WriteChar
	
	xor eax,eax
	mov al, Rect.Direction
	cmp al,1
	je _NW
	cmp al,2
	je _SE
	cmp al,3
	je _SW
	
	_NE:
	mov edx, OFFSET __STR_NE
	call WriteString
	jmp _END
	_NW:
	mov edx, OFFSET __STR_NW
	call WriteString
	jmp _END
	_SE:
	mov edx, OFFSET __STR_SE
	call WriteString
	jmp _END
	_SW:
	mov edx, OFFSET __STR_SW
	call WriteString
	
	_END:
	mov al, '>'
	call WriteChar
	ret
OutputRectangle endp



end start
