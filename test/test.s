	.data

	.globl  .str.0
.str.0:
	.asciz  "Oops!"

	.globl  .str.1
.str.1:
	.asciz  " "

	.globl  .str.2
.str.2:
	.asciz  "\n"

	.globl  .str.3
.str.3:
	.asciz  ">"

	.globl  .str.4
.str.4:
	.asciz  "<="

	.globl  .str.5
.str.5:
	.asciz  ","

	.globl  .str.6
.str.6:
	.asciz  ""

	.globl  countA
	.p2align	2
countA:
	.word   0

	.globl  countB
	.p2align	2
countB:
	.word   0

	.globl  countC
	.p2align	2
countC:
	.word   0

	.globl  something
	.p2align	2
something:
	.word   0


	.text

	.globl  A.A
	.p2align	2
	.type   A.A, @function
A.A:
	addi    sp,sp,-48
	sw      ra,44(sp)
	sw      s0,40(sp)
	sw      s1,36(sp)
	sw      s2,32(sp)
	sw      s3,28(sp)
	sw      s4,24(sp)
	sw      s5,20(sp)
	sw      s6,16(sp)
	sw      s7,12(sp)
	sw      s8,8(sp)
	sw      s9,4(sp)
	sw      s10,0(sp)
	mv      s4,a0
	lui     a1,%hi(countA)
	lw      t2,%lo(countA)(a1)
	addi    a3,t2,1
	lui     a1,%hi(countA)
	sw      a3,%lo(countA)(a1)
	sw      t2,12(s4)
	lw      t2,12(s4)
	li      a1,2
	rem     a1,t2,a1
	li      t2,0
	beq     a1,t2,A.A_thenBodyBlock.0
	j       A.A_elseBodyBlock.0

A.A_thenBodyBlock.0:
	li      a0,16
	call    __malloc
	mv      s6,a0
	mv      a0,s6
	call    A.A
	sw      s6,0(s4)
	lui     a1,%hi(countB)
	lw      a1,%lo(countB)(a1)
	li      t2,2
	rem     a1,a1,t2
	li      t2,0
	beq     a1,t2,A.A_thenBodyBlock.1
	j       A.A_elseBodyBlock.1

A.A_elseBodyBlock.0:
	sw      zero,0(s4)
	j       A.A_afterIfBlock.0

A.A_afterIfBlock.0:
	li      a0,20
	call    __malloc
	li      a1,2
	sw      a1,0(a0)
	addi    a1,a0,4
	sw      a1,8(s4)
	lw      s6,8(s4)
	li      a0,52
	call    __malloc
	li      a1,6
	sw      a1,0(a0)
	addi    a1,a0,4
	mv      s1,a1
	addi    s7,s1,24
	mv      s0,s1
	j       A.A_creatorCondBlock.0

A.A_thenBodyBlock.1:
	li      a0,8
	call    __malloc
	mv      s6,a0
	mv      a0,s6
	call    B.B
	sw      s6,4(s4)
	j       A.A_afterIfBlock.1

A.A_elseBodyBlock.1:
	sw      zero,4(s4)
	j       A.A_afterIfBlock.1

A.A_afterIfBlock.1:
	j       A.A_afterIfBlock.0

A.A_creatorCondBlock.0:
	bne     s0,s7,A.A_creatorBodyBlock.0
	j       A.A_afterCreatorBlock.0

A.A_creatorBodyBlock.0:
	li      a0,52
	call    __malloc
	li      a1,6
	sw      a1,0(a0)
	addi    a1,a0,4
	mv      s2,a1
	addi    s8,s2,24
	mv      s9,s2
	j       A.A_creatorCondBlock.1

A.A_afterCreatorBlock.0:
	lw      a1,8(s1)
	lw      a1,12(a1)
	lw      a1,12(a1)
	sw      a1,0(s6)
	lw      a1,8(s4)
	sw      zero,4(a1)
	lw      a1,8(s4)
	lw      s4,-4(a1)
	li      a1,2
	bne     s4,a1,A.A_thenBodyBlock.2
	j       A.A_afterIfBlock.2

A.A_creatorCondBlock.1:
	bne     s9,s8,A.A_creatorBodyBlock.1
	j       A.A_afterCreatorBlock.1

A.A_creatorBodyBlock.1:
	li      a0,52
	call    __malloc
	li      a1,6
	sw      a1,0(a0)
	addi    a1,a0,4
	mv      s3,a1
	addi    s5,s3,24
	mv      s10,s3
	j       A.A_creatorCondBlock.2

