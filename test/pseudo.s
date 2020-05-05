	.text

	.globl  qpow
	.p2align	2
	.type   qpow, @function
qpow:
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
	mv      a.0_13,a0
	mv      p.0_14,a1
	mv      mod.0_15,a2
	mv      p.5_16,p.0_14
	li      tmp_18,1
	mv      t.4_17,tmp_18
	mv      y.4_19,a.0_13
	j       qpow_whileCondBlock.0

qpow_whileCondBlock.0:
	li      tmp_20,0
	bgt     p.5_16,tmp_20,qpow_whileBodyBlock.0
	j       qpow_afterWhileBlock.0

qpow_whileBodyBlock.0:
	andi    and.0_21,p.5_16,1
	li      tmp_22,1
	beq     and.0_21,tmp_22,qpow_thenBodyBlock.0
	j       qpow_criticalEdge.0

qpow_afterWhileBlock.0:
	mv      a0,t.4_17
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

qpow_thenBodyBlock.0:
	mul     mul.0_23,t.4_17,y.4_19
	rem     srem.0_24,mul.0_23,mod.0_15
	mv      t.3_25,srem.0_24
	j       qpow_afterIfBlock.0

qpow_afterIfBlock.0:
	mul     mul.1_26,y.4_19,y.4_19
	rem     srem.1_27,mul.1_26,mod.0_15
	li      tmp_29,2
	div     sdiv.0_28,p.5_16,tmp_29
	mv      p.5_16,sdiv.0_28
	mv      t.4_17,t.3_25
	mv      y.4_19,srem.1_27
	j       qpow_whileCondBlock.0

qpow_criticalEdge.0:
	mv      t.3_25,t.4_17
	j       qpow_afterIfBlock.0


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
	li      tmp_13,2
	mv      a0,tmp_13
	li      tmp_14,10
	mv      a1,tmp_14
	li      tmp_15,10000
	mv      a2,tmp_15
	call    qpow
	mv      call.1_16,a0
	mv      a0,call.1_16
	call    __toString
	mv      call.0_17,a0
	mv      a0,call.0_17
	call    __println
	li      tmp_18,0
	mv      a0,tmp_18
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


