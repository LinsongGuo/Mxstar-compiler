	.data

	.globl  .str.0
.str.0:
	.asciz  "1234"

	.globl  a
	.p2align	2
a:
	.word   0

	.globl  b
	.p2align	2
b:
	.word   10

	.globl  c
	.p2align	2
c:
	.word   .str.0

	.globl  d
	.p2align	2
d:
	.word   0

	.text

	.globl  main
	.p2align	2
main:
.entranceBlock.0:
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
	call    __init__
	call    __getInt
	mv      call.0_12,a0
	li      tmp_14,1000
	div     sdiv.0_13,call.0_12,tmp_14
	mv      a0,sdiv.0_13
	call    __printInt
	li      tmp_15,0
	mv      ra,tmp_15
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
	jr      ra

	.globl  __init__
	.p2align	2
__init__:
.entranceBlock.0:
	mv      calleeSaved_16,s0
	mv      calleeSaved_17,s1
	mv      calleeSaved_18,s2
	mv      calleeSaved_19,s3
	mv      calleeSaved_20,s4
	mv      calleeSaved_21,s5
	mv      calleeSaved_22,s6
	mv      calleeSaved_23,s7
	mv      calleeSaved_24,s8
	mv      calleeSaved_25,s9
	mv      calleeSaved_26,s10
	mv      calleeSaved_27,s11
	mv      s0,calleeSaved_16
	mv      s1,calleeSaved_17
	mv      s2,calleeSaved_18
	mv      s3,calleeSaved_19
	mv      s4,calleeSaved_20
	mv      s5,calleeSaved_21
	mv      s6,calleeSaved_22
	mv      s7,calleeSaved_23
	mv      s8,calleeSaved_24
	mv      s9,calleeSaved_25
	mv      s10,calleeSaved_26
	mv      s11,calleeSaved_27
	jr      ra