A.A_afterCreatorBlock.1:
	sw      s2,0(s0)
	addi    a1,s0,4
	mv      s0,a1
	j       A.A_creatorCondBlock.0

A.A_creatorCondBlock.2:
	bne     s10,s5,A.A_creatorBodyBlock.2
	j       A.A_afterCreatorBlock.2

A.A_creatorBodyBlock.2:
	li      a0,52
	call    __malloc
	li      a1,6
	sw      a1,0(a0)
	addi    a1,a0,4
	sw      a1,0(s10)
	addi    a1,s10,4
	mv      s10,a1
	j       A.A_creatorCondBlock.2

A.A_afterCreatorBlock.2:
	sw      s3,0(s9)
	addi    a1,s9,4
	mv      s9,a1
	j       A.A_creatorCondBlock.1

A.A_thenBodyBlock.2:
	lui     a1,%hi(.str.0)
	addi    a0,a1,%lo(.str.0)
	call    __println
	j       A.A_afterIfBlock.2

A.A_afterIfBlock.2:
	lw      s10,0(sp)
	lw      s9,4(sp)
	lw      s8,8(sp)
	lw      s7,12(sp)
	lw      s6,16(sp)
	lw      s5,20(sp)
	lw      s4,24(sp)
	lw      s3,28(sp)
	lw      s2,32(sp)
	lw      s1,36(sp)
	lw      s0,40(sp)
	lw      ra,44(sp)
	addi    sp,sp,48
	jr      ra


	.globl  A.getc0
	.p2align	2
	.type   A.getc0, @function
A.getc0:
	lw      a1,8(a0)
	lw      a0,0(a1)
	jr      ra


	.globl  B.B
	.p2align	2
	.type   B.B, @function
B.B:
	addi    sp,sp,-16
	sw      ra,12(sp)
	sw      s0,8(sp)
	sw      s1,4(sp)
	mv      s0,a0
	lui     a1,%hi(countB)
	lw      a3,%lo(countB)(a1)
	addi    a1,a3,1
	lui     t2,%hi(countB)
	sw      a1,%lo(countB)(t2)
	sw      a3,0(s0)
	li      a0,16
	call    __malloc
	mv      s1,a0
	mv      a0,s1
	call    A.A
	mv      a0,s1
	call    A.getc0
	lw      a0,0(a0)
	call    C.Me
	call    C.Me
	sw      a0,4(s0)
	lw      s1,4(sp)
	lw      s0,8(sp)
	lw      ra,12(sp)
	addi    sp,sp,16
	jr      ra


	.globl  C.C
	.p2align	2
	.type   C.C, @function
C.C:
	addi    sp,sp,-16
	sw      ra,12(sp)
	sw      s0,8(sp)
	mv      s0,a0
	lui     a1,%hi(countC)
	lw      t2,%lo(countC)(a1)
	addi    a3,t2,1
	lui     a1,%hi(countC)
	sw      a3,%lo(countC)(a1)
	sw      t2,0(s0)
	lw      a0,0(s0)
	call    __toString
	sw      a0,4(s0)
	mv      a0,s0
	call    C.Me
	lui     a1,%hi(something)
	sw      a0,%lo(something)(a1)
	lw      s0,8(sp)
	lw      ra,12(sp)
	addi    sp,sp,16
	jr      ra


	.globl  C.Me
	.p2align	2
	.type   C.Me, @function
C.Me:
	jr      ra


	.globl  count
	.p2align	2
	.type   count, @function
