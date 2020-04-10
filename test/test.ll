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

define i32 @main() {
entranceBlock.0:
    call void @__init__()
    %returnValue$.0 = alloca i32
    %i$.0 = alloca i32
    %n$.0 = alloca i32
    %call.0 = call i32 @__getInt()
    store i32 %call.0, i32* %n$.0
    %s$.0 = alloca i32
    store i32 0, i32* %s$.0
    %i.0 = load i32, i32* %i$.0
    store i32 1, i32* %i$.0
    br label %forCondBlock.0

forCondBlock.0:
    %i.1 = load i32, i32* %i$.0
    %n.0 = load i32, i32* %n$.0
    %sle.0 = icmp sle i32 %i.1, %n.0
    br i1 %sle.0, label %forBodyBlock.0, label %afterForBlock.0

forBodyBlock.0:
    %s.0 = load i32, i32* %s$.0
    %s.1 = load i32, i32* %s$.0
    %i.2 = load i32, i32* %i$.0
    %add.0 = add i32 %s.1, %i.2
    store i32 %add.0, i32* %s$.0
    br label %forStepBlock.0

forStepBlock.0:
    %i.3 = load i32, i32* %i$.0
    %prefixIncr.0 = add i32 %i.3, 1
    store i32 %prefixIncr.0, i32* %i$.0
    br label %forCondBlock.0

afterForBlock.0:
    %s.2 = load i32, i32* %s$.0
    call void @__printlnInt(i32 %s.2)
    store i32 0, i32* %returnValue$.0
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

