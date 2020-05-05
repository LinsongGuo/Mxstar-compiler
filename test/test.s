	.text

	.globl  qpow
	.p2align	2
	.type   qpow, @function
qpow:
	li      t6,1
	j       qpow_whileCondBlock.0

qpow_whileCondBlock.0:
	li      t2,0
	bgt     a1,t2,qpow_whileBodyBlock.0
	j       qpow_afterWhileBlock.0

qpow_whileBodyBlock.0:
	andi    t2,a1,1
	li      t0,1
	beq     t2,t0,qpow_thenBodyBlock.0
	j       qpow_criticalEdge.0

qpow_afterWhileBlock.0:
	mv      a0,t6
	jr      ra

qpow_thenBodyBlock.0:
	mul     t6,t6,a0
	rem     t6,t6,a2
	j       qpow_afterIfBlock.0

qpow_afterIfBlock.0:
	mul     t2,a0,a0
	rem     a0,t2,a2
	li      t2,2
	div     a1,a1,t2
	j       qpow_whileCondBlock.0

qpow_criticalEdge.0:
	j       qpow_afterIfBlock.0


	.globl  main
	.p2align	2
	.type   main, @function
main:
	addi    sp,sp,-16
	sw      ra,12(sp)
	call    __init__
	li      a0,2
	li      a1,10
	li      a2,10000
	call    qpow
	call    __toString
	call    __println
	li      a0,0
	lw      ra,12(sp)
	addi    sp,sp,16
	jr      ra


	.globl  __init__
	.p2align	2
	.type   __init__, @function
__init__:
	jr      ra


