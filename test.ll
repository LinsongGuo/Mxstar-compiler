; ModuleID = 'test.txt'
source_filename = "test.txt"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

%class.A = type { i32 }
%class.B = type { %class.A*, i32 }

@.str.0 = private unnamed_addr constant [18 x i8] c"sfajasifjdsahdska\00"

@s1 = global i8* null

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

define void @__init__() {
entranceBlock.0:
    %stringLiteral.0 = getelementptr [18 x i8], [18 x i8]* @.str.0, i32 0, i32 0
    store i8* %stringLiteral.0, i8** @s1
    br label %returnBlock.0

returnBlock.0:
    ret void

}

define void @A.A(%class.A* %this.0) {
entranceBlock.0:
    %this$.0 = alloca %class.A*
    store %class.A* %this.0, %class.A** %this$.0
    %this.1 = load %class.A*, %class.A** %this$.0
    %value$.0 = getelementptr %class.A, %class.A* %this.1, i32 0, i32 0
    %value.0 = load i32, i32* %value$.0
    store i32 1, i32* %value$.0
    br label %returnBlock.0

returnBlock.0:
    ret void

}

define i32 @B.f(%class.B* %this.0, i32 %x.0) {
entranceBlock.0:
    %returnValue$.0 = alloca i32
    %this$.0 = alloca %class.B*
    store %class.B* %this.0, %class.B** %this$.0
    %x$.0 = alloca i32
    store i32 %x.0, i32* %x$.0
    %x.1 = load i32, i32* %x$.0
    store i32 %x.1, i32* %returnValue$.0
    br label %returnBlock.0

returnBlock.0:
    %returnValue.0 = load i32, i32* %returnValue$.0
    ret i32 %returnValue.0

}

define void @B.g(%class.B* %this.0, i32 %x.0) {
entranceBlock.0:
    %this$.0 = alloca %class.B*
    store %class.B* %this.0, %class.B** %this$.0
    %x$.0 = alloca i32
    store i32 %x.0, i32* %x$.0
    %this.1 = load %class.B*, %class.B** %this$.0
    %value$.0 = getelementptr %class.B, %class.B* %this.1, i32 0, i32 1
    %value.0 = load i32, i32* %value$.0
    %this.2 = load %class.B*, %class.B** %this$.0
    %x.1 = load i32, i32* %x$.0
    %call.0 = call i32 @B.f(%class.B* %this.2, i32 %x.1)
    store i32 %call.0, i32* %value$.0
    br label %returnBlock.0

returnBlock.0:
    ret void

}

define void @B.B(%class.B* %this.0) {
entranceBlock.0:
    %this$.0 = alloca %class.B*
    store %class.B* %this.0, %class.B** %this$.0
    %b$.0 = alloca %class.A*
    %this.1 = load %class.B*, %class.B** %this$.0
    %a$.0 = getelementptr %class.B, %class.B* %this.1, i32 0, i32 0
    %a.0 = load %class.A*, %class.A** %a$.0
    %b.0 = load %class.A*, %class.A** %b$.0
    store %class.A* %b.0, %class.A** %a$.0
    %this.2 = load %class.B*, %class.B** %this$.0
    %value$.0 = getelementptr %class.B, %class.B* %this.2, i32 0, i32 1
    %value.0 = load i32, i32* %value$.0
    %suffixIncr.0 = add i32 %value.0, 1
    store i32 %suffixIncr.0, i32* %value$.0
    br label %returnBlock.0

returnBlock.0:
    ret void

}

define void @dfs(i32 %x.0) {
entranceBlock.0:
    %x$.0 = alloca i32
    store i32 %x.0, i32* %x$.0
    %x.1 = load i32, i32* %x$.0
    %add.0 = add i32 %x.1, 1
    call void @dfs(i32 %add.0)
    br label %returnBlock.0

returnBlock.0:
    ret void

}

define i32 @main() {
entranceBlock.0:
    call void @__init__()
    %returnValue$.0 = alloca i32
    %i$.0 = alloca i32
    store i32 0, i32* %i$.0
    %n$.0 = alloca i32
    %n.0 = load i32, i32* %n$.0
    %prefixIncr.0 = add i32 %n.0, 1
    store i32 %prefixIncr.0, i32* %n$.0
    %i.0 = load i32, i32* %i$.0
    store i32 0, i32* %i$.0
    br label %forCondBlock.0

forCondBlock.0:
    %i.1 = load i32, i32* %i$.0
    %slt.0 = icmp slt i32 %i.1, 10
    br i1 %slt.0, label %forBodyBlock.0, label %afterForBlock.0

forBodyBlock.0:
    %n$.1 = alloca i32
    store i32 0, i32* %n$.1
    %n.1 = load i32, i32* %n$.1
    %prefixIncr.1 = add i32 %n.1, 1
    store i32 %prefixIncr.1, i32* %n$.1
    br label %forStepBlock.0

forStepBlock.0:
    %i.2 = load i32, i32* %i$.0
    %prefixIncr.2 = add i32 %i.2, 1
    store i32 %prefixIncr.2, i32* %i$.0
    br label %forCondBlock.0

afterForBlock.0:
    store i32 0, i32* %returnValue$.0
    br label %returnBlock.0

returnBlock.0:
    %returnValue.0 = load i32, i32* %returnValue$.0
    ret i32 %returnValue.0

}

