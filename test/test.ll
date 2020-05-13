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
    %returnValue$.0 = alloca %class.point*
    %this$.0 = alloca %class.point*
    store %class.point* %this.0, %class.point** %this$.0
    %other$.0 = alloca %class.point*
    store %class.point* %other.0, %class.point** %other$.0
    %retval$.0 = alloca %class.point*
    %malloc8.0 = call i8* @__malloc(i32 12)
    %malloc.0 = bitcast i8* %malloc8.0 to %class.point*
    %this$.1 = alloca %class.point*
    store %class.point* %malloc.0, %class.point** %this$.1
    %this.7 = load %class.point*, %class.point** %this$.1
    %x$.4 = getelementptr %class.point, %class.point* %this.7, i32 0, i32 0
    %x.4 = load i32, i32* %x$.4
    store i32 0, i32* %x$.4
    %this.8 = load %class.point*, %class.point** %this$.1
    %y$.4 = getelementptr %class.point, %class.point* %this.8, i32 0, i32 1
    %y.4 = load i32, i32* %y$.4
    store i32 0, i32* %y$.4
    %this.9 = load %class.point*, %class.point** %this$.1
    %z$.4 = getelementptr %class.point, %class.point* %this.9, i32 0, i32 2
    %z.4 = load i32, i32* %z$.4
    store i32 0, i32* %z$.4
    store %class.point* %malloc.0, %class.point** %retval$.0
    %retval.0 = load %class.point*, %class.point** %retval$.0
    %this.1 = load %class.point*, %class.point** %this$.0
    %y$.0 = getelementptr %class.point, %class.point* %this.1, i32 0, i32 1
    %y.0 = load i32, i32* %y$.0
    %other.1 = load %class.point*, %class.point** %other$.0
    %z$.0 = getelementptr %class.point, %class.point* %other.1, i32 0, i32 2
    %z.0 = load i32, i32* %z$.0
    %mul.0 = mul i32 %y.0, %z.0
    %this.2 = load %class.point*, %class.point** %this$.0
    %z$.1 = getelementptr %class.point, %class.point* %this.2, i32 0, i32 2
    %z.1 = load i32, i32* %z$.1
    %other.2 = load %class.point*, %class.point** %other$.0
    %y$.1 = getelementptr %class.point, %class.point* %other.2, i32 0, i32 1
    %y.1 = load i32, i32* %y$.1
    %mul.1 = mul i32 %z.1, %y.1
    %sub.0 = sub i32 %mul.0, %mul.1
    %this.3 = load %class.point*, %class.point** %this$.0
    %z$.2 = getelementptr %class.point, %class.point* %this.3, i32 0, i32 2
    %z.2 = load i32, i32* %z$.2
    %other.3 = load %class.point*, %class.point** %other$.0
    %x$.0 = getelementptr %class.point, %class.point* %other.3, i32 0, i32 0
    %x.0 = load i32, i32* %x$.0
    %mul.2 = mul i32 %z.2, %x.0
    %this.4 = load %class.point*, %class.point** %this$.0
    %x$.1 = getelementptr %class.point, %class.point* %this.4, i32 0, i32 0
    %x.1 = load i32, i32* %x$.1
    %other.4 = load %class.point*, %class.point** %other$.0
    %z$.3 = getelementptr %class.point, %class.point* %other.4, i32 0, i32 2
    %z.3 = load i32, i32* %z$.3
    %mul.3 = mul i32 %x.1, %z.3
    %sub.1 = sub i32 %mul.2, %mul.3
    %this.5 = load %class.point*, %class.point** %this$.0
    %x$.2 = getelementptr %class.point, %class.point* %this.5, i32 0, i32 0
    %x.2 = load i32, i32* %x$.2
    %other.5 = load %class.point*, %class.point** %other$.0
    %y$.2 = getelementptr %class.point, %class.point* %other.5, i32 0, i32 1
    %y.2 = load i32, i32* %y$.2
    %mul.4 = mul i32 %x.2, %y.2
    %this.6 = load %class.point*, %class.point** %this$.0
    %y$.3 = getelementptr %class.point, %class.point* %this.6, i32 0, i32 1
    %y.3 = load i32, i32* %y$.3
    %other.6 = load %class.point*, %class.point** %other$.0
    %x$.3 = getelementptr %class.point, %class.point* %other.6, i32 0, i32 0
    %x.3 = load i32, i32* %x$.3
    %mul.5 = mul i32 %y.3, %x.3
    %sub.2 = sub i32 %mul.4, %mul.5
    %this$.2 = alloca %class.point*
    store %class.point* %retval.0, %class.point** %this$.2
    %a_x$.0 = alloca i32
    store i32 %sub.0, i32* %a_x$.0
    %a_y$.0 = alloca i32
    store i32 %sub.1, i32* %a_y$.0
    %a_z$.0 = alloca i32
    store i32 %sub.2, i32* %a_z$.0
    %this.10 = load %class.point*, %class.point** %this$.2
    %x$.5 = getelementptr %class.point, %class.point* %this.10, i32 0, i32 0
    %x.5 = load i32, i32* %x$.5
    %a_x.0 = load i32, i32* %a_x$.0
    store i32 %a_x.0, i32* %x$.5
    %this.11 = load %class.point*, %class.point** %this$.2
    %y$.5 = getelementptr %class.point, %class.point* %this.11, i32 0, i32 1
    %y.5 = load i32, i32* %y$.5
    %a_y.0 = load i32, i32* %a_y$.0
    store i32 %a_y.0, i32* %y$.5
    %this.12 = load %class.point*, %class.point** %this$.2
    %z$.5 = getelementptr %class.point, %class.point* %this.12, i32 0, i32 2
    %z.5 = load i32, i32* %z$.5
    %a_z.0 = load i32, i32* %a_z$.0
    store i32 %a_z.0, i32* %z$.5
    %retval.1 = load %class.point*, %class.point** %retval$.0
    store %class.point* %retval.1, %class.point** %returnValue$.0
    %returnValue.0 = load %class.point*, %class.point** %returnValue$.0
    ret %class.point* %returnValue.0

}

