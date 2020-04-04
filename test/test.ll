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

define i32 @gcd(i32 %x.0, i32 %y.0) {
entranceBlock.0:
    %x$.0 = alloca i32
    store i32 %x.0, i32* %x$.0
    %y$.0 = alloca i32
    store i32 %y.0, i32* %y$.0
    %returnValue$.0 = alloca i32
    br label %ifCondBlock.0

ifCondBlock.0:
    %x.1 = load i32, i32* %x$.0
    %y.1 = load i32, i32* %y$.0
    %srem.0 = srem i32 %x.1, %y.1
    %eq.0 = icmp eq i32 %srem.0, 0
    br i1 %eq.0, label %thenBodyBlock.0, label %elseBodyBlock.0

thenBodyBlock.0:
    %y.2 = load i32, i32* %y$.0
    store i32 %y.2, i32* %returnValue$.0
    br label %returnBlock.0
    br label %afterIfBlock.0

elseBodyBlock.0:
    %y.3 = load i32, i32* %y$.0
    %x.2 = load i32, i32* %x$.0
    %y.4 = load i32, i32* %y$.0
    %srem.1 = srem i32 %x.2, %y.4
    %call.0 = call i32 @gcd(i32 %y.3, i32 %srem.1)
    store i32 %call.0, i32* %returnValue$.0
    br label %returnBlock.0
    br label %afterIfBlock.0

afterIfBlock.0:
    br label %returnBlock.0

returnBlock.0:
    %returnValue.0 = load i32, i32* %returnValue$.0
    ret i32 %returnValue.0

}

define i32 @main() {
entranceBlock.0:
    call void @__init__()
    %returnValue$.0 = alloca i32
    %call.1 = call i32 @gcd(i32 10, i32 1)
    %call.0 = call i8* @__toString(i32 %call.1)
    call void @__println(i8* %call.0)
    %call.3 = call i32 @gcd(i32 34986, i32 3087)
    %call.2 = call i8* @__toString(i32 %call.3)
    call void @__println(i8* %call.2)
    %call.5 = call i32 @gcd(i32 2907, i32 1539)
    %call.4 = call i8* @__toString(i32 %call.5)
    call void @__println(i8* %call.4)
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

