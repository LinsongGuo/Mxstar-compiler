	.data

	.globl  .str.0
.str.0:
	.asciz  " O"

	.globl  .str.1
.str.1:
	.asciz  " ."

	.globl  .str.2
.str.2:
	.asciz  ""

	.globl  N
	.p2align	2
N:
	.word   8

	.globl  row
	.p2align	2
row:
	.word   0

	.globl  col
	.p2align	2
col:
	.word   0

	.globl  d
	.p2align	2
d:
	.word   0


	.text

	.globl  printBoard
	.p2align	2
	.type   printBoard, @function
printBoard:
	addi    sp,sp,-16
	sw      ra,12(sp)
	sw      s0,8(sp)
	sw      s1,4(sp)
	mv      s1,s2
	li      t3,0
	mv      s2,t3
	j       printBoard_forCondBlock.0

printBoard_forCondBlock.0:
	lui     t3,%hi(N)
	lw      t3,%lo(N)(t3)
	blt     s2,t3,printBoard_forBodyBlock.0
	j       printBoard_afterForBlock.0

printBoard_forBodyBlock.0:
	li      t3,0
	mv      s0,t3
	j       printBoard_forCondBlock.1

printBoard_afterForBlock.0:
	lui     t3,%hi(.str.2)
	addi    a0,t3,%lo(.str.2)
	call    __println
	mv      s2,s1
	lw      s1,4(sp)
	lw      s0,8(sp)
	lw      ra,12(sp)
	addi    sp,sp,16
	jr      ra

printBoard_forCondBlock.1:
	lui     t3,%hi(N)
	lw      t3,%lo(N)(t3)
	blt     s0,t3,printBoard_forBodyBlock.1
	j       printBoard_afterForBlock.1

printBoard_forBodyBlock.1:
	lui     t3,%hi(col)
	lw      a4,%lo(col)(t3)
	slli    t3,s2,2
	add     t3,a4,t3
	lw      t3,0(t3)
	beq     t3,s0,printBoard_thenBodyBlock.0
	j       printBoard_elseBodyBlock.0

printBoard_afterForBlock.1:
	lui     t3,%hi(.str.2)
	addi    a0,t3,%lo(.str.2)
	call    __println
	addi    t3,s2,1
	mv      s2,t3
	j       printBoard_forCondBlock.0

printBoard_thenBodyBlock.0:
	lui     t3,%hi(.str.0)
	addi    a0,t3,%lo(.str.0)
	call    __print
	j       printBoard_afterIfBlock.0

printBoard_elseBodyBlock.0:
	lui     t3,%hi(.str.1)
	addi    a0,t3,%lo(.str.1)
	call    __print
	j       printBoard_afterIfBlock.0

printBoard_afterIfBlock.0:
	addi    t3,s0,1
	mv      s0,t3
	j       printBoard_forCondBlock.1


	.globl  search
	.p2align	2
	.type   search, @function
search:
	addi    sp,sp,-16
	sw      ra,12(sp)
	sw      s0,8(sp)
	sw      s1,4(sp)
	mv      s1,s2
	mv      s2,a0
	lui     t3,%hi(N)
	lw      t3,%lo(N)(t3)
	beq     s2,t3,search_thenBodyBlock.0
	j       search_elseBodyBlock.0

search_thenBodyBlock.0:
	call    printBoard
	j       search_afterIfBlock.0

search_elseBodyBlock.0:
	li      t3,0
	mv      s0,t3
	j       search_forCondBlock.0

search_afterIfBlock.0:
	mv      s2,s1
	lw      s1,4(sp)
	lw      s0,8(sp)
	lw      ra,12(sp)
	addi    sp,sp,16
	jr      ra

search_forCondBlock.0:
	lui     t3,%hi(N)
	lw      t3,%lo(N)(t3)
	blt     s0,t3,search_forBodyBlock.0
	j       search_afterForBlock.0

search_forBodyBlock.0:
	lui     t3,%hi(row)
	lw      t3,%lo(row)(t3)
	slli    a4,s0,2
	add     t3,t3,a4
	lw      t3,0(t3)
	li      a4,0
	beq     t3,a4,search_logicalAnd.0
	j       search_criticalEdge.0

search_afterForBlock.0:
	j       search_afterIfBlock.0

