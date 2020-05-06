	.text

	.globl  f
	.p2align	2
	.type   f, @function
f:
	mv      ra_0,ra
	mv      calleeSaved_1,s0
	mv      calleeSaved_2,s1
	mv      calleeSaved_3,s2
	mv      calleeSaved_4,s3
	mv      calleeSaved_5,s4
	mv      calleeSaved_6,s5
	mv      calleeSaved_7,s6
	mv      calleeSaved_8,s7
	mv      calleeSaved_9,s8
	mv      calleeSaved_10,s9
	mv      calleeSaved_11,s10
	mv      calleeSaved_12,s11
	mv      x.0_13,a0
	li      tmp_14,0
	mv      a0,tmp_14
	mv      s11,calleeSaved_12
	mv      s10,calleeSaved_11
	mv      s9,calleeSaved_10
	mv      s8,calleeSaved_9
	mv      s7,calleeSaved_8
	mv      s6,calleeSaved_7
	mv      s5,calleeSaved_6
	mv      s4,calleeSaved_5
	mv      s3,calleeSaved_4
	mv      s2,calleeSaved_3
	mv      s1,calleeSaved_2
	mv      s0,calleeSaved_1
	mv      ra,ra_0
	jr      ra


	.globl  main
	.p2align	2
	.type   main, @function
main:
	mv      ra_0,ra
	mv      calleeSaved_1,s0
	mv      calleeSaved_2,s1
	mv      calleeSaved_3,s2
	mv      calleeSaved_4,s3
	mv      calleeSaved_5,s4
	mv      calleeSaved_6,s5
	mv      calleeSaved_7,s6
	mv      calleeSaved_8,s7
	mv      calleeSaved_9,s8
	mv      calleeSaved_10,s9
	mv      calleeSaved_11,s10
	mv      calleeSaved_12,s11
	call    __init__
	call    __getInt
	mv      call.0_13,a0
	mv      a0,call.0_13
	call    f
	mv      call.1_14,a0
	mv      a0,call.1_14
	call    __printInt
	li      tmp_15,0
	mv      a0,tmp_15
	mv      s11,calleeSaved_12
	mv      s10,calleeSaved_11
	mv      s9,calleeSaved_10
	mv      s8,calleeSaved_9
	mv      s7,calleeSaved_8
	mv      s6,calleeSaved_7
	mv      s5,calleeSaved_6
	mv      s4,calleeSaved_5
	mv      s3,calleeSaved_4
	mv      s2,calleeSaved_3
	mv      s1,calleeSaved_2
	mv      s0,calleeSaved_1
	mv      ra,ra_0
	jr      ra


	.globl  __init__
	.p2align	2
	.type   __init__, @function
__init__:
	mv      ra_0,ra
	mv      calleeSaved_1,s0
	mv      calleeSaved_2,s1
	mv      calleeSaved_3,s2
	mv      calleeSaved_4,s3
	mv      calleeSaved_5,s4
	mv      calleeSaved_6,s5
	mv      calleeSaved_7,s6
	mv      calleeSaved_8,s7
	mv      calleeSaved_9,s8
	mv      calleeSaved_10,s9
	mv      calleeSaved_11,s10
	mv      calleeSaved_12,s11
	mv      s11,calleeSaved_12
	mv      s10,calleeSaved_11
	mv      s9,calleeSaved_10
	mv      s8,calleeSaved_9
	mv      s7,calleeSaved_8
	mv      s6,calleeSaved_7
	mv      s5,calleeSaved_6
	mv      s4,calleeSaved_5
	mv      s3,calleeSaved_4
	mv      s2,calleeSaved_3
	mv      s1,calleeSaved_2
	mv      s0,calleeSaved_1
	mv      ra,ra_0
	jr      ra


