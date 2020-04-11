; ModuleID = 'test.txt'
source_filename = "test.txt"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@a = global i32* null
@m = global i32 0
@k = global i32 0
@i = global i32 0

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
    store i32 %call.0, i32* @m
    %call.1 = call i32 @__getInt()
    store i32 %call.1, i32* @k
    store i32 0, i32* @i
    br label %forCondBlock.0

forCondBlock.0:
    %i.1 = load i32, i32* @i
    %m.1 = load i32, i32* @m
    %slt.0 = icmp slt i32 %i.1, %m.1
    br i1 %slt.0, label %forBodyBlock.0, label %afterForBlock.0

forBodyBlock.0:
    %a.0 = load i32*, i32** @a
    %i.2 = load i32, i32* @i
    %element$.0 = getelementptr i32, i32* %a.0, i32 %i.2
    %call.2 = call i32 @__getInt()
    store i32 %call.2, i32* %element$.0
    br label %forStepBlock.0

forStepBlock.0:
    %i.3 = load i32, i32* @i
    %suffixIncr.0 = add i32 %i.3, 1
    store i32 %suffixIncr.0, i32* @i
    br label %forCondBlock.0

afterForBlock.0:
    store i32 0, i32* @i
    br label %forCondBlock.1

forCondBlock.1:
    %a.1 = load i32*, i32** @a
    %i.5 = load i32, i32* @i
    %element$.1 = getelementptr i32, i32* %a.1, i32 %i.5
    %element.1 = load i32, i32* %element$.1
    %a.2 = load i32*, i32** @a
    %k.1 = load i32, i32* @k
    %sub.0 = sub i32 %k.1, 1
    %element$.2 = getelementptr i32, i32* %a.2, i32 %sub.0
    %element.2 = load i32, i32* %element$.2
    %sge.0 = icmp sge i32 %element.1, %element.2
    br i1 %sge.0, label %logicalAnd.0, label %afterLogicalAnd.0

forBodyBlock.1:
    br label %forStepBlock.1

forStepBlock.1:
    %i.8 = load i32, i32* @i
    %suffixIncr.1 = add i32 %i.8, 1
    store i32 %suffixIncr.1, i32* @i
    br label %forCondBlock.1

afterForBlock.1:
    %i.9 = load i32, i32* @i
    call void @__printInt(i32 %i.9)
    br label %returnBlock.0

logicalAnd.0:
    %a.3 = load i32*, i32** @a
    %i.6 = load i32, i32* @i
    %element$.3 = getelementptr i32, i32* %a.3, i32 %i.6
    %element.3 = load i32, i32* %element$.3
    %sgt.0 = icmp sgt i32 %element.3, 0
    br label %afterLogicalAnd.0

afterLogicalAnd.0:
    %phi.0 = phi i1  [ false, %forCondBlock.1 ],  [ %sgt.0, %logicalAnd.0 ]
    br i1 %phi.0, label %logicalAnd.1, label %afterLogicalAnd.1

logicalAnd.1:
    %i.7 = load i32, i32* @i
    %m.2 = load i32, i32* @m
    %slt.1 = icmp slt i32 %i.7, %m.2
    br label %afterLogicalAnd.1

afterLogicalAnd.1:
    %phi.1 = phi i1  [ false, %afterLogicalAnd.0 ],  [ %slt.1, %logicalAnd.1 ]
    br i1 %phi.1, label %forBodyBlock.1, label %afterForBlock.1

returnBlock.0:
    ret i32 0

}

define void @__init__() {
entranceBlock.0:
    %mul.0 = mul i32 4, 50
    %add.0 = add i32 %mul.0, 4
    %malloc8.0 = call i8* @__malloc(i32 %add.0)
    %malloc32.0 = bitcast i8* %malloc8.0 to i32*
    store i32 50, i32* %malloc32.0
    %arrayHead32.0 = getelementptr i32, i32* %malloc32.0, i32 1
    store i32* %arrayHead32.0, i32** @a
    br label %returnBlock.0

returnBlock.0:
    ret void

}

