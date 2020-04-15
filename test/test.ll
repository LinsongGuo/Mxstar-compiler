; ModuleID = 'test.txt'
source_filename = "test.txt"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

%class.TA = type { i8*, i32 }

@.str.0 = private unnamed_addr constant [3 x i8] c", \00"
@.str.1 = private unnamed_addr constant [22 x i8] c" enjoys this work. XD\00"
@.str.2 = private unnamed_addr constant [23 x i8] c" wants to give up!!!!!\00"
@.str.3 = private unnamed_addr constant [15 x i8] c"the leading TA\00"
@.str.4 = private unnamed_addr constant [16 x i8] c"the striking TA\00"
@.str.5 = private unnamed_addr constant [3 x i8] c"MR\00"
@.str.6 = private unnamed_addr constant [5 x i8] c"Mars\00"

@init_anger = global i32 100
@work_anger = global i32 10

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

define void @work(i8* %st.0, %class.TA* %ta.0) {
entranceBlock.0:
    br label %ifCondBlock.0

ifCondBlock.0:
    %anger$.0 = getelementptr %class.TA, %class.TA* %ta.0, i32 0, i32 1
    %anger.0 = load i32, i32* %anger$.0
    %sle.0 = icmp sle i32 %anger.0, 100
    br i1 %sle.0, label %thenBodyBlock.0, label %elseBodyBlock.0

thenBodyBlock.0:
    %stringLiteral.0 = getelementptr [3 x i8], [3 x i8]* @.str.0, i32 0, i32 0
    %add.0 = call i8* @__stringAdd(i8* %st.0, i8* %stringLiteral.0)
    %state$.0 = getelementptr %class.TA, %class.TA* %ta.0, i32 0, i32 0
    %state.0 = load i8*, i8** %state$.0
    %add.1 = call i8* @__stringAdd(i8* %add.0, i8* %state.0)
    %stringLiteral.1 = getelementptr [22 x i8], [22 x i8]* @.str.1, i32 0, i32 0
    %add.2 = call i8* @__stringAdd(i8* %add.1, i8* %stringLiteral.1)
    call void @__println(i8* %add.2)
    br label %afterIfBlock.0

elseBodyBlock.0:
    %stringLiteral.2 = getelementptr [3 x i8], [3 x i8]* @.str.0, i32 0, i32 0
    %add.3 = call i8* @__stringAdd(i8* %st.0, i8* %stringLiteral.2)
    %state$.1 = getelementptr %class.TA, %class.TA* %ta.0, i32 0, i32 0
    %state.1 = load i8*, i8** %state$.1
    %add.4 = call i8* @__stringAdd(i8* %add.3, i8* %state.1)
    %stringLiteral.3 = getelementptr [23 x i8], [23 x i8]* @.str.2, i32 0, i32 0
    %add.5 = call i8* @__stringAdd(i8* %add.4, i8* %stringLiteral.3)
    call void @__println(i8* %add.5)
    br label %afterIfBlock.0

afterIfBlock.0:
    %anger$.1 = getelementptr %class.TA, %class.TA* %ta.0, i32 0, i32 1
    %anger$.2 = getelementptr %class.TA, %class.TA* %ta.0, i32 0, i32 1
    %anger.2 = load i32, i32* %anger$.2
    %work_anger.0 = load i32, i32* @work_anger
    %add.6 = add i32 %anger.2, %work_anger.0
    store i32 %add.6, i32* %anger$.1
    br label %returnBlock.0

returnBlock.0:
    ret void

}

define i32 @main() {
entranceBlock.0:
    call void @__init__()
    %malloc8.0 = call i8* @__malloc(i32 12)
    %malloc.0 = bitcast i8* %malloc8.0 to %class.TA*
    %state$.0 = getelementptr %class.TA, %class.TA* %malloc.0, i32 0, i32 0
    %stringLiteral.0 = getelementptr [15 x i8], [15 x i8]* @.str.3, i32 0, i32 0
    store i8* %stringLiteral.0, i8** %state$.0
    %anger$.0 = getelementptr %class.TA, %class.TA* %malloc.0, i32 0, i32 1
    store i32 0, i32* %anger$.0
    %malloc8.1 = call i8* @__malloc(i32 12)
    %malloc.1 = bitcast i8* %malloc8.1 to %class.TA*
    %state$.1 = getelementptr %class.TA, %class.TA* %malloc.1, i32 0, i32 0
    %stringLiteral.1 = getelementptr [16 x i8], [16 x i8]* @.str.4, i32 0, i32 0
    store i8* %stringLiteral.1, i8** %state$.1
    %anger$.1 = getelementptr %class.TA, %class.TA* %malloc.1, i32 0, i32 1
    %init_anger.0 = load i32, i32* @init_anger
    store i32 %init_anger.0, i32* %anger$.1
    %stringLiteral.2 = getelementptr [3 x i8], [3 x i8]* @.str.5, i32 0, i32 0
    call void @work(i8* %stringLiteral.2, %class.TA* %malloc.0)
    %stringLiteral.3 = getelementptr [5 x i8], [5 x i8]* @.str.6, i32 0, i32 0
    call void @work(i8* %stringLiteral.3, %class.TA* %malloc.1)
    %stringLiteral.4 = getelementptr [5 x i8], [5 x i8]* @.str.6, i32 0, i32 0
    call void @work(i8* %stringLiteral.4, %class.TA* %malloc.1)
    br label %returnBlock.0

returnBlock.0:
    ret i32 0

}

define void @__init__() {
entranceBlock.0:
    br label %returnBlock.0

returnBlock.0:
    ret void

}

