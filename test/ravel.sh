riscv32-unknown-elf-gcc -S -std=c99 -fno-section-anchors -march=rv32ima -mabi=ilp32 test.c

riscv32-unknown-elf-gcc -S -std=c99 -fno-section-anchors -march=rv32ima -mabi=ilp32 builtin.c

ravel --input-file=test.in --output-file=test.out test.s builtin.s 
