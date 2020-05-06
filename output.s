	.data

	.globl  a
	.p2align	2
a:
	.word   2333


	.text

	.globl  main
	.p2align	2
	.type   main, @function
main:
	addi    sp,sp,-16
	sw      ra,12(sp)
	call    __init__
	lui     t0,%hi(a)
	lw      a0,%lo(a)(t0)
	call    __printInt
	li      a0,0
	lw      ra,12(sp)
	addi    sp,sp,16
	jr      ra


	.globl  __init__
	.p2align	2
	.type   __init__, @function
__init__:
	jr      ra


