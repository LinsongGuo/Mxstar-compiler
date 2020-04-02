; ModuleID = 'test.txt'
source_filename = "test.txt"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

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
    %a$.0 = alloca i32
    store i32 %a.0, i32* %a$.0
    %p$.0 = alloca i32
    store i32 %p.0, i32* %p$.0
    %mod$.0 = alloca i32
    store i32 %mod.0, i32* %mod$.0
    %returnValue$.0 = alloca i32
    %t$.0 = alloca i32
    store i32 1, i32* %t$.0
    %y$.0 = alloca i32
    %a.1 = load i32, i32* %a$.0
    store i32 %a.1, i32* %y$.0
    br label %whileCondBlock.0

whileCondBlock.0:
    %p.1 = load i32, i32* %p$.0
    %sgt.0 = icmp sgt i32 %p.1, 0
    br i1 %sgt.0, label %whileBodyBlock.0, label %afterWhileBlock.0

whileBodyBlock.0:
    br label %ifCondBlock.0

ifCondBlock.0:
    %p.2 = load i32, i32* %p$.0
    %and.0 = and i32 %p.2, 1
    %eq.0 = icmp eq i32 %and.0, 1
    br i1 %eq.0, label %thenBodyBlock.0, label %afterIfBlock.0

thenBodyBlock.0:
    %t.0 = load i32, i32* %t$.0
    %t.1 = load i32, i32* %t$.0
    %y.0 = load i32, i32* %y$.0
    %mul.0 = mul i32 %t.1, %y.0
    %mod.1 = load i32, i32* %mod$.0
    %srem.0 = srem i32 %mul.0, %mod.1
    store i32 %srem.0, i32* %t$.0
    br label %afterIfBlock.0

afterIfBlock.0:
    %y.1 = load i32, i32* %y$.0
    %y.2 = load i32, i32* %y$.0
    %y.3 = load i32, i32* %y$.0
    %mul.1 = mul i32 %y.2, %y.3
    %mod.2 = load i32, i32* %mod$.0
    %srem.1 = srem i32 %mul.1, %mod.2
    store i32 %srem.1, i32* %y$.0
    %p.3 = load i32, i32* %p$.0
    %p.4 = load i32, i32* %p$.0
    %sdiv.0 = sdiv i32 %p.4, 2
    store i32 %sdiv.0, i32* %p$.0
    br label %whileCondBlock.0

afterWhileBlock.0:
    %t.2 = load i32, i32* %t$.0
    store i32 %t.2, i32* %returnValue$.0
    br label %returnBlock.0
    br label %returnBlock.0

returnBlock.0:
    %returnValue.0 = load i32, i32* %returnValue$.0
    ret i32 %returnValue.0

}

define i32 @main() {
entranceBlock.0:
    call void @__init__()
    %returnValue$.0 = alloca i32
    %call.1 = call i32 @qpow(i32 2, i32 10, i32 10000)
    %call.0 = call i8* @__toString(i32 %call.1)
    call void @__println(i8* %call.0)
    store i32 0, i32* %returnValue$.0
    br label %returnBlock.0
    br label %returnBlock.0

returnBlock.0:
    %returnValue.0 = load i32, i32* %returnValue$.0
    ret i32 %returnValue.0

}

define void @__init__() {
entranceBlock.0:
    br label %returnBlock.0

returnBlock.0:
    ret void

}

