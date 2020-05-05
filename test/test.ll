; ModuleID = 'test.txt'
source_filename = "test.txt"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

declare i8* @__malloc(i32 %n)
declare void @__print(i8* %str)
declare void @__println(i8* %str)
declare void @__printInt(i32 %n)
declare void @__printlnInt(i32 %n)
declare i8* @__getString()
declare i32 @__getInt()
declare i8* @__toString(i32 %i)
declare i32 @__stringLength(i8* %str)
declare i8* @__stringSubstring(i8* %str, i32 %l, i32 %r)
declare i32 @__stringParseInt(i8* %str)
declare i32 @__stringOrd(i8* %str, i32 %pos)
declare i8* @__stringAdd(i8* %str1, i8* %str2)
declare i1 @__stringEqual(i8* %str1, i8* %str2)
declare i1 @__stringNotEqual(i8* %str1, i8* %str2)
declare i1 @__stringLess(i8* %str1, i8* %str2)
declare i1 @__stringLessEqual(i8* %str1, i8* %str2)
declare i1 @__stringGreater(i8* %str1, i8* %str2)
declare i1 @__stringGreaterEqual(i8* %str1, i8* %str2)

define i32 @qpow(i32 %a.0, i32 %p.0, i32 %mod.0) {
entranceBlock.0:
    move %p.5, %p.0
    move %t.4, 1
    move %y.4, %a.0
    br label %whileCondBlock.0

whileCondBlock.0:
    %sgt.0 = icmp sgt i32 %p.5, 0
    br i1 %sgt.0, label %whileBodyBlock.0, label %afterWhileBlock.0

whileBodyBlock.0:
    %and.0 = and i32 %p.5, 1
    %eq.0 = icmp eq i32 %and.0, 1
    br i1 %eq.0, label %thenBodyBlock.0, label %criticalEdge.0

afterWhileBlock.0:
    ret i32 %t.4

thenBodyBlock.0:
    %mul.0 = mul i32 %t.4, %y.4
    %srem.0 = srem i32 %mul.0, %mod.0
    move %t.3, %srem.0
    br label %afterIfBlock.0

afterIfBlock.0:
    %mul.1 = mul i32 %y.4, %y.4
    %srem.1 = srem i32 %mul.1, %mod.0
    %sdiv.0 = sdiv i32 %p.5, 2
    move %p.5, %sdiv.0
    move %t.4, %t.3
    move %y.4, %srem.1
    br label %whileCondBlock.0

criticalEdge.0:
    move %t.3, %t.4
    br label %afterIfBlock.0

}

define i32 @main() {
entranceBlock.0:
    call void @__init__()
    %call.1 = call i32 @qpow(i32 2, i32 10, i32 10000)
    %call.0 = call i8* @__toString(i32 %call.1)
    call void @__println(i8* %call.0)
    ret i32 0

}

define void @__init__() {
entranceBlock.0:
    ret void

}

