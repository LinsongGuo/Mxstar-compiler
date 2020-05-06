	.data

	.globl  n
	.p2align	2
n:
	.word   0

	.globl  r
	.p2align	2
r:
	.word   0

	.globl  c
	.p2align	2
c:
	.word   0

	.globl  i
	.p2align	2
i:
	.word   0

	.globl  j
	.p2align	2
j:
	.word   0


	.text

	.globl  abs
	.p2align	2
	.type   abs, @function
abs:
	li      a5,0
	bgt     a0,a5,abs_thenBodyBlock.0
	j       abs_afterIfBlock.0

abs_thenBodyBlock.0:
	j       abs_returnBlock.0

abs_afterIfBlock.0:
	li      a5,0
	sub     a0,a5,a0
	j       abs_returnBlock.0

abs_returnBlock.0:
	jr      ra


	.globl  main
	.p2align	2
	.type   main, @function
main:
	addi    sp,sp,-16
	sw      ra,12(sp)
	sw      s0,8(sp)
	call    __init__
	lui     a0,%hi(i)
	li      a5,0
	sw      a5,%lo(i)(a0)
	j       main_forCondBlock.0

main_forCondBlock.0:
	lui     a0,%hi(i)
	lw      a0,%lo(i)(a0)
	li      a5,5
	blt     a0,a5,main_forBodyBlock.0
	j       main_afterForBlock.0

main_forBodyBlock.0:
	lui     a0,%hi(j)
	li      a5,0
	sw      a5,%lo(j)(a0)
	j       main_forCondBlock.1

main_afterForBlock.0:
	lui     a0,%hi(r)
	lw      a0,%lo(r)(a0)
	li      a5,2
	sub     a0,a5,a0
	call    abs
	mv      s0,a0
	lui     a0,%hi(c)
	lw      a0,%lo(c)(a0)
	li      a5,2
	sub     a0,a5,a0
	call    abs
	add     a0,s0,a0
	call    __printInt
	li      a0,0
	lw      s0,8(sp)
	lw      ra,12(sp)
	addi    sp,sp,16
	jr      ra

main_forCondBlock.1:
	lui     a0,%hi(j)
	lw      a0,%lo(j)(a0)
	li      a5,5
	blt     a0,a5,main_forBodyBlock.1
	j       main_afterForBlock.1

main_forBodyBlock.1:
	call    __getInt
	lui     a5,%hi(n)
	sw      a0,%lo(n)(a5)
	lui     a0,%hi(n)
	lw      a0,%lo(n)(a0)
	li      a5,1
	beq     a0,a5,main_thenBodyBlock.0
	j       main_afterIfBlock.0

main_afterForBlock.1:
	lui     a0,%hi(i)
	lw      a0,%lo(i)(a0)
	addi    a0,a0,1
	lui     a5,%hi(i)
	sw      a0,%lo(i)(a5)
	j       main_forCondBlock.0

main_thenBodyBlock.0:
	lui     a0,%hi(i)
	lw      a5,%lo(i)(a0)
	lui     a0,%hi(r)
	sw      a5,%lo(r)(a0)
	lui     a0,%hi(j)
	lw      a5,%lo(j)(a0)
	lui     a0,%hi(c)
	sw      a5,%lo(c)(a0)
	j       main_afterIfBlock.0

main_afterIfBlock.0:
	lui     a0,%hi(j)
	lw      a0,%lo(j)(a0)
	addi    a5,a0,1
	lui     a0,%hi(j)
	sw      a5,%lo(j)(a0)
	j       main_forCondBlock.1


	.globl  __init__
	.p2align	2
	.type   __init__, @function
__init__:
	jr      ra


