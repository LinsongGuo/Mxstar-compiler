; ModuleID = 'test.txt'
source_filename = "test.txt"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

%class.A = type { i32, i32 }

@a = global %class.A* null

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
    %a.0 = load %class.A*, %class.A** @a
    %eq.0 = icmp eq %class.A* %a.0, null
    br i1 %eq.0, label %thenBodyBlock.0, label %afterIfBlock.0

thenBodyBlock.0:
    %a.1 = load %class.A*, %class.A** @a
    %x$.0 = getelementptr %class.A, %class.A* %a.1, i32 0, i32 0
    %x.0 = load i32, i32* %x$.0
    call void @__printInt(i32 %x.0)
    br label %afterIfBlock.0

afterIfBlock.0:
    ret i32 0

}

define void @__init__() {
entranceBlock.0:
    ret void

}

