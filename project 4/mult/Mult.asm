// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
// The algorithm is based on repetitive addition.

//// Replace this comment with your code.
// Mult.asm

//R[2]=0
@2
M=0
//R[6]=R[1]
@1
D=M
@6
M=D

(LOOP)
// IF (R[6]==0) GO TO END
@6
D=M
@END
D;JEQ

//ADD TO R[2] = R[2]+R[0]
@0
D=M
@2
M=D+M

// R[6]--
@6
M=M-1

//GO TO LOOP
@LOOP
0;JMP

//EXIT OF THE LOOP
(END)
@END
0;JMP


