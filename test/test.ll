; ModuleID = 'test.txt'
source_filename = "test.txt"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@.str.0 = private unnamed_addr constant [5 x i8] c"1234\00"

@a = global i32 0
@b = global i32 10
@c = global i8* null
@d = global i8* null

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
    %call.0 = call i32 @__getInt()
    %sdiv.0 = sdiv i32 %call.0, 1000
    call void @__printInt(i32 %sdiv.0)
    ret i32 0

}

define void @__init__() {
entranceBlock.0:
    %__stringLiteral.0 = getelementptr [5 x i8], [5 x i8]* @.str.0, i32 0, i32 0
    store i8* %__stringLiteral.0, i8** @c
    ret void

}