define i32 @main() {
entranceBlock.0:
    %returnValue$.0 = alloca i32
    %a$.0 = alloca %class.point*
    %malloc8.0 = call i8* @__malloc(i32 12)
    %malloc.0 = bitcast i8* %malloc8.0 to %class.point*
    %this$.0 = alloca %class.point*
    store %class.point* %malloc.0, %class.point** %this$.0
    %this.0 = load %class.point*, %class.point** %this$.0
    %x$.0 = getelementptr %class.point, %class.point* %this.0, i32 0, i32 0
    %x.0 = load i32, i32* %x$.0
    store i32 0, i32* %x$.0
    %this.1 = load %class.point*, %class.point** %this$.0
    %y$.0 = getelementptr %class.point, %class.point* %this.1, i32 0, i32 1
    %y.0 = load i32, i32* %y$.0
    store i32 0, i32* %y$.0
    %this.2 = load %class.point*, %class.point** %this$.0
    %z$.0 = getelementptr %class.point, %class.point* %this.2, i32 0, i32 2
    %z.0 = load i32, i32* %z$.0
    store i32 0, i32* %z$.0
    store %class.point* %malloc.0, %class.point** %a$.0
    %b$.0 = alloca %class.point*
    %malloc8.1 = call i8* @__malloc(i32 12)
    %malloc.1 = bitcast i8* %malloc8.1 to %class.point*
    %this$.1 = alloca %class.point*
    store %class.point* %malloc.1, %class.point** %this$.1
    %this.3 = load %class.point*, %class.point** %this$.1
    %x$.1 = getelementptr %class.point, %class.point* %this.3, i32 0, i32 0
    %x.1 = load i32, i32* %x$.1
    store i32 0, i32* %x$.1
    %this.4 = load %class.point*, %class.point** %this$.1
    %y$.1 = getelementptr %class.point, %class.point* %this.4, i32 0, i32 1
    %y.1 = load i32, i32* %y$.1
    store i32 0, i32* %y$.1
    %this.5 = load %class.point*, %class.point** %this$.1
    %z$.1 = getelementptr %class.point, %class.point* %this.5, i32 0, i32 2
    %z.1 = load i32, i32* %z$.1
    store i32 0, i32* %z$.1
    store %class.point* %malloc.1, %class.point** %b$.0
    %c$.0 = alloca %class.point*
    %malloc8.2 = call i8* @__malloc(i32 12)
    %malloc.2 = bitcast i8* %malloc8.2 to %class.point*
    %this$.2 = alloca %class.point*
    store %class.point* %malloc.2, %class.point** %this$.2
    %this.6 = load %class.point*, %class.point** %this$.2
    %x$.2 = getelementptr %class.point, %class.point* %this.6, i32 0, i32 0
    %x.2 = load i32, i32* %x$.2
    store i32 0, i32* %x$.2
    %this.7 = load %class.point*, %class.point** %this$.2
    %y$.2 = getelementptr %class.point, %class.point* %this.7, i32 0, i32 1
    %y.2 = load i32, i32* %y$.2
    store i32 0, i32* %y$.2
    %this.8 = load %class.point*, %class.point** %this$.2
    %z$.2 = getelementptr %class.point, %class.point* %this.8, i32 0, i32 2
    %z.2 = load i32, i32* %z$.2
    store i32 0, i32* %z$.2
    store %class.point* %malloc.2, %class.point** %c$.0
    %d$.0 = alloca %class.point*
    %malloc8.3 = call i8* @__malloc(i32 12)
    %malloc.3 = bitcast i8* %malloc8.3 to %class.point*
    %this$.3 = alloca %class.point*
    store %class.point* %malloc.3, %class.point** %this$.3
    %this.9 = load %class.point*, %class.point** %this$.3
    %x$.3 = getelementptr %class.point, %class.point* %this.9, i32 0, i32 0
    %x.3 = load i32, i32* %x$.3
    store i32 0, i32* %x$.3
    %this.10 = load %class.point*, %class.point** %this$.3
    %y$.3 = getelementptr %class.point, %class.point* %this.10, i32 0, i32 1
    %y.3 = load i32, i32* %y$.3
    store i32 0, i32* %y$.3
    %this.11 = load %class.point*, %class.point** %this$.3
    %z$.3 = getelementptr %class.point, %class.point* %this.11, i32 0, i32 2
    %z.3 = load i32, i32* %z$.3
    store i32 0, i32* %z$.3
    store %class.point* %malloc.3, %class.point** %d$.0
    %a.0 = load %class.point*, %class.point** %a$.0
    %this$.4 = alloca %class.point*
    store %class.point* %a.0, %class.point** %this$.4
    %__stringLiteral.0 = getelementptr [2 x i8], [2 x i8]* @.str.0, i32 0, i32 0
    %this.12 = load %class.point*, %class.point** %this$.4
    %x$.4 = getelementptr %class.point, %class.point* %this.12, i32 0, i32 0
    %x.4 = load i32, i32* %x$.4
    %call.24 = call i8* @__toString(i32 %x.4)
    %add.0 = call i8* @__stringAdd(i8* %__stringLiteral.0, i8* %call.24)
    %__stringLiteral.1 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.1 = call i8* @__stringAdd(i8* %add.0, i8* %__stringLiteral.1)
    %this.13 = load %class.point*, %class.point** %this$.4
    %y$.4 = getelementptr %class.point, %class.point* %this.13, i32 0, i32 1
    %y.4 = load i32, i32* %y$.4
    %call.25 = call i8* @__toString(i32 %y.4)
    %add.2 = call i8* @__stringAdd(i8* %add.1, i8* %call.25)
    %__stringLiteral.2 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.3 = call i8* @__stringAdd(i8* %add.2, i8* %__stringLiteral.2)
    %this.14 = load %class.point*, %class.point** %this$.4
    %z$.4 = getelementptr %class.point, %class.point* %this.14, i32 0, i32 2
    %z.4 = load i32, i32* %z$.4
    %call.26 = call i8* @__toString(i32 %z.4)
    %add.4 = call i8* @__stringAdd(i8* %add.3, i8* %call.26)
    %__stringLiteral.3 = getelementptr [2 x i8], [2 x i8]* @.str.2, i32 0, i32 0
    %add.5 = call i8* @__stringAdd(i8* %add.4, i8* %__stringLiteral.3)
    call void @__println(i8* %add.5)
    %a.1 = load %class.point*, %class.point** %a$.0
    %neg.0 = sub i32 0, 463
    %this$.5 = alloca %class.point*
    store %class.point* %a.1, %class.point** %this$.5
    %a_x$.0 = alloca i32
    store i32 849, i32* %a_x$.0
    %a_y$.0 = alloca i32
    store i32 %neg.0, i32* %a_y$.0
    %a_z$.0 = alloca i32
    store i32 480, i32* %a_z$.0
    %this.15 = load %class.point*, %class.point** %this$.5
    %x$.5 = getelementptr %class.point, %class.point* %this.15, i32 0, i32 0
    %x.5 = load i32, i32* %x$.5
    %a_x.0 = load i32, i32* %a_x$.0
    store i32 %a_x.0, i32* %x$.5
    %this.16 = load %class.point*, %class.point** %this$.5
    %y$.5 = getelementptr %class.point, %class.point* %this.16, i32 0, i32 1
    %y.5 = load i32, i32* %y$.5
    %a_y.0 = load i32, i32* %a_y$.0
    store i32 %a_y.0, i32* %y$.5
    %this.17 = load %class.point*, %class.point** %this$.5
    %z$.5 = getelementptr %class.point, %class.point* %this.17, i32 0, i32 2
    %z.5 = load i32, i32* %z$.5
    %a_z.0 = load i32, i32* %a_z$.0
    store i32 %a_z.0, i32* %z$.5
    %b.0 = load %class.point*, %class.point** %b$.0
    %neg.1 = sub i32 0, 208
    %neg.2 = sub i32 0, 150
    %this$.6 = alloca %class.point*
    store %class.point* %b.0, %class.point** %this$.6
    %a_x$.1 = alloca i32
    store i32 %neg.1, i32* %a_x$.1
    %a_y$.1 = alloca i32
    store i32 585, i32* %a_y$.1
    %a_z$.1 = alloca i32
    store i32 %neg.2, i32* %a_z$.1
    %this.18 = load %class.point*, %class.point** %this$.6
    %x$.6 = getelementptr %class.point, %class.point* %this.18, i32 0, i32 0
    %x.6 = load i32, i32* %x$.6
    %a_x.1 = load i32, i32* %a_x$.1
    store i32 %a_x.1, i32* %x$.6
    %this.19 = load %class.point*, %class.point** %this$.6
    %y$.6 = getelementptr %class.point, %class.point* %this.19, i32 0, i32 1
    %y.6 = load i32, i32* %y$.6
    %a_y.1 = load i32, i32* %a_y$.1
    store i32 %a_y.1, i32* %y$.6
    %this.20 = load %class.point*, %class.point** %this$.6
    %z$.6 = getelementptr %class.point, %class.point* %this.20, i32 0, i32 2
    %z.6 = load i32, i32* %z$.6
    %a_z.1 = load i32, i32* %a_z$.1
    store i32 %a_z.1, i32* %z$.6
    %c.0 = load %class.point*, %class.point** %c$.0
    %neg.3 = sub i32 0, 670
    %neg.4 = sub i32 0, 742
    %this$.7 = alloca %class.point*
    store %class.point* %c.0, %class.point** %this$.7
    %a_x$.2 = alloca i32
    store i32 360, i32* %a_x$.2
    %a_y$.2 = alloca i32
    store i32 %neg.3, i32* %a_y$.2
    %a_z$.2 = alloca i32
    store i32 %neg.4, i32* %a_z$.2
    %this.21 = load %class.point*, %class.point** %this$.7
    %x$.7 = getelementptr %class.point, %class.point* %this.21, i32 0, i32 0
    %x.7 = load i32, i32* %x$.7
    %a_x.2 = load i32, i32* %a_x$.2
    store i32 %a_x.2, i32* %x$.7
    %this.22 = load %class.point*, %class.point** %this$.7
    %y$.7 = getelementptr %class.point, %class.point* %this.22, i32 0, i32 1
    %y.7 = load i32, i32* %y$.7
    %a_y.2 = load i32, i32* %a_y$.2
    store i32 %a_y.2, i32* %y$.7
    %this.23 = load %class.point*, %class.point** %this$.7
    %z$.7 = getelementptr %class.point, %class.point* %this.23, i32 0, i32 2
    %z.7 = load i32, i32* %z$.7
    %a_z.2 = load i32, i32* %a_z$.2
    store i32 %a_z.2, i32* %z$.7
    %d.0 = load %class.point*, %class.point** %d$.0
    %neg.5 = sub i32 0, 29
    %neg.6 = sub i32 0, 591
    %neg.7 = sub i32 0, 960
    %this$.8 = alloca %class.point*
    store %class.point* %d.0, %class.point** %this$.8
    %a_x$.3 = alloca i32
    store i32 %neg.5, i32* %a_x$.3
    %a_y$.3 = alloca i32
    store i32 %neg.6, i32* %a_y$.3
    %a_z$.3 = alloca i32
    store i32 %neg.7, i32* %a_z$.3
    %this.24 = load %class.point*, %class.point** %this$.8
    %x$.8 = getelementptr %class.point, %class.point* %this.24, i32 0, i32 0
    %x.8 = load i32, i32* %x$.8
    %a_x.3 = load i32, i32* %a_x$.3
    store i32 %a_x.3, i32* %x$.8
    %this.25 = load %class.point*, %class.point** %this$.8
    %y$.8 = getelementptr %class.point, %class.point* %this.25, i32 0, i32 1
    %y.8 = load i32, i32* %y$.8
    %a_y.3 = load i32, i32* %a_y$.3
    store i32 %a_y.3, i32* %y$.8
    %this.26 = load %class.point*, %class.point** %this$.8
    %z$.8 = getelementptr %class.point, %class.point* %this.26, i32 0, i32 2
    %z.8 = load i32, i32* %z$.8
    %a_z.3 = load i32, i32* %a_z$.3
    store i32 %a_z.3, i32* %z$.8
    %a.2 = load %class.point*, %class.point** %a$.0
    %b.1 = load %class.point*, %class.point** %b$.0
    %returnValue$.1 = alloca %class.point*
    %this$.9 = alloca %class.point*
    store %class.point* %a.2, %class.point** %this$.9
    %other$.0 = alloca %class.point*
    store %class.point* %b.1, %class.point** %other$.0
    %this.27 = load %class.point*, %class.point** %this$.9
    %x$.9 = getelementptr %class.point, %class.point* %this.27, i32 0, i32 0
    %x.9 = load i32, i32* %x$.9
    %this.28 = load %class.point*, %class.point** %this$.9
    %x$.10 = getelementptr %class.point, %class.point* %this.28, i32 0, i32 0
    %x.10 = load i32, i32* %x$.10
    %other.0 = load %class.point*, %class.point** %other$.0
    %x$.11 = getelementptr %class.point, %class.point* %other.0, i32 0, i32 0
    %x.11 = load i32, i32* %x$.11
    %add.6 = add i32 %x.10, %x.11
    store i32 %add.6, i32* %x$.9
    %this.29 = load %class.point*, %class.point** %this$.9
    %y$.9 = getelementptr %class.point, %class.point* %this.29, i32 0, i32 1
    %y.9 = load i32, i32* %y$.9
    %this.30 = load %class.point*, %class.point** %this$.9
    %y$.10 = getelementptr %class.point, %class.point* %this.30, i32 0, i32 1
    %y.10 = load i32, i32* %y$.10
    %other.1 = load %class.point*, %class.point** %other$.0
    %y$.11 = getelementptr %class.point, %class.point* %other.1, i32 0, i32 1
    %y.11 = load i32, i32* %y$.11
    %add.7 = add i32 %y.10, %y.11
    store i32 %add.7, i32* %y$.9
    %this.31 = load %class.point*, %class.point** %this$.9
    %z$.9 = getelementptr %class.point, %class.point* %this.31, i32 0, i32 2
    %z.9 = load i32, i32* %z$.9
    %this.32 = load %class.point*, %class.point** %this$.9
    %z$.10 = getelementptr %class.point, %class.point* %this.32, i32 0, i32 2
    %z.10 = load i32, i32* %z$.10
    %other.2 = load %class.point*, %class.point** %other$.0
    %z$.11 = getelementptr %class.point, %class.point* %other.2, i32 0, i32 2
    %z.11 = load i32, i32* %z$.11
    %add.8 = add i32 %z.10, %z.11
    store i32 %add.8, i32* %z$.9
    %this.33 = load %class.point*, %class.point** %this$.9
    store %class.point* %this.33, %class.point** %returnValue$.1
    %returnValue.1 = load %class.point*, %class.point** %returnValue$.1
    %b.2 = load %class.point*, %class.point** %b$.0
    %c.1 = load %class.point*, %class.point** %c$.0
    %returnValue$.2 = alloca %class.point*
    %this$.10 = alloca %class.point*
    store %class.point* %b.2, %class.point** %this$.10
    %other$.1 = alloca %class.point*
    store %class.point* %c.1, %class.point** %other$.1
    %this.34 = load %class.point*, %class.point** %this$.10
    %x$.12 = getelementptr %class.point, %class.point* %this.34, i32 0, i32 0
    %x.12 = load i32, i32* %x$.12
    %this.35 = load %class.point*, %class.point** %this$.10
    %x$.13 = getelementptr %class.point, %class.point* %this.35, i32 0, i32 0
    %x.13 = load i32, i32* %x$.13
    %other.3 = load %class.point*, %class.point** %other$.1
    %x$.14 = getelementptr %class.point, %class.point* %other.3, i32 0, i32 0
    %x.14 = load i32, i32* %x$.14
    %add.9 = add i32 %x.13, %x.14
    store i32 %add.9, i32* %x$.12
    %this.36 = load %class.point*, %class.point** %this$.10
    %y$.12 = getelementptr %class.point, %class.point* %this.36, i32 0, i32 1
    %y.12 = load i32, i32* %y$.12
    %this.37 = load %class.point*, %class.point** %this$.10
    %y$.13 = getelementptr %class.point, %class.point* %this.37, i32 0, i32 1
    %y.13 = load i32, i32* %y$.13
    %other.4 = load %class.point*, %class.point** %other$.1
    %y$.14 = getelementptr %class.point, %class.point* %other.4, i32 0, i32 1
    %y.14 = load i32, i32* %y$.14
    %add.10 = add i32 %y.13, %y.14
    store i32 %add.10, i32* %y$.12
    %this.38 = load %class.point*, %class.point** %this$.10
    %z$.12 = getelementptr %class.point, %class.point* %this.38, i32 0, i32 2
    %z.12 = load i32, i32* %z$.12
    %this.39 = load %class.point*, %class.point** %this$.10
    %z$.13 = getelementptr %class.point, %class.point* %this.39, i32 0, i32 2
    %z.13 = load i32, i32* %z$.13
    %other.5 = load %class.point*, %class.point** %other$.1
    %z$.14 = getelementptr %class.point, %class.point* %other.5, i32 0, i32 2
    %z.14 = load i32, i32* %z$.14
    %add.11 = add i32 %z.13, %z.14
    store i32 %add.11, i32* %z$.12
    %this.40 = load %class.point*, %class.point** %this$.10
    store %class.point* %this.40, %class.point** %returnValue$.2
    %returnValue.2 = load %class.point*, %class.point** %returnValue$.2
    %d.1 = load %class.point*, %class.point** %d$.0
    %c.2 = load %class.point*, %class.point** %c$.0
    %returnValue$.3 = alloca %class.point*
    %this$.11 = alloca %class.point*
    store %class.point* %d.1, %class.point** %this$.11
    %other$.2 = alloca %class.point*
    store %class.point* %c.2, %class.point** %other$.2
    %this.41 = load %class.point*, %class.point** %this$.11
    %x$.15 = getelementptr %class.point, %class.point* %this.41, i32 0, i32 0
    %x.15 = load i32, i32* %x$.15
    %this.42 = load %class.point*, %class.point** %this$.11
    %x$.16 = getelementptr %class.point, %class.point* %this.42, i32 0, i32 0
    %x.16 = load i32, i32* %x$.16
    %other.6 = load %class.point*, %class.point** %other$.2
    %x$.17 = getelementptr %class.point, %class.point* %other.6, i32 0, i32 0
    %x.17 = load i32, i32* %x$.17
    %add.12 = add i32 %x.16, %x.17
    store i32 %add.12, i32* %x$.15
    %this.43 = load %class.point*, %class.point** %this$.11
    %y$.15 = getelementptr %class.point, %class.point* %this.43, i32 0, i32 1
    %y.15 = load i32, i32* %y$.15
    %this.44 = load %class.point*, %class.point** %this$.11
    %y$.16 = getelementptr %class.point, %class.point* %this.44, i32 0, i32 1
    %y.16 = load i32, i32* %y$.16
    %other.7 = load %class.point*, %class.point** %other$.2
    %y$.17 = getelementptr %class.point, %class.point* %other.7, i32 0, i32 1
    %y.17 = load i32, i32* %y$.17
    %add.13 = add i32 %y.16, %y.17
    store i32 %add.13, i32* %y$.15
    %this.45 = load %class.point*, %class.point** %this$.11
    %z$.15 = getelementptr %class.point, %class.point* %this.45, i32 0, i32 2
    %z.15 = load i32, i32* %z$.15
    %this.46 = load %class.point*, %class.point** %this$.11
    %z$.16 = getelementptr %class.point, %class.point* %this.46, i32 0, i32 2
    %z.16 = load i32, i32* %z$.16
    %other.8 = load %class.point*, %class.point** %other$.2
    %z$.17 = getelementptr %class.point, %class.point* %other.8, i32 0, i32 2
    %z.17 = load i32, i32* %z$.17
    %add.14 = add i32 %z.16, %z.17
    store i32 %add.14, i32* %z$.15
    %this.47 = load %class.point*, %class.point** %this$.11
    store %class.point* %this.47, %class.point** %returnValue$.3
    %returnValue.3 = load %class.point*, %class.point** %returnValue$.3
    %c.3 = load %class.point*, %class.point** %c$.0
    %a.3 = load %class.point*, %class.point** %a$.0
    %returnValue$.4 = alloca %class.point*
    %this$.12 = alloca %class.point*
    store %class.point* %c.3, %class.point** %this$.12
    %other$.3 = alloca %class.point*
    store %class.point* %a.3, %class.point** %other$.3
    %this.48 = load %class.point*, %class.point** %this$.12
    %x$.18 = getelementptr %class.point, %class.point* %this.48, i32 0, i32 0
    %x.18 = load i32, i32* %x$.18
    %this.49 = load %class.point*, %class.point** %this$.12
    %x$.19 = getelementptr %class.point, %class.point* %this.49, i32 0, i32 0
    %x.19 = load i32, i32* %x$.19
    %other.9 = load %class.point*, %class.point** %other$.3
    %x$.20 = getelementptr %class.point, %class.point* %other.9, i32 0, i32 0
    %x.20 = load i32, i32* %x$.20
    %sub.0 = sub i32 %x.19, %x.20
    store i32 %sub.0, i32* %x$.18
    %this.50 = load %class.point*, %class.point** %this$.12
    %y$.18 = getelementptr %class.point, %class.point* %this.50, i32 0, i32 1
    %y.18 = load i32, i32* %y$.18
    %this.51 = load %class.point*, %class.point** %this$.12
    %y$.19 = getelementptr %class.point, %class.point* %this.51, i32 0, i32 1
    %y.19 = load i32, i32* %y$.19
    %other.10 = load %class.point*, %class.point** %other$.3
    %y$.20 = getelementptr %class.point, %class.point* %other.10, i32 0, i32 1
    %y.20 = load i32, i32* %y$.20
    %sub.1 = sub i32 %y.19, %y.20
    store i32 %sub.1, i32* %y$.18
    %this.52 = load %class.point*, %class.point** %this$.12
    %z$.18 = getelementptr %class.point, %class.point* %this.52, i32 0, i32 2
    %z.18 = load i32, i32* %z$.18
    %this.53 = load %class.point*, %class.point** %this$.12
    %z$.19 = getelementptr %class.point, %class.point* %this.53, i32 0, i32 2
    %z.19 = load i32, i32* %z$.19
    %other.11 = load %class.point*, %class.point** %other$.3
    %z$.20 = getelementptr %class.point, %class.point* %other.11, i32 0, i32 2
    %z.20 = load i32, i32* %z$.20
    %sub.2 = sub i32 %z.19, %z.20
    store i32 %sub.2, i32* %z$.18
    %this.54 = load %class.point*, %class.point** %this$.12
    store %class.point* %this.54, %class.point** %returnValue$.4
    %returnValue.4 = load %class.point*, %class.point** %returnValue$.4
    %b.3 = load %class.point*, %class.point** %b$.0
    %d.2 = load %class.point*, %class.point** %d$.0
    %returnValue$.5 = alloca %class.point*
    %this$.13 = alloca %class.point*
    store %class.point* %b.3, %class.point** %this$.13
    %other$.4 = alloca %class.point*
    store %class.point* %d.2, %class.point** %other$.4
    %this.55 = load %class.point*, %class.point** %this$.13
    %x$.21 = getelementptr %class.point, %class.point* %this.55, i32 0, i32 0
    %x.21 = load i32, i32* %x$.21
    %this.56 = load %class.point*, %class.point** %this$.13
    %x$.22 = getelementptr %class.point, %class.point* %this.56, i32 0, i32 0
    %x.22 = load i32, i32* %x$.22
    %other.12 = load %class.point*, %class.point** %other$.4
    %x$.23 = getelementptr %class.point, %class.point* %other.12, i32 0, i32 0
    %x.23 = load i32, i32* %x$.23
    %sub.3 = sub i32 %x.22, %x.23
    store i32 %sub.3, i32* %x$.21
    %this.57 = load %class.point*, %class.point** %this$.13
    %y$.21 = getelementptr %class.point, %class.point* %this.57, i32 0, i32 1
    %y.21 = load i32, i32* %y$.21
    %this.58 = load %class.point*, %class.point** %this$.13
    %y$.22 = getelementptr %class.point, %class.point* %this.58, i32 0, i32 1
    %y.22 = load i32, i32* %y$.22
    %other.13 = load %class.point*, %class.point** %other$.4
    %y$.23 = getelementptr %class.point, %class.point* %other.13, i32 0, i32 1
    %y.23 = load i32, i32* %y$.23
    %sub.4 = sub i32 %y.22, %y.23
    store i32 %sub.4, i32* %y$.21
    %this.59 = load %class.point*, %class.point** %this$.13
    %z$.21 = getelementptr %class.point, %class.point* %this.59, i32 0, i32 2
    %z.21 = load i32, i32* %z$.21
    %this.60 = load %class.point*, %class.point** %this$.13
    %z$.22 = getelementptr %class.point, %class.point* %this.60, i32 0, i32 2
    %z.22 = load i32, i32* %z$.22
    %other.14 = load %class.point*, %class.point** %other$.4
    %z$.23 = getelementptr %class.point, %class.point* %other.14, i32 0, i32 2
    %z.23 = load i32, i32* %z$.23
    %sub.5 = sub i32 %z.22, %z.23
    store i32 %sub.5, i32* %z$.21
    %this.61 = load %class.point*, %class.point** %this$.13
    store %class.point* %this.61, %class.point** %returnValue$.5
    %returnValue.5 = load %class.point*, %class.point** %returnValue$.5
    %d.3 = load %class.point*, %class.point** %d$.0
    %c.4 = load %class.point*, %class.point** %c$.0
    %returnValue$.6 = alloca %class.point*
    %this$.14 = alloca %class.point*
    store %class.point* %d.3, %class.point** %this$.14
    %other$.5 = alloca %class.point*
    store %class.point* %c.4, %class.point** %other$.5
    %this.62 = load %class.point*, %class.point** %this$.14
    %x$.24 = getelementptr %class.point, %class.point* %this.62, i32 0, i32 0
    %x.24 = load i32, i32* %x$.24
    %this.63 = load %class.point*, %class.point** %this$.14
    %x$.25 = getelementptr %class.point, %class.point* %this.63, i32 0, i32 0
    %x.25 = load i32, i32* %x$.25
    %other.15 = load %class.point*, %class.point** %other$.5
    %x$.26 = getelementptr %class.point, %class.point* %other.15, i32 0, i32 0
    %x.26 = load i32, i32* %x$.26
    %sub.6 = sub i32 %x.25, %x.26
    store i32 %sub.6, i32* %x$.24
    %this.64 = load %class.point*, %class.point** %this$.14
    %y$.24 = getelementptr %class.point, %class.point* %this.64, i32 0, i32 1
    %y.24 = load i32, i32* %y$.24
    %this.65 = load %class.point*, %class.point** %this$.14
    %y$.25 = getelementptr %class.point, %class.point* %this.65, i32 0, i32 1
    %y.25 = load i32, i32* %y$.25
    %other.16 = load %class.point*, %class.point** %other$.5
    %y$.26 = getelementptr %class.point, %class.point* %other.16, i32 0, i32 1
    %y.26 = load i32, i32* %y$.26
    %sub.7 = sub i32 %y.25, %y.26
    store i32 %sub.7, i32* %y$.24
    %this.66 = load %class.point*, %class.point** %this$.14
    %z$.24 = getelementptr %class.point, %class.point* %this.66, i32 0, i32 2
    %z.24 = load i32, i32* %z$.24
    %this.67 = load %class.point*, %class.point** %this$.14
    %z$.25 = getelementptr %class.point, %class.point* %this.67, i32 0, i32 2
    %z.25 = load i32, i32* %z$.25
    %other.17 = load %class.point*, %class.point** %other$.5
    %z$.26 = getelementptr %class.point, %class.point* %other.17, i32 0, i32 2
    %z.26 = load i32, i32* %z$.26
    %sub.8 = sub i32 %z.25, %z.26
    store i32 %sub.8, i32* %z$.24
    %this.68 = load %class.point*, %class.point** %this$.14
    store %class.point* %this.68, %class.point** %returnValue$.6
    %returnValue.6 = load %class.point*, %class.point** %returnValue$.6
    %c.5 = load %class.point*, %class.point** %c$.0
    %b.4 = load %class.point*, %class.point** %b$.0
    %returnValue$.7 = alloca %class.point*
    %this$.15 = alloca %class.point*
    store %class.point* %c.5, %class.point** %this$.15
    %other$.6 = alloca %class.point*
    store %class.point* %b.4, %class.point** %other$.6
    %this.69 = load %class.point*, %class.point** %this$.15
    %x$.27 = getelementptr %class.point, %class.point* %this.69, i32 0, i32 0
    %x.27 = load i32, i32* %x$.27
    %this.70 = load %class.point*, %class.point** %this$.15
    %x$.28 = getelementptr %class.point, %class.point* %this.70, i32 0, i32 0
    %x.28 = load i32, i32* %x$.28
    %other.18 = load %class.point*, %class.point** %other$.6
    %x$.29 = getelementptr %class.point, %class.point* %other.18, i32 0, i32 0
    %x.29 = load i32, i32* %x$.29
    %add.15 = add i32 %x.28, %x.29
    store i32 %add.15, i32* %x$.27
    %this.71 = load %class.point*, %class.point** %this$.15
    %y$.27 = getelementptr %class.point, %class.point* %this.71, i32 0, i32 1
    %y.27 = load i32, i32* %y$.27
    %this.72 = load %class.point*, %class.point** %this$.15
    %y$.28 = getelementptr %class.point, %class.point* %this.72, i32 0, i32 1
    %y.28 = load i32, i32* %y$.28
    %other.19 = load %class.point*, %class.point** %other$.6
    %y$.29 = getelementptr %class.point, %class.point* %other.19, i32 0, i32 1
    %y.29 = load i32, i32* %y$.29
    %add.16 = add i32 %y.28, %y.29
    store i32 %add.16, i32* %y$.27
    %this.73 = load %class.point*, %class.point** %this$.15
    %z$.27 = getelementptr %class.point, %class.point* %this.73, i32 0, i32 2
    %z.27 = load i32, i32* %z$.27
    %this.74 = load %class.point*, %class.point** %this$.15
    %z$.28 = getelementptr %class.point, %class.point* %this.74, i32 0, i32 2
    %z.28 = load i32, i32* %z$.28
    %other.20 = load %class.point*, %class.point** %other$.6
    %z$.29 = getelementptr %class.point, %class.point* %other.20, i32 0, i32 2
    %z.29 = load i32, i32* %z$.29
    %add.17 = add i32 %z.28, %z.29
    store i32 %add.17, i32* %z$.27
    %this.75 = load %class.point*, %class.point** %this$.15
    store %class.point* %this.75, %class.point** %returnValue$.7
    %returnValue.7 = load %class.point*, %class.point** %returnValue$.7
    %a.4 = load %class.point*, %class.point** %a$.0
    %b.5 = load %class.point*, %class.point** %b$.0
    %returnValue$.8 = alloca %class.point*
    %this$.16 = alloca %class.point*
    store %class.point* %a.4, %class.point** %this$.16
    %other$.7 = alloca %class.point*
    store %class.point* %b.5, %class.point** %other$.7
    %this.76 = load %class.point*, %class.point** %this$.16
    %x$.30 = getelementptr %class.point, %class.point* %this.76, i32 0, i32 0
    %x.30 = load i32, i32* %x$.30
    %this.77 = load %class.point*, %class.point** %this$.16
    %x$.31 = getelementptr %class.point, %class.point* %this.77, i32 0, i32 0
    %x.31 = load i32, i32* %x$.31
    %other.21 = load %class.point*, %class.point** %other$.7
    %x$.32 = getelementptr %class.point, %class.point* %other.21, i32 0, i32 0
    %x.32 = load i32, i32* %x$.32
    %add.18 = add i32 %x.31, %x.32
    store i32 %add.18, i32* %x$.30
    %this.78 = load %class.point*, %class.point** %this$.16
    %y$.30 = getelementptr %class.point, %class.point* %this.78, i32 0, i32 1
    %y.30 = load i32, i32* %y$.30
    %this.79 = load %class.point*, %class.point** %this$.16
    %y$.31 = getelementptr %class.point, %class.point* %this.79, i32 0, i32 1
    %y.31 = load i32, i32* %y$.31
    %other.22 = load %class.point*, %class.point** %other$.7
    %y$.32 = getelementptr %class.point, %class.point* %other.22, i32 0, i32 1
    %y.32 = load i32, i32* %y$.32
    %add.19 = add i32 %y.31, %y.32
    store i32 %add.19, i32* %y$.30
    %this.80 = load %class.point*, %class.point** %this$.16
    %z$.30 = getelementptr %class.point, %class.point* %this.80, i32 0, i32 2
    %z.30 = load i32, i32* %z$.30
    %this.81 = load %class.point*, %class.point** %this$.16
    %z$.31 = getelementptr %class.point, %class.point* %this.81, i32 0, i32 2
    %z.31 = load i32, i32* %z$.31
    %other.23 = load %class.point*, %class.point** %other$.7
    %z$.32 = getelementptr %class.point, %class.point* %other.23, i32 0, i32 2
    %z.32 = load i32, i32* %z$.32
    %add.20 = add i32 %z.31, %z.32
    store i32 %add.20, i32* %z$.30
    %this.82 = load %class.point*, %class.point** %this$.16
    store %class.point* %this.82, %class.point** %returnValue$.8
    %returnValue.8 = load %class.point*, %class.point** %returnValue$.8
    %b.6 = load %class.point*, %class.point** %b$.0
    %b.7 = load %class.point*, %class.point** %b$.0
    %returnValue$.9 = alloca %class.point*
    %this$.17 = alloca %class.point*
    store %class.point* %b.6, %class.point** %this$.17
    %other$.8 = alloca %class.point*
    store %class.point* %b.7, %class.point** %other$.8
    %this.83 = load %class.point*, %class.point** %this$.17
    %x$.33 = getelementptr %class.point, %class.point* %this.83, i32 0, i32 0
    %x.33 = load i32, i32* %x$.33
    %this.84 = load %class.point*, %class.point** %this$.17
    %x$.34 = getelementptr %class.point, %class.point* %this.84, i32 0, i32 0
    %x.34 = load i32, i32* %x$.34
    %other.24 = load %class.point*, %class.point** %other$.8
    %x$.35 = getelementptr %class.point, %class.point* %other.24, i32 0, i32 0
    %x.35 = load i32, i32* %x$.35
    %add.21 = add i32 %x.34, %x.35
    store i32 %add.21, i32* %x$.33
    %this.85 = load %class.point*, %class.point** %this$.17
    %y$.33 = getelementptr %class.point, %class.point* %this.85, i32 0, i32 1
    %y.33 = load i32, i32* %y$.33
    %this.86 = load %class.point*, %class.point** %this$.17
    %y$.34 = getelementptr %class.point, %class.point* %this.86, i32 0, i32 1
    %y.34 = load i32, i32* %y$.34
    %other.25 = load %class.point*, %class.point** %other$.8
    %y$.35 = getelementptr %class.point, %class.point* %other.25, i32 0, i32 1
    %y.35 = load i32, i32* %y$.35
    %add.22 = add i32 %y.34, %y.35
    store i32 %add.22, i32* %y$.33
    %this.87 = load %class.point*, %class.point** %this$.17
    %z$.33 = getelementptr %class.point, %class.point* %this.87, i32 0, i32 2
    %z.33 = load i32, i32* %z$.33
    %this.88 = load %class.point*, %class.point** %this$.17
    %z$.34 = getelementptr %class.point, %class.point* %this.88, i32 0, i32 2
    %z.34 = load i32, i32* %z$.34
    %other.26 = load %class.point*, %class.point** %other$.8
    %z$.35 = getelementptr %class.point, %class.point* %other.26, i32 0, i32 2
    %z.35 = load i32, i32* %z$.35
    %add.23 = add i32 %z.34, %z.35
    store i32 %add.23, i32* %z$.33
    %this.89 = load %class.point*, %class.point** %this$.17
    store %class.point* %this.89, %class.point** %returnValue$.9
    %returnValue.9 = load %class.point*, %class.point** %returnValue$.9
    %c.6 = load %class.point*, %class.point** %c$.0
    %c.7 = load %class.point*, %class.point** %c$.0
    %returnValue$.10 = alloca %class.point*
    %this$.18 = alloca %class.point*
    store %class.point* %c.6, %class.point** %this$.18
    %other$.9 = alloca %class.point*
    store %class.point* %c.7, %class.point** %other$.9
    %this.90 = load %class.point*, %class.point** %this$.18
    %x$.36 = getelementptr %class.point, %class.point* %this.90, i32 0, i32 0
    %x.36 = load i32, i32* %x$.36
    %this.91 = load %class.point*, %class.point** %this$.18
    %x$.37 = getelementptr %class.point, %class.point* %this.91, i32 0, i32 0
    %x.37 = load i32, i32* %x$.37
    %other.27 = load %class.point*, %class.point** %other$.9
    %x$.38 = getelementptr %class.point, %class.point* %other.27, i32 0, i32 0
    %x.38 = load i32, i32* %x$.38
    %add.24 = add i32 %x.37, %x.38
    store i32 %add.24, i32* %x$.36
    %this.92 = load %class.point*, %class.point** %this$.18
    %y$.36 = getelementptr %class.point, %class.point* %this.92, i32 0, i32 1
    %y.36 = load i32, i32* %y$.36
    %this.93 = load %class.point*, %class.point** %this$.18
    %y$.37 = getelementptr %class.point, %class.point* %this.93, i32 0, i32 1
    %y.37 = load i32, i32* %y$.37
    %other.28 = load %class.point*, %class.point** %other$.9
    %y$.38 = getelementptr %class.point, %class.point* %other.28, i32 0, i32 1
    %y.38 = load i32, i32* %y$.38
    %add.25 = add i32 %y.37, %y.38
    store i32 %add.25, i32* %y$.36
    %this.94 = load %class.point*, %class.point** %this$.18
    %z$.36 = getelementptr %class.point, %class.point* %this.94, i32 0, i32 2
    %z.36 = load i32, i32* %z$.36
    %this.95 = load %class.point*, %class.point** %this$.18
    %z$.37 = getelementptr %class.point, %class.point* %this.95, i32 0, i32 2
    %z.37 = load i32, i32* %z$.37
    %other.29 = load %class.point*, %class.point** %other$.9
    %z$.38 = getelementptr %class.point, %class.point* %other.29, i32 0, i32 2
    %z.38 = load i32, i32* %z$.38
    %add.26 = add i32 %z.37, %z.38
    store i32 %add.26, i32* %z$.36
    %this.96 = load %class.point*, %class.point** %this$.18
    store %class.point* %this.96, %class.point** %returnValue$.10
    %returnValue.10 = load %class.point*, %class.point** %returnValue$.10
    %a.5 = load %class.point*, %class.point** %a$.0
    %d.4 = load %class.point*, %class.point** %d$.0
    %returnValue$.11 = alloca %class.point*
    %this$.19 = alloca %class.point*
    store %class.point* %a.5, %class.point** %this$.19
    %other$.10 = alloca %class.point*
    store %class.point* %d.4, %class.point** %other$.10
    %this.97 = load %class.point*, %class.point** %this$.19
    %x$.39 = getelementptr %class.point, %class.point* %this.97, i32 0, i32 0
    %x.39 = load i32, i32* %x$.39
    %this.98 = load %class.point*, %class.point** %this$.19
    %x$.40 = getelementptr %class.point, %class.point* %this.98, i32 0, i32 0
    %x.40 = load i32, i32* %x$.40
    %other.30 = load %class.point*, %class.point** %other$.10
    %x$.41 = getelementptr %class.point, %class.point* %other.30, i32 0, i32 0
    %x.41 = load i32, i32* %x$.41
    %sub.9 = sub i32 %x.40, %x.41
    store i32 %sub.9, i32* %x$.39
    %this.99 = load %class.point*, %class.point** %this$.19
    %y$.39 = getelementptr %class.point, %class.point* %this.99, i32 0, i32 1
    %y.39 = load i32, i32* %y$.39
    %this.100 = load %class.point*, %class.point** %this$.19
    %y$.40 = getelementptr %class.point, %class.point* %this.100, i32 0, i32 1
    %y.40 = load i32, i32* %y$.40
    %other.31 = load %class.point*, %class.point** %other$.10
    %y$.41 = getelementptr %class.point, %class.point* %other.31, i32 0, i32 1
    %y.41 = load i32, i32* %y$.41
    %sub.10 = sub i32 %y.40, %y.41
    store i32 %sub.10, i32* %y$.39
    %this.101 = load %class.point*, %class.point** %this$.19
    %z$.39 = getelementptr %class.point, %class.point* %this.101, i32 0, i32 2
    %z.39 = load i32, i32* %z$.39
    %this.102 = load %class.point*, %class.point** %this$.19
    %z$.40 = getelementptr %class.point, %class.point* %this.102, i32 0, i32 2
    %z.40 = load i32, i32* %z$.40
    %other.32 = load %class.point*, %class.point** %other$.10
    %z$.41 = getelementptr %class.point, %class.point* %other.32, i32 0, i32 2
    %z.41 = load i32, i32* %z$.41
    %sub.11 = sub i32 %z.40, %z.41
    store i32 %sub.11, i32* %z$.39
    %this.103 = load %class.point*, %class.point** %this$.19
    store %class.point* %this.103, %class.point** %returnValue$.11
    %returnValue.11 = load %class.point*, %class.point** %returnValue$.11
    %a.6 = load %class.point*, %class.point** %a$.0
    %b.8 = load %class.point*, %class.point** %b$.0
    %returnValue$.12 = alloca %class.point*
    %this$.20 = alloca %class.point*
    store %class.point* %a.6, %class.point** %this$.20
    %other$.11 = alloca %class.point*
    store %class.point* %b.8, %class.point** %other$.11
    %this.104 = load %class.point*, %class.point** %this$.20
    %x$.42 = getelementptr %class.point, %class.point* %this.104, i32 0, i32 0
    %x.42 = load i32, i32* %x$.42
    %this.105 = load %class.point*, %class.point** %this$.20
    %x$.43 = getelementptr %class.point, %class.point* %this.105, i32 0, i32 0
    %x.43 = load i32, i32* %x$.43
    %other.33 = load %class.point*, %class.point** %other$.11
    %x$.44 = getelementptr %class.point, %class.point* %other.33, i32 0, i32 0
    %x.44 = load i32, i32* %x$.44
    %add.27 = add i32 %x.43, %x.44
    store i32 %add.27, i32* %x$.42
    %this.106 = load %class.point*, %class.point** %this$.20
    %y$.42 = getelementptr %class.point, %class.point* %this.106, i32 0, i32 1
    %y.42 = load i32, i32* %y$.42
    %this.107 = load %class.point*, %class.point** %this$.20
    %y$.43 = getelementptr %class.point, %class.point* %this.107, i32 0, i32 1
    %y.43 = load i32, i32* %y$.43
    %other.34 = load %class.point*, %class.point** %other$.11
    %y$.44 = getelementptr %class.point, %class.point* %other.34, i32 0, i32 1
    %y.44 = load i32, i32* %y$.44
    %add.28 = add i32 %y.43, %y.44
    store i32 %add.28, i32* %y$.42
    %this.108 = load %class.point*, %class.point** %this$.20
    %z$.42 = getelementptr %class.point, %class.point* %this.108, i32 0, i32 2
    %z.42 = load i32, i32* %z$.42
    %this.109 = load %class.point*, %class.point** %this$.20
    %z$.43 = getelementptr %class.point, %class.point* %this.109, i32 0, i32 2
    %z.43 = load i32, i32* %z$.43
    %other.35 = load %class.point*, %class.point** %other$.11
    %z$.44 = getelementptr %class.point, %class.point* %other.35, i32 0, i32 2
    %z.44 = load i32, i32* %z$.44
    %add.29 = add i32 %z.43, %z.44
    store i32 %add.29, i32* %z$.42
    %this.110 = load %class.point*, %class.point** %this$.20
    store %class.point* %this.110, %class.point** %returnValue$.12
    %returnValue.12 = load %class.point*, %class.point** %returnValue$.12
    %b.9 = load %class.point*, %class.point** %b$.0
    %c.8 = load %class.point*, %class.point** %c$.0
    %returnValue$.13 = alloca %class.point*
    %this$.21 = alloca %class.point*
    store %class.point* %b.9, %class.point** %this$.21
    %other$.12 = alloca %class.point*
    store %class.point* %c.8, %class.point** %other$.12
    %this.111 = load %class.point*, %class.point** %this$.21
    %x$.45 = getelementptr %class.point, %class.point* %this.111, i32 0, i32 0
    %x.45 = load i32, i32* %x$.45
    %this.112 = load %class.point*, %class.point** %this$.21
    %x$.46 = getelementptr %class.point, %class.point* %this.112, i32 0, i32 0
    %x.46 = load i32, i32* %x$.46
    %other.36 = load %class.point*, %class.point** %other$.12
    %x$.47 = getelementptr %class.point, %class.point* %other.36, i32 0, i32 0
    %x.47 = load i32, i32* %x$.47
    %sub.12 = sub i32 %x.46, %x.47
    store i32 %sub.12, i32* %x$.45
    %this.113 = load %class.point*, %class.point** %this$.21
    %y$.45 = getelementptr %class.point, %class.point* %this.113, i32 0, i32 1
    %y.45 = load i32, i32* %y$.45
    %this.114 = load %class.point*, %class.point** %this$.21
    %y$.46 = getelementptr %class.point, %class.point* %this.114, i32 0, i32 1
    %y.46 = load i32, i32* %y$.46
    %other.37 = load %class.point*, %class.point** %other$.12
    %y$.47 = getelementptr %class.point, %class.point* %other.37, i32 0, i32 1
    %y.47 = load i32, i32* %y$.47
    %sub.13 = sub i32 %y.46, %y.47
    store i32 %sub.13, i32* %y$.45
    %this.115 = load %class.point*, %class.point** %this$.21
    %z$.45 = getelementptr %class.point, %class.point* %this.115, i32 0, i32 2
    %z.45 = load i32, i32* %z$.45
    %this.116 = load %class.point*, %class.point** %this$.21
    %z$.46 = getelementptr %class.point, %class.point* %this.116, i32 0, i32 2
    %z.46 = load i32, i32* %z$.46
    %other.38 = load %class.point*, %class.point** %other$.12
    %z$.47 = getelementptr %class.point, %class.point* %other.38, i32 0, i32 2
    %z.47 = load i32, i32* %z$.47
    %sub.14 = sub i32 %z.46, %z.47
    store i32 %sub.14, i32* %z$.45
    %this.117 = load %class.point*, %class.point** %this$.21
    store %class.point* %this.117, %class.point** %returnValue$.13
    %returnValue.13 = load %class.point*, %class.point** %returnValue$.13
    %a.7 = load %class.point*, %class.point** %a$.0
    %returnValue$.14 = alloca i32
    %this$.22 = alloca %class.point*
    store %class.point* %a.7, %class.point** %this$.22
    %this.118 = load %class.point*, %class.point** %this$.22
    %x$.48 = getelementptr %class.point, %class.point* %this.118, i32 0, i32 0
    %x.48 = load i32, i32* %x$.48
    %this.119 = load %class.point*, %class.point** %this$.22
    %x$.49 = getelementptr %class.point, %class.point* %this.119, i32 0, i32 0
    %x.49 = load i32, i32* %x$.49
    %mul.0 = mul i32 %x.48, %x.49
    %this.120 = load %class.point*, %class.point** %this$.22
    %y$.48 = getelementptr %class.point, %class.point* %this.120, i32 0, i32 1
    %y.48 = load i32, i32* %y$.48
    %this.121 = load %class.point*, %class.point** %this$.22
    %y$.49 = getelementptr %class.point, %class.point* %this.121, i32 0, i32 1
    %y.49 = load i32, i32* %y$.49
    %mul.1 = mul i32 %y.48, %y.49
    %add.30 = add i32 %mul.0, %mul.1
    %this.122 = load %class.point*, %class.point** %this$.22
    %z$.48 = getelementptr %class.point, %class.point* %this.122, i32 0, i32 2
    %z.48 = load i32, i32* %z$.48
    %this.123 = load %class.point*, %class.point** %this$.22
    %z$.49 = getelementptr %class.point, %class.point* %this.123, i32 0, i32 2
    %z.49 = load i32, i32* %z$.49
    %mul.2 = mul i32 %z.48, %z.49
    %add.31 = add i32 %add.30, %mul.2
    store i32 %add.31, i32* %returnValue$.14
    %returnValue.14 = load i32, i32* %returnValue$.14
    %call.13 = call i8* @__toString(i32 %returnValue.14)
    call void @__println(i8* %call.13)
    %b.10 = load %class.point*, %class.point** %b$.0
    %returnValue$.15 = alloca i32
    %this$.23 = alloca %class.point*
    store %class.point* %b.10, %class.point** %this$.23
    %this.124 = load %class.point*, %class.point** %this$.23
    %x$.50 = getelementptr %class.point, %class.point* %this.124, i32 0, i32 0
    %x.50 = load i32, i32* %x$.50
    %this.125 = load %class.point*, %class.point** %this$.23
    %x$.51 = getelementptr %class.point, %class.point* %this.125, i32 0, i32 0
    %x.51 = load i32, i32* %x$.51
    %mul.3 = mul i32 %x.50, %x.51
    %this.126 = load %class.point*, %class.point** %this$.23
    %y$.50 = getelementptr %class.point, %class.point* %this.126, i32 0, i32 1
    %y.50 = load i32, i32* %y$.50
    %this.127 = load %class.point*, %class.point** %this$.23
    %y$.51 = getelementptr %class.point, %class.point* %this.127, i32 0, i32 1
    %y.51 = load i32, i32* %y$.51
    %mul.4 = mul i32 %y.50, %y.51
    %add.32 = add i32 %mul.3, %mul.4
    %this.128 = load %class.point*, %class.point** %this$.23
    %z$.50 = getelementptr %class.point, %class.point* %this.128, i32 0, i32 2
    %z.50 = load i32, i32* %z$.50
    %this.129 = load %class.point*, %class.point** %this$.23
    %z$.51 = getelementptr %class.point, %class.point* %this.129, i32 0, i32 2
    %z.51 = load i32, i32* %z$.51
    %mul.5 = mul i32 %z.50, %z.51
    %add.33 = add i32 %add.32, %mul.5
    store i32 %add.33, i32* %returnValue$.15
    %returnValue.15 = load i32, i32* %returnValue$.15
    %call.15 = call i8* @__toString(i32 %returnValue.15)
    call void @__println(i8* %call.15)
    %b.11 = load %class.point*, %class.point** %b$.0
    %c.9 = load %class.point*, %class.point** %c$.0
    %returnValue$.16 = alloca i32
    %this$.24 = alloca %class.point*
    store %class.point* %b.11, %class.point** %this$.24
    %other$.13 = alloca %class.point*
    store %class.point* %c.9, %class.point** %other$.13
    %this.130 = load %class.point*, %class.point** %this$.24
    %x$.52 = getelementptr %class.point, %class.point* %this.130, i32 0, i32 0
    %x.52 = load i32, i32* %x$.52
    %other.39 = load %class.point*, %class.point** %other$.13
    %x$.53 = getelementptr %class.point, %class.point* %other.39, i32 0, i32 0
    %x.53 = load i32, i32* %x$.53
    %sub.15 = sub i32 %x.52, %x.53
    %this.131 = load %class.point*, %class.point** %this$.24
    %x$.54 = getelementptr %class.point, %class.point* %this.131, i32 0, i32 0
    %x.54 = load i32, i32* %x$.54
    %other.40 = load %class.point*, %class.point** %other$.13
    %x$.55 = getelementptr %class.point, %class.point* %other.40, i32 0, i32 0
    %x.55 = load i32, i32* %x$.55
    %sub.16 = sub i32 %x.54, %x.55
    %mul.6 = mul i32 %sub.15, %sub.16
    %this.132 = load %class.point*, %class.point** %this$.24
    %y$.52 = getelementptr %class.point, %class.point* %this.132, i32 0, i32 1
    %y.52 = load i32, i32* %y$.52
    %other.41 = load %class.point*, %class.point** %other$.13
    %y$.53 = getelementptr %class.point, %class.point* %other.41, i32 0, i32 1
    %y.53 = load i32, i32* %y$.53
    %sub.17 = sub i32 %y.52, %y.53
    %this.133 = load %class.point*, %class.point** %this$.24
    %y$.54 = getelementptr %class.point, %class.point* %this.133, i32 0, i32 1
    %y.54 = load i32, i32* %y$.54
    %other.42 = load %class.point*, %class.point** %other$.13
    %y$.55 = getelementptr %class.point, %class.point* %other.42, i32 0, i32 1
    %y.55 = load i32, i32* %y$.55
    %sub.18 = sub i32 %y.54, %y.55
    %mul.7 = mul i32 %sub.17, %sub.18
    %add.34 = add i32 %mul.6, %mul.7
    %this.134 = load %class.point*, %class.point** %this$.24
    %z$.52 = getelementptr %class.point, %class.point* %this.134, i32 0, i32 2
    %z.52 = load i32, i32* %z$.52
    %other.43 = load %class.point*, %class.point** %other$.13
    %z$.53 = getelementptr %class.point, %class.point* %other.43, i32 0, i32 2
    %z.53 = load i32, i32* %z$.53
    %sub.19 = sub i32 %z.52, %z.53
    %this.135 = load %class.point*, %class.point** %this$.24
    %z$.54 = getelementptr %class.point, %class.point* %this.135, i32 0, i32 2
    %z.54 = load i32, i32* %z$.54
    %other.44 = load %class.point*, %class.point** %other$.13
    %z$.55 = getelementptr %class.point, %class.point* %other.44, i32 0, i32 2
    %z.55 = load i32, i32* %z$.55
    %sub.20 = sub i32 %z.54, %z.55
    %mul.8 = mul i32 %sub.19, %sub.20
    %add.35 = add i32 %add.34, %mul.8
    store i32 %add.35, i32* %returnValue$.16
    %returnValue.16 = load i32, i32* %returnValue$.16
    %call.17 = call i8* @__toString(i32 %returnValue.16)
    call void @__println(i8* %call.17)
    %d.5 = load %class.point*, %class.point** %d$.0
    %a.8 = load %class.point*, %class.point** %a$.0
    %returnValue$.17 = alloca i32
    %this$.25 = alloca %class.point*
    store %class.point* %d.5, %class.point** %this$.25
    %other$.14 = alloca %class.point*
    store %class.point* %a.8, %class.point** %other$.14
    %this.136 = load %class.point*, %class.point** %this$.25
    %x$.56 = getelementptr %class.point, %class.point* %this.136, i32 0, i32 0
    %x.56 = load i32, i32* %x$.56
    %other.45 = load %class.point*, %class.point** %other$.14
    %x$.57 = getelementptr %class.point, %class.point* %other.45, i32 0, i32 0
    %x.57 = load i32, i32* %x$.57
    %sub.21 = sub i32 %x.56, %x.57
    %this.137 = load %class.point*, %class.point** %this$.25
    %x$.58 = getelementptr %class.point, %class.point* %this.137, i32 0, i32 0
    %x.58 = load i32, i32* %x$.58
    %other.46 = load %class.point*, %class.point** %other$.14
    %x$.59 = getelementptr %class.point, %class.point* %other.46, i32 0, i32 0
    %x.59 = load i32, i32* %x$.59
    %sub.22 = sub i32 %x.58, %x.59
    %mul.9 = mul i32 %sub.21, %sub.22
    %this.138 = load %class.point*, %class.point** %this$.25
    %y$.56 = getelementptr %class.point, %class.point* %this.138, i32 0, i32 1
    %y.56 = load i32, i32* %y$.56
    %other.47 = load %class.point*, %class.point** %other$.14
    %y$.57 = getelementptr %class.point, %class.point* %other.47, i32 0, i32 1
    %y.57 = load i32, i32* %y$.57
    %sub.23 = sub i32 %y.56, %y.57
    %this.139 = load %class.point*, %class.point** %this$.25
    %y$.58 = getelementptr %class.point, %class.point* %this.139, i32 0, i32 1
    %y.58 = load i32, i32* %y$.58
    %other.48 = load %class.point*, %class.point** %other$.14
    %y$.59 = getelementptr %class.point, %class.point* %other.48, i32 0, i32 1
    %y.59 = load i32, i32* %y$.59
    %sub.24 = sub i32 %y.58, %y.59
    %mul.10 = mul i32 %sub.23, %sub.24
    %add.36 = add i32 %mul.9, %mul.10
    %this.140 = load %class.point*, %class.point** %this$.25
    %z$.56 = getelementptr %class.point, %class.point* %this.140, i32 0, i32 2
    %z.56 = load i32, i32* %z$.56
    %other.49 = load %class.point*, %class.point** %other$.14
    %z$.57 = getelementptr %class.point, %class.point* %other.49, i32 0, i32 2
    %z.57 = load i32, i32* %z$.57
    %sub.25 = sub i32 %z.56, %z.57
    %this.141 = load %class.point*, %class.point** %this$.25
    %z$.58 = getelementptr %class.point, %class.point* %this.141, i32 0, i32 2
    %z.58 = load i32, i32* %z$.58
    %other.50 = load %class.point*, %class.point** %other$.14
    %z$.59 = getelementptr %class.point, %class.point* %other.50, i32 0, i32 2
    %z.59 = load i32, i32* %z$.59
    %sub.26 = sub i32 %z.58, %z.59
    %mul.11 = mul i32 %sub.25, %sub.26
    %add.37 = add i32 %add.36, %mul.11
    store i32 %add.37, i32* %returnValue$.17
    %returnValue.17 = load i32, i32* %returnValue$.17
    %call.19 = call i8* @__toString(i32 %returnValue.17)
    call void @__println(i8* %call.19)
    %c.10 = load %class.point*, %class.point** %c$.0
    %a.9 = load %class.point*, %class.point** %a$.0
    %returnValue$.18 = alloca i32
    %this$.26 = alloca %class.point*
    store %class.point* %c.10, %class.point** %this$.26
    %other$.15 = alloca %class.point*
    store %class.point* %a.9, %class.point** %other$.15
    %this.142 = load %class.point*, %class.point** %this$.26
    %x$.60 = getelementptr %class.point, %class.point* %this.142, i32 0, i32 0
    %x.60 = load i32, i32* %x$.60
    %other.51 = load %class.point*, %class.point** %other$.15
    %x$.61 = getelementptr %class.point, %class.point* %other.51, i32 0, i32 0
    %x.61 = load i32, i32* %x$.61
    %mul.12 = mul i32 %x.60, %x.61
    %this.143 = load %class.point*, %class.point** %this$.26
    %y$.60 = getelementptr %class.point, %class.point* %this.143, i32 0, i32 1
    %y.60 = load i32, i32* %y$.60
    %other.52 = load %class.point*, %class.point** %other$.15
    %y$.61 = getelementptr %class.point, %class.point* %other.52, i32 0, i32 1
    %y.61 = load i32, i32* %y$.61
    %mul.13 = mul i32 %y.60, %y.61
    %add.38 = add i32 %mul.12, %mul.13
    %this.144 = load %class.point*, %class.point** %this$.26
    %z$.60 = getelementptr %class.point, %class.point* %this.144, i32 0, i32 2
    %z.60 = load i32, i32* %z$.60
    %other.53 = load %class.point*, %class.point** %other$.15
    %z$.61 = getelementptr %class.point, %class.point* %other.53, i32 0, i32 2
    %z.61 = load i32, i32* %z$.61
    %mul.14 = mul i32 %z.60, %z.61
    %add.39 = add i32 %add.38, %mul.14
    store i32 %add.39, i32* %returnValue$.18
    %returnValue.18 = load i32, i32* %returnValue$.18
    %call.21 = call i8* @__toString(i32 %returnValue.18)
    call void @__println(i8* %call.21)
    %b.12 = load %class.point*, %class.point** %b$.0
    %d.6 = load %class.point*, %class.point** %d$.0
    %call.23 = call %class.point* @point.cross(%class.point* %b.12, %class.point* %d.6)
    %this$.27 = alloca %class.point*
    store %class.point* %call.23, %class.point** %this$.27
    %__stringLiteral.4 = getelementptr [2 x i8], [2 x i8]* @.str.0, i32 0, i32 0
    %this.145 = load %class.point*, %class.point** %this$.27
    %x$.62 = getelementptr %class.point, %class.point* %this.145, i32 0, i32 0
    %x.62 = load i32, i32* %x$.62
    %call.27 = call i8* @__toString(i32 %x.62)
    %add.40 = call i8* @__stringAdd(i8* %__stringLiteral.4, i8* %call.27)
    %__stringLiteral.5 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.41 = call i8* @__stringAdd(i8* %add.40, i8* %__stringLiteral.5)
    %this.146 = load %class.point*, %class.point** %this$.27
    %y$.62 = getelementptr %class.point, %class.point* %this.146, i32 0, i32 1
    %y.62 = load i32, i32* %y$.62
    %call.28 = call i8* @__toString(i32 %y.62)
    %add.42 = call i8* @__stringAdd(i8* %add.41, i8* %call.28)
    %__stringLiteral.6 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.43 = call i8* @__stringAdd(i8* %add.42, i8* %__stringLiteral.6)
    %this.147 = load %class.point*, %class.point** %this$.27
    %z$.62 = getelementptr %class.point, %class.point* %this.147, i32 0, i32 2
    %z.62 = load i32, i32* %z$.62
    %call.29 = call i8* @__toString(i32 %z.62)
    %add.44 = call i8* @__stringAdd(i8* %add.43, i8* %call.29)
    %__stringLiteral.7 = getelementptr [2 x i8], [2 x i8]* @.str.2, i32 0, i32 0
    %add.45 = call i8* @__stringAdd(i8* %add.44, i8* %__stringLiteral.7)
    call void @__println(i8* %add.45)
    %a.10 = load %class.point*, %class.point** %a$.0
    %this$.28 = alloca %class.point*
    store %class.point* %a.10, %class.point** %this$.28
    %__stringLiteral.8 = getelementptr [2 x i8], [2 x i8]* @.str.0, i32 0, i32 0
    %this.148 = load %class.point*, %class.point** %this$.28
    %x$.63 = getelementptr %class.point, %class.point* %this.148, i32 0, i32 0
    %x.63 = load i32, i32* %x$.63
    %call.30 = call i8* @__toString(i32 %x.63)
    %add.46 = call i8* @__stringAdd(i8* %__stringLiteral.8, i8* %call.30)
    %__stringLiteral.9 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.47 = call i8* @__stringAdd(i8* %add.46, i8* %__stringLiteral.9)
    %this.149 = load %class.point*, %class.point** %this$.28
    %y$.63 = getelementptr %class.point, %class.point* %this.149, i32 0, i32 1
    %y.63 = load i32, i32* %y$.63
    %call.31 = call i8* @__toString(i32 %y.63)
    %add.48 = call i8* @__stringAdd(i8* %add.47, i8* %call.31)
    %__stringLiteral.10 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.49 = call i8* @__stringAdd(i8* %add.48, i8* %__stringLiteral.10)
    %this.150 = load %class.point*, %class.point** %this$.28
    %z$.63 = getelementptr %class.point, %class.point* %this.150, i32 0, i32 2
    %z.63 = load i32, i32* %z$.63
    %call.32 = call i8* @__toString(i32 %z.63)
    %add.50 = call i8* @__stringAdd(i8* %add.49, i8* %call.32)
    %__stringLiteral.11 = getelementptr [2 x i8], [2 x i8]* @.str.2, i32 0, i32 0
    %add.51 = call i8* @__stringAdd(i8* %add.50, i8* %__stringLiteral.11)
    call void @__println(i8* %add.51)
    %b.13 = load %class.point*, %class.point** %b$.0
    %this$.29 = alloca %class.point*
    store %class.point* %b.13, %class.point** %this$.29
    %__stringLiteral.12 = getelementptr [2 x i8], [2 x i8]* @.str.0, i32 0, i32 0
    %this.151 = load %class.point*, %class.point** %this$.29
    %x$.64 = getelementptr %class.point, %class.point* %this.151, i32 0, i32 0
    %x.64 = load i32, i32* %x$.64
    %call.33 = call i8* @__toString(i32 %x.64)
    %add.52 = call i8* @__stringAdd(i8* %__stringLiteral.12, i8* %call.33)
    %__stringLiteral.13 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.53 = call i8* @__stringAdd(i8* %add.52, i8* %__stringLiteral.13)
    %this.152 = load %class.point*, %class.point** %this$.29
    %y$.64 = getelementptr %class.point, %class.point* %this.152, i32 0, i32 1
    %y.64 = load i32, i32* %y$.64
    %call.34 = call i8* @__toString(i32 %y.64)
    %add.54 = call i8* @__stringAdd(i8* %add.53, i8* %call.34)
    %__stringLiteral.14 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.55 = call i8* @__stringAdd(i8* %add.54, i8* %__stringLiteral.14)
    %this.153 = load %class.point*, %class.point** %this$.29
    %z$.64 = getelementptr %class.point, %class.point* %this.153, i32 0, i32 2
    %z.64 = load i32, i32* %z$.64
    %call.35 = call i8* @__toString(i32 %z.64)
    %add.56 = call i8* @__stringAdd(i8* %add.55, i8* %call.35)
    %__stringLiteral.15 = getelementptr [2 x i8], [2 x i8]* @.str.2, i32 0, i32 0
    %add.57 = call i8* @__stringAdd(i8* %add.56, i8* %__stringLiteral.15)
    call void @__println(i8* %add.57)
    %c.11 = load %class.point*, %class.point** %c$.0
    %this$.30 = alloca %class.point*
    store %class.point* %c.11, %class.point** %this$.30
    %__stringLiteral.16 = getelementptr [2 x i8], [2 x i8]* @.str.0, i32 0, i32 0
    %this.154 = load %class.point*, %class.point** %this$.30
    %x$.65 = getelementptr %class.point, %class.point* %this.154, i32 0, i32 0
    %x.65 = load i32, i32* %x$.65
    %call.36 = call i8* @__toString(i32 %x.65)
    %add.58 = call i8* @__stringAdd(i8* %__stringLiteral.16, i8* %call.36)
    %__stringLiteral.17 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.59 = call i8* @__stringAdd(i8* %add.58, i8* %__stringLiteral.17)
    %this.155 = load %class.point*, %class.point** %this$.30
    %y$.65 = getelementptr %class.point, %class.point* %this.155, i32 0, i32 1
    %y.65 = load i32, i32* %y$.65
    %call.37 = call i8* @__toString(i32 %y.65)
    %add.60 = call i8* @__stringAdd(i8* %add.59, i8* %call.37)
    %__stringLiteral.18 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.61 = call i8* @__stringAdd(i8* %add.60, i8* %__stringLiteral.18)
    %this.156 = load %class.point*, %class.point** %this$.30
    %z$.65 = getelementptr %class.point, %class.point* %this.156, i32 0, i32 2
    %z.65 = load i32, i32* %z$.65
    %call.38 = call i8* @__toString(i32 %z.65)
    %add.62 = call i8* @__stringAdd(i8* %add.61, i8* %call.38)
    %__stringLiteral.19 = getelementptr [2 x i8], [2 x i8]* @.str.2, i32 0, i32 0
    %add.63 = call i8* @__stringAdd(i8* %add.62, i8* %__stringLiteral.19)
    call void @__println(i8* %add.63)
    %d.7 = load %class.point*, %class.point** %d$.0
    %this$.31 = alloca %class.point*
    store %class.point* %d.7, %class.point** %this$.31
    %__stringLiteral.20 = getelementptr [2 x i8], [2 x i8]* @.str.0, i32 0, i32 0
    %this.157 = load %class.point*, %class.point** %this$.31
    %x$.66 = getelementptr %class.point, %class.point* %this.157, i32 0, i32 0
    %x.66 = load i32, i32* %x$.66
    %call.39 = call i8* @__toString(i32 %x.66)
    %add.64 = call i8* @__stringAdd(i8* %__stringLiteral.20, i8* %call.39)
    %__stringLiteral.21 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.65 = call i8* @__stringAdd(i8* %add.64, i8* %__stringLiteral.21)
    %this.158 = load %class.point*, %class.point** %this$.31
    %y$.66 = getelementptr %class.point, %class.point* %this.158, i32 0, i32 1
    %y.66 = load i32, i32* %y$.66
    %call.40 = call i8* @__toString(i32 %y.66)
    %add.66 = call i8* @__stringAdd(i8* %add.65, i8* %call.40)
    %__stringLiteral.22 = getelementptr [3 x i8], [3 x i8]* @.str.1, i32 0, i32 0
    %add.67 = call i8* @__stringAdd(i8* %add.66, i8* %__stringLiteral.22)
    %this.159 = load %class.point*, %class.point** %this$.31
    %z$.66 = getelementptr %class.point, %class.point* %this.159, i32 0, i32 2
    %z.66 = load i32, i32* %z$.66
    %call.41 = call i8* @__toString(i32 %z.66)
    %add.68 = call i8* @__stringAdd(i8* %add.67, i8* %call.41)
    %__stringLiteral.23 = getelementptr [2 x i8], [2 x i8]* @.str.2, i32 0, i32 0
    %add.69 = call i8* @__stringAdd(i8* %add.68, i8* %__stringLiteral.23)
    call void @__println(i8* %add.69)
    store i32 0, i32* %returnValue$.0
    %returnValue.0 = load i32, i32* %returnValue$.0
    ret i32 %returnValue.0

}

