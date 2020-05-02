	.text

	.globl	main					 # -- Begin function main
	.p2align	2
	.type	main,@function
main:
#entry_0:
	addi	sp, sp, -16
	sw	ra,0(sp)
	la	a0, .str
	mv	a1, a0
	call	__stringAdd
	call	println
	mv	a0, zero
	lw	ra, 0(sp)
	addi	sp, sp, 16
	ret 
						 # -- End function
	.section	.sdata,"aw",@progbits
	.globl	s					#@s
	.p2align	2
s:
	.word	0
	.globl	.str					#@.str
.str:
	.asciz	"abcd"
