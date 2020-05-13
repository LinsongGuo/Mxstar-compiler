; ModuleID = 'test.txt'
source_filename = "test.txt"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

%class.point = type { i32, i32, i32 }

@.str.0 = private unnamed_addr constant [2 x i8] c"(\00"
@.str.1 = private unnamed_addr constant [3 x i8] c", \00"
@.str.2 = private unnamed_addr constant [2 x i8] c")\00"

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

define %class.point* @point.cross(%class.point* %this.0, %class.point* %other.0) {
entranceBlock.0:
    %malloc8.0 = call i8* @__malloc(i32 12)
    %malloc.0 = bitcast i8* %malloc8.0 to %class.point*
    %x$.4 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    store i32 0, i32* %x$.4
    %y$.4 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    store i32 0, i32* %y$.4
    %z$.4 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    store i32 0, i32* %z$.4
    %y$.0 = getelementptr %class.point, %class.point* %this.0, i32 0, i32 1
    %y.0 = load i32, i32* %y$.0
    %z$.0 = getelementptr %class.point, %class.point* %other.0, i32 0, i32 2
    %z.0 = load i32, i32* %z$.0
    %mul.0 = mul i32 %y.0, %z.0
    %z$.1 = getelementptr %class.point, %class.point* %this.0, i32 0, i32 2
    %z.1 = load i32, i32* %z$.1
    %y$.1 = getelementptr %class.point, %class.point* %other.0, i32 0, i32 1
    %y.1 = load i32, i32* %y$.1
    %mul.1 = mul i32 %z.1, %y.1
    %sub.0 = sub i32 %mul.0, %mul.1
    %z$.2 = getelementptr %class.point, %class.point* %this.0, i32 0, i32 2
    %z.2 = load i32, i32* %z$.2
    %x$.0 = getelementptr %class.point, %class.point* %other.0, i32 0, i32 0
    %x.0 = load i32, i32* %x$.0
    %mul.2 = mul i32 %z.2, %x.0
    %x$.1 = getelementptr %class.point, %class.point* %this.0, i32 0, i32 0
    %x.1 = load i32, i32* %x$.1
    %z$.3 = getelementptr %class.point, %class.point* %other.0, i32 0, i32 2
    %z.3 = load i32, i32* %z$.3
    %mul.3 = mul i32 %x.1, %z.3
    %sub.1 = sub i32 %mul.2, %mul.3
    %x$.2 = getelementptr %class.point, %class.point* %this.0, i32 0, i32 0
    %x.2 = load i32, i32* %x$.2
    %y$.2 = getelementptr %class.point, %class.point* %other.0, i32 0, i32 1
    %y.2 = load i32, i32* %y$.2
    %mul.4 = mul i32 %x.2, %y.2
    %y$.3 = getelementptr %class.point, %class.point* %this.0, i32 0, i32 1
    %y.3 = load i32, i32* %y$.3
    %x$.3 = getelementptr %class.point, %class.point* %other.0, i32 0, i32 0
    %x.3 = load i32, i32* %x$.3
    %mul.5 = mul i32 %y.3, %x.3
    %sub.2 = sub i32 %mul.4, %mul.5
    %x$.5 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    store i32 %sub.0, i32* %x$.5
    %y$.5 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    store i32 %sub.1, i32* %y$.5
    %z$.5 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    store i32 %sub.2, i32* %z$.5
    ret %class.point* %malloc.0

}