count:
	addi    sp,sp,-16
	sw      ra,12(sp)
	sw      s0,8(sp)
	lui     a1,%hi(countA)
	li      t2,0
	sw      t2,%lo(countA)(a1)
	lui     a1,%hi(countB)
	li      t2,0
	sw      t2,%lo(countB)(a1)
	lui     a1,%hi(countC)
	li      t2,0
	sw      t2,%lo(countC)(a1)
	li      a0,8
	call    __malloc
	call    B.B
	lui     a1,%hi(countA)
	lw      a0,%lo(countA)(a1)
	call    __toString
	lui     a1,%hi(.str.1)
	addi    a1,a1,%lo(.str.1)
	call    __stringAdd
	mv      s0,a0
	lui     a1,%hi(countB)
	lw      a0,%lo(countB)(a1)
	call    __toString
	mv      a1,a0
	mv      a0,s0
	call    __stringAdd
	lui     a1,%hi(.str.1)
	addi    a1,a1,%lo(.str.1)
	call    __stringAdd
	mv      s0,a0
	lui     a1,%hi(countC)
	lw      a0,%lo(countC)(a1)
	call    __toString
	mv      a1,a0
	mv      a0,s0
	call    __stringAdd
	call    __println
	lui     t2,%hi(countA)
	li      a1,1
	sw      a1,%lo(countA)(t2)
	lui     t2,%hi(countB)
	li      a1,1
	sw      a1,%lo(countB)(t2)
	lui     t2,%hi(countC)
	li      a1,1
	sw      a1,%lo(countC)(t2)
	li      a0,8
	call    __malloc
	call    B.B
	lui     a1,%hi(countA)
	lw      a1,%lo(countA)(a1)
	addi    a0,a1,-1
	call    __toString
	lui     a1,%hi(.str.1)
	addi    a1,a1,%lo(.str.1)
	call    __stringAdd
	mv      s0,a0
	lui     a1,%hi(countB)
	lw      a1,%lo(countB)(a1)
	addi    a0,a1,-1
	call    __toString
	mv      a1,a0
	mv      a0,s0
	call    __stringAdd
	lui     a1,%hi(.str.1)
	addi    a1,a1,%lo(.str.1)
	call    __stringAdd
	mv      s0,a0
	lui     a1,%hi(countC)
	lw      a1,%lo(countC)(a1)
	addi    a0,a1,-1
	call    __toString
	mv      a1,a0
	mv      a0,s0
	call    __stringAdd
	call    __print
	lui     a1,%hi(.str.2)
	addi    a0,a1,%lo(.str.2)
	call    __print
	lui     a1,%hi(something)
	lw      a0,%lo(something)(a1)
	call    C.Me
	lw      s0,4(a0)
	lui     a1,%hi(something)
	lw      a1,%lo(something)(a1)
	lw      a0,4(a1)
	call    __stringLength
	addi    a2,a0,-1
	mv      a0,s0
	li      a1,1
	call    __stringSubstring
	call    __stringParseInt
	call    __toString
	call    __println
	lui     a1,%hi(something)
	lw      a1,%lo(something)(a1)
	lw      a0,4(a1)
	li      a1,0
	call    __stringOrd
	call    __toString
	mv      s0,a0
	lui     a1,%hi(something)
	lw      a1,%lo(something)(a1)
	lw      a1,4(a1)
	mv      a0,s0
	call    __stringLess
	bne     a0,zero,count_thenBodyBlock.0
	j       count_elseBodyBlock.0

count_thenBodyBlock.0:
	lui     a1,%hi(something)
	lw      a1,%lo(something)(a1)
	lw      a0,4(a1)
	lui     a1,%hi(.str.3)
	addi    a1,a1,%lo(.str.3)
	call    __stringAdd
	mv      a1,s0
	call    __stringAdd
	call    __println
	j       count_afterIfBlock.0

count_elseBodyBlock.0:
	lui     a1,%hi(something)
	lw      a1,%lo(something)(a1)
	lw      a0,4(a1)
	lui     a1,%hi(.str.4)
	addi    a1,a1,%lo(.str.4)
	call    __stringAdd
	mv      a1,s0
	call    __stringAdd
	call    __println
	j       count_afterIfBlock.0

count_afterIfBlock.0:
	lw      s0,8(sp)
	lw      ra,12(sp)
	addi    sp,sp,16
	jr      ra


	.globl  main
	.p2align	2
	.type   main, @function
main:
	call    __init__
	li      s4,0
	j       main_forCondBlock.0

main_forCondBlock.0:
	xori    a1,s4,627
	xori    t2,a1,666
	seqz    t2,t2
	addi    a1,zero,1
	beq     t2,a1,main_thenBodyBlock.0
	j       main_afterIfBlock.0

main_thenBodyBlock.0:
	mv      a0,s4
	call    __toString
	call    __println
	li      a0,0
	call    __toString
	call    __println
	li      a0,1
	call    __toString
	call    __println
	call    count
	j       main_whileCondBlock.0

main_afterIfBlock.0:
	addi    s4,s4,1
	j       main_forCondBlock.0

main_whileCondBlock.0:
	li      a1,2
	rem     a1,s4,a1
	li      t2,0
	beq     a1,t2,main_thenBodyBlock.1
	j       main_afterIfBlock.1

main_thenBodyBlock.1:
	j       main_whileCondBlock.0

main_afterIfBlock.1:
	mv      a0,s4
	call    __toString
	lui     a1,%hi(.str.5)
	addi    a1,a1,%lo(.str.5)
	call    __stringAdd
	call    __print
	j       main_whileCondBlock.0


	.globl  __init__
	.p2align	2
	.type   __init__, @function
__init__:
	jr      ra