search_thenBodyBlock.1:
	lui     t3,%hi(d)
	lw      t3,%lo(d)(t3)
	lw      a4,4(t3)
	lui     t3,%hi(N)
	lw      t3,%lo(N)(t3)
	add     t3,s0,t3
	addi    t3,t3,-1
	sub     t3,t3,s2
	slli    t3,t3,2
	add     a4,a4,t3
	li      t3,1
	sw      t3,0(a4)
	lui     t3,%hi(d)
	lw      t3,%lo(d)(t3)
	lw      t3,0(t3)
	add     a4,s0,s2
	slli    a4,a4,2
	add     a4,t3,a4
	li      t3,1
	sw      t3,0(a4)
	lui     t3,%hi(row)
	lw      a4,%lo(row)(t3)
	slli    t3,s0,2
	add     t3,a4,t3
	li      a4,1
	sw      a4,0(t3)
	lui     t3,%hi(col)
	lw      t3,%lo(col)(t3)
	slli    a4,s2,2
	add     t3,t3,a4
	sw      s0,0(t3)
	addi    a0,s2,1
	call    search
	lui     t3,%hi(row)
	lw      t3,%lo(row)(t3)
	slli    a4,s0,2
	add     a4,t3,a4
	li      t3,0
	sw      t3,0(a4)
	lui     t3,%hi(d)
	lw      t3,%lo(d)(t3)
	lw      t3,0(t3)
	add     a4,s0,s2
	slli    a4,a4,2
	add     a4,t3,a4
	li      t3,0
	sw      t3,0(a4)
	lui     t3,%hi(d)
	lw      t3,%lo(d)(t3)
	lw      t3,4(t3)
	lui     a4,%hi(N)
	lw      a4,%lo(N)(a4)
	add     a4,s0,a4
	addi    a4,a4,-1
	sub     a4,a4,s2
	slli    a4,a4,2
	add     t3,t3,a4
	li      a4,0
	sw      a4,0(t3)
	j       search_afterIfBlock.1

search_afterIfBlock.1:
	addi    t3,s0,1
	mv      s0,t3
	j       search_forCondBlock.0

search_logicalAnd.0:
	lui     t3,%hi(d)
	lw      t3,%lo(d)(t3)
	lw      a4,0(t3)
	add     t3,s0,s2
	slli    t3,t3,2
	add     t3,a4,t3
	lw      t3,0(t3)
	xori    t3,t3,0
	seqz    t3,t3
	j       search_afterLogicalAnd.0

search_afterLogicalAnd.0:
	bne     t3,zero,search_logicalAnd.1
	j       search_criticalEdge.1

search_logicalAnd.1:
	lui     t3,%hi(d)
	lw      t3,%lo(d)(t3)
	lw      a4,4(t3)
	lui     t3,%hi(N)
	lw      t3,%lo(N)(t3)
	add     t3,s0,t3
	addi    t3,t3,-1
	sub     t3,t3,s2
	slli    t3,t3,2
	add     t3,a4,t3
	lw      t3,0(t3)
	xori    t3,t3,0
	seqz    t3,t3
	j       search_afterLogicalAnd.1

search_afterLogicalAnd.1:
	bne     t3,zero,search_thenBodyBlock.1
	j       search_afterIfBlock.1

search_criticalEdge.0:
	mv      t3,zero
	j       search_afterLogicalAnd.0

search_criticalEdge.1:
	mv      t3,zero
	j       search_afterLogicalAnd.1


	.globl  main
	.p2align	2
	.type   main, @function
main:
	addi    sp,sp,-16
	sw      ra,12(sp)
	sw      s0,8(sp)
	sw      s1,4(sp)
	mv      s1,s2
	call    __init__
	li      t3,0
	mv      s0,t3
	j       main_forCondBlock.0

main_forCondBlock.0:
	li      t3,2
	blt     s0,t3,main_forBodyBlock.0
	j       main_afterForBlock.0

main_forBodyBlock.0:
	lui     t3,%hi(d)
	lw      t3,%lo(d)(t3)
	slli    s2,s0,2
	add     s2,t3,s2
	li      a0,64
	call    __malloc
	li      t3,15
	sw      t3,0(a0)
	addi    t3,a0,4
	sw      t3,0(s2)
	addi    t3,s0,1
	mv      s0,t3
	j       main_forCondBlock.0

main_afterForBlock.0:
	li      a0,0
	call    search
	li      a0,0
	mv      s2,s1
	lw      s1,4(sp)
	lw      s0,8(sp)
	lw      ra,12(sp)
	addi    sp,sp,16
	jr      ra


	.globl  __init__
	.p2align	2
	.type   __init__, @function
__init__:
	addi    sp,sp,-16
	sw      ra,12(sp)
	li      a0,36
	call    __malloc
	li      t3,8
	sw      t3,0(a0)
	addi    t3,a0,4
	lui     a4,%hi(row)
	sw      t3,%lo(row)(a4)
	li      a0,36
	call    __malloc
	li      t3,8
	sw      t3,0(a0)
	addi    t3,a0,4
	lui     a4,%hi(col)
	sw      t3,%lo(col)(a4)
	li      a0,12
	call    __malloc
	li      t3,2
	sw      t3,0(a0)
	addi    t3,a0,4
	lui     a4,%hi(d)
	sw      t3,%lo(d)(a4)
	lw      ra,12(sp)
	addi    sp,sp,16
	jr      ra


