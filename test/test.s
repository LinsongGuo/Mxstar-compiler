	.text

	.globl  f
	.p2align	2
	.type   f, @function
f:
	mv      calleeSaved_0,s0
	mv      calleeSaved_1,s1
	mv      calleeSaved_2,s2
	mv      calleeSaved_3,s3
	mv      calleeSaved_4,s4
	mv      calleeSaved_5,s5
	mv      calleeSaved_6,s6
	mv      calleeSaved_7,s7
	mv      calleeSaved_8,s8
	mv      calleeSaved_9,s9
	mv      calleeSaved_10,s10
	mv      calleeSaved_11,s11
	mv      ra_12,ra
	mv      x.0_13,a0
	mv      y.0_14,a1
	mv      a0,x.0_13
	mv      a1,y.0_14
	call    f
	mv      call.0_15,a0
	mv      a0,call.0_15
	mv      s0,calleeSaved_0
	mv      s1,calleeSaved_1
	mv      s2,calleeSaved_2
	mv      s3,calleeSaved_3
	mv      s4,calleeSaved_4
	mv      s5,calleeSaved_5
	mv      s6,calleeSaved_6
	mv      s7,calleeSaved_7
	mv      s8,calleeSaved_8
	mv      s9,calleeSaved_9
	mv      s10,calleeSaved_10
	mv      s11,calleeSaved_11
	mv      ra,ra_12
	jr      ra

	.globl  main
	.p2align	2
	.type   main, @function
main:
	addi    sp,sp,-16
	mv      spill_19,ra
	sw      spill_19,8(sp)
	call    __init__
	call    __getInt
	mv      spill_17,a0
	sw      spill_17,12(sp)
	call    __getInt
	mv      a1,a0
	lw      spill_18,12(sp)
	mv      a0,spill_18
	call    f
	call    __printInt
	li      a0,0
	lw      spill_20,8(sp)
	mv      ra,spill_20
	addi    sp,sp,16
	jr      ra

	.globl  __init__
	.p2align	2
	.type   __init__, @function
__init__:
	jr      ra

