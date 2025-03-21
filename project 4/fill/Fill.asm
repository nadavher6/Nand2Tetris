// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

//i WILL BE THE ROW COUNTER
(START)
//i=8191
@8191
D=A
@i
M=D

//IF (KBD==0) GO TO "WHITEJUMP" 
@KBD
D=M
@WHITEJUMP
D;JEQ
//IF WE ARE AT THIS POINT - (KBD!=0) GO TO "BLACK" 
@BLACKJUMP
0;JMP

//MAKE ALL THE SCREEN WHITE AND START FROM THE BEGINING 
(WHITEJUMP)
(WHITEN)
@i
D=M
@SCREEN
A=D+A
M=0

// i = i - 1
@i
M=M-1

//CHECK IF (i==-1) START FROM THE BEGINNIG 
@i
D=M
@START
D;JLT
////IF WE ARE AT THIS POINT - (i!=0) KEEP WHITEN THE SCREEN
@WHITEN 
0;JMP

//MAKE ALL THE SCREEN BLACK 
(BLACKJUMP)
(BLACKEN)
@i
D=M
@SCREEN
A=D+A
M=-1

// i = i - 1
@i
M=M-1

//CHECK IF (i==-1) START FROM THE BEGINNIG 
@i
D=M
@START
D;JLT
////IF WE ARE AT THIS POINT - (i!=0) KEEP BLACKEN THE SCREEN
@BLACKEN
0;JMP


