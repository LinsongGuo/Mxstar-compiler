; ModuleID = 'test.txt'
source_filename = "test.txt"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

%class.A = type { i32 }

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

define void @A.A(%class.A* %this.0) {
entranceBlock.0:
    %this$.0 = alloca %class.A*
    store %class.A* %this.0, %class.A** %this$.0
    %this.1 = load %class.A*, %class.A** %this$.0
    %val$.0 = getelementptr %class.A, %class.A* %this.1, i32 0, i32 0
    %val.0 = load i32, i32* %val$.0
    store i32 1, i32* %val$.0
    br label %returnBlock.0

returnBlock.0:
    ret void

}

define i32 @f(%class.A* %a.0) {
entranceBlock.0:
    %a$.0 = alloca %class.A*
    store %class.A* %a.0, %class.A** %a$.0
    %returnValue$.0 = alloca i32
    %a.1 = load %class.A*, %class.A** %a$.0
    %val$.0 = getelementptr %class.A, %class.A* %a.1, i32 0, i32 0
    %val.0 = load i32, i32* %val$.0
    %add.0 = add i32 %val.0, 10
    store i32 %add.0, i32* %returnValue$.0
    br label %returnBlock.0

returnBlock.0:
    %returnValue.0 = load i32, i32* %returnValue$.0
    ret i32 %returnValue.0

}

define i32 @main() {
entranceBlock.0:
    call void @__init__()
    %returnValue$.0 = alloca i32
    %a$.0 = alloca %class.A*
    %malloc8.0 = call i8* @__malloc(i32 4)
    %malloc.0 = bitcast i8* %malloc8.0 to %class.A*
    call void @A.A(%class.A* %malloc.0)
    store %class.A* %malloc.0, %class.A** %a$.0
    %a.0 = load %class.A*, %class.A** %a$.0
    %call.0 = call i32 @f(%class.A* %a.0)
    call void @__printlnInt(i32 %call.0)
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