define i32 @main() {
entranceBlock.0:
    %malloc8.0 = call i8* @__malloc(i32 12)
    %malloc.0 = bitcast i8* %malloc8.0 to %class.point*
    %x$.0 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    store i32 0, i32* %x$.0
    %y$.0 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    store i32 0, i32* %y$.0
    %z$.0 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    store i32 0, i32* %z$.0
    %malloc8.1 = call i8* @__malloc(i32 12)
    %malloc.1 = bitcast i8* %malloc8.1 to %class.point*
    %x$.1 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    store i32 0, i32* %x$.1
    %y$.1 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    store i32 0, i32* %y$.1
    %z$.1 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    store i32 0, i32* %z$.1
    %malloc8.2 = call i8* @__malloc(i32 12)
    %malloc.2 = bitcast i8* %malloc8.2 to %class.point*
    %x$.2 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    store i32 0, i32* %x$.2
    %y$.2 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    store i32 0, i32* %y$.2
    %z$.2 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    store i32 0, i32* %z$.2
    %malloc8.3 = call i8* @__malloc(i32 12)
    %malloc.3 = bitcast i8* %malloc8.3 to %class.point*
    %x$.3 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 0
    store i32 0, i32* %x$.3
    %y$.3 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 1
    store i32 0, i32* %y$.3
    %z$.3 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 2
    store i32 0, i32* %z$.3
    %__stringLiteral.0 = getelementptr [2 x i8], [2 x i8]* @.str.0, i32 0, i32 0
    %x$.4 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x.4 = load i32, i32* %x$.4
    %call.24 = call i8* @__toString(i32 %x.4)
    %add.0 = call i8* @__stringAdd(i8* %__stringLiteral.0, i8* %call.24)
    %__stringLiteral.1 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.1 = call i8* @__stringAdd(i8* %add.0, i8* %__stringLiteral.1)
    %y$.4 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y.4 = load i32, i32* %y$.4
    %call.25 = call i8* @__toString(i32 %y.4)
    %add.2 = call i8* @__stringAdd(i8* %add.1, i8* %call.25)
    %__stringLiteral.2 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.3 = call i8* @__stringAdd(i8* %add.2, i8* %__stringLiteral.2)
    %z$.4 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z.4 = load i32, i32* %z$.4
    %call.26 = call i8* @__toString(i32 %z.4)
    %add.4 = call i8* @__stringAdd(i8* %add.3, i8* %call.26)
    %__stringLiteral.3 = getelementptr [2 x i8], [2 x i8]* @.str.2, i32 0, i32 0
    %add.5 = call i8* @__stringAdd(i8* %add.4, i8* %__stringLiteral.3)
    call void @__println(i8* %add.5)
    %x$.5 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    store i32 849, i32* %x$.5
    %y$.5 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    store i32 -463, i32* %y$.5
    %z$.5 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    store i32 480, i32* %z$.5
    %x$.6 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    store i32 -208, i32* %x$.6
    %y$.6 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    store i32 585, i32* %y$.6
    %z$.6 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    store i32 -150, i32* %z$.6
    %x$.7 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    store i32 360, i32* %x$.7
    %y$.7 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    store i32 -670, i32* %y$.7
    %z$.7 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    store i32 -742, i32* %z$.7
    %x$.8 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 0
    store i32 -29, i32* %x$.8
    %y$.8 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 1
    store i32 -591, i32* %y$.8
    %z$.8 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 2
    store i32 -960, i32* %z$.8
    %x$.9 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x$.10 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x.10 = load i32, i32* %x$.10
    %x$.11 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.11 = load i32, i32* %x$.11
    %add.6 = add i32 %x.10, %x.11
    store i32 %add.6, i32* %x$.9
    %y$.9 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y$.10 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y.10 = load i32, i32* %y$.10
    %y$.11 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.11 = load i32, i32* %y$.11
    %add.7 = add i32 %y.10, %y.11
    store i32 %add.7, i32* %y$.9
    %z$.9 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z$.10 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z.10 = load i32, i32* %z$.10
    %z$.11 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.11 = load i32, i32* %z$.11
    %add.8 = add i32 %z.10, %z.11
    store i32 %add.8, i32* %z$.9
    %x$.12 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x$.13 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.13 = load i32, i32* %x$.13
    %x$.14 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x.14 = load i32, i32* %x$.14
    %add.9 = add i32 %x.13, %x.14
    store i32 %add.9, i32* %x$.12
    %y$.12 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y$.13 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.13 = load i32, i32* %y$.13
    %y$.14 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y.14 = load i32, i32* %y$.14
    %add.10 = add i32 %y.13, %y.14
    store i32 %add.10, i32* %y$.12
    %z$.12 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z$.13 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.13 = load i32, i32* %z$.13
    %z$.14 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z.14 = load i32, i32* %z$.14
    %add.11 = add i32 %z.13, %z.14
    store i32 %add.11, i32* %z$.12
    %x$.15 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 0
    %x$.16 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 0
    %x.16 = load i32, i32* %x$.16
    %x$.17 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x.17 = load i32, i32* %x$.17
    %add.12 = add i32 %x.16, %x.17
    store i32 %add.12, i32* %x$.15
    %y$.15 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 1
    %y$.16 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 1
    %y.16 = load i32, i32* %y$.16
    %y$.17 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y.17 = load i32, i32* %y$.17
    %add.13 = add i32 %y.16, %y.17
    store i32 %add.13, i32* %y$.15
    %z$.15 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 2
    %z$.16 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 2
    %z.16 = load i32, i32* %z$.16
    %z$.17 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z.17 = load i32, i32* %z$.17
    %add.14 = add i32 %z.16, %z.17
    store i32 %add.14, i32* %z$.15
    %x$.18 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x$.19 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x.19 = load i32, i32* %x$.19
    %x$.20 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x.20 = load i32, i32* %x$.20
    %sub.0 = sub i32 %x.19, %x.20
    store i32 %sub.0, i32* %x$.18
    %y$.18 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y$.19 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y.19 = load i32, i32* %y$.19
    %y$.20 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y.20 = load i32, i32* %y$.20
    %sub.1 = sub i32 %y.19, %y.20
    store i32 %sub.1, i32* %y$.18
    %z$.18 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z$.19 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z.19 = load i32, i32* %z$.19
    %z$.20 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z.20 = load i32, i32* %z$.20
    %sub.2 = sub i32 %z.19, %z.20
    store i32 %sub.2, i32* %z$.18
    %x$.21 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x$.22 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.22 = load i32, i32* %x$.22
    %x$.23 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 0
    %x.23 = load i32, i32* %x$.23
    %sub.3 = sub i32 %x.22, %x.23
    store i32 %sub.3, i32* %x$.21
    %y$.21 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y$.22 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.22 = load i32, i32* %y$.22
    %y$.23 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 1
    %y.23 = load i32, i32* %y$.23
    %sub.4 = sub i32 %y.22, %y.23
    store i32 %sub.4, i32* %y$.21
    %z$.21 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z$.22 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.22 = load i32, i32* %z$.22
    %z$.23 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 2
    %z.23 = load i32, i32* %z$.23
    %sub.5 = sub i32 %z.22, %z.23
    store i32 %sub.5, i32* %z$.21
    %x$.24 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 0
    %x$.25 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 0
    %x.25 = load i32, i32* %x$.25
    %x$.26 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x.26 = load i32, i32* %x$.26
    %sub.6 = sub i32 %x.25, %x.26
    store i32 %sub.6, i32* %x$.24
    %y$.24 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 1
    %y$.25 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 1
    %y.25 = load i32, i32* %y$.25
    %y$.26 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y.26 = load i32, i32* %y$.26
    %sub.7 = sub i32 %y.25, %y.26
    store i32 %sub.7, i32* %y$.24
    %z$.24 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 2
    %z$.25 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 2
    %z.25 = load i32, i32* %z$.25
    %z$.26 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z.26 = load i32, i32* %z$.26
    %sub.8 = sub i32 %z.25, %z.26
    store i32 %sub.8, i32* %z$.24
    %x$.27 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x$.28 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x.28 = load i32, i32* %x$.28
    %x$.29 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.29 = load i32, i32* %x$.29
    %add.15 = add i32 %x.28, %x.29
    store i32 %add.15, i32* %x$.27
    %y$.27 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y$.28 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y.28 = load i32, i32* %y$.28
    %y$.29 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.29 = load i32, i32* %y$.29
    %add.16 = add i32 %y.28, %y.29
    store i32 %add.16, i32* %y$.27
    %z$.27 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z$.28 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z.28 = load i32, i32* %z$.28
    %z$.29 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.29 = load i32, i32* %z$.29
    %add.17 = add i32 %z.28, %z.29
    store i32 %add.17, i32* %z$.27
    %x$.30 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x$.31 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x.31 = load i32, i32* %x$.31
    %x$.32 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.32 = load i32, i32* %x$.32
    %add.18 = add i32 %x.31, %x.32
    store i32 %add.18, i32* %x$.30
    %y$.30 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y$.31 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y.31 = load i32, i32* %y$.31
    %y$.32 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.32 = load i32, i32* %y$.32
    %add.19 = add i32 %y.31, %y.32
    store i32 %add.19, i32* %y$.30
    %z$.30 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z$.31 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z.31 = load i32, i32* %z$.31
    %z$.32 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.32 = load i32, i32* %z$.32
    %add.20 = add i32 %z.31, %z.32
    store i32 %add.20, i32* %z$.30
    %x$.33 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x$.34 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.34 = load i32, i32* %x$.34
    %x$.35 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.35 = load i32, i32* %x$.35
    %add.21 = add i32 %x.34, %x.35
    store i32 %add.21, i32* %x$.33
    %y$.33 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y$.34 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.34 = load i32, i32* %y$.34
    %y$.35 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.35 = load i32, i32* %y$.35
    %add.22 = add i32 %y.34, %y.35
    store i32 %add.22, i32* %y$.33
    %z$.33 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z$.34 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.34 = load i32, i32* %z$.34
    %z$.35 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.35 = load i32, i32* %z$.35
    %add.23 = add i32 %z.34, %z.35
    store i32 %add.23, i32* %z$.33
    %x$.36 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x$.37 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x.37 = load i32, i32* %x$.37
    %x$.38 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x.38 = load i32, i32* %x$.38
    %add.24 = add i32 %x.37, %x.38
    store i32 %add.24, i32* %x$.36
    %y$.36 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y$.37 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y.37 = load i32, i32* %y$.37
    %y$.38 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y.38 = load i32, i32* %y$.38
    %add.25 = add i32 %y.37, %y.38
    store i32 %add.25, i32* %y$.36
    %z$.36 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z$.37 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z.37 = load i32, i32* %z$.37
    %z$.38 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z.38 = load i32, i32* %z$.38
    %add.26 = add i32 %z.37, %z.38
    store i32 %add.26, i32* %z$.36
    %x$.39 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x$.40 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x.40 = load i32, i32* %x$.40
    %x$.41 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 0
    %x.41 = load i32, i32* %x$.41
    %sub.9 = sub i32 %x.40, %x.41
    store i32 %sub.9, i32* %x$.39
    %y$.39 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y$.40 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y.40 = load i32, i32* %y$.40
    %y$.41 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 1
    %y.41 = load i32, i32* %y$.41
    %sub.10 = sub i32 %y.40, %y.41
    store i32 %sub.10, i32* %y$.39
    %z$.39 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z$.40 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z.40 = load i32, i32* %z$.40
    %z$.41 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 2
    %z.41 = load i32, i32* %z$.41
    %sub.11 = sub i32 %z.40, %z.41
    store i32 %sub.11, i32* %z$.39
    %x$.42 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x$.43 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x.43 = load i32, i32* %x$.43
    %x$.44 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.44 = load i32, i32* %x$.44
    %add.27 = add i32 %x.43, %x.44
    store i32 %add.27, i32* %x$.42
    %y$.42 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y$.43 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y.43 = load i32, i32* %y$.43
    %y$.44 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.44 = load i32, i32* %y$.44
    %add.28 = add i32 %y.43, %y.44
    store i32 %add.28, i32* %y$.42
    %z$.42 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z$.43 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z.43 = load i32, i32* %z$.43
    %z$.44 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.44 = load i32, i32* %z$.44
    %add.29 = add i32 %z.43, %z.44
    store i32 %add.29, i32* %z$.42
    %x$.45 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x$.46 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.46 = load i32, i32* %x$.46
    %x$.47 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x.47 = load i32, i32* %x$.47
    %sub.12 = sub i32 %x.46, %x.47
    store i32 %sub.12, i32* %x$.45
    %y$.45 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y$.46 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.46 = load i32, i32* %y$.46
    %y$.47 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y.47 = load i32, i32* %y$.47
    %sub.13 = sub i32 %y.46, %y.47
    store i32 %sub.13, i32* %y$.45
    %z$.45 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z$.46 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.46 = load i32, i32* %z$.46
    %z$.47 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z.47 = load i32, i32* %z$.47
    %sub.14 = sub i32 %z.46, %z.47
    store i32 %sub.14, i32* %z$.45
    %x$.48 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x.48 = load i32, i32* %x$.48
    %x$.49 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x.49 = load i32, i32* %x$.49
    %mul.0 = mul i32 %x.48, %x.49
    %y$.48 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y.48 = load i32, i32* %y$.48
    %y$.49 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y.49 = load i32, i32* %y$.49
    %mul.1 = mul i32 %y.48, %y.49
    %add.30 = add i32 %mul.0, %mul.1
    %z$.48 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z.48 = load i32, i32* %z$.48
    %z$.49 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z.49 = load i32, i32* %z$.49
    %mul.2 = mul i32 %z.48, %z.49
    %add.31 = add i32 %add.30, %mul.2
    %call.13 = call i8* @__toString(i32 %add.31)
    call void @__println(i8* %call.13)
    %x$.50 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.50 = load i32, i32* %x$.50
    %x$.51 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.51 = load i32, i32* %x$.51
    %mul.3 = mul i32 %x.50, %x.51
    %y$.50 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.50 = load i32, i32* %y$.50
    %y$.51 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.51 = load i32, i32* %y$.51
    %mul.4 = mul i32 %y.50, %y.51
    %add.32 = add i32 %mul.3, %mul.4
    %z$.50 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.50 = load i32, i32* %z$.50
    %z$.51 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.51 = load i32, i32* %z$.51
    %mul.5 = mul i32 %z.50, %z.51
    %add.33 = add i32 %add.32, %mul.5
    %call.15 = call i8* @__toString(i32 %add.33)
    call void @__println(i8* %call.15)
    %x$.52 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.52 = load i32, i32* %x$.52
    %x$.53 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x.53 = load i32, i32* %x$.53
    %sub.15 = sub i32 %x.52, %x.53
    %x$.54 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.54 = load i32, i32* %x$.54
    %x$.55 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x.55 = load i32, i32* %x$.55
    %sub.16 = sub i32 %x.54, %x.55
    %mul.6 = mul i32 %sub.15, %sub.16
    %y$.52 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.52 = load i32, i32* %y$.52
    %y$.53 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y.53 = load i32, i32* %y$.53
    %sub.17 = sub i32 %y.52, %y.53
    %y$.54 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.54 = load i32, i32* %y$.54
    %y$.55 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y.55 = load i32, i32* %y$.55
    %sub.18 = sub i32 %y.54, %y.55
    %mul.7 = mul i32 %sub.17, %sub.18
    %add.34 = add i32 %mul.6, %mul.7
    %z$.52 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.52 = load i32, i32* %z$.52
    %z$.53 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z.53 = load i32, i32* %z$.53
    %sub.19 = sub i32 %z.52, %z.53
    %z$.54 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.54 = load i32, i32* %z$.54
    %z$.55 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z.55 = load i32, i32* %z$.55
    %sub.20 = sub i32 %z.54, %z.55
    %mul.8 = mul i32 %sub.19, %sub.20
    %add.35 = add i32 %add.34, %mul.8
    %call.17 = call i8* @__toString(i32 %add.35)
    call void @__println(i8* %call.17)
    %x$.56 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 0
    %x.56 = load i32, i32* %x$.56
    %x$.57 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x.57 = load i32, i32* %x$.57
    %sub.21 = sub i32 %x.56, %x.57
    %x$.58 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 0
    %x.58 = load i32, i32* %x$.58
    %x$.59 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x.59 = load i32, i32* %x$.59
    %sub.22 = sub i32 %x.58, %x.59
    %mul.9 = mul i32 %sub.21, %sub.22
    %y$.56 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 1
    %y.56 = load i32, i32* %y$.56
    %y$.57 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y.57 = load i32, i32* %y$.57
    %sub.23 = sub i32 %y.56, %y.57
    %y$.58 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 1
    %y.58 = load i32, i32* %y$.58
    %y$.59 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y.59 = load i32, i32* %y$.59
    %sub.24 = sub i32 %y.58, %y.59
    %mul.10 = mul i32 %sub.23, %sub.24
    %add.36 = add i32 %mul.9, %mul.10
    %z$.56 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 2
    %z.56 = load i32, i32* %z$.56
    %z$.57 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z.57 = load i32, i32* %z$.57
    %sub.25 = sub i32 %z.56, %z.57
    %z$.58 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 2
    %z.58 = load i32, i32* %z$.58
    %z$.59 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z.59 = load i32, i32* %z$.59
    %sub.26 = sub i32 %z.58, %z.59
    %mul.11 = mul i32 %sub.25, %sub.26
    %add.37 = add i32 %add.36, %mul.11
    %call.19 = call i8* @__toString(i32 %add.37)
    call void @__println(i8* %call.19)
    %x$.60 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x.60 = load i32, i32* %x$.60
    %x$.61 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x.61 = load i32, i32* %x$.61
    %mul.12 = mul i32 %x.60, %x.61
    %y$.60 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y.60 = load i32, i32* %y$.60
    %y$.61 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y.61 = load i32, i32* %y$.61
    %mul.13 = mul i32 %y.60, %y.61
    %add.38 = add i32 %mul.12, %mul.13
    %z$.60 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z.60 = load i32, i32* %z$.60
    %z$.61 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z.61 = load i32, i32* %z$.61
    %mul.14 = mul i32 %z.60, %z.61
    %add.39 = add i32 %add.38, %mul.14
    %call.21 = call i8* @__toString(i32 %add.39)
    call void @__println(i8* %call.21)
    %call.23 = call %class.point* @point.cross(%class.point* %malloc.1, %class.point* %malloc.3)
    %__stringLiteral.4 = getelementptr [2 x i8], [2 x i8]* @.str.0, i32 0, i32 0
    %x$.62 = getelementptr %class.point, %class.point* %call.23, i32 0, i32 0
    %x.62 = load i32, i32* %x$.62
    %call.27 = call i8* @__toString(i32 %x.62)
    %add.40 = call i8* @__stringAdd(i8* %__stringLiteral.4, i8* %call.27)
    %__stringLiteral.5 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.41 = call i8* @__stringAdd(i8* %add.40, i8* %__stringLiteral.5)
    %y$.62 = getelementptr %class.point, %class.point* %call.23, i32 0, i32 1
    %y.62 = load i32, i32* %y$.62
    %call.28 = call i8* @__toString(i32 %y.62)
    %add.42 = call i8* @__stringAdd(i8* %add.41, i8* %call.28)
    %__stringLiteral.6 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.43 = call i8* @__stringAdd(i8* %add.42, i8* %__stringLiteral.6)
    %z$.62 = getelementptr %class.point, %class.point* %call.23, i32 0, i32 2
    %z.62 = load i32, i32* %z$.62
    %call.29 = call i8* @__toString(i32 %z.62)
    %add.44 = call i8* @__stringAdd(i8* %add.43, i8* %call.29)
    %__stringLiteral.7 = getelementptr [2 x i8], [2 x i8]* @.str.2, i32 0, i32 0
    %add.45 = call i8* @__stringAdd(i8* %add.44, i8* %__stringLiteral.7)
    call void @__println(i8* %add.45)
    %__stringLiteral.8 = getelementptr [2 x i8], [2 x i8]* @.str.0, i32 0, i32 0
    %x$.63 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 0
    %x.63 = load i32, i32* %x$.63
    %call.30 = call i8* @__toString(i32 %x.63)
    %add.46 = call i8* @__stringAdd(i8* %__stringLiteral.8, i8* %call.30)
    %__stringLiteral.9 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.47 = call i8* @__stringAdd(i8* %add.46, i8* %__stringLiteral.9)
    %y$.63 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 1
    %y.63 = load i32, i32* %y$.63
    %call.31 = call i8* @__toString(i32 %y.63)
    %add.48 = call i8* @__stringAdd(i8* %add.47, i8* %call.31)
    %__stringLiteral.10 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.49 = call i8* @__stringAdd(i8* %add.48, i8* %__stringLiteral.10)
    %z$.63 = getelementptr %class.point, %class.point* %malloc.0, i32 0, i32 2
    %z.63 = load i32, i32* %z$.63
    %call.32 = call i8* @__toString(i32 %z.63)
    %add.50 = call i8* @__stringAdd(i8* %add.49, i8* %call.32)
    %__stringLiteral.11 = getelementptr [2 x i8], [2 x i8]* @.str.2, i32 0, i32 0
    %add.51 = call i8* @__stringAdd(i8* %add.50, i8* %__stringLiteral.11)
    call void @__println(i8* %add.51)
    %__stringLiteral.12 = getelementptr [2 x i8], [2 x i8]* @.str.0, i32 0, i32 0
    %x$.64 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 0
    %x.64 = load i32, i32* %x$.64
    %call.33 = call i8* @__toString(i32 %x.64)
    %add.52 = call i8* @__stringAdd(i8* %__stringLiteral.12, i8* %call.33)
    %__stringLiteral.13 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.53 = call i8* @__stringAdd(i8* %add.52, i8* %__stringLiteral.13)
    %y$.64 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 1
    %y.64 = load i32, i32* %y$.64
    %call.34 = call i8* @__toString(i32 %y.64)
    %add.54 = call i8* @__stringAdd(i8* %add.53, i8* %call.34)
    %__stringLiteral.14 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.55 = call i8* @__stringAdd(i8* %add.54, i8* %__stringLiteral.14)
    %z$.64 = getelementptr %class.point, %class.point* %malloc.1, i32 0, i32 2
    %z.64 = load i32, i32* %z$.64
    %call.35 = call i8* @__toString(i32 %z.64)
    %add.56 = call i8* @__stringAdd(i8* %add.55, i8* %call.35)
    %__stringLiteral.15 = getelementptr [2 x i8], [2 x i8]* @.str.2, i32 0, i32 0
    %add.57 = call i8* @__stringAdd(i8* %add.56, i8* %__stringLiteral.15)
    call void @__println(i8* %add.57)
    %__stringLiteral.16 = getelementptr [2 x i8], [2 x i8]* @.str.0, i32 0, i32 0
    %x$.65 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 0
    %x.65 = load i32, i32* %x$.65
    %call.36 = call i8* @__toString(i32 %x.65)
    %add.58 = call i8* @__stringAdd(i8* %__stringLiteral.16, i8* %call.36)
    %__stringLiteral.17 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.59 = call i8* @__stringAdd(i8* %add.58, i8* %__stringLiteral.17)
    %y$.65 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 1
    %y.65 = load i32, i32* %y$.65
    %call.37 = call i8* @__toString(i32 %y.65)
    %add.60 = call i8* @__stringAdd(i8* %add.59, i8* %call.37)
    %__stringLiteral.18 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.61 = call i8* @__stringAdd(i8* %add.60, i8* %__stringLiteral.18)
    %z$.65 = getelementptr %class.point, %class.point* %malloc.2, i32 0, i32 2
    %z.65 = load i32, i32* %z$.65
    %call.38 = call i8* @__toString(i32 %z.65)
    %add.62 = call i8* @__stringAdd(i8* %add.61, i8* %call.38)
    %__stringLiteral.19 = getelementptr [2 x i8], [2 x i8]* @.str.2, i32 0, i32 0
    %add.63 = call i8* @__stringAdd(i8* %add.62, i8* %__stringLiteral.19)
    call void @__println(i8* %add.63)
    %__stringLiteral.20 = getelementptr [2 x i8], [2 x i8]* @.str.0, i32 0, i32 0
    %x$.66 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 0
    %x.66 = load i32, i32* %x$.66
    %call.39 = call i8* @__toString(i32 %x.66)
    %add.64 = call i8* @__stringAdd(i8* %__stringLiteral.20, i8* %call.39)
    %__stringLiteral.21 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.65 = call i8* @__stringAdd(i8* %add.64, i8* %__stringLiteral.21)
    %y$.66 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 1
    %y.66 = load i32, i32* %y$.66
    %call.40 = call i8* @__toString(i32 %y.66)
    %add.66 = call i8* @__stringAdd(i8* %add.65, i8* %call.40)
    %__stringLiteral.22 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.67 = call i8* @__stringAdd(i8* %add.66, i8* %__stringLiteral.22)
    %z$.66 = getelementptr %class.point, %class.point* %malloc.3, i32 0, i32 2
    %z.66 = load i32, i32* %z$.66
    %call.41 = call i8* @__toString(i32 %z.66)
    %add.68 = call i8* @__stringAdd(i8* %add.67, i8* %call.41)
    %__stringLiteral.23 = getelementptr [2 x i8], [2 x i8]* @.str.2, i32 0, i32 0
    %add.69 = call i8* @__stringAdd(i8* %add.68, i8* %__stringLiteral.23)
    call void @__println(i8* %add.69)
    ret i32 0

}

